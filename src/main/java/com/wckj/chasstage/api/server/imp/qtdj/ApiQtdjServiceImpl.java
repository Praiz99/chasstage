package com.wckj.chasstage.api.server.imp.qtdj;

import com.wckj.api.core.query.IApiPageDataResultSet;
import com.wckj.api.def.zfba.model.ApiGgAjxx;
import com.wckj.api.def.zfba.model.ApiGgJqxx;
import com.wckj.api.def.zfba.model.param.AjxxSelectParam;
import com.wckj.api.def.zfba.model.param.JqxxSelectParam;
import com.wckj.api.def.zfba.service.gg.ApiGgAjxxService;
import com.wckj.api.def.zfba.service.gg.ApiGgJqxxService;
import com.wckj.chasstage.api.def.face.model.FaceResult;
import com.wckj.chasstage.api.def.face.model.RecognitionParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.api.def.qtdj.model.*;
import com.wckj.chasstage.api.def.qtdj.service.ApiQtdjService;
import com.wckj.chasstage.api.def.yybb.model.YybbSendParam;
import com.wckj.chasstage.api.def.yybb.model.YyhjEnue;
import com.wckj.chasstage.api.server.device.ICameraService;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.device.IWpgService;
import com.wckj.chasstage.api.server.imp.device.internal.ILocationOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.gwzz.entity.ChasXtGwlc;
import com.wckj.chasstage.modules.gwzz.service.ChasXtGwlcService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;

import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.chasstage.modules.ryxl.service.ChasYwRyxlService;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.chasstage.modules.sxdp.service.ChasSxdpService;
import com.wckj.chasstage.modules.ythcjqk.entity.ChasythcjQk;
import com.wckj.chasstage.modules.ythcjqk.service.ChasYthcjQkService;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;

