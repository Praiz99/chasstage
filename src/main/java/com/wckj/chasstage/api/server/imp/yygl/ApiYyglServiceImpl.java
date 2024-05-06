package com.wckj.chasstage.api.server.imp.yygl;

import com.wckj.api.def.zfba.model.ApiGgYwbh;
import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.def.yygl.model.YyglBean;
import com.wckj.chasstage.api.def.yygl.model.YyglParam;
import com.wckj.chasstage.api.def.yygl.model.YyjyParam;
import com.wckj.chasstage.api.def.yygl.service.ApiYyglService;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.imp.device.internal.EntranceOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.chasstage.modules.clcrjl.service.ChasYwClcrjlService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.entity.JdoneSysRole;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * 预约管理
 */
@Service
public class ApiYyglServiceImpl implements ApiYyglService {
    private static final Logger log = LoggerFactory.getLogger(ApiYyglServiceImpl.class);
    @Autowired
    private ChasYwYyService apiService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ApiGgYwbhService jdoneComYwbhService;
    @Autowired
    private IWdService wdService;
    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private ChasXtCldjService cldjService;
    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private ChasYwClcrjlService chasYwClcrjlService;
    @Autowired
    private EntranceOper entranceOper;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private JdoneSysUserService jdoneSysUserService;
    @Autowired
    private JdoneSysRoleService jdoneSysRoleService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYwYy xgpz = apiService.findById(id);
        if (xgpz != null) {
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到预约信息");
    }

