package com.wckj.chasstage.api.server.release.dc.service.impl;

import com.wckj.chasstage.api.server.release.dc.dto.TBaseTagDis;
import com.wckj.chasstage.api.server.release.dc.dto.Tag;
import com.wckj.chasstage.api.server.release.dc.service.ILSBaseDisService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.chasstage.modules.clsyjl.service.ChasYwClsyjlService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ILSBaseDisServiceImpl implements ILSBaseDisService {
    private static Logger log = Logger.getLogger(ILSBaseDisServiceImpl.class);
    @Autowired
    private ChasYwClsyjlService clsyjlService;
    @Autowired
    private ChasXtCldjService cldjService;
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    private int innerDis = 1000;
    private int outerDis = 10000;
    @Override
    public void dealBaseDisEvent(TBaseTagDis content) {
        log.debug("开始处理基站距离消息"+content);
        Map<String,Object> map = new HashMap<>();
        map.put("jzbh", content.getBaseId()+"");
        map.put("isend", "0");

        List<ChasYwClsyjl> list;
        for(Tag tag:content.getTags()){
            map.put("wdbhH", ""+tag.getTagId());
            list = clsyjlService.findList(map, "lrsj desc");
            if(list!=null&&!list.isEmpty()){
                processTag(tag,list.get(0));
            }
        }

    }

    private void processTag(Tag tag,ChasYwClsyjl syjl){
        if(tag.getDis()<=innerDis){//进入车辆，设置开始时间
            if(syjl.getKssj()==null){
                syjl.setKssj(new Date(tag.getTimeDis()));
                clsyjlService.update(syjl);
                addSygj(syjl);
            }
        }else if(tag.getDis()>=outerDis){
            if(syjl.getJssj()==null&&syjl.getKssj()!=null){//已经上车了，处理下车
                Date kssj = syjl.getKssj();
                long now = System.currentTimeMillis();
                if(now-kssj.getTime()>=15*60*1000){//超过30min
                    syjl.setJssj(new Date(tag.getTimeDis()));
                    syjl.setIsend("1");
                    clsyjlService.update(syjl);
                    endSygj(syjl);
                }
            }
            //processClxx(syjl);
        }
    }
    //添加送押轨迹
    private void addSygj(ChasYwClsyjl syjl){
        ChasBaqryxx ryxx = chasBaqryxxService.findByRybh(syjl.getRybh());
        ChasRygj chasRygj = new ChasRygj();
        chasRygj.setId(StringUtils.getGuid32());
        chasRygj.setLrsj(new Date());
        chasRygj.setXgrSfzh(ryxx.getMjSfzh());
        chasRygj.setLrrSfzh(ryxx.getMjSfzh());
        chasRygj.setKssj(syjl.getKssj());
        chasRygj.setJssj(syjl.getJssj());
        chasRygj.setBaqid(ryxx.getBaqid());
        chasRygj.setBaqmc(ryxx.getBaqmc());
        chasRygj.setRyid(ryxx.getId());
        chasRygj.setQyid("");
        chasRygj.setQymc("送押");
        chasRygj.setRybh(ryxx.getRybh());
        chasRygj.setWdbh(syjl.getWdbhH());
        chasRygj.setXm(ryxx.getRyxm());
        chasRygj.setRylx("xyr");
        if(StringUtils.isNotEmpty(ryxx.getMjXm())){
            chasRygj.setFzrxm(ryxx.getMjXm());
        }
        chasRygj.setSbid(syjl.getJzbh());
        chasYwRygjService.save(chasRygj);
    }
    private void endSygj(ChasYwClsyjl syjl){
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", syjl.getBaqid());
        map.put("rybh", syjl.getRybh());
        ChasRygj chasRygj =chasYwRygjService.findzhlocation(map);
        if(chasRygj!=null){
            ChasBaqryxx ryxx = chasBaqryxxService.findByRybh(syjl.getRybh());
            chasRygj.setXgsj(new Date());
            chasRygj.setJssj(syjl.getJssj());
            chasRygj.setXgrSfzh(ryxx.getMjSfzh());
            chasYwRygjService.update(chasRygj);
        }
    }

    private void processClxx(ChasYwClsyjl syjl){
        int count = clsyjlService.getClsyryslByjzbh(syjl.getJzbh());
        if(count<1){//无人使用
            ChasXtCldj cldj = cldjService.findById(syjl.getClid());
            if(cldj!=null&&"1".equals(cldj.getClsyzt())){
                cldj.setClsyzt("0");
                cldjService.update(cldj);
            }
        }
    }
}
