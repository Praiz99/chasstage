package com.wckj.chasstage.api.server.release.dc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.release.dc.dto.CameraLocationInfo;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.HikFaceLocationEventService;
import com.wckj.chasstage.api.server.release.dc.service.gj.IRygjProcessor;
import com.wckj.chasstage.common.util.MemUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.xgjl.entity.ChasXgjl;
import com.wckj.chasstage.modules.xgjl.service.ChasXgjlService;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
public class HikFaceLocationEventServiceImpl implements HikFaceLocationEventService {

    private static Logger log = Logger.getLogger(HikFaceLocationEventServiceImpl.class);

    @Autowired
    @Qualifier("xyrgjProcessor")
    private IRygjProcessor xyrgjProcessor;
    @Autowired
    @Qualifier("mjgjProcessor")
    private IRygjProcessor mjgjProcessor;
    @Autowired
    @Qualifier("fkgjProcessor")
    private IRygjProcessor fkgjProcessor;

    @Autowired
    private ChasBaqService chasBaqService;

    @Autowired
    private ChasXgpzService chasXgpzService;

    @Autowired
    private ChasXtQyService chasQyService;

    @Autowired
    private ChasXgjlService chasXgjlService;

    @Autowired
    private IJdqService jdqService;

    @Autowired
    private ChasRyjlService chasRyjlService;

    @Autowired
    private ChasYwRygjService chasYwRygjService;

    @Autowired
    private ChasBaqryxxService baqryxxService;

    @Autowired
    private ChasYjlbService chasYjlbService;
    @Autowired
    private ChasYjxxService chasYjxxService;

    @Autowired
    private ChasYwMjrqService chasYwMjrqService;



    @Override
    public void dealLocationMessageWckj(String baqid, CameraLocationInfo content) {
        log.error(String.format("dealLocationMessageWckj 接口调用 参数信息：%s  %s",baqid, JSONObject.toJSONString(content)));
        String rylx = content.getRylx();
        String rybh = content.getRybh();
        String equipFun = content.getEquipFun();
        if(StringUtil.isEmpty(rybh)||StringUtil.isEmpty(content.getAreaId())){
            log.warn("参数缺失");
            return;
        }
        ChasBaq chasBaq=chasBaqService.findById(baqid);
        if(chasBaq==null){
            log.error(String.format("未配置办案区%s人脸定位", baqid));
            return;
        }
        //转换一下原生成轨迹对象信息
        TagLocationInfo tagLocationInfo = new TagLocationInfo();
        tagLocationInfo.setTagType(content.getRylx());
        tagLocationInfo.setEquipFun(content.getEquipFun());
        tagLocationInfo.setRybh(content.getRybh());
        tagLocationInfo.setEquipNo(content.getCameraId());
        tagLocationInfo.setArea(content.getAreaId());
        tagLocationInfo.setAreaName(content.getAreaName());
        tagLocationInfo.setZsid(content.getCameraId());
        //闻堰测试环境存在和海康服务器时间不一致问题，直接采用本机当前时间
        //tagLocationInfo.setLocationTime(content.getLocationTime());
        tagLocationInfo.setLocationTime(new Date().getTime());
        tagLocationInfo.setLocationType("1");
        tagLocationInfo.setStartTime(content.getLocationTime());
        if("1".equals(rylx)){//嫌疑人
            xyrgjProcessor.process(baqid, tagLocationInfo);
        }else{
            //非嫌疑人，先尝试从民警入区表中查询人员信息，如果没有则从访客登记表中查找人员信息
            boolean mjgj = mjgjProcessor.process(baqid, tagLocationInfo);
            boolean fkgj = fkgjProcessor.process(baqid, tagLocationInfo);
            if(mjgj || fkgj){
                log.info("保存人脸定位轨迹信息成功");
            }
        }

        if ("24".equals(equipFun)) { //巡更打卡
            log.info("巡更打卡摄像头编号:" + content.getCameraId());
            processXgdk(content,chasBaq);
            log.info("已处理人脸定位触发的巡更打卡事件");
        }else if ("12".equals(equipFun)) {//人员逃脱
            log.info("人员逃脱摄像头编号:" + content.getCameraId());
            processTtdw(baqid,content);
            log.info("已处理人脸定位触发的逃脱事件");
        }

    }