import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import scala.util.parsing.combinator.testing.Str;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class ApiQtdjServiceImpl implements ApiQtdjService {
    protected Logger log = LoggerFactory.getLogger(ApiQtdjServiceImpl.class);
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private ChasYwFkdjService fkdjService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private ChasYwYyService yyService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private IWpgService wpgService;
    @Autowired
    private ChasYthcjQkService ythcjQkService;
    @Autowired
    private ChasBaqrefService baqrefService;
    @Autowired
    private ChasXtGwlcService gwlcService;
    @Autowired
    private ChasSswpxxService sswpxxService;
    @Autowired
    private ICameraService cameraService;
    @Autowired
    private ChasYmCsspService csspService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private IWdService iWdService;
    @Autowired
    private ChasDhsKzService dhsKzService;
    @Autowired
    private ChasSxdpService sxdpService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasDhsglService dhsglService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private BaqFaceService baqFaceService;
    @Autowired
    private HikFaceLocationService faceLocationService;
    @Autowired
    private ChasXtGnpzService gnpzService;
    @Autowired
    private FaceInvokeService faceInvokeService;
    private ILocationOper locationOper = ServiceContext.getServiceByClass(ILocationOper.class);
    private JdoneSysOrgService orgService = ServiceContext.getServiceByClass(JdoneSysOrgService.class);


    @Autowired
    private ChasYwRyxlService ryxlService;

    @Override
    public ApiReturnResult<?> getJqxxPageData(JqxxParam param) {
        SessionUser user = WebContext.getSessionUser();
        if (user == null) {
            JdoneSysOrg org = orgService.findBySysCode(param.getOrgSysCode());
            user = new SessionUser();
            user.setCurrentOrgCode(org.getCode());
            user.setCurrentOrgSysCode(org.getSysCode());
        }
        JqxxSelectParam jqxxselectparam = new JqxxSelectParam();

        if (StringUtils.isEmpty(param.getStatus())) {
            String jqbh = param.getJqbh();
            jqxxselectparam.setJqbh(jqbh);
            jqxxselectparam.setJqmc(param.getJqmc());
            jqxxselectparam.setClrXm(param.getCjry());
            jqxxselectparam.setCldwXtbh(user.getCurrentOrgSysCode());
            if (StringUtil.isEmpty(jqbh)) {  //如果没有警情编号,加上时间
                if (StringUtils.isNotEmpty(param.getBegin())) {
                    jqxxselectparam.setCxStartTime(DateTimeUtils.parseDateTime(param.getBegin(), "yyyy-MM-dd HH:mm:ss"));
                } else {  //默认查询1个月内的
                    LocalDate localDate = LocalDate.now().plusMonths(-1);
                    jqxxselectparam.setCxStartTime(DateTimeUtils.parseDateTime(localDate.toString() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                }
                if (StringUtils.isNotEmpty(param.getEnd())) {
                    jqxxselectparam.setCxEndTime(DateTimeUtils.parseDateTime(param.getEnd(), "yyyy-MM-dd HH:mm:ss"));
                } else {  //默认查询1个月内的
                    LocalDate localDate = LocalDate.now();
                    jqxxselectparam.setCxEndTime(DateTimeUtils.parseDateTime(localDate.toString() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
                }
            }
        } else {
            jqxxselectparam.setCxEndTime(new Date());
            jqxxselectparam.setCxStartTime(DateTimeUtils.getBeforeDate());
            if (param.getStatus().equals("01")) {// 我的七日处警
                jqxxselectparam.setClrXm(user.getName());
            } else if (param.getStatus().equals("02")) {// 本单位七日处警
                jqxxselectparam.setCldwXtbh(user.getCurrentOrgSysCode());
            } else if (param.getStatus().equals("03")) {  //本区县七日处警
                jqxxselectparam.setCldwXtbh(user.getCurrentOrgSysCode().substring(0, 6));
            }
        }

        /*if(StringUtils.isEmpty(jqxxselectparam.getBjdh())
                &&StringUtils.isEmpty(jqxxselectparam.getBjnr())
                &&StringUtils.isEmpty(jqxxselectparam.getBjrXm())
                &&StringUtils.isEmpty(jqxxselectparam.getCldwBh())
                &&StringUtils.isEmpty(jqxxselectparam.getClrXm())
                &&StringUtils.isEmpty(jqxxselectparam.getJjdh())
                &&StringUtils.isEmpty(jqxxselectparam.getJqbh())
                &&StringUtils.isEmpty(jqxxselectparam.getJqdd())
                &&StringUtils.isEmpty(jqxxselectparam.getJqlb())
                &&StringUtils.isEmpty(jqxxselectparam.getJqmc())
                &&StringUtils.isEmpty(jqxxselectparam.getJqzt())
                &&StringUtils.isEmpty(jqxxselectparam.getShzt())
                &&StringUtils.isEmpty(jqxxselectparam.getSjly())){
            log.error("getJqxxPageData参数为空");
            return ResultUtil.ReturnError("getJqxxPageData参数为空");
        }*/
        //如果由警情编号，那么就截取前6位（查询本区县的）
        if (StringUtils.isNotEmpty(jqxxselectparam.getJqbh()) || StringUtil.isNotEmpty(param.getOrgSysCode())) {
            jqxxselectparam.setCldwXtbh(jqxxselectparam.getCldwXtbh().substring(0, 6));
        }
        if(StringUtils.isNotEmpty(param.getOrgCode())){
            jqxxselectparam.setCldwXtbh("");
            jqxxselectparam.setCldwBh(param.getOrgCode());
        }
        ApiGgJqxxService apiGgJqxxService = ServiceContext.getServiceByClass(ApiGgJqxxService.class);
        IApiPageDataResultSet<ApiGgJqxx> set = apiGgJqxxService.getJqxxPageData(jqxxselectparam, param.getRows(), param.getPage(), null);
        if (set == null) {
            return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(0l, null));
        }
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(set.getTotal(), set.getData()));
    }

    @Override
    public ApiReturnResult<?> getAjxxPageData(AjxxParam param) {
        SessionUser user = WebContext.getSessionUser();
        if (user == null) {
            JdoneSysOrg org = orgService.findBySysCode(param.getOrgSysCode());
            user = new SessionUser();
            user.setCurrentOrgCode(org.getCode());
            user.setCurrentOrgSysCode(org.getSysCode());
        }
        AjxxSelectParam ajxxParam = new AjxxSelectParam();
        ajxxParam.setAjmc(param.getAjmc());
        ajxxParam.setAjbh(param.getAjbh());
        ajxxParam.setZxbSfzh(param.getZxbSfzh());
        ajxxParam.setSlsjStart(DateTimeUtils.parseDateTime(param.getSlsjStart(), "yyyy-MM-dd HH:mm:ss"));
        ajxxParam.setSlsjEnd(DateTimeUtils.parseDateTime(param.getSlsjEnd(), "yyyy-MM-dd HH:mm:ss"));
        if(StringUtils.isEmpty(param.getZbdwBh())){
            ajxxParam.setZxbDwbh(user.getCurrentOrgCode());
        }else{
            ajxxParam.setZxbDwbh(param.getZbdwBh());
        }
        if (StringUtils.isEmpty(ajxxParam.getZxbDwbh())) {
            log.error("getAjxxPageData参数为空");
            return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(0l, null));
        }
        //如果有案件编号   那么默认查询前六位单位机构代码
        if (StringUtils.isNotEmpty(ajxxParam.getAjmc())||StringUtils.isNotEmpty(ajxxParam.getAjbh()) || StringUtil.isNotEmpty(param.getOrgSysCode())) {
            ajxxParam.setZxbDwbh(ajxxParam.getZxbDwbh().substring(0, 6));
        }
        ApiGgAjxxService aopiGgAjxxService = ServiceContext.getServiceByClass(ApiGgAjxxService.class);
        ajxxParam.setAjlx(param.getAjlx());
        IApiPageDataResultSet<ApiGgAjxx> ajxxs = aopiGgAjxxService.getAjxxPageData(ajxxParam, param.getRows(), param.getPage(), "");
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(ajxxs.getTotal(), DicUtil.translate(ajxxs.getData(), new String[]{"ZD_SYS_AJLX", "ZD_SYS_AJZT"}, new String[]{"ajlx", "ajzt"})));
    }

    @Override
    public ApiReturnResult<?> validateWd(WdParam param) {
        if (StringUtils.isEmpty(param.getBaqid())) {
            return ResultUtil.ReturnError("办案区ID不能为空!");
        }
        if (StringUtils.isEmpty(param.getWdbhL())) {
            if("1".equals(param.getType())){
                return ResultUtil.ReturnError("手环编号不能为空!");
            }else{
                return ResultUtil.ReturnError("胸卡编号不能为空!");
            }
        }
        if (StringUtils.isEmpty(param.getType()) || "1".equals(param.getType())) {
            return validateRyWd(param.getBaqid(), param.getRybh(), param.getWdbhL());
        } else {
            return validateAllxk(param.getBaqid(), param.getRybh(), param.getWdbhL());
        }
    }

    private ApiReturnResult<?> validateRyWd(String baqid, String rybh, String wdbhL) {
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("wdbhL", wdbhL);
        params.put("sblx", SYSCONSTANT.SBLX_BQ);
        params.put("kzcs3", "1");
        List<ChasSb> wdList = sbService.findList(params, null);
        if (wdList.isEmpty()) {
            return ResultUtil.ReturnError("本办案区不存在该手环!");
        }

        ChasSb wd = wdList.get(0);
        if (StringUtils.isNotEmpty(rybh)) {
            ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqid, rybh);
            if (ryjl != null) {
                if (wdbhL.equals(ryjl.getWdbhL())) {
                    return ResultUtil.ReturnError("当前本人正在使用!");
                }
            }
        }

        params.clear();
        params.put("baqid",baqid);
        params.put("wdbhL", wdbhL);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        params.put("isdel", SYSCONSTANT.N_I);
        List<ChasRyjl> ryjls = ryjlService.findList(params, null);
        if (ryjls.isEmpty()) {
            int dl = validateWdDl(baqid, wd);
            if(dl==1){
                return ResultUtil.ReturnError("该手环电量低!");
            }
            //暂时不用验证
//            else if(dl==2){
//                return ResultUtil.ReturnError("该手环已被占用!");
//            }
            else if(dl==-1){
                return ResultUtil.ReturnError("dc验证手环失败");
            }
        } else {
            return ResultUtil.ReturnError("该手环已被占用!");
        }
        return ResultUtil.ReturnSuccess("该手环可以使用!");
    }

    //验证手环是否低电量
    private int validateWdDl(String baqid,ChasSb wd){
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

    private ApiReturnResult<?> validateAllxk(String baqid, String rybh, String wdbhL) {
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("wdbhL", wdbhL);
        params.put("sblx", SYSCONSTANT.SBLX_BQ);
        //params.put("kzcs3","4");
        List<ChasSb> wdList = sbService.findList(params, null);
        if (wdList.isEmpty()) {
            return ResultUtil.ReturnError("本办案区不存在该类型胸卡!");
        }
        ChasSb wd = wdList.get(0);
        if (StringUtils.isNotEmpty(rybh)) {
            ChasYwFkdj fkjl = fkdjService.findFkdjByRybh(baqid, rybh);
            if (fkjl != null && wdbhL.equals(fkjl.getWdbhL())) {
                return ResultUtil.ReturnError("当前本人正在使用!");
            }
            ChasYwMjrq ryjl = mjrqService.findMjrqByRybh(baqid, rybh);
            if (ryjl != null && wdbhL.equals(ryjl.getWdbhL())) {
                return ResultUtil.ReturnError("当前本人正在使用!");
            }
        }

        params.clear();
        params.put("wdbhL", wdbhL);
        params.put("zt", SYSCONSTANT.MJRQ_ZQ);
        params.put("isdel", SYSCONSTANT.N_I);
        List<ChasYwFkdj> ryjls = fkdjService.findList(params, null);

        List<ChasYwMjrq> mjlist = mjrqService.findList(params, null);
        params.clear();
        params.put("shbh", wdbhL);
        params.put("crqzt", SYSCONSTANT.MJRQ_ZQ);
        params.put("isdel", SYSCONSTANT.N_I);
        List<ChasYwYy> yyList = yyService.findByParams(params);
        if (ryjls.isEmpty() && mjlist.isEmpty() && yyList.isEmpty()) {
            int dl = validateWdDl(baqid, wd);
            if(dl==1){
                return ResultUtil.ReturnError("该胸卡电量低!");
            }else if(dl==2){
                return ResultUtil.ReturnError("该胸卡已被占用!");
            }else if(dl==-1){
                return ResultUtil.ReturnError("dc验证胸卡失败");
            }
        } else {
            return ResultUtil.ReturnError("该胸卡已被占用!");
        }
        return ResultUtil.ReturnSuccess("该胸卡可以使用!");
    }

    @Override
    public ApiReturnResult<?> getBoxNoByType(String boxType, String baqid, String type) {
        log.info(String.format("storagePreAssign: baqid{%s}, boxtype{%s}, sbgn{%s}", baqid, boxType, type));
        String flag = boxType == null ? "" : boxType.toLowerCase();
        DevResult wr = null;
        if (StringUtil.equals(flag, "false")) {  //不区分大小柜
            try {
                if ("1".equals(type)) {
                    wr = wpgService.fpCwg(baqid, "");
                    String boxNo = (String) wr.getData().get("boxNo");
                    if (StringUtil.isEmpty(boxNo)) {
                        wr.setCode(500);
                    }
                } else if ("40".equals(type)) {
                    wr = wpgService.fpSjg(baqid, "");
                    String boxNo = (String) wr.getData().get("boxNo");
                    if (StringUtil.isEmpty(boxNo)) {
                        wr.setCode(500);
                    }
                } else {
                    return ResultUtil.ReturnError("暂不支持该类智能柜");
                }

            } catch (Exception e) {
                return ResultUtil.ReturnError("没有空闲的储物柜,请重新选择!");
            }
            if (wr.getCode() == 1) {
                return ResultUtil.ReturnSuccess(wr.getData());
            } else {
                return ResultUtil.ReturnSuccess(wr.getMessage());
            }
        } else {
            try {
                int wgh = Integer.parseInt(StringUtils.isEmpty(boxType) ? type : boxType);
                if (wgh < 1) {
                    wr.setCodeMessage(1, "无物品不需要分配");
                } else if (wgh <= 4) {
                    String boxtype = SYSCONSTANT.BOX_TYPE_X;
                    switch (wgh) {
                        case 1:
                            boxtype = SYSCONSTANT.BOX_TYPE_D;
                            break;
                        case 2:
                            boxtype = SYSCONSTANT.BOX_TYPE_Z;
                            break;
                        case 3:
                            boxtype = SYSCONSTANT.BOX_TYPE_X;
                            break;
                        case 4:
                            boxtype = SYSCONSTANT.BOX_TYPE_CD;
                            break;
                    }
                    wr = wpgService.fpCwg(baqid, boxtype);
                    String boxNo = (String) wr.getData().get("boxNo");
                    if (StringUtil.isEmpty(boxNo)) {
                        wr.setCode(500);
                    }
                } else {
                    wr = wpgService.fpSjg(baqid, SYSCONSTANT.BOX_TYPE_X);
                    String boxNo = (String) wr.getData().get("boxNo");
                    if (StringUtil.isEmpty(boxNo)) {
                        wr.setCode(500);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ReturnError("没有空闲的储物柜,请重新选择!");
            }
            if (wr.getCode() == 1) {
                return ResultUtil.ReturnSuccess(wr.getData());
            } else {
                return ResultUtil.ReturnSuccess(wr.getMessage());
            }
        }
    }

    @Override
    public ApiReturnResult<?> SaveWithUpdate(RyxxBean param) {
        try {
            String id = param.getId();
            String yytj = param.getYytj();
            String baqid = param.getBaqid();
            if (StringUtils.isEmpty(baqid)) {
                return ResultUtil.ReturnError("办案区id不能为空!");
            }
            ChasBaq baq = baqService.findById(baqid);
            if (baq == null) {
                return ResultUtil.ReturnError("获取办案区信息失败!");
            }
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
            if ("1".equals(yytj)) {//预约体检
                String baqmc = param.getBaqmc();
                String zpid = param.getZpid();
                //查询有人员记录的已出所被拘留人员
                ChasBaqryxx baqryxx = null;
                if(configuration.getDwHkrl()){//若开启了海康人脸定位则无法通过腕带编号查询，通过查询人脸信息
                    IByteFileObj iByteFileObj = FrwsApiForThirdPart.downloadByteFileByBizId(param.getZpid());
                    if(iByteFileObj != null){
                        String base64 = Base64.encodeBase64String(iByteFileObj.getBytes());
                        RecognitionParam recognitionParam = new RecognitionParam();
                        recognitionParam.setDwxtbh(baq.getDwxtbh());
                        recognitionParam.setBaqid(baqid);
                        recognitionParam.setBase64Str(base64);
                        recognitionParam.setTzlx("baqry");
                        recognitionParam.setSfcxls("1");//查询包括已经被逻辑删除的人脸信息
                        List<FaceResult> faceResults = baqFaceService.recognition(recognitionParam);
                        if(!ObjectUtils.isEmpty(faceResults) && faceResults.size() > 0){
                            FaceResult faceResult = faceResults.get(0);
                            String rybh = faceResult.getTzbh();
                            ChasBaqryxx baqryxx1 = baqryxxService.findByRybh(rybh);
                            if(!ObjectUtils.isEmpty(baqryxx1)){
                                if(StringUtil.equals(baqryxx1.getRyzt(),SYSCONSTANT.BAQRYZT_YCS)){
                                    if("01".equals(baqryxx1.getCRyqx()) || "04".equals(baqryxx1.getCRyqx())){//拘留
                                        baqryxx = baqryxx1;
                                    }
                                }
                            }
                        }
                    }
                }else{
                    baqryxx = baqryxxService.getSyryByWdbhl(param.getWdbhL(), param.getBaqid());
                }
                if (baqryxx != null) {
                    baqryxx.setId("");
                    MyBeanUtils.copyBeanNotNull2Bean(baqryxx,param);
                    param.setcRyqx("");
                    param.setcCsyy("");
                    param.setcCssj(null);
                    param.setBaqid(baqid);
                    param.setBaqmc(baqmc);
                    param.setZpid(zpid);
                    param.setRyZaybh(baqryxx.getRyzaybh());
                    param.setRyZaymc(baqryxx.getRyzaymc());
                    param.setrYzjb(baqryxx.getRYzjb());
                    param.setRySfzh(baqryxx.getRysfzh());
                    param.setMjSfzh(baqryxx.getMjSfzh());
                    param.setrSfyzjb(baqryxx.getRSfyzjb());
                    param.setrSfssjc(baqryxx.getRSfssjc());
                    param.setrSsms(baqryxx.getRSsms());
                    param.setrSjtstbtz(baqryxx.getRSjtstbtz());
                    param.setrSfzs(baqryxx.getRSfzs());
                    param.setrZsss(baqryxx.getRZsss());
                    param.setHjdXzqh(baqryxx.getHjdxzqh());
                    param.setDafsText(baqryxx.getDafsText());
                    param.setXbMjSfzh(baqryxx.getXbMjSfzh());
                    param.setXbMjXm(baqryxx.getXbMjXm());
                    param.setrRssj(new Date());
                    Map<String, Object> result = baqryxxService.SaveWithUpdate(param);
                    baqryxx = (ChasBaqryxx) result.get("baqryxx");
                    if (baqryxx != null) {
                        log.info("SaveWithUpdate:新增登记区轨迹:" + baqryxx.getRybh() + "," + baqryxx.getRyxm());
                        rygjService.manualAddRygj(baqryxx);
                    }
                    YybbSendParam yybbSendParam = new YybbSendParam();
                    yybbSendParam.setBaqid(baqryxx.getBaqid());
                    yybbSendParam.setRyxm(baqryxx.getRyxm());
                    yybbSendParam.setBbhj(YyhjEnue.XXDJDT);
                    YybbUtil.sendYybb(yybbSendParam);
                    return ResultUtil.ReturnSuccess("关联人员信息成功!", result);
                }
            }
            Map<String, Object> result = baqryxxService.SaveWithUpdate(param);
            ChasBaqryxx baqryxx = (ChasBaqryxx) result.get("baqryxx");
            if (StringUtil.isEmpty(id)) {
                log.info("SaveWithUpdate:新增登记区轨迹:" + (baqryxx != null));
                if (baqryxx != null) {
                    log.info("SaveWithUpdate:新增登记区轨迹:" + baqryxx.getRybh() + "," + baqryxx.getRyxm());
                    rygjService.manualAddRygj(baqryxx);
                }
                YybbSendParam yybbSendParam = new YybbSendParam();
                yybbSendParam.setBaqid(baqryxx.getBaqid());
                yybbSendParam.setRyxm(baqryxx.getRyxm());
                yybbSendParam.setBbhj(YyhjEnue.XXDJDT);
                YybbUtil.sendYybb(yybbSendParam);
            }
            //return ResultUtil.ReturnSuccess(result);
            //开始海康人脸定位
            if(configuration.getDwHkrl()){
                String bizId = param.getZpid();
                FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
                if (fileInfoObj == null) {
                    return ResultUtil.ReturnWarning("未查询到人脸照片！", result);
                }else{
                    Map<String, Object> response = new HashMap<>();
                    response = baqryxxService.saveOrUpdateRldw(baqryxx);
                    if((Boolean) response.get("status")){
                        return ResultUtil.ReturnSuccess(result);
                    }else{
                        return ResultUtil.ReturnWarning("人脸信息上传失败，请重新尝试！", result);
                    }
                }
            }else{
                return ResultUtil.ReturnSuccess(result);
            }
        } catch (Exception e) {
            //注释删除人员，避免数据丢失
            /*ryjlService.clearByRybh(param.getRybh());
            baqryxxService.clearByRybh(param.getRybh());*/
            log.error("ApiQtdjService:", e);
            return ResultUtil.ReturnError("保存人员失败:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getBaqryxxDataGrid(RyxxParam param) {
        String baqid = param.getBaqid();  //第三方接入

        Map<String, Object> params = MapCollectionUtil.beanToMap(param);

        /*if (StringUtil.isEmpty(baqid)) {
            DataQxbsUtil.getSelectAll(baqryxxService, params);
        }*/
        DataQxbsUtil.getSelectAll(baqryxxService, params);
        try {
            //如果带了主办单位代码查询，则不按权限查询。按主办单位来查询
            String  zbdwBh= (String) params.get("zbdwBh");
            if(StringUtils.isNotEmpty(zbdwBh)){
                params.put("baqid", null);
                params.put("qxbs", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isEmpty(param.getAjbh()) && StringUtil.isEmpty(param.getAjmc()) && StringUtil.isEmpty(param.getRyxm())
                && StringUtil.isEmpty(param.getRyZaybh()) && StringUtil.isEmpty(param.getcRyqx())
                && StringUtil.isEmpty(param.getZbdwBh()) && StringUtil.isEmpty(param.getSysCode())
                && (StringUtil.isEmpty(param.getRssj1()) && StringUtil.isEmpty(param.getRssj2()))) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -7);
            Date d = c.getTime();
            params.put("lrsjStart", DateTimeUtils.getDateStr(d, 13) + " 00:00:00");
            params.put("lrsjEnd", DateTimeUtils.getDateStr(new Date(), 13) + " 23:59:59");
        }
        if (StringUtil.isNotEmpty(param.getRyztMulti())) {
            String[] strs = param.getRyztMulti().split(",");
            params.put("ryztMulti", strs);
        }

        PageDataResultSet<ChasBaqryxxBq> qt = baqryxxService.getEntityPageDataBecauseQymc(param.getPage(), param.getRows(), params, " r_rssj desc,id ");
        qt.getData().stream().forEach(ry->{
            String rybh = ry.getRybh();
            ChasYwRyxl ryxl = ryxlService.getLastestRyxlByRybh(rybh);
            if(null != ryxl) {
                ry.setRyxl(String.valueOf(ryxl.getRyxl()));
            }
        });
        //设置心率状态
        setXlztForBaqryxx(qt.getData());
        List<Map<String, Object>> data = DicUtil.translate(qt.getData(), new String[]{
                        "CHAS_ZD_ZB_XB", "CHAS_ZD_CASE_TSQT", "RSYY", "RYZT",
                        "ZD_SYS_ORG_CODE_SNAME", "CSYY", "BAQRYSSWPZT", "DAFS", "CHAS_ZD_CASE_ZJLX", "CHAS_ZD_CASE_RYLB"},
                new String[]{"xb", "tsqt", "ryZaybh", "ryzt", "zbdwBh", "cCsyy", "sswpzt", "dafs", "zjlx", "rylx"});
        for(Map<String, Object> item : data){
            if(StringUtils.isNotEmpty((String) item.get("zpid"))){
                FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId((String) item.get("zpid"));
                if(null != fileInfoObj) {
                    item.put("zpUrl", fileInfoObj.getDownUrl());
                }
            }else{
                item.put("zpUrl", "");
            }
        }
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(qt.getTotal(), data));
    }

    /**
     * 办案区人员信息，设置心率状态
     * @param list
     */
    public void setXlztForBaqryxx(List<ChasBaqryxxBq> list) {
        String minValueStr = SysUtil.getParamValue("RYXL_MIN_VALUE");
        String maxValueStr = SysUtil.getParamValue("RYXL_MAX_VALUE");
        for (int i = 0; i < list.size(); i++) {
            ChasBaqryxxBq chasBaqryxxBq = list.get(i);
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(chasBaqryxxBq.getBaqid());
            if(baqznpz != null && StringUtils.isNotEmpty(baqznpz.getGnpzid())){
                ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
                minValueStr = gnpz.getZdypzVule("RYXL_MIN_VALUE") == null ? minValueStr : gnpz.getZdypzVule("RYXL_MIN_VALUE");
                maxValueStr = gnpz.getZdypzVule("RYXL_MAX_VALUE") == null ? maxValueStr : gnpz.getZdypzVule("RYXL_MAX_VALUE");
            }
            Integer minValue = 60;
            Integer maxValue = 100;
            try {
                if(StringUtils.isNotEmpty(minValueStr)){
                    minValue = new Integer(minValueStr);
                }
                if(StringUtils.isNotEmpty(maxValueStr)){
                    maxValue = new Integer(maxValueStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("心率上下限数字转换异常", e);
            }
            String ryxl = chasBaqryxxBq.getRyxl();
            if(StringUtils.isEmpty(ryxl) || "null".equals(ryxl)){
                chasBaqryxxBq.setXlzt("0");
            }else{
                Integer ryxlInt = new Integer(ryxl);
                //大于最高心率
                if(ryxlInt > maxValue){
                    chasBaqryxxBq.setXlzt("3");
                }else if(ryxlInt < minValue){
                    //低心率
                    chasBaqryxxBq.setXlzt("1");
                }else{
                    //正常心率
                    chasBaqryxxBq.setXlzt("2");
                }
            }
        }
    }

    @Override
    public ApiReturnResult<?> getDataByYwbh(TaryParam param) {
        Map<String, Object> params = new HashMap<>(16);
        String baqid = param.getBaqid();
        if (StringUtil.isNotEmpty(baqid)) {
            params.put("baqid", baqid);
        } else {
            params.put("zbdwBh", WebContext.getSessionUser().getCurrentOrgSysCode());
        }
        params.put("ryzt", SYSCONSTANT.BAQRYZT_ZS);
        params.put("ryxm", param.getRyxm());
        String rybh = param.getRybh();

        String startTime = "";
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        startTime = sim.format(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, -6);//减6天
        date = rightNow.getTime();

        params.put("rssj1", sim.format(date) + " 00:00:00");
        params.put("rssj2", startTime + " 23:59:59");

        List<Map<String, Object>> baqryxxes = baqryxxService.getDataByYwbh(params);

        for (Map<String, Object> ryxx : baqryxxes) {
            if (StringUtils.isNotEmpty((String) ryxx.get("zpid"))) {
                FileInfoObj fileInfoByBizId = FrwsApiForThirdPart.getFileInfoByBizId((String) ryxx.get("zpid"));
                if (fileInfoByBizId != null) {
                    ryxx.put("zpid", fileInfoByBizId.getDownUrl());
                }
            }
        }

        //排除自己
        if (StringUtils.isNotEmpty(rybh)) {
            Iterator<Map<String, Object>> iterator = baqryxxes.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> baqry = iterator.next();
                if (StringUtils.equals(rybh, (String) baqry.get("rybh"))) {
                    iterator.remove();
                }
            }
        }

        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(Long.valueOf(baqryxxes.size()), baqryxxes));
    }

    @Override
    public ApiReturnResult<?> SaveWithUpdateSswpByList(String json, String del) {
        try {
            sswpxxService.SaveWithUpdate_List(json, del);
        } catch (Exception e) {
            log.error("SaveWithUpdateSswpByList:", e);
            return ResultUtil.ReturnError("保存失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("保存成功!");
    }

    @Override
    public ApiReturnResult<?> saveYthcjQk(YthcjParam param) {
        try {
            ythcjQkService.saveOrUpdate(param);
        } catch (Exception e) {
            log.error("saveYthcjQk:", e);
            return ResultUtil.ReturnError("保存失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("保存成功!");
    }

    @Override
    public ApiReturnResult<?> getYthcjQk(String rybh) {
        if (StringUtils.isEmpty(rybh)) {
            throw new BizDataException("人员编号为NULL");
        }
        List<ChasythcjQk> chasythcjQks = ythcjQkService.findList(ParamUtil.builder().accept("rybh", rybh).toMap(), null);
        if (!chasythcjQks.isEmpty()) {
            return ResultUtil.ReturnSuccess(chasythcjQks.get(0));
        } else {
            return ResultUtil.ReturnSuccess(new ChasythcjQk());
        }
    }

    @Override
    public ApiReturnResult<?> loginByWdbh(String wdbhL, String rybh) {
        SessionUser user = WebContext.getSessionUser();
        Map<String, Object> param = new HashMap<>(16);
        Map<String, Object> sbParam = new HashMap<>(16);
        if (StringUtil.isNotEmpty(wdbhL)) {
            sbParam.put("wdbhL", wdbhL);
            List<ChasSb> sbList = sbService.findList(sbParam, "");
            if (sbList.size() > 0) {
                ChasSb sb = sbList.get(0);

                if (SYSCONSTANT.TAGTYPE_XYR.equals(sb.getKzcs3())) {
                    param.put("wdbh_l", wdbhL);
                    param.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
                    ChasRyjl chasRyjl = ryjlService.findByParams(param);
                    if (chasRyjl != null) {
                        //流程状态 判断
                        /**根据办案区角色配置 当前登录用户角色 流程状态判断是否有权限进去界面*/
                        param.clear();
                        param.put("sydwdm", user.getOrgCode());
                        List<ChasBaqref> baqRefList = baqrefService.findList(param, "");
                        if (baqRefList.size() <= 0) {
                            return ResultUtil.ReturnError("该人员所在办案区不存在!");
                        }
                        for (ChasBaqref baqRef : baqRefList) {
                            if (baqRef.getBaqid().equals(chasRyjl.getBaqid())) {
                                //查找 办案区岗位控制表，获取角色与流程对应关系
                                param.clear();
                                param.put("baqid", baqRef.getBaqid());
                                param.put("jsdm", user.getRoleCode());
                                List<ChasXtGwlc> gwlcs = gwlcService.findList(param, "");
                                if (gwlcs.size() > 0) {
                                    ChasXtGwlc gwlc = gwlcs.get(0);
                                    if (gwlc.getJsdm().contains(user.getRoleCode()) && gwlc.getLczzdm().contains(chasRyjl.getLczt())) {
                                        Map<String, Object> data = new HashMap<>(16);
                                        data.put("ryjl", chasRyjl);
                                        ChasBaqryxx ryxxBywdbhL = baqryxxService.findRyxxBywdbhL(wdbhL);
                                        if (ryxxBywdbhL != null) {
                                            data.put("ryxx", ryxxBywdbhL);
                                        }
                                        data.put("gwzz", gwlc.getLczzdm());
                                        data.put("rqType", SYSCONSTANT.TAGTYPE_XYR);
                                        return ResultUtil.ReturnSuccess("成功", data);
                                    } else {
                                        //result.setMsg("无权进入"+DicUtil.translate("BAQLCJD",chasRyjl.getLczt()));
                                        return ResultUtil.ReturnError("该岗位未配置" + com.wckj.framework.core.dic.DicUtil.translate("BAQLCJD", chasRyjl.getLczt()));
                                    }
                                } else {
                                    return ResultUtil.ReturnError("该人员所在办案区未配置流程控制!");
                                }
                            }
                        }
                        return ResultUtil.ReturnError("办案区与人员所在办案区不匹配!");
                    } else {
                        return ResultUtil.ReturnError("人员信息不存在!");
                    }
                } else if (SYSCONSTANT.TAGTYPE_MJ.equals(sb.getKzcs3())) {
                    //民警
                    param.put("wdbh_l", wdbhL);
                    param.put("zt", "02");
                    List<ChasYwMjrq> list = mjrqService.findList(param, "");
                    Map<String, Object> data = new HashMap<>(16);
                    data.put("rqType", SYSCONSTANT.TAGTYPE_MJ);
                    data.put("ryxx", list.size() > 0 ? list.get(0) : null);
                    return ResultUtil.ReturnSuccess("成功", data);
                } else if (SYSCONSTANT.TAGTYPE_FK.equals(sb.getKzcs3())) {
                    //访客
                    param.put("wdbhL", wdbhL);
                    param.put("zt", "02");
                    List<ChasYwFkdj> list = fkdjService.findList(param, "");
                    Map<String, Object> data = new HashMap<>(16);
                    data.put("rqType", SYSCONSTANT.TAGTYPE_FK);
                    data.put("ryxx", list.size() > 0 ? list.get(0) : null);
                    return ResultUtil.ReturnSuccess("成功", data);
                } else {
                    return ResultUtil.ReturnError("类型不匹配!");
                }
            } else {
                return ResultUtil.ReturnError("设备不存在!");
            }
        } else if (StringUtils.isNotEmpty(rybh)) {
            param.put("rybh", rybh);
            param.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
            ChasRyjl chasRyjl = ryjlService.findByParams(param);
            if (chasRyjl != null) {
                //流程状态 判断
                /**根据办案区角色配置 当前登录用户角色 流程状态判断是否有权限进去界面*/
                param.clear();
                param.put("sydwdm", user.getOrgCode());
                List<ChasBaqref> baqRefList = baqrefService.findList(param, "");
                if (baqRefList.size() <= 0) {
                    return ResultUtil.ReturnError("该人员所在办案区不存在!");
                }
                log.info("loginByWdbh,user,sydwdm:"+user.getOrgCode()+",rolecode:"+user.getRoleCode()+",chasRyjl.getLczt:"+chasRyjl.getLczt()+",rybh:"+rybh);
                for (ChasBaqref baqRef : baqRefList) {
                    if (baqRef.getBaqid().equals(chasRyjl.getBaqid())) {
                        //查找 办案区岗位控制表，获取角色与流程对应关系
                        param.clear();
                        param.put("baqid", baqRef.getBaqid());
                        param.put("jsdm", user.getRoleCode());
                        List<ChasXtGwlc> gwlcs = gwlcService.findList(param, "");
                        if (gwlcs.size() > 0) {
                            ChasXtGwlc gwlc = gwlcs.get(0);
                            if (gwlc.getJsdm().contains(user.getRoleCode()) && gwlc.getLczzdm().contains(chasRyjl.getLczt())) {
                                log.info("开始获取人员信息");
                                Map<String, Object> data = new HashMap<>(16);
                                data.put("ryjl", chasRyjl);
                                ChasBaqryxx ryxxBywdbhL = baqryxxService.findByRybh(rybh);
                                if (ryxxBywdbhL != null) {
                                    data.put("ryxx", ryxxBywdbhL);
                                }
                                data.put("gwzz", gwlc.getLczzdm());
                                data.put("rqType", SYSCONSTANT.TAGTYPE_XYR);
                                return ResultUtil.ReturnSuccess("成功", data);
                            }
                        }
                    }
                }
                return ResultUtil.ReturnError("该人员所在办案区未配置流程控制!");
            }
            //民警
            param.put("rybh", rybh);
            param.put("zt", "02");
            List<ChasYwMjrq> list = mjrqService.findList(param, "");
            if(list.size() > 0){
                Map<String, Object> data = new HashMap<>(16);
                data.put("rqType", SYSCONSTANT.TAGTYPE_MJ);
                data.put("ryxx", list.size() > 0 ? list.get(0) : null);
                return ResultUtil.ReturnSuccess("成功", data);
            }
            //访客
            param.put("rybh", rybh);
            param.put("zt", "02");
            List<ChasYwFkdj> list1 = fkdjService.findList(param, "");
            if(list1.size() > 0){
                Map<String, Object> data = new HashMap<>(16);
                data.put("rqType", SYSCONSTANT.TAGTYPE_FK);
                data.put("ryxx", list1.size() > 0 ? list1.get(0) : null);
                return ResultUtil.ReturnSuccess("成功", data);
            }
            return ResultUtil.ReturnError("该人员编号未找到人员信息");
        } else {
            return ResultUtil.ReturnError("手环编号和人员编号为空！");
        }
    }

    @Override
    public ApiReturnResult<?> capture(SswpPzParam param) {
        try {
            DevResult result = cameraService.capture(param.getRybh(), param.getCameraFun(),
                    param.getBizId(), param.getGw());
            if (result.getCode() == 1) {
                return ResultUtil.ReturnSuccess(result.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("随身物品拍照出错", e);
        }

        return ResultUtil.ReturnError("随身物品拍照出错");
    }

    @Override
    public ApiReturnResult<?> findByRybh(String rybh) {
        return ResultUtil.ReturnSuccess(baqryxxService.findByRybh(rybh));
    }

    @Override
    public ApiReturnResult<?> SaveAqjc(AqjcBean aqjcBean) {
        RyxxBean ryxxBean = new RyxxBean();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(aqjcBean, ryxxBean);
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(aqjcBean.getRybh());
            if(StringUtils.isNotEmpty(baqryxx.getAjbh())){
                ryxxBean.setYwbhStr(baqryxx.getAjbh());
            }
            if(StringUtils.isNotEmpty(baqryxx.getJqbh())){
                ryxxBean.setYwbhStr(baqryxx.getJqbh());
            }
            baqryxxService.SaveWithUpdate(ryxxBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SaveAqjc:", e);
            return ResultUtil.ReturnError("保存失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("保存成功!");
    }

    @Override
    public ApiReturnResult<?> getRyxxAllByRybh(String rybh) {
        if (StringUtils.isEmpty(rybh)) {
            throw new BizDataException("人员编号为NULL");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("rybh", rybh);
        List<ChasBaqryxx> baqryxxList = baqryxxService.findList(params, null);
        if (baqryxxList.isEmpty()) {
            return ResultUtil.ReturnError("暂无数据!");
        }

        ChasBaqryxx baqryxx = baqryxxList.get(0);
        ChasRyjl ryjl = ryjlService.findRyjlByRybhUndel(baqryxx.getBaqid(), baqryxx.getRybh());
        String taryxm = "";
        if (StringUtil.isNotEmpty(ryjl.getYwbh())) {
            if (ryjl.getYwbh().endsWith(",")) {
                ryjl.setYwbh(ryjl.getYwbh().substring(0, ryjl.getYwbh().length() - 1));
            }
            List<ChasBaqryxx> baqryxxes = baqryxxService.findTaryByRyjlYwbh(ryjl.getYwbh());
            for (ChasBaqryxx ryxx : baqryxxes) {
                if (!StringUtils.equals(ryxx.getRybh(), baqryxx.getRybh())) {
                    taryxm += ryxx.getRyxm() + "、";
                }
            }
            if (StringUtils.isNotEmpty(taryxm)) {
                taryxm = taryxm.substring(0, taryxm.length() - 1);
            }
        }

        RyxxResult ryxxResult = new RyxxResult();
        ChasBaqryxxBq baqryxxBq = new ChasBaqryxxBq();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(baqryxx, baqryxxBq);
        } catch (Exception e) {
        }
        List<ChasBaqryxxBq> translist = new ArrayList<>();
        translist.add(baqryxxBq);
        List<Map<String, Object>> translate = DicUtil.translate(translist, new String[]{
                        "CHAS_ZD_ZB_XB", "CHAS_ZD_CASE_TSQT", "RSYY", "RYZT",
                        "ZD_SYS_ORG_CODE_SNAME", "CSYY", "BAQRYSSWPZT", "DAFS", "CHAS_ZD_CASE_ZJLX", "CHAS_ZD_CASE_RYLB"},
                new String[]{"xb", "tsqt", "ryZaybh", "ryzt", "zbdwBh", "cCsyy", "sswpzt", "dafs", "zjlx", "rylx"});
        ryxxResult.setBaqryxx(translate.get(0));
        ryxxResult.setCwgbh(ryjl.getCwgBh());
        ryxxResult.setCwgid(ryjl.getCwgId());
        ryxxResult.setCwglx(ryjl.getCwgLx());
        ryxxResult.setTaryxm(taryxm);
        ryxxResult.setWdbhH(ryjl.getWdbhH());
        ryxxResult.setWdbhL(ryjl.getWdbhL());
        ryxxResult.setSjgBh(ryjl.getSjgBh());
        ryxxResult.setSjgId(ryjl.getSjgId());
        ryxxResult.setYwbh(ryjl.getYwbh());
        return ResultUtil.ReturnSuccess(ryxxResult);
    }

    /**
     * 流程状态修改
     *
     * @param rybh
     * @param lczt
     * @return
     */
    @Override
    public ApiReturnResult<?> updateStaffProcessStatus(String rybh, String lczt) {
        SessionUser user = WebContext.getSessionUser();
        ApiReturnResult<?> apiReturnResult = new ApiReturnResult<>();
        if (StringUtils.isEmpty(rybh)) {
            throw new BizDataException("人员编号为NULL");
        }
        ChasRyjl ryjl = ryjlService.findRyjlByRybh(null, rybh);
        ryjl.setLczt(lczt);
        ryjlService.update(ryjl);
        if (user == null) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("保存成功！");
            return apiReturnResult;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("baqid", ryjl.getBaqid());
        param.put("jsdm", user.getRoleCode());
        List<ChasXtGwlc> gwlcs = gwlcService.findList(param, "");
        if (gwlcs.size() > 0) {
            for (ChasXtGwlc gwlc : gwlcs) {
                if (gwlc.getJsdm().contains(user.getRoleCode()) && gwlc.getLczzdm().contains(lczt)) {
                    apiReturnResult.setCode("200");
                    apiReturnResult.setMessage("有权访问!");
                    return apiReturnResult;
                }
            }
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("该岗位未配置" + DicUtil.translate("BAQLCJD", lczt));
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("该人员所在办案区未配置流程控制!");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> Securitycheck(String rybh) {
        if (StringUtils.isEmpty(rybh)) {
            throw new BizDataException("人员编号为NULL");
        }
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        if (StringUtils.isNotEmpty(baqryxx.getSsclid())) {
            List<Map<String, Object>> fileInfoList = new ArrayList<>();
            try {
                if (StringUtils.isEmpty(baqryxx.getRSsms()) || "null".equals(baqryxx.getRSsms())) {
                    return ResultUtil.ReturnSuccess(fileInfoList);
                }
                List<Map<String, Object>> info = JsonUtil.parse(baqryxx.getRSsms(), List.class);
                for (Map<String, Object> map : info) {
                    String bizid = (String) map.get("bizId");
                    Integer index = (Integer) map.get("index");
                    if (StringUtil.isNotEmpty(bizid)) {
                        List<FileInfoObj> fileInfoListSon = FrwsApiForThirdPart.getFileInfoList(bizid);
                        for (int i = 0; i < fileInfoListSon.size(); i++) {
                            Map<String, Object> resultMap = new HashMap<>();
                            FileInfoObj fileInfoObj = fileInfoListSon.get(i);
                            resultMap.put("id", fileInfoObj.getId());
                            resultMap.put("downUrl", fileInfoObj.getDownUrl());
                            resultMap.put("deleteUrl", fileInfoObj.getDeleteUrl());
                            resultMap.put("index", index);
                            fileInfoList.add(resultMap);
                        }
                    }
                }
            } catch (IOException e) {
                return ResultUtil.ReturnError(e.getMessage());
            }
            return ResultUtil.ReturnSuccess(fileInfoList);
        } else {
            baqryxx.setSsclid(StringUtils.getGuid32());
            return ResultUtil.ReturnSuccess();
        }
    }

    /**
     * 语音播报环节
     *
     * @param baqid
     * @param bbhj
     * @return
     */
    @Override
    public ApiReturnResult<?> yybbByQtdj(String baqid, String bbhj, String rybh) {
        ApiReturnResult<?> returnResult = new ApiReturnResult<>();
        ChasRyjl ryjlByRybh = ryjlService.findRyjlByRybh(baqid, rybh);
        if (ryjlByRybh == null) {
            ResultUtil.ReturnError("人员编号为NULL");
        }
        //随身物品登记完成 嫌疑人随身物品登记完成后触发语音播报，播报内容为请带嫌疑人（具体姓名）到等候室XX（XX为具体的等候室号）
        if (YyhjEnue.SSWPDJWC.getCode().equals(bbhj)) {
            String dhsBh = ryjlByRybh.getDhsBh();
            if(StringUtils.isEmpty(dhsBh)){
                return ResultUtil.ReturnSuccess("语音播报等候室编号为NULL！");
            }
            ChasDhsKz dhsKz = dhsKzService.findById(dhsBh);
            if (dhsKz == null) {
                return ResultUtil.ReturnSuccess("语音播报等候室不存在！");
            }
            String qyid = dhsKz.getQyid();
            if(StringUtils.isEmpty(qyid)){
                return ResultUtil.ReturnSuccess("语音播报等候室区域ID为空！");
            }
            ChasXtQy xtqy = qyService.findByYsid(qyid);
            if (xtqy == null) {
                return ResultUtil.ReturnSuccess("语音播报等候室区域不存在！");
            }
            String qymc = xtqy.getQymc();
            YybbSendParam sendParam = new YybbSendParam();
            sendParam.setBbhj(YyhjEnue.SSWPDJWC);
            sendParam.setBaqid(baqid);
            sendParam.setRyxm(ryjlByRybh.getXm());
            sendParam.setDhsmc(qymc);
            YybbUtil.sendYybb(sendParam);
        }
        //安全检查播报 人身安全检查完成时触发语音播报，以人身安全检查页面提交为准，播报内容为请带嫌疑人（具体姓名）进行随身物品登记
        if (YyhjEnue.RSAQJCWC.getCode().equals(bbhj)) {
            YybbSendParam sendParam = new YybbSendParam();
            sendParam.setBbhj(YyhjEnue.RSAQJCWC);
            sendParam.setBaqid(baqid);
            sendParam.setRyxm(ryjlByRybh.getXm());
            YybbUtil.sendYybb(sendParam);
        }
        return ResultUtil.ReturnSuccess("播报成功");
    }

    @Override
    public ApiReturnResult<?> getRyxxByImg(HttpServletRequest request, String baqid, String base64) {
        if (StringUtils.isEmpty(baqid) || StringUtils.isEmpty(base64)) {
            return ResultUtil.ReturnError("参数不能为空!");
        }
        ChasBaq baq = baqService.findById(baqid);
        if (baq == null) {
            return ResultUtil.ReturnError("获取办案区信息失败!");
        }

        try {
            if (StringUtils.isNotEmpty(baq.getDwxtbh())) {
                RecognitionParam recognitionParam = new RecognitionParam();
                recognitionParam.setDwxtbh(baq.getDwxtbh());
                recognitionParam.setBaqid(baqid);
                recognitionParam.setBase64Str(base64);
                recognitionParam.setTzlx("baqry");
                List<FaceResult> faceResults = baqFaceService.recognition(recognitionParam);
                if(ObjectUtils.isEmpty(faceResults) || faceResults.size() == 0){
                    return ResultUtil.ReturnSuccess("未查询到人员信息!");
                }else{
                    FaceResult faceResult = faceResults.get(0);
                    String rybh = faceResult.getTzbh();
                    ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                    if(ObjectUtils.isEmpty(baqryxx)){
                        return ResultUtil.ReturnSuccess("未匹配到办案区人员信息!");
                    }else{
                        ChasRyjl ryjl = ryjlService.findRyjlByRybh(null, rybh);
                        Map<String, Object> map = new HashMap<>();
                        map.put("baqryxx", baqryxx);
                        if(ObjectUtils.isEmpty(ryjl)){
                            map.put("lczt", "01");
                        }else{
                            map.put("lczt", ryjl.getLczt());
                        }
                        return ResultUtil.ReturnSuccess("人脸识别成功", map);
                    }
                }
            } else {
                return ResultUtil.ReturnError("办案区单位系统编号不能为空!");
            }
        } catch (Exception e) {
            log.error("系统异常{}", e);
            e.printStackTrace();
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
    }
}
