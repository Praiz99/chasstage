package com.wckj.chasstage.modules.fkgl.service.impl;

import com.alibaba.fastjson.JSON;
import com.wckj.api.def.zfba.model.ApiGgYwbh;
import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.server.device.IBrakeService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.fkgl.ApiFkglServiceImpl;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.dao.ChasYwFkdjMapper;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYwFkdjServiceImpl extends BaseService<ChasYwFkdjMapper, ChasYwFkdj> implements ChasYwFkdjService {

    @Autowired
    private IWdService iWdService;
    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ApiGgYwbhService jdoneComYwbhService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private HikFaceLocationService faceLocationService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private IBrakeService brakeService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    private static final Logger log = LoggerFactory.getLogger(ChasYwFkdjServiceImpl.class);

    @Override
    public String delete(String[] ids) {
        if (ids==null||ids.length==0){
            return "传入的参数为空";
        }
        for (String id : ids) {
            deleteById(id);
        }
        return SYSCONSTANT.N;
    }

    @Override
    public boolean saveOrUpdate(ChasYwFkdj fkdj)throws Exception {
        SessionUser user = WebContext.getSessionUser();
        String baqid = fkdj.getBaqid();
        BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
        if(StringUtils.isEmpty(fkdj.getId())){
            fkdj.setId(StringUtils.getGuid32());
            if(fkdj.getJrsj() == null){
                fkdj.setJrsj(new Date());
            }
            ApiGgYwbh comYwbh = null;

            try{
                comYwbh = jdoneComYwbhService.getYwbh("F",user.getOrgCode(),null);

                fkdj.setDataFlag("0");
                fkdj.setIsdel(0);
                fkdj.setLrrSfzh(user!=null?user.getIdCard():"");
                fkdj.setLrsj(new Date());
                fkdj.setXgrSfzh(user!=null?user.getIdCard():"");
                fkdj.setXgsj(new Date());
                fkdj.setRybh(comYwbh.getYwbh());
                fkdj.setZt("02"); //02 入区  03离区
                if(!configuration.getDwHkrl() && StringUtils.isNotEmpty(fkdj.getWdbhL())){
                    bindxk(fkdj);
                }
                save(fkdj);
                // 下发人脸
                if(configuration.getRyrsRqrlxf()){
                    new Thread(() -> {
                        brakeService.sendRyFaceInfo(fkdj);
                    }).start();
                }
                rygjService.manualAddRygj(fkdj);
                jdoneComYwbhService.ywbhCommit(comYwbh);
                return true;
            }catch (Exception e){
                if(!configuration.getDwHkrl()){
                    iWdService.wdJc(fkdj);
                }
                jdoneComYwbhService.ywbhRollBack(comYwbh);
                throw new Exception(e);
            }


        }else{
            ChasYwFkdj fkdj1 = findById(fkdj.getId());

            if(!configuration.getDwHkrl() && StringUtils.isNotEmpty(fkdj.getWdbhL())){
                if(!fkdj.getWdbhL().equals(fkdj1.getWdbhL())){//解除原胸卡
                    DevResult devResult = iWdService.wdJc(fkdj1);
                    if(devResult.getCode() != 1){
                        throw new Exception("解除手环失败:"+devResult.getMessage());
                    }
                    fkdj1.setWdbhL(fkdj.getWdbhL());
                    bindxk(fkdj1);
                }
            }
            fkdj.setWdbhH(null);
            MyBeanUtils.copyBeanNotNull2Bean(fkdj,fkdj1);
            update(fkdj1);

            if(StringUtils.equals(fkdj1.getZt(),SYSCONSTANT.MJRQ_CQ)){ //离区
                if(configuration.getDwHkrl()){
                    //结束人脸定位
                    baqryxxService.endRldw(fkdj1.getBaqid(), fkdj1.getRybh(), fkdj.getRegisterCode());
                }else{
                    //解除绑定胸卡
                    DevResult devResult = iWdService.wdJc(fkdj1);
                    if(devResult.getCode() != 1){
                        throw new Exception("解除手环失败:"+devResult.getMessage());
                    }
                }
                //删除下发的海康人脸门禁
                new Thread(() -> {
                    iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("F",fkdj.getId(),fkdj.getFkxm(),fkdj.getZpid(),fkdj.getBaqid(),new Date());
                }).start();
            }
            return true;
        }
    }

    private void bindxk(ChasYwFkdj fkdj) throws Exception {
        //查找胸卡
        Map<String,Object> params = new HashMap<>();
        params.put("wdbhL",fkdj.getWdbhL());
        params.put("baqid",fkdj.getBaqid());
        List<ChasSb> chasSb = chasSbService.findByParams(params);
        if(chasSb.isEmpty()){
            throw new Exception("胸卡不存在，不能绑定");
        }
        String wdbhH = chasSb.get(0).getKzcs1();
        fkdj.setWdbhH(wdbhH);
        //绑定胸卡
        if(StringUtils.isNotEmpty(fkdj.getWdbhL())){
            DevResult devResult = iWdService.wdBd(fkdj);
            if(devResult.getCode() != 1){
                throw new Exception("绑定手环失败:"+devResult.getMessage());
            }
        }

    }

    @Override
    public ChasYwFkdj findFkdjByRybh(String baqid, String rybh) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("rybh", rybh);
        List<ChasYwFkdj> list = baseDao.selectAll(map, null);
        if(list != null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public ChasYwFkdj isFkzq(String sfzh) {
        return baseDao.isFkzq(sfzh);
    }

    @Override
    public Map<String,Object> saveOrUpdateFkRldw(ChasYwFkdj chasYwFkdj){
        log.info("访客入区上传人脸定位信息：" + JSON.toJSONString(chasYwFkdj));
        HikFaceLocationRyxx locationRyxx = new HikFaceLocationRyxx();
        locationRyxx.setPlaceCode(chasYwFkdj.getBaqid());
        locationRyxx.setPersonId(chasYwFkdj.getRybh());
        locationRyxx.setPersonName(chasYwFkdj.getFkxm());
        locationRyxx.setCardType(chasYwFkdj.getZjlx());
        locationRyxx.setCardNo(chasYwFkdj.getFksfzh());
        locationRyxx.setPersonType("3");
        String bizId = chasYwFkdj.getZpid();
        FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
        locationRyxx.setPhotoUrl(fileInfoObj.getDownUrl());
        locationRyxx.setEnableFace(1);
        Map<String, Object> response = new HashMap<>();
        if(StringUtils.isNotEmpty(chasYwFkdj.getRegisterCode())){
            log.info(chasYwFkdj.getRybh() + "访客更新人脸定位信息");
            response = faceLocationService.updateHikLocationRyxx(locationRyxx, chasYwFkdj.getRegisterCode());
        }else{
            log.info(chasYwFkdj.getRybh() + "访客开始人脸定位信息");
            response = faceLocationService.startLocation(locationRyxx);
        }
        if((Boolean) response.get("status")){
            String glid = String.valueOf(response.get("registerCode"));
            chasYwFkdj.setRegisterCode(glid);
            update(chasYwFkdj);
        }
        return response;
    }
}
