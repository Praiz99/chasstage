package com.wckj.chasstage.api.server.release.rest.khd;


import com.wckj.chasstage.api.def.baq.model.BaqBean;
import com.wckj.chasstage.api.def.baq.model.BaqParam;
import com.wckj.chasstage.api.def.baq.service.ApiBaqService;
import com.wckj.chasstage.api.def.baq.service.ApiBaqrefService;
import com.wckj.chasstage.api.def.common.service.DicService;
import com.wckj.chasstage.api.def.khd.service.ApiXxdjzdService;
import com.wckj.chasstage.api.def.qtdj.model.*;
import com.wckj.chasstage.api.def.qtdj.service.ApiQtdjService;
import com.wckj.chasstage.api.def.sys.model.MjxxParam;
import com.wckj.chasstage.api.def.sys.model.OrgParam;
import com.wckj.chasstage.api.def.sys.service.ApiJdoneSysService;
import com.wckj.chasstage.api.def.wpgl.model.SswpBeanView;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wutl
 * @Title: 信息登记终端控制
 * @Package 信息登记终端控制
 * @Description: 信息登记终端控制
 * @date 2020-9-179:25
 */
@Api(tags = "信息登记终端")
@RestController
@RequestMapping("/api/rest/chasstage/xxdjzd/apiXxdjzdService")
public class ApiXxdjzdController extends RestApiBaseController<ApiXxdjzdService> {
    @Autowired
    private DicService dicService;
    @Autowired
    private ApiBaqrefService baqrefService;
    @Autowired
    private ApiQtdjService qtdjService;
    @Autowired
    private ApiJdoneSysService apiJdoneSysService;
    @Autowired
    private ApiBaqService apiBaqService;

