package com.wckj.chasstage.api.server.release.dc.service.impl;



import com.wckj.chasstage.api.server.release.dc.dto.ClcrInfo;
import com.wckj.chasstage.api.server.release.dc.service.IVehicleService;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.chasstage.modules.clcrjl.service.ChasYwClcrjlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IVehicleServiceImpl implements IVehicleService {
    private static Logger log = Logger.getLogger(IVehicleServiceImpl.class);
    @Autowired
    private ChasYwClcrjlService clcrjlService;
    @Autowired
    private ChasBaqService chasBaqService;
    @Override
    public void dealVehicleMessage(String org, ClcrInfo content) {
        try {
            ChasYwClcrjl clcrjl = new ChasYwClcrjl();
            ChasBaq baq = chasBaqService.findById(content.getOrg());
            clcrjl.setBaqid(content.getOrg());
            clcrjl.setBaqmc(content.getOrgName());
            clcrjl.setDwxtbh(baq.getDwxtbh());//用于做数据权限管理
            clcrjl.setDwmc(baq.getDwmc());
            clcrjl.setLrsj(new Date(content.getCreateDate()));
            clcrjl.setXgsj(new Date(content.getUpdateDate()));
            clcrjl.setClid(content.getId());
            clcrjl.setClBrand(content.getCarNumber());
            clcrjl.setClModel(content.getCarType());
            clcrjl.setClNumber(content.getLicense());
            clcrjl.setRqsj(new Date(content.getYxsjq()));
            clcrjl.setCqsj(new Date(content.getYxsjz()));
            clcrjl.setName(content.getCarName());

            clcrjlService.saveOrUpdate(clcrjl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理车辆出入记录事件出错"+e.getMessage());
        }
    }
}
