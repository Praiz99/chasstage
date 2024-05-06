package com.wckj.chasstage.modules.dhsgl.service.impl;

import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.dhssyjl.service.ChasDhsSyjlService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.msg.MessageProduceCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChasDhsglServiceImpl implements ChasDhsglService {
    protected Logger log = LoggerFactory.getLogger(ChasDhsglServiceImpl.class);
    @Autowired
    private ChasXtQyService chasQyService;
    @Lazy
    @Autowired(required = false)
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ChasSxsKzService chasSxsKzService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private ChasDhsKzService dhsKzService;
    @Autowired
    private ChasSxsglService chasSxsglService;
    // 回到等候室 验证笔录,嫌疑人定位事件中调用
    @Autowired
    private ChasDhsSyjlService chasDhsSyjlService;
    @Override
    public DevResult dhsFpYzBl(String baqid, String rybh, String qyid) {
        log.debug(String.format("办案区{%s}人员{%s}回到等候室,开始验证笔录是否结束",baqid,rybh));
        DevResult wr = new DevResult();
        // 查询笔录状态
        String msg;
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        //根据人员编号查找最后一条审讯室分配记录，用来判断笔录是否结束
        Map<String,Object> map = new HashMap<>();
        map.put("rybh", rybh);
        List<ChasSxsKz> sxsKzs = chasSxsKzService.getSxsKzByRybh(map);
        if(sxsKzs != null && !sxsKzs.isEmpty()){
            ChasSxsKz chasSxsKz = sxsKzs.get(0);
            // 检查笔录完成
            if (chasSxsKz.getHdsj() == null) {
                msg = String.format("办案区:%s 人员:%s  编号:%s 到达等候室 笔录未完成",
                        chasRyj.getBaqmc(), chasRyj.getXm(), chasRyj.getRybh());
                log.error(msg);
                wr.setCodeMessage(0, msg);
                return wr;
            }
            //刷新审讯室，防止定位没有感应到人员离开审讯室led不刷新
            ledService.refreshSxsLedBySxskz(chasSxsKz.getBaqid(), chasSxsKz.getQyid(), chasSxsKz.getId());
            String content=chasSxsglService.getKsqLedText(chasSxsKz.getBaqid());
            ledService.refreshSxsDxp(chasSxsKz.getBaqid(),content);
            return dhsFp(baqid, rybh, qyid);
        }
        wr.setCodeMessage(0, "未找到人员审讯室分配记录");
        return wr;
    }
    @Override
    public DevResult aloneAssign(String baqid, String rybh, String qyid){
        String msg;
        ChasRyjl chasRyjl = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if(chasRyjl==null){
            return DevResult.error(rybh+"找不到人员信息");
        }
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
        DevResult wr = new DevResult();
        if(StringUtils.isNotEmpty(chasRyjl.getSxsBh())){
            ChasSxsKz chasSxsKz = chasSxsKzService.findAllById(chasRyjl.getSxsBh());
            if (chasSxsKz != null) {
                //验证笔录是否完成
                if(chasSxsKz.getHdsj() == null){
                    msg = String.format("办案区:%s 人员:%s  编号:%s 等候室分配,笔录未完成不予分配",
                            chasRyjl.getBaqmc(), chasRyjl.getXm(), chasRyjl.getRybh());
                    wr.setCodeMessage(0, msg);
                    wr.setReason("笔录未完成不予分配");
                    return wr;
                }
            }
        }
        // 已经分配过 检查分配状态 检查
        String dhsbh = chasRyjl.getDhsBh();

        // 手动分配验证逻辑在C层
        if (StringUtils.isNotEmpty(qyid)) {
            return manualAssign(baqid, rybh, qyid, chasRyjl, dhsbh);
        } else {//自动分配
            if (StringUtils.isNotEmpty(dhsbh)) {
                ChasDhsKz chasDhsKz = dhsKzService.findById(dhsbh);
                if (chasDhsKz != null) {
                    // 已分配 但暂离
                    if (SYSCONSTANT.DHSTZ_ZL.equals(chasDhsKz.getFpzt())) {
                        if (StringUtils.isNotEmpty(chasRyjl.getSxsBh())) {
                            ChasSxsKz chasSxsKz = chasSxsKzService.findAllById(chasRyjl.getSxsBh());
                            if (chasSxsKz != null) {
                                //验证笔录是否完成
                                if (chasSxsKz.getHdsj() == null) {
                                    msg = String.format("办案区:%s 人员:%s  编号:%s 等候室分配,笔录未完成不予分配",
                                            chasRyjl.getBaqmc(), chasRyjl.getXm(), chasRyjl.getRybh());
                                    wr.setCodeMessage(0, msg);
                                    wr.setReason("笔录未完成不予分配");
                                    return wr;
                                }
                            }
                        }
                        chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_FP);
                        dhsKzService.update(chasDhsKz);
                        dhsGx(baqid, dhsbh);
                        msg = "办案区:{" + baqid + "} 人员:{" + rybh + "} 暂离->分配 恢复分配状态";
                        log.debug(msg);
                        wr.setCodeMessage(1, msg);
                        ChasXtQy qy = chasQyService.findByYsid(chasDhsKz.getQyid());
                        wr.add("qy", qy);
                        wr.add("ryxx", baqryxx);
                        wr.setReason("人员重新回到等候室");
                        return wr;
                    }
                }
            }
            List<ChasXtQy> qyList = getKxDhs(baqid, SYSCONSTANT.DHSLX_PT);
            if(qyList==null||qyList.isEmpty()){
                wr.setCodeMessage(3, "暂无空闲等候室,无法单独看押");
                return wr;
            }
            Collections.shuffle(qyList);
            ChasXtQy qy=qyList.get(0);

            //保存等候室分配关系
            ChasDhsKz chasDhsKz = new ChasDhsKz();
            chasDhsKz.setId(StringUtils.getGuid32());
            chasDhsKz.setBaqid(baqid);
            chasDhsKz.setBaqmc(chasRyjl.getBaqmc());
            chasDhsKz.setRyid(chasRyjl.getId());
            chasDhsKz.setRybh(rybh);
            chasDhsKz.setRyxm(chasRyjl.getXm());
            chasDhsKz.setRyxb(chasRyjl.getXb());
            chasDhsKz.setLrsj(new Date());
            chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_FP);
            chasDhsKz.setWdbh(chasRyjl.getWdbhL());
            chasDhsKz.setDcjd(chasRyjl.getRyzt());
            chasDhsKz.setKssj(new Date());
            chasDhsKz.setQyid(qy.getYsid());
            dhsKzService.save(chasDhsKz);
            chasRyjl.setDhsBh(chasDhsKz.getId());
            chasRyjl.setXgrSfzh(baqryxx.getMjSfzh());
            chasRyjlService.update(chasRyjl);
            updateQykzlx(qy);//防止单独看押再分配其他人员
            chasQyService.update(qy);
            // 刷新等候室
            ledService.refreshDhsLed(baqid, chasDhsKz.getQyid());
            // 刷新大屏
            ledService.refreshDhsDp(baqid);
            wr.setCodeMessage(1, "分配成功");
            wr.add("qy", qy);
            wr.add("ryxx", baqryxx);
            wr.setReason("分配成功");
            return wr;

        }
    }
    private void updateQykzlx(ChasXtQy qy){
        if(qy!=null&&StringUtils.isNotEmpty(qy.getId())){
            if(SYSCONSTANT.DHSLX_PT.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_PT_DDKY);//防止该等候室分配其他人员
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_TS.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_TS_DDKY);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NAN.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NAN_DDKY);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NV.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NV_DDKY);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_WCN.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_WCN_DDKY);
                chasQyService.update(qy);
            }

        }
    }
    private void resetQykzlx(ChasXtQy qy){
        if(qy!=null&&StringUtils.isNotEmpty(qy.getId())){
            if(SYSCONSTANT.DHSLX_PT_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_PT);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_TS_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_TS);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NAN_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NAN);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NV_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NV);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_WCN_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_WCN);
                chasQyService.update(qy);
            }

        }
    }
    @Override
    public DevResult dhsFp(String baqid, String rybh, String qyid) {
        String msg;
        ChasRyjl chasRyjl = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if(chasRyjl==null){
            return DevResult.error(rybh+"找不到人员信息");
        }
        DevResult wr = new DevResult();
        if(StringUtils.isNotEmpty(chasRyjl.getSxsBh())){
            ChasSxsKz chasSxsKz = chasSxsKzService.findAllById(chasRyjl.getSxsBh());
            if (chasSxsKz != null) {
                //验证笔录是否完成
                if(chasSxsKz.getHdsj() == null){
                    msg = String.format("办案区:%s 人员:%s  编号:%s 等候室分配,笔录未完成不予分配",
                            chasRyjl.getBaqmc(), chasRyjl.getXm(), chasRyjl.getRybh());
                    wr.setCodeMessage(0, msg);
                    wr.setReason("笔录未完成不予分配");
                    return wr;
                }
            }
        }
        // 已经分配过 检查分配状态 检查
        String dhsbh = chasRyjl.getDhsBh();

        // 手动分配验证逻辑在C层
        if (StringUtils.isNotEmpty(qyid)) {
            return manualAssign(baqid, rybh, qyid, chasRyjl, dhsbh);
        } else {//自动分配
            if (StringUtils.isNotEmpty(dhsbh)) {
                ChasDhsKz chasDhsKz = dhsKzService.findById(dhsbh);
                if (chasDhsKz != null) {
                    // 已分配 但暂离
                    if (SYSCONSTANT.DHSTZ_ZL.equals(chasDhsKz.getFpzt())) {
                        if(StringUtils.isNotEmpty(chasRyjl.getSxsBh())){
                            ChasSxsKz chasSxsKz = chasSxsKzService.findAllById(chasRyjl.getSxsBh());
                            if (chasSxsKz != null) {
                                //验证笔录是否完成
                                if(chasSxsKz.getHdsj() == null){
                                    msg = String.format("办案区:%s 人员:%s  编号:%s 等候室分配,笔录未完成不予分配",
                                            chasRyjl.getBaqmc(), chasRyjl.getXm(), chasRyjl.getRybh());
                                    wr.setCodeMessage(0, msg);
                                    wr.setReason("笔录未完成不予分配");
                                    return wr;
                                }
                            }
                        }
                        chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_FP);
                        dhsKzService.update(chasDhsKz);
                        dhsGx(baqid, dhsbh);
                        msg = "办案区:{"+baqid+"} 人员:{"+rybh+"} 暂离->分配 恢复分配状态";
                        log.debug(msg);
                        wr.setCodeMessage(1, msg);
                        ChasXtQy qy = chasQyService.findByYsid(chasDhsKz.getQyid());
                        wr.add("qy", qy);
                        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
                        wr.add("ryxx", baqryxx);
                        wr.setReason("人员重新回到等候室");
                        return wr;
                    }
                }
            }
            return autoAssign(baqid,rybh,dhsbh,chasRyjl);
        }
    }
    //根据房间扩展类型查询办案区空闲等候室
    private List<ChasXtQy> getKxDhs(String baqid, String roomType){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        map.put("kzlx", roomType);
        map.put("isdel", SYSCONSTANT.N_I);
        map.put("dhsk", SYSCONSTANT.N);
        return chasQyService.findByParams(map);
    }
    private ChasBaqryxx getRyxx(String baqid, String rybh){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("rybh", rybh);
        map.put("isdel", SYSCONSTANT.ALL_DATA_MARK_NORMAL_I);
        List<ChasBaqryxx> ryxxlist = chasBaqryxxService.findList(map, null);
        if (ryxxlist != null && !ryxxlist.isEmpty()) {
            return ryxxlist.get(0);
        }
        return null;
    }
    /**
     * 根据案件、性别、房间类型、人数、人员类型等因素自动分配等候室
     * @return
     */
    private DevResult autoAssign(String baqid, String rybh, String dhsbh, ChasRyjl chasRyjl){
        String msg;
        // 如果已经分配 是分配状态 则不再自动分配
        if (StringUtils.isNotEmpty(dhsbh)) {
            ChasDhsKz chasDhsKz = dhsKzService.findById(dhsbh);
            if (chasDhsKz != null) {
                if (SYSCONSTANT.DHSTZ_FP.equals(chasDhsKz.getFpzt())) {
                    msg = String.format("办案区:%s 人员:%s 已分配不再自动分配", baqid,
                            chasDhsKz.getRyxm());
                    log.debug(msg);
                    ChasXtQy qy = chasQyService.findByYsid(chasDhsKz.getQyid());
                    DevResult result =  DevResult.error(msg);
                    result.add("qy", qy);
                    ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
                    result.add("ryxx", baqryxx);
                    result.setReason("已分配不再自动分配");
                    result.setCode(2);
                    return result;
                }
            }
        }
        ChasBaqryxx ryxx =  getRyxx(baqid,rybh);
        // 自动分配
        // 登记时查询是否同案
        //String ywbh = chasRyjl.getYwbh();
        String ywbh=ryxx.getAjbh();
        if(StringUtil.isEmpty(ywbh)){
            ywbh = ryxx.getJqbh();
        }
        log.debug(String.format("办案区:%s 查询是否存在同案犯：%s", baqid, ywbh));
        Map<String, Object> map = new HashMap<>();
        // 查询房间类型 办案区必须配等候室
        List<ChasXtQy> qylist = getDhs(baqid);
        if (qylist.isEmpty()) {
            msg = String.format("办案区:%s 未配置等候室", baqid);
            log.debug(msg);
            return DevResult.error(msg);
        }
        ChasDhsKz chasDhsKz = createDhsKz(baqid,rybh,chasRyjl);


        String message = "分配等候室成功";
        int code = 1;
        if(ryxx != null){
            if(chasBaqryxxService.isWcn(ryxx)){//未成年人分配到未成年人等候室
                code= assignWcnDhs(chasDhsKz,chasRyjl,ryxx);
                if(code==3){
                    String xm = chasDhsKz.getRyxm();
                    if(StringUtils.isEmpty(xm)){
                        xm="此人员";
                    }
                    ChasXtQy xtQy = chasQyService.findByYsid(chasDhsKz.getQyid());
                    message = String.format("人员：%s，不能预分配至同案、异性人员所在等候室，已被强制分配至：%s。",
                            xm,xtQy.getQymc());
                }
            }
            if(chasDhsKz.getQyid() == null){
                if(StringUtils.equals(ryxx.getTsqt(),"99") ||
                        StringUtils.isEmpty(ryxx.getTsqt())){
                    //tsqt = "0";//人员不是特殊群体
                }else{
                    //tsqt = "1";//人员是特殊群体
                    map.clear();
                    map.put("baqid", baqid);
                    map.put("fjlx", SYSCONSTANT.FJLX_DHS);
                    map.put("kzlx", SYSCONSTANT.DHSLX_TS);
                    map.put("isdel", SYSCONSTANT.N_I);
                    List<ChasXtQy> tsdhslist = chasQyService.findByParams(map);
                    if (tsdhslist!=null&&!tsdhslist.isEmpty()){//存在特殊等候室
                        //assignTsDhs(chasDhsKz,chasRyjl,ryxx);
                        assignTsDhs(chasDhsKz,chasRyjl,ryxx);
                        if (chasDhsKz.getQyid() == null) {
                            log.debug(String.format("办案区:%s 最终分配-所有特殊房间分配人数最少的:%s", baqid,
                                    chasDhsKz.getQyid()));
                            map.clear();
                            map.put("baqid", baqid);
                            map.put("isdel", SYSCONSTANT.N_I);
                            map.put("kzlx", SYSCONSTANT.DHSLX_TS);
                            List<ChasXtQy> fn = chasQyService.findKfpDhs(map);
                            chasDhsKz.setQyid(fn.get(0).getYsid());
                            String xm = chasDhsKz.getRyxm();
                            if(StringUtils.isEmpty(xm)){
                                xm="此人员";
                            }
                            ChasXtQy xtQy = chasQyService.findByYsid(fn.get(0).getYsid());
                            message = String.format("人员：%s，不能预分配至同案、异性人员所在等候室，已被强制分配至：%s。",
                                    xm,xtQy.getQymc());
                            code = 3;
                        }
                    }
                }
            }
        }

        if (chasDhsKz.getQyid() == null) {
            // 只有一间直接分配等候室
            map.clear();
            map.put("baqid", baqid);
            map.put("fjlx", SYSCONSTANT.FJLX_DHS);
            map.put("isdel", SYSCONSTANT.N_I);
            List<ChasXtQy> onedhslist = chasQyService.findByParams(map);
            if (onedhslist.size() == 1 && chasDhsKz.getQyid() == null)
                chasDhsKz.setQyid(onedhslist.get(0).getYsid());
        }
        if(chasDhsKz.getQyid() == null){
            List<ChasXtQy> qyList= null;
            String type = "";
            if("1".equals(ryxx.getXb())){//男
                type = SYSCONSTANT.DHSLX_NAN;
                qyList = getNanOrNvDhs(baqid, SYSCONSTANT.DHSLX_NAN);
            }else if("2".equals(ryxx.getXb())){//女
                type = SYSCONSTANT.DHSLX_NV;
                qyList = getNanOrNvDhs(baqid, SYSCONSTANT.DHSLX_NV);
            }
            if(StringUtils.isNotEmpty(type)){
                List<ChasXtQy> kong = getKxDhs(baqid,type);
                if (!kong.isEmpty()) {
                    log.debug("查询空房 有直接随机分配一间");
                    Collections.shuffle(kong);
                    Random random = new Random();
                    int i=random.nextInt(kong.size());
                    chasDhsKz.setQyid(kong.get(i).getYsid());
                }
            }
            if(chasDhsKz.getQyid() == null&&qyList!=null&&!qyList.isEmpty()){
                chasDhsKz.setQyid(qyList.get(0).getYsid());
            }
        }
        if(chasDhsKz.getQyid() == null){
            // 空房分配 、 同性别、不同案分配
            assignroom(chasDhsKz, chasRyjl.getBaqid(), SYSCONSTANT.DHSLX_PT,ywbh, chasRyjl.getXb());
        }

        // 清一色 等候室&候问室&同案&性别 直接分配 人数最少
        if (chasDhsKz.getQyid() == null) {
            log.debug(String.format("办案区:%s 最终分配-所有房间分配人数最少的:%s", baqid,
                    chasDhsKz.getQyid()));
            map.clear();
            map.put("baqid", baqid);
            map.put("isdel", SYSCONSTANT.N_I);
            map.put("fjlx", SYSCONSTANT.FJLX_DHS);
            map.put("kzlx", SYSCONSTANT.DHSLX_PT);
            List<ChasXtQy> fn = chasQyService.findKfpDhs(map);
            if(fn==null||fn.isEmpty()){
                return DevResult.error("无可用普通等候室");
            }
            chasDhsKz.setQyid(fn.get(0).getYsid());
            ChasXtQy xtQy = chasQyService.findByYsid(fn.get(0).getYsid());
            String xm = chasDhsKz.getRyxm();
            if(StringUtils.isEmpty(xm)){
                xm="此人员";
            }
            message = String.format("人员：%s，不能预分配至同案、异性人员所在等候室，已被强制分配至：%s。",
                    xm,xtQy.getQymc());
            code = 3;
        }
        dhsKzService.save(chasDhsKz);
        chasRyjl.setDhsBh(chasDhsKz.getId());
        chasRyjl.setXgrSfzh(ryxx.getMjSfzh());
        chasRyjlService.update(chasRyjl);
        // 刷新等候室
        ledService.refreshDhsLed(baqid,chasDhsKz.getQyid());
        // 刷新大屏
        ledService.refreshDhsDp(baqid);
        log.debug(String.format("办案区:%s 人员:%s分配到等候室:%s", baqid,
                chasRyjl.getXm(), chasDhsKz.getQyid()));
        DevResult r= DevResult.success(message);
        r.setCode(code);
        ChasXtQy qy = chasQyService.findByYsid(chasDhsKz.getQyid());
        r.add("qy", qy);
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
        r.add("ryxx", baqryxx);
        //发送消息给yjjd
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> sendMap = new HashMap<String, Object>();
        sendMap.put("ajbh", baqryxx.getAjbh());
        sendMap.put("jqbh", baqryxx.getJqbh());
        sendMap.put("ryxm", baqryxx.getRyxm());
        sendMap.put("rybh", baqryxx.getRybh());
        sendMap.put("rysfzh", baqryxx.getRysfzh());
        sendMap.put("czlx", "01");
        sendMap.put("fpsj", format.format(new Date()));
        log.debug("发送分配等候室消息给yjjd系统！");
        MessageProduceCenter.send(this, "baqryfp", sendMap, "zfgl-yjjd");
        if(code==1){
            r.setReason("分配成功");
        }else if(code ==3){
            // r.setReason("分配成功");
        }
        return r;
    }


    //获取男等候室、或者女等候室列表
    private List<ChasXtQy> getNanOrNvDhs(String baqid, String roomType){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        map.put("kzlx", roomType);
        map.put("isdel", SYSCONSTANT.N_I);
        return chasQyService.findKfpDhs(map);
    }
    //为特殊群体分配等候室
    private void assignTsDhs(ChasDhsKz chasDhsKz, ChasRyjl chasRyjl, ChasBaqryxx ryxx){
        //查询空闲特殊等候室
        List<ChasXtQy> kongTs = getKxDhs(ryxx.getBaqid(), SYSCONSTANT.DHSLX_TS);
        log.debug(String.format("查询空闲特殊等候室 有直接分配一间  空房数量：%d", kongTs.size()));
        if (!kongTs.isEmpty()) {
            log.debug("查询空闲特殊等候室 有直接随机分配一间");
            Collections.shuffle(kongTs);
            Random random = new Random();
            int i=random.nextInt(kongTs.size());
            chasDhsKz.setQyid(kongTs.get(i).getYsid());
            return;
        }
        //查询空闲普通等候室
        List<ChasXtQy> kongpt = getKxDhs(ryxx.getBaqid(), SYSCONSTANT.DHSLX_PT);
        if (!kongpt.isEmpty()) {
            log.debug("查询空闲普通等候室 有直接随机分配一间");
            Collections.shuffle(kongpt);
            Random random = new Random();
            int i=random.nextInt(kongpt.size());
            ChasXtQy dhs=kongpt.get(i);
            chasDhsKz.setQyid(dhs.getYsid());
            //同时将普通等候室暂时设置成临时特殊等候室，但是在人员离开办案区后，将此等候室还原成普通等候室
            //dhs.setKzlx(SYSCONSTANT.DHSLX_LSTS);
            chasQyService.update(dhs);
            return;
        }
        //从特殊等候室中分配房间
        assignroom(chasDhsKz, ryxx.getBaqid(), SYSCONSTANT.DHSLX_TS, chasRyjl.getAjbh(), chasRyjl.getXb());
    }
    //分配未成年人等候室
    private int assignWcnDhs(ChasDhsKz chasDhsKz, ChasRyjl chasRyjl, ChasBaqryxx ryxx){
        int code =1;
        String message="";
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", chasDhsKz.getBaqid());
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        map.put("isdel", SYSCONSTANT.N_I);
        List<ChasXtQy> onedhslist = chasQyService.findByParams(map);
        if (onedhslist.size() == 1 && chasDhsKz.getQyid() == null){
            chasDhsKz.setQyid(onedhslist.get(0).getYsid());
            code=1;
        }
        if(StringUtils.isEmpty(chasDhsKz.getQyid())){
            //查询空闲未成年人等候室
            List<ChasXtQy> kongTs = getKxDhs(ryxx.getBaqid(), SYSCONSTANT.DHSLX_WCN);
            log.debug(String.format("查询空闲未成年人等候室 有直接分配一间  空房数量：%d", kongTs.size()));
            if (!kongTs.isEmpty()) {
                log.debug("查询空闲未成年人等候室 有直接随机分配一间");
                Collections.shuffle(kongTs);
                Random random = new Random();
                int i=random.nextInt(kongTs.size());
                chasDhsKz.setQyid(kongTs.get(i).getYsid());
                code=1;
            }
        }
        if(StringUtils.isEmpty(chasDhsKz.getQyid())){
            //从未成年人等候室中分配房间
            assignroom(chasDhsKz, ryxx.getBaqid(), SYSCONSTANT.DHSLX_WCN, chasRyjl.getAjbh(), chasRyjl.getXb());
            if(StringUtils.isNotEmpty(chasDhsKz.getQyid())){
                code=1;
            }
        }
        if (StringUtils.isEmpty(chasDhsKz.getQyid())) {
            map.clear();
            map.put("baqid", chasDhsKz.getBaqid());
            map.put("isdel", SYSCONSTANT.N_I);
            map.put("fjlx", SYSCONSTANT.FJLX_DHS);
            map.put("kzlx", SYSCONSTANT.DHSLX_WCN);
            List<ChasXtQy> fn = chasQyService.findKfpDhs(map);
            if(fn==null||fn.isEmpty()){
                return 1;
            }
            chasDhsKz.setQyid(fn.get(0).getYsid());
            log.debug(String.format("办案区:%s 最终分配-所有未成年人等候室分配人数最少的:%s", chasDhsKz.getBaqid(),
                    chasDhsKz.getQyid()));
            code = 3;
        }
        return code;
    }
    /**
     * 手动分配等候室
     * @param baqid  办案区id
     * @param rybh 人员编号
     * @param qyid  选择的等候室id
     * @param chasRyjl 人员记录
     * @param dhsbh  原等候室id
     * @return
     */
    private DevResult manualAssign(String baqid, String rybh, String qyid, ChasRyjl chasRyjl, String dhsbh) {

        //原来已经分配过了等候室，需要先解除分配关系，再重新分配。用于等候室手动调整
        if (StringUtils.isNotEmpty(dhsbh)) {

            ChasDhsKz chasDhsKz = dhsKzService.findById(dhsbh);
            if(chasDhsKz.getQyid().equals(qyid)){
                log.error(String.format("办案区{%s}人员{%s}已经被分配在等候室{%s}，无需再次分配",
                        baqid,rybh,qyid));
                return  DevResult.error("该人员已经在此等候室，请选择其他等候室");
            }
            if (chasDhsKz != null) {
                if (SYSCONSTANT.DHSTZ_FP.equals(chasDhsKz.getFpzt())) {
                    // 解除并且刷新
                    String dhsold = chasDhsKz.getQyid();
                    ChasXtQy qy = chasQyService.findByYsid(dhsold);
                    resetQykzlx(qy);
                    chasDhsKz.setQyid(null);
                    dhsKzService.update(chasDhsKz);
                    // 刷新等候室
                    ledService.refreshDhsLed(baqid,dhsold);
                }
            }
            // 手动分配
            chasDhsKz.setQyid(qyid);
            dhsKzService.update(chasDhsKz);
            chasDhsSyjlService.updateDhsJl(chasDhsKz);
            // 刷新等候室
            ledService.refreshDhsLed(baqid,qyid);
            // 刷新大屏
            ledService.refreshDhsDp(baqid);
            log.debug(String.format("办案区:%s 人员:%s 分配等候室%s成功", baqid,
                    chasDhsKz.getRyxm(), qyid));

        }else {
            //保存等候室分配关系
            ChasDhsKz chasDhsKz = new ChasDhsKz();
            chasDhsKz.setId(StringUtils.getGuid32());
            chasDhsKz.setBaqid(baqid);
            chasDhsKz.setBaqmc(chasRyjl.getBaqmc());
            chasDhsKz.setRyid(chasRyjl.getId());
            chasDhsKz.setRybh(rybh);
            chasDhsKz.setRyxm(chasRyjl.getXm());
            chasDhsKz.setRyxb(chasRyjl.getXb());
            chasDhsKz.setLrsj(new Date());
            chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_FP);
            chasDhsKz.setWdbh(chasRyjl.getWdbhL());
            chasDhsKz.setDcjd(chasRyjl.getRyzt());
            chasDhsKz.setKssj(new Date());
            chasDhsKz.setQyid(qyid);
            dhsKzService.save(chasDhsKz);
            chasDhsSyjlService.saveSyjl(chasDhsKz);
            ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
            chasRyjl.setDhsBh(chasDhsKz.getId());
            chasRyjl.setXgrSfzh(baqryxx.getMjSfzh());
            chasRyjlService.update(chasRyjl);
            // 刷新等候室
            ledService.refreshDhsLed(baqid, chasDhsKz.getQyid());
            // 刷新大屏
            ledService.refreshDhsDp(baqid);
            log.debug(String.format("办案区:%s 人员:%s分配到等候室:%s", baqid,
                    chasRyjl.getXm(), chasDhsKz.getQyid()));
        }
        ChasXtQy qy = chasQyService.findByYsid(qyid);
        DevResult result =  DevResult.success("分配等候室成功");
        result.add("qy", qy);
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
        result.add("ryxx", baqryxx);
        //发送消息给yjjd
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> sendMap = new HashMap<String, Object>();
        sendMap.put("ajbh", baqryxx.getAjbh());
        sendMap.put("jqbh", baqryxx.getJqbh());
        sendMap.put("ryxm", baqryxx.getRyxm());
        sendMap.put("rybh", baqryxx.getRybh());
        sendMap.put("rysfzh", baqryxx.getRysfzh());
        sendMap.put("czlx", "01");
        sendMap.put("fpsj", format.format(new Date()));
        log.debug("发送分配等候室消息给yjjd系统！");
        MessageProduceCenter.send(this, "baqryfp", sendMap, "zfgl-yjjd");
        return result;
    }
    //查询办案区等候室
    private List<ChasXtQy> getDhs(String baqid){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        //不需要 区分等候室 侯问室
//        map.put("kzlx", SYSCONSTANT.DHSLX_DHS);
        map.put("isdel", SYSCONSTANT.N_I);
        return chasQyService.findByParams(map);
    }
    //创建等候室分配记录对象
    private ChasDhsKz createDhsKz(String baqid, String rybh, ChasRyjl chasRyjl){
        ChasDhsKz chasDhsKz = new ChasDhsKz();
        chasDhsKz.setId(StringUtils.getGuid32());
        chasDhsKz.setBaqid(baqid);
        chasDhsKz.setBaqmc(chasRyjl.getBaqmc());
        chasDhsKz.setRyid(chasRyjl.getId());
        chasDhsKz.setRybh(rybh);
        chasDhsKz.setRyxm(chasRyjl.getXm());
        chasDhsKz.setRyxb(chasRyjl.getXb());
        chasDhsKz.setLrsj(new Date());
        chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_FP);
        chasDhsKz.setWdbh(chasRyjl.getWdbhL());
        chasDhsKz.setDcjd(chasRyjl.getRyzt());
        chasDhsKz.setKssj(new Date());
        return chasDhsKz;
    }

    //查询不同案 同性别 按已分配人数升序排序的等候室
    private List<ChasXtQy> getNotTaTxbLeastDhs(String baqid, String roomType, String ywbh, String xb){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        map.put("kzlx", roomType);
        map.put("isdel", SYSCONSTANT.N_I);
        //查询所有的等候室
        List<ChasXtQy> xtQyList = chasQyService.findByParams(map);
        //过滤掉同案的等候室
        if(ywbh!=null){
            String[] ywArray = ywbh.split(",");
            if(ywArray!=null&&ywArray.length>0){
                xtQyList=xtQyList.stream().filter(qy->{
                    for(String yw:ywArray){
                        if(StringUtils.isEmpty(yw)){
                            continue;
                        }
                        int c=dhsKzService.getTaRs(qy.getYsid(), yw, baqid);
                        if(c>0){
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());
            }
        }

        //过滤掉混关的等候室
        if(xtQyList!=null&&!xtQyList.isEmpty()){
            if("1".equals(xb)){
                xtQyList=xtQyList.stream().filter(qy->{
                    int c=dhsKzService.getHgRs(qy.getYsid(), "2",baqid);
                    if(c>0){
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList());
            }else if("2".equals(xb)){
                xtQyList=xtQyList.stream().filter(qy->{
                    int c=dhsKzService.getHgRs(qy.getYsid(), "1",baqid);
                    if(c>0){
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList());
            }

        }
        //根据人数排序
        if(xtQyList!=null&&!xtQyList.isEmpty()){
            xtQyList.sort((qy1,qy2)->{
                int c1= dhsKzService.findcountByBaqidAndQyid(qy1.getBaqid(),qy1.getYsid());
                int c2= dhsKzService.findcountByBaqidAndQyid(qy2.getBaqid(),qy2.getYsid());
                return c1-c2;
            });
        }
        return xtQyList;
    }

    /**
     * 1、直接分配空闲等候室
     * 2、分配同案、同性别、 人数最少的等候室(废弃)
     * 3、分配不同案、同性别、人数最少的等候室
     * @param chasDhsKz
     * @param baqid
     * @param roomType
     * @param ywbh
     * @param xb
     */
    private void assignroom(ChasDhsKz chasDhsKz, String baqid, String roomType, String ywbh, String xb) {
        String msg = "开始分配房间 办案区:{"+baqid+"}|房间类型:{"+roomType+"}|业务:{"+ywbh+"}|性别:{"+xb+"}";
        log.debug(msg);
        // 查询空房 有直接分配一间
        List<ChasXtQy> kong = getKxDhs(baqid,roomType);
        log.debug(String.format("查询空房 有直接分配一间  空房数量：%d", kong.size()));
        if (!kong.isEmpty()) {
            log.debug("查询空房 有直接随机分配一间");
            Collections.shuffle(kong);
            Random random = new Random();
            int i=random.nextInt(kong.size());
            chasDhsKz.setQyid(kong.get(i).getYsid());
            return;
        }
        // 无空房 不同案 同性别 人最少一间
        List<ChasXtQy> ny = getNotTaTxbLeastDhs(baqid,roomType,ywbh,xb);
        log.debug(String.format("无空房 不同案 同性别 人最少一间  空房数量：%d", ny.size()));
        if (!ny.isEmpty()) {
            log.debug("无空房 不同案 同性别 人最少一间");
            chasDhsKz.setQyid(ny.get(0).getYsid());
            return;
        }
    }


    @Override
    public DevResult dhsGx(String baqid, String dhsbh) {
        ChasDhsKz chasDhs = dhsKzService.findById(dhsbh);
        if (chasDhs != null) {
            DevResult wr = ledService.refreshDhsLed(baqid,chasDhs.getQyid());
            wr.add("dhsDXLed", ledService.refreshDhsDp(baqid));
            return wr;
        } else {
            //String text = "根据等候室编号 未找到分配记录,办案区:"+baqid+",等候室编号:"+dhsbh;
            log.error("根据等候室编号 未找到分配记录");
            return DevResult.error("根据等候室编号 未找到分配记录");
        }
    }
    //人员变更姓名时，更新等候室led信息
    @Override
    public DevResult dhsGxXx(String baqid, String rybh) {
        log.debug(String.format("更新办案区{%s}人员{%s}等候室led显示信息",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        ChasDhsKz chasDhs = dhsKzService.findById(chasRyj.getDhsBh());
        if (chasDhs != null) {
            if (!chasRyj.getXm().equals(chasDhs.getRyxm())) {
                chasDhs.setRyxm(chasRyj.getXm());
                dhsKzService.update(chasDhs);
                return dhsGx(baqid, chasRyj.getDhsBh());
            } else {
                return DevResult.error("无变更项");
            }
        } else {
            log.error("根据等候室编号 未找到分配记录");
            return DevResult.error("根据等候室编号 未找到分配记录");
        }

    }

    //解除人员与等候室之间的分配关系
    @Override
    public DevResult dhsJc(String baqid, String rybh, boolean dxled) {
        log.debug(String.format("解除 办案区{%s}人员{%s}等候室分配关系",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        ChasDhsKz chasDhs = dhsKzService.findById(chasRyj.getDhsBh());
        if (chasDhs == null) {
            return DevResult.success("解除等候室-已出所");
        }
        //将临时特殊等候室恢复成普通等候室
        ChasXtQy xtQy = chasQyService.findByYsid(chasDhs.getQyid());
        if(xtQy != null&& SYSCONSTANT.DHSLX_LSTS.equals(xtQy.getKzlx())){
            xtQy.setKzlx(SYSCONSTANT.DHSLX_PT);
            chasQyService.update(xtQy);
        }
        resetQykzlx(xtQy);//恢复单独看押的状态
        chasDhs.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
        dhsKzService.update(chasDhs);

        //((ChasDhsKzService)AopContext.currentProxy()).clearByrybh(chasRyj.getRybh());
        //删除人员记录表中人员分配的等候室信息
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
        chasRyj.setDhsBh(null);
        chasRyj.setXgrSfzh(baqryxx.getMjSfzh());
        chasRyjlService.update(chasRyj);
        DevResult wr = ledService.refreshDhsLed(baqid,chasDhs.getQyid());
        if (dxled) {
            wr.add("dhsJc dhsDXLed", ledService.refreshDhsDp(baqid));
        }
        String msg = String.format("办案区：%s 人员:%s解除等候室:%s 是否刷新大小屏:%s", baqid,
                chasRyj.getXm(), chasDhs.getQyid(), dxled);
        log.debug(msg);
        wr.setCodeMessage(1, msg);
        return wr;
    }
}