    @RequestMapping(value = "/checkOn",method = RequestMethod.GET)
    @ApiOperation(value = "考勤打卡", notes = "考勤打卡")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "mjjh", value = "民警警号", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "dkType", value = "打开类型", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> checkOn(HttpServletRequest request, String mjjh, String dkType) {
        return null;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getDicData",method = RequestMethod.GET)
    @ApiOperation(value = "获取字典", notes = "获取字典")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<?> getDicData(HttpServletRequest request) {
        return dicService.getDicData();
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/getOrgsByBaqid",method = RequestMethod.GET)
    @ApiOperation(value = "根据办案区id获取单位", notes = "获取字典")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = OrgBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string")})
    public ApiReturnResult<?> getOrgsByBaqid(HttpServletRequest request,String baqid) {
        return baqrefService.getOrgsByBaqid(baqid);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "民警信息查询", notes = "民警信息查询")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = UserBean.class)})
    @RequestMapping(value = "/getMjxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getMjxx(HttpServletRequest request, MjxxParam mjxxParam) {
        return apiJdoneSysService.getMjxxPageData(request, mjxxParam);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "机构信息查询", notes = "机构信息查询")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = OrgBean.class)})
    @RequestMapping(value = "/getOrgxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getOrgxx(HttpServletRequest request, OrgParam orgParam) {
        return apiJdoneSysService.getOrgPageData(request,orgParam);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "岗位腕带编号登录", notes = "根据腕带编号登录岗位")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/loginByWdbh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "wdbhl", value = "低频腕带编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> loginByWdbh(HttpServletRequest request,String wdbhl) {
        return qtdjService.loginByWdbh(wdbhl, "");
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取警情信息", notes = "获取警情信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = JqxxBean.class)})
    @RequestMapping(value = "/getJqxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getJqxx(HttpServletRequest request, JqxxParam jqxxParam) {
        return qtdjService.getJqxxPageData(jqxxParam);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取案件信息", notes = "获取案件信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = AjxxBean.class)})
    @RequestMapping(value = "/getAjxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getAjxx(HttpServletRequest request, AjxxParam ajxxParam) {
        return qtdjService.getAjxxPageData(ajxxParam);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "校验腕带", notes = "校验腕带")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/validateWd", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> validateWd(HttpServletRequest request, WdParam wdParam) {
        return qtdjService.validateWd(wdParam);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "预分配储物柜", notes = "预分配储物柜")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = Yfpwpgxx.class)})
    @RequestMapping(value = "/getBoxNoByType", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "箱子类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "boxType", value = "是否大小柜（true 是 false 否）", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getBoxNoByType(HttpServletRequest request, String baqid,String type,String boxType) {
        return qtdjService.getBoxNoByType(boxType,baqid,type);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存人员信息", notes = "保存人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/SaveWithUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> SaveWithUpdate(HttpServletRequest request, RyxxBean ryxxBean) {
        return qtdjService.SaveWithUpdate(ryxxBean);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取同案人员信息", notes = "获取同案人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = TaryBean.class)})
    @RequestMapping(value = "/getDataByYwbh", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getDataByYwbh(HttpServletRequest request, TaryParam param) {
        return qtdjService.getDataByYwbh(param);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取人员信息", notes = "获取人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxResult.class)})
    @RequestMapping(value = "/getRyxxAllByRybh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getRyxxAllByRybh(HttpServletRequest request,String rybh) {
        return qtdjService.getRyxxAllByRybh(rybh);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getBaqPageData", method = RequestMethod.GET)
    @ApiOperation(value = "办案区查询", notes = "办案区查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = BaqBean.class)})
    public ApiReturnResult<String> getBaqPageData(HttpServletRequest request, BaqParam baqParam) {
        return (ApiReturnResult<String>) apiBaqService.getBaqPageData(baqParam);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取办案区人员信息列表", notes = "获取办案区人员信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxBean.class)})
    @RequestMapping(value = "/getBaqryxxDataGrid", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getBaqryxxDataGrid(HttpServletRequest request, RyxxParam ryxxParam) {
        return this.service.getBaqryxxDataGrid(ryxxParam);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取送押管理页面总数", notes = "获取送押管理页面总数")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/getsyglymzs", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getsyglymzs(HttpServletRequest request) {
        Map<String,Object> maps = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        param.put("ryzt","01");
        param.put("yytj","1");
        param.put("baqid",request.getParameter("baqid"));
        maps.put("yytj",service.getBaqryxxList(param).size());
        param.clear();
        param.put("ryzt","03");
        param.put("cRyqx","04");
        param.put("baqid",request.getParameter("baqid"));
        maps.put("jls",service.getBaqryxxList(param).size());
        param.clear();
        param.put("ryzt","03");
        param.put("cRyqx","01");
        param.put("baqid",request.getParameter("baqid"));
        maps.put("kss",service.getBaqryxxList(param).size());
        return ResultUtil.ReturnSuccess(maps);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "根据腕带编号获取送押人员", notes = "根据腕带编号获取送押人员")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "wdbhl", value = "低频腕带编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "syzt", value = "送押状态（解除手环01，送押入区02）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/findSyryByWdbhl", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> findSyryByWdbhl(HttpServletRequest request, String wdbhl,String baqid,String syzt) {
        return this.service.findSyryByWdbhl(wdbhl,baqid,syzt);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "根据腕带编号解除手环", notes = "根据腕带编号解除手环")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxBean.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ywbh", value = "业务编号（人员编号或者/腕带编号）", required = false, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/jcwd", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> jcwd(HttpServletRequest request, String ywbh,String baqid) {
        if(StringUtils.isEmpty(ywbh)){
            return ResultUtil.ReturnError("业务编号为空");
        }
        String ywlx = ywbh.substring(0, 1);
        if("R".equals(ywlx)){
            return this.service.jcdwByRybh(ywbh,baqid);
        }else{
            return this.service.jcwd(ywbh,baqid);
        }
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "根据版本号和客户端类型获取客户端升级信息", notes = "根据版本号和客户端类型获取客户端升级信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionNo", value = "版本号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "clientType", value = "客户端类型", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/getClientVersion", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getClientVersion(HttpServletRequest request, String versionNo,String clientType) {
        return this.service.getClientVersion(versionNo, clientType);
    }

    @ApiAccessNotValidate
    @ApiOperation(value = "获取国籍等引用表字典", notes = "获取国籍等引用表字典")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicMark", value = "字典名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkDate", value = "更新时间", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/getRefDicDataByName", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getRefDicDataByName(HttpServletRequest request, String dicMark,String checkDate) {
        return this.service.getRefDicDataByName(dicMark, checkDate);
    }
    @ApiAccessNotValidate
    @ApiOperation(value = "获取有柜子的在区嫌疑人信息", notes = "获取有柜子的在区嫌疑人信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = SswpBeanView.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/getYfpwpgBaqryxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getYfpwpgBaqryxx(HttpServletRequest request, Integer page, Integer rows, String  baqid) {
        return service.getYfpwpgBaqryxx(page,rows,baqid);
    }

    @ApiAccessNotValidate
    @ApiOperation(value = "分页获取办案区物品柜", notes = "分页获取办案区物品柜")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = SswpBeanView.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/getWpgPageData", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getWpgPageData(HttpServletRequest request, Integer page, Integer rows, String  baqid) {
        return service.getWpgPageData(page,rows,baqid);
    }
}
