package com.wckj.chasstage.api.server.release.rest.cldj;


import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.chasstage.api.def.cldj.model.ClunbindParam;
import com.wckj.chasstage.api.def.cldj.service.ApiCldjService;
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


@Api(tags = "车辆管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/cldj/apiService")
public class ApiCldjController extends RestApiBaseController<ApiCldjService> {

    @Autowired
    private ApiCldjService apiService;

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询车辆信息", notes = "查询车辆信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增车辆信息", notes = "新增车辆信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, CldjBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改车辆信息", notes = "修改车辆信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, CldjBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除车辆信息", notes = "删除车辆信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }

    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "车辆信息列表查询", notes = "车辆信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = CldjBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, CldjParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @RequestMapping(value = "/getUsableSycl", method = RequestMethod.GET)
    @ApiOperation(value = "可使用送押车辆列表", notes = "可使用送押车辆列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = CldjBean.class)})
    public ApiReturnResult<String> getUsableSycl(HttpServletRequest request, CldjParam param) {
        return (ApiReturnResult<String>) apiService.getUsableSycl(param);
    }
    @RequestMapping(value = "/unbindSycl", method = RequestMethod.POST)
    @ApiOperation(value = "解绑车辆", notes = "解绑车辆")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> unbindSycl(HttpServletRequest request, ClunbindParam param) {
        return (ApiReturnResult<String>) apiService.deleteSycl(param);
    }

    @RequestMapping(value = "/unbindAllSycl", method = RequestMethod.POST)
    @ApiOperation(value = "解除所有绑定人员", notes = "解除所有绑定人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> unbindAllSycl(HttpServletRequest request, String  id) {
        return (ApiReturnResult<String>) apiService.unBindSycl(id);
    }
}
