package com.wckj.chasstage.modules.mjgl.service.impl;

import com.alibaba.fastjson.JSON;
import com.wckj.api.def.zfba.model.ApiGgYwbh;
import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.chasstage.api.server.device.IBrakeService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.mjgl.ApiMjglServiceImpl;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.mjgl.dao.ChasYwMjrqMapper;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
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
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYwMjrqServiceImpl extends BaseService<ChasYwMjrqMapper, ChasYwMjrq> implements ChasYwMjrqService {
    private static final Logger log= Logger.getLogger(ChasYwMjrqServiceImpl.class);
    @Autowired
    private IWdService wdService;
    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ApiGgYwbhService jdoneComYwbhService;
    @Autowired
    private ChasYwYyService yyService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ApiRygjService apiRygjService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private HikFaceLocationService faceLocationService;
    @Autowired
    private IBrakeService brakeService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Override
    public boolean insert(ChasYwMjrq chasYwFkdj) throws Exception {
            // 新增
            ApiGgYwbh comYwbh = null;
            boolean result = false;
            String baqid = chasYwFkdj.getBaqid();
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
            try{
                if(configuration.getDwHkrl()){
                    log.info("开始录入民警入区信息"+chasYwFkdj.getWdbhL());
                }else{
                    log.info("开始录入民警入区信息，无胸卡");
                }
                ChasXtMjzpk mjzpk = mjzpkService.findBySfzh(chasYwFkdj.getMjsfzh());
                String dwdm = WebContext.getSessionUser() != null ? WebContext.getSessionUser().getOrgCode() : mjzpk.getDwdm();
                comYwbh = jdoneComYwbhService.getYwbh("M", dwdm,null);
                SessionUser user = WebContext.getSessionUser();
                chasYwFkdj.setRybh(comYwbh.getYwbh());
                chasYwFkdj.setId(StringUtils.getGuid32());
                chasYwFkdj.setIsdel(SYSCONSTANT.N_I);
                chasYwFkdj.setLrsj(new Date());
                chasYwFkdj.setXgsj(new Date());
                chasYwFkdj.setLrrSfzh(user!=null?user.getIdCard():"");
                chasYwFkdj.setJrsj(new Date());
                String xklx = chasYwFkdj.getXklx();
                if(StringUtils.isEmpty(xklx)){
                    chasYwFkdj.setXklx("2");//民警
                }else{
                    chasYwFkdj.setXklx(xklx);
                }
                String fwyy= DicUtil.translate("YYLX",chasYwFkdj.getFwyy() );
                chasYwFkdj.setFwyy(fwyy);
                chasYwFkdj.setZt(SYSCONSTANT.MJRQ_ZQ);
                if(StringUtil.isEmpty(chasYwFkdj.getXb())){
                    chasYwFkdj.setXb(calcxb(chasYwFkdj.getMjsfzh()));
                }
                if(!configuration.getDwHkrl() && StringUtil.isNotEmpty(chasYwFkdj.getWdbhL())){
                    bindXk(chasYwFkdj, chasYwFkdj.getWdbhL());
                }
                result = save(chasYwFkdj)>0;
                log.info("录入民警入区信息"+chasYwFkdj.getRybh()+"成功");
                rygjService.manualAddRygj(chasYwFkdj);
                log.info("录入民警轨迹信息"+chasYwFkdj.getRybh()+"成功");
                jdoneComYwbhService.ywbhCommit(comYwbh);
                // 下发人脸
                if(configuration.getRyrsRqrlxf()){
                    new Thread(() -> {
                        brakeService.sendRyFaceInfo(chasYwFkdj);
                    }).start();
                }
            }catch (Exception e){
                if(!configuration.getDwHkrl()){
                    wdService.wdJc(chasYwFkdj);
                }
                jdoneComYwbhService.ywbhRollBack(comYwbh);
                throw new Exception(e);
            }
            return result;
    }

    @Override
    public boolean edit(ChasYwMjrq chasYwFkdj) throws Exception {
        ChasYwMjrq ywFkdj = findById(chasYwFkdj.getId());
        if(ywFkdj != null) {
            BaqConfiguration configuration = baqznpzService.findByBaqid(ywFkdj.getBaqid());
            // 修改
            if(!configuration.getDwHkrl() && chasYwFkdj.getWdbhL()!=null&&
                    !chasYwFkdj.getWdbhL().equals(ywFkdj.getWdbhL())){
                bindXk(ywFkdj, chasYwFkdj.getWdbhL());
            }
            chasYwFkdj.setWdbhH(null);
            MyBeanUtils.copyBeanNotNull2Bean(chasYwFkdj, ywFkdj);
            ywFkdj.setXgsj(new Date());
            SessionUser user = WebContext.getSessionUser();
            ywFkdj.setXgrSfzh(user!=null?user.getIdCard():"");
            return update(ywFkdj)>0;
        }
        return false;
    }
    //绑定胸卡
    public void bindXk(ChasYwMjrq mjrq, String wdbhL) throws Exception{

        //String wdbhL = (String) form.get("wdbhL");  //低频
        //String wdbhH = (String) form.get("wdbhH");  //高频
        log.info("开始绑定胸卡"+wdbhL);
        String wd_ = mjrq.getWdbhL();
        if(StringUtils.isNotEmpty(wd_)&& StringUtils.isNotEmpty(wdbhL)&&
                    !StringUtils.equals(wdbhL,wd_)) {
            //解除原胸卡
            log.info("解绑原胸卡"+mjrq.getWdbhL());
            DevResult result = wdService.wdJc(mjrq);
            if (result.getCode() != 1) {
                throw new Exception("解除胸卡失败:" + result.getMessage());
            }
        }
        //查找胸卡
        log.info("查找胸卡"+wdbhL);
        Map<String,Object> params = new HashMap<>();
        params.put("wdbhL",wdbhL);
        params.put("baqid",mjrq.getBaqid());
        List<ChasSb> chasSb = chasSbService.findByParams(params);
        if(chasSb.isEmpty()){
            log.info("胸卡不存在"+wdbhL);
            throw new Exception("胸卡不存在，不能绑定");
        }

        String wdbhH = chasSb.get(0).getKzcs1();
        //修改民警胸卡编号
        mjrq.setWdbhL(wdbhL);
        mjrq.setWdbhH(wdbhH);
        log.info("绑定胸卡"+wdbhL);
        //绑定胸卡
        DevResult result = wdService.wdBd(mjrq);
        if(result.getCode() != 1){
            log.info("绑定胸卡失败"+wdbhL+result.getMessage());
            throw new Exception("绑定胸卡失败:"+result.getMessage());
        }
        log.info("绑定胸卡"+wdbhL+"成功");
    }

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
    public ChasYwMjrq findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }



    @Override
    public ChasYwMjrq findMjrqByRybh(String baqid, String rybh) {
        return findRyjlByRybh(baqid,rybh);
    }

    @Override
    public Map<String, Object> mjChuqu(String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("开始出区民警"+id);
            ChasYwMjrq mjrq = findById(id);
            String baqid = mjrq.getBaqid();
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
            if(mjrq == null){
                log.info("入区信息不存在"+id);
                result.put("success",false);
                result.put("message","入区信息不存在");
                return result;
            }
            if(!configuration.getDwHkrl() && StringUtil.isNotEmpty(mjrq.getWdbhL())){
                DevResult devResult = wdService.wdJc(mjrq);
                if(devResult.getCode() != 1){
                    log.info("胸卡解除失败"+id);
                    result.put("success",false);
                    result.put("message","胸卡解除失败");
                    return result;
                }
            }
            mjrq.setZt(SYSCONSTANT.MJRQ_CQ);
            mjrq.setCqsj(new Date());
            update(mjrq);
            log.info("出区民警"+mjrq.getRybh()+mjrq.getWdbhL()+"成功");
            Map<String, Object> map = new HashMap<>();
            map.put("baqid",mjrq.getBaqid());
            map.put("yyrsfzh", mjrq.getMjsfzh());
            map.put("crqzt","02");
            List<ChasYwYy> yyList = yyService.findByParams(map);
            if(yyList!=null&&!yyList.isEmpty()){
                for(ChasYwYy yy:yyList){
                    yy.setCrqzt("03");
                    yy.setCqsj(new Date());
                    yyService.update(yy);
                }
                log.info("修改预约信息"+id+"成功");
            }
            //ChasRygjmapService rygjmapService = ServiceContext.getServiceByClass(ChasRygjmapService.class);
            //rygjmapService.buildLocaltionlkInfo(mjrq.getRybh());
            map.clear();
            map.put("baqid",mjrq.getBaqid());
            map.put("rybh",mjrq.getRybh());
            ChasRygj rygj = rygjService.findzhlocation(map);
            if(rygj!=null){
                rygj.setJssj(new Date());
                rygjService.update(rygj);
                apiRygjService.sendLastVmsInfo(rygj.getBaqid(),rygj.getRybh(),rygj.getQyid());
            }
            new Thread(() -> {
                iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("M",mjrq.getId(),mjrq.getMjxm(),mjrq.getZpid(),mjrq.getBaqid(),new Date());
            }).start();
            result.put("success",true);
            result.put("message","出区成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("message","出区失败");
        }
        return result;
    }

    public ChasYwMjrq findRyjlByRybh(String baqid, String rybh) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("rybh", rybh);
        return findByParams(map);
    }

    @Override
    public int insertMjrq(ChasYwYy yy,String zpid) throws Exception{
        if(yy!=null){
            BaqConfiguration configuration = baqznpzService.findByBaqid(yy.getBaqid());
            ChasYwMjrq mjrq = new ChasYwMjrq();
            mjrq.setId(yy.getId());
            mjrq.setIsdel(0);
            mjrq.setDataFlag("");
            mjrq.setLrrSfzh(yy.getLrrSfzh());
            mjrq.setLrsj(new Date());
            mjrq.setJrsj(yy.getRqsj());
            mjrq.setCqsj(yy.getCqsj());
            mjrq.setRybh(yy.getRybh());
            mjrq.setZt(SYSCONSTANT.MJRQ_ZQ);
            if(!configuration.getDwHkrl()){
                //查找胸卡
                Map<String,Object> params = new HashMap<>();
                params.put("wdbhL",yy.getShbh());
                params.put("baqid",yy.getBaqid());
                List<ChasSb> chasSb = chasSbService.findByParams(params);
                if(chasSb.isEmpty()){
                    throw new Exception("胸卡不存在，不能绑定");
                }
                mjrq.setWdbhL(chasSb.get(0).getKzcs2());
                mjrq.setWdbhH(chasSb.get(0).getKzcs1());
                mjrq.setXklx("2");
            }
            mjrq.setBaqid(yy.getBaqid());
            mjrq.setBaqmc(yy.getBaqmc());
            String fwyy= DicUtil.translate("YYLX",yy.getYylx() );
            mjrq.setFwyy(fwyy);
            mjrq.setMjsfzh(yy.getYyrsfzh());
            mjrq.setMjxm(yy.getYyrxm());
            mjrq.setMjjh(yy.getYyrjh());
            mjrq.setZjlx("111");
            mjrq.setLxdh(yy.getTel());
            mjrq.setXb(calcxb(yy.getYyrsfzh()));
            mjrq.setFwsx(calcTime(yy.getRqsj(),yy.getCqsj()));
            mjrq.setZpid(zpid);
            rygjService.manualAddRygj(mjrq);
            // 下发人脸
            if(configuration.getRyrsRqrlxf()){
                new Thread(() -> {
                    brakeService.sendRyFaceInfo(mjrq);
                }).start();
            }
            return baseDao.insert(mjrq);
        }
        return 0;
    }
    //计算访问时限
    private short calcTime(Date start,Date end){
        if(end==null||start==null){
            return 0;
        }
        long from2 = start.getTime();
        long to2 = end.getTime();
        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
        if(hours<0){
            return 0;
        }
        return (short) hours;
    }
    public static String calcxb(String sfzh){
        if(StringUtils.isEmpty(sfzh)){
            return "1";
        }
        String sex = sfzh.substring(sfzh.length()-2, sfzh.length()-1); //取指定位置的值(16位之后,17位结束;)
       int i = Integer.parseInt(sex);
        return i%2==0?"2":"1";
    }

    @Override
    public ChasYwMjrq isMjzq(String sfzh) {
        return baseDao.isMjzq(sfzh);
    }

    @Override
    public Map<String,Object> saveOrUpdateMjRldw(ChasYwMjrq chasYwMjrq){
        log.info("民警入区上传人脸定位信息：" + JSON.toJSONString(chasYwMjrq));
        HikFaceLocationRyxx locationRyxx = new HikFaceLocationRyxx();
        locationRyxx.setPlaceCode(chasYwMjrq.getBaqid());
        locationRyxx.setPersonId(chasYwMjrq.getRybh());
        locationRyxx.setPersonName(chasYwMjrq.getMjxm());
        locationRyxx.setCardType(chasYwMjrq.getZjlx());
        locationRyxx.setCardNo(chasYwMjrq.getMjsfzh());
        locationRyxx.setPersonType("2");
        String bizId = chasYwMjrq.getZpid();
        FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
        locationRyxx.setPhotoUrl(fileInfoObj.getDownUrl());
        locationRyxx.setEnableFace(1);
        Map<String, Object> response = new HashMap<>();
        if(StringUtils.isNotEmpty(chasYwMjrq.getRegisterCode())){
            log.info(chasYwMjrq.getRybh() + "民警更新人脸定位信息");
            response = faceLocationService.updateHikLocationRyxx(locationRyxx, chasYwMjrq.getRegisterCode());
        }else{
            log.info(chasYwMjrq.getRybh() + "民警开始人脸定位信息");
            response = faceLocationService.startLocation(locationRyxx);
        }
        if((Boolean) response.get("status")){
            String glid = String.valueOf(response.get("registerCode"));
            chasYwMjrq.setRegisterCode(glid);
            update(chasYwMjrq);
        }
        return response;
    }
}
