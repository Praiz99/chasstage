package com.wckj.chasstage.api.server.release.rest.baq;

/**
 * @author wutl
 * @Title: 办案区管理功能
 * @Package
 * @Description:
 * @date 2020-9-39:57
 */



import com.wckj.chasstage.api.def.baq.model.*;
import com.wckj.chasstage.api.def.baq.service.ApiBaqService;
import com.wckj.chasstage.common.task.RqcsTask;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 出所管理
 * @Package
 * @Description:
 * @date 2020-9-516:29
 */
@Api(tags = "办案区配置管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baq/apiBaqService")
public class ApiBaqController extends RestApiBaseController<ApiBaqService> {

    @Autowired
    private ApiBaqService apiBaqService;

    @RequestMapping(value = "/saveBaq", method = RequestMethod.POST)
    @ApiOperation(value = "新增办案区", notes = "新增办案区")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {rows:'''',total:''},isSuccess: true,message: \"查询成功\"},rows中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<String> saveBaq(HttpServletRequest request, BaqBean baqBean) {
        return (ApiReturnResult<String>) apiBaqService.SaveWithUpdate(baqBean);
    }

    @RequestMapping(value = "/updateBaq", method = RequestMethod.POST)
    @ApiOperation(value = "修改办案区", notes = "修改办案区")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<String> updateBaq(HttpServletRequest request, BaqBean baqBean) {
        return (ApiReturnResult<String>) apiBaqService.SaveWithUpdate(baqBean);
    }

    @RequestMapping(value = "/deleteBaq", method = RequestMethod.GET)
    @ApiOperation(value = "删除办案区", notes = "删除办案区")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},rows中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<String> deleteBaq(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiBaqService.deletes(id);
    }


    @RequestMapping(value = "/getBaqPageData", method = RequestMethod.GET)
    @ApiOperation(value = "办案区查询", notes = "办案区查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = BaqBean.class)})
    public ApiReturnResult<String> getBaqPageData(HttpServletRequest request, BaqParam baqParam) {
        return (ApiReturnResult<String>) apiBaqService.getBaqPageData(baqParam);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存/修改功能配置", notes = "保存/修改功能配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @RequestMapping(value = "/saveOrUpdateBaqcfg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrUpdateBaqcfg(HttpServletRequest request, BaqznpzBean baqznpzBean) {
        SessionUser sessionUser = WebContext.getSessionUser();
        ApiReturnResult<?> result = this.service.saveBaqznpz(baqznpzBean);
        return ResultUtil.WebResult(result);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存/修改签名配置", notes = "保存/修改签名配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @RequestMapping(value = "/saveOrUpdateBaqqz", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrUpdateBaqqz(HttpServletRequest request, SignConfigBean signConfigBean) {
        ApiReturnResult<?> result = this.service.saveSignConfig(signConfigBean);
        return ResultUtil.WebResult(result);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改签字类型", notes = "保存/修改签名配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @RequestMapping(value = "/xgqzlx", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xgqzlx(HttpServletRequest request, String id,String[] checkedCities) {
        ApiReturnResult<?> result = this.service.xgqzlx(id,checkedCities);
        return ResultUtil.WebResult(result);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存/修改文书字号配置", notes = "保存/修改文书字号配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: '',data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @RequestMapping(value = "/saveOrUpdateBaqzh", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrUpdateBaqzh(HttpServletRequest request, WszhBean wszhBean) {
        return ResultUtil.WebResult(this.service.saveWithUpdateInstrument(wszhBean));
    }


    @RequestMapping(value = "/getOrgsByBaqid", method = RequestMethod.POST)
    @ApiOperation(value = "办案区id获取单位", notes = "办案区id获取单位")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = BaqBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "办案区id", value = "baqid", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> getOrgsByBaqid(HttpServletRequest request, String baqid) {
        SessionUser sessionUser = WebContext.getSessionUser();
        return null;
    }


    @RequestMapping(value = "/baqtb", method = RequestMethod.GET)
    @ApiOperation(value = "校验设备系统（先校验设备，然后同步区域，再同步设备）", notes = "校验设备系统")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '校验办案区完毕'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> baqtb(HttpServletRequest request, String baqid) {
        return service.baqtb(baqid);
    }

    @RequestMapping(value = "/tbqy", method = RequestMethod.GET)
    @ApiOperation(value = "同步区域", notes = "同步区域")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '同步区域数量12'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> tbqy(HttpServletRequest request, String baqid) {
        ApiReturnResult<?> apiReturnResult = service.tbBaqqy(baqid);
        return apiReturnResult;
    }

    @RequestMapping(value = "/tbAllsb", method = RequestMethod.GET)
    @ApiOperation(value = "同步所有设备", notes = "同步所有设备")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {msg:'同步设备成功',cab:{code:1,message:同步储物柜数量:13},tag:{code:1,message:同步设备数量:55}},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> tbAllsb(HttpServletRequest request, String baqid) {
        return service.tbSb(baqid);
    }

    @RequestMapping(value = "/getWszh", method = RequestMethod.GET)
    @ApiOperation(value = "获取文书字号", notes = "获取文书字号")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = WszhBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> getWszh(HttpServletRequest request, String baqid) {
        return service.getWszhPData(baqid);
    }

  /*  @RequestMapping(value = "/getBaqznpz", method = RequestMethod.GET)
    @ApiOperation(value = "获取办案区配置", notes = "获取办案区配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {msg:'同步设备成功',cab:{code:1,message:同步储物柜数量:13},tag:{code:1,message:同步设备数量:55}},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = BaqznpzBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> getBaqznpz(HttpServletRequest request, String baqid) {
        return service.tbSb(baqid);
    }*/

    @RequestMapping(value = "/getSignCfg", method = RequestMethod.GET)
    @ApiOperation(value = "获取签字捺印配置", notes = "获取签字捺印配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {msg:'同步设备成功',cab:{code:1,message:同步储物柜数量:13},tag:{code:1,message:同步设备数量:55}},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = SignConfigBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> getSignCfg(HttpServletRequest request, String baqid) {
        SignConfigParam param = new  SignConfigParam();
        ApiReturnResult<?> signConfigPageData = this.service.getBaqSingnData(baqid);
        return signConfigPageData;
    }


    @RequestMapping(value = "/getBaqDicByUser", method = RequestMethod.GET)
    @ApiOperation(value = "根据单位获取办案区字典", notes = "根据单位获取办案区字典")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {msg:'同步设备成功',cab:{code:1,message:同步储物柜数量:13},tag:{code:1,message:同步设备数量:55}},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = SignConfigBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "queryValue", value = "查询字典", required = false, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "page", value = "字典当前页", required = false, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "pagesize", value = "字典每页数", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> getBaqDicByUser(String queryValue, Integer page, Integer pagesize) {
        return service.getBaqDicByUser(queryValue, page, pagesize);
    }


    @RequestMapping(value = "/getBaqByLogin", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录人办案区信息", notes = "获取当前登录人办案区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model", response = BaqBean.class)})
    public ApiReturnResult<BaqBean> getBaqByLogin(HttpServletRequest request) {
        ApiReturnResult<BaqBean> baqresult = apiBaqService.getBaqByLogin();
        return baqresult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getBaqcfgByBaqid", method = RequestMethod.GET)
    @ApiOperation(value = "获取办案区配置", notes = "获取办案区配置")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = BaqFunCfg.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<BaqFunCfg> getBaqcfgByBaqid(HttpServletRequest request,String baqid) {
        return apiBaqService.getBaqcfgByBaqid(baqid);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getBaqgnglByBaqid", method = RequestMethod.GET)
    @ApiOperation(value = "获取办案区功能配置模版", notes = "获取办案区功能配置模版")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = BaqGnglBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = false, dataType = "string",
            paramType = "from")})
    public ApiReturnResult<List<BaqGnglBean>> getBaqgnglByBaqid(HttpServletRequest request, String baqid) {
        return apiBaqService.getBaqgnglByBaqid(baqid);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/saveBaqgnglByBaqid", method = RequestMethod.POST)
    @ApiOperation(value = "保存办案区功能配置模版", notes = "保存办案区功能配置模版")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> saveBaqgnglByBaqid(HttpServletRequest request, BaqGnglBean baqGnglBean) {
        return apiBaqService.saveBaqgnglByBaqid(baqGnglBean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/deleteBaqgnglByBaqid", method = RequestMethod.GET)
    @ApiOperation(value = "删除办案区功能配置模版", notes = "删除办案区功能配置模版")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "配置Id 主键", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> deleteBaqgnglByBaqid(HttpServletRequest request, String id) {
        return apiBaqService.deleteBaqgnglByBaqid(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/checkInBaqpz", method = RequestMethod.GET)
    @ApiOperation(value = "启用办案区功能配置模版", notes = "启用办案区功能配置模版")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = false, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "gnpzId", value = "功能配置id", required = false, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> checkInBaqpz(HttpServletRequest request, String baqid,String gnpzId) {
        return apiBaqService.checkInBaqpz(baqid, gnpzId);
    }


    @RequestMapping(value = "/getBaqDicByLogin", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录人管辖办案区", notes = "获取当前登录人管辖办案区")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> getBaqDicByLogin(HttpServletRequest request) {
        return apiBaqService.getBaqDicByLogin();
    }

    @RequestMapping(value = "getStartBhByLogin",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录单位编号开头值", notes = "获取当前登录单位编号过滤值")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> getStartBhByLogin(HttpServletRequest request) {
        return apiBaqService.getStartBhByLogin();
    }
}
