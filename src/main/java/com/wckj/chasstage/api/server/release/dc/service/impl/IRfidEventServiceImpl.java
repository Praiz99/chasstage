package com.wckj.chasstage.api.server.release.dc.service.impl;


import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.service.IRfidEventService;
import com.wckj.chasstage.common.util.MemUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.shsng.entity.ChasSng;
import com.wckj.chasstage.modules.shsng.service.ChasShsngService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
public class IRfidEventServiceImpl implements IRfidEventService {
    private static final Logger log = Logger.getLogger(IRfidEventServiceImpl.class);
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ChasSxsKzService chasSxsKzService;
    @Autowired
    private ChasSxsglService chasSxsglService;
    @Autowired
    private ChasQqyzService chasQqyzService;
    @Autowired
    private ChasDhsKzService dhsKzService;
    @Autowired
    private ChasDhsglService dhsglService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasShsngService shsngService;
    @Autowired
    private ChasYjxxService yjxxService;
    @Autowired
    private ChasYjlbService chasYjlbService;
    @Autowired
    private ChasBaqService baqService;
    @Override
    public DevResult cardScan(String org, String card, String sbgn) {
        DevResult wr = new DevResult();
        String key = MessageFormat.format("{0}&{1}&{2}", org, card,sbgn);
        Long lastTrigerTime = (Long) MemUtil.get(key);
        if (lastTrigerTime != null) {
            long interval = (System.currentTimeMillis() - lastTrigerTime.longValue()) / 1000;
            if (interval < 60) {
                log.warn(String.format("一分钟内重复刷卡无效：%s", key));
                wr.setCode(SYSCONSTANT.N_I);
                wr.setMessage("一分钟内重复刷卡无效");
                return wr;
            }
        }
        //防止在处理时间内重新请求，而产生并发问题
        MemUtil.put(key, System.currentTimeMillis(), 60 * 1000);
        //检查手环
        if (SYSCONSTANT.SBGN_FPSXS.equals(sbgn)) {//刷卡分配审讯室
            wr = scanForInterrogate(org, card);
        } else if (SYSCONSTANT.SBGN_QQDH.equals(sbgn)) {//刷卡打亲情电话
            wr = scanForPhone(org, card);
        }else if (SYSCONSTANT.SBGN_KSQDKQ.equals(sbgn)) {//刷卡看守区读卡器
            wr = scanForKsq(org, card,sbgn);
        }else if (SYSCONSTANT.SBGN_WCNKSQDKQ.equals(sbgn)) {//刷卡未成年看守区读卡器
            wr = scanForKsq(org, card,sbgn);
        }else if (SYSCONSTANT.SBGN_GHSNG.equals(sbgn)){//手环胸卡归还收纳柜
            wr = scanForGhsng(org, card);
        }
        //更新过期时间
        MemUtil.put(key, System.currentTimeMillis(), 60 * 1000);
        return wr;
    }


