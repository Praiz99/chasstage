package com.wckj.chasstage.api.server.imp.map;


import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.dao.ChasRygjSnapMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnapExt;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChasRygjmapService {

    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasYwRygjSnapService rygjSnapService;

    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    protected ChasXtQyService qyService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private ChasYwFkdjService fkdjService;
    @Autowired
    private ChasYjxxService chasYjxxService;
    @Autowired
    private ChasRygjSnapMapper snapMapper;






    private int getRysl(String baqid,String qyid){
        List<ChasRygj> rygjList = rygjService.selectrygjBysxs(baqid, qyid,null);
        if(rygjList != null){
            return rygjList.size();
        }
        return 0;
    }


    public Map<String, List<ChasRygjSnap>> getBaqRydwxx(String baqid){
        List<ChasRygjSnapExt> snapList = snapMapper.getRywzxxBybaqid(baqid);
        snapList.stream().forEach(snap->{
            if(StringUtils.isNotEmpty(snap.getZpid())){
                FileInfoObj fileInfo = FrwsApiForThirdPart.getFileInfoByBizId(snap.getZpid());
                if(Objects.nonNull(fileInfo)){
                    snap.setZpurl(fileInfo.getDownUrl());
                }
            }
        });
        return snapList.stream().collect(Collectors.groupingBy(ChasRygjSnap::getQyid));
    }
    public Map<String, List<ChasRygjSnap>> getBaqMjdwxx(String baqid){
        List<ChasRygjSnap> snapList = snapMapper.getMjwzxxBybaqid(baqid);
        return snapList.stream().collect(Collectors.groupingBy(ChasRygjSnap::getQyid));
    }
    public Map<String, List<ChasRygjSnap>> getBaqFkdwxx(String baqid){
        List<ChasRygjSnap> snapList = snapMapper.getFkwzxxBybaqid(baqid);
        return snapList.stream().collect(Collectors.groupingBy(ChasRygjSnap::getQyid));
    }
    public Map<String, List<ChasRygjSnap>> getBaqDjjdwxx(String baqid){
        List<ChasRygjSnap> snapList = snapMapper.getDjjwzxxBybaqid(baqid);
        return snapList.stream().collect(Collectors.groupingBy(ChasRygjSnap::getQyid));
    }
    private String getImageByqyid(String qyid) {

        return "";
    }
}
