package com.wckj.chasstage.api.server.release.rest.njxx;


import com.wckj.chasstage.api.def.njxx.bean.NjxxBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxParam;
import com.wckj.chasstage.api.def.njxx.bean.NjxxjlBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxjlParam;
import com.wckj.chasstage.api.def.njxx.service.ApiNjxxService;
import com.wckj.chasstage.api.def.njxx.service.ApiNjxxjlService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 尿检信息控制器
 * @Package 尿检信息控制器
 * @Description:
 * @date 2020-9-1616:24
 */
@Api(tags = "尿检信息")
@RestController
@RequestMapping("/api/rest/chasstage/njxx/apiNjxxService")
public class ApiNjxxController extends RestApiBaseController<ApiNjxxService> {
    @Autowired
    private ApiNjxxjlService njxxjlService;

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存尿检信息", notes = "保存尿检信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {bizId:'xxxxxxx'},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/saveNjxx", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> saveNjxx(HttpServletRequest request, NjxxBean njxxBean) {
        //this.service
        return service.saveOrUpdate(njxxBean);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存尿检记录", notes = "保存尿检记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/saveNjxxjl", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> saveNjxxjl(HttpServletRequest request, NjxxjlBean njxxjlBean) {
        //this.service
        return njxxjlService.saveOrUpdate(njxxjlBean);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查询尿检信息", notes = "查询尿检信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = NjxxBean.class)})
    @RequestMapping(value = "/getNjxxPage", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getNjxxPage(HttpServletRequest request, NjxxParam njxxParam) {
        //this.service
        return service.getPageData(njxxParam);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查询尿检记录", notes = "查询尿检记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = NjxxjlBean.class)})
    @RequestMapping(value = "/getNjxxjlPage", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getNjxxjlPage(HttpServletRequest request, NjxxjlParam njxxjlParam) {
        //this.service
        return njxxjlService.getPageData(njxxjlParam);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取尿检PDF数据", notes = "获取尿检PDF数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: base64 ,isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = String.class)})
    @RequestMapping(value = "/getNjPdfBase64", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "njid", value = "尿检Id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "type", value = "尿检类型", required = true, dataType = "string"
            , paramType = "query")})
    public ApiReturnResult<String> getNjPdfBase64(HttpServletRequest request, String njid, String type) {

        return (ApiReturnResult<String>) service.getNjPdfBase64(njid, type);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "尿检记录详情", notes = "尿检记录详情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {} ,isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = NjxxjlBean.class)})
    @RequestMapping(value = "/njxxRecordForm", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "尿检记录Id", required = true, dataType = "string", paramType = "query")})
    public ApiReturnResult<?> njxxRecordForm(HttpServletRequest request, String id) {
        //this.service
        return njxxjlService.findNjjlById(id);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "尿检信息详情", notes = "尿检信息详情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {} ,isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = NjxxjlBean.class)})
    @RequestMapping(value = "/njxxForm", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "尿检Id", required = true, dataType = "string", paramType = "query")})
    public ApiReturnResult<?> njxxForm(HttpServletRequest request, String id) {
        //this.service
        return service.findNjxxId(id);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "删除尿检信息", notes = "删除尿检信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {} ,isSuccess: true,message: '查询成功'},rows中的属性参照下方Model")})
    @RequestMapping(value = "/delNjxx", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "尿检Id", required = true, dataType = "string", paramType = "query")})
    public ApiReturnResult<?> delNjxx(HttpServletRequest request, String id) {
        //this.service
        return service.deletes(id);
    }
}