    //处理巡更打卡
    private synchronized void processXgdk(CameraLocationInfo c, ChasBaq chasBaq){
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
        String cameraId = c.getCameraId();
        String rybh = c.getRybh();
        String key = MessageFormat.format("{0}&{1}", baqid, rybh);
        Long lastTrigerTime = (Long) MemUtil.get(key);
        if (lastTrigerTime != null) {
            long interval = (System.currentTimeMillis() - lastTrigerTime.longValue()) / 1000;
            if (interval < 60) {
                log.debug(String.format("办案区：%s 一分钟内重复提交无效", key));
                return;
            }
        }
        log.debug(String.format("办案区:%s 人员编号为%s巡更打卡,人员类型%s", baqid, rybh, c.getRylx()));
        //因为改为人脸打卡后无需绑定胸卡，所以暂时改为只要是民警就巡更打卡成功，后续有需求完善再说
        if("2".equals(c.getRylx())){
            ChasYwMjrq mj = chasYwMjrqService.findMjrqByRybh(baqid, rybh);
            if(mj != null){
                ChasXgjl duty = new ChasXgjl();
                duty.setId(com.wckj.framework.core.utils.StringUtils.getGuid32());
                duty.setBaqid(baqid);
                duty.setBaqmc(chasBaq.getBaqmc());
                duty.setDksj(new Date());
                duty.setSbbh(cameraId);
                duty.setQyid(c.getAreaId());
                duty.setSbmc(c.getCameraName());
                //duty.setKh(c.getRybh());
                //人脸定位没有巡更绑定的卡号，暂时用姓名代替
                duty.setKh(mj.getMjxm());
                if(StringUtil.isNotEmpty(duty.getQyid())){
                    Map<String, Object> params = new HashMap<>();
                    params.put("baqid", baqid);
                    params.put("ysid", duty.getQyid());
                    List<ChasXtQy> chasQyss=chasQyService.findList(params, null);
                    if(!chasQyss.isEmpty()){
                        duty.setQyid(chasQyss.get(0).getYsid());
                        duty.setQymc(chasQyss.get(0).getQymc());
                    }
                }
                chasXgjlService.save(duty);
                jdqService.closeAlarm(baqid);
                MemUtil.put(key, System.currentTimeMillis(), 600 * 1000);
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

    //处理逃脱定位
    private void processTtdw(String baqid, CameraLocationInfo c){
        log.error(String.format("人员逃脱定位信息打印：%s %s",baqid,JSONObject.toJSONString(c)));
        String cameraId = c.getCameraId();
        String rybh = c.getRybh();
        Map<String, Object> params = new HashMap<>();
        ChasRyjl ryjl = null;
        params.put("baqid", baqid);
        params.put("rybh", rybh);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> ryjls = chasRyjlService.findList(params, null);
        log.error(String.format("processTtdw 查询人员记录参数：%s %s %s 结果 %s",baqid,rybh,SYSCONSTANT.BAQRYDCZT_JXZ,ryjls.size()));
        if (!ryjls.isEmpty()) {
            ryjl = ryjls.get(0);
            params.clear();
            params.put("baqid", baqid);
            params.put("rybh", ryjl.getRybh());
            params.put("ryzt", SYSCONSTANT.BAQRYZT_LSCS);
            List<ChasBaqryxx> ryxx = baqryxxService.findList(params, null);
            if(!ryxx.isEmpty()){
                log.debug(String.format("办案区:%s 人员:%s临时出所不触发逃脱预警", baqid,rybh));
                log.error(String.format("办案区:%s 人员:%s临时出所不触发逃脱预警", baqid,rybh));
                return;
            }
        }
        if (ryjl == null) {
            log.debug(String.format("办案区:%s 人员%s不存在或者该人员已经出所", baqid,rybh));
            log.error(String.format("办案区:%s 人员%s不存在或者该人员已经出所", baqid,rybh));
            return;
        }
        // 保证定位的手环和人员位置表一致
        params.clear();
        params.put("baqid", baqid);
        params.put("rybh", rybh);
        ChasRygj irPl = null;
        List<ChasRygj> plList = chasYwRygjService.findList(params, null);
        log.error(String.format("人员轨迹查询参数：%s %s 结果 %s",baqid,rybh,plList.size()));
        if (!plList.isEmpty()) {
            irPl = plList.get(0);
        }
        if (irPl == null) {
            log.debug(String.format("办案区:%s没有在人员定位表中找到记录：%s", baqid, rybh));
            log.error(String.format("办案区:%s没有在人员定位表中找到记录：%s", baqid, rybh));
            return;
        }
        if (StringUtils.isEmpty(irPl.getQyid())) {
            log.debug(String.format("办案区:%s人员%s还未开始定位，不触发报警", baqid, rybh));
            log.error(String.format("办案区:%s人员%s还未开始定位，不触发报警", baqid, rybh));
            return;
        }

        params.clear();
        params.put("yjlb", SYSCONSTANT.YJLB_TT);
        params.put("baqid", baqid);
        List<ChasYjlb> yjlbs = chasYjlbService.findList(params, null);
        log.error(String.format("预警类别查询参数：%s %s 结果 %s",baqid,SYSCONSTANT.YJLB_TT,yjlbs.size()));
        if (yjlbs.isEmpty()){
            return;
        }
        ChasYjlb yjlb = yjlbs.get(0);
        String message = String.format("失去了办案区人员的定位信息：{%s}",ryjl.getXm());

        ChasYjxx am = new ChasYjxx();
        am.setId(StringUtils.getGuid32());
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
        //摄像头编号
        am.setCfsbid(cameraId);
        log.debug(String.format("办案区:%s 触发异常：%s", baqid, am.getJqms()));
        log.error(String.format("办案区:%s 触发异常：%s", baqid, am.getJqms()));
        if (StringUtils.isNotEmpty(am.getCfrid())) {
            Map<String, Object> params5 = new HashMap<>();
            params5.put("baqid", baqid);
            params5.put("cfrid", am.getCfrid());
            params5.put("cfqyid", am.getCfqyid());
            params5.put("yjlb", am.getYjlb());
            params5.put("yjzt", SYSCONSTANT.YJZT_WCL);
            List<ChasYjxx> yjxxs = chasYjxxService.findList(params5, null);
            log.error(String.format("查询预警信息参数：%s %s %s %s %s 结果 %s",baqid,am.getCfrid(),am.getCfqyid(),am.getYjlb(),SYSCONSTANT.YJZT_WCL,yjxxs.size()));
            if (yjxxs.isEmpty()) {
                chasYjxxService.save(am);  //在打开报警器方法中保存预警记录
                // 打开继电器报警
                if (SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb()) &&
                        yjlb.getYjsc() != null && yjlb.getYjsc() > 0) {
                    jdqService.openAlarm(baqid, yjlb.getYjsc() * 60000);
                }

            }else {
                yjxxs.forEach(yj -> {
                    yj.setCfsj(new Date());
                    chasYjxxService.update(yj);
                });
            }
        }
    }
}
