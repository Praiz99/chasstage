package com.wckj.chasstage.api.server.release.dc.service.gj.impl;


import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.gj.IRygjProcessor;
import com.wckj.chasstage.common.util.BaqrysypUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.taskClient.web.SysStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("xyrgjProcessor")
public class XyrgjProcessor implements IRygjProcessor {
    private static Logger log = LoggerFactory.getLogger(SysStart.class);
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ChasXtQyService chasQyService;
    @Autowired
    private ChasSxsglService chasSxsglService;
    @Autowired
    private ChasDhsKzService chasDhsKzService;
    @Autowired
    private ChasDhsglService chasDhsglService;
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;
    //@Autowired
    //private ChasRygjmapService chasRygjmapService;
    @Autowired
    private ChasYwRygjSnapService chasYwRygjSnapService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasSxsKzService sxsKzService;
    @Autowired
    private ApiRygjService apiRygjService;

    @Override
    public boolean process(String baqid, TagLocationInfo content) {
        String tagType = content.getTagType();
        String equipFun = content.getEquipFun();
        String rybh = content.getRybh();
        if("1".equals(tagType)){//嫌疑人
            String tagId = content.getTagId();
            String equipNo = content.getEquipNo();
            // 1 嫌疑人 2 民警 4 访客
            //String tagType =content.getTagType();
            String areaId = content.getArea();
            if(StringUtils.isNotEmpty(tagId)){
                log.debug(String.format("办案区:%s手环:%s的被设备%s感应到了", baqid, tagId, equipNo));
            }else{
                log.debug(String.format("办案区:%s人员:%s的被摄像头%s感应到了", baqid, rybh, equipNo));
            }
            String watchNo = tagId;
            // 查找人员记录
            Map<String, Object> map = new HashMap<>();
            map.put("baqid", baqid);
            map.put("rybh", rybh);
            map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
            ChasRyjl chasRyjl = chasRyjlService.findByParams(map);
            if (chasRyjl != null) {
                map.clear();
                map.put("baqid", baqid);
                map.put("rybh", rybh);
                List<ChasBaqryxx> ryxxList = chasBaqryxxService.findList(map, null);
                if(ryxxList == null||ryxxList.isEmpty()){
                    log.debug(String.format("办案区%s人员信息表%s未找到人员", baqid, rybh));
                    return false;
                }
                // 查找区域
                ChasXtQy chasXtQy = chasQyService.findByYsid(areaId);
                if(chasXtQy == null){
                    log.debug(String.format("办案区%s未找到区域编号%s", baqid,areaId));
                    return false;
                }
                saveRygjSnapInfo(baqid,content,chasXtQy,ryxxList.get(0) ,chasRyjl);

                if(filterRygj(chasXtQy,chasRyjl)){
                    log.debug(String.format("办案区%s过滤掉人员%s区域%s轨迹记录", baqid,chasRyjl.getRybh(),chasXtQy.getQymc()));
                    return false;
                }
                ChasRygj chasRygj = chasYwRygjService.findzhlocation(map);
                boolean isadd= saveGjInfo(chasXtQy, chasRygj, ryxxList.get(0),content, map);
                if(StringUtils.isNotEmpty(tagId)){
                    log.debug(String.format("办案区:%s人员%s第一次定位到手环%s", baqid, rybh, tagId));
                }else{
                    log.debug(String.format("办案区:%s人员%s第一次被摄像头%s定位到", baqid, rybh, equipNo));
                }
                if(isadd){//位置发生变化
                    //chasRygjmapService.buildLocaltionMoveInfo(chasRyjl.getRybh());
                }
                if (equipFun.equals("19")) {// 离开审讯室  可以认为先收到笔录结束通知
                    chasSxsglService.sxsLkYzBl(baqid, rybh);
                }
                if (equipFun.equals("20")) {// 到达等候室
                    log.info("人员回流等候室:rybh"+rybh);
                    chasDhsglService.dhsFpYzBl(baqid, rybh, null);
                }
                return true;
            }
        }
        return false;
    }