    public synchronized DevResult  scanForInterrogate(String baqid, String watchNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("baqid", baqid);
        map.put("wdbh_l", watchNo);
        map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        ChasRyjl chasRyjl = chasRyjlService.findByParams(map);
        log.info("看守区刷卡，查出人员分配的审讯室编号:"+chasRyjl.getSxsBh());
        if(StringUtil.isNotEmpty(chasRyjl.getSxsBh())){
            ChasSxsKz sxsKz = chasSxsKzService.findAllById(chasRyjl.getSxsBh());
            log.info("看守区刷卡，查出的审讯室:"+sxsKz.getQymc());
            if(sxsKz!=null){
                log.info("qymc:"+sxsKz.getQymc()+",isdel:"+sxsKz.getIsdel()+",hdsj:"+sxsKz.getHdsj());
                if(sxsKz.getIsdel()==1&&sxsKz.getHdsj()!=null){//已经分配审讯室，按照人员回流逻辑处理
                	log.info("人员刷卡回流，人员姓名:"+sxsKz.getRyxm());
                    //强制关闭审讯室
                    chasSxsglService.close(sxsKz.getQyid());
                    //chasSxsKzService.sxsLkYzBl(baqid, chasRyjl.getRybh());
                    dhsglService.dhsFp(baqid, chasRyjl.getRybh(), null);
                    DevResult result= DevResult.success();
                    result.setMessage("已经分配审讯室,人员回流到等候室");
                    return result;

                }
            	
            }
        }
        if (chasRyjl != null) {
            DevResult sxsFp = chasSxsglService.sxsFp(baqid, chasRyjl.getRybh(), null,null,null);
            Map<String,String> retData= (Map<String,String>)sxsFp.get("retData");
            DevResult result= DevResult.success();
            if(retData != null){
                result.add("symj", retData.get("symj"));
                result.add("sxsmc", retData.get("sxsmc"));
                result.add("xyrxm", retData.get("xyrxm"));
                result.add("kssj", retData.get("kssj"));
                if(retData.containsKey("rylx")){
                    result.add("rylx", retData.get("rylx"));
                }else{
                    result.add("rylx", "");
                }

                result.setCode(1);
            }else{
                result.setCode(0);
            }
            result.setMessage(sxsFp.getMessage());

            //结束人员戒护任务
            //ChasBaqryxx baqryxx = baqryxxService.findByRybh(chasRyjl.getRybh());
            //jhrwService.changeJhrwZt(baqryxx.getZbdwBh(),chasRyjl.getRybh(),SYSCONSTANT.JHRWLX_RQJH,SYSCONSTANT.JHRWZT_YZX);
            return result;
        } else {
            String msg = String.format("办案区%s 人员记录表找不到腕带号%s ", baqid, watchNo);
            log.error(msg);
            return DevResult.error(msg);
        }
    }

    /**
     * 刷卡打电话
     *
     * @param watchNo
     * 手环ID 状态：01 申请 02 审批中 03 审批通过 04 审批不通过 05 使用中 06结束';
     */
    public DevResult scanForPhone(String org, String watchNo) {
        log.debug(String.format("办案区%s 刷卡打电话手环%s", org, watchNo));
        return chasQqyzService.openQqdh(org, null, watchNo);
    }

    //手环胸卡归还收纳柜
    public synchronized DevResult  scanForGhsng(String baqid, String watchNo) {
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("baqid", baqid);
//        map.put("wdbh_l", watchNo);
        map.put("sblx", "6");
        map.put("wdbhL", watchNo);
        try {
            ChasSng chassng = shsngService.findeByWdbhL(watchNo);
            if (chassng == null) {
                List<ChasSb> chasSbs = sbService.findByParams(map);
                for (ChasSb sb : chasSbs) {
                    ChasSng sng = new ChasSng();
                    sng.setId(StringUtils.getGuid32());
                    sng.setXgsj(new Date());
                    sng.setLrsj(new Date());
                    sng.setIsdel((short) 0);
                    sng.setDataFlag("0");
                    sng.setZgqk("1");
                    sng.setShbh(sb.getSbbh());
                    sng.setBaqid(sb.getBaqid());
                    sng.setBaqmc(sb.getBaqmc());
                    sng.setKzcs1(sb.getKzcs1());
                    sng.setKzcs2(sb.getKzcs2());
                    sng.setSblx(sb.getSblx());
                    sng.setSbmc(sb.getSbmc());
                    sng.setKzcs3(sb.getKzcs3());
                    sng.setZt("2");
                    sng.setGhsj(new Date());
                    String wdbhl = sb.getKzcs2();
                    ChasBaqryxx ryxx = baqryxxService.findRyxxBywdbhL(wdbhl);
                    if (null != ryxx) {
                        sng.setFfsj(ryxx.getRRssj());
                    }
                    shsngService.save(sng);
                }
            } else {
                chassng.setXgsj(new Date());
                chassng.setGhsj(new Date());
                chassng.setZgqk("1");
                chassng.setZt("2");
                shsngService.update(chassng);
            }
            Integer count = shsngService.selectZg();
            if(count >= 50){//超出50个预警
                Map<String, Object> params = new HashMap<>();
                params.put("yjlb",SYSCONSTANT.YJLB_SNGCLYJ);
                params.put("yjzt",SYSCONSTANT.YJZT_WCL);

                List<ChasYjxx> list = yjxxService.findList(params, "");
                params.clear();
                params.put("yjlb", SYSCONSTANT.YJLB_SNGCLYJ);
                params.put("baqid", baqid);
                List<ChasYjlb> yjlbs = chasYjlbService.findList(params, null);
                if(list.size() <= 0) {
                    ChasYjxx chasYjxx = new ChasYjxx();
                    chasYjxx.setId(StringUtils.getGuid32());
                    chasYjxx.setBaqid(baqid);
                    ChasBaq baq = baqService.findById(baqid);
                    chasYjxx.setBaqmc(baq == null ? "" : baq.getBaqmc());
                    chasYjxx.setLrsj(new Date());
                    chasYjxx.setJqms("收纳柜存量已超过50个，请及时处理!");
                    chasYjxx.setYjjb(yjlbs.get(0).getYjjb());
                    chasYjxx.setYjlb(SYSCONSTANT.YJLB_SNGCLYJ);
                    chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
                    chasYjxx.setCfsj(new Date());
                    chasYjxx.setCfrid("");
                    chasYjxx.setCfrxm("");
                    yjxxService.save(chasYjxx);
                }
            }

            DevResult result = DevResult.success();
            result.setMessage("归还成功！");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return DevResult.error(e.getMessage());
        }
    }

