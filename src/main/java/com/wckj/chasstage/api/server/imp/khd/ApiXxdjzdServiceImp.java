package com.wckj.chasstage.api.server.imp.khd;

import com.wckj.chasstage.api.def.khd.service.ApiXxdjzdService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.api.def.qtdj.model.RyxxParam;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.imp.device.internal.ILocationOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.chasstage.modules.clsyjl.service.ChasYwClsyjlService;
import com.wckj.chasstage.modules.khdbb.service.ChasXtBbkzService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicCodeObj;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.dic.RefDicObject;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicCat;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicRef;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicCatService;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicRefService;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUserRole;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicManageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wutl
 * @Title: 信息登记终端服务
 * @Package
 * @Description:
 * @date 2020-11-49:53
 */
@Service
public class ApiXxdjzdServiceImp implements ApiXxdjzdService {
    private static final Logger log = Logger.getLogger(ApiXxdjzdServiceImp.class);
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private IWdService wdService;
    @Autowired
    private ChasXtBbkzService bbkzService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasYwClsyjlService clsyjlService;
    @Autowired
    private ChasXtCldjService cldjService;
    @Autowired
    private JdoneSysUserRoleService userRoleService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private JdoneComDicRefService refService;
    @Autowired
    private ChasSswpxgService sswpxgService;
    /**
     * 信息登记终端查询办案区人员信息
     *
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getBaqryxxDataGrid(RyxxParam param) {
        String sysCode = param.getSysCode();  //第三方接入
        String userRoleCode = param.getRoleCode();
        Map<String, Object> params = MapCollectionUtil.beanToMap(param);
        /*if(!"400008".equals(userRoleCode)){
            params.put("mjSfzh", param.getAppSfzh());
        }*/
        if("400001".equals(userRoleCode)){//办案民警
            params.put("mjSfzh", param.getAppSfzh());
        }else if("400003".equals(userRoleCode)){//办案单位领导
            params.put("qxbs", "org");
        }else if("300002".equals(userRoleCode) || "200002".equals(userRoleCode) || "100002".equals(userRoleCode) || "300003".equals(userRoleCode) || "200003".equals(userRoleCode)){
            params.put("qxbs", "reg");
        }
        if (StringUtil.isNotEmpty(sysCode) && "01".equals(param.getBizType())) {
            params.put("qxbs", "reg");
            params.put("orgSysCode", sysCode);
        }
        if("app".equals(param.getFrom())){
            String appSfzh = param.getAppSfzh();
            JdoneSysUser appuser = userService.findSysUserByIdCard(appSfzh);
            String id = appuser.getId();
            Map<String, Object> roleParam = new HashMap<>();
            roleParam.put("userId", id);
            List<JdoneSysUserRole> roleList = userRoleService.findList(roleParam, null);
            boolean isqxj = false;
            String qxbs = "org";
            String zbdwBh = param.getZbdwBh();
            if(StringUtil.isEmpty(zbdwBh)){
                log.error("app查询，主办单位编号为NULL");
                return ResultUtil.ReturnError("app查询，主办单位编号为NULL");
            }
            JdoneSysOrg sysOrg = orgService.findByCode(zbdwBh);
            String orgSysCode = sysOrg.getSysCode();
            for (int i = 0; i < roleList.size(); i++) {
                JdoneSysUserRole userRole = roleList.get(i);
                String roleCode = userRole.getRoleCode();
                //如果是区县级，则按区县级查询数据
                isqxj = roleCode.startsWith("30");
                if(isqxj){
                    qxbs = "reg";
                    break;
                }
            }
            params.put("zbdwBh", null);
            params.put("baqid", param.getBaqid());
            params.put("qxbs", qxbs);
            params.put("orgSysCode",orgSysCode);
        }
        PageDataResultSet<ChasBaqryxxBq> qt = baqryxxService.getEntityPageDataBecauseQymc(param.getPage(), param.getRows(), params, " r_rssj desc,id ");
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(qt.getTotal(), DicUtil.translate(qt.getData(), new String[]{
                        "CHAS_ZD_ZB_XB", "CHAS_ZD_CASE_TSQT", "RSYY", "RYZT",
                        "ZD_SYS_ORG_CODE_SNAME", "CSYY", "BAQRYSSWPZT", "DAFS", "CHAS_ZD_CASE_ZJLX", "CHAS_ZD_CASE_RYLB"},
                new String[]{"xb", "tsqt", "ryZaybh", "ryzt", "zbdwBh", "cCsyy", "sswpzt", "dafs", "zjlx", "rylx"})));
    }

    @Override
    public List<ChasBaqryxx> getBaqryxxList(Map<String, Object> param) {
        return baqryxxService.findList(param, null);
    }

    /**
     * 腕带编号
     *
     * @param wdbhl
     * @param baqid
     * @param syzt
     * @return
     */
    @Override
    public ApiReturnResult<?> findSyryByWdbhl(String wdbhl, String baqid, String syzt) {
        ChasBaqryxx ryxxBywdbhL = new ChasBaqryxx();
        if (StringUtil.isNotEmpty(wdbhl)) {
            ryxxBywdbhL = baqryxxService.getSyryByWdbhl(wdbhl, baqid);
            if (ryxxBywdbhL == null) {
                return ResultUtil.ReturnError("不存在此送押人员，或不属本办案区管辖！");
            }
        } else {
            return ResultUtil.ReturnError("腕带编号为NULL！");
        }
        RyxxBean ryxxBean = new RyxxBean();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(ryxxBywdbhL, ryxxBean);
            ryxxBean.setcRyqx(ryxxBywdbhL.getCRyqx());
            ryxxBean.setRySfzh(ryxxBywdbhL.getRysfzh());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError("人员信息获取失败！" + e.getMessage());
        }
        //送押状态（解除手环01，送押入区02）
        if ("02".equals(syzt)) {
            if(StringUtils.isNotEmpty(baqid)&&baqid.equals(ryxxBywdbhL.getBaqid())){
                return ResultUtil.ReturnError("暂无绑定送押人员。");
            }
            return ResultUtil.ReturnSuccess(ryxxBean);
        } else {
            if (isBindWdinDc(ryxxBywdbhL, wdbhl)) {
                return ResultUtil.ReturnSuccess(ryxxBean);
            }
        }
        return ResultUtil.ReturnError("暂无绑定送押人员！");
    }

    private boolean isBindWdinDc(ChasBaqryxx ryxx, String wdbhl) {
        ILocationOper locationOper = ServiceContext.getServiceByClass(ILocationOper.class);
        if (locationOper == null) {
            return false;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", ryxx.getBaqid());
        params.put("wdbhL", wdbhl);
        params.put("sblx", SYSCONSTANT.SBLX_BQ);
        params.put("kzcs3", "1");
        List<ChasSb> wdList = sbService.findList(params, null);
        if (wdList.isEmpty()) {
            return false;
        }

        ChasSb wd = wdList.get(0);
        DevResult dev = locationOper.wdDdl(ryxx.getBaqid(), wd);
        if (dev != null) {
            int code = dev.getCode();
            if (code == 1) {
                HashMap device = (HashMap) dev.get("device");
                if (device != null) {
                    String userId = device.get("userId") == null ? "" : device.get("userId").toString();
                    return userId.equals(ryxx.getRybh());
                }
            }
        }
        return false;
    }

    @Override
    public ApiReturnResult<?> jcwd(String wdbhl, String baqid) {
        if (StringUtil.isEmpty(wdbhl) || StringUtil.isEmpty(baqid)) {
            return ResultUtil.ReturnError("参数错误");
        }
        try {

            ChasRyjl chasRyjl = ryjlService.findByWdbhL(baqid, wdbhl);
            if (chasRyjl == null) {
                return ResultUtil.ReturnError("找不到人员信息");
            }
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(chasRyjl.getRybh());
            String cRyqx = baqryxx.getCRyqx();
            //出区原因不是 送押和刑事拘留，行政拘留的人 不能解除手环
            String syryqx = "01,04";
            if (StringUtils.isEmpty(cRyqx) || !syryqx.contains(cRyqx)) {
                return ResultUtil.ReturnError("该人员状态不能解除手环！");
            }
            DevResult result = wdService.wdJc(chasRyjl);
            if (result.getCode() == 1) {
                //processClxx(chasRyjl);
                return ResultUtil.ReturnSuccess("手环解绑成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("手环解绑出错", e);
        }
        return ResultUtil.ReturnError("手环解绑失败");
    }
    private void processClxx(ChasRyjl chasRyjl){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("baqid", chasRyjl.getBaqid());
            map.put("rybh", chasRyjl.getRybh());
            List<ChasYwClsyjl> clsyjlList = clsyjlService.findList(map, "lrsj desc");
            if(clsyjlList!=null && !clsyjlList.isEmpty()){
                for(ChasYwClsyjl clsyjl:clsyjlList){
                    if("0".equals(clsyjl.getIsend())||clsyjl.getKssj()==null){
                        clsyjl.setIsdel(1);
                        clsyjl.setIsend("2");//未使用
                        clsyjlService.update(clsyjl);
                    }
                    String clid = clsyjl.getClid();
                    log.info(chasRyjl.getRybh()+"使用了车辆"+clid);
                    int count = clsyjlService.getClsyryslByclid(clid);
                    if(count<1){//无人使用
                        log.info(chasRyjl.getRybh()+"使用车辆"+clid+"已无人使用了，变成空闲");
                        ChasXtCldj cldj = cldjService.findById(clid);
                        if(cldj!=null&&"1".equals(cldj.getClsyzt())){
                            cldj.setClsyzt("0");
                            cldjService.update(cldj);
                        }
                    }
                }

            }else{
                log.info(chasRyjl.getRybh()+"没有车辆使用记录");
            }
        } catch (Exception e) {
            log.error("处理送押车辆出错", e);
            e.printStackTrace();
        }
    }
    /**
     * 根据人员编号解除腕带编号
     *
     * @param rybh
     * @param baqid
     * @return
     */
    @Override
    public ApiReturnResult<?> jcdwByRybh(String rybh, String baqid) {
        if (StringUtil.isEmpty(rybh) || StringUtil.isEmpty(baqid)) {
            return ResultUtil.ReturnError("参数错误");
        }
        try {
            ChasRyjl chasRyjl = ryjlService.findRyjlByRybhUndel(baqid, rybh);
            if (chasRyjl == null) {
                return ResultUtil.ReturnError("找不到人员信息");
            }
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
            String cRyqx = baqryxx.getCRyqx();
            //出区原因不是 送押和刑事拘留，行政拘留的人 不能解除手环
            String syryqx = "01,04";
            if (StringUtils.isEmpty(cRyqx) || !syryqx.contains(cRyqx)) {
                return ResultUtil.ReturnError("该人员状态不能解除手环！");
            }
            DevResult result = wdService.wdJc(chasRyjl);
            if (result.getCode() == 1) {
                processClxx(chasRyjl);
                return ResultUtil.ReturnSuccess("手环解绑成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("手环解绑出错", e);
        }
        return ResultUtil.ReturnError("手环解绑失败");
    }

    @Override
    public ApiReturnResult<?> getClientVersion(String versionNo, String clientType) {
        return ResultUtil.ReturnSuccess(bbkzService.getClientVersion(versionNo, clientType));
    }

    @Override
    public ApiReturnResult<?> getRefDicDataByName(String dicMark, String checkDate) {
        //Map<String, Object> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("dicMark", dicMark);
        if(StringUtils.isNotEmpty(checkDate)){
            if(!DateTimeUtils.isValidDate(checkDate, "yyyy-MM-dd HH:mm:ss")){
                return ResultUtil.ReturnError("数据获取异常！时间格式不匹配====yyyy-MM-dd HH:mm:ss");
            }
        }else{
            checkDate = "0000-01-01 00:00:00";
        }
        try {
            List<JdoneComDicRef> dicList = refService.findList(params, "");
            PageDataResultSet<DicCodeObj> pageDataResultSet = null;
            List<JdoneComDicCat> dicCatList = new ArrayList<>();
            JdoneComDicRef jdoneComDicRef = null;
            for (JdoneComDicRef ref : dicList) {
//				JdoneComDicRef ref = dicList.get(0);
                if(StringUtils.equals(dicMark, ref.getDicMark())) {
                    jdoneComDicRef = ref;
                    RefDicObject object = new RefDicObject();
                    object.setFcode(ref.getCodeField());
                    object.setSourceMark(ref.getSourceMark());
                    object.setFname(ref.getNameField());
                    object.setFfpy(ref.getFpyField());
                    object.setTableName(ref.getTableName());
                    object.setTableMark(ref.getTableName());
                    object.setOrders(ref.getOrders());
                    object.setFilterRule(ref.getFilterRule());
                    pageDataResultSet = DicManageHelper.getRefDicCodePageData(object, -1, -1);

                    JdoneComDicCatService dicCatService = ServiceContext.getServiceByClass(JdoneComDicCatService.class);
                    Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(checkDate);
                    dicCatList = dicCatService.selectAllByUpdateTime(d);
                }
            }
            Map<String, Object> data=new HashMap<>();
            data.put("jdone_com_dic",jdoneComDicRef);
            data.put("jdone_com_dic_code",pageDataResultSet.getData());
            return ResultUtil.ReturnSuccess(data);
        }catch (Exception e){
            return ResultUtil.ReturnSuccess(e.getMessage());
        }
    }
    @Override
    public ApiReturnResult<?> getYfpwpgBaqryxx(Integer page, Integer rows, String baqid) {
        try {
            if(StringUtils.isEmpty(baqid)){
                return ResultUtil.ReturnError("办案区id为空");
            }
            Map<String, Object> param=new HashMap<>();
            param.put("baqid",baqid);
            param.put("ryzt","1");//在区
            PageDataResultSet<ChasBaqryxx> yfwpgBaqryxx = baqryxxService.getYfpwpgBaqryxx(page, rows, param,"lrsj desc");
            List<ChasBaqryxx> data = yfwpgBaqryxx.getData();
            List<Map> datamap = com.wckj.framework.json.jackson.JsonUtil.getListFromJsonString(
                    com.wckj.framework.json.jackson.JsonUtil.getJsonString(data), Map.class);
            for (Map<String, Object> item : datamap) {
                if (StringUtils.isNotEmpty((String) item.get("zpid"))) {
                    FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId((String) item.get("zpid"));
                    if (null != fileInfoObj) {
                        item.put("zpUrl", fileInfoObj.getDownUrl());
                    }else {
                        item.put("zpUrl", "");
                    }
                } else {
                    item.put("zpUrl", "");
                }
            }
            return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(yfwpgBaqryxx.getTotal(),datamap));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError(e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getWpgPageData(Integer page, Integer rows, String baqid) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            if(StringUtils.isEmpty(baqid)){
                return ResultUtil.ReturnError("办案区id为空");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("baqid",baqid);
            PageDataResultSet<ChasSswpxg> pageData = sswpxgService.getWpgPageData(page, rows, params, "lrsj desc");
            apiReturnResult.setData(ParamUtil.getDataGrid(pageData.getTotal(),pageData.getData()));
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }

}
