package com.wckj.chasstage.api.server.release.dc.service.impl;



import com.wckj.chasstage.api.server.device.IDzspService;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.release.dc.LSConstant;
import com.wckj.chasstage.api.server.release.dc.dto.LSLocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.service.ILSAlarmEventService;
import com.wckj.chasstage.common.util.MemUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.chasstage.modules.ryxl.service.ChasYwRyxlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.chasstage.modules.xgjl.entity.ChasXgjl;
import com.wckj.chasstage.modules.xgjl.service.ChasXgjlService;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
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
public class ILSAlarmEventServiceImpl implements ILSAlarmEventService {
    private static Logger log = Logger.getLogger(ILSAlarmEventServiceImpl.class);

    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ChasXtQyService chasQyService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private ChasXgjlService chasXgjlService;
    @Autowired
    private ChasYjlbService chasYjlbService;
    @Autowired
    private ChasYjxxService chasYjxxService;
    @Autowired
    private IJdqService jdqService;
    @Autowired
    private ChasSxsKzService chasSxsKzService;
    @Autowired
    private ChasSxsglService chasSxsglService;
    @Autowired
    private ChasDhsKzService chasDhsKzService;
    @Autowired
    private ChasDhsglService chasDhsglService;
    @Autowired
    private ChasYwRyxlService chasYwRyxlService;
    @Autowired
    private IDzspService dzspService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasXgpzService chasXgpzService;
    /**
     * 处理巡更打卡事件、人员逃脱、手环没电
     * @param baqid
     * @param c
     */
    @Override
    public void dealWckjEvent(String baqid, LSLocationEventInfo c) {
        log.debug("LocationDeviceImpl dealWckjEvent baqid:"+baqid);
        String tagNo = c.getTagNo();
        int eventNo = c.getEventType();
        //String equipNo = c.getEquipNo();
        ChasBaq chasBaq=chasBaqService.findById(baqid);
        if(chasBaq==null){
            log.error(String.format("未配置办案区%s手环%s", baqid, tagNo));
            return;
        }
        log.debug("巡更打卡1/逃脱定位2:"+c.getEventType()+" tag:"+c.getTagNo());
        if (eventNo == LSConstant.EVENT_TYPE_XGDK) { //巡更打卡
            processXgdk(c,chasBaq);
        }else if (eventNo == LSConstant.EVENT_TYPE_RYTT) {//人员逃脱
            processTtdw(baqid,c);
        }else if(eventNo == LSConstant.EVENT_TYPE_XLYC){//手环心率
            dealRyLifeData(baqid, c);
        }else if(eventNo == LSConstant.EVENT_TYPE_DDL){//手环没电
            processShmd(chasBaq,c);
        }else if(eventNo == LSConstant.EVENT_TYPE_RYJJ){//人员聚集
            processRyjj(baqid,c);
        }else if(eventNo == LSConstant.EVENT_TYPE_LKSXS){//人员离开审讯室
            processRylksxs(baqid,c);
            processRyddsxsmk(baqid,c);
        }else if(eventNo == LSConstant.EVENT_TYPE_DDDHS){//人员到达等候室
            processRydddhs(baqid,c);
        }else if(eventNo == LSConstant.EVENT_TYPE_FACE){//人员刷脸
            //TODO:收到人员刷脸后，根据人员编号找到人员信息并结束出区戒护任务
            processFace(baqid,c);
        }


    }
    private void processFace(String baqid, LSLocationEventInfo c){
       log.info("开始处理人员闸机刷脸事件,参数"+baqid+",人员编号"+c.getExpandParm());
        ChasBaqryxx ryxx = baqryxxService.findByRybh(c.getExpandParm());
        if(ryxx==null){
            log.info("无法根据人员编号找到人员信息");
        }
        jhrwService.changeJhrwZt("看守区管理员",ryxx.getZbdwBh(), ryxx, SYSCONSTANT.JHRWLX_CQJH ,SYSCONSTANT.JHRWZT_YZX );
    }
    private void processRylksxs(String baqid, LSLocationEventInfo c){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("ryzt", "1");
        map.put("wdbh_h", c.getTagNo());
        ChasRyjl ryjl = chasRyjlService.findByParams(map);
        if(ryjl!=null)
        chasSxsglService.sxsLkYzBl(baqid, ryjl.getRybh());
    }
    private void processRydddhs(String baqid, LSLocationEventInfo c){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("ryzt", "1");
        map.put("wdbh_h", c.getTagNo());
        ChasRyjl ryjl = chasRyjlService.findByParams(map);
        if(ryjl!=null)
        chasDhsglService.dhsFpYzBl(baqid, ryjl.getRybh(), null);
    }
    /**
     * 到达审讯室门口，检测人员是否进入了正确的审讯室（分配的），
     * 如果是则将电子水牌变为审讯中，并结束审讯监护任务；
     * 否则，在相应的审讯室关联的电子水牌提醒走错审讯室
     */
    private void processRyddsxsmk(String baqid, LSLocationEventInfo c){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("ryzt", "1");
        map.put("wdbh_h", c.getTagNo());
        ChasRyjl ryjl = chasRyjlService.findByParams(map);
        if(ryjl==null){
            log.error("根据手环编号"+c.getTagNo()+"找不到人员记录信息");
            return;
        }
        ChasXtQy qy = chasQyService.findByYsid(c.getAreaId());
        if(qy==null){
            log.error("找不到区域信息"+c.getAreaId()+"信息");
            return;
        }
        String targetQy = null;
        if(SYSCONSTANT.FJLX_SXS.equals(qy.getFjlx())){//人员当前位置在审讯区
            String sxsid = ryjl.getSxsBh();
            ChasSxsKz sxs = null;
            if(StringUtils.isNotEmpty(sxsid)){
                sxs = chasSxsKzService.findById(sxsid);
            }else{
                //由于笔录结束人员记录表中审讯室分配记录信息已经删除，此时需要在审讯室分配记录表中查询
                map.clear();
                map.put("baqid",ryjl.getBaqid());
                map.put("rybh", ryjl.getRybh());
                List<ChasSxsKz> sxsKzList = chasSxsKzService.getSxsKzByRybh(map);
                if(sxsKzList != null&&!sxsKzList.isEmpty()){
                    sxs = sxsKzList.get(0);
                }
            }
            if(sxs != null&&sxs.getHdsj()!=null){
                log.info("笔录已经结束");
            }
            if(sxs != null&&sxs.getHdsj()==null){//笔录没有结束
                targetQy = sxs.getQyid();
                if(targetQy.equals(c.getAreaId())){//到达分配的审讯室
                    log.info("到达分配的审讯室结束审讯监护任务");
                    //结束审讯监护任务
                    BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(baqid);
                    if(baqConfiguration!=null&&baqConfiguration.getJhrwSxjh()){
                        ChasBaq baq = chasBaqService.findById(ryjl.getBaqid());
                        ChasBaqryxx ryxx= baqryxxService.findByRybh(ryjl.getRybh());
                        jhrwService.changeJhrwZt("审讯室民警",baq.getDwdm(), ryxx,SYSCONSTANT.JHRWLX_SXJH ,SYSCONSTANT.JHRWZT_YZX );
                    }

                    //电子水牌变为审讯中
                    dzspService.sendUsingInfo(sxs);
                }else{
                    log.info("走错审讯室");
                    //电子水牌提醒走错审讯室
                    ChasXtQy sxsQy = chasQyService.findByYsid(sxs.getQyid());
                    dzspService.sendErrorInfo(baqid, c.getAreaId(), "请将人员"+sxs.getRyxm()+"带到"+sxsQy.getQymc());
                }
            }
        }
    }
    private boolean intime(ChasXgpz pz){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        long now = calendar.getTimeInMillis()/(1000*60);
        if(pz.getJckssj()<pz.getJcjssj()){
            calendar.set(Calendar.HOUR_OF_DAY, pz.getJckssj());
            calendar.set(Calendar.MINUTE, pz.getJckssjfz());
            long start = calendar.getTimeInMillis()/(1000*60);
            calendar.set(Calendar.HOUR_OF_DAY, pz.getJcjssj());
            calendar.set(Calendar.MINUTE, pz.getJcjssjfz());
            //calendar.add(Calendar.DAY_OF_MONTH, 1);
            long end = calendar.getTimeInMillis()/(1000*60);
            if(now>start&&now<end){
                return true;
            }
        }else{
            calendar.set(Calendar.HOUR_OF_DAY, pz.getJckssj());
            calendar.set(Calendar.MINUTE, pz.getJckssjfz());
            long start = calendar.getTimeInMillis()/(1000*60);
            calendar.set(Calendar.HOUR_OF_DAY, pz.getJcjssj());
            calendar.set(Calendar.MINUTE, pz.getJcjssjfz());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            long end = calendar.getTimeInMillis()/(1000*60);
            if(now>start&&now<end){
                return true;
            }
        }
        return false;
    }
    //处理巡更打卡
    private synchronized void  processXgdk(LSLocationEventInfo c, ChasBaq chasBaq){
        String baqid = chasBaq.getId();
        Map<String, Object> params1 = new HashMap<>();
        params1.put("baqid", baqid);
        List<ChasXgpz> configList = chasXgpzService.findList(params1, null);
        if(configList==null||configList.isEmpty()){
            log.debug("未配置巡更");
            return;
        }
        //是否在配置巡更时间范围内
        boolean isInTime = false;
        for(ChasXgpz pz:configList){
            if(intime(pz)){
                isInTime = true;
                break;
            }
        }
        if(!isInTime){
            log.debug("不在配置巡更时间范围内");
            return;
        }
        String tagNo = c.getTagNo();
        String equipNo = c.getFenceId();
        String key = MessageFormat.format("{0}&{1}", baqid, tagNo);
        Long lastTrigerTime = (Long) MemUtil.get(key);
        if (lastTrigerTime != null) {
            long interval = (System.currentTimeMillis() - lastTrigerTime
                    .longValue()) / 1000;
            if (interval < 60) {
                log.debug(String.format("办案区：%s 一分钟内重复提交无效", key));
                return;
            }
        }
        log.debug(String.format("办案区:%s 卡号为%s的卡片打卡", baqid, tagNo));
        if (StringUtils.isNotEmpty(equipNo)) {
            ChasXgjl duty = new ChasXgjl();
            duty.setId(StringUtils.getGuid32());
            duty.setLrsj(new Date());
            duty.setBaqid(baqid);
            duty.setBaqmc(chasBaq.getBaqmc());
            duty.setDksj(new Date());
            duty.setKh(tagNo);
            duty.setSbbh(equipNo);
            duty.setQyid(c.getAreaId());
            ChasXtQy qy = chasQyService.findByYsid(c.getAreaId());
            if(qy!=null){
                duty.setQymc(qy.getQymc());
            }
            //duty.setSbmc(c.getFenceName());
            duty.setQymc("巡更");
            chasXgjlService.save(duty);
            jdqService.closeAlarm(baqid);
        }
        MemUtil.put(key, System.currentTimeMillis(), 600 * 1000);
    }
    //处理逃脱定位
    private void processTtdw(String baqid, LSLocationEventInfo c){
        String tagNo = c.getTagNo();
        String equipNo = c.getFenceId();
        Map<String, Object> params = new HashMap<>();
        ChasRyjl ryjl = null;
        params.put("baqid", baqid);
        params.put("wdbhH", tagNo);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> ryjls = chasRyjlService.findList(params, null);
        if (!ryjls.isEmpty()) {
            ryjl = ryjls.get(0);
            params.clear();
            params.put("baqid", baqid);
            params.put("rybh", ryjl.getRybh());
            params.put("ryzt", SYSCONSTANT.BAQRYZT_LSCS);
            List<ChasBaqryxx> ryxx = baqryxxService.findList(params, null);
            if(!ryxx.isEmpty()){
                log.debug(String.format("办案区:%s 手环:%s临时出所不触发逃脱预警", baqid,tagNo));
                return;
            }
        }
        if (ryjl == null) {
            log.debug(String.format("办案区:%s 手环:%s人员不存在或者该人员已经出所", baqid,tagNo));
            return;
        }
        // 保证定位的手环和人员位置表一致
        params.clear();
        params.put("baqid", baqid);
        params.put("wdbh", tagNo);
        ChasRygj irPl = null;
        List<ChasRygj> plList = chasYwRygjService.findList(params, null);
        if (!plList.isEmpty()) {
            irPl = plList.get(0);
        }
        if (irPl == null) {
            log.debug(String.format("办案区:%s没有在人员定位表中找到记录：%s", baqid, tagNo));
            return;
        }
        if (StringUtils.isEmpty(irPl.getQyid())) {
            log.debug(String.format("办案区:%s手环%s还未开始定位，不触发报警", baqid, tagNo));
            return;
        }

        params.clear();
        params.put("yjlb", SYSCONSTANT.YJLB_TT);
        params.put("baqid", baqid);
        List<ChasYjlb> yjlbs = chasYjlbService.findList(params, null);
        if (yjlbs.isEmpty()){
            return;
        }
        ChasYjlb yjlb = yjlbs.get(0);
        String message = String.format("失去了办案区人员的定位信息：{%s}",ryjl.getXm());

        ChasYjxx am = new ChasYjxx();
        am.setId(StringUtils.getGuid32());
        am.setLrsj(new Date());
        am.setXgsj(new Date());
        am.setBaqid(baqid);
        am.setYjlb(SYSCONSTANT.YJLB_TT);
        am.setJqms(message);
        am.setYjzt(SYSCONSTANT.YJZT_WCL);
        am.setYjjb(yjlb.getYjjb());
        am.setCfsj(new Date());
        am.setCfrxm(ryjl.getXm());
        am.setCfrid(ryjl.getRybh());
        am.setBaqmc(ryjl.getBaqmc());
        am.setCfqyid(c.getAreaId());
        ChasXtQy qy = chasQyService.findByYsid(c.getAreaId());
        if(qy!=null){
            am.setCfqymc(qy.getQymc());
        }
        am.setCfsbid(equipNo);
        log.debug(String.format("办案区:%s 触发异常：%s", baqid, am.getJqms()));
        if (StringUtils.isNotEmpty(am.getCfrid())) {
            Map<String, Object> params5 = new HashMap<>();
            params5.put("baqid", baqid);
            params5.put("cfrid", am.getCfrid());
            params5.put("cfqyid", am.getCfqyid());
            params5.put("yjlb", am.getYjlb());
            params5.put("yjzt", SYSCONSTANT.YJZT_WCL);
            List<ChasYjxx> yjxxs = chasYjxxService.findList(params5, null);
            if (yjxxs.isEmpty()) {
                    chasYjxxService.save(am);  //在打开报警器方法中保存预警记录
                    // 打开继电器报警
                if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())
                        &&yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                    jdqService.openAlarm(baqid,yjlb.getYjsc()*60000);
                }
            }else {
                yjxxs.stream().forEach(yj->{
                    yj.setCfsj(new Date());
                    chasYjxxService.update(yj);
                });
            }
        }
    }
    //处理手环没电
    private void processShmd(ChasBaq chasBaq, LSLocationEventInfo c) {
        String tagNo = c.getTagNo();
        String message = MessageFormat.format("办案区手环设备{0}电量即将用完",tagNo);
        log.debug(message);
        ChasRyjl ryjl = null;
        Map<String, Object> params = new HashMap<>();
        params.put("yjlb", SYSCONSTANT.YJLB_MD);
        params.put("baqid", chasBaq.getId());
        List<ChasYjlb> yjlbs = chasYjlbService.findList(params, null);
        if (yjlbs.isEmpty()){
            log.info("未开启手环没电预警配置");
            return;
        }
        params.clear();
        params.put("baqid", chasBaq.getId());
        params.put("wdbhH", tagNo);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> ryjls = chasRyjlService.findList(params, null);
        if (!ryjls.isEmpty()) {
            ryjl = ryjls.get(0);
        }
        ChasYjlb yjlb = yjlbs.get(0);
        ChasYjxx am = new ChasYjxx();
        am.setId(StringUtils.getGuid32());
        am.setBaqid(chasBaq.getId());
        am.setYjlb(yjlb.getYjlb());
        am.setJqms(message);
        am.setYjzt(SYSCONSTANT.YJZT_WCL);
        am.setYjjb(yjlb.getYjjb());
        am.setCfsj(new Date());
        am.setBaqmc(chasBaq.getBaqmc());
        am.setCfqyid(c.getAreaId());
        am.setCfsbid(tagNo);
        if(ryjl!=null){
            am.setCfrid(ryjl.getRybh());
            am.setCfrxm(ryjl.getXm());
        }else {
            am.setCfrxm(tagNo);
        }
        log.debug(String.format("办案区:%s 触发异常：%s", chasBaq.getId(), am.getJqms()));
        params.clear();
        params.put("baqid", chasBaq.getId());
        params.put("cfsbid", tagNo);
        params.put("yjlb", am.getYjlb());
        params.put("cfrid",ryjl==null?"": ryjl.getRybh());
        params.put("yjzt", SYSCONSTANT.YJZT_WCL);
        List<ChasYjxx> yjxxs = chasYjxxService.findList(params, null);
        if (yjxxs.isEmpty()) {
            // 在打开报警器方法中保存预警记录
            chasYjxxService.save(am);
            // 打开继电器报警
            if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())&&
                    yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                jdqService.openAlarm(chasBaq.getId(),yjlb.getYjsc()*60000);
            }
        }
    }
    //处理人员聚集消息
    public void processRyjj(String baqid, LSLocationEventInfo content){

        String wdnoH= content.getTagNo();
        Map<String, Object> params = new HashMap<>();
        ChasRyjl ryjl = null;
        params.put("baqid", baqid);
        params.put("wdbhH", wdnoH);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> ryjls = chasRyjlService.findList(params, null);
        if(ryjls != null && !ryjls.isEmpty()){
            ryjl = ryjls.get(0);
        }

        if(ryjl == null){
            log.error(String.format("办案区:%s 手环:%s没有找到对应人员", baqid, wdnoH));
            return;
        }
        params.clear();
        params.put("baqid", baqid);
        params.put("rybh", ryjl.getRybh());
        ChasRygj rygj = chasYwRygjService.findzhlocation(params);
        //处理人员聚集预警
        params.clear();
        params.put("baqid", baqid);
        params.put("yjlb", SYSCONSTANT.YJLB_JJ);
        List<ChasYjlb> yjlbs =  chasYjlbService.findList(params, null);
        if (yjlbs == null||yjlbs.isEmpty()){
            return;
        }
        ChasYjlb yjlb=yjlbs.get(0);
        ChasYjxx chasYjxx = new ChasYjxx();
        chasYjxx.setId(StringUtils.getGuid32());
        chasYjxx.setBaqid(ryjl.getBaqid());
        chasYjxx.setBaqmc(ryjl.getBaqmc());
        chasYjxx.setLrsj(new Date());
        chasYjxx.setCfrxm(ryjl.getXm());
        chasYjxx.setJqms(content.getDisc());
        chasYjxx.setYjjb(yjlb.getYjjb());
        chasYjxx.setYjlb(SYSCONSTANT.YJLB_JJ);
        chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
        chasYjxx.setCfsj(new Date());
        chasYjxx.setCfrid(ryjl.getRybh());
        //chasYjxx.setCfqyid(rygj.getQyid());
        //chasYjxx.setCfqymc(rygj.getQymc());
        if(isNeedSaveAlarmByRy(chasYjxx)){
            chasYjxxService.save(chasYjxx);
            if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())
                    &&yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                jdqService.openAlarm(ryjl.getBaqid(),yjlb.getYjsc()*60000);
            }
        }else{
            //更新触发时间
            ChasYjxx lastYjxx = findLastYjxx(chasYjxx);
            if(lastYjxx == null){
                chasYjxxService.save(chasYjxx);
                if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())
                        &&yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                    jdqService.openAlarm(ryjl.getBaqid(),yjlb.getYjsc()*60000);
                }
            }
            lastYjxx.setJqms(chasYjxx.getJqms());
            lastYjxx.setCfsj(new Date());
            chasYjxxService.update(lastYjxx);
        }
    }
    //处理人员心率消息
    public void dealRyLifeData(String baqid, LSLocationEventInfo content){
        String wdnoH= content.getTagNo();
        String areaid= content.getAreaId();
        String descrition =  content.getDisc();//描述
        String expand = content.getExpandParm();//心率值
        if(expand == null){
            return;
        }
        int ryxlz = 0;
        try {
            ryxlz = Integer.parseInt(expand);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<>();
        ChasRyjl ryjl = null;
        params.put("baqid", baqid);
        params.put("wdbhH", wdnoH);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> ryjls = chasRyjlService.findList(params, null);
        if(ryjls != null && !ryjls.isEmpty()){
            ryjl = ryjls.get(0);
        }

        if(ryjl == null){
            log.error(String.format("办案区:%s 手环:%s没有找到对应人员", baqid, wdnoH));
            return;
        }
        ChasXtQy chasXtQy = chasQyService.findByYsid(areaid);
        if(chasXtQy == null){
            log.debug(String.format("办案区%s未找到区域原始编号%s", baqid,areaid));
            return;
        }
        //保存人员心率信息
        ChasYwRyxl ryxl = new ChasYwRyxl();
        ryxl.setId(StringUtils.getGuid32());
        ryxl.setLrsj(new Date());
        ryxl.setIsdel(0);
        ryxl.setBaqid(ryjl.getBaqid());
        ryxl.setBaqmc(ryjl.getBaqmc());
        ryxl.setWdbhH(ryjl.getWdbhH());
        ryxl.setWdbhL(ryjl.getWdbhL());
        ryxl.setRybh(ryjl.getRybh());
        ryxl.setRyxm(ryjl.getXm());
        ryxl.setBcsj(new Date());
        ryxl.setRyxl(ryxlz);
        ryxl.setQyid(chasXtQy.getYsid());
        ryxl.setQymc(chasXtQy.getQymc());
        chasYwRyxlService.save(ryxl);
        //处理人员心率异常预警
        params.clear();
        params.put("baqid", baqid);
        params.put("yjlb", SYSCONSTANT.YJLB_XL);
        List<ChasYjlb> yjlbs =  chasYjlbService.findList(params, null);
        if (yjlbs == null||yjlbs.isEmpty()){
            return;
        }
        chasYwRyxlService.processRyxlYj(ryxl);
    }
    public boolean isNeedSaveAlarmByRy(ChasYjxx yjxx){
        //判断区域、触发人员是否存在预警信息
        // 人员id不为空
        if (StringUtils.isNotEmpty(yjxx.getCfrid())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("baqid", yjxx.getBaqid());
            params.put("cfrid", yjxx.getCfrid());
            params.put("yjzt", yjxx.getYjzt());
            params.put("yjlb", yjxx.getYjlb());
            List<ChasYjxx> yjxxs = chasYjxxService.findList(params, null);
            if (yjxxs!= null&&!yjxxs.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ChasYjxx findLastYjxx(Map<String, Object> params){
        List<ChasYjxx> yjxxs = chasYjxxService.findList(params, "cfsj desc");
        if(yjxxs != null&&!yjxxs.isEmpty()){
            return yjxxs.get(0);
        }
        return null;
    }
    public ChasYjxx findLastYjxx(ChasYjxx yjxx){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", yjxx.getBaqid());
        params.put("cfrid", yjxx.getCfrid());
        params.put("yjzt", yjxx.getYjzt());
        params.put("yjlb", yjxx.getYjlb());
        return findLastYjxx(params);
    }

}