    /**
     * 看守区读卡器
     * @param baqid 办案区id
     * @param watchNo 低频编号
     * @param sbgn 设备功能 刷（未成年）看守区读卡器
     * @return
     */
    public DevResult scanForKsq(String baqid, String watchNo,String sbgn){
        log.info(String.format("处理看守区读卡事件，参数:单位[%s]设备功能[%s]读取到卡号：[%s]", baqid, sbgn,watchNo));
        /*
         * 1、根据手环编号找到 人员信息。判断（未）成年人是否与刷了正确的读卡器，
         * 如果没有，在该读卡器相应看守区大屏，提醒人员进入错误看守区；
         * 否则在该读卡器相应看守区大屏显示人员分配到XX等候室
         * 2、结束入区监护任务。
         * 3、如果人员存在分配审讯室记录，该读卡器相应看守区大屏显示人员分配到XX审讯室
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("baqid", baqid);
        map.put("wdbh_l", watchNo);
        map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        ChasRyjl chasRyjl = chasRyjlService.findByParams(map);
        if(chasRyjl==null){
            log.error("找不到人员记录信息");
            return DevResult.error("找不到人员记录信息");
        }
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(chasRyjl.getRybh());
        if(baqryxx==null){
            log.error("找不到人员信息");
            return DevResult.error("找不到人员信息");
        }
        //完成入区监护任务
        BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(baqryxx.getBaqid());
        if(baqConfiguration!=null&&baqConfiguration.getJhrwRqjh()){

            jhrwService.changeJhrwZt("看守区管理员",baqryxx.getZbdwBh(), baqryxx,SYSCONSTANT.JHRWLX_RQJH ,SYSCONSTANT.JHRWZT_YZX );
        }

        if(SYSCONSTANT.SBGN_KSQDKQ.equals(sbgn)&&
                SYSCONSTANT.TSQT_WCN.equals(baqryxx.getTsqt())){
            //未成年人刷了看守区读卡器
            String info = baqryxx.getRyxm()+"带错区域，应带到未成年区";
            log.error(info);
            List<ChasSb> sbList = getKsqDpLed(baqid, sbgn, SYSCONSTANT.SBGN_KSQDP);
            if(sbList!=null&&!sbList.isEmpty()){
                ledService.refreshKsqDp(sbList, info,60000,"暂无看守区人员");
            }
            return DevResult.success(info);
        }
        if(SYSCONSTANT.SBGN_WCNKSQDKQ.equals(sbgn)&&
                !SYSCONSTANT.TSQT_WCN.equals(baqryxx.getTsqt())){
            //成年人刷了未成年人看守区读卡器
            String info = baqryxx.getRyxm()+"带错区域，应带到成年区";
            log.error(info);
            List<ChasSb> sbList = getKsqDpLed(baqid,sbgn,SYSCONSTANT.SBGN_WCNKSQDP);
            if(sbList!=null&&!sbList.isEmpty()){
                ledService.refreshKsqDp(sbList, info,60000,"暂无看守区人员");
            }
            return DevResult.success(info);
        }
        map.clear();
        map.put("baqid", baqid);
        map.put("isdel", 0);
        map.put("rybh", chasRyjl.getRybh());
        List<ChasSxsKz> sxsKzs = chasSxsKzService.findByParams(map);
        if(sxsKzs!=null&&!sxsKzs.isEmpty()){
            //存在审讯室分配记录，在大屏上提醒人员分配到XX审讯室
            log.error("存在审讯室分配记录，在大屏上提醒人员分配到XX审讯室");
            String qyid=sxsKzs.get(0).getQyid();
            if(StringUtil.isEmpty(qyid)){
                return DevResult.error("找不到分配的审讯室区域信息");
            }
            ChasXtQy sxs = qyService.findByYsid(qyid);
            return showDpMsgByRyxx(baqid,baqryxx,sbgn,baqryxx.getRyxm()+"被分配到"+sxs.getQymc());
        }else{
            //不存在审讯室分配记录
            log.error("存在审讯室分配记录");
            String dhskzid=chasRyjl.getDhsBh();
            if(StringUtil.isEmpty(dhskzid)){
                log.error("找不到"+baqryxx.getRyxm()+"等候室分配信息");
                return DevResult.error("找不到"+baqryxx.getRyxm()+"等候室分配信息");
            }
            ChasDhsKz dhsKz = dhsKzService.findById(dhskzid);
            String qyid=dhsKz.getQyid();
            if(StringUtil.isEmpty(qyid)){
                return DevResult.error("找不到分配的等候室区域信息");
            }
            ChasXtQy dhs = qyService.findByYsid(qyid);
            return showDpMsgByRyxx(baqid,baqryxx,sbgn,baqryxx.getRyxm()+"被分配到"+dhs.getQymc());
        }
    }
    private DevResult showDpMsgByRyxx(String baqid,ChasBaqryxx baqryxx ,String sbgn,String info){
        String ledGn ;
        if(SYSCONSTANT.TSQT_WCN.equals(baqryxx.getTsqt())){
            ledGn=SYSCONSTANT.SBGN_WCNKSQDP;
        }else{
            ledGn=SYSCONSTANT.SBGN_KSQDP;
        }
        List<ChasSb> sbList = getKsqDpLed(baqid, sbgn, ledGn);
        log.error("看守区led屏数量"+sbList.size());
        if(sbList!=null&&!sbList.isEmpty()){
            ledService.refreshKsqDp(sbList, info,60000,"暂无看守区人员");
        }
        return DevResult.success(info);
    }
    //根据读卡器设备功能获取看守区大屏
    private List<ChasSb> getKsqDpLed(String baqid,String sbgn,String sbgnLed){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sbgn", sbgn);
        map.put("sblx", SYSCONSTANT.SBLX_DKQ);
        List<ChasSb> dkqList = sbService.findList(map, null);
        if(dkqList==null||dkqList.isEmpty()){
            return null;
        }
        map.put("baqid", baqid);
        map.put("sbgn", sbgnLed);
        map.put("sblx", SYSCONSTANT.SBLX_LED);

        List<ChasSb> ledList = new ArrayList<>();
        for(ChasSb sb:dkqList){
            if(StringUtil.isNotEmpty(sb.getQyid())){
                map.put("qyid", sb.getQyid());
                List<ChasSb> dpList = sbService.findList(map, null);
                if(dpList!=null&&!dpList.isEmpty()){
                    ledList.addAll(dpList);
                }
            }
        }
        return ledList;
    }
}
