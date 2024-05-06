package com.wckj.chasstage.api.server.release.dc.service.gj.impl;


import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.gj.IRygjProcessor;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("mjgjProcessor")
public class MjgjProcessor implements IRygjProcessor {
    private static Logger log = Logger.getLogger(MjgjProcessor.class);
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasXtQyService chasQyService;
    @Autowired
    private ChasYwMjrqService chasYwMjrqService;
    //@Autowired
    //private ChasRygjmapService chasRygjmapService;
    @Autowired
    private ChasYwRygjSnapService chasYwRygjSnapService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;

    @Override
    public boolean process(String baqid, TagLocationInfo content) {
        String tagId = content.getTagId();
        String equipNo = content.getEquipNo();
        //String equipFun = content.getEquipFun();
        // 1 嫌疑人 2 民警 4 访客
        //String tagType =content.getTagType();
        String rybh = content.getRybh();
        String areaId = content.getArea();
        //String zsid = content.getZsid();
        if(StringUtils.isNotEmpty(tagId)){
            log.debug(String.format("办案区:%s手环:%s的被设备%s感应到了", baqid, tagId, equipNo));
        }
        String watchNo = tagId;
        // 查找民警记录
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("rybh", rybh);
        map.put("zt", SYSCONSTANT.MJRQ_ZQ);//入区状态
        ChasYwMjrq chasMjjl = chasYwMjrqService.findByParams(map);
        if (chasMjjl != null) {
            // 查找区域
            ChasXtQy chasXtQy = chasQyService.findByYsid(areaId);
            if(chasXtQy == null){
                log.error(String.format("办案区%s未找到区域编号%s", baqid,areaId));
                return false;
            }
            ChasRygj chasRygj = chasYwRygjService.findzhlocation(map);
            saveRygjSnapInfo(baqid,content,chasXtQy,chasMjjl);
            boolean isadd=saveGjInfo(chasXtQy, chasRygj, chasMjjl,content);
            log.debug(String.format("办案区:%s访客%s第一次定位到%s", baqid,chasMjjl.getMjxm(), tagId));
            if(isadd){//位置发生变化
                //chasRygjmapService.buildLocaltionMoveInfo(chasMjjl.getRybh());
            }
            return true;
        }
        return false;
    }

    //保存轨迹信息
    private boolean saveGjInfo(ChasXtQy chasXtQy,ChasRygj chasRygj,ChasYwMjrq chasMjjl,TagLocationInfo content){
        // 轨迹移动 当前定位区域与当前区域
        //BaqConfiguration configuration = baqznpzService.findByBaqid(chasRygj.getBaqid());
        if (chasRygj != null) {
            if (!chasXtQy.getYsid().equals(chasRygj.getQyid())) {
                log.debug(String.format("民警从办案区%s %s从%s到%s", chasMjjl.getBaqid(),
                        chasMjjl.getMjxm(), chasRygj.getQyid(), chasXtQy.getYsid()));
                chasRygj.setJssj(new Date(content.getLocationTime()));
                chasYwRygjService.update(chasRygj);
                /*if(configuration.getSendVms()){
                    ChasYwVmsjl vmsjl = vmsjlService.getVmsjlByRybh(chasRygj.getRybh(), "0");
                    if(vmsjl!=null){
                        vmsjl.setIsend("1");
                        vmsjl.setJssj(new Date());
                        vmsjlService.update(vmsjl);
                        vmsService.endVms(vmsjl.getRybh(), vmsjl.getQyid(), 0);
                    }
                }*/
            }else{
                //位置不变,不保存轨迹信息,不需要通知轨迹变化消息
                return false;
            }
        }
        chasRygj = new ChasRygj();
        chasRygj.setId(StringUtils.getGuid32());
        chasRygj.setLrsj(new Date());
        chasRygj.setKssj(new Date(content.getLocationTime()));
        chasRygj.setBaqid(chasMjjl.getBaqid());
        chasRygj.setBaqmc(chasXtQy.getBaqmc());
        chasRygj.setRyid(chasMjjl.getId());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setRybh(chasMjjl.getRybh());
        chasRygj.setWdbh(content.getTagId());
        chasRygj.setXm(chasMjjl.getMjxm());
        chasRygj.setSbid(content.getZsid());
        chasRygj.setRylx("mj");
        chasYwRygjService.save(chasRygj);
       /* if(configuration.getSendVms()){
            vmsjlService.addVmsjl(chasRygj);
            vmsService.startVms(chasRygj);

        }*/
        return true;
    }


    //保存人员轨迹快照信息，用于对讲调度界面
    private void saveRygjSnapInfo(String baqid, TagLocationInfo content, ChasXtQy chasXtQy, ChasYwMjrq mjrq){
        String tagId = content.getTagId();
        String rybh = content.getRybh();
        String areaId = content.getArea();
        String zsid = content.getZsid();
        String watchNo = tagId;
        String ryid = mjrq.getId();
        ChasRygjSnap chasRygj = chasYwRygjSnapService.findById(mjrq.getRybh());
        boolean isAdd = false;
        if(chasRygj == null){
            isAdd = true;
            chasRygj = new ChasRygjSnap();
            chasRygj.setId(mjrq.getRybh());
            chasRygj.setLrsj(new Date());
            chasRygj.setBaqid(baqid);
            chasRygj.setBaqmc(chasXtQy.getBaqmc());
            chasRygj.setRyid(ryid);
            chasRygj.setRybh(rybh);
            chasRygj.setWdbh(mjrq.getWdbhH());
            chasRygj.setXm(mjrq.getMjxm());
        }
        chasRygj.setKssj(new Date());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setRylx("mj");
        chasRygj.setSbid(zsid);
        if(isAdd){
            chasYwRygjSnapService.save(chasRygj);
        }else{
            chasYwRygjSnapService.update(chasRygj);
        }
    }



}
