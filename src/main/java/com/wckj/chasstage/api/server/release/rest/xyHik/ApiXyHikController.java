package com.wckj.chasstage.api.server.release.rest.xyHik;

import com.wckj.chasstage.api.def.qtdj.model.RyxxParam;
import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.xyHik.service.ApiXyHikService;
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


@Api(tags = "安吉孝源海康数据接口")
@RestController
@RequestMapping(value = "/api/rest/chasstage/xyHik/apiService")
public class ApiXyHikController extends RestApiBaseController<ApiXyHikService> {

    @Autowired
    private ApiXyHikService apiService;

    @RequestMapping(value = "/getSxsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询审讯室使用情况", notes = "查询审讯室使用情况")
    @ApiAccessNotValidate
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dwdm", value = "单位代码", required = true, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页数", required = true, dataType = "int",paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = SxsBean.class)})
    public ApiReturnResult<String> getSxsInfo(HttpServletRequest request, String  dwdm,int page,int rows) {
        return (ApiReturnResult<String>) apiService.getSxsInfo(dwdm, "", page, rows);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取办案区人员列表", notes = "获取办案区人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dwdm", value = "单位代码", required = true, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "rssj1", value = "入区时间1", required = false, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "rssj2", value = "入区时间2", required = false, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页数", required = true, dataType = "int",paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = String.class)})
    @RequestMapping(value = "/getRyxxDataGrid", method = {RequestMethod.POST})
    @ResponseBody
    public ApiReturnResult<?> getRyxxDataGrid(String dwdm, String rssj1, String rssj2, int page, int rows) {
        return apiService.getBaqryxxDataGrid(dwdm, rssj1, rssj2, page, rows);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "getAlarmData")
    @ApiOperation(value = "查询大屏预警统计信息", notes = "查询大屏预警统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dwdm", value = "单位代码", required = true, dataType = "string",paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = String.class)})
    @RequestMapping(value = "/getAlarmData", method = {RequestMethod.POST})
    @ResponseBody
    public ApiReturnResult<?> getAlarmData(String dwdm) {
        return apiService.getAlarmData(dwdm);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "getRyxxDataTotal")
    @ApiOperation(value = "查询大屏在区人员统计信息", notes = "查询大屏在区人员统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dwdm", value = "单位代码", required = true, dataType = "string",paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = String.class)})
    @RequestMapping(value = "/getRyxxDataTotal", method = {RequestMethod.POST})
    @ResponseBody
    public ApiReturnResult<?> getRyxxDataTotal(String dwdm) {
        return apiService.getRyxxDataTotal(dwdm);
    }


}