    private boolean filterRygj(ChasXtQy qy, ChasRyjl ryjl){
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryjl.getBaqid());
        if(configuration != null){
            if(configuration.getLocationTracks()){  // 启动轨迹过滤
                return screenByRule(qy,ryjl);
            }
        }
        return false;
    }

    private boolean screenByRule(ChasXtQy qy, ChasRyjl ryjl) {
        log.debug(String.format("进入轨迹过滤方法 人员姓名: %s 人员编号: %s 区域名称: %s",ryjl.getXm(),ryjl.getRybh(),qy.getQymc()));
        if(SYSCONSTANT.FJLX_DHS.equals(qy.getFjlx())){ //人员当前位置在看守区
            String dhsid = ryjl.getDhsBh();
            if(StringUtils.isNotEmpty(dhsid)){
                ChasDhsKz dhs = chasDhsKzService.findById(dhsid);
                if(dhs != null){
                    log.debug(String.format("匹配当前已分配的等候室: %s",!StringUtils.equals(dhs.getQyid(),qy.getYsid())));
                    return !StringUtils.equals(dhs.getQyid(),qy.getYsid());
                }
            }
            return true;
        } else if(SYSCONSTANT.FJLX_SXS.equals(qy.getFjlx())){
            String sxsid = ryjl.getSxsBh();
            ChasSxsKz sxs = null;
            if(StringUtils.isNotEmpty(sxsid)){
                sxs = sxsKzService.findById(sxsid);
            }else{
                //由于笔录结束人员记录表中审讯室分配记录信息已经删除，此时需要在审讯室分配记录表中查询
                Map<String,Object> map = new HashMap<>();
                map.put("baqid",ryjl.getBaqid());
                map.put("rybh", ryjl.getRybh());
                map.put("condition",true);
                List<ChasSxsKz> sxsKzList = sxsKzService.findByParams(map);
                if(sxsKzList != null&&!sxsKzList.isEmpty()){
                    sxs = sxsKzList.get(0);
                }
            }
            if(sxs != null){
                log.debug(String.format("匹配当前已分配的审讯室: %s",!StringUtils.equals(sxs.getQyid(),qy.getYsid())));
                return !StringUtils.equals(sxs.getQyid(),qy.getYsid());
            }
            return true;
        }else {  //其他通用
            return false;
        }
    }


    //保存轨迹信息
    private boolean saveGjInfo(ChasXtQy chasXtQy,ChasRygj chasRygj ,
                               ChasBaqryxx ryxx,TagLocationInfo content, Map<String, Object> map){
        // 轨迹移动 当前定位区域与当前区域
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxx.getBaqid());
        if (chasRygj != null) {
            if (!chasXtQy.getYsid().equals(chasRygj.getQyid())) {
                log.debug(String.format("办案区%s %s从%s到%s", ryxx.getBaqid(),
                        ryxx.getRyxm(), chasRygj.getQyid(), chasXtQy.getYsid()));
                chasRygj.setJssj(new Date(content.getLocationTime()));
                chasRygj.setXgrSfzh(ryxx.getMjSfzh());
                chasYwRygjService.update(chasRygj);
                //如果一条发消息
                List<ChasRygj> chasRygjList = chasYwRygjService.findzhlocations(map);
                if (chasRygjList != null && chasRygjList.size() == 1) {
                    apiRygjService.sendRecVmsInfo(chasRygj.getKssj(), chasRygj.getJssj(), ryxx.getBaqid(), ryxx.getRybh(), chasRygj.getQyid());
                }

                //PushBecauseofTygkUtil.PushLocationInformationByRy(chasRygj);
                //PushBecauseofTygkUtil.PushTrackInformationByRy(chasRygj);

                if(configuration != null){
                    if(configuration.getQgc()){
                        //保存人员视音频数据到全过程系统
                        BaqrysypUtil.uplodsaveAudioVideoByRygj(chasRygj);
                    }
                    /*if(configuration.getSendVms()){
                        ChasYwVmsjl vmsjl = vmsjlService.getVmsjlByRybh(chasRygj.getRybh(), "0");
                        if(vmsjl!=null){
                            vmsjl.setIsend("1");
                            vmsjl.setJssj(new Date());
                            vmsjlService.update(vmsjl);
                            vmsService.endVms(vmsjl.getRybh(), vmsjl.getQyid(), 0);
                        }
                    }*/
                }
            }else{
                //位置不变,不保存轨迹信息,不需要通知轨迹变化消息
                return false;
            }
        }

        chasRygj = new ChasRygj();
        chasRygj.setId(StringUtils.getGuid32());
        chasRygj.setLrsj(new Date());
        chasRygj.setKssj(new Date(content.getLocationTime()));
        chasRygj.setLrrSfzh(ryxx.getLrrSfzh());
        chasRygj.setXgrSfzh(ryxx.getLrrSfzh());
        chasRygj.setBaqid(ryxx.getBaqid());
        chasRygj.setBaqmc(chasXtQy.getBaqmc());
        chasRygj.setRyid(ryxx.getId());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setRybh(ryxx.getRybh());
        chasRygj.setWdbh(content.getTagId());
        chasRygj.setXm(ryxx.getRyxm());
        chasRygj.setRylx("xyr");
        if(StringUtils.isNotEmpty(ryxx.getMjXm())){
            chasRygj.setFzrxm(ryxx.getMjXm());
        }
        chasRygj.setSbid(content.getZsid());
        chasYwRygjService.save(chasRygj);
        /*if(configuration.getSendVms()){
            vmsjlService.addVmsjl(chasRygj);
            vmsService.startVms(chasRygj);

        }*/
        return true;
    }
    private void saveRygjSnapInfo(String baqid, TagLocationInfo content, ChasXtQy chasXtQy, ChasBaqryxx ryxx , ChasRyjl chasRyjl){
        String tagId = content.getTagId();
        String rybh = content.getRybh();
        //String areaId = content.getArea();
        String zsid = content.getZsid();
        //String watchNo = tagId;
        String mjxm = ryxx.getMjXm();
        String ryid = ryxx.getId();
        ChasRygjSnap chasRygj = chasYwRygjSnapService.findById(chasRyjl.getRybh());
        boolean isAdd = false;
        if(chasRygj == null){
            isAdd = true;
            chasRygj = new ChasRygjSnap();
            chasRygj.setId(chasRyjl.getRybh());
            chasRygj.setLrsj(new Date());
            chasRygj.setBaqid(baqid);
            chasRygj.setBaqmc(chasXtQy.getBaqmc());
            chasRygj.setRyid(ryid);
            chasRygj.setRybh(rybh);
            chasRygj.setWdbh(chasRyjl.getWdbhH());
            chasRygj.setXm(chasRyjl.getXm());
            if(StringUtils.isNotEmpty(mjxm)){
                chasRygj.setFzrxm(mjxm);
            }
        }
        chasRygj.setKssj(new Date());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setSbid(zsid);
        chasRygj.setRylx("xyr");
        if(isAdd){
            chasYwRygjSnapService.save(chasRygj);
        }else{
            chasYwRygjSnapService.update(chasRygj);
        }
    }



}
