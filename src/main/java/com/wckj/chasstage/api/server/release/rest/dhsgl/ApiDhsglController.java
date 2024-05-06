package com.wckj.chasstage.api.server.release.rest.dhsgl;

import com.wckj.chasstage.api.def.dhsgl.model.*;
import com.wckj.chasstage.api.def.dhsgl.service.ApiDhsglService;
import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.def.qqdh.model.QqdhParam;
import com.wckj.chasstage.api.def.qqdh.service.ApiQqdhService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Api(tags = "等候室管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/dhsgl/apiService")
public class ApiDhsglController extends RestApiBaseController<ApiDhsglService> {

    @Autowired
    private ApiDhsglService apiService;


    @RequestMapping(value = "/getDhsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询等候室分配情况", notes = "查询等候室分配情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model",response = DhsBean.class)})
    public ApiReturnResult<?> getDhsInfo(HttpServletRequest request, String baqid) {
        return apiService.getDhsInfo(baqid);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getDhsRyInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询等候室人员信息", notes = "查询等候室人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model",response = DhsRyBean.class)})
    public ApiReturnResult<String> getDhsRyInfo(HttpServletRequest request, DhsParam param) {
        return (ApiReturnResult<String>) apiService.getDhsRyInfo(param);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getYfpInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询等候室预分配信息", notes = "查询等候室预分配信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data:{}},data中的属性参照下方Model",response = DhsYfpBean.class)})
    public ApiReturnResult<String> getYfpInfo(HttpServletRequest request, DhsFpParam param) {
        return (ApiReturnResult<String>) apiService.getYfpInfo(param);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/dhsfp", method = RequestMethod.GET)
    @ApiOperation(value = "等候室调整", notes = "等候室调整")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data:{}},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> dhsfp(HttpServletRequest request, DhsFpParam param) {
        return (ApiReturnResult<String>) apiService.dhsfp(param);
    }

}
