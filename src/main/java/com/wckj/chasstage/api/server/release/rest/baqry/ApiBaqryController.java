package com.wckj.chasstage.api.server.release.rest.baqry;

import com.wckj.chasstage.api.def.baqry.model.CrqkBean;
import com.wckj.chasstage.api.def.baqry.model.TjdjBean;
import com.wckj.chasstage.api.def.baqry.model.UnProcessActParam;
import com.wckj.chasstage.api.def.baqry.service.ApiBaqryService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.api.def.qtdj.model.RyxxParam;
import com.wckj.chasstage.api.def.qtdj.model.UserParam;
import com.wckj.chasstage.api.def.qtdj.service.ApiQtdjService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.pdf.PdfComprehensiveUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.annotation.ApiAccessValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicCodeObj;
import com.wckj.framework.core.dic.DicObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.com.dic.core.ComDicManager;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicCode;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicCodeService;
import com.wckj.jdone.modules.com.export.entity.JdoneExport;
import com.wckj.jdone.modules.sys.util.DicUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "人员管理（后台）")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baqry/apiBaqryService")
public class ApiBaqryController extends RestApiBaseController<ApiBaqryService> {

    protected Logger log = LoggerFactory.getLogger(ApiBaqryController.class);

    @Autowired
    private ComDicManager manager;
    @Autowired
    private JdoneComDicCodeService codeService;
    @Autowired
    private ApiQtdjService qtdjService;
    @Autowired
    private ApiBaqryService baqryService;
    @Autowired
    private ChasBaqryxxService baqryxxService;

