package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;

import com.wckj.chasstage.api.server.device.IBrakeService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IBrakeServiceImpl extends BaseDevService implements IBrakeService {
    private static final Logger log = Logger.getLogger(IBrakeServiceImpl.class);
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Override
    public DevResult sendRyFaceInfo(ChasBaqryxx ryxx) {
        if(brakeOper==null){
            init();
        }
        if(ryxx==null){
            log.info("人员信息为空");
            return DevResult.error("人员信息不能为空");
        }
        if(StringUtils.isEmpty(ryxx.getZpid())){
            log.info("人员照片信息为空"+ryxx.getId());
            return DevResult.error("人员照片信息不能为空");
        }
        return iHikBrakeService.IssuedToBrakeByFaceAsyn("R", ryxx.getId(),ryxx.getRyxm(), ryxx.getZpid(), ryxx.getBaqid(),new Date());
    }

    @Override
    public DevResult sendRyFaceInfo(ChasYwMjrq mjrq) {
        log.info("sendRyFaceInfo--baqid:"+mjrq.getBaqid()+",mjName:"+mjrq.getMjxm());
        if(brakeOper==null){
            init();
        }
        if(mjrq==null){
            log.info("民警入区信息为空");
            return DevResult.error("民警入区信息为空");
        }
        if(StringUtil.isEmpty(mjrq.getZpid())){
            log.info("民警入区>>>人员照片信息为空"+mjrq.getId());
            return DevResult.error("人员照片信息不能为空");
        }
        return iHikBrakeService.IssuedToBrakeByFaceAsyn("M",mjrq.getId(),mjrq.getMjxm(),mjrq.getZpid(),mjrq.getBaqid(),new Date());
    }

    @Override
    public DevResult sendRyFaceInfo(ChasYwFkdj fkdj) {
        if(brakeOper==null){
            init();
        }
        if(fkdj==null){
            log.info("访客信息为空");
            return DevResult.error("访客信息为空");
        }
        if(StringUtil.isEmpty(fkdj.getZpid())){
            log.info("访客信息>>>人员照片信息为空"+fkdj.getId());
            return DevResult.error("人员照片信息不能为空");
        }
        return iHikBrakeService.IssuedToBrakeByFaceAsyn("F",fkdj.getId(),fkdj.getFkxm(),fkdj.getZpid(),fkdj.getBaqid(),new Date());
    }


}
