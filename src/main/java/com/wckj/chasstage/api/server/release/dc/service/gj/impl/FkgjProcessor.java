package com.wckj.chasstage.api.server.release.dc.service.gj.impl;


import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.gj.IRygjProcessor;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
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
import java.util.List;
import java.util.Map;

@Component("fkgjProcessor")
public class FkgjProcessor implements IRygjProcessor {
    private static Logger log = Logger.getLogger(FkgjProcessor.class);
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasXtQyService chasQyService;
    @Autowired
    private ChasYwFkdjService chasYwFkdjService;
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
        String tagType =content.getTagType();
        String rybh = content.getRybh();
        String areaId = content.getArea();
        //String zsid = content.getZsid();
        if(StringUtils.isNotEmpty(tagId)){
            log.debug(String.format("办案区:%s手环:%s的被设备%s感应到了", baqid, tagId, equipNo));
        }
        String watchNo = tagId;
        // 查找人员记录
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("rybh", rybh);
        map.put("zt", SYSCONSTANT.MJRQ_ZQ);//入区状态
        List<ChasYwFkdj> chasFkjlList = chasYwFkdjService.findList(map,null);
        ChasYwFkdj chasFkjl = null;
        if(!chasFkjlList.isEmpty()){
            chasFkjl = chasFkjlList.get(0);
            // 查找区域
            ChasXtQy chasXtQy = chasQyService.findByYsid(areaId);
            if(chasXtQy == null){
                log.error(String.format("办案区%s未找到区域编号%s", baqid,watchNo));
                return false;
            }
            ChasRygj chasRygj = chasYwRygjService.findzhlocation(map);
            saveRygjSnapInfo(baqid,content,chasXtQy,chasFkjl);
            boolean isadd=saveGjInfo(chasXtQy,chasRygj,chasFkjl,content);
            log.debug(String.format("办案区:%s访客%s第一次定位到%s", baqid,chasFkjl.getFkxm(), tagId));
            if(isadd){//位置发生变化
                //chasRygjmapService.buildLocaltionMoveInfo(chasFkjl.getRybh());
            }
            return true;
        }
        return false;
    }

    //保存轨迹信息
    private boolean saveGjInfo(ChasXtQy chasXtQy,ChasRygj chasRygj,ChasYwFkdj chasFkjl,TagLocationInfo content){
        // 轨迹移动 当前定位区域与当前区域
        //BaqConfiguration configuration = baqznpzService.findByBaqid(chasRygj.getBaqid());
        if (chasRygj != null) {
            if (!chasXtQy.getYsid().equals(chasRygj.getQyid())) {
                log.debug(String.format("访客办案区%s %s从%s到%s", chasFkjl.getBaqid(),
                        chasFkjl.getFkxm(), chasRygj.getQyid(), chasXtQy.getYsid()));
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
        chasRygj.setBaqid(chasFkjl.getBaqid());
        chasRygj.setBaqmc(chasXtQy.getBaqmc());
        chasRygj.setRyid(chasFkjl.getId());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setRybh(chasFkjl.getRybh());
        chasRygj.setWdbh(content.getTagId());
        chasRygj.setXm(chasFkjl.getFkxm());
        chasRygj.setSbid(content.getZsid());
        chasRygj.setRylx("fk");
        chasYwRygjService.save(chasRygj);
        /*if(configuration.getSendVms()){
            vmsjlService.addVmsjl(chasRygj);
            vmsService.startVms(chasRygj);

        }*/
        return true;
    }

    private void saveRygjSnapInfo(String baqid, TagLocationInfo content, ChasXtQy chasXtQy, ChasYwFkdj chasFkjl){
        //String tagId = content.getTagId();
        String rybh = content.getRybh();
        //String areaId = content.getArea();
        String zsid = content.getZsid();
        //String watchNo = tagId;
        String ryid = chasFkjl.getId();
        ChasRygjSnap chasRygj = chasYwRygjSnapService.findById(chasFkjl.getRybh());
        boolean isAdd = false;
        if(chasRygj == null){
            isAdd = true;
            chasRygj = new ChasRygjSnap();
            chasRygj.setId(chasFkjl.getRybh());
            chasRygj.setLrsj(new Date());
            chasRygj.setBaqid(baqid);
            chasRygj.setBaqmc(chasXtQy.getBaqmc());
            chasRygj.setRyid(ryid);
            chasRygj.setRybh(rybh);
            chasRygj.setWdbh(chasFkjl.getWdbhH());
            chasRygj.setXm(chasFkjl.getFkxm());
        }
        chasRygj.setKssj(new Date());
        chasRygj.setQyid(chasXtQy.getYsid());
        chasRygj.setQymc(chasXtQy.getQymc());
        chasRygj.setSbid(zsid);
        chasRygj.setRylx("fk");
        if(isAdd){
            chasYwRygjSnapService.save(chasRygj);
        }else{
            chasYwRygjSnapService.update(chasRygj);
        }
    }

}