    private Map<String, Object> objectMap = new HashMap<String, Object>();  //存储字典只信息

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取办案区人员信息列表", notes = "获取办案区人员信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RyxxBean.class)})
    @RequestMapping(value = "/getBaqryxxDataGrid", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getBaqryxxDataGrid(HttpServletRequest request, RyxxParam ryxxParam) {
        return qtdjService.getBaqryxxDataGrid(ryxxParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存/修改人员信息", notes = "保存人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/SaveWithUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> SaveWithUpdate(HttpServletRequest request, RyxxBean ryxxBean) {
        return baqryService.SaveWithUpdateByForm(ryxxBean);
    }


    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "删除人员", notes = "删除人员入区")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/deleteRyrq", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> deleteRyrq(HttpServletRequest request, String ids) {
        return baqryService.deleteBaqryxx(ids);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "启动审批流程", notes = "启动审批流程")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> startProcess(NodeProcessCmdObj2 nodeProcessCmdObj2) {
        SessionUser sessionUser = WebContext.getSessionUser();
        if (sessionUser == null) {
            return ResultUtil.ReturnError("当前登录信息丢失，请刷新页面或重新登录！");
        }
        String currentOrgSysCode = sessionUser.getCurrentOrgSysCode();
        if(StringUtil.isEmpty(currentOrgSysCode)){
            return ResultUtil.ReturnError("当前登录信息丢失，请刷新页面或重新登录！");
        }
        return baqryService.startProcess(nodeProcessCmdObj2);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "执行审批流程", notes = "启动审批流程")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/executeProcess", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj cmdObj) {
        return baqryService.executeProcess(cmdObj);
    }


    //checked
    @RequestMapping(value = "/personnelReturn", method = RequestMethod.GET)
    @ApiOperation(value = "人员归区", notes = "人员归区")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ryid", value = "人员id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<String> personnelReturn(HttpServletRequest request, String ryid) {
        return baqryService.PersonnelReturn(ryid);
    }


    //checked
    @RequestMapping(value = "/getBaqsyqkdjbByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "人员编号获取登记表", notes = "人员编号获取登记表")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<String> getBaqsyqkdjbByRybh(HttpServletRequest request, String rybh) {
        try {
            byte[] by = PdfComprehensiveUtil.buildMsgObjectRyxx_Interface(rybh, false);
            BASE64Encoder encoder = new BASE64Encoder();
            return ResultUtil.ReturnSuccess("加载成功!", encoder.encode(by));
        } catch (Exception e) {
            return ResultUtil.ReturnError("加载人员登记表异常:" + e.getMessage());
        }
    }

    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/getRyxxByWdbhl", method = RequestMethod.GET)
    @ApiOperation(value = "根据腕带获取人员信息", notes = "根据腕带获取人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "wdbhl", value = "腕带编号", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "sfzq", value = "1或空为查询在区人员，0为查询最新手环使用人", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<RyxxBean> getRyxxByWdbhl(HttpServletRequest request, String wdbhl, String sfzq) {
        ApiReturnResult<RyxxBean> result = new ApiReturnResult<>();
        if ("0".equals(sfzq)) {
            result = baqryService.findCurrentByWdbhl(wdbhl);
        } else {
            result = baqryService.getRyxxIdByWdbhL(wdbhl);
        }
        return result;
    }

    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/getRyzpByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "根据人员编号获取照片", notes = "根据人员编号获取照片")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> getRyzpByRybh(HttpServletRequest request, String rybh) {
        return baqryService.getPhotoByRybh(rybh);
    }


    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/canleaveByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "人员是否出所审批成功", notes = "人员是否出所审批成功")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> canleaveByRybh(HttpServletRequest request, String rybh) {
        return baqryService.canleaveByRybh(rybh);
    }

    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/departure", method = RequestMethod.GET)
    @ApiOperation(value = "根据人员编号出所", notes = "根据人员编号出所")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "qwImgId", value = "取物图片id", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "fhzt", value = "返回状态", dataType = "string",paramType = "query")})
    public ApiReturnResult<String> Departure(HttpServletRequest request, String rybh, String qwImgId,String fhzt) {
        return baqryService.Departure(rybh, qwImgId,fhzt);
    }

    //checked
    @RequestMapping(value = "/getMjData", method = RequestMethod.GET)
    @ApiOperation(value = "获取民警信息", notes = "获取民警信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = UserParam.class)})
    public ApiReturnResult<?> getMjData(HttpServletRequest request, UserParam users) {
        return baqryService.getMjData(users);
    }

    //checked
    @RequestMapping(value = "/rytjdj", method = RequestMethod.GET)
    @ApiOperation(value = "获取体检登记", notes = "获取体检登记")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = TjdjBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<TjdjBean> rytjdj(HttpServletRequest request, String rybh) {
        ApiReturnResult<TjdjBean> apiReturnResult = this.baqryService.getTytjdj(rybh);
        return apiReturnResult;
    }

    //checked
    @RequestMapping(value = "/saveRytjdj", method = RequestMethod.GET)
    @ApiOperation(value = "保存/修改体检登记", notes = "保存/修改体检登记")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = TjdjBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ryid", value = "人员id", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> saveRytjdj(HttpServletRequest request, TjdjBean tjdjBean) {
        ApiReturnResult<String> apiReturnResult = this.baqryService.saveTytjdj(tjdjBean);
        return apiReturnResult;
    }

    //checked
    @RequestMapping(value = "/rycrqk", method = RequestMethod.GET)
    @ApiOperation(value = "进出情况", notes = "进出情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = CrqkBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "rows", value = "每页数", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "rybh", value = "人员编号", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> rycrqk(HttpServletRequest request, int page, int rows, String rybh) {
        Map<String, Object> param = new HashMap<>();
        param.put("rybh", rybh);
        ApiReturnResult<Map<String, Object>> apiReturnResult = this.baqryService.getRycrqk(page, rows, param);
        return apiReturnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/confirmLeavingUnProcess", method = RequestMethod.POST)
    @ApiOperation(value = "无流程版出所", notes = "进出情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<?> confirmLeavingUnProcess(HttpServletRequest request, UnProcessActParam processActParam) {
        return baqryService.confirmLeavingunprocess(processActParam);
    }

    /**
     * 批量出所  无流程版
     *
     * @return
     */
    /*@RequestMapping(value = "/deavebatchBaqry", method = RequestMethod.POST)
    @ApiOperation(value = "无流程版批量出所", notes = "进出情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<?> deavebatchBaqry(HttpServletRequest request,String rybhs,String csyy,String qtyy){
        return baqryService.deavebatchBaqry(rybhs,csyy,qtyy);
    }*/


    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/associationJq", method = RequestMethod.GET)
    @ApiOperation(value = "关联警情", notes = "关联警情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "jqbh", value = "警情编号", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> associationJq(HttpServletRequest request, String rybh, String jqbh) {
        return baqryService.associationJq(rybh, jqbh);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/updateRyztByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "修改人员流程状态", notes = "关联警情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "ryzt", value = "人员状态（01在所 02 在所审批 03待出所 04 已出所，人员出所时，如果csfs是0终端出所，人员状态修改为03即可。）", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> updateRyztByRybh(HttpServletRequest request, String rybh, String ryzt) {
        return baqryService.updateRyztByRybh(rybh, ryzt);
    }

    @RequestMapping(value = "/getwgsjtj", method = RequestMethod.GET)
    @ApiOperation(value = "获取网格化模块数据", notes = "获取网格化模块数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<?> getwgsjtj(HttpServletRequest request, String sysOrgCode) {
        return baqryService.getwgsjtj(sysOrgCode);
    }

    @RequestMapping(value = "/confirmLeaving", method = RequestMethod.POST)
    @ApiOperation(value = "确认出所", notes = "确认出所")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "cssj", value = "出所时间", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "rybh", value = "人员编号", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "bizId", value = "出所照片业务id", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> confirmLeaving(HttpServletRequest request, String cssj, String rybh, String bizId) {
        ApiReturnResult<?> apiReturnResult = this.service.saveConfirmLeaving(cssj, rybh, bizId);
        return apiReturnResult;
    }

    @RequestMapping(value = "/departOpencwg", method = RequestMethod.POST)
    @ApiOperation(value = "出所开柜", notes = "出所开柜")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "出所时间", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "rybh", value = "人员编号", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "cwgbh", value = "储物柜编号", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "sjgbh", value = "手机柜编号", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> departOpencwg(HttpServletRequest request, String baqid, String rybh, String cwgbh, String sjgbh) {
        ApiReturnResult<?> apiReturnResult = this.service.saveDepartOpencwg(baqid, rybh, cwgbh, sjgbh);
        return apiReturnResult;
    }

    @ApiOperation(value = "出办案区人员信息", notes = "出办案区人员信息")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public String exportData(HttpServletRequest request, String exportStr) throws Exception {
        if (StringUtils.isNotEmpty(exportStr)) {
            exportStr = java.net.URLDecoder.decode(exportStr, "UTF-8");
        }
        JdoneExport jdoneExport = JsonUtil.getObjectFromJsonString(exportStr, JdoneExport.class);
        if ("0".equals(jdoneExport.getExportScope())) {
            String fileName = ExportFileByBrowserUtil.writeFiletoDisk(request, jdoneExport, DicUtil.translate(jdoneExport.getExportSelectData(), new String[]{"CHAS_XT_BAQ", "JDONE_SYS_ORG", "CHAS_ZD_ZB_XB", "CHAS_ZD_CASE_TSQT", "DAFS", "RYZT", "RSYY", "CSYY"}, new String[]{"baqid", "zbdwBh", "xb", "tsqt", "dafs", "ryzt", "ryzaybh", "cCsyy"}), ".xls");
            return fileName;
        } else if ("1".equals(jdoneExport.getExportScope())) {
            Map<String, Object> params = new HashMap<String, Object>();
            DataQxbsUtil.getSelectAll(baqryxxService, params);
            if (request.getParameter("ryzt") != null
                    && request.getParameter("ryzt").equals("100")) {
            } else if (request.getParameter("ryzt") != null
                    && request.getParameter("ryzt").equals("99")) {
                params.put("mjSfzh", WebContext.getSessionUser().getIdCard());
            } else if (StringUtils.equals(request.getParameter("ryzt"), "TwoDaysWithin")) {
                //默认查询两天内
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                String startTime = sdf.format(date) + " 00:00:00";
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = calendar.getTime();
                String endTime = sdf.format(date) + " 23:59:59";
                params.put("rssj1", startTime);
                params.put("rssj2", endTime);
            } else {
                params.putAll(MapCollectionUtil.conversionRequestToMap(request));
            }
            //如果选择的不是在逃那么就排除在逃人员
            String ryzt = request.getParameter("ryzt");
            if (StringUtils.isEmpty(ryzt) || !StringUtils.equals(ryzt, "09")) {
                params.put("ryzts", "09");
            }

            PageDataResultSet<ChasBaqryxxBq> baqryxxList = baqryxxService.getEntityPageDataBecauseQymc(-1, -1, params, "lrsj desc");
            List<ChasBaqryxxBq> baqryxxes = baqryxxList.getData();
            List<Map> datamap = com.wckj.framework.json.jackson.JsonUtil.getListFromJsonString(
                    com.wckj.framework.json.jackson.JsonUtil.getJsonString(baqryxxes), Map.class);
            initDicData(); //初始化字典值
            String dataJson = JsonUtil.toJson(datamap);
            Map<String, String> map = (Map<String, String>) objectMap.get("ZD_ZB_XB");
            for (String key : map.keySet()) {
                //"\"xb\":\""+key+"\""
                dataJson = dataJson.replaceAll("\"xb\":\"" + key + "\"", "\"xbName\":" + "\"" + map.get(key) + "\"");
            }
            map = (Map<String, String>) objectMap.get("CHAS_ZD_CASE_TSQT");
            for (String key : map.keySet()) {
                dataJson = dataJson.replaceAll("\"tsqt\":\"" + key + "\"", "\"tsqtName\":" + "\"" + map.get(key) + "\"");
            }
            //替换多个code值并存的情况
            String pattern = "(\\d{2},+)+\\d{2},*";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(dataJson);
            Map<String, String> tsqtmap = (Map<String, String>) objectMap.get("CHAS_ZD_CASE_TSQT");
            while (m.find()) {
                String tsqt = m.group();
                String tsqtval = "";
                for (String key : tsqt.split(",")) {
                    tsqtval += tsqtmap.get(key) + ",";
                }
                tsqtval = tsqtval.substring(0, tsqtval.length() - 1);
                dataJson = dataJson.replaceAll("\"tsqt\":\"" + tsqt + "\"", "\"tsqtName\":" + "\"" + tsqtval + "\"");
            }
            map = (Map<String, String>) objectMap.get("RYZT");
            for (String key : map.keySet()) {
                dataJson = dataJson.replaceAll("\"ryzt\":\"" + key + "\"", "\"ryztName\":" + "\"" + map.get(key) + "\"");
            }
            dataJson = dataJson.replaceAll("ccsyy", "cCsyy");
            dataJson = dataJson.replaceAll("rrssj", "rRssj");
            dataJson = dataJson.replaceAll("ccssj", "cCssj");
            dataJson = dataJson.replaceAll("\"cCssj\":null", "\"cCssj\":\"\"");
            map = (Map<String, String>) objectMap.get("BAQRYSSWPZT");
            for (String key : map.keySet()) {
                dataJson = dataJson.replaceAll("\"sswpzt\":\"" + key + "\"", "\"sswpztName\":" + "\"" + map.get(key) + "\"");
            }
            dataJson = dataJson.replaceAll("\"sfythcjmc\":null", "\"sfythcjmcName\":\"\"");
            dataJson = dataJson.replaceAll("\"sfythcj\":null", "\"sfythcjmc\":\"未采集\"");
            dataJson = dataJson.replaceAll("\"sfythcj\":0", "\"sfythcjmc\":\"未采集\"");
            dataJson = dataJson.replaceAll("\"sfythcj\":1", "\"sfythcjmc\":\"已采集\"");
            datamap = JsonUtil.parse(dataJson, List.class);
            String fileName = ExportFileByBrowserUtil.writeFiletoDisk(request, jdoneExport, datamap, ".xls");
            return fileName;
        }
        return "";
    }

    private void initDicData() {
        DicObj dicObj = manager.getDicObj("ZD_ZB_XB");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dicId", dicObj.getId());
        List<JdoneComDicCode> codeObjs = codeService.findList(params, null);
        Map<String, Object> map = new HashMap<String, Object>();
        for (DicCodeObj codeObj : codeObjs) {
            map.put(codeObj.getCode(), codeObj.getName());
        }
        objectMap.put("ZD_ZB_XB", map);
        dicObj = manager.getDicObj("CHAS_ZD_CASE_TSQT");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        map = new HashMap<String, Object>();
        for (DicCodeObj codeObj : codeObjs) {
            map.put(codeObj.getCode(), codeObj.getName());
        }
        objectMap.put("CHAS_ZD_CASE_TSQT", map);
        //RYZT
        dicObj = manager.getDicObj("RYZT");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        map = new HashMap<String, Object>();
        for (DicCodeObj codeObj : codeObjs) {
            map.put(codeObj.getCode(), codeObj.getName());
        }
        objectMap.put("RYZT", map);
        //CSYY
        dicObj = manager.getDicObj("CSYY");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        map = new HashMap<String, Object>();
        for (DicCodeObj codeObj : codeObjs) {
            map.put(codeObj.getCode(), codeObj.getName());
        }
        objectMap.put("CSYY", map);
        //BAQRYSSWPZT
        dicObj = manager.getDicObj("BAQRYSSWPZT");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        map = new HashMap<String, Object>();
        for (DicCodeObj codeObj : codeObjs) {
            map.put(codeObj.getCode(), codeObj.getName());
        }
        objectMap.put("BAQRYSSWPZT", map);
    }

    @ApiOperation(value = "导出人员登记表", notes = "导出人员登记表")
    @RequestMapping(value = "/exportrydjb", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<String> exportrydjb(HttpServletRequest request, String ryid) {
        try {
            ChasBaqryxx baqryxx = baqryxxService.findById(ryid);
            if (StringUtil.isEmpty(baqryxx.getRyxm())) {  //如果人员名称为空，那么则填充人员编号
                baqryxx.setRyxm("嫌疑人员-在区人员");
            }
            byte[] wspdf = PdfComprehensiveUtil.buildMsgObjectRyxx_Interface(baqryxx.getRybh(), true);
            ExportFileByBrowserUtil.writeFiletoDisk(request, baqryxx.getRyxm(), wspdf, ".pdf");
            return ResultUtil.ReturnSuccess(baqryxx.getRyxm());
        } catch (Exception e) {
            log.error("导出办案区人员登记表异常:", e);
        }
        return ResultUtil.ReturnSuccess("");
    }

    /**
     * 下载文件数据，根据请求地址参数
     *
     * @param fileName
     */
    @ApiAccessNotValidate
    @ApiOperation(value = "下载文件数据，根据请求地址参数", notes = "载人员excel文件数据，根据请求地址参数")
    @RequestMapping(value = "/downLoadFile/{fileName}", method = RequestMethod.GET)
    public void getDownFileDataByUrl(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        ExportFileByBrowserUtil.getDownFileDataByUrl(fileName, request, response);
    }


    //checked
    @ApiAccessNotValidate
    @RequestMapping(value = "/getRyxxByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "根据人员编号获取人员信息(不分在区不在区)", notes = "根据人员编号获取人员信息(不分在区不在区)")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = false, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "sfzh", value = "人员身份证号", required = false, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = false, dataType = "string",paramType = "query")
    })
    public ApiReturnResult<RyxxBean> getRyxxByRybh(HttpServletRequest request, String rybh,String sfzh,String baqid) {
        ChasBaqryxx baqryxx=null;
        if(StringUtils.isNotEmpty(rybh)){
            baqryxx= this.baqryxxService.findBaqryxByRybhAll(rybh,baqid);
        }
        else if(StringUtils.isNotEmpty(sfzh)){
            baqryxx= this.baqryxxService.findBaqryxBySfzhAll(sfzh,baqid);
        }
        RyxxBean ryxxBean = new RyxxBean();
        try {
            if(Objects.nonNull(baqryxx)){
                MyBeanUtils.copyBeanNotNull2Bean(baqryxx, ryxxBean);
                ryxxBean.setcRyqx(baqryxx.getCRyqx());
                ryxxBean.setRySfzh(baqryxx.getRysfzh());
                ryxxBean.setRyZaybh(baqryxx.getRyzaybh());
                ryxxBean.setRyZaymc(baqryxx.getRyzaymc());
                ryxxBean.setrRssj(baqryxx.getRRssj());
                ryxxBean.setcCssj(baqryxx.getCCssj());
                return ResultUtil.ReturnSuccess(ryxxBean);
            }else {
                return ResultUtil.ReturnSuccess("未查询到该人员");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError("人员信息获取失败！" + e.getMessage());
        }
    }



    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改人员信息（无业务）", notes = "修改人员信息（无业务）")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/updateRyxx", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> updateRyxx(HttpServletRequest request, RyxxBean ryxxBean) {
        return baqryService.updateRyxx(ryxxBean);
    }

    @ApiAccessValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取人员信息数据，常口数据", notes = "获取人员信息数据，常口数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/getSfzhyyxxData", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getSfzhyyxxData(HttpServletRequest request,String zjhm,String xm,String type,Integer page,Integer rows) {
        return baqryService.getSfzhyyxxData(zjhm,xm,type,page,rows);
    }

    @ApiAccessValidate
    @ApiOperation(value = "获取出所摄像头信息", notes = "获取出所摄像头信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/getCsSxtInfo", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getCsSxtInfo(HttpServletRequest request,String baqid) {
        return baqryService.getCsSxtInfo(baqid);
    }
}
