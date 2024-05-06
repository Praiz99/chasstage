package com.wckj.chasstage.api.server.release.rest.xgpz;


import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.chasstage.api.def.xgpz.service.ApiXgpzService;
import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.chasstage.api.def.yjxx.service.ApiYjxxService;
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


@Api(tags = "巡更配置管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/xgpz/apiService")
public class ApiXgpzController extends RestApiBaseController<ApiXgpzService> {

    @Autowired
    private ApiXgpzService apiService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询巡更配置", notes = "查询巡更配置")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增巡更配置", notes = "新增巡更配置")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, XgpzBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改巡更配置", notes = "修改办巡更配置")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, XgpzBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除巡更配置", notes = "删除巡更配置")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }



    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "巡更配置列表查询", notes = "巡更配置列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = XgpzBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, XgpzParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

}