    @Override
    public ApiReturnResult<?> save(YyglBean bean) {
        // 新增
        ApiGgYwbh comYwbh = null;
        try {
            if(StringUtils.isEmpty(bean.getBaqid())){
                return ResultUtil.ReturnError("民警办案区id不能为空");
            }
            BaqConfiguration configuration = baqznpzService.findByBaqid(bean.getBaqid());
            if(StringUtils.isEmpty(bean.getBaqmc())){
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if(baq!=null){
                    bean.setBaqmc(baq.getBaqmc());
                }else{
                    return ResultUtil.ReturnError("无民警办案区信息");
                }
            }
            if (StringUtils.isEmpty(bean.getYyrsfzh())) {
                return ResultUtil.ReturnError("预约人证件号码不能为空");
            }
            ChasXtMjzpk mjzpk = mjzpkService.findBySfzh(bean.getYyrsfzh());
            if (mjzpk == null) {
                return ResultUtil.ReturnError("民警照片信息未注册");
            }
            ChasYwYy yy = new ChasYwYy();

            Map<String, Object> param2 = new HashMap<>();
            param2.put("yyrsfzh", bean.getYyrsfzh());
            param2.put("nowTime", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            param2.put("notcrqzt", "03");
            //param2.put("yyzt","2");
            List<ChasYwYy> list = apiService.findList(param2, "kssj desc");
            if (("web".equals(bean.getFrom())||"client".equals(bean.getFrom())) && !list.isEmpty()) {//防止客户端重复预约
                if ("02".equals(list.get(0).getCrqzt())) {
                    return ResultUtil.ReturnError("民警已经入区，不需要预约了");
                }
            }
            if (!list.isEmpty()) {//App预约后，现场登记
                yy = list.get(0);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotEmpty(bean.getKssj())) {
                yy.setKssj(sdf.parse(bean.getKssj().replace("+", " ")));
            }
            if (StringUtils.isNotEmpty(bean.getJssj())) {
                yy.setJssj(sdf.parse(bean.getJssj().replace("+", " ")));
            }
            //防止copyBeanNotNull2Bean出错
            bean.setKssj(null);
            bean.setJssj(null);
            MyBeanUtils.copyBeanNotNull2Bean(bean, yy);
            if (StringUtils.isEmpty(yy.getYyrdwmc())) {
                yy.setYyrdwdm(mjzpk.getDwdm());
                yy.setYyrdwmc(mjzpk.getDwmc());
            }
            if (StringUtils.isEmpty(yy.getYyrxm())) {
                yy.setYyrxm(mjzpk.getMjxm());
            }
            if (StringUtils.isEmpty(yy.getYyrjh())) {
                yy.setYyrjh(mjzpk.getMjjh());
            }
            if (StringUtils.isEmpty(yy.getBaqid())) {
                yy.setBaqid(mjzpk.getBaqid());
                yy.setBaqmc(mjzpk.getBaqmc());
            }
            if (yy.getRqsj() == null) {
                yy.setRqsj(new Date());
            }
            if (yy.getRqsj() != null) {
                yy.setCqsj(new Date(yy.getRqsj().getTime() + 1000 * 3600 * 24));
            }
            if (yy.getKssj() == null) {
                yy.setKssj(new Date());
            }
            if (yy.getKssj() != null) {
                yy.setJssj(new Date(yy.getKssj().getTime() + 1000 * 3600 * 24));
            }
            yy.setSysxs((short) 0);
            yy.setSxssl((short) 0);
            if (yy.getRysl() == null) {
                yy.setRysl((short) 0);
            }
            if (StringUtils.isEmpty(yy.getRybh())) {
                String dwdm = WebContext.getSessionUser() != null ? WebContext.getSessionUser().getOrgCode() : mjzpk.getDwdm();
                comYwbh = jdoneComYwbhService.getYwbh("M", dwdm, null);
                yy.setRybh(comYwbh.getYwbh());
            }
            //客户端预约才绑定胸卡
            if (!configuration.getDwHkrl() && "client".equals(bean.getFrom()) && StringUtils.isNotEmpty(yy.getShbh())) {
                bindXk(yy);
            }
            yy.setYyzt("2");
            if ("app".equals(bean.getFrom())||"web".equals(bean.getFrom())) {
                yy.setCrqzt("01");//预约
            } else {
                yy.setCrqzt("02");//入区
            }

            Map<String, Object> map = apiService.saveOrUpdate(yy);
            if (map.get("success") != null && (boolean) map.get("success")) {
                if ("client".equals(bean.getFrom())) {//现场登记时
                    mjrqService.insertMjrq(yy, mjzpk.getZpid());
                } else if ("app".equals(bean.getFrom())) {
                    //异步发送门禁
                    ChasYwYy yyFace = new ChasYwYy();
                    MyBeanUtils.copyBeanNotNull2Bean(yy, yyFace);
                    fixedThreadPool.execute(() ->
                            entranceOper.apptifs(mjzpk, yyFace)
                    );
                }
                //开启民警人脸定位
                if(configuration.getDwHkrl()){
                    FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(mjzpk.getZpid());
                    if (fileInfoObj == null) {
                        log.warn("民警开启人脸定位时未查询到人脸照片！");
                    }else{
                        ChasYwMjrq mjrq = mjrqService.findById(yy.getId());
                        if(mjrq != null){
                            Map<String, Object> response = mjrqService.saveOrUpdateMjRldw(mjrq);
                            if((Boolean) response.get("status")){
                                log.info("民警开启人脸定位成功");
                            }else{
                                log.info("民警开启人脸定位失败！");
                            }
                        }
                    }
                }
                return ResultUtil.ReturnSuccess("保存成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (comYwbh != null) {
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            log.error("保存预约信息出错", e);
        }
        return ResultUtil.ReturnError("保存失败");
    }

    //绑定胸卡
    public void bindXk(ChasYwYy yy) throws Exception {
        //查找胸卡
        Map<String, Object> params = new HashMap<>();
        params.put("wdbhL", yy.getShbh());
        params.put("baqid", yy.getBaqid());
        List<ChasSb> chasSb = chasSbService.findByParams(params);
        if (chasSb.isEmpty()) {
            throw new Exception("胸卡不存在，不能绑定");
        }
        String wdbhH = chasSb.get(0).getKzcs1();
        //绑定胸卡
        DevResult result = wdService.wdBd(yy);
        if (result.getCode() != 1) {
            throw new Exception("绑定胸卡失败:" + result.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id -> apiService.deleteById(id));
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除预约信息出错", e);
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(YyglParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);

//        DataQxbsUtil.getSelectAll(apiService, map);
//        SessionUser sessionUser = WebContext.getSessionUser();
//        if (sessionUser != null) {
//            String userRoleId = WebContext.getSessionUser().getRoleCode();
//            if (!"0101".equals(userRoleId)) {
//                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
//                map.put("sydwdm", orgCode);
//            }
//        }
        if(StringUtils.isNotEmpty(String.valueOf(map.get("roleCode")))){
            JdoneSysUser user = jdoneSysUserService.findSysUserByIdCard(String.valueOf(map.get("yyrsfzh")));
            String role = String.valueOf(map.get("roleCode"));
            if("400003".equals(role) || "400002".equals(role)){//单位法制员、办案单位领导查询本单位
                map.put("yyrsfzh", "");
                map.put("qxbs", "org");
                map.put("orgCode", user.getOrgCode());
            }else if("300002".equals(role) || "200002".equals(role) || "100002".equals(role) || "300003".equals(role) || "200003".equals(role)){//分局领导和法制领导查询分局
                map.put("yyrsfzh", "");
                map.put("qxbs", "reg");
                map.put("orgCode", user.getOrgCode());
            }
            //其他只查看自己预约的
        }
        PageDataResultSet<ChasYwYy> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        for (ChasYwYy yy : pageData.getData()) {
            if (StringUtils.isNotEmpty(yy.getYyrsfzh())) {
                params.put("mjsfzh", yy.getYyrsfzh());
                List<ChasXtMjzpk> list = mjzpkService.findByParams(params);
                if (list.size() > 0 && StringUtils.isNotEmpty(list.get(0).getZpid())) {
                    FileInfoObj fileList = FrwsApiForThirdPart.getFileInfoByBizId(list.get(0).getZpid());
                    if (fileList != null) {
                        yy.setImgUrl(fileList.getDownUrl());
                    }
                }
            }
        }
        result.put("total", pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData()
                , new String[]{"YYLX", "ZD_SYS_SF", "FSARRQZT", "YYZT"}
                , new String[]{"yylx", "sysxs", "crqzt", "yyzt"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> yyjy(YyjyParam param) {
        Map<String, Object> params = new HashMap<>();
        String sfzh = param.getSfzh();
        if (StringUtils.isEmpty(sfzh)) {
            return ResultUtil.ReturnSuccess("民警身份号不能为空！");
        }
        params.put("mjsfzh", sfzh);
        params.put("spzt", "1");
        List<ChasXtMjzpk> mjzpks = mjzpkService.findByParams(params);
        if (mjzpks != null && !mjzpks.isEmpty()) {
            return ResultUtil.ReturnSuccess("人脸已注册", mjzpks.get(0));
        }
        return ResultUtil.ReturnError("人脸未注册，请携带身份证到现场登记");
    }

    /**
     * 检查车牌是否预约
     *
     * @param baqid
     * @param cphm
     * @param crlx
     * @return
     */
    @Override
    public ApiReturnResult<?> checkYyCphm(String baqid, String cphm, String crlx) {
        //进入
        log.info("开始检查车牌是否预约检查" + baqid + "," + cphm + "," + crlx);
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        if ("1".equals(crlx)) {
            log.info("车辆进入");
            Map<String, Object> param = new HashMap<>();
            String curtime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
            param.put("curtime", curtime);
            param.put("baqid", baqid);
            param.put("cphm", cphm);
            List<ChasYwYy> byParams = apiService.findByParams(param);
            if (byParams.size() > 0) {
                log.info("存在车辆预约信息");
                ChasYwYy chasYwYy = byParams.get(0);
                ChasXtCldj chasXtCldj = cldjService.findByCphm(cphm);
                if (chasXtCldj == null) {
                    log.info("不存在车辆信息，保存车辆信息");
                    chasXtCldj = new ChasXtCldj();
                    chasXtCldj.setId(StringUtils.getGuid32());
                    chasXtCldj.setBaqid(chasYwYy.getBaqid());
                    chasXtCldj.setBaqmc(chasYwYy.getBaqmc());
                    // 1 预约类型 2 押送类型
                    chasXtCldj.setCllx("1");
                    chasXtCldj.setQssj(chasYwYy.getKssj());
                    chasXtCldj.setJssj(chasYwYy.getJssj());
                    chasXtCldj.setClNumber(cphm);
                    chasXtCldj.setIsdel(0);
                    chasXtCldj.setZrrXm(chasYwYy.getYyrxm());
                    chasXtCldj.setZrrSfzh(chasYwYy.getYyrsfzh());
                    chasXtCldj.setDwmc(chasYwYy.getYyrdwmc());
                    JdoneSysOrg jdoneSysOrg = orgService.findByCode(chasYwYy.getYyrdwdm());
                    if (jdoneSysOrg != null) {
                        chasXtCldj.setDwxtbh(jdoneSysOrg.getSysCode());
                    }
                    chasXtCldj.setLrsj(new Date());
                    chasXtCldj.setLrrSfzh(chasYwYy.getLrrSfzh());
                    chasXtCldj.setXgsj(new Date());
                    chasXtCldj.setXgrSfzh(chasYwYy.getLrrSfzh());
                    cldjService.save(chasXtCldj);
                    log.info("保存车辆信息结束");
                }
                //插入车辆进入记录

                try {
                    log.info("保存车辆出入记录信息");
                    ChasYwClcrjl chasYwClcrjl = new ChasYwClcrjl();
                    chasYwClcrjl.setId(StringUtils.getGuid32());
                    chasYwClcrjl.setIsdel(0);
                    chasYwClcrjl.setDataFlag(chasXtCldj.getDataFlag());
                    chasYwClcrjl.setLrrSfzh(chasXtCldj.getLrrSfzh());
                    chasYwClcrjl.setLrsj(chasXtCldj.getLrsj());
                    chasYwClcrjl.setXgrSfzh(chasXtCldj.getXgrSfzh());
                    chasYwClcrjl.setXgsj(chasXtCldj.getXgsj());
                    chasYwClcrjl.setClid(chasXtCldj.getId());
                    chasYwClcrjl.setBaqid(chasXtCldj.getBaqid());
                    chasYwClcrjl.setBaqmc(chasXtCldj.getBaqmc());
                    chasYwClcrjl.setDwxtbh(chasXtCldj.getDwxtbh());
                    chasYwClcrjl.setDwmc(chasXtCldj.getDwmc());
                    chasYwClcrjl.setClBrand(chasXtCldj.getClBrand());
                    chasYwClcrjl.setClModel(chasXtCldj.getClModel());
                    chasYwClcrjl.setClNumber(chasXtCldj.getClNumber());
                    chasYwClcrjl.setZrrSfzh(chasXtCldj.getZrrSfzh());
                    chasYwClcrjl.setZrrXm(chasXtCldj.getZrrXm());
                    chasYwClcrjl.setRqsj(new Date());
                    chasYwClcrjlService.save(chasYwClcrjl);
                    log.info("保存车辆出入记录信息结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                returnResult.setCode("200");
                returnResult.setMessage("存在车牌预约！");
            } else {
                returnResult.setCode("500");
                returnResult.setMessage("不存在车牌预约！");
            }
        } else {
            //出
            log.info("车辆离开办案区");
            Map<String, Object> param = new HashMap<>();
            param.put("clNumber", cphm);
            ChasYwClcrjl clcrjl = chasYwClcrjlService.findByParams(param);
            clcrjl.setCqsj(new Date());
            chasYwClcrjlService.update(clcrjl);
        }
        return returnResult;
    }


    @Override
    public ApiReturnResult<?> getBaqrefByOrgcode(String orgCode) {
        ApiReturnResult<Map<String, Object>> returnResult = new ApiReturnResult<>();
        try {
            if(StringUtils.isEmpty(orgCode)){
                throw new BizDataException("单位不能为NULL");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("sydwdm", orgCode);
            List<ChasBaq> baqList = baqService.findListByParams(param);
            returnResult.setCode("200");
            returnResult.setMessage("获取成功！");
            Map<String, Object> baqData = new HashMap<>();
            baqData.put("total", baqList.size());
            baqData.put("rows", baqList);
            returnResult.setData(baqData);
            return returnResult;
        } catch (Exception e) {
            returnResult.setCode("500");
            returnResult.setMessage("获取失败："+e.getMessage());
            return returnResult;
        }
    }
}
