package com.wckj.chasstage.api.server.imp.baqry;

import com.wckj.api.def.zfba.service.ythba.ApiYthbaService;
import com.wckj.chasstage.api.def.baqry.model.TjdjBean;
import com.wckj.chasstage.api.def.baqry.model.UnProcessActParam;
import com.wckj.chasstage.api.def.baqry.service.ApiBaqryService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.api.def.qtdj.model.UserParam;
import com.wckj.chasstage.api.server.device.IWpgService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.tjdj.entity.ChasTjdj;
import com.wckj.chasstage.modules.tjdj.service.ChasTjdjService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApiBaqryServiceImpl implements ApiBaqryService {
    protected Logger log = LoggerFactory.getLogger(ApiBaqryServiceImpl.class);

    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasSswpxxService sswpxxService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ChasYmCsspService csspService;
    @Autowired
    private ChasTjdjService tjdjService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private IWpgService wpgService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasXtBaqznpzService chasXtBaqznpzService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Autowired
    private ChasSbService sbService;
    @Override
    public ApiReturnResult<?> deleteBaqryxx(String ids) {
        try {
            String[] idstr = ids.split(",");
            for (String id : idstr) {
                baqryxxService.deleteBaqryxxs(id);
            }
            return ResultUtil.ReturnSuccess("删除成功!");
        } catch (Exception e) {
            log.error("deleteBaqryxx:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<RyxxBean> getRyxxIdByWdbhL(String wdbhL) {
        ChasBaqryxx ryxxBywdbhL = null;
        if (StringUtil.isNotEmpty(wdbhL)) {
            ryxxBywdbhL = baqryxxService.findRyxxBywdbhL(wdbhL);
        }
        RyxxBean ryxxBean = new RyxxBean();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(ryxxBywdbhL, ryxxBean);
            ryxxBean.setcRyqx(ryxxBywdbhL.getCRyqx());
            ryxxBean.setRySfzh(ryxxBywdbhL.getRysfzh());
            return ResultUtil.ReturnSuccess(ryxxBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError("人员信息获取失败！");
        }

    }

    @Override
    public ApiReturnResult<String> PersonnelReturn(String ryid) {
        Map<String, Object> result = baqryxxService.PersonnelReturn(ryid);
        if ((boolean) result.get("success")) {
            return ResultUtil.ReturnSuccess((String) result.get("msg"));
        } else {
            return ResultUtil.ReturnError((String) result.get("msg"));
        }
    }

    @Override
    public ApiReturnResult<String> getPhotoByRybh(String rybh) {

        Map<String, Object> params = new HashMap<>();
        params.put("rybh", rybh);
        if(StringUtil.isEmpty(rybh)){
            return ResultUtil.ReturnError("人员编号不能为空!");
        }
        List<ChasBaqryxx> baqryxxes = baqryxxService.findList(params, null);

        if (baqryxxes.isEmpty()) {
            return ResultUtil.ReturnError("暂无人员数据!");
        }

        ChasBaqryxx baqryxx = baqryxxes.get(0);

        try {
            List<FileInfoObj> infoObjs = FrwsApiForThirdPart.getFileInfoList(baqryxx.getZpid());  //人像图片
            if (!infoObjs.isEmpty()) {
                FileInfoObj infoObj = infoObjs.get(0);
                return ResultUtil.ReturnSuccess("请求成功!", infoObj.getDownUrl());
            }
        } catch (Exception e) {
            log.error("getPeopleAvatarByBusinessId:", e);
            return ResultUtil.ReturnError("请求文件服务器异常:" + e.getMessage());
        }
        return ResultUtil.ReturnError("该人员没有照片!");
    }

    @Override
    public ApiReturnResult<String> Departure(String rybh, String qwid,String fhzt) {
        try {
            Map<String, Object> result = baqryxxService.Departure(rybh, qwid,fhzt);
            if ((int) result.get("code") == 0) {
                return ResultUtil.ReturnSuccess((String) result.get("message"));
            } else {
                return ResultUtil.ReturnError((String) result.get("message"));
            }
        } catch (Exception e) {
            log.error("Departure:", e);
            return ResultUtil.ReturnError("出所失败:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<String> canleaveByRybh(String rybh) {
        if (StringUtils.isEmpty(rybh)) {
            return ResultUtil.ReturnError("人员编号不能为空!");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("rybh", rybh);
        List<ChasBaqryxx> baqryxxList = baqryxxService.findList(params, null);
        if (baqryxxList.isEmpty()) {
            return ResultUtil.ReturnError("暂无数据!");
        }

        ChasBaqryxx baqryxx = baqryxxList.get(0);
        if (StringUtils.equals(baqryxx.getRyzt(), SYSCONSTANT.BAQRYZT_DCS)) {  //待出所
            return ResultUtil.ReturnSuccess("该人员可以出所!");
        } else {
            return ResultUtil.ReturnError("该人员未提交审批或审批未通过!");
        }
    }

    @Override
    public ApiReturnResult<?> startProcess(NodeProcessCmdObj2 cmdObj) {
        try {
            Map<String, Object> param = cmdObj.getBizFormData();
            String ryid = (String) param.get("ryid");
            String csyy = (String) param.get("csyy");
            String csyyName = (String) param.get("csyyName");
            String type = (String) param.get("type");
            String qtyy = (String) param.get("qtyy");
            String wpztData = (String) param.get("wpztData");  //人员出区物品状态修改
            cmdObj.setCurrentProUser(WebContext.getSessionUser());
            baqryxxService.startProcess(ryid, csyy, csyyName, type, qtyy, cmdObj);

            //修改物品处理状态  正式出区才处理物品信息
            if (StringUtil.isNotEmpty(wpztData) && StringUtil.equals(type,"02")) {
                List<Map> maps = JsonUtil.getListFromJsonString(wpztData, Map.class);
                for (Map<String, Object> map : maps) {
                    String id = (String) map.get("id");
                    String wpclzt = (String) map.get("wpclzt");
                    ChasSswpxx sswpxx = sswpxxService.findById(id);
                    if (sswpxx != null) {
                        sswpxx.setWpclzt(wpclzt);  //物品状态，没有物品处理状态了
                        //sswpxx.setWpzt(wpclzt);  //物品状态，没有物品处理状态了
                        sswpxxService.update(sswpxx);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("startProcess:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("提交审核成功!");
    }

    @Override
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj cmdObj) {
        try {
            String result = cmdObj.getProResultType();
            String bizId = cmdObj.getBizId();
            String spyj = cmdObj.getProOpinion();
            String spzt = "1";

            JdoneSysUserService jdoneSysUserService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
            JdoneSysUser spr = jdoneSysUserService.findSysUserByIdCard(cmdObj.getSessionUser().getIdCard());
            if ("approve".equals(result)) {//同意
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            } else if ("disagree".equals(result)) {//不同意
                spzt = "-1";
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            } else {//退回
                spzt = "-1";
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            }
        } catch (Exception e) {
            log.error("executeProcess:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("审批成功!");
    }

    @Override
    public ApiReturnResult<?> SaveWithUpdateByForm(RyxxBean ryxxBean) {
        Map<String, Object> result = null;
        try {
            String id = ryxxBean.getId();
            String baqid = ryxxBean.getBaqid();
            BaqConfiguration configuration = chasXtBaqznpzService.findByBaqid(baqid);
            result = baqryxxService.SaveWithUpdateByForm(ryxxBean);
            ChasBaqryxx baqryxx = (ChasBaqryxx) result.get("baqryxx");
            if(StringUtil.isEmpty(id)){
                log.info("SaveWithUpdate:新增登记区轨迹:"+(baqryxx != null));
                if(baqryxx != null){
                    log.info("SaveWithUpdate:新增登记区轨迹:"+baqryxx.getRybh()+","+baqryxx.getRyxm());
                    rygjService.manualAddRygj(baqryxx);
                }
            }
            //开始海康人脸定位
            if(configuration.getDwHkrl()){
                String bizId = baqryxx.getZpid();
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
            log.error("保存办案区人员失败:", e);
            return ResultUtil.ReturnError("保存失败:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getMjData(UserParam jdoneSysUser) {
        if (StringUtils.isEmpty(jdoneSysUser.getLoginId())
                && StringUtils.isEmpty(jdoneSysUser.getName())
                && StringUtils.isEmpty(jdoneSysUser.getIdCard())
                && StringUtils.isEmpty(jdoneSysUser.getOrgCode())
                && StringUtils.isEmpty(jdoneSysUser.getOrgName())
                && StringUtils.isEmpty(jdoneSysUser.getOrgSysCode())
                && StringUtils.isEmpty(jdoneSysUser.getSex())
        ) {
            log.error("getMjData参数为空");
            return ResultUtil.ReturnError("系统异常:" + "getMjData参数为空");

        }
        List<JdoneSysUser> list = userService.findList(jdoneSysUser, null);
        return ResultUtil.ReturnSuccess("获取数据成功", list);
    }

    /**
     * 出入情况查询
     *
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<Map<String, Object>> getRycrqk(int page, int rows, Map<String, Object> param) {
        ApiReturnResult<Map<String, Object>> apiReturnResult = new ApiReturnResult<Map<String, Object>>();
        try {
            PageDataResultSet<ChasYmCssp> qt = csspService.getEntityPageData(page, rows, param, " xgsj desc");
            apiReturnResult.setMessage("查询成功");
            apiReturnResult.setCode("200");
            Map<String, Object> data = new HashMap<>();
            data.put("total", qt.getTotal());
            data.put("rows", qt.getData());
            apiReturnResult.setData(data);
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }

    /**
     * 根据人员编号获取人员体检登记
     *
     * @param rybh
     * @return
     */
    @Override
    public ApiReturnResult<TjdjBean> getTytjdj(String rybh) {
        ApiReturnResult<TjdjBean> apiReturnResult = new ApiReturnResult<>();
        TjdjBean tjdjBean = new TjdjBean();
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        ChasTjdj tjdj = tjdjService.getTjdjByRyid(baqryxx.getId());
        if (tjdj == null) {
            tjdjBean.setId("");
            tjdjBean.setRyid(baqryxx.getId());
            tjdjBean.setBcjc(StringUtils.getGuid32());
            tjdjBean.setRstj(StringUtils.getGuid32());
            tjdjBean.setXyjc(StringUtils.getGuid32());
            tjdjBean.setXbzw(StringUtils.getGuid32());
            tjdjBean.setXcg(StringUtils.getGuid32());
            tjdjBean.setXdt(StringUtils.getGuid32());
            tjdjBean.setQt(StringUtils.getGuid32());
        } else {
            try {
                MyBeanUtils.copyBean2Bean(tjdjBean, tjdj);
            } catch (Exception e) {
                apiReturnResult.setMessage("体检登记实体转化异常");
                apiReturnResult.setCode("500");
                apiReturnResult.setData(null);
                return apiReturnResult;
            }
        }
        apiReturnResult.setMessage("获取成功");
        apiReturnResult.setCode("200");
        apiReturnResult.setData(tjdjBean);
        return apiReturnResult;
    }

    /**
     * 保存体检登记
     *
     * @param tjdjBean
     * @return
     */
    @Override
    public ApiReturnResult<String> saveTytjdj(TjdjBean tjdjBean) {
        ApiReturnResult<String> apiReturnResult = new ApiReturnResult<>();
        ChasTjdj tjdj = new ChasTjdj();
        try {
            MyBeanUtils.copyBean2Bean(tjdj, tjdjBean);
            if (StringUtil.isNotEmpty(tjdj.getId()) && !"null".equals(tjdj.getId())) {
                tjdjService.update(tjdj);
            } else {
                ChasTjdj tjdj1 = tjdjService.getTjdjByRyid(tjdj.getRyid());
                if (tjdj1 != null) {  //防止重复保存
                    tjdj.setId(tjdj1.getId());
                    tjdjService.update(tjdj);
                    apiReturnResult.setCode("200");
                    apiReturnResult.setMessage("保存成功");
                    return apiReturnResult;
                }
                tjdj.setId(StringUtils.getGuid32());
                tjdjService.save(tjdj);

                ChasBaqryxx baqryxx = baqryxxService.findById(tjdj.getRyid());
                if (baqryxx != null) {
                    //结束体检戒护
                    DevResult devResult = jhrwService.changeJhrwZt(baqryxx.getMjXm(), baqryxx.getZbdwBh(), baqryxx, SYSCONSTANT.JHRWLX_TJJH, SYSCONSTANT.JHRWZT_YZX);
                    if (devResult.getCode() != 0) {
                        throw new Exception("开启戒护任务失败:" + devResult.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("保存失败:" + e.getMessage());
            return apiReturnResult;
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> confirmLeavingunprocess(UnProcessActParam unProcessActParam) {
        try {
            String rybhs = unProcessActParam.getRybh();
            String[] rybhArr = rybhs.split(",");
            for (int i = 0; i < rybhArr.length; i++) {
                if (StringUtils.isNotEmpty(rybhArr[i])) {
                    ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybhArr[i]);
                    baqryxx.setRyzt(SYSCONSTANT.BAQRYZT_DCS);
                    baqryxx.setCCsyy(unProcessActParam.getCsyy());
                    baqryxxService.update(baqryxx);
                    // TODO: 2020-11-7 送押、戒护
                }
            }
        } catch (Exception e) {
            log.error("confirmLeavingunprocess:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功！");
    }

    @Override
    public ApiReturnResult<?> deavebatchBaqry(String rybhs, String csyy, String qtyy) {
        try {
            baqryxxService.deavebatchBaqry(rybhs, csyy, qtyy);
        } catch (Exception e) {
            log.error("deavebatchBaqry:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功！");
    }

    /**
     * 关联警情
     *
     * @param rybh 人员编号
     * @param jqbh 警情编号
     * @return
     */
    @Override
    public ApiReturnResult<String> associationJq(String rybh, String jqbh) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        if (baqryxx != null) {
            String oldJqbh = baqryxx.getJqbh();
            baqryxx.setJqbh(jqbh);
            baqryxxService.update(baqryxx);
            if (!StringUtils.equals(oldJqbh, jqbh)) {  //如果已修改
                //如果已修改,那么同步人员记录表中的业务编号，同时修改相关的同案人员的业务编号
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(), baqryxx.getRybh());
                ryjl.setYwbh(jqbh);
                ryjl.setXgrSfzh(baqryxx.getMjSfzh());
                ryjlService.update(ryjl);
            }
            returnResult.setMessage("关联成功");
            returnResult.setCode("200");
        } else {
            returnResult.setCode("500");
            returnResult.setMessage("办案区不存在该人员：" + rybh);
        }
        return returnResult;
    }

    /**
     * 修改人员审批流程状态
     *
     * @param rybh
     * @param ryzt
     * @return
     */
    @Override
    public ApiReturnResult<String> updateRyztByRybh(String rybh, String ryzt) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        if (baqryxx != null) {
            baqryxx.setRyzt(ryzt);
            baqryxxService.update(baqryxx);
        } else {
            returnResult.setCode("500");
            returnResult.setMessage("人员不存在：" + rybh);
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> getwgsjtj(String orgSysCode) {
        if(StringUtil.isEmpty(orgSysCode)){
            orgSysCode = WebContext.getSessionUser().getOrgSysCode();
        }
        Map<String,String> map = baqryxxService.getwgsjtj(orgSysCode);
        return ResultUtil.ReturnSuccess(map);
    }

    @Override
    public ApiReturnResult<RyxxBean> findByRybh(String rybh) {
        if(StringUtils.isEmpty(rybh)){
            throw new BizDataException("人员编号为空！");
        }
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        if(baqryxx == null){
            return ResultUtil.ReturnError("不存在人员信息");
        }
        RyxxBean ryxxBean = new RyxxBean();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(baqryxx, ryxxBean);
            ryxxBean.setcRyqx(baqryxx.getCRyqx());
            ryxxBean.setRySfzh(baqryxx.getRysfzh());
            ryxxBean.setRyZaybh(baqryxx.getRyzaybh());
            ryxxBean.setRyZaymc(baqryxx.getRyzaymc());
            ryxxBean.setrRssj(baqryxx.getRRssj());
            ryxxBean.setcCssj(baqryxx.getCCssj());
        } catch (Exception e) {
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess(ryxxBean);
    }

    @Override
    public ApiReturnResult<?> saveConfirmLeaving(String cssj, String rybh, String bizId) {
        ApiReturnResult<?> apiReturnResult = new ApiReturnResult<>();
        try {
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
            String baqid = baqryxx.getBaqid();
            BaqConfiguration configuration = chasXtBaqznpzService.findByBaqid(baqid);
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtil.isNotEmpty(cssj)) {
                baqryxx.setCCssj(sim.parse(cssj + ":00"));
            }
            baqryxxService.DepartureBynotDevice(baqryxx, bizId);
            //结束海康人脸定位
            if(configuration.getDwHkrl()){
                baqryxxService.endRldw(baqid, baqryxx.getRybh(), baqryxx.getRegisterCode());
            }
            //删除下发的海康人脸门禁
            new Thread(() -> {
                iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("R",baqryxx.getId(),baqryxx.getRyxm(),baqryxx.getZpid(),baqryxx.getBaqid(),new Date());
            }).start();
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("操作成功！");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("操作失败：" + e.getMessage());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> saveDepartOpencwg(String baqid, String rybh, String cwgbh, String sjgbh) {
        ApiReturnResult<?> apiReturnResult = new ApiReturnResult<>();
        if (StringUtils.isEmpty(baqid) || StringUtils.isEmpty(rybh)) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("办案区/人员信息为空");
            return apiReturnResult;
        }
        if (StringUtils.isEmpty(cwgbh)||"null".equals(cwgbh)) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("无物品柜信息。");
            return apiReturnResult;
        }
        try {
            wpgService.openBgCwg(baqid, rybh, cwgbh);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("开柜成功！");
        } catch (Exception e) {
            e.printStackTrace();
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("开柜失败！" + e.getMessage());
        }
        return apiReturnResult;
    }

    /**
     * 查询腕带最新使用人员信息
     *
     * @param wdbhl
     * @return
     */
    @Override
    public ApiReturnResult<RyxxBean> findCurrentByWdbhl(String wdbhl) {
        ChasBaqryxx ryxxBywdbhL = null;
        if (StringUtil.isNotEmpty(wdbhl)) {
            ryxxBywdbhL = baqryxxService.findCurrentByWdbhl(wdbhl);
        }
        RyxxBean ryxxBean = new RyxxBean();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(ryxxBywdbhL, ryxxBean);
            ryxxBean.setcRyqx(ryxxBywdbhL.getCRyqx());
            ryxxBean.setRySfzh(ryxxBywdbhL.getRysfzh());
            return ResultUtil.ReturnSuccess(ryxxBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError("人员信息获取失败！");
        }
    }

    /**
     * 人员信息修改（无业务）
     *
     * @param ryxxBean
     * @return
     */
    @Override
    public ApiReturnResult<?> updateRyxx(RyxxBean ryxxBean) {
        String id = ryxxBean.getId();
        if(StringUtils.isEmpty(id)){
            return ResultUtil.ReturnError("人员id空，无法更正！");
        }
        ChasBaqryxx baqryxx = baqryxxService.findById(id);
        if (baqryxx == null) {
            return ResultUtil.ReturnError("人员不存在！");
        }
        Date rssj = ryxxBean.getrRssj();
        Date cssj = ryxxBean.getcCssj();
        String ryZaybh = ryxxBean.getRyZaybh();
        String ryZaymc = ryxxBean.getRyZaymc();
        String csyy = ryxxBean.getcCsyy();
        String ryqx = ryxxBean.getcRyqx();
        Date aqjckssj = ryxxBean.getAqjckssj();
        Date aqjcjssj = ryxxBean.getAqjcjssj();
        baqryxx.setRRssj(rssj);
        baqryxx.setCCssj(cssj);
        baqryxx.setRyzaybh(ryZaybh);
        baqryxx.setRyzaymc(ryZaymc);
        baqryxx.setCCsyy(csyy);
        baqryxx.setCRyqx(ryqx);
        baqryxx.setAqjcjssj(aqjcjssj);
        baqryxx.setAqjckssj(aqjckssj);
        baqryxxService.update(baqryxx);
        Map<String,Object> csspParam=new HashMap<>();
        csspParam.put("spzt",1);
        csspParam.put("lklx",2);
        csspParam.put("rybh",baqryxx.getRybh());
        List<ChasYmCssp> csspList = csspService.findList(csspParam, "xgsj desc");
        if(csspList.size()>0){
            ChasYmCssp chasYmCssp = csspList.get(0);
            chasYmCssp.setLksj(cssj);
            chasYmCssp.setLkyy(baqryxx.getCCsyy());
            chasYmCssp.setLkyydm(baqryxx.getCRyqx());
            chasYmCssp.setXgsj(new Date());
            csspService.update(chasYmCssp);
        }
        return ResultUtil.ReturnSuccess("更正成功！");
    }

    @Override
    public ApiReturnResult<?> getSfzhyyxxData(String sfzh, String xm, String type, Integer page, Integer rows) {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, String> param = new HashMap<String, String>();
        ApiYthbaService ythService = ServiceContext.getServiceByClass(ApiYthbaService.class);
        SessionUser user = WebContext.getSessionUser();
        param.put("sfzh", user.getIdCard());
        param.put("name", user.getName());
        param.put("orgCode", user.getOrgCode());
        param.put("orgName",user.getOrgName());
        param.put("clientIp",WebContext.getClientIp());
        try {
            if("gabRyxx".equals(type)){
                /*ArrayList<Map<String, Object>> list1 = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("xm","姓名");
                map.put("xb","01");
                map.put("csrq",new Date());
                map.put("sfzh","159845633021457");
                map.put("zbdw","主办单位");
                map.put("mz","05");
                map.put("jgssx","05");
                map.put("hkszd","05");
                map.put("zzxz","住址详址");
                map.put("whcd","05");
                list1.add(map);
                result.put("gabRyxx", DicUtil.translate(list1,new String[] {"ZD_ZB_XB","ZD_ZB_MZ","ZD_CASE_XZQH","ZD_CASE_XZQH","ZD_ZB_XL"}, new String[] {"xb","mz","jgssx","hkszd","whcd"}));*/
                Map<String, Object> map1 = ythService.getGabPageData(sfzh, xm, param);
                if(map1.get("rows")!=null){
                    if(map1.get("rows") instanceof List){
                        List<Map<String,Object>> list = (List<Map<String,Object>>)map1.get("rows");
                        result.put("gabRyxx", DicUtil.translate(list,new String[] {"ZD_ZB_XB","ZD_ZB_MZ","ZD_CASE_XZQH","ZD_CASE_XZQH","ZD_ZB_XL"}, new String[] {"xb","mz","jgssx","hkszd","whcd"}));
                    }else{
                        result.put("gabRyxx",new ArrayList<Map<String,Object>>());
                    }
                }
            }else if("qsldrkRyxx".equals(type)){
                /*ArrayList<Map<String, Object>> list1 = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("xfwcs","现服务处所");
                map.put("xm","姓名");
                map.put("xb","01");
                map.put("sfzh","159845633021457");
                map.put("zbdw","主办单位");
                map.put("mz","05");
                map.put("zzqx","暂住区县");
                map.put("zzdz","暂住地址");
                map.put("dqrq",new Date());
                map.put("csrq",new Date());
                map.put("hjdxz","户籍地现址");
                list1.add(map);
                result.put("qsldrkRyxx",DicUtil.translate(list1,new String[] {"ZD_ZB_XB","ZD_ZB_MZ"}, new String[] { "xb","mz"}));*/
                result.put("qsldrkRyxx",new ArrayList<Map<String,Object>>());

                /*Map<String, Object> map2 = ythService.getQsldrkPageData(sfzh, xm, param);
                if(map2.get("rows")!=null){
                    if(map2.get("rows") instanceof List){
                        List<Map<String,Object>> list = (List<Map<String,Object>>)map2.get("rows");
                        result.put("qsldrkRyxx",DicUtil.translate(list,new String[] {"ZD_ZB_XB","ZD_ZB_MZ"}, new String[] { "xb","mz"}));
                    }else{
                        result.put("qsldrkRyxx",new ArrayList<Map<String,Object>>());
                    }
                }*/
            }else{
                // 人员查重数据--办案区人员信息
                param.clear();
                param.put("rySfzh", sfzh);
                param.put("ryxm", xm);
                PageDataResultSet<ChasBaqryxxBq> list = baqryxxService.getEntityPageDataBecauseQymc(page, rows, param, " r_rssj desc,id ");
                result.put("total", list.getTotal());
                List<Map<String, Object>> list1 = DicUtil.translate(list.getData(), new String[]{"ZD_ZB_XB"}, new String[]{"xb"});
                result.put("ryccRyxx", list1);
            }
        }catch (Exception e) {
            log.error("getRyxxData:",e);
            return ResultUtil.ReturnError("查询异常");
        }
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getCsSxtInfo(String baqid) {
        if(StringUtils.isEmpty(baqid)){
            return ResultUtil.ReturnError("参数错误");
        }
        try {
            Map map = new HashMap();
            map.put("baqid",baqid);
            map.put("sbgn","4");
            map.put("sblx",SYSCONSTANT.SBLX_SXT);
            List<ChasSb> list = sbService.findByParams(map);
            if(list!=null&&!list.isEmpty()){
                map.clear();
                String userpwd=list.get(0).getKzcs4();
                String[] strings = userpwd.split(":");
                map.put("ip", list.get(0).getKzcs2());
                map.put("port", list.get(0).getKzcs3());
                map.put("username", strings[0]);
                map.put("password", strings[1]);
                return ResultUtil.ReturnSuccess("获取出所摄像头成功", map);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取出所摄像头出错", e);
        }
        return ResultUtil.ReturnSuccess("未获取到摄像头");
    }
}
