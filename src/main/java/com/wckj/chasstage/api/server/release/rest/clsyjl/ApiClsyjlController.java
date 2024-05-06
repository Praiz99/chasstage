package com.wckj.chasstage.api.server.release.rest.clsyjl;


import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.chasstage.api.def.cldj.service.ApiCldjService;
import com.wckj.chasstage.api.def.clsyjl.model.AddClsyjlParam;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlBean;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlParam;
import com.wckj.chasstage.api.def.clsyjl.service.ApiClsyjlService;
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


@Api(tags = "车辆使用记录")
@RestController
@RequestMapping(value = "/api/rest/chasstage/clsyjl/apiService")
public class ApiClsyjlController extends RestApiBaseController<ApiClsyjlService> {

    @Autowired
    private ApiClsyjlService apiService;

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询车辆使用记录", notes = "查询车辆使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增车辆使用记录", notes = "新增车辆使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, AddClsyjlParam bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改车辆使用记录", notes = "修改车辆使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, ClsyjlBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除车辆使用记录", notes = "删除车辆使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "车辆使用记录列表查询", notes = "车辆使用记录列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ClsyjlBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, ClsyjlParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getBindCarByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "获取人员已绑定车辆", notes = "获取人员已绑定车辆")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ClsyjlBean.class)})
    public ApiReturnResult<String> getBindCarByRybh(HttpServletRequest request, ClsyjlParam param) {
        return (ApiReturnResult<String>) apiService.getBindCarByRybh(param);
    }

}
