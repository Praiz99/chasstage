package com.wckj.chasstage.modules.sxsgl.service.impl;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.yybb.model.YybbSendParam;
import com.wckj.chasstage.api.def.yybb.model.YyhjEnue;
import com.wckj.chasstage.api.server.device.IDzspService;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.internal.IBbqOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.YybbUtil;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.qysljcjl.service.ChasYwQysljcjlService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.chasstage.modules.sxdp.service.ChasSxdpService;
import com.wckj.chasstage.modules.sxsgbjl.service.ChasSxsGbjlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.msg.MessageProduceCenter;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserLoginTokenService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChasSxsglServiceImpl implements ChasSxsglService {
    protected Logger log = Logger.getLogger(ChasSxsglServiceImpl.class);
    @Autowired
    private ChasXtQyService qyService;
    @Autowired(required = false)
    @Lazy
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private IJdqService jdqService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ChasDhsKzService chasDhsKzService;
    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private IDzspService dzspService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasSxsKzService sxsKzService;
    @Autowired
    private ChasSxsGbjlService sxsGbjlService;
    @Autowired
    private ChasSxdpService sxdpService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private IBbqOper bbqOper;
    @Autowired
    private ChasYwQysljcjlService qysljcjlService;
    @Override
    public Map<String, Object> close(String qyid) {
        Map<String,Object> result = new HashMap<>();
        try {
            ChasXtQy qy = qyService.findByYsid(qyid);
            if(StringUtils.isEmpty(qyid)){
                result.put("success", false);
                result.put("msg", "主键ID不能为空!");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            param.put("qyid", qyid);
            param.put("baqid", qy.getBaqid());
            List<ChasSxsKz> sxsKzList = sxsKzService.findList(param, "xgsj desc");
            if(!sxsKzList.isEmpty()) {
                ChasSxsKz sxs = sxsKzList.get(0);

                if(StringUtils.isNotEmpty(sxs.getRybh())) {
                    param.clear();
                    param.put("baqid", sxs.getBaqid());
                    param.put("rybh", sxs.getRybh());
                    List<ChasDhsKz> dhsKzList = chasDhsKzService.findList(param, "xgsj desc");
                    if (!dhsKzList.isEmpty()) {
                        ChasDhsKz dhs = dhsKzList.get(0);
                        dhs.setFpzt(SYSCONSTANT.DHSTZ_FP);
                        chasDhsKzService.update(dhs);
                        //ledService.refreshMjRoom(dhs.getBaqid());
                        ledService.refreshDhsLed(dhs.getBaqid(), dhs.getQyid());
                        //ledService.refreshDhsDp(dhs.getBaqid());
                    }
                    ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(sxs.getBaqid(), sxs.getRybh());
                    chasRyj.setSxsBh(null);
                    chasRyjlService.update(chasRyj);
                }
                sxsKzList.stream().forEach(sxskz->{
                    sxskz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                    sxskz.setJxyd("2");//结束继续用电
                    sxskz.setFpzt(SYSCONSTANT.SXSZT_KX);
                    sxskz.setDyzt(SYSCONSTANT.SXSZT_KX);
                    sxsKzService.update(sxskz);
                });
                sxsGbjlService.saveOrUpdate(sxs);
                //关闭电源
                jdqService.openOrColseXxs(sxs.getBaqid(), sxs.getQyid(), SYSCONSTANT.OFF);
                // 刷新LED
                ledService.refreshSxsLedBySxskz(sxs.getBaqid(), sxs.getQyid(),sxs.getId());
                //刷新电子水牌
                dzspService.sendIdleInfo(sxs.getBaqid(), sxs.getQyid());
                //刷新民警休息室
                ledService.refreshMjRoom(sxs.getBaqid());
                //刷新看守区led审讯室使用情况
                if(StringUtils.isNotEmpty(sxs.getRybh())){
                    //查询审讯室使用记录，以便更新led
                    String content = getKsqLedText(sxs.getBaqid());
                    log.debug("关闭审讯室 刷新看守区大小屏:"+content);
                    //刷新看守区大小屏
                    ledService.refreshSxsDxp(sxs.getBaqid(),content);
                }

            }
            // 审讯室结束，对存在的进出记录进行离区
            qysljcjlService.leaveRecordsBasedOnZoneId(qyid);
            result.put("success", true);
            result.put("msg", "关闭成功!");
        } catch (Exception e) {
            log.error("close:",e);
            result.put("success", false);
            result.put("msg", "系统异常:"+e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public Map<String, Object> open(String qyid) {
        Map<String, Object> result = new HashMap<>();
        try {
            int findcount = sxsKzService.findcountByQyid(qyid);
            if(findcount>0){
                result.put("success", false);
                result.put("msg", "该审讯室正在使用!");
            }
            SessionUser user = WebContext.getSessionUser();
            ChasXtQy qy = qyService.findByYsid(qyid);
            ChasBaq baq = chasBaqService.findById(qy.getBaqid());
            ChasSxsKz chasSxsKz = new ChasSxsKz();
            chasSxsKz.setId(StringUtils.getGuid32());
            if(user!=null){
                chasSxsKz.setSymj(user.getName());
            }
            chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_SY);
            chasSxsKz.setDyzt(SYSCONSTANT.SXSZT_SY);
            chasSxsKz.setBaqid(baq.getId());
            chasSxsKz.setQyid(qy.getYsid());
            chasSxsKz.setBaqmc(baq.getBaqmc());
            chasSxsKz.setKssj(new Date());
            chasSxsKz.setLrsj(new Date());
            chasSxsKz.setQymc(qy.getQymc());
            sxsKzService.save(chasSxsKz);
            jdqService.openOrColseXxs(baq.getId(), qyid, 0);
            ledService.refreshSxsLedBySxskz(baq.getId(), qyid, chasSxsKz.getId());
            result.put("success", true);
            result.put("msg", "打开成功!");
        } catch (Exception e) {
            log.error("open:" , e);
            result.put("success", false);
            result.put("msg", "系统异常:" + e.getMessage());
        }
        return result;
    }

    //分配未成年审讯室
    private String fpWcnSxs(String baqid,ChasBaqryxx ryxx){
        if (chasBaqryxxService.isWcn(ryxx)) {
            Map<String,Object> map = new HashMap<>();
            map.put("baqid", baqid);
            map.put("fjlx", SYSCONSTANT.FJLX_SXS);
            map.put("kzlx", SYSCONSTANT.SXSLX_WCNXWS);
            map.put("isdel", SYSCONSTANT.N_I);
            map.put("sxsk", SYSCONSTANT.N);
            List<ChasXtQy> wcnxws = qyService.findByParams(map);
            log.debug(String.format("办案区:%s 未成年审讯室数量:%d", baqid,wcnxws.size()));
            if (wcnxws != null&&!wcnxws.isEmpty()) {
                Collections.shuffle(wcnxws);
                return wcnxws.get(0).getYsid();
            }
        }
        return null;
    }
    private String fpSxsByLx(String baqid,String lx){

        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_SXS);
        map.put("kzlx", lx);
        map.put("isdel", SYSCONSTANT.N_I);
        map.put("sxsk", SYSCONSTANT.N);
        List<ChasXtQy> wcnxws = qyService.findByParams(map);
        log.debug(String.format("办案区:%s 审讯室数量:%d", baqid,wcnxws.size()));
        if (wcnxws != null&&!wcnxws.isEmpty()) {
            Collections.shuffle(wcnxws);
            return wcnxws.get(0).getYsid();
        }

        return null;
    }

    /**
     * 自动分配审讯室
     * 1、如果是未成年人则分配到未成年人审讯室
     * 2、根据人员类型分配审讯室
     * 3、
     * @param baqid
     * @param chasSxsKz
     * @param ryxx
     */
    private String autoFp(String baqid, ChasSxsKz chasSxsKz, ChasBaqryxx ryxx){
        String qyid = null;
        String msg = null;
        // 优先分配未成年审讯室
        String tsqt = "";
        if(StringUtils.equals(ryxx.getTsqt(),"99") || StringUtils.isEmpty(ryxx.getTsqt())){
            tsqt = "0";
        }else{
            tsqt = "1";
        }
        String kzlx = SYSCONSTANT.SXSLX_XWS2;

        //根据是否特殊群体分配未成年审讯室
        qyid = fpWcnSxs(baqid,ryxx);
        if(!StringUtils.isEmpty(ryxx.getTsqt())&&!StringUtils.equals(ryxx.getTsqt(),"99")){
            kzlx = SYSCONSTANT.SXSLX_TSXWS;//特殊群体分配到特殊审讯室
            if (qyid == null) {
                qyid = fpSxsByLx(baqid,kzlx);
            }
        }
        // 普通分配 1讯 2 询 5讯/询
        kzlx = SYSCONSTANT.SXSLX_XWS2;
        // 表示犯罪嫌疑人，分配到讯问室。其他类型人员分配到询问室。
        if (SYSCONSTANT.RYLX_FZXYR.equals(ryxx.getRylx())
                || SYSCONSTANT.DAFS_JXPW.equals(ryxx.getDafs())) {
            kzlx = SYSCONSTANT.SXSLX_XWS;
        }

        // 根据人员类型分配
        if (qyid == null) {
            qyid = fpSxsByLx(baqid,kzlx);
        }
        // 根据人员类型分讯/询问室
        if (qyid == null) {
            qyid = fpSxsByLx(baqid, SYSCONSTANT.SXSLX_XXWS);
        }
        // 人员类型分配
        if (qyid == null) {
            kzlx = SYSCONSTANT.SXSLX_XWS.equals(kzlx) ? SYSCONSTANT.SXSLX_XWS2
                    : SYSCONSTANT.SXSLX_XWS;
            qyid = fpSxsByLx(baqid,kzlx);
        }
        // 两种普通类型满或无 补充分配 未成年审讯室
        if (qyid == null) {
            qyid = fpSxsByLx(baqid, SYSCONSTANT.SXSLX_WCNXWS);
        }
        //如果经过以上一系列算法，任无法获取区域ID，那么则获取空闲审讯室ID，并返回。
        if(StringUtils.isEmpty(qyid)){
            List<ChasXtQy> qyList = qyService.findfreeSxs(baqid);
            if(!qyList.isEmpty()){
                qyid = qyList.get(0).getYsid();
            }else{
                return null;
            }
        }
        chasSxsKz.setQyid(qyid);
        msg = String.format("办案区:%s 人员:%s 自动分配审讯室:%s", baqid,ryxx.getRyxm(), qyid);
        log.debug(msg);
        return qyid;
    }
    //保存审讯室分配记录和人员等候室状态，并打开电源刷新LED
    private DevResult saveSxsState(String baqid, ChasSxsKz chasSxsKz, ChasRyjl chasRyj, ChasBaqryxx ryxx,String useSxsMjxm,Map<String,Object> user){
        if(StringUtils.isNotEmpty(chasSxsKz.getQyid())){
            Map<String, Object> params=new HashMap<>();
            params.put("qyid",chasSxsKz.getQyid());
            List<ChasSxsKz> list = sxsKzService.findList(params, "");
            if(list.size()>0){
                ChasSxsKz sxsKz = list.get(0);
                sxsKz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                sxsKz.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():(String) user.get("idCard"));
                sxsKzService.update(sxsKz);
                ChasRyjl rygj = chasRyjlService.findRyjlByRybh(baqid, sxsKz.getRybh());
                rygj.setSxsBh(null);
                rygj.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():(String) user.get("idCard"));
                chasRyjlService.update(chasRyj);
                log.info("人员编号:"+chasSxsKz.getRybh()+"审讯结束");
            }
        }
        DevResult wr = new DevResult();
        chasSxsKz.setId(StringUtils.getGuid32());
        chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_SY);
        chasSxsKz.setDyzt(SYSCONSTANT.SXSZT_SY);
        chasSxsKz.setBaqid(baqid);
        chasSxsKz.setLrrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():(String) user.get("idCard"));
        chasSxsKz.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():(String) user.get("idCard"));
        chasSxsKz.setBaqmc(chasRyj.getBaqmc());
        chasSxsKz.setRyid(chasRyj.getId());
        chasSxsKz.setRybh(chasRyj.getRybh());
        chasSxsKz.setLrsj(new Date());
        chasSxsKz.setKssj(new Date());
        chasSxsKz.setRyxm(chasRyj.getXm());
        ChasXtQy qy = qyService.findByYsid(chasSxsKz.getQyid());
        if(qy!=null){
            chasSxsKz.setQymc(qy.getQymc());
        }
        //刷脸分配审讯室不一定是嫌疑人的主办民警，如果是刷脸进行分配审讯室的话，该接口会传useSxsMjxm(实际刷脸分配审讯室的民警)
        if(StringUtils.isEmpty(useSxsMjxm)){
            String mjsfzh = ryxx.getMjSfzh();
            if (StringUtils.isNotEmpty(mjsfzh)) {
                // 根据民警身份证号查找姓名
                Map<String,Object> map = new HashMap<>();
                map.put("idCard", mjsfzh);
                List<JdoneSysUser> userlist = userService.findList(map, null);
                if (!userlist.isEmpty())
                    chasSxsKz.setSymj(userlist.get(0).getName());
            }
        }else {
            chasSxsKz.setSymj(useSxsMjxm);
        }

        // User
        // 分配审讯室后 等候室暂离
        ChasDhsKz chasDhsKz = chasDhsKzService.findById(chasRyj.getDhsBh());
        if(chasDhsKz != null){
            chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_ZL);
            chasDhsKzService.update(chasDhsKz);
        }

        sxsKzService.save(chasSxsKz);
        chasRyj.setSxsBh(chasSxsKz.getId());
        chasRyj.setXgrSfzh(ryxx.getMjSfzh());
        chasRyjlService.update(chasRyj);
        // 保存后开启继电器（除了智能墙体） 刷新LED
        if(chasDhsKz != null){
            wr.add("分配审讯室-刷新等候室", ledService.refreshDhsLed(baqid, chasDhsKz.getQyid()));
        }
        ChasBaq baq = chasBaqService.findById(baqid);

        String content = chasSxsKz.getRyxm()+"分配到"+qy.getQymc();
        wr.add("分配审讯室-刷新大屏", ledService.refreshDhsDp(baqid));
        wr.add("分配审讯室-刷新小屏", ledService.refreshSxsDxp(baqid,content));
        wr.add("分配审讯室-刷新休息屏", ledService.refreshMjRoom(baqid));
        wr.add("分配审讯室-打开继电器",jdqService.openOrColseXxs(baqid,chasSxsKz.getQyid(), SYSCONSTANT.ON));
        DevResult led = ledService.refreshSxsLedBySxskz(baqid, chasSxsKz.getQyid(), chasSxsKz.getId());
        Map<String,String> retData= (Map<String,String>)led.get("retData");

        //设置电子水牌为待审讯状态
        dzspService.sendTrialInfo(chasSxsKz);
        //更新审讯大屏设备（电视机）
        ChasSxdp sxdp = new ChasSxdp();
        sxdp.setSxsmc(qy.getQymc());
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(chasSxsKz.getRybh());
        sxdp.setSxmjxm(baqryxx.getMjXm());
        sxdp.setXyr(chasSxsKz.getRybh());
        sxdpService.sxStart(sxdp);
        //发起审讯监护任务
        YybbSendParam sendParam = new YybbSendParam();
        sendParam.setBbhj(YyhjEnue.FPSXSH);
        sendParam.setBaqid(chasSxsKz.getBaqid());
        sendParam.setRyxm(ryxx.getRyxm());
        sendParam.setSxsmc(qy.getQymc());
        YybbUtil.sendYybb(sendParam);
        startSXjhrw(chasRyj,chasSxsKz);
        String msg = String.format("办案区:%s 人员:%s 分配审讯室完成", baq.getBaqmc(), chasSxsKz.getRyxm());
        log.debug(msg);
        wr.setCodeMessage(1, msg);
        //发送消息给yjjd
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> sendMap = new HashMap<String, Object>();
        sendMap.put("ajbh", baqryxx.getAjbh());
        sendMap.put("jqbh", baqryxx.getJqbh());
        sendMap.put("ryxm", baqryxx.getRyxm());
        sendMap.put("rybh", baqryxx.getRybh());
        sendMap.put("rysfzh", baqryxx.getRysfzh());
        sendMap.put("czlx", "02");
        sendMap.put("fpsj", format.format(new Date()));
        MessageProduceCenter.send(this, "baqryfp", sendMap, "zfgl-yjjd");
        if(retData != null){
            wr.add("retData", retData);
        }
        wr.setReason("分配审讯室成功");
        wr.add("qy", qy);
        wr.add("ryxx", ryxx);
        return wr;
    }
    @Override
    public DevResult sxsFp(String baqid, String rybh, String qyid,String useSxsMjxm, HttpServletRequest request) {
        Map<String, Object> user=null;
        if(Objects.nonNull(request)){
            Enumeration<String> headers = request.getHeaders("X-Token");
            if(Objects.nonNull(headers)){
                JdoneSysUserLoginTokenService jdoneSysUserLoginTokenService = ServiceContext.getServiceByClass(JdoneSysUserLoginTokenService.class);
                user = jdoneSysUserLoginTokenService.tokenVerify("", headers.nextElement());
            }
        }

        String msg;
        DevResult wr = new DevResult();
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        // 已经分配过 检查分配状态 检查
        ChasBaqryxx ryxx =  chasBaqryxxService.findByRybh(rybh);
        if (ryxx == null) {
            msg = String.format("办案区%s 人员信息列表未找到编号%s", baqid, rybh);
            log.error(msg);
            wr = DevResult.error(msg);
            wr.setReason("无法找到人员信息，不能分配审讯室");
            return wr;
        }
        String sxsbh = chasRyj.getSxsBh();
        if (StringUtils.isNotEmpty(sxsbh)) {
            ChasSxsKz chasSxsKz = sxsKzService.findAllById(sxsbh);
            if (chasSxsKz != null) {
                //验证笔录是否完成
                if(StringUtils.isEmpty(qyid)&&chasSxsKz.getHdsj() == null){
                    msg = String.format("办案区:%s 人员:%s  编号:%s 审讯室分配,笔录未完成不予分配",
                            chasRyj.getBaqmc(), chasRyj.getXm(), chasRyj.getRybh());
                    wr.setCodeMessage(0, msg);
                    wr.setReason("笔录未完成不予分配");
                    return wr;
                }
                // 分配标记逻辑删除
                chasSxsKz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                chasSxsKz.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():(String) user.get("idCard"));
                sxsKzService.update(chasSxsKz);
                // 关闭继电器
                wr.add("关闭审讯室-继电器",jdqService.openOrColseXxs(baqid, chasSxsKz.getQyid(), SYSCONSTANT.OFF));
                // 刷新屏幕
                wr.add("关闭审讯室-刷新屏幕",ledService.refreshSxsLedBySxskz(baqid,chasSxsKz.getQyid(), sxsbh));
                log.debug(String.format("办案区:%s 人员:%s 分配解除 :%s", baqid,
                        ryxx.getRyxm(), sxsbh));
                //将电子水牌变为空闲
                dzspService.sendIdleInfo(chasSxsKz.getBaqid(), chasSxsKz.getQyid());
            }
        }
        log.debug(String.format("办案区:%s 人员:%s 开始分配审讯室", baqid, ryxx.getRyxm()));
        // 分配
        ChasSxsKz chasSxsKz = new ChasSxsKz();

        // 手动分配
        if (StringUtils.isNotEmpty(qyid)) {
            chasSxsKz.setQyid(qyid);
            msg = String.format("办案区:%s 人员:%s 手动分配审讯室:%s", baqid,ryxx.getRyxm(), qyid);
            log.debug(msg);
        } else { // 自动分配
            String fpqyid= autoFp(baqid, chasSxsKz, ryxx);
            if(StringUtils.isEmpty(fpqyid)){
                msg = String.format("办案区:%s 人员:%s 自动分配审讯室:%s 无审讯室空闲，不进行分配", baqid,ryxx.getRyxm(), qyid);
                wr.setCodeMessage(0, msg);
                wr.setReason("无审讯室空闲，不进行分配");
                return wr;
            }
        }
        return saveSxsState(baqid,chasSxsKz,chasRyj,ryxx,useSxsMjxm,user);
    }



    @Override
    public DevResult sxsGx(String baqid, String sxsbh) {
        ChasSxsKz chasSxsKz = sxsKzService.findAllById(sxsbh);
        if (chasSxsKz == null) {
            log.error("根据审讯室编号 未找到分配记录");
            return DevResult.error("根据审讯室编号 未找到分配记录");
        }
        DevResult wr = new DevResult();
        ledService.refreshSxsLedBySxskz(baqid, chasSxsKz.getQyid(),chasSxsKz.getId());
        ledService.refreshMjRoom(baqid);
        wr.setCodeMessage(1, "更新成功");
        return wr;
    }

    @Override
    public DevResult sxsGxXx(String baqid, String rybh) {
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if (StringUtils.isNotEmpty(chasRyj.getSxsBh())) {
            ChasSxsKz chasSxsKz = sxsKzService.findAllById(chasRyj
                    .getSxsBh());
            if (chasSxsKz == null) {
                log.error("根据审讯室编号 未找到分配记录");
                return DevResult.error("根据审讯室编号 未找到分配记录");
            }
            if (!chasRyj.getXm().equals(chasSxsKz.getRyxm())) {
                chasSxsKz.setRyxm(chasRyj.getXm());
                sxsKzService.update(chasSxsKz);
                return sxsGx(baqid, chasRyj.getSxsBh());
            } else {
                return DevResult.success("无变更项");
            }
        }
        return DevResult.error("未分配审讯室");
    }

    @Override
    public DevResult sxsJc(String baqid, String rybh, boolean dxled) {
        // 审讯室解除
        log.debug(String.format("办案区：%s 人员编号:%s 审讯室解除  是否刷新大小屏:%s", baqid,
                rybh, dxled));
        DevResult wr = new DevResult();
        String msg;
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if(chasRyj == null){
            return DevResult.error(rybh+"找不到人员信息");
        }
        String sxsbh = chasRyj.getSxsBh();
        if (StringUtils.isNotEmpty(sxsbh)) {
            // 解除审讯室分配
            ChasSxsKz chasSxsKz = sxsKzService.findAllById(sxsbh);
            ChasBaqryxx chasBaqryxx = chasBaqryxxService.findByRybh(rybh);

            if (chasSxsKz == null) {
                log.error("根据审讯室编号 未找到分配记录");
                return DevResult.error("根据审讯室编号 未找到分配记录");
            }

            // 笔录结束定时检查和定位触发事件共存
            if (SYSCONSTANT.SXSZT_KX.equals(chasSxsKz.getFpzt()) ) {
                // 已解除不再触发事件
                msg = String.format("办案区:%s 人员:%s  编号:%s 已解除不再触发事件",
                        chasRyj.getBaqmc(), chasRyj.getXm(), chasRyj.getRybh());
                wr.setCode(0);
                wr.setMessage(msg);
                log.debug(msg);
                return wr;
            } else {
                chasSxsKz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_TY);
                chasSxsKz.setDyzt(SYSCONSTANT.SXSZT_TY);
                chasSxsKz.setXgrSfzh(chasBaqryxx.getMjSfzh());
                sxsKzService.update(chasSxsKz);
                //解除人员记录表中审讯室id
                chasRyj.setSxsBh(null);
                chasRyj.setXgrSfzh(chasBaqryxx.getMjSfzh());
                chasRyjlService.update(chasRyj);
                // 刷新LED
                wr.add("解除审讯室 刷新审讯屏",ledService.refreshSxsLedBySxskz(baqid, chasSxsKz.getQyid(),chasSxsKz.getId()));
                wr.add("解除审讯室 刷新电子水牌",dzspService.refresh(baqid, chasSxsKz.getQyid(),rybh));
                if (dxled) {
                    String content = getKsqLedText(baqid);
                    log.debug("解除审讯室 刷新大小屏:"+content);
                    wr.add("解除审讯室 刷新大小屏", ledService.refreshSxsDxp(baqid,content));
                }
                wr.add("解除审讯室 刷新休息屏", ledService.refreshMjRoom(baqid));
            }
            msg = "解除分配成功";
            log.debug(String.format("办案区：%s 人员编号:%s 解除分配成功  是否刷新大小屏:%s",
                    baqid, rybh, dxled));
            wr.setCodeMessage(1, msg);
        } else {
            msg = "未分配审讯室 解除分配失败";
            log.debug(String.format("办案区：%s 人员编号:%s 未分配审讯室 解除分配失败 是否刷新大小屏:%s",
                    baqid, rybh, dxled));
            wr.setCodeMessage(0, msg);
        }
        return wr;
    }

    // 离开审讯室 验证笔录，人员定位事件中调用
    @Override
    public DevResult sxsLkYzBl(String baqid, String rybh) {
        log.debug(String.format("办案区：%s 人员编号:%s  离开审讯室(解除)", baqid, rybh));
        DevResult wr = new DevResult();
        String msg;
        //人员记录
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        String sxsbh = chasRyj.getSxsBh();

        if (StringUtils.isNotEmpty(sxsbh)) {
            // 离开审讯室
            ChasSxsKz chasSxsKz = sxsKzService.findAllById(sxsbh);
            if (chasSxsKz == null) {
                log.error("根据审讯室编号 未找到分配记录");
                return DevResult.error("根据审讯室编号 未找到分配记录");
            }
            // 检查笔录完成
            if (chasSxsKz.getHdsj() == null) {
                msg = String.format("办案区:%s 人员:%s  编号:%s 离开审讯室(解除) 笔录未完成",
                        chasRyj.getBaqmc(), chasRyj.getXm(), chasRyj.getRybh());
                log.debug(msg);
                wr.setCodeMessage(1, msg);
                return wr;
            }
            if (!"1".equals(chasSxsKz.getJxyd())) {
                wr.add("离开审讯室解除", sxsJc(baqid, rybh, SYSCONSTANT.Y_B));
            }else{
                ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
                //继续用电的情况
                chasSxsKz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                chasSxsKz.setXgrSfzh(baqryxx.getMjSfzh());
                sxsKzService.update(chasSxsKz);
                //解除人员记录表中审讯室id
                chasRyj.setSxsBh(null);
                chasRyj.setXgrSfzh(baqryxx.getMjSfzh());
                chasRyjlService.update(chasRyj);
                // 刷新LED
                wr.add("解除审讯室 刷新审讯屏",ledService.refreshSxsLedBySxskz(baqid, chasSxsKz.getQyid(),chasSxsKz.getId()));
                String content = getKsqLedText(baqid);
                //String content = getKsqLedText(baqid);
                log.debug("解除审讯室 继续用电 刷新大小屏:"+content);
                wr.add("解除审讯室 刷新大小屏", ledService.refreshSxsDxp(baqid,content));
                wr.add("解除审讯室 刷新休息屏", ledService.refreshMjRoom(baqid));
            }
            //人员离开审讯室后，将电子水牌变为空闲
            dzspService.sendIdleInfo(baqid, chasSxsKz.getQyid());
            msg = "审讯室 离开成功";
            log.debug(String.format("办案区：%s 人员编号:%s 审讯室 离开(解除)成功", baqid, rybh));
            wr.setCodeMessage(1, msg);
        } else {
            msg = "未查到分配的审讯室 离开失败";
            log.debug(String.format("办案区：%s 人员编号:%s 未查到分配的审讯室 离开(解除)失败",
                    baqid, rybh));
            wr.setCodeMessage(0, msg);
        }
        log.debug(msg);
        return wr;
    }

    @Override
    public DevResult sxsLk(String baqid, String rybh) {
        // 离开审讯室
        log.debug(String.format("办案区：%s 人员编号:%s  离开审讯室(解除)", baqid, rybh));
        DevResult wr = new DevResult();
        String msg;
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        String sxsbh = chasRyj.getSxsBh();
        if (StringUtils.isNotEmpty(sxsbh)) {
            // 离开审讯室分配
            ChasSxsKz chasSxsKz = sxsKzService.findAllById(sxsbh);
            if (!SYSCONSTANT.SXSZT_JX.equals(chasSxsKz.getJxyd())
                    && chasSxsKz.getHdsj() == null) {
                wr.add("离开审讯室 解除", sxsJc(baqid, rybh, SYSCONSTANT.Y_B));
            }
            msg = "审讯室 离开成功";
            log.debug(String.format("办案区：%s 人员编号:%s 审讯室 离开(解除)成功", baqid, rybh));
            wr.setCodeMessage(1, msg);
        } else {
            msg = "未查到分配的审讯室 离开失败";
            log.debug(String.format("办案区：%s 人员编号:%s 未查到分配的审讯室 离开(解除)失败",
                    baqid, rybh));
            wr.setCodeMessage(0, msg);
        }
        log.debug(msg);
        return wr;
    }






    @Override
    public DevResult startSXjhrw(ChasRyjl ryjl,ChasSxsKz sxsKz) {
        ChasBaq baq = chasBaqService.findById(ryjl.getBaqid());
        BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(baq.getId());
        if(baqConfiguration==null||!baqConfiguration.getJhrwSxjh()){
            return DevResult.error(baq.getBaqmc()+"没有启用审讯戒护任务");
        }
        String sqymc = "看守区",eqymc="审讯区";
        if(StringUtils.isNotEmpty(ryjl.getDhsBh())){
            ChasDhsKz dhsKz = chasDhsKzService.findById(ryjl.getDhsBh());
            if(dhsKz!=null){
                ChasXtQy qy = qyService.findByYsid(dhsKz.getQyid());
                if(qy!=null){
                    sqymc = qy.getQymc();
                }
            }
        }
        if(sxsKz!=null&&StringUtils.isNotEmpty(sxsKz.getQyid())){
            ChasXtQy qy = qyService.findByYsid(sxsKz.getQyid());
            if(qy!=null){
                eqymc = qy.getQymc();
            }
        }
        SessionUser sessionUser = WebContext.getSessionUser();
        String name=sessionUser==null?"":sessionUser.getName();
        ChasBaqryxx ryxx = chasBaqryxxService.findByRybh(ryjl.getRybh());
        //return null;
        return jhrwService.saveJhrw(name,baq.getDwdm(), ryxx, SYSCONSTANT.JHRWLX_SXJH, sqymc, eqymc);
    }

    @Override
    public DevResult endSXjhrw(ChasRyjl ryjl) {
        ChasBaq baq = chasBaqService.findById(ryjl.getBaqid());
        BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(baq.getId());
        if(baqConfiguration==null||!baqConfiguration.getJhrwSxjh()){
            return DevResult.error(baq.getBaqmc()+"没有启用审讯戒护任务");
        }
        ChasBaqryxx ryxx = chasBaqryxxService.findByRybh(ryjl.getRybh());
        //return null;
        return jhrwService.changeJhrwZt("审讯民警",baq.getDwdm(), ryxx,SYSCONSTANT.JHRWLX_SXJH ,SYSCONSTANT.JHRWZT_YZX );
    }

    public String getKsqLedText(String baqid){
        ChasSxsKz sxskz = sxsKzService.findTheLastFpjl(baqid);
        if(sxskz==null){
            return "暂无人员分配到审讯室";
        }else{
            return String.format("%s分配到%s",sxskz.getRyxm(),sxskz.getQymc());
        }
    }

    @Override
    public Map<String, Object> closePfs(String qyid) {
        Map<String,Object> result = new HashMap<>();
        try {
            if(StringUtils.isEmpty(qyid)){
                result.put("success", false);
                result.put("msg", "主键ID不能为空!");
                return result;
            }
            ChasXtQy qy = qyService.findByYsid(qyid);
            if(qy==null){
                result.put("success", false);
                result.put("msg", "找不到审讯室信息");
                return result;
            }
            DevResult devResult = jdqService.openOrColsePfs(qy.getBaqid(), qy.getYsid(), SYSCONSTANT.OFF);
            if(devResult.getCode()==1){
                result.put("success", true);
                result.put("msg", "排风扇关闭成功!");
            }else{
                result.put("success", false);
                result.put("msg", devResult.getMessage());
            }
        } catch (Exception e) {
            log.error("closePfs:",e);
            result.put("success", false);
            result.put("msg", "系统异常:"+e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public Map<String, Object> openPfs(String qyid) {
        Map<String,Object> result = new HashMap<>();
        try {

            if(StringUtils.isEmpty(qyid)){
                result.put("success", false);
                result.put("msg", "主键ID不能为空!");
                return result;
            }
            ChasXtQy qy = qyService.findByYsid(qyid);
            if(qy==null){
                result.put("success", false);
                result.put("msg", "找不到审讯室信息");
                return result;
            }
            DevResult devResult = jdqService.openOrColsePfs(qy.getBaqid(), qy.getYsid(), SYSCONSTANT.ON);
            if(devResult.getCode()==1){
                result.put("success", true);
                result.put("msg", "排风扇打开成功!");
            }else{
                result.put("success", false);
                result.put("msg", devResult.getMessage());
            }

        } catch (Exception e) {
            log.error("openPfs:",e);
            result.put("success", false);
            result.put("msg", "系统异常:"+e.getMessage());
            throw e;
        }
        return result;
    }
}
