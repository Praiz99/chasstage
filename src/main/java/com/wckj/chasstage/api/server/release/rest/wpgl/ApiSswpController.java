package com.wckj.chasstage.api.server.release.rest.wpgl;


import com.wckj.chasstage.api.def.wpgl.model.*;
import com.wckj.chasstage.api.def.wpgl.service.ApiSswpService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lcm
 * @Title: 随身物品管理
 * @Package 随身物品管理
 * @Description: 随身物品管理
 * @date 2020-9-515:50
 */
@Api(tags = "随身物品")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baq/apiSswpService")
public class ApiSswpController extends RestApiBaseController<ApiSswpService> {


    @ApiOperationSupport(author = "wutl")
    @ApiAccessNotValidate
    @ApiOperation(value = "人员编号查询随身物品", notes = "人员编号查询随身物品")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "{code: 200,data: {},isSuccess: true,message: '保存成功'}", response = SswpBeanView.class)})
    @RequestMapping(value = "/getSswp", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<String> getSswpByRybh(HttpServletRequest request, String rybh) {
        return service.getWpxxByRybh(rybh);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "随身物品分页数据", notes = "根据参数查询随身物品分页数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = SswpBeanView.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query")})
    @RequestMapping(value = "/getSswpPageData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<String> getSswpPageData(HttpServletRequest request, SswpParam sswpParam) {
        return service.getSswpApiPageData(sswpParam);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "持有人分页数据", notes = "持有人分页数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = SswpBeanView.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType =
                    "query"), @ApiImplicitParam(name = "ryxm", value = "人员姓名", required = false, dataType = "int",
            paramType = "query")})
    @RequestMapping(value = "/getCyrPageData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<String> getCyrPageData(HttpServletRequest request, String ryxm, Integer page, Integer rows) {
        return service.getCyrApiPageData(page, rows, ryxm);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "新增随身物品", notes = "新增随身物品")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/saveSswp", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<String> saveSswp(HttpServletRequest request, SswpBean sswpBean) {
        return service.saveOrUpdateSswp(sswpBean);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改随身物品", notes = "修改随身物品")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/updateSswp", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<String> updateSswp(HttpServletRequest request, SswpBean sswpBean) {
        return service.saveOrUpdateSswp(sswpBean);

    }


    @ApiOperationSupport(author = "wutl")
    @ApiAccessNotValidate
    @ApiOperation(value = "删除随身物品", notes = "删除随身物品")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "物品主键id数组", required = true, dataType = "string",
            paramType = "query")})
    @RequestMapping(value = "/deleteSswp", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<String> deleteSswp(HttpServletRequest request, String id) {
        return service.deleteSswp(id);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiAccessNotValidate
    @ApiOperation(value = "随身物品领回", notes = "随身物品领回")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "物品主键id数组", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "wplhzt", value = "物品状态 01本人领回 02家属带领", required = true, dataType = "string",
            paramType = "query")})
    @RequestMapping(value = "/wptakeBack", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<String> wptakeBack(HttpServletRequest request, String id, String wplhzt) {
        return service.sswplh(id, wplhzt);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiAccessNotValidate
    @ApiOperation(value = "根据人员编号获取物品所有照片", notes = "根据人员编号获取物品所有照片")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "zplx", value = "照片类型（01入所总照片，02明细照片，03出所总照片，传空查所有）", dataType = "string",
            paramType = "query")})
    @RequestMapping(value = "/getArticleByRybh", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<String> getArticleByRybh(HttpServletRequest request, String rybh,String zplx) {
        if(StringUtils.isEmpty(zplx)||"null".equals(zplx)){
            zplx = "";
        }
        return service.getZpxxByRybh(rybh, zplx);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiAccessNotValidate
    @ApiOperation(value = "随身物品柜号选择", notes = "随身物品柜号选择")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code: 200,data: [],isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区Id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "lb", value = "物品类别(手机(sj) 物品(qt) )", required = true,
            dataType =
                    "string",
            paramType = "query")})
    @RequestMapping(value = "/getSswpWpgDic", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<String> getSswpWpgDic(HttpServletRequest request, String baqid, String lb) {
        return service.getWpgByDwdm(baqid, lb);

    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wl")
    @ApiOperation(value = "根据人员编号，获取随身物品合照", notes = "据人员编号，获取随身物品合照")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data：{'222':'xxxxxxx'},isSuccess:true,message:‘获取成功’},data中的属性参照下方Model", response = SswpPzxxBean.class)})
    @RequestMapping(value = "/getSspwhzByRybh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getSspwhzByRybh(HttpServletRequest request, String rybh) {
        return service.getZpxxByRybh(rybh, SYSCONSTANT.SSWP_ZP_RS_ZL);

    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wl")
    @ApiOperation(value = "根据rybh和cabId获取取物记录", notes = "根据rybh和cabId获取取物记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data：{'222':'xxxxxxx'},isSuccess:true,message:‘获取成功’},data中的属性参照下方Model", response = SswpPzxxBean.class)})
    @RequestMapping(value = "/getQwjlApiPageData", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getQwjlApiPageData(HttpServletRequest request, SswpQwjlParam param) {
        return service.getQwjlApiPageData(param);
    }
}
