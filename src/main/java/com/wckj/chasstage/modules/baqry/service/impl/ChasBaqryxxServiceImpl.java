package com.wckj.chasstage.modules.baqry.service.impl;

import com.alibaba.fastjson.JSON;
import com.wckj.api.def.zfba.model.*;
import com.wckj.api.def.zfba.service.gg.*;
import com.wckj.chasstage.api.def.face.model.FaceTzlx;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.chasstage.api.server.device.IBrakeService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.device.IWpgService;
import com.wckj.chasstage.api.server.imp.device.internal.ILocationOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.modules.ajxx.service.ChasYwAjxxService;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.dao.ChasBaqryxxMapper;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.njxx.entity.ChasYwNj;
import com.wckj.chasstage.modules.njxx.service.ChasYwNjService;
import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.chasstage.modules.pcry.service.ChasYwPcryService;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.chasstage.modules.sxdp.service.ChasSxdpService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.chasstage.modules.syrw.entity.ChasSyrw;
import com.wckj.chasstage.modules.syrw.service.ChasYwSyrwService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.chasstage.modules.ythcjqk.entity.ChasythcjQk;
import com.wckj.chasstage.modules.ythcjqk.service.ChasYthcjQkService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.msg.FrameworkMessageObj;
import com.wckj.framework.msg.MessageProduceCenter;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.act2.service.ProcessEngine2Service;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChasBaqryxxServiceImpl extends BaseService<ChasBaqryxxMapper, ChasBaqryxx> implements ChasBaqryxxService {

    private static final Logger log = Logger.getLogger(ChasBaqryxxServiceImpl.class);

    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private FaceInvokeService faceInvokeService;
    @Autowired
    private ApiGgYwbhService jdoneComYwbhService;
    @Autowired
    private ChasYmCsspService csspService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private IWdService iWdService;
    @Autowired
    private IWpgService iWpgService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasDhsKzService dhsKzService;
    @Autowired
    private ChasSxsKzService sxsKzService;
    @Autowired
    private ChasDhsglService dhsglService;
    @Autowired
    private ChasSxsglService sxsglService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasSxdpService sxdpService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private IBrakeService brakeService;
    @Autowired
    private ILedService iLedService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasYjxxService yjxxService;
    @Autowired
    private ChasSswpxxService sswpxxService;
    @Autowired
    private ChasYwAjxxService ajxxService;
    @Autowired
    private ChasYwSyrwService syrwService;
    @Autowired
    private ProcessEngine2Service processEngineService;
    @Autowired
    private ChasSswpZpxxService zpxxService;
    @Autowired
    private ChasQqyzService qqyzService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasYthcjQkService ythcjQkService;
    @Autowired
    private ChasYwNjService njService;
    @Autowired
    private ChasYwPcryService pcryService;
    @Autowired
    private ApiRygjService apiRygjService;
    @Autowired
    private ChasXtGnpzService chasXtGnpzService;
    @Autowired
    private HikFaceLocationService faceLocationService;
    @Autowired
    private BaqFaceService baqFaceService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> SaveWithUpdate(RyxxBean ryxxBean) throws Exception {
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxxBean.getBaqid());
        //未启用海康人脸
        if(StringUtil.isEmpty(ryxxBean.getId()) && !configuration.getDwHkrl()){
            if(!validateRyWd(ryxxBean.getBaqid(),ryxxBean.getWdbhL())){
                throw new Exception("该手环被占用,请联系管理员!");
            }
        }
        //参数转换
        ChasBaqryxx baqryxx = new ChasBaqryxx();
        MyBeanUtils.copyBeanNotNull2Bean(ryxxBean,baqryxx);

        SessionUser user = WebContext.getSessionUser();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(user == null){  //可能是接口调用,所以会为空
            JdoneSysUser user1 = userService.findSysUserByIdCard(baqryxx.getMjSfzh());
            user = new SessionUser();
            user.setOrgCode(user1.getOrgCode());
            user.setOrgSysCode(user1.getOrgSysCode());
            user.setIdCard(user1.getIdCard());
            user.setName(user1.getName());
        }

        Map<String,Object> result = new HashMap<>();
        log.info("保存办案区人员-SaveWithUpdate:开始执行耗时:"+format.format(new Date()));

        String cwglx = ryxxBean.getCwglx();
        String cwgbh = ryxxBean.getCwgbh();
        String sjgbh = ryxxBean.getSjgBh();
        String sjgid = ryxxBean.getSjgId();
        String jagl = ryxxBean.getYwbhStr();

        if(StringUtils.isNotEmpty(baqryxx.getId())){  //修改
            ChasBaqryxx baqryxx1 = findById(baqryxx.getId());
            String his_mj = baqryxx1.getMjSfzh();
            MyBeanUtils.copyBeanNotNull2Bean(baqryxx,baqryxx1);

            if(StringUtils.isNotEmpty(jagl) && jagl.startsWith("J")){
                baqryxx1.setJqbh(jagl);
                baqryxx1.setAjbh("");
            }
            if(StringUtils.isNotEmpty(jagl) && jagl.startsWith("A")){
                baqryxx1.setAjbh(jagl);
                baqryxx1.setJqbh("");
            }
            if(StringUtil.isEmpty(jagl)){
                baqryxx1.setAjbh("");
                baqryxx1.setJqbh("");
            }

            //是否更换主办民警  (已更换)
            if(!StringUtil.equals(baqryxx.getMjSfzh(),his_mj)){
                if(StringUtil.isNotEmpty(baqryxx.getMjSfzh())){
                    JdoneSysUser sysUser = userService.findSysUserByIdCard(baqryxx.getMjSfzh());
                    if(sysUser != null){
                        baqryxx1.setZbdwBh(sysUser.getOrgCode());
                        baqryxx1.setZbdwMc(sysUser.getOrgName());
                        baqryxx1.setDwxtbh(sysUser.getOrgSysCode());
                        SessionUser sessionUser = WebContext.getSessionUser();
                        baqryxx1.setTjxgsj(new Date());
                        baqryxx1.setTjxgrsfzh(sessionUser.getIdCard());
                    }
                }
            }
            //修改人脸特征
            faceInvokeService.saveBaqryFeature(baqryxx1.getZpid(), baqryxx1.getBaqid(), baqryxx1.getDwxtbh(), baqryxx1.getRybh());
            baqryxx1.setSfznbaq(1);
            update(baqryxx1);
            baqryxx = baqryxx1;
            log.info("保存办案区人员-SaveWithUpdate:修改业务逻辑执行耗时:"+format.format(new Date()));
        }else{  //新增
            if(StringUtils.isNotEmpty(baqryxx.getRysfzh())){
                if(findByRysfzh(baqryxx.getRysfzh()) != null){
                    throw new Exception("证件号码为:"+baqryxx.getRysfzh()+"的人员:"+baqryxx.getRyxm()+",已在所!");
                }
            }

            if (!StringUtils.equals(cwglx,"1") && !"0".equals(cwgbh)) {
                baqryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WCL);
            } else {
                if(StringUtils.isNotEmpty(sjgbh) && StringUtils.isNotEmpty(sjgid)){
                    baqryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WCL);
                }else if (baqryxx.getSswpzt() == null) {
                    baqryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WU);
                }
            }

            ApiGgYwbh comYwbh = null;
            //办案区人员的业务编号与 人员记录表的业务编号区分开
            if(StringUtils.isNotEmpty(jagl) && jagl.startsWith("J")){
                baqryxx.setJqbh(jagl);
            }
            if(StringUtils.isNotEmpty(jagl) && jagl.startsWith("A")){
                baqryxx.setAjbh(jagl);
            }

            try{
                comYwbh = jdoneComYwbhService.getYwbh("R",user.getOrgCode(),null);
                baqryxx.setId(StringUtils.getGuid32());
                baqryxx.setLrrSfzh(user.getIdCard());
                baqryxx.setLrsj(new Date());
                baqryxx.setTjxgrsfzh(user.getIdCard());
                baqryxx.setTjxgsj(new Date());
                baqryxx.setIsdel(0);
                baqryxx.setDataflag("0");
                baqryxx.setRybh(comYwbh.getYwbh());
                ryxxBean.setRybh(baqryxx.getRybh());
                baqryxx.setRyzt(SYSCONSTANT.BAQRYZT_ZS);
                if(StringUtil.isEmpty(baqryxx.getMjSfzh())){
                    throw new Exception("主办民警身份证号码不能为空!");
                }
                String mjsfzh = baqryxx.getMjSfzh();
                //取主办民警的所属机构信息
                JdoneSysUser sysUser = userService.findSysUserByIdCard(mjsfzh);
                baqryxx.setZbdwBh(sysUser.getOrgCode());
                baqryxx.setZbdwMc(sysUser.getOrgName());
                baqryxx.setDwxtbh(sysUser.getOrgSysCode());
                baqryxx.setSfythcj(0);
                baqryxx.setSftb(0);
                baqryxx.setSfznbaq(1);
                baqryxx.setNl(0);
                if(!StringUtil.equals(baqryxx.getRyzaybh(),SYSCONSTANT.DAFS_QT)){  //其他
                    baqryxx.setRyzaymc(DicUtil.translate("RSYY",baqryxx.getRyzaybh()));
                }
                if(baqryxx.getCsrq() != null){  //设置出生日期
                    baqryxx.setNl(DateTimeUtils.getAge(baqryxx.getCsrq()));
                }

                baqryxx.setBaqsyqkbh(getbaqjlbh(baqryxx.getBaqid(),baqryxx.getRRssj()));
                //保存人脸特征
                faceInvokeService.saveBaqryFeature(baqryxx.getZpid(), baqryxx.getBaqid(), baqryxx.getDwxtbh(), baqryxx.getRybh());
                // 下发人脸
                if(configuration.getRyrsRqrlxf()){
                    ChasBaqryxx finalryxx = baqryxx;
                    new Thread(() -> {
                        brakeService.sendRyFaceInfo(finalryxx);
                    }).start();
                }
                save(baqryxx);
                jdoneComYwbhService.ywbhCommit(comYwbh);
            }catch (Exception e){
                jdoneComYwbhService.ywbhRollBack(comYwbh);
                throw new Exception(e);
            }

            sendMessageTozfba(baqryxx);
            ryRsCsSendMsg(baqryxx);
            log.info("保存办案区人员-SaveWithUpdate:新增业务逻辑执行耗时:"+format.format(new Date()));
        }

        log.info("保存办案区人员-SaveWithUpdate:出所审批保存开始:"+format.format(new Date()));
        //保存cssp
        SaveWithUpdateCssp(baqryxx);
        log.info("保存办案区人员-SaveWithUpdate:出所审批保存结束:"+format.format(new Date()));
        log.info("保存办案区人员-SaveWithUpdate:人员轨迹保存开始:"+format.format(new Date()));
        //保存ryjl
        result = SaveWithUpdateRyjl(baqryxx,ryxxBean);
        log.info("保存办案区人员-SaveWithUpdate:人员轨迹保存结束:"+format.format(new Date()));

        relationYwbh(jagl,baqryxx);
        result.put("baqryxx",baqryxx);
        return result;
    }

    private String getbaqjlbh(String baqid, Date rssj) {
        String bh = DateTimeUtils.getDateStr(rssj, 17);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", baqid);
        params.put("baqsyqkbh", bh);
        if(StringUtils.isEmpty(baqid) || StringUtils.isEmpty(bh)){
            return "";
        }
        ChasBaqryxx baqryxx = baseDao.getBaqryBybaqid(baqid,bh);
        if (baqryxx == null) {
            bh += "0001";
        } else {
            bh = (Long.valueOf((String) baqryxx.getBaqsyqkbh()) + 1)+ "";
        }
        return bh;
    }

    public void SaveWithUpdateCssp(ChasBaqryxx ryxx){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", ryxx.getBaqid());
        params.put("rybh", ryxx.getRybh());
        params.put("lklx", SYSCONSTANT.LKLX_RS);
        List<ChasYmCssp> cssps = csspService.findList(params, null);
        ChasYmCssp cssp = null;
        if (cssps == null || cssps.size() == 0) {
            cssp = new ChasYmCssp();
            cssp.setLrrSfzh(WebContext.getSessionUser() == null ? ryxx
                    .getMjSfzh() : WebContext.getSessionUser().getIdCard());
            cssp.setLrsj(new Date());
            cssp.setXgrSfzh(WebContext.getSessionUser() == null ? ryxx
                    .getMjSfzh() : WebContext.getSessionUser().getIdCard());
            cssp.setXgsj(new Date());
        } else {
            cssp = cssps.get(0);
        }
        cssp.setBaqid(ryxx.getBaqid());
        cssp.setBaqmc(ryxx.getBaqmc());
        cssp.setRybh(ryxx.getRybh());
        cssp.setRyxm(ryxx.getRyxm());
        cssp.setHlsj(ryxx.getRRssj());
        cssp.setZbdwBh(ryxx.getZbdwBh());
        cssp.setZbdwMc(ryxx.getZbdwMc());
        cssp.setDwxtbh(ryxx.getDwxtbh());
        cssp.setZbrSfzh(ryxx.getMjSfzh());
        cssp.setLkyydm(ryxx.getRyzaybh());
        if (StringUtils.isNotEmpty(ryxx.getRyzaymc())) {
            cssp.setLkyy(ryxx.getRyzaymc());
        } else {
            cssp.setLkyy(DicUtil.translate("RSYY", ryxx.getRyzaybh()));
        }
        cssp.setZbrSfzh(ryxx.getMjSfzh());
        cssp.setZbrXm(ryxx.getMjXm());
        cssp.setSpzt(SYSCONSTANT.N);
        cssp.setLklx(SYSCONSTANT.LKLX_RS);
        if (cssps == null || cssps.size() == 0) {
            cssp.setId(StringUtils.getGuid32());
            cssp.setDwxtbh(ryxx.getDwxtbh());
            csspService.save(cssp);
            BaqConfiguration configuration = baqznpzService.findByBaqid(cssp.getBaqid());
            if(configuration != null){
                if(configuration.getQgc()){
                    if(ryxx.getAqjckssj() != null && ryxx.getAqjcjssj() != null){
                        BaqrysypUtil.uploadSaveAudioVideoByAqjc(ryxx,"2","303");  //新增安全检查视音频
                    }
                    BaqrysypUtil.uplodsaveAudioVideoByRyjc(ryxx,"6","300");
                    log.info("更改办案区，新增入区音视频!"+ryxx.getRyxm()+",办案区ID:"+ryxx.getBaqid());
                }
            }
        } else {
            csspService.update(cssp);
        }
    }

    public Map<String,Object> SaveWithUpdateRyjl(ChasBaqryxx baqryxx,RyxxBean ryxxBean) throws Exception{
        BaqConfiguration baqznpz = baqznpzService.findByBaqid(baqryxx.getBaqid());
        ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(),baqryxx.getRybh());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String,Object> result_ = new HashMap<>();

        String tary_ywbh = ryxxBean.getTarybh();  //同案人员虚拟业务编号
        String wdbhL = ryxxBean.getWdbhL();  //低频
        String wdbhH = ryxxBean.getWdbhH();  //高频
        String cwgid = ryxxBean.getCwgid();
        String cwglx = ryxxBean.getCwglx();
        String cwgbh = ryxxBean.getCwgbh();
        String sjgId = ryxxBean.getSjgId();
        String sjgBh = ryxxBean.getSjgBh();
        String ddkg = ryxxBean.getDdkg();  //单独看管

        if(ryjl != null){
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:修改业务逻辑耗时:"+format.format(new Date()));
            if(!baqznpz.getDwHkrl()){//未启用人脸定位走手环逻辑
                String wd_ = ryjl.getWdbhL();
                if(!StringUtils.equals(wdbhL,wd_) && StringUtils.isNotEmpty(wdbhL)){  //如果腕带已更改,那么则更新人员记录信息
                    DevResult result = null;
                    if(baqznpz.getDw()){
                        //解除腕带
                        result = iWdService.wdJc(ryjl);
                        if(result.getCode() != 1){
                            throw new Exception("解除手环失败:"+result.getMessage());
                        }
                    }
                    //修改腕带编号
                    Map<String,Object> params = new HashMap<>();
                    params.put("wdbhL",wdbhL);
                    params.put("baqid",baqryxx.getBaqid());
                    List<ChasSb> chasSb = sbService.findByParams(params);
                    if(!chasSb.isEmpty()){
                        wdbhH = chasSb.get(0).getKzcs1();
                    }
                    ryjl.setWdbhL(wdbhL);
                    ryjl.setWdbhH(wdbhH);
                    ryjl.setXm(baqryxx.getRyxm());
                    ryjlService.update(ryjl);
                    if(baqznpz.getDw()){
                        //重新绑定
                        result = iWdService.wdBd(ryjl);
                        if(result.getCode() != 1){
                            throw new Exception("重新绑定手环失败:"+result.getMessage());
                        }
                    }

                    //如果更新了人员姓名
                    dhsglService.dhsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                    sxsglService.sxsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                }
            }else{
                ryjl.setXm(baqryxx.getRyxm());
                ryjlService.update(ryjl);

                //如果更新了人员姓名
                dhsglService.dhsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                sxsglService.sxsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
            }


            //储物柜更改
            if(baqznpz.getSswp()){
                if(!StringUtil.equals(ryjl.getCwgId(),cwgid) && StringUtils.isNotEmpty(cwgid)){
                    String history_cwgbh = ryjl.getCwgBh();
                    //启动储物柜后
                    DevResult result = new DevResult();
                    if(StringUtil.isNotEmpty(history_cwgbh) && !StringUtil.equals(history_cwgbh,"0")){
                        //解绑储物柜
                        result = iWpgService.jcCwg(ryjl.getBaqid(),ryjl.getRybh());
                        if(result.getCode() != 1){
                            throw new Exception("解除储物柜失败:"+result.getMessage());
                        }
                    }
                    //绑定储物柜
                    if(StringUtils.isNotEmpty(cwgbh) && !StringUtils.equals(cwgbh,"0")){
                        result = iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                        if(result.getCode() != 1){
                            throw new Exception("绑定储物柜失败:"+result.getMessage());
                        }
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh, "1");
                        if(result.getCode() != 1){
                            //重新绑定原有储物柜
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                            iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh(),ryjl.getCwgBh());
                            throw new Exception("打开储物柜失败:"+result.getMessage());
                        }
                    }
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setCwgId(cwgid);
                    ryjl.setCwgLx(cwglx);
                    ryjl.setXm(baqryxx.getRyxm());
                    //ryjlService.update(ryjl);
                }
            }

            //判断手机柜是否已更改
            if(baqznpz.getPhonecab()){
                if(!StringUtil.equals(ryjl.getSjgId(),sjgId) && StringUtils.isNotEmpty(sjgId)){
                    String history_sjgbh = ryjl.getSjgBh();
                    //启动储物柜后
                    DevResult result = new DevResult();
                    if(StringUtil.isNotEmpty(history_sjgbh) && !StringUtil.equals(history_sjgbh,"0")){
                        //解绑手机柜
                        result = iWpgService.jcSjg(ryjl.getBaqid(),ryjl.getRybh());
                        if(result.getCode() != 1){
                            throw new Exception("解除手机柜失败:"+result.getMessage());
                        }
                    }

                    //绑定手机柜
                    if(StringUtils.isNotEmpty(sjgBh) && !StringUtils.equals(sjgBh,"0")){
                        result = iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh);
                        if(result.getCode() != 1){
                            throw new Exception("绑定手机柜失败:"+result.getMessage());
                        }
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh, "40");
                        if(result.getCode() != 1){
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh);
                            iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh(),ryjl.getSjgBh());
                            throw new Exception("打开手机柜失败:"+result.getMessage());
                        }
                    }
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setSjgId(sjgId);
                }
            }

            //同步修改人员姓名和性别信息
            if(!StringUtil.equals(ryjl.getXm(),baqryxx.getRyxm()) || !StringUtil.equals(ryjl.getXb(),baqryxx.getXb())){
                ryjl.setXm(baqryxx.getRyxm());
                ryjl.setXb(baqryxx.getXb());
                //同步修改等候室\审讯室 人员基本信息
                if(StringUtil.isNotEmpty(ryjl.getSxsBh())){
                    ChasSxsKz sxsKz = sxsKzService.findById(ryjl.getSxsBh());
                    if(sxsKz != null){
                        if(!StringUtil.equals(sxsKz.getRyxm(),baqryxx.getRyxm())){
                            sxsKzService.updateRyxmByRybh(baqryxx.getRyxm(),baqryxx.getRybh());
                        }
                    }
                }
                if(StringUtil.isNotEmpty(ryjl.getDhsBh())){
                    ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                    if(dhsKz != null){
                        if(!StringUtil.equals(dhsKz.getRyxm(),baqryxx.getRyxm()) || !StringUtil.equals(dhsKz.getRyxb(),baqryxx.getXb())){
                            dhsKzService.updateXmXbByRybh(baqryxx.getRyxm(),baqryxx.getXb(),baqryxx.getRybh());
                        }
                    }
                }
            }
            if(StringUtil.isNotEmpty(baqryxx.getJqbh())){
                ryjl.setYwbh(baqryxx.getJqbh());
                ryjl.setAjbh("");
            }
            if(StringUtil.isNotEmpty(baqryxx.getAjbh())){
                ryjl.setAjbh(baqryxx.getAjbh());
                ryjl.setYwbh(baqryxx.getAjbh());
            }
            ryjlService.update(ryjl);
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:修改业务逻辑耗时:"+format.format(new Date()));
        }else{
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:保存业务逻辑耗时:"+format.format(new Date()));
            DevResult result = null;
            if(!baqznpz.getDwHkrl()){//未启用人脸定位走手环逻辑
                Map<String,Object> params = new HashMap<>();
                params.put("wdbhL",wdbhL);
                params.put("baqid",baqryxx.getBaqid());
                List<ChasSb> chasSb = sbService.findByParams(params);
                if(!chasSb.isEmpty()){
                    wdbhH = chasSb.get(0).getKzcs1();
                }

                ApiGgYwbh comYwbh = null;

                try{
                    boolean flag = true;  //是否使用了业务编号

                    comYwbh = jdoneComYwbhService.getYwbh("Y",baqryxx.getZbdwBh(),null);
                    ryjl = new ChasRyjl();
                    ryjl.setLrrSfzh(baqryxx.getLrrSfzh());
                    ryjl.setLrsj(new Date());
                    ryjl.setXgsj(new Date());
                    ryjl.setXgrSfzh(baqryxx.getTjxgrsfzh());
                    ryjl.setId(StringUtils.getGuid32());
                    ryjl.setBaqid(baqryxx.getBaqid());
                    ryjl.setBaqmc(baqryxx.getBaqmc());
                    ryjl.setRybh(baqryxx.getRybh());
                    ryjl.setWdbhL(wdbhL);
                    ryjl.setWdbhH(wdbhH);
                    ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JXZ);
                    if(StringUtils.isEmpty(tary_ywbh)){  //如果页面提交了同安人员编号，那么就不使用虚拟值
                        if(StringUtils.isNotEmpty(baqryxx.getAjbh()) || StringUtils.isNotEmpty(baqryxx.getJqbh())){  //如果该人员关联了警案信息，那么就使用同一编号
                            if(StringUtils.isNotEmpty(baqryxx.getAjbh())){
                                ryjl.setAjbh(baqryxx.getAjbh());
                                ryjl.setYwbh(baqryxx.getAjbh());
                            }
                            if(StringUtils.isNotEmpty(baqryxx.getJqbh())){
                                ryjl.setYwbh(baqryxx.getJqbh());
                            }
                            flag = false;
                        }else{
                            ryjl.setYwbh(comYwbh.getYwbh());
                        }
                    }else{
                        ryjl.setYwbh(tary_ywbh);
                        flag = false;
                    }
                    ryjl.setCwgId(cwgid);
                    ryjl.setCwgLx(cwglx);
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setCfsj(new Date());
                    ryjl.setXb(baqryxx.getXb());
                    ryjl.setXm(baqryxx.getRyxm());
                    ryjl.setRylb(baqryxx.getRylx());
                    ryjl.setSjgId(sjgId);
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setSftayj(1);
                    ryjl.setLczt(SYSCONSTANT.LCJD_XXDJ_QZ);  //流程节点信息登记
                    if(StringUtil.isEmpty(ryjl.getYwbh())){
                        flag = true;
                        ryjl.setYwbh(comYwbh.getYwbh());
                    }
                    ryjlService.save(ryjl);  //必须在这里进行保存,否则在分配等候室时就无法查询出数据

                    if(flag){
                        jdoneComYwbhService.ywbhCommit(comYwbh);
                    }else{
                        jdoneComYwbhService.ywbhRollBack(comYwbh);
                    }
                }catch (Exception e){
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    throw new Exception(e);
                }


                if(baqznpz.getDw()){
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手环开始:"+format.format(new Date()));
                    //绑定手环
                    result = iWdService.wdBd(ryjl);
                    if(result.getCode() != 1){
                        throw new Exception("绑定手环失败:"+result.getMessage());
                    }
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手环结束:"+format.format(new Date()));
                }
            }else{
                ApiGgYwbh comYwbh = null;

                try{
                    boolean flag = true;  //是否使用了业务编号

                    comYwbh = jdoneComYwbhService.getYwbh("Y",baqryxx.getZbdwBh(),null);
                    ryjl = new ChasRyjl();
                    ryjl.setLrrSfzh(baqryxx.getLrrSfzh());
                    ryjl.setLrsj(new Date());
                    ryjl.setXgsj(new Date());
                    ryjl.setXgrSfzh(baqryxx.getTjxgrsfzh());
                    ryjl.setId(StringUtils.getGuid32());
                    ryjl.setBaqid(baqryxx.getBaqid());
                    ryjl.setBaqmc(baqryxx.getBaqmc());
                    ryjl.setRybh(baqryxx.getRybh());
                    ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JXZ);
                    if(StringUtils.isEmpty(tary_ywbh)){  //如果页面提交了同安人员编号，那么就不使用虚拟值
                        if(StringUtils.isNotEmpty(baqryxx.getAjbh()) || StringUtils.isNotEmpty(baqryxx.getJqbh())){  //如果该人员关联了警案信息，那么就使用同一编号
                            if(StringUtils.isNotEmpty(baqryxx.getAjbh())){
                                ryjl.setAjbh(baqryxx.getAjbh());
                                ryjl.setYwbh(baqryxx.getAjbh());
                            }
                            if(StringUtils.isNotEmpty(baqryxx.getJqbh())){
                                ryjl.setYwbh(baqryxx.getJqbh());
                            }
                            flag = false;
                        }else{
                            ryjl.setYwbh(comYwbh.getYwbh());
                        }
                    }else{
                        ryjl.setYwbh(tary_ywbh);
                        flag = false;
                    }
                    ryjl.setCwgId(cwgid);
                    ryjl.setCwgLx(cwglx);
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setCfsj(new Date());
                    ryjl.setXb(baqryxx.getXb());
                    ryjl.setXm(baqryxx.getRyxm());
                    ryjl.setRylb(baqryxx.getRylx());
                    ryjl.setSjgId(sjgId);
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setSftayj(1);
                    ryjl.setLczt(SYSCONSTANT.LCJD_XXDJ_QZ);  //流程节点信息登记
                    if(StringUtil.isEmpty(ryjl.getYwbh())){
                        flag = true;
                        ryjl.setYwbh(comYwbh.getYwbh());
                    }
                    ryjlService.save(ryjl);  //必须在这里进行保存,否则在分配等候室时就无法查询出数据

                    if(flag){
                        jdoneComYwbhService.ywbhCommit(comYwbh);
                    }else{
                        jdoneComYwbhService.ywbhRollBack(comYwbh);
                    }
                }catch (Exception e){
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    throw new Exception(e);
                }
            }


            if(baqznpz.getSswp()){
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定储物柜开始:"+format.format(new Date()));
                //绑定储物柜
                if(StringUtils.isNotEmpty(ryjl.getCwgBh()) && !StringUtils.equals(ryjl.getCwgBh(),"0")){
                    //校验是否已经被占用
                    ChasRyjl locker = ryjlService.findUsedLockerById(ryjl.getCwgId());
                    if(locker != null && !StringUtil.equals(locker.getRybh(),ryjl.getRybh())){  //该储物柜已被占用，重新分配
                        DevResult lockerResult = null;
                        if(baqznpz.getRyrsDxg()){  //如果使用大小柜,那么就带入大小柜类型
                            lockerResult = iWpgService.fpCwg(ryjl.getBaqid(), cwglx);
                        }else{
                            lockerResult = iWpgService.fpCwg(ryjl.getBaqid(), "");
                        }
                        if(lockerResult.getCode() == 1){  //预分配成功
                            String bh = (String) lockerResult.getData().get("boxNo");
                            String id = (String) lockerResult.getData().get("cabid");
                            ryjl.setCwgBh(bh);
                            ryjl.setCwgId(id);
                            cwgbh = ryjl.getCwgBh();
                            ryjlService.update(ryjl);
                        }else{
                            throw new Exception("分配储物柜失败:无空闲柜子!");
                        }
                    }
                    result = iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh());
                    if(result.getCode() != 1){
                        //保存储物柜失败后，需要解除已绑定的手环
                        iWdService.wdJc(ryjl);
                        throw new Exception("绑定储物柜失败:"+result.getMessage());
                    }
                    //打开储物柜
                    if(baqznpz.getSswpZjkg()){
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh, "1");
                        if(result.getCode() != 1){
                            iWdService.wdJc(ryjl);
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                            throw new Exception("打开储物柜失败:"+result.getMessage());
                        }
                    }
                }
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定储物柜结束:"+format.format(new Date()));
            }

            //绑定手机柜
            if(baqznpz.getPhonecab()){
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手机柜开始:"+format.format(new Date()));
                if(StringUtils.isNotEmpty(ryjl.getSjgBh()) && !StringUtils.equals(ryjl.getSjgBh(),"0")){
                    //校验该手机柜是否被占用
                    ChasRyjl locker = ryjlService.findUsedLockerById(ryjl.getSjgId());
                    if(locker != null && !StringUtil.equals(locker.getRybh(),ryjl.getRybh())){  //表示已被占用
                        DevResult lockerResult = iWpgService.fpSjg(ryjl.getBaqid(), "");
                        if(lockerResult.getCode() == 1){  //预分配成功
                            String bh = (String) lockerResult.getData().get("boxNo");
                            String id = (String) lockerResult.getData().get("cabid");
                            ryjl.setSjgBh(bh);
                            ryjl.setSjgId(id);
                            sjgBh = ryjl.getSjgBh();
                            ryjlService.update(ryjl);
                        }else{
                            throw new Exception("分配手机柜失败:无空闲柜子!");
                        }
                    }
                    result = iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh());
                    if(result.getCode() != 1){
                        iWdService.wdJc(ryjl);
                        iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                        throw new Exception("绑定手机柜失败:"+result.getMessage());
                    }
                    //打开手机柜
                    if(baqznpz.getSswpZjkg()){
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh, "40");
                        if(result.getCode() != 1){
                            iWdService.wdJc(ryjl);
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh);
                            throw new Exception("打开手机柜失败:"+result.getMessage());
                        }
                    }
                }
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手机柜结束:"+format.format(new Date()));
            }

            if(baqznpz.getDhsDjym()){
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:分配等候室开始:"+format.format(new Date()));
                //分配等候室
                if(StringUtil.equals(ddkg,"true")){  //单独看管
                    result = dhsglService.aloneAssign(baqryxx.getBaqid(),baqryxx.getRybh(),"");
                }else{
                    result = dhsglService.dhsFp(baqryxx.getBaqid(),baqryxx.getRybh(),"");
                }
                if(result.getCode() != 1 && result.getCode() != 3){
                    iWdService.wdJc(ryjl);
                    iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                    iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh);
                    throw new Exception("等候室分配失败:"+result.getMessage());
                }
                if(result.getCode() == 3){
                    result_.put("tips",result.getMessage());
                }
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:分配等候室结束:"+format.format(new Date()));
            }

            if(baqznpz.getJhrwRqjh()){
                String qymc = "看守区";
                ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                if(dhsKz != null){
                    ChasXtQy qy = qyService.findByYsid(dhsKz.getQyid());
                    if(qy != null){
                        qymc = qy.getQymc();
                    }
                }
                //开启戒护任务
                DevResult devResult = jhrwService.saveJhrw(baqryxx.getMjXm(),baqryxx.getZbdwBh(),baqryxx,SYSCONSTANT.JHRWLX_RQJH,"办案区入口",qymc);
                if(devResult.getCode() != 0){
                    throw new Exception("开启戒护任务失败:"+devResult.getMessage());
                }
            }
            if(baqznpz.getDdrwSxdd()){
                //审讯大屏
                ChasSxdp sxdp = new ChasSxdp();
                sxdp.setXyr(baqryxx.getRybh());
                sxdp.setXyrxm(baqryxx.getRyxm());
                sxdpService.createSx(sxdp);
            }
            log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:保存业务逻辑耗时:"+format.format(new Date()));
        }
        return result_;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> SaveWithUpdateByForm(RyxxBean param) throws Exception {
        BaqConfiguration configuration = baqznpzService.findByBaqid(param.getBaqid());
        //未启用人脸定位
        if(StringUtil.isEmpty(param.getId()) && !configuration.getDwHkrl()){
            if(!validateRyWd(param.getBaqid(),param.getWdbhL())){
                throw new Exception("该手环被占用,请联系管理员!");
            }
        }
        Map<String,Object> result = new HashMap<>();
        ChasBaqryxx baqryxx = new ChasBaqryxx();
        MyBeanUtils.copyBeanNotNull2Bean(param,baqryxx);

        ChasBaqryxx ryxx = getRyxxObj(baqryxx);
        SessionUser user = WebContext.getSessionUser();
        String id = ryxx.getId();
        // 智能办案区的数据
        String cwglx = param.getCwglx();
        String cwgbh = param.getCwgbh();
        String ywbhStr = param.getYwbhStr();  //警案编号
        String rs_wp_all_bizId = param.getRsWpAllBizId();  //物品总照片ID
        if (StringUtils.isEmpty(id)) { // 新增
            ryxx.setRyzt(SYSCONSTANT.BAQRYZT_ZS);
            ryxx.setId(StringUtils.getGuid32());
            if (ryxx.getSfythcj() == null) {
                ryxx.setSfythcj(0);
            }
            if (StringUtils.isEmpty(ryxx.getZbdwBh())) {
                if(StringUtils.isNotEmpty(ryxx.getMjSfzh())){
                    JdoneSysUser sysUser = userService.findSysUserByIdCard(ryxx.getMjSfzh());
                    if(sysUser != null){
                        ryxx.setZbdwBh(sysUser.getOrgCode());
                        ryxx.setZbdwMc(sysUser.getOrgName());
                        ryxx.setDwxtbh(sysUser.getOrgSysCode());
                    }
                }
            }
            if(StringUtils.isEmpty(ryxx.getDwxtbh())){
                String sysCode = user.getOrgSysCode();
                ryxx.setDwxtbh(sysCode);
            }
            ryxx.setSftb(0);
            if(StringUtil.isNotEmpty(ywbhStr) && ywbhStr.startsWith("A")){
                ryxx.setAjbh(ywbhStr);
            }
            if(StringUtil.isNotEmpty(ywbhStr) && ywbhStr.startsWith("J")){
                ryxx.setJqbh(ywbhStr);
            }
            ryxx.setLrrSfzh(user.getIdCard());
            ryxx.setLrsj(new Date());
            ryxx.setTjxgrsfzh(user.getIdCard());
            ryxx.setTjxgsj(new Date());
            ryxx.setIsdel(0);
            ryxx.setDataflag("0");
            if(!StringUtil.equals(ryxx.getRyzaybh(),SYSCONSTANT.DAFS_QT)){
                ryxx.setRyzaymc(DicUtil.translate("RSYY",ryxx.getRyzaybh()));
            }

            // 保存办案区人员信息
            // 判断储物柜写物品状态
            if (StringUtils.isNotEmpty(cwglx) && !"0".equals(cwgbh)) {
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WCL);
            } else if (ryxx.getSswpzt() == null){
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WU);
            }

            // 当查出身份证号相同时有在所人员时，返回异常
            if(StringUtils.isNotEmpty(ryxx.getRysfzh())){
                if(findByRysfzh(ryxx.getRysfzh()) != null){
                    throw new Exception("证件号码为:"+baqryxx.getRysfzh()+"的人员:"+baqryxx.getRyxm()+",已在所!");
                }
            }

            ApiGgYwbh comYwbh = null;
            if(StringUtil.isEmpty(ryxx.getRybh())){  //盘查人员录入时会带入rybh
                try{
                    comYwbh = jdoneComYwbhService.getYwbh("R",user.getOrgCode(),null);
                    ryxx.setRybh(comYwbh.getYwbh());
                    jdoneComYwbhService.ywbhCommit(comYwbh);
                }catch (Exception e){
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    throw new Exception(e);
                }
            }
            ryxx.setBaqsyqkbh(getbaqjlbh(ryxx.getBaqid(),ryxx.getRRssj()));

            //同步修改盘查人员状态
            Map<String,Object> params = new HashMap<>();
            params.put("rybh",ryxx.getRybh());
            List<ChasYwPcry> pcries = pcryService.findList(params,null);
            if(!pcries.isEmpty()){
                ChasYwPcry pcry = pcries.get(0);
                pcry.setDataflag("1"); //代表已入所
                ryxx.setSfythcj(pcry.getYthcjzt());
                pcryService.update(pcry);
            }
            //修改人脸特征
            faceInvokeService.saveBaqryFeature(ryxx.getZpid(), ryxx.getBaqid(), ryxx.getDwxtbh(), ryxx.getRybh());
            // 下发人脸
            if(configuration.getRyrsRqrlxf()){
                ChasBaqryxx finalryxx = baqryxx;
                new Thread(() -> {
                    brakeService.sendRyFaceInfo(finalryxx);
                }).start();
            }
            ryxx.setSfznbaq(1);
            save(ryxx);

            result.put("success", true);
            result.put("id", ryxx.getId());
            result.put("msg", "保存成功!");
            result.put("baqryxx",ryxx);

            ChasBaqryxx finalRyxx1 = ryxx;
            new Thread(() -> {
                sendMessageTozfba(finalRyxx1);
                ryRsCsSendMsg(finalRyxx1);
            }).start();
        } else { // 修改
            ChasBaqryxx oldryxx = findById(id);

            String his_mj = oldryxx.getMjSfzh();
            MyBeanUtils.copyBeanNotNull2Bean(ryxx, oldryxx);
            if(StringUtil.isNotEmpty(ywbhStr) && ywbhStr.startsWith("A")){
                oldryxx.setAjbh(ywbhStr);
                oldryxx.setJqbh("");
            }
            if(StringUtil.isNotEmpty(ywbhStr) && ywbhStr.startsWith("J")){
                oldryxx.setJqbh(ywbhStr);
                oldryxx.setAjbh("");
            }
            if(StringUtil.isEmpty(ywbhStr)){
                oldryxx.setAjbh("");
                oldryxx.setJqbh("");
            }

            //是否更换主办民警  (已更换)
            if(!StringUtil.equals(baqryxx.getMjSfzh(),his_mj)){
                if(StringUtil.isNotEmpty(baqryxx.getMjSfzh())){
                    JdoneSysUser sysUser = userService.findSysUserByIdCard(baqryxx.getMjSfzh());
                    if(sysUser != null){
                        oldryxx.setZbdwBh(sysUser.getOrgCode());
                        oldryxx.setZbdwMc(sysUser.getOrgName());
                        oldryxx.setDwxtbh(sysUser.getOrgSysCode());
                        SessionUser sessionUser = WebContext.getSessionUser();
                        oldryxx.setTjxgsj(new Date());
                        oldryxx.setTjxgrsfzh(sessionUser.getIdCard());
                    }
                }
            }

            if (StringUtils.isEmpty(oldryxx.getRyzaymc())) {
                oldryxx.setRyzaymc(DicUtil.translate("RSYY",ryxx.getRyzaybh()));
            }
            //修改人脸特征
            faceInvokeService.saveBaqryFeature(oldryxx.getZpid(), oldryxx.getBaqid(), oldryxx.getDwxtbh(), oldryxx.getRybh());
            oldryxx.setSfznbaq(1);
            update(oldryxx);
            //更新物品总照片信息
            if(StringUtil.isNotEmpty(rs_wp_all_bizId)){
                Map<String,Object> param_ = new HashMap<>();
                param_.put("rybh",oldryxx.getRybh());
                param_.put("zplx",SYSCONSTANT.SSWP_ZP_RS_ZL);
                List<ChasSswpZpxx> zpxxes = zpxxService.findList(param_,null);
                if(zpxxes.isEmpty()){  //如果第一次保存，则录入
                    ChasSswpZpxx zpxx = new ChasSswpZpxx();
                    zpxx.setId(StringUtils.getGuid32());
                    zpxx.setLrrSfzh(user.getIdCard());
                    zpxx.setLrsj(new Date());
                    zpxx.setBaqid(baqryxx.getBaqid());
                    zpxx.setBaqmc(baqryxx.getBaqmc());
                    zpxx.setBizId(rs_wp_all_bizId);
                    zpxx.setDataFlag("");
                    zpxx.setIsdel(0);
                    zpxx.setRybh(baqryxx.getRybh());
                    zpxx.setWpid("");  //入所总物品照片没有物品ID
                    zpxx.setZplx(SYSCONSTANT.SSWP_ZP_RS_ZL);
                    zpxx.setXgrSfzh(user.getIdCard());
                    zpxx.setXgsj(new Date());
                    zpxxService.save(zpxx);
                }else{
                    ChasSswpZpxx zpxx = zpxxes.get(0);
                    zpxx.setBizId(rs_wp_all_bizId);
                    zpxxService.update(zpxx);
                }
            }

            ryxx = oldryxx;
            result.put("success", true);
            result.put("id", oldryxx.getId());
            result.put("baqryxx",ryxx);
            result.put("msg", "保存成功!");
        }

        SaveWithUpdateCssp(ryxx);
        SaveWithUpdateRyjlByForm(ryxx, param);
        relationYwbh(ywbhStr,ryxx);
        return result;
    }

    public Map<String,Object> SaveWithUpdateRyjlByForm(ChasBaqryxx baqryxx,RyxxBean form) throws Exception{
        ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(),baqryxx.getRybh());
        BaqConfiguration baqznpz = baqznpzService.findByBaqid(baqryxx.getBaqid());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String,Object> result_ = new HashMap<>();

        String tary_ywbh = form.getTarybh();  //同案人员虚拟业务编号
        String wdbhL = form.getWdbhL();  //低频
        String wdbhH = form.getWdbhH();  //高频
        String cwgid = form.getCwgid();
        String cwglx = form.getCwglx();
        String cwgbh = form.getCwgbh();
        String sjgId = form.getSjgId();
        String sjgBh = form.getSjgBh();
        String ddkg = form.getDdkg();  //单独看管

        if(ryjl != null){
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:修改业务逻辑耗时:"+format.format(new Date()));
            if(!baqznpz.getDwHkrl()) {//未启用人脸定位走手环逻辑
                String wd_ = ryjl.getWdbhL();
                if(!StringUtils.equals(wdbhL,wd_) && StringUtils.isNotEmpty(wdbhL)){  //如果腕带已更改,那么则更新人员记录信息
                    //是否启用腕带
                    DevResult result = new DevResult();
                    if(baqznpz.getDw()){
                        //解除腕带
                        result = iWdService.wdJc(ryjl);
                        if(result.getCode() != 1){
                            throw new Exception("解除手环失败:"+result.getMessage());
                        }
                    }
                    //修改腕带编号
                    Map<String,Object> params = new HashMap<>();
                    params.put("wdbhL",wdbhL);
                    params.put("baqid",baqryxx.getBaqid());
                    List<ChasSb> chasSb = sbService.findByParams(params);
                    if(!chasSb.isEmpty()){
                        wdbhH = chasSb.get(0).getKzcs1();
                    }
                    ryjl.setWdbhL(wdbhL);
                    ryjl.setWdbhH(wdbhH);
                    ryjlService.update(ryjl);
                    if(baqznpz.getDw()){
                        //重新绑定
                        result = iWdService.wdBd(ryjl);
                        if(result.getCode() != 1){
                            throw new Exception("重新绑定手环失败:"+result.getMessage());
                        }
                    }

                    //如果更新了人员姓名
                    dhsglService.dhsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                    sxsglService.sxsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                }
            }else{
                ryjl.setXm(baqryxx.getRyxm());
                ryjlService.update(ryjl);

                //如果更新了人员姓名
                dhsglService.dhsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
                sxsglService.sxsGxXx(baqryxx.getBaqid(),baqryxx.getRybh());
            }


            //储物柜更改
            if(baqznpz.getSswp()){
                if(!StringUtil.equals(ryjl.getCwgId(),cwgid) && StringUtils.isNotEmpty(cwgid)){
                    String history_cwgbh = ryjl.getCwgBh();
                    //启动储物柜后
                    DevResult result = new DevResult();
                    if(StringUtil.isNotEmpty(history_cwgbh) && !StringUtil.equals(history_cwgbh,"0")){
                        //解绑储物柜
                        result = iWpgService.jcCwg(ryjl.getBaqid(),ryjl.getRybh());
                        if(result.getCode() != 1){
                            throw new Exception("解除储物柜失败:"+result.getMessage());
                        }
                    }
                    //绑定储物柜
                    if(StringUtils.isNotEmpty(cwgbh) && !StringUtils.equals(cwgbh,"0")){
                        result = iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                        if(result.getCode() != 1){
                            throw new Exception("绑定储物柜失败:"+result.getMessage());
                        }
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh, "1");
                        if(result.getCode() != 1){
                            iWpgService.jcZng(ryjl.getBaqid(),ryjl.getRybh(),cwgbh);
                            iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh(),ryjl.getCwgBh());
                            throw new Exception("打开储物柜失败:"+result.getMessage());
                        }
                    }
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setCwgId(cwgid);
                    //ryjlService.update(ryjl);
                }
            }
            ryjl.setCwgLx(cwglx);

            //判断手机柜是否已更改
            if(baqznpz.getPhonecab()){
                if(!StringUtil.equals(ryjl.getSjgId(),sjgId) && StringUtils.isNotEmpty(sjgId)){
                    String history_sjgbh = ryjl.getSjgBh();
                    //启动储物柜后
                    DevResult result = new DevResult();
                    if(StringUtil.isNotEmpty(history_sjgbh) && !StringUtil.equals(history_sjgbh,"0")){
                        //解绑手机柜
                        result = iWpgService.jcSjg(ryjl.getBaqid(),ryjl.getRybh());
                        if(result.getCode() != 1){
                            throw new Exception("解除手机柜失败:"+result.getMessage());
                        }
                    }

                    //绑定手机柜
                    if(StringUtils.isNotEmpty(sjgBh) && !StringUtils.equals(sjgBh,"0")){
                        result = iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh);
                        if(result.getCode() != 1){
                            throw new Exception("绑定手机柜失败:"+result.getMessage());
                        }
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh, "40");
                        if(result.getCode() != 1){
                            //如果手机柜打开失败,则取消绑定手机柜并清空柜号
                            iWpgService.jcZng(ryjl.getBaqid(),ryjl.getRybh(),sjgBh);
                            iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh(),ryjl.getSjgBh());
                            throw new Exception("打开手机柜失败:"+result.getMessage());
                        }
                    }
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setSjgId(sjgId);
                    //ryjlService.update(ryjl);
                }
            }

            //同步修改人员姓名和性别信息
            if(!StringUtil.equals(ryjl.getXm(),baqryxx.getRyxm()) || !StringUtil.equals(ryjl.getXb(),baqryxx.getXb())){
                ryjl.setXm(baqryxx.getRyxm());
                ryjl.setXb(baqryxx.getXb());
                //ryjlService.update(ryjl);
                //同步修改等候室\审讯室 人员基本信息
                if(StringUtil.isNotEmpty(ryjl.getSxsBh())){
                    ChasSxsKz sxsKz = sxsKzService.findById(ryjl.getSxsBh());
                    if(sxsKz != null){
                        if(!StringUtil.equals(sxsKz.getRyxm(),baqryxx.getRyxm())){
                            sxsKzService.updateRyxmByRybh(baqryxx.getRyxm(),baqryxx.getRybh());
                        }
                    }
                }
                if(StringUtil.isNotEmpty(ryjl.getDhsBh())){
                    ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                    if(dhsKz != null){
                        if(!StringUtil.equals(dhsKz.getRyxm(),baqryxx.getRyxm()) || !StringUtil.equals(dhsKz.getRyxb(),baqryxx.getXb())){
                            dhsKzService.updateXmXbByRybh(baqryxx.getRyxm(),baqryxx.getXb(),baqryxx.getRybh());
                        }
                    }
                }
            }

            if(StringUtil.isNotEmpty(baqryxx.getJqbh())){
                ryjl.setAjbh("");
                ryjl.setYwbh(baqryxx.getJqbh());
            }
            if(StringUtil.isNotEmpty(baqryxx.getAjbh())){
                ryjl.setAjbh(baqryxx.getAjbh());
                ryjl.setYwbh(baqryxx.getAjbh());
            }
            ryjlService.update(ryjl);
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:修改业务逻辑耗时:"+format.format(new Date()));
        }else{
            log.info("保存办案区人员_人员轨迹-SaveWithUpdateRyjl:保存业务逻辑耗时:"+format.format(new Date()));
            DevResult result = null;
            if(!baqznpz.getDwHkrl()) {//未启用人脸定位走手环逻辑
                Map<String,Object> params = new HashMap<>();
                params.put("wdbhL",wdbhL);
                params.put("baqid",baqryxx.getBaqid());
                List<ChasSb> chasSb = sbService.findByParams(params);
                if(!chasSb.isEmpty()){
                    wdbhH = chasSb.get(0).getKzcs1();
                }

                ApiGgYwbh comYwbh = null;

                try{
                    boolean flag = true;  //是否使用了业务编号
                    log.info("未启用人脸定位,轨迹ZbdwBh():"+baqryxx.getZbdwBh());
                    comYwbh = jdoneComYwbhService.getYwbh("Y",baqryxx.getZbdwBh(),null);
                    log.info("未启用人脸定位,轨迹comYwbh:"+comYwbh);
                    ryjl = new ChasRyjl();
                    ryjl.setLrrSfzh(baqryxx.getLrrSfzh());
                    ryjl.setLrsj(new Date());
                    ryjl.setXgsj(new Date());
                    ryjl.setXgrSfzh(baqryxx.getTjxgrsfzh());
                    ryjl.setId(StringUtils.getGuid32());
                    ryjl.setBaqid(baqryxx.getBaqid());
                    ryjl.setBaqmc(baqryxx.getBaqmc());
                    ryjl.setRybh(baqryxx.getRybh());
                    ryjl.setWdbhL(wdbhL);
                    ryjl.setWdbhH(wdbhH);
                    ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JXZ);
                    if(StringUtils.isEmpty(tary_ywbh)){  //如果页面提交了同安人员编号，那么就不使用虚拟值
                        if(StringUtils.isNotEmpty(baqryxx.getAjbh()) || StringUtils.isNotEmpty(baqryxx.getJqbh())){  //如果该人员关联了警案信息，那么就使用同一编号
                            if(StringUtils.isNotEmpty(baqryxx.getAjbh())){
                                ryjl.setAjbh(baqryxx.getAjbh());
                                ryjl.setYwbh(baqryxx.getAjbh());
                            }
                            if(StringUtils.isNotEmpty(baqryxx.getJqbh())){
                                ryjl.setYwbh(baqryxx.getJqbh());
                            }
                            flag = false;
                        }else{
                            ryjl.setYwbh(comYwbh.getYwbh());
                        }
                    }else{
                        ryjl.setYwbh(tary_ywbh);
                        flag = false;
                    }
                    ryjl.setCwgId(cwgid);
                    ryjl.setCwgLx(cwglx);
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setSjgId(sjgId);
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setCfsj(new Date());
                    ryjl.setXb(baqryxx.getXb());
                    ryjl.setXm(baqryxx.getRyxm());
                    ryjl.setRylb(baqryxx.getRylx());
                    ryjl.setSftayj(1);
                    ryjl.setLczt(SYSCONSTANT.LCJD_XXDJ_QZ);  //流程节点信息登记
                    if(StringUtil.isEmpty(ryjl.getYwbh())){
                        flag = true;
                        ryjl.setYwbh(comYwbh.getYwbh());
                    }
                    log.info("未启用人脸定位,轨迹ryjl:"+JSON.toJSONString(ryjl));
                    ryjlService.save(ryjl);

                    if(flag){
                        jdoneComYwbhService.ywbhCommit(comYwbh);
                    }else{
                        jdoneComYwbhService.ywbhRollBack(comYwbh);
                    }
                }catch (Exception e){
                    log.info("生成轨迹异常"+e);;
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    throw new Exception(e);
                }

                if(baqznpz.getDw()){
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手环开始:"+format.format(new Date()));
                    //绑定手环
                    result = iWdService.wdBd(ryjl);
                    if(result.getCode() != 1){
                        throw new Exception("绑定手环失败:"+result.getMessage());
                    }
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手环结束:"+format.format(new Date()));
                }
            }else{
                ApiGgYwbh comYwbh = null;

                try{
                    boolean flag = true;  //是否使用了业务编号
                    log.info("启用人脸定位，轨迹ZbdwBh():"+baqryxx.getZbdwBh());
                    comYwbh = jdoneComYwbhService.getYwbh("Y",baqryxx.getZbdwBh(),null);
                    log.info("启用人脸定位，轨迹comYwbh:"+comYwbh);
                    ryjl = new ChasRyjl();
                    ryjl.setLrrSfzh(baqryxx.getLrrSfzh());
                    ryjl.setLrsj(new Date());
                    ryjl.setXgsj(new Date());
                    ryjl.setXgrSfzh(baqryxx.getTjxgrsfzh());
                    ryjl.setId(StringUtils.getGuid32());
                    ryjl.setBaqid(baqryxx.getBaqid());
                    ryjl.setBaqmc(baqryxx.getBaqmc());
                    ryjl.setRybh(baqryxx.getRybh());
                    ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JXZ);
                    if(StringUtils.isEmpty(tary_ywbh)){  //如果页面提交了同安人员编号，那么就不使用虚拟值
                        if(StringUtils.isNotEmpty(baqryxx.getAjbh()) || StringUtils.isNotEmpty(baqryxx.getJqbh())){  //如果该人员关联了警案信息，那么就使用同一编号
                            if(StringUtils.isNotEmpty(baqryxx.getAjbh())){
                                ryjl.setAjbh(baqryxx.getAjbh());
                                ryjl.setYwbh(baqryxx.getAjbh());
                            }
                            if(StringUtils.isNotEmpty(baqryxx.getJqbh())){
                                ryjl.setYwbh(baqryxx.getJqbh());
                            }
                            flag = false;
                        }else{
                            ryjl.setYwbh(comYwbh.getYwbh());
                        }
                    }else{
                        ryjl.setYwbh(tary_ywbh);
                        flag = false;
                    }
                    ryjl.setCwgId(cwgid);
                    ryjl.setCwgLx(cwglx);
                    ryjl.setCwgBh(cwgbh);
                    ryjl.setSjgId(sjgId);
                    ryjl.setSjgBh(sjgBh);
                    ryjl.setCfsj(new Date());
                    ryjl.setXb(baqryxx.getXb());
                    ryjl.setXm(baqryxx.getRyxm());
                    ryjl.setRylb(baqryxx.getRylx());
                    ryjl.setSftayj(1);
                    ryjl.setLczt(SYSCONSTANT.LCJD_XXDJ_QZ);  //流程节点信息登记
                    if(StringUtil.isEmpty(ryjl.getYwbh())){
                        flag = true;
                        ryjl.setYwbh(comYwbh.getYwbh());
                    }
                    ryjlService.save(ryjl);

                    if(flag){
                        jdoneComYwbhService.ywbhCommit(comYwbh);
                    }else{
                        jdoneComYwbhService.ywbhRollBack(comYwbh);
                    }
                }catch (Exception e){
                    log.info("生成轨迹异常"+e);;
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    throw new Exception(e);
                }
            }


            if(baqznpz.getSswp()){
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定储物柜开始:"+format.format(new Date()));
                //绑定储物柜
                if(StringUtils.isNotEmpty(ryjl.getCwgBh()) && !StringUtils.equals(ryjl.getCwgBh(),"0")){
                    result = iWpgService.cwgBd(baqryxx.getBaqid(),baqryxx.getRybh());
                    if(result.getCode() != 1){
                        //保存储物柜失败后，需要解除已绑定的手环
                        iWdService.wdJc(ryjl);
                        throw new Exception("绑定储物柜失败:"+result.getMessage());
                    }
                    if(baqznpz.getSswpZjkg()){
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh, "1");
                        if(result.getCode() != 1){
                            iWdService.wdJc(ryjl);
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                            throw new Exception("打开储物柜失败:"+result.getMessage());
                        }
                    }
                }
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定储物柜结束:"+format.format(new Date()));
            }

            //绑定手机柜
            if(baqznpz.getPhonecab()){
                if(StringUtils.isNotEmpty(ryjl.getSjgBh()) && !StringUtils.equals(ryjl.getSjgBh(),"0")){
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手机柜开始:"+format.format(new Date()));
                    result = iWpgService.sjgBd(baqryxx.getBaqid(),baqryxx.getRybh());
                    if(result.getCode() != 1){
                        iWdService.wdJc(ryjl);
                        iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                        throw new Exception("绑定手机柜失败:"+result.getMessage());
                    }
                    if(baqznpz.getSswpZjkg()){
                        result = iWpgService.openCwg(baqryxx.getBaqid(),baqryxx.getRybh(),sjgBh, "40");
                        if(result.getCode() != 1){
                            //如果手机柜打开失败,则取消绑定手机柜并清空柜号
                            iWdService.wdJc(ryjl);
                            iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                            iWpgService.jcZng(ryjl.getBaqid(),ryjl.getRybh(),sjgBh);
                            throw new Exception("打开手机柜失败:"+result.getMessage());
                        }
                    }
                    log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:绑定手机柜结束:"+format.format(new Date()));
                }
            }

            if(baqznpz.getDhsDjym()){
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:分配等候室开始:"+format.format(new Date()));
                //分配等候室
                if(StringUtil.equals(ddkg,"true")){
                    result = dhsglService.aloneAssign(baqryxx.getBaqid(),baqryxx.getRybh(),"");
                }else{
                    result = dhsglService.dhsFp(baqryxx.getBaqid(),baqryxx.getRybh(),"");
                }
                if(result.getCode() != 1 && result.getCode() != 3){
                    iWdService.wdJc(ryjl);
                    iWpgService.jcZng(baqryxx.getBaqid(),baqryxx.getRybh(),cwgbh);
                    iWpgService.jcZng(ryjl.getBaqid(),ryjl.getRybh(),sjgBh);
                    throw new Exception("等候室分配失败:"+result.getMessage());
                }
                if(result.getCode() == 3){
                    result_.put("tips",result.getMessage());
                }
                log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:分配等候室结束:"+format.format(new Date()));
            }

            if(baqznpz.getJhrwRqjh()){
                String qymc = "看守区";
                ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                if(dhsKz != null){
                    ChasXtQy qy = qyService.findByYsid(dhsKz.getQyid());
                    if(qy != null){
                        qymc = qy.getQymc();
                    }
                }
                //开启戒护任务
                DevResult devResult =  jhrwService.saveJhrw(baqryxx.getMjXm(),baqryxx.getZbdwBh(),baqryxx,SYSCONSTANT.JHRWLX_RQJH,"办案区入口",qymc);
                if(devResult.getCode() != 0){
                    throw new Exception("开启戒护任务失败:"+devResult.getMessage());
                }
            }
            if(baqznpz.getDdrwSxdd()){
                //审讯大屏
                ChasSxdp sxdp = new ChasSxdp();
                sxdp.setXyr(baqryxx.getRybh());
                sxdp.setXyrxm(baqryxx.getRyxm());
                sxdpService.createSx(sxdp);
            }
            log.info("保存办案区人员_保存人员轨迹-SaveWithUpdateRyjl:保存业务逻辑耗时:"+format.format(new Date()));
            //ryjlService.save(ryjl);
        }
        return result_;
    }

    @Override
    public void rydeparture(String csspId, String spzt, String spyj, JdoneSysUser spr) throws Exception {
        ChasYmCssp cssp = csspService.findById(csspId);
        cssp.setSpyj(spyj);
        cssp.setSpzt(spzt);
        if (cssp.getLklx().equals(SYSCONSTANT.LKLX_LSCS) && spzt.equals("1")) {
            cssp.setRyzt(SYSCONSTANT.CSSP_RYZT_LSCS);
        }
        if (spr != null) {
            cssp.setSprSfzh(spr.getIdCard());
            cssp.setSprXm(spr.getName());
        } else {
            cssp.setSprSfzh(WebContext.getSessionUser().getIdCard());
            cssp.setSprXm(WebContext.getSessionUser().getName());
        }
        cssp.setSpsj(new Date());

        BaqConfiguration configuration = baqznpzService.findByBaqid(cssp.getBaqid());

        //*修改人员状态/
        String baqid = cssp.getBaqid();
        String rybh = cssp.getRybh();
        String lklx = cssp.getLklx();
        if (spzt.equals(SYSCONSTANT.SHZT_SPBTG)) {  //如果不同意
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("baqid", baqid);
            params.put("rybh", rybh);
            List<ChasBaqryxx> ryxxs = findList(params,null);
            if (ryxxs != null && ryxxs.size() > 0) {
                ChasBaqryxx ryxx = ryxxs.get(0);
                ryxx.setRyzt(SYSCONSTANT.BAQRYZT_ZS);
                ryxx.setCCsyy("");
                update(ryxx);
            }
        }else if(spzt.equals(SYSCONSTANT.SHZT_SPTG)){  //如果同意
            if(lklx.equals(SYSCONSTANT.LKLX_LSCS)){  //临时出所
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("baqid", baqid);
                params.put("rybh", rybh);
                List<ChasRyjl> chasRyjllist = ryjlService.findList(params, null);
                params.put("ryzt", SYSCONSTANT.BAQRYZT_LCSH);
                List<ChasBaqryxx> ryxxs = findList(params, null);
                if (!ryxxs.isEmpty()) {
                    ChasBaqryxx ryxx = ryxxs.get(0);
                    ryxx.setRyzt(SYSCONSTANT.BAQRYZT_LSCS);
                    update(ryxx);
                    // 更新cssp记录
                    cssp.setLksj(new Date());
                    csspService.update(cssp);

                    if(configuration.getJhrwCqjh()){
                        String qymc = "看守区";
                        ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(),ryxx.getRybh());
                        if(StringUtil.isNotEmpty(ryjl.getDhsBh())){
                            ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                            if(dhsKz != null){
                                ChasXtQy qy = qyService.findByYsid(dhsKz.getQyid());
                                if(qy != null){
                                    qymc = qy.getQymc();
                                }
                            }
                        }
                        //开启出区戒护任务
                        DevResult devResult = jhrwService.saveJhrw(ryxx.getMjXm(),ryxx.getZbdwBh(),ryxx,SYSCONSTANT.JHRWLX_CQJH,qymc,"办案区出口");
                        if(devResult.getCode() != 0){
                            throw new Exception("开启戒护任务失败:"+devResult.getMessage());
                        }
                    }

                    //下发人脸
                    brakeService.sendRyFaceInfo(ryxx);

                    if(configuration != null){
                        if(configuration.getQgc()){
                            BaqrysypUtil.uplodsaveAudioVideoByRyjc(ryxxs.get(0),"8","301");
                        }
                    }
                }
                // 处理临时出所 在所-暂离-刷新led 暂离无处理
                if (!chasRyjllist.isEmpty()) {
                    ChasRyjl chasRyjl = chasRyjllist.get(0);
                    // 分配审讯室后 等候室暂离
                    ChasDhsKz chasDhsKz = dhsKzService.findById(chasRyjl.getDhsBh());
                    if(chasDhsKz != null){
                        if (!SYSCONSTANT.DHSTZ_ZL.equals(chasDhsKz.getFpzt())) {
                            chasDhsKz.setFpzt(SYSCONSTANT.DHSTZ_ZL);
                            dhsKzService.update(chasDhsKz);
                            // 保存后开启继电器（除了智能墙体） 刷新LED
                            iLedService.refreshDhsLed(baqid,chasDhsKz.getQyid());
                            iLedService.refreshDhsDp(baqid);
                        }
                    }
                    chasRyjl.setLczt(SYSCONSTANT.LCJD_LSCS);  //流程节点  临时出所
                }
                log.info("人员:"+rybh+",临时出所成功!");
            }else if(lklx.equals(SYSCONSTANT.CSSP_SHLX_CS)){  //正式出所
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("baqid", baqid);
                ChasBaq baq = baqService.findById(baqid);
                if (baq != null) {
                    params.put("rybh", rybh);
                    params.put("ryzt", SYSCONSTANT.BAQRYZT_DSH);
                    List<ChasBaqryxx> ryxxs = findList(params, null);
                    if (ryxxs != null && ryxxs.size() > 0) {
                        ChasBaqryxx ryxx = ryxxs.get(0);
                        ryxx.setRyzt(SYSCONSTANT.BAQRYZT_DCS);
                        update(ryxx);
                        cssp.setLksj(new Date());
                        csspService.update(cssp);
                        /*ChasRygjmapService rygjmapService = ServiceContext.getServiceByClass(ChasRygjmapService.class);
                        rygjmapService.buildLocaltionlkInfo(ryxx.getRybh());*/

                        if(configuration.getJhrwCqjh()){
                            String qymc = "看守区";
                            ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(),ryxx.getRybh());
                            if(StringUtil.isNotEmpty(ryjl.getDhsBh())){
                                ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                                if(dhsKz != null){
                                    ChasXtQy qy = qyService.findByYsid(dhsKz.getQyid());
                                    if(qy != null){
                                        qymc = qy.getQymc();
                                    }
                                }
                            }
                            //开启出区戒护任务
                            DevResult devResult = jhrwService.saveJhrw(ryxx.getMjXm(),ryxx.getZbdwBh(),ryxx,SYSCONSTANT.JHRWLX_CQJH,qymc,"办案区出口");
                            if(devResult.getCode() != 0){
                                throw new Exception("开启戒护任务失败:"+devResult.getMessage());
                            }
                        }

                        //发起送押任务  (行政拘留 或者 刑事拘留)
                        if(StringUtil.equals(ryxx.getCRyqx(),"01") || StringUtil.equals(ryxx.getCRyqx(),"04")){
                            ChasSyrw syrw = new ChasSyrw();
                            syrw.setBaq(ryxx.getBaqid());
                            syrw.setBaqmc(ryxx.getBaqmc());
                            syrw.setXyrbh(ryxx.getRybh());
                            syrw.setXyrxm(ryxx.getRyxm());
                            syrw.setXb(ryxx.getXb());
                            syrw.setRqyy(ryxx.getRyzaybh());
                            syrw.setTsqt(ryxx.getTsqt());
                            params.clear();
                            params.put("ajbh",ryxx.getAjbh());
                            if(StringUtil.isNotEmpty(ryxx.getAjbh())){
                                ApiGgAjxxService apiGgAjxxService = ServiceContext.getServiceByClass(ApiGgAjxxService.class);
                                ApiGgAjxx ajxx = apiGgAjxxService.getAjxxByAjbh(ryxx.getAjbh());
                                if(ajxx != null){
                                    syrw.setAjlx(ajxx.getAjlx());
                                }
                            }
                            syrw.setZbmj(ryxx.getMjXm());
                            syrw.setCqyy(ryxx.getCRyqx());
                            syrw.setSqr(ryxx.getMjSfzh());
                            syrw.setSqrxm(ryxx.getMjXm());
                            Map<String,Object> result = syrwService.missionStart(syrw);
                            log.info("发起送押-"+ryxx.getRybh()+":"+result.toString());
                        }

                        //下发人脸
                        brakeService.sendRyFaceInfo(ryxx);

                        //如果一体化未采集则发起预警
                        if(ryxx.getSfythcj() == 0){
                            ChasYjxx chasYjxx = new ChasYjxx();
                            chasYjxx.setId(StringUtils.getGuid32());
                            chasYjxx.setBaqid(ryxx.getBaqid());
                            chasYjxx.setBaqmc(ryxx.getBaqmc());
                            chasYjxx.setLrsj(new Date());
                            chasYjxx.setJqms(ryxx.getRyxm()+",一体化未采集!");
                            chasYjxx.setYjjb(SYSCONSTANT.YJJB_YJ);
                            chasYjxx.setYjlb(SYSCONSTANT.YJLB_YTHWCJ);
                            chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
                            chasYjxx.setCfsj(new Date());
                            chasYjxx.setCfrid(ryxx.getRybh());
                            chasYjxx.setCfrxm(ryxx.getRyxm());
                            yjxxService.save(chasYjxx);
                        }

                        if(configuration != null){
                            if(configuration.getQgc()){
                                BaqrysypUtil.uplodsaveAudioVideoByRyjc(ryxxs.get(0),"7","301");
                            }
                        }
                    }
                }
                //修改人员的随身物品状态
                params.clear();
                params.put("rybh",rybh);
                List<ChasSswpxx> sswpxxes = sswpxxService.findList(params,null);
                boolean flag = false;
                for(ChasSswpxx sswpxx : sswpxxes) {
                    if(StringUtil.equals(sswpxx.getWpclzt(),"01") || StringUtil.equals(sswpxx.getWpclzt(),"31")){  //只要有一个是在所，那么就是部分处理
                        flag = true;
                    }
                    sswpxx.setWpzt(sswpxx.getWpclzt());
                    sswpxxService.update(sswpxx);
                }
                List<ChasBaqryxx> baqryxxes = findList(params,null);
                if(!baqryxxes.isEmpty()){
                    ChasBaqryxx baqryxx = baqryxxes.get(0);
                    if(flag){
                        baqryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_BFCL);
                    }else{
                        baqryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);
                    }
                    update(baqryxx);
                }
                log.info("人员:"+rybh+",出所成功!");
            }
        }
    }

    @Override
    public void startProcess(String ryId, String csyy, String csyyName, String type, String qtyy, NodeProcessCmdObj2 cmdObj) throws Exception {
        SessionUser user = WebContext.getSessionUser();
        // 修改人员状态
        ChasBaqryxx ryxx = baseDao.selectByPrimaryKey(ryId);
        ryxx.setRyzt(type);
        // 添加出所记录
        ChasYmCssp cssp = new ChasYmCssp();
        cssp.setId(cmdObj.getBizId());
        cssp.setLrrSfzh(user.getIdCard());
        cssp.setLrsj(new Date());
        cssp.setXgrSfzh(user.getIdCard());
        cssp.setLrsj(new Date());
        cssp.setBaqid(ryxx.getBaqid());
        cssp.setBaqmc(ryxx.getBaqmc());
        cssp.setRybh(ryxx.getRybh());
        cssp.setRyxm(ryxx.getRyxm());
        cssp.setLkyy(csyyName);
        cssp.setZbdwBh(ryxx.getZbdwBh());
        cssp.setZbdwMc(ryxx.getZbdwMc());
        cssp.setZbrSfzh(ryxx.getMjSfzh());
        cssp.setZbrXm(ryxx.getMjXm());
        cssp.setSpzt(SYSCONSTANT.N);
        cssp.setRyzt(SYSCONSTANT.CSSP_RYZT_ZS);
        cssp.setLkyydm(csyy);
        String sysCode = user.getOrgSysCode();
        if (StringUtils.isNotEmpty(sysCode)) {
            cssp.setDwxtbh(sysCode);
        }
        if (type.equals(SYSCONSTANT.BAQRYZT_DSH)) {// 出所审核
            cssp.setLklx(SYSCONSTANT.CSSP_SHLX_CS);
            ryxx.setCRyqx(csyy);
            ryxx.setCCsyy(csyyName);
        } else if (type.equals(SYSCONSTANT.BAQRYZT_LCSH)) {// 临时出所
            cssp.setLklx(SYSCONSTANT.CSSP_SHLX_LSCS);
            ryxx.setCRyqx(csyy);
            ryxx.setCCsyy(csyyName);
            ryxx.setCCssj(new Date());
        }
        baseDao.updateByPrimaryKey(ryxx);
        // 其他入所原因
        if (StringUtils.isNotEmpty(qtyy)) {
            cssp.setLkyy(qtyy);
        }
        if (csspService.save(cssp) == 1) {
            cmdObj.setDxbh(ryxx.getRybh());
            cmdObj.setDxmc(ryxx.getRyxm());
            processEngineService.startProcess(cmdObj);
        }

        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxx.getBaqid());

        if(configuration.getJhrwTjjh()){
            //开启体检戒护  (行政拘留，刑事拘留)
            if(StringUtil.equals(csyy,"01") || StringUtil.equals(csyy,"04")){
                String qymc = "看守区";
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(),ryxx.getRybh());
                ChasDhsKz dhskz = dhsKzService.findById(ryjl.getDhsBh());
                if(dhskz != null){
                    ChasXtQy qy = qyService.findByYsid(dhskz.getQyid());
                    if(qy != null){
                        qymc = qy.getQymc();
                    }
                }
                DevResult devResult = jhrwService.saveJhrw(ryxx.getMjXm(),ryxx.getZbdwBh(),ryxx,SYSCONSTANT.JHRWLX_TJJH,qymc,"体检区");
                if(devResult.getCode() != 0){
                    throw new Exception("开启戒护任务失败:"+devResult.getMessage());
                }
            }
        }
    }

    @Override
    public Map<String, Object> PersonnelReturn(String ryid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("msg", "人员归所失败");
        ChasBaqryxx ryxx = findById(ryid);
        String ryzt = ryxx.getRyzt();
        if (SYSCONSTANT.BAQRYZT_LSCS.equals(ryzt) && StringUtils.isNotEmpty(ryxx.getRybh())) {
            try{
                ryxx.setRyzt(SYSCONSTANT.BAQRYZT_ZS);
                ryxx.setCCssj(null);
                ryxx.setCCsyy("");
                ryxx.setCRyqx("");
                update(ryxx);
                // 修改出入所信心表状态为回所
                // Map<String, Object> params = new HashMap<String, Object>();
                // 修改出入所信心表状态为回所
                // 临时出所值更新人员状态,不更新cssp表
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("rybh", ryxx.getRybh());
                params.put("ryzt", SYSCONSTANT.LKLX_LSCS);
                List<ChasYmCssp> cssps = csspService.findList(params,"lrsj desc");
                if (!cssps.isEmpty()) {
                    ChasYmCssp cssp = cssps.get(0);
                    cssp.setRyzt(SYSCONSTANT.CSSP_RYZT_HS);
                    cssp.setHlsj(new Date());
                    csspService.update(cssp);
                }
                params.clear();
                params.put("baqid", ryxx.getBaqid());
                params.put("rybh", ryxx.getRybh());
                // map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
                ChasRyjl chasRyjl = ryjlService.findByParams(params);
                if (chasRyjl != null) {
                    // 三种情况 等候室 已分配审讯室未结束笔录 已分配结束笔录
                    if (StringUtil.isNotEmpty(chasRyjl.getSxsBh())) {
                        ChasSxsKz chasSxsKz = sxsKzService.findAllById(chasRyjl.getSxsBh());
                        if (SYSCONSTANT.Y_I == chasSxsKz.getIsdel()) {
                            dhsglService.dhsFp(ryxx.getBaqid(),ryxx.getRybh(),null);
                        }
                    }else{
                        dhsglService.dhsFp(ryxx.getBaqid(),ryxx.getRybh(),null);
                    }
                }

                BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(ryxx.getBaqid());
                if(baqConfiguration != null){
                    if(baqConfiguration.getQgc()){
                        BaqrysypUtil.uplodsaveAudioVideoByRyjc(ryxx,"9","300");
                        log.info("更改办案区，新增入区音视频!"+ryxx.getRyxm()+",办案区ID:"+ryxx.getBaqid());
                    }
                }
                map.put("success", true);
                map.put("msg", "人员归所成功");
            }catch (Exception e){
                log.error("人员归所失败:"+e);
            }
        }
        return map;
    }

    @Override
    public ChasBaqryxx findRyxxBywdbhL(String wdbhL) {
        return baseDao.findRyxxBywdbhL(wdbhL);
    }

    @Override
    public Map<String, Object> Departure(String rybh,String qwid,String fhzt) throws Exception {
        Map<String,Object> result = new HashMap<>();

        if(StringUtils.isEmpty(rybh)){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","人员编号不能为空!");
            return result;
        }

        //这两个参数，可能为空  因为可能出现没有物品的情况。
        /*if(StringUtils.isEmpty(qwid) || StringUtils.isEmpty(fhzt)){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","明细照片ID 或 返回状态 不能为空!");
            return result;
        }*/

        Map<String,Object> params = new HashMap<>();
        params.put("rybh",rybh);
        List<ChasBaqryxx> baqryxxes = findList(params,null);

        if(baqryxxes.isEmpty()){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","该人员可能已经离区,暂无数据");
            return result;
        }

        ChasBaqryxx ryxx = baqryxxes.get(0);
        BaqConfiguration baqznpz = baqznpzService.findByBaqid(ryxx.getBaqid());

        if(!StringUtils.equals(ryxx.getRyzt(),SYSCONSTANT.BAQRYZT_DCS)){  //不等于待出所
            result.put("code",-1);
            result.put("data",null);
            result.put("message","该人员未提交出所审批,或审批未通过!");
            return result;
        }

        if(baqznpz.getRycsSk()){ //智能柜出所(带签字版的模式)
            ryxx.setRyzt(SYSCONSTANT.BAQRYZT_YCS);
        }

        // 查询是否有出所时间,没有则添加
        if (ryxx != null && (ryxx.getCCssj() == null)) {
            ryxx.setCCssj(new Date());
        }

        //处理物品信息
        if(StringUtils.isNotEmpty(qwid)){  //出所总照片
            ChasSswpZpxx zpxx = new ChasSswpZpxx();
            zpxx.setId(StringUtils.getGuid32());
            zpxx.setLrrSfzh(ryxx.getLrrSfzh());
            zpxx.setLrsj(new Date());
            zpxx.setBaqid(ryxx.getBaqid());
            zpxx.setBaqmc(ryxx.getBaqmc());
            zpxx.setBizId(qwid);
            zpxx.setDataFlag("");
            zpxx.setIsdel(0);
            zpxx.setRybh(ryxx.getRybh());
            zpxx.setWpid("");
            zpxx.setZplx(SYSCONSTANT.SSWP_ZP_CS_ZL);  //照片总类
            zpxx.setXgrSfzh(ryxx.getTjxgrsfzh());
            zpxx.setXgsj(new Date());
            zpxxService.save(zpxx);
        }

        //处理随身物品信息
        //equals 方法中判断了是否为空!
        if(StringUtils.equals(fhzt,"2")){ //全部返回
            Map<String,Object> wpParams = new HashMap<>();
            wpParams.put("rybh",ryxx.getRybh());
            wpParams.put("wpzt", SYSCONSTANT.SSWPZT_ZSWP);
            List<ChasSswpxx> sswpxxes = sswpxxService.findList(wpParams,null);
            for(ChasSswpxx sswpxx : sswpxxes){
                sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);
                sswpxx.setWpclzt(SYSCONSTANT.SSWPZT_CSWP);
                sswpxxService.update(sswpxx);
            }
            ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);
        }
        /*else if(StringUtils.equals(fhzt,"0")){  //部分领回
            ChasSswpxx sswpxx = null;
            for(String wpid : wpids.split(",")){  //已领回的物品IDS
                sswpxx = sswpxxService.findById(wpid);
                sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);  //在所物品
                sswpxxService.update(sswpxx);
            }
            ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_BFCL);  //部分处理
        }*/

        // 修改人员表和人员记录表
        ryxx.setRyzt(SYSCONSTANT.BAQRYZT_YCS);
        ryxx.setCCsyy(DicUtil.translate("CSYY",ryxx.getCRyqx()));
        update(ryxx);
        ryRsCsSendMsg(ryxx);

        //查询人员记录表
        ChasRyjl ryjl = null;
        params.put("ryzt",SYSCONSTANT.BAQRYDCZT_JXZ);
        List<ChasRyjl> chasRyjls = ryjlService.findList(params,null);
        if(!chasRyjls.isEmpty()){
            ryjl = chasRyjls.get(0);
        }

        if(ryjl == null){
            log.info("{“"+ryxx.getRybh()+"”}该人员记录为空,出所操作中断！");
            throw new Exception("该人员的记录为空,出所操作中断!");
        }

        params.clear();
        params.put("baqid", ryjl.getBaqid());
        params.put("rybh", rybh);
        //params.put("wdbh", ryjl.getWdbhL());
        List<ChasRygj> irpfs = rygjService.findbyparams(params);
        ChasRygj rygj = null;
        if (irpfs.size() > 0) {
            ChasRygj pfe = irpfs.get(0);
            pfe.setJssj(new Date());
            rygjService.update(pfe);
            rygj = pfe;
        } else {
            log.info("办案区{“"+ryxx.getBaqid()+","+ryxx.getBaqmc()+"”,人员编号:"+ryxx.getRybh()+",腕带编号:"+ryjl.getWdbhL()+",不存在定位信息!}");
        }

        params.clear();
        params.put("rybh", rybh);
        params.put("lklx", SYSCONSTANT.LKLX_CS);
        params.put("spzt", SYSCONSTANT.SHZT_SPTG);
        List<ChasYmCssp> cssplist = csspService.findList(params, null);
        for (ChasYmCssp cssp : cssplist) {
            cssp.setLksj(ryxx.getCCssj());
            csspService.update(cssp);
        }

        ChasBaq baqTemp = baqService.findById(ryxx.getBaqid());
        if (baqTemp == null) {
            log.error("Departure-该人员办案区不存在-无法清除硬件数据记录");
            throw new Exception("该人员办案区不存在-无法清除硬件数据记录");
        }

        if(baqznpz.getSswp()){
            // 解除储物柜
            iWpgService.jcCwg(ryxx.getBaqid(), ryxx.getRybh());
            iWpgService.jcSjg(ryxx.getBaqid(),ryxx.getRybh());
            iWpgService.openBgCwg(ryxx.getBaqid(),ryxx.getRybh(),ryjl.getCwgBh());
            iWpgService.openBgCwg(ryxx.getBaqid(),ryxx.getRybh(),ryjl.getSjgBh());
        }
        if(baqznpz.getDw()){
            //【按需求[2795]去除行政、刑事拘留的判断】 if(!StringUtil.equals(ryxx.getCRyqx(),"04") && !StringUtil.equals(ryxx.getCRyqx(),"01")){  //人员如果是送押的话 那么就不解除腕带
            // 解除手环
            if(!baqznpz.getRycsSk() && !baqznpz.getDwHkrl()){
                iWdService.wdJc(ryjl);
            }
            //【按需求[2795]去除行政、刑事拘留的判断】 }

            if(baqznpz.getDwHkrl()){//结束海康人脸定位
                endRldw(ryxx.getBaqid(), rybh, ryxx.getRegisterCode());
            }
            new Thread(() -> {
                iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("R",ryxx.getId(),ryxx.getRyxm(),ryxx.getZpid(),ryxx.getBaqid(),new Date());
            }).start();
        }
        //if(StringUtil.equals(baqznpz.getLed(),"1")){
        dhsKzService.clearByrybh(ryxx.getRybh());
        sxsKzService.clearByrybh(ryxx.getRybh());
        // 解除等候室分配记录 刷新LED
        dhsglService.dhsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        // 解除审讯室分配记录 刷新LED
        sxsglService.sxsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        log.info("人员出所逻辑删除分配表:"+ryxx.getRybh());
        //}
        // 设置出所时间
        ryjl.setQzsj(new Date());
        ryjl.setIsdel(1);
        ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JS);
        ryjl.setLczt(SYSCONSTANT.LCJD_CS);  //流程节点  出所
        ryjlService.update(ryjl);
        // 查询下是否存在申请亲情驿站没有使用 如果没有使用就把记录给结束掉
        qqyzService.unusedPhone(ryjl.getId(), ryjl.getBaqid());
        if(rygj != null){
            apiRygjService.sendLastVmsInfo(rygj.getBaqid(),rygj.getRybh(),rygj.getQyid());
        }
        /*ChasRygjmapService rygjmapService = ServiceContext.getServiceByClass(ChasRygjmapService.class);
        rygjmapService.buildLocaltionlkInfo(ryjl.getRybh());
        PushBecauseofTygkUtil.PushLeaveZoneInformationByRy(ryxx);*/
        result.put("code",0);
        result.put("data",null);
        result.put("message","人员出所成功!");
        log.info("办案区人员:"+ryxx.getRybh()+",出所成功!");
        return result;
    }

    @Override
    public void DepartureBynotDevice(ChasBaqryxx ryxx, String qwid) throws Exception {
        SessionUser user = WebContext.getSessionUser();
        Map<String,Object> result = new HashMap<>();

        if(!StringUtils.equals(ryxx.getRyzt(),SYSCONSTANT.BAQRYZT_DCS)){  //不等于待出所
            throw new Exception("该人员未提交出所审批,或审批未通过!");
        }

        if (SYSCONSTANT.BAQRYZT_DCS.equals(ryxx.getRyzt())) {
            ryxx.setRyzt(SYSCONSTANT.BAQRYZT_YCS);
        }

        // 查询是否有出所时间,没有则添加
        if (ryxx != null && (ryxx.getCCssj() == null)) {
            ryxx.setCCssj(new Date());
        }

        //处理物品信息
        if(StringUtils.isNotEmpty(qwid)){  //出所总照片
            //在确认出所页面选择完照片后，会直接保存该照片信息，所以这里发现以保存的则不做处理。
            ChasSswpZpxx zpxx = zpxxService.findZpxxByBizId(qwid);
            if(zpxx == null){
                zpxx = new ChasSswpZpxx();
                zpxx.setId(StringUtils.getGuid32());
                zpxx.setLrrSfzh(ryxx.getLrrSfzh());
                zpxx.setLrsj(new Date());
                zpxx.setBaqid(ryxx.getBaqid());
                zpxx.setBaqmc(ryxx.getBaqmc());
                zpxx.setBizId(qwid);
                zpxx.setDataFlag("");
                zpxx.setIsdel(0);
                zpxx.setRybh(ryxx.getRybh());
                zpxx.setWpid("");
                zpxx.setZplx(SYSCONSTANT.SSWP_ZP_CS_ZL);  //照片总类
                zpxx.setXgrSfzh(user.getIdCard());
                zpxx.setXgsj(new Date());
                zpxxService.save(zpxx);
            }
        }

        //处理随身物品信息(已在人员出区流程后置事件处理了)
        /*Map<String,Object> wpParams = new HashMap<>();
        wpParams.put("rybh",ryxx.getRybh());
        wpParams.put("wpzt", SYSCONSTANT.SSWPZT_ZSWP);
        List<ChasSswpxx> sswpxxes = sswpxxService.findList(wpParams,null);
        for(ChasSswpxx sswpxx : sswpxxes){
            sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);
            sswpxxService.update(sswpxx);
        }
        ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);*/

        // 修改人员表和人员记录表
        update(ryxx);

        //查询人员记录表
        Map<String,Object> params = new HashMap<>();
        ChasRyjl ryjl = null;
        params.put("ryzt",SYSCONSTANT.BAQRYDCZT_JXZ);
        params.put("rybh",ryxx.getRybh());
        List<ChasRyjl> chasRyjls = ryjlService.findList(params,null);
        if(!chasRyjls.isEmpty()){
            ryjl = chasRyjls.get(0);
        }

        if(ryjl == null){
            log.info("{“"+ryxx.getRybh()+"”}该人员记录为空,出所操作中断！");
            throw new Exception("该人员的记录为空,出所操作中断!");
        }

        params.clear();
        params.put("baqid", ryjl.getBaqid());
        params.put("rybh", ryxx.getRybh());
        //params.put("wdbh", ryjl.getWdbhL());
        List<ChasRygj> irpfs = rygjService.findbyparams(params);
        ChasRygj rygj = null;
        if (irpfs.size() > 0) {
            ChasRygj pfe = irpfs.get(0);
            pfe.setJssj(new Date());
            rygjService.update(pfe);
            rygj = pfe;
        } else {
            log.info("办案区{“"+ryxx.getBaqid()+","+ryxx.getBaqmc()+"”,人员编号:"+ryxx.getRybh()+",腕带编号:"+ryjl.getWdbhL()+",不存在定位信息!}");
        }

        params.clear();
        params.put("rybh", ryxx.getRybh());
        params.put("lklx", SYSCONSTANT.LKLX_CS);
        params.put("spzt", SYSCONSTANT.SHZT_SPTG);
        List<ChasYmCssp> cssplist = csspService.findList(params, null);
        for (ChasYmCssp cssp : cssplist) {
            cssp.setLksj(ryxx.getCCssj());
            csspService.update(cssp);
        }

        ChasBaq baqTemp = baqService.findById(ryxx.getBaqid());
        if (baqTemp == null) {
            log.error("DepartureBynotDevice-该人员办案区不存在-无法清除硬件数据记录");
            throw new Exception("该人员办案区不存在-无法清除硬件数据记录");
        }

        BaqConfiguration baqznpz = baqznpzService.findByBaqid(ryxx.getBaqid());

        if(baqznpz.getSswp()){
            // 解除储物柜
            iWpgService.jcCwg(ryxx.getBaqid(), ryxx.getRybh());
            iWpgService.jcSjg(ryxx.getBaqid(),ryxx.getRybh());
            iWpgService.openBgCwg(ryxx.getBaqid(),ryxx.getRybh(),ryjl.getCwgBh());
            iWpgService.openBgCwg(ryxx.getBaqid(),ryxx.getRybh(),ryjl.getSjgBh());
        }
        if(baqznpz.getDw()){
           //【按需求[2795]去除行政、刑事拘留的判断】  if(!StringUtil.equals(ryxx.getCRyqx(),"04") && !StringUtil.equals(ryxx.getCRyqx(),"01")){
                // 解除手环
            if(!baqznpz.getRycsSk()) {
                iWdService.wdJc(ryjl);
            }
           //【按需求[2795]去除行政、刑事拘留的判断】     }
        }
        // 查询下是否存在申请亲情驿站没有使用 如果没有使用就把记录给结束掉
        qqyzService.unusedPhone(ryjl.getId(), ryjl.getBaqid());
        //if(StringUtil.equals(baqznpz.getLed(),"1")){  //没有配置led 代表没有等候室和审讯室
        dhsKzService.clearByrybh(ryxx.getRybh());
        sxsKzService.clearByrybh(ryxx.getRybh());
        // 解除等候室分配记录 刷新LED
        dhsglService.dhsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        // 解除审讯室分配记录 刷新LED
        sxsglService.sxsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        log.info("人员出所逻辑删除分配表:"+ryxx.getRybh());
        //}
        // 设置出所时间
        ryjl.setQzsj(new Date());
        ryjl.setIsdel(1);
        ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JS);
        ryjl.setLczt(SYSCONSTANT.LCJD_CS);  //流程节点  出所
        ryjlService.update(ryjl);
        if(rygj != null){
            apiRygjService.sendLastVmsInfo(rygj.getBaqid(),rygj.getRybh(),rygj.getQyid());
        }
        log.info("办案区人员:"+ryxx.getRybh()+",出所成功!");
    }

    @Override
    public void DepartureBynotProcess(ChasBaqryxx ryxx, ChasYmCssp cssp, String wpztData) throws Exception {
        BaqConfiguration baqConfiguration = baqznpzService.findByBaqid(ryxx.getBaqid());
        if(ryxx.getCCssj() == null){
            ryxx.setCCssj(new Date());
        }
        ryxx.setRyzt(SYSCONSTANT.BAQRYZT_YCS);
        if(StringUtil.isNotEmpty(wpztData)){
            //修改物品处理状态
            List<Map> maps = JsonUtil.getListFromJsonString(wpztData,Map.class);
            for(Map<String,Object> map : maps){
                String id = (String) map.get("id");
                String wpclzt = (String) map.get("wpclzt");
                ChasSswpxx sswpxx = sswpxxService.findById(id);
                if(sswpxx != null){
                    sswpxx.setWpclzt(wpclzt);
                    sswpxxService.update(sswpxx);
                }
            }
        }

        //修改物品状态
        Map<String,Object> params = new HashMap<>();
        params.put("rybh",ryxx.getRybh());
        List<ChasSswpxx> sswpxxes = sswpxxService.findList(params,null);
        boolean flag = true;
        for(ChasSswpxx sswpxx : sswpxxes){
            //除了 转扣押  转涉案 其他的都改为已出区。
            if(!StringUtil.equals(sswpxx.getWpclzt(),"04") && !StringUtil.equals(sswpxx.getWpclzt(),"05")){
                sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);
                sswpxxService.update(sswpxx);
            }else{
                flag = false;  //部分处理
            }
        }

        if(!flag){
            ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_BFCL);  //部分处理
        }else{
            //已处理
            ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);  //部分处理
        }
        cssp.setLklx(SYSCONSTANT.CSSP_SHLX_CS);
        cssp.setLksj(ryxx.getCCssj());
        update(ryxx);
        ryRsCsSendMsg(ryxx);
        csspService.save(cssp);

        //查询人员记录表
        ChasRyjl ryjl = null;
        params.clear();
        params.put("ryzt",SYSCONSTANT.BAQRYDCZT_JXZ);
        params.put("rybh",ryxx.getRybh());
        List<ChasRyjl> chasRyjls = ryjlService.findList(params,null);
        if(!chasRyjls.isEmpty()){
            ryjl = chasRyjls.get(0);
        }

        if(ryjl == null){
            log.info("{“"+ryxx.getRybh()+"”}该人员记录为空,出所操作中断！");
            throw new Exception("该人员的记录为空,出所操作中断!");
        }

        params.clear();
        params.put("baqid", ryjl.getBaqid());
        params.put("rybh", ryxx.getRybh());
        //params.put("wdbh", ryjl.getWdbhL());
        List<ChasRygj> irpfs = rygjService.findbyparams(params);
        if (irpfs.size() > 0) {
            ChasRygj pfe = irpfs.get(0);
            pfe.setJssj(new Date());
            rygjService.update(pfe);
        } else {
            log.info("办案区{“"+ryxx.getBaqid()+","+ryxx.getBaqmc()+"”,人员编号:"+ryxx.getRybh()+",腕带编号:"+ryjl.getWdbhL()+",不存在定位信息!}");
        }

        ChasBaq baqTemp = baqService.findById(ryxx.getBaqid());
        if (baqTemp == null) {
            log.error("DepartureBynotDevice-该人员办案区不存在-无法清除硬件数据记录");
            throw new Exception("该人员办案区不存在-无法清除硬件数据记录");
        }
        if(baqConfiguration.getSswp()){
            iWpgService.jcCwg(ryxx.getBaqid(), ryxx.getRybh());
            iWpgService.jcSjg(ryxx.getBaqid(),ryxx.getRybh());
        }
        if(baqConfiguration.getDw()){
            // 【按需求[2795]去除行政、刑事拘留的判断】if(!StringUtil.equals(ryxx.getCRyqx(),"01") && !StringUtil.equals(ryxx.getCRyqx(),"04")){
            iWdService.wdJc(ryjl);
            // 【按需求[2795]去除行政、刑事拘留的判断】}
        }
        // 查询下是否存在申请亲情驿站没有使用 如果没有使用就把记录给结束掉
        qqyzService.unusedPhone(ryjl.getId(), ryjl.getBaqid());
        dhsKzService.clearByrybh(ryxx.getRybh());
        sxsKzService.clearByrybh(ryxx.getRybh());
        // 解除等候室分配记录 刷新LED(LED在另一端控制)
        dhsglService.dhsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        // 解除审讯室分配记录 刷新LED(LED在另一端控制)
        sxsglService.sxsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
        // 设置出所时间
        ryjl.setIsdel(1);
        ryjl.setQzsj(new Date());
        ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JS);
        ryjl.setLczt(SYSCONSTANT.LCJD_CS);  //流程节点  出所
        ryjlService.update(ryjl);
        log.info("办案区人员:"+ryxx.getRybh()+",出所成功!");
    }

    @Override
    public List<ChasBaqryxx> findTaryByRyjlYwbh(String ywbh) {
        return baseDao.findTaryByRyjlYwbh(ywbh);
    }

    @Override
    public String getRyxxCount(String baqid, String ryzt) {
        return null;
    }

    @Override
    public ChasBaqryxx getRyxxObj(ChasBaqryxx ryxx) {
        if (ryxx.getSfzbxm() == null) {
            ryxx.setSfzbxm(0);
        }
        if (ryxx.getSfsfbm() == null) {
            ryxx.setSfsfbm(0);
        }
        if (StringUtils.isEmpty(ryxx.getHjdxzqh()))
            ryxx.setHjdxz(ryxx.getHjdxz());
        // 设置年龄
        if (ryxx.getCsrq() != null) {
            Calendar cal = Calendar.getInstance();
            int nyear = cal.get(Calendar.YEAR);
            cal.setTime(ryxx.getCsrq());
            int cyear = cal.get(Calendar.YEAR);
            ryxx.setNl(nyear - cyear);
        }
        // 办案区人员使用编号
        if (StringUtils.isEmpty(ryxx.getId())) {
            ryxx.setBaqsyqkbh(getbaqjlbh(ryxx.getBaqid(), ryxx.getRRssj()));
        }
        if (ryxx.getCCsyy() == null)
            ryxx.setCCsyy("");
        if(StringUtils.isNotEmpty(ryxx.getZbdwBh())){
            ryxx.setDwxtbh(DicUtil.translate("JDONE_SYS_SYSCODE", ryxx.getZbdwBh()));
        }

        /**
         * 人员入所信息：是否严重疾病
         */
        Integer rSfyzjb;
        if ("无".equals(ryxx.getRYzjb())) {
            rSfyzjb = 0;
        } else {
            if (!"".equals(ryxx.getRYzjb()) && ryxx.getRYzjb() != null) {
                rSfyzjb = 1;
            } else {
                rSfyzjb = 0;
            }
        }

        /**
         * 人员入所信息：是否伤势检查,是否特殊体表特征,有无伤情
         */
        Integer rSfssjc;
        if ("无".equals(ryxx.getRSsms())) {
            rSfssjc = 0;
        } else {
            if (!"".equals(ryxx.getRSsms()) && ryxx.getRSsms() != null) {
                rSfssjc = 1;
            } else {
                rSfssjc = 0;
            }
        }

        ryxx.setRSfyzjb(rSfyzjb);
        ryxx.setRSfssjc(rSfssjc);
        return ryxx;
    }

    @Override
    public void deleteBaqryxxs(String id) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        ChasBaqryxx baqryxx = findById(id);
        if (baqryxx != null && StringUtils.isNotEmpty(baqryxx.getRybh())) {
            //结束人脸定位
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqryxx.getBaqid());
            if(configuration.getDwHkrl()){
                endRldw(baqryxx.getBaqid(), baqryxx.getRybh(), baqryxx.getRegisterCode());
            }
            //删除下发的海康人脸门禁
            new Thread(() -> {
                iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("R",baqryxx.getId(),baqryxx.getRyxm(),baqryxx.getZpid(),baqryxx.getBaqid(),new Date());
            }).start();
            params.put("rybh", baqryxx.getRybh());
            // 智能办案区
            if (SYSCONSTANT.Y_I == baqryxx.getSfznbaq()) {
                // 解除所有硬件关联
                iWpgService.jcCwg(baqryxx.getBaqid(),baqryxx.getRybh());  //解除储物柜
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(),baqryxx.getRybh());
                iWdService.wdJc(ryjl);
                dhsglService.dhsJc(baqryxx.getBaqid(),baqryxx.getRybh(),true);
                sxsglService.sxsJc(baqryxx.getBaqid(),baqryxx.getRybh(),true);
            }
            // 删除办案区人员信息
            deleteById(baqryxx.getId());
            // 删除审核信息
            List<ChasYmCssp> cssps = csspService.findList(params,null);
            for (ChasYmCssp cssp : cssps) {
                csspService.deleteById(cssp.getId());
            }
            // 删除随身物品
            List<ChasSswpxx> wpxxs = sswpxxService.findList(params,null);
            for (ChasSswpxx wpxx : wpxxs) {
                sswpxxService.deleteById(wpxx.getId());
            }
            //删除物品照片信息
            List<ChasSswpZpxx> zpxxes = zpxxService.findList(params,null);
            for(ChasSswpZpxx zpxx : zpxxes){
                zpxxService.deleteById(zpxx.getId());
            }
            // 删除一体化采集
                /*List<ChasYthcj> ythcjs = ythcjService.findList(params, null);
                for (ChasYthcj ythcj : ythcjs) {
                    ythcjService.deleteById(ythcj.getId());
                }*/
            List<ChasythcjQk> chasythcjQks = ythcjQkService.findList(params,null);
            for(ChasythcjQk ythcjQk : chasythcjQks){
                ythcjQkService.deleteById(ythcjQk.getId());
            }
            // 删除轨迹
            List<ChasRygj> rygjs = rygjService.findbyparams(params);
            for (ChasRygj rygj : rygjs) {
                rygjService.deleteById(rygj.getId());
            }
            //删除人员记录
            List<ChasRyjl> ryjls = ryjlService.findList(params,null);
            for(ChasRyjl ryjl : ryjls){
                ryjlService.deleteById(ryjl.getId());
            }
            // 删除尿检 尿检暂无处理
            List<ChasYwNj> njs = njService.findList(params, null);
            for (ChasYwNj nj : njs) {
                njService.deleteById(nj.getId());
            }
            // 删除亲情驿站 申请数据
            List<ChasQqyz> qqyzs = qqyzService.findbyparams(params);
            for (ChasQqyz qqyz : qqyzs) {
                qqyzService.deleteById(qqyz.getId());
            }
            //删除人脸特征
            baqFaceService.delete(baqryxx.getBaqid(),baqryxx.getRybh(),FaceTzlx.BAQRY.getCode());
            ryScSendMsg(baqryxx);
        }
    }

    @Override
    public List<Map<String, Object>> getDataByYwbh(Map<String, Object> params) {
        return baseDao.getDataByYwbh(params);
    }

    @Override
    public ChasBaqryxx findByRysfzh(String sfzh) {
        return baseDao.findBaqryxBySfzh(sfzh);
    }

    @Override
    public ChasBaqryxx findByRybh(String rybh) {
        return baseDao.findBaqryxByRybh(rybh);
    }

    @Override
    public Map<String, String> getwgsjtj(String sysOrgCode) {
        return baseDao.getwgsjtj(sysOrgCode);
    }

    @Override
    public ChasBaqryxx findBaqryxByRybhAll(String rybh,String baqid) {
        return baseDao.findBaqryxByRybhAll(rybh,baqid);
    }
    @Override
    public ChasBaqryxx findBaqryxBySfzhAll(String sfzh,String baqid) {
        return baseDao.findBaqryxBySfzhAll(sfzh,baqid);
    }
    @Override
    public void deavebatchBaqry(String rybhs, String csyy, String qtyy) {
        for(String rybh : rybhs.split(",")){
            if(StringUtils.isEmpty(rybh)){
                continue;
            }

            Map<String,Object> params = new HashMap<>();
            params.put("rybh",rybh);
            List<ChasBaqryxx> baqryxxes = findList(params,null);

            if(baqryxxes.isEmpty()){
                continue;
            }

            ChasBaqryxx ryxx = baqryxxes.get(0);

            // 查询是否有出所时间,没有则添加
            if (ryxx != null && (ryxx.getCCssj() == null)) {
                ryxx.setCCssj(new Date());
            }

            Map<String,Object> wpParams = new HashMap<>();
            wpParams.put("rybh",ryxx.getRybh());
            wpParams.put("wpzt", SYSCONSTANT.SSWPZT_ZSWP);
            List<ChasSswpxx> sswpxxes = sswpxxService.findList(wpParams,null);
            for(ChasSswpxx sswpxx : sswpxxes){
                sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);
                sswpxxService.update(sswpxx);
            }

            if(!sswpxxes.isEmpty()){
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);
            }

            ryxx.setRyzt(SYSCONSTANT.BAQRYZT_YCS);
            ryxx.setCRyqx(csyy);
            if(StringUtil.equals(csyy,"99")){
                ryxx.setCCsyy(qtyy);
            }else{
                ryxx.setCCsyy(DicUtil.translate("CSYY",csyy));
            }
            // 修改人员表和人员记录表
            update(ryxx);
            ryRsCsSendMsg(ryxx);

            //添加出所审批记录
            ChasYmCssp cssp = new ChasYmCssp();
            cssp.setId(StringUtils.getGuid32());
            cssp.setLrrSfzh(ryxx.getLrrSfzh());
            cssp.setLrsj(new Date());
            cssp.setXgrSfzh(ryxx.getTjxgrsfzh());
            cssp.setBaqid(ryxx.getBaqid());
            cssp.setBaqmc(ryxx.getBaqmc());
            cssp.setRybh(ryxx.getRybh());
            cssp.setRyxm(ryxx.getRyxm());
            cssp.setLkyy(ryxx.getCCsyy());
            cssp.setLkyydm(ryxx.getCRyqx());
            cssp.setZbdwBh(ryxx.getZbdwBh());
            cssp.setZbdwMc(ryxx.getZbdwMc());
            cssp.setZbrSfzh(ryxx.getMjSfzh());
            cssp.setZbrXm(ryxx.getMjXm());
            cssp.setSpzt(SYSCONSTANT.SHZT_SPTG);
            //cssp.setRyzt(SYSCONSTANT.CSSP_RYZT_ZS);
            cssp.setLklx(SYSCONSTANT.CSSP_SHLX_CS);
            cssp.setLksj(new Date());
            cssp.setDwxtbh(ryxx.getDwxtbh());
            csspService.save(cssp);

            //查询人员记录表
            ChasRyjl ryjl = null;
            params.put("ryzt",SYSCONSTANT.BAQRYDCZT_JXZ);
            List<ChasRyjl> chasRyjls = ryjlService.findList(params,null);
            if(!chasRyjls.isEmpty()){
                ryjl = chasRyjls.get(0);
            }

            if(ryjl == null){
                log.info("{“"+ryxx.getRybh()+"”}该人员记录为空,出所操作中断！");
                continue;
            }

            params.clear();
            params.put("baqid", ryjl.getBaqid());
            params.put("rybh", rybh);
            params.put("wdbh", ryjl.getWdbhL());
            List<ChasRygj> irpfs = rygjService.findbyparams(params);
            ChasRygj rygj = null;
            if (irpfs.size() > 0) {
                ChasRygj pfe = irpfs.get(0);
                pfe.setJssj(new Date());
                rygjService.update(pfe);
                rygj = pfe;
            } else {
                log.info("办案区{“"+ryxx.getBaqid()+","+ryxx.getBaqmc()+"”,人员编号:"+ryxx.getRybh()+",腕带编号:"+ryjl.getWdbhL()+",不存在定位信息!}");
            }

            ChasBaq baqTemp = baqService.findById(ryxx.getBaqid());
            if (baqTemp == null) {
                log.error("Departure-该人员办案区不存在-无法清除硬件数据记录");
                continue;
            }
            // 解除储物柜
            iWpgService.jcCwg(ryxx.getBaqid(), ryxx.getRybh());
            iWpgService.jcSjg(ryxx.getBaqid(),ryxx.getRybh());
            if(!StringUtil.equals(ryxx.getCRyqx(),"04") && !StringUtil.equals(ryxx.getCRyqx(),"01")){
                // 解除手环
                iWdService.wdJc(ryjl);
            }
            // 解除等候室分配记录 刷新LED
            dhsglService.dhsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
            // 解除审讯室分配记录 刷新LED
            sxsglService.sxsJc(ryxx.getBaqid(), ryxx.getRybh(), SYSCONSTANT.Y_B);
            // 设置出所时间
            ryjl.setQzsj(new Date());
            ryjl.setIsdel(1);
            ryjl.setRyzt(SYSCONSTANT.BAQRYDCZT_JS);
            ryjl.setLczt(SYSCONSTANT.LCJD_CS);  //流程节点  出所
            ryjlService.update(ryjl);
            // 查询下是否存在申请亲情驿站没有使用 如果没有使用就把记录给结束掉
            qqyzService.unusedPhone(ryjl.getId(), ryjl.getBaqid());
            if(rygj != null){
                apiRygjService.sendLastVmsInfo(rygj.getBaqid(),rygj.getRybh(),rygj.getQyid());
            }
        }
    }

    @Override
    public PageDataResultSet<ChasBaqryxxBq> getEntityPageDataBecauseQymc(int var1, int var2, Object var3, String var4) {
        MybatisPageDataResultSet<ChasBaqryxxBq> mybatisPageData = baseDao.getEntityPageDataBecauseQymc(var1, var2, var3, var4);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }

    @Override
    public boolean isWcn(ChasBaqryxx baqryxx) {
        if(baqryxx==null){
            return false;
        }
        if(SYSCONSTANT.TSQT_WCN.equals(baqryxx.getTsqt())){
            return true;
        }
        if(baqryxx.getNl()!=null){
            return baqryxx.getNl()>0&&baqryxx.getNl()<18;
        }
        return false;
    }

    @Override
    public int clearByRybh(String rybh) {
        if(StringUtils.isNotEmpty(rybh)){
            baseDao.clearByRybh(rybh);
        }
        return 0;
    }

    /**
     * 根据腕带编号获取送押人员信息
     * @param wdbhl
     * @return
     */  /**
     * 根据腕带编号获取送押人员信息
     * @param wdbhl
     * @param baqid
     * @return
     */
    @Override
    public ChasBaqryxx getSyryByWdbhl(String wdbhl, String baqid) {
        ChasRyjl chasRyjl = ryjlService.findByWdbhLUndel( wdbhl);
        if (chasRyjl == null) {
            return null;
        }
        ChasBaqryxx baqryxx = baseDao.findBaqryxByRybh(chasRyjl.getRybh());
        if(StringUtil.equals(baqryxx.getRyzt(),SYSCONSTANT.BAQRYZT_YCS)){
            if("01".equals(baqryxx.getCRyqx()) || "04".equals(baqryxx.getCRyqx())){//拘留
                return baqryxx;
            }
        }
        return null;
    }

    /**
     * 获取当前最新腕带使用人信息
     *
     * @param wdbhl
     * @return
     */
    @Override
    public ChasBaqryxx findCurrentByWdbhl(String wdbhl) {
        return baseDao.findCurrentByWdbhl(wdbhl);
    }

    @Override
    public Map<String,Object> saveOrUpdateRldw(ChasBaqryxx chasBaqryxx){
        log.info("办案区人员上传人脸定位信息：" + JSON.toJSONString(chasBaqryxx));
        HikFaceLocationRyxx locationRyxx = new HikFaceLocationRyxx();
        locationRyxx.setPlaceCode(chasBaqryxx.getBaqid());
        locationRyxx.setPersonId(chasBaqryxx.getRybh());
        locationRyxx.setPersonName(chasBaqryxx.getRyxm());
        locationRyxx.setCardType(chasBaqryxx.getZjlx());
        locationRyxx.setCardNo(chasBaqryxx.getRysfzh());
        locationRyxx.setPersonType("1");
        String bizId = chasBaqryxx.getZpid();
        FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
        locationRyxx.setPhotoUrl(fileInfoObj.getDownUrl());
        locationRyxx.setEnableFace(1);
        Map<String, Object> response = new HashMap<>();
        if(StringUtils.isNotEmpty(chasBaqryxx.getRegisterCode())){
            log.info(chasBaqryxx.getRybh() + "人员更新人脸定位信息");
            //海康人员定位更新信息接口仍存在问题，目前返回保存失败，现场貌似成功了
//            IByteFileObj iByteFileObj = FrwsApiForThirdPart.downloadByteFileByBizId(bizId);
//            String base64 = Base64.encodeBase64String(iByteFileObj.getBytes());
//            String type = StringUtils.isNotEmpty(fileInfoObj.getFileType()) ? fileInfoObj.getFileType() : "png";
//            locationRyxx.setPhotoUrl("data:image/" + type + ";base64," + base64);
            response = faceLocationService.updateHikLocationRyxx(locationRyxx, chasBaqryxx.getRegisterCode());
        }else{
            log.info(chasBaqryxx.getRybh() + "人员开始人脸定位信息");
            response = faceLocationService.startLocation(locationRyxx);
        }
        if((Boolean) response.get("status")){
            String glid = String.valueOf(response.get("registerCode"));
            chasBaqryxx.setRegisterCode(glid);
            update(chasBaqryxx);
        }
        return response;
    }

    @Async
    @Override
    public void endRldw(String baqid, String rybh, String registerCode){
        log.info("结束人脸定位人员编号：" + rybh);
        log.info("结束人脸定位唯一标识：" + registerCode);
        if(StringUtils.isNotEmpty(registerCode)){
            Map<String, Object> response = faceLocationService.endLocation(baqid, registerCode);
            if((Boolean) response.get("status")){
                log.info("人员" + rybh + "已结束人脸定位");
            }else{
                log.warn("人员" + rybh + "结束人脸定位失败：" + response.get("msg"));
            }
        }else{
            log.info("未查询到人员" + rybh + "开启人脸定位，无需结束");
        }
    }

    @Override
    public PageDataResultSet<ChasBaqryxx> getYfpwpgBaqryxx(Integer page, Integer rows, Map<String, Object> param,String orderBy) {
        MybatisPageDataResultSet<ChasBaqryxx> mybatisPageData =this.baseDao.getYfwpgBaqryxx(page, rows, param, orderBy);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }


    public void sendMessageTozfba(ChasBaqryxx ryxx){
        try{
            FrameworkMessageObj msg = new FrameworkMessageObj();
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("rybh", ryxx.getRybh());
            msgData.put("baqrybh", ryxx.getRybh());
            MessageProduceCenter.send(msg, "CHAS_RYXX_ADD", msgData,"zfpt-zfba");
        }catch (Exception e){
            log.error("sendMessageTozfba:",e);
        }
    }

    public void relationYwbh(String ywbhStr,ChasBaqryxx ryxx){
        try{
            if (StringUtils.isNotEmpty(ywbhStr)) {
                if (ywbhStr.startsWith("J")) {
                    ApiGlRjglService apiGlRjglService = ServiceContext.getServiceByClass(ApiGlRjglService.class);
                    List<String> rybhList = apiGlRjglService.getRybhByJqbh(ryxx.getJqbh());
                    if (!rybhList.contains(ryxx.getRybh())) {
                        saveRjgx(ryxx, ryxx.getJqbh());
                    }
                } else if (ywbhStr.startsWith("A")) {
                    ApiGlRaglService apiRaglService = ServiceContext.getServiceByClass(ApiGlRaglService.class);
                    List<String> rybhList = apiRaglService.getRybhByAjbh(ryxx.getAjbh());
                    if (!rybhList.contains(ryxx.getRybh())) {
                        saveRagx(ryxx, ryxx.getAjbh());
                    }
                }
            }
        }catch (Exception e){
            log.error("relationYwbh:",e);
        }
    }

    private void saveRjgx(ChasBaqryxx ryxx, String jqbh) {
        // 同步数据到zfba
        ApiGlRjgl rjgl = new ApiGlRjgl();
        rjgl.setId(StringUtils.getGuid32());
        rjgl.setRybh(ryxx.getRybh());
        rjgl.setJqbh(jqbh);
        if(WebContext.getSessionUser() != null){
            rjgl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
            rjgl.setTjxgrSfzh(WebContext.getSessionUser().getIdCard());
        }else{
            rjgl.setLrrSfzh(ryxx.getLrrSfzh());
            rjgl.setTjxgrSfzh(ryxx.getTjxgrsfzh());
        }
        rjgl.setTjxgsj(new Date());
        ApiGlRjglService apiGlRjglService = ServiceContext.getServiceByClass(ApiGlRjglService.class);
        ApiGgXyrxx xyr = new ApiGgXyrxx();
        setXyrXx(ryxx, xyr);
        xyr.setXyrly("baq-jq");
        apiGlRjglService.saveRjgl(rjgl, xyr);
    }

    private void saveRagx(ChasBaqryxx ryxx, String ajbh) {
        // 同步数据到zfba
        ApiGlRagl ragl = new ApiGlRagl();
        ragl.setId(StringUtils.getGuid32());
        ragl.setRybh(ryxx.getRybh());
        ragl.setAjbh(ajbh);
        ragl.setLrsj(new Date());
        if (WebContext.getSessionUser() != null) {
            ragl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
            ragl.setTjxgrSfzh(WebContext.getSessionUser().getIdCard());
        }else{
            ragl.setLrrSfzh(ryxx.getLrrSfzh());
            ragl.setTjxgrSfzh(ryxx.getTjxgrsfzh());
        }
        ragl.setTjxgsj(new Date());
        ApiGlRaglService apiGlRaglService = ServiceContext.getServiceByClass(ApiGlRaglService.class);
        ApiGgXyrxx xyr = new ApiGgXyrxx();
        setXyrXx(ryxx, xyr);
        //人员案由使用案件案由
        ApiGgAjxxService ajxxService = ServiceContext.getServiceByClass(ApiGgAjxxService.class);
        ApiGgAjxx ajxx = ajxxService.getAjxxByAjbh(ajbh);
        xyr.setRyZaybh(ajxx.getAybh());
        xyr.setRyZaymc(ajxx.getXgaymc());
        xyr.setXyrly("baq-aj");
        apiGlRaglService.saveRagl(ragl, xyr);
    }

    private void setXyrXx(ChasBaqryxx ryxx, ApiGgXyrxx xyr) {
        xyr.setBaqRybh(ryxx.getRybh());
        xyr.setBm(ryxx.getBm());
        xyr.setCh(ryxx.getCh());
        xyr.setCsrq(ryxx.getCsrq());
        xyr.setCym(ryxx.getCym());
        xyr.setDataFlag(ryxx.getDataflag());
        xyr.setGj(ryxx.getGj());
        xyr.setGzdw(ryxx.getGzdw());
        xyr.setHjdxz(ryxx.getHjdxz());
        xyr.setHjdXzqh(ryxx.getHjdxzqh());
        xyr.setId(ryxx.getId());
        xyr.setIsdel(0);
        xyr.setLrdwBh(ryxx.getZbdwBh());
        xyr.setLrdwXtbh(ryxx.getDwxtbh());
        xyr.setLrrSfzh(ryxx.getLrrSfzh());
        xyr.setLrsj(ryxx.getLrsj());
        xyr.setLxfs(ryxx.getLxdh());
        xyr.setMz(ryxx.getMz());
        xyr.setRybh(ryxx.getRybh());
        xyr.setRySfzh(ryxx.getRysfzh());
        xyr.setRyxm(ryxx.getRyxm());
        xyr.setRyZaybh(ryxx.getRyzaybh());
        xyr.setRyZaymc(ryxx.getRyzaymc());
        xyr.setSfsfbm(ryxx.getSfsfbm());
        // xyr.setSftsqt(ryxx.getSftsqt());
        xyr.setXb(ryxx.getXb());
        xyr.setXzdxz(ryxx.getXzdxz());
        xyr.setXzdXzqh(ryxx.getXzdxzqh());
        xyr.setZjhm(ryxx.getRysfzh());
        xyr.setZjlx(ryxx.getZjlx());
        xyr.setZpid(ryxx.getZpid());
        xyr.setZzmm(ryxx.getZzmm());
        xyr.setSfzbxm(ryxx.getSfzbxm());
        xyr.setWhcd(ryxx.getWhcd());
        xyr.setTjxgsj(new Date());
        if(WebContext.getSessionUser() != null){
            xyr.setTjxgrSfzh(WebContext.getSessionUser().getIdCard());
        }else{
            xyr.setTjxgrSfzh(ryxx.getTjxgrsfzh());
        }
    }

    public void ryRsCsSendMsg(ChasBaqryxx chasBaqryxx) {
        String chas_ryxx_ryrscs = "CHAS_RYXX_RYRS";
        // chas_ryxx_ryrscs="CHAS_RYXX_RYCS";
        String zfpt_zfba = "zfpt-zfba";
        FrameworkMessageObj msg = new FrameworkMessageObj();
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("ryxm", chasBaqryxx.getRyxm());
        msgData.put("rybh", chasBaqryxx.getRybh());
        SessionUser suser = WebContext.getSessionUser();
        if (suser != null) {
            msgData.put("lrrsfzh", WebContext.getSessionUser().getIdCard());
        } else {
            msgData.put("lrrsfzh", chasBaqryxx.getLrrSfzh());
        }
        if (SYSCONSTANT.BAQRYZT_ZS.equals(chasBaqryxx.getRyzt())) {
            chas_ryxx_ryrscs = "CHAS_RYXX_RYRS";
            msgData.put("rrssj", chasBaqryxx.getRRssj());
            msgData.put("dafs", chasBaqryxx.getDafs());
            msgData.put("dafsName",
                    DicUtil.translate("DAFS", chasBaqryxx.getDafs()));
        } else if (SYSCONSTANT.BAQRYZT_YCS.equals(chasBaqryxx.getRyzt())) {
            chas_ryxx_ryrscs = "CHAS_RYXX_RYCS";
            msgData.put("ccssj", chasBaqryxx.getCCssj());
            msgData.put("ccsyy", chasBaqryxx.getCCsyy());
            msgData.put("ccsyyName",
                    DicUtil.translate("CSYY", chasBaqryxx.getCCsyy()));
        }
        try {
            if (SYSCONSTANT.Y_I != chasBaqryxx.getSfsfbm()) {
                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,zfpt_zfba);

                msgData.put("rybh",chasBaqryxx.getRybh());
                msgData.put("ryid",chasBaqryxx.getId());
                msgData.put("ryxm",chasBaqryxx.getRyxm());
                msgData.put("dwbh",chasBaqryxx.getZbdwBh());
                msgData.put("dwmc",chasBaqryxx.getZbdwMc());
                msgData.put("dwxtbh",chasBaqryxx.getDwxtbh());
                msgData.put("zbrsfzh",chasBaqryxx.getMjSfzh());
                msgData.put("zbrxm",chasBaqryxx.getMjXm());
                msgData.put("ajbh",chasBaqryxx.getAjbh());
                msgData.put("ajmc","");
                msgData.put("jqbh",chasBaqryxx.getJqbh());
                msgData.put("jqmc","");
                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,"zfgl-swyt");

                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,"zfgl-yjjd");
                log.info("发送消息成功,人员姓名:{"+chasBaqryxx.getRyxm()+"} 人员编号:{"+chasBaqryxx.getRybh()+"}");
            }
        } catch (Exception e) {
            log.error("发送消息失败 人员姓名:{"+chasBaqryxx.getRyxm()+"} 人员编号{"+chasBaqryxx.getRybh()+"}",e);
        }
    }

    public void ryScSendMsg(ChasBaqryxx chasBaqryxx) {
        String chas_ryxx_ryrscs = "CHAS_RYXX_RYSC";
        String zfpt_zfba = "zfpt-zfba";
        FrameworkMessageObj msg = new FrameworkMessageObj();
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("ryxm", chasBaqryxx.getRyxm());
        msgData.put("rybh", chasBaqryxx.getRybh());
        SessionUser suser = WebContext.getSessionUser();
        if (suser != null) {
            msgData.put("lrrsfzh", WebContext.getSessionUser().getIdCard());
        } else {
            msgData.put("lrrsfzh", chasBaqryxx.getLrrSfzh());
        }
        msgData.put("rrssj", chasBaqryxx.getRRssj());
        msgData.put("dafs", chasBaqryxx.getDafs());
        msgData.put("dafsName",DicUtil.translate("DAFS", chasBaqryxx.getDafs()));
        try {
            if (SYSCONSTANT.Y_I != chasBaqryxx.getSfsfbm()) {
                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,zfpt_zfba);
                msgData.put("rybh",chasBaqryxx.getRybh());
                msgData.put("ryid",chasBaqryxx.getId());
                msgData.put("ryxm",chasBaqryxx.getRyxm());
                msgData.put("dwbh",chasBaqryxx.getZbdwBh());
                msgData.put("dwmc",chasBaqryxx.getZbdwMc());
                msgData.put("dwxtbh",chasBaqryxx.getDwxtbh());
                msgData.put("zbrsfzh",chasBaqryxx.getMjSfzh());
                msgData.put("zbrxm",chasBaqryxx.getMjXm());
                msgData.put("ajbh",chasBaqryxx.getAjbh());
                msgData.put("ajmc","");
                msgData.put("jqbh",chasBaqryxx.getJqbh());
                msgData.put("jqmc","");
                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,"zfgl-swyt");

                MessageProduceCenter.send(msg, chas_ryxx_ryrscs, msgData,"zfgl-yjjd");
            }
        } catch (Exception e) {
            log.error("办案区:{"+chasBaqryxx.getBaqid()+"} ryRsCsSendMsg方法 发送消息失败 人员姓名:{"+chasBaqryxx.getRyxm()+"} 人员编号{"+chasBaqryxx.getRybh()+"} ",e);
        }
    }

    public boolean validateRyWd(String baqid,String wdbhL){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("wdbhL", wdbhL);
        params.put("sblx", SYSCONSTANT.SBLX_BQ);
        params.put("kzcs3", "1");
        List<ChasSb> wdList = sbService.findList(params, null);
        if (wdList.isEmpty()) {
            return false;
        }

        ChasSb wd = wdList.get(0);
        params.clear();
        params.put("baqid",baqid);
        params.put("wdbhL", wdbhL);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        params.put("isdel", SYSCONSTANT.N_I);
        List<ChasRyjl> ryjls = ryjlService.findList(params, null);
        if (ryjls.isEmpty()) {
            int dl = validateWdDl(baqid, wd);
            if(dl==1){
                return false;
            }
            //暂时不用验证
//            else if(dl==2){
//                return false;
//            }
            else if(dl==-1){
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private int validateWdDl(String baqid,ChasSb wd){
        ILocationOper locationOper = ServiceContext.getServiceByClass(ILocationOper.class);
        DevResult dev=locationOper.wdDdl(baqid, wd);
        if(dev != null){
            int code = dev.getCode();
            if(code == 1){
                HashMap device = (HashMap)dev.get("device");
                if(device != null){
                    String status = device.get("status")==null?"":device.get("status").toString();
                    String userId = device.get("userId")==null?"":device.get("userId").toString();
                    String userName = device.get("userId")==null?"":device.get("userName").toString();
                    if(StringUtil.isNotEmpty(userId)||StringUtil.isNotEmpty(userName)){//已经绑人了
                        //验证绑定人是否出区
                        ChasBaqryxx chasBaqryxx = findByRybh(userId);
                        if(chasBaqryxx != null){
                            if("04".equals(chasBaqryxx.getRyzt())){
                                //出区的人的话解除手环
                                ChasRyjl chasRyjl = ryjlService.findRyjlByRybh(chasBaqryxx.getBaqid(),chasBaqryxx.getRybh(), "1");
                                DevResult devResult = iWdService.wdJc(chasRyjl);
                                if(SYSCONSTANT.N_I == devResult.getCode()) {
                                    log.error(devResult.getMessage());
                                    return 2;
                                }
                                return 0;
                            }
                        }
                        return 2;
                    }
                    String isQuantity = device.get("isQuantity")==null?"":device.get("isQuantity").toString();
                    if(!"N".equalsIgnoreCase(isQuantity)){
                        return 1;
                    }
                    return 0;
                }
            }
        }
        return -1;
    }
}
