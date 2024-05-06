package com.wckj.chasstage.api.server.release.rest.xgjl;


import com.wckj.chasstage.api.def.xgjl.model.SdXgBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlParam;
import com.wckj.chasstage.api.def.xgjl.service.ApiXgjlService;
import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.chasstage.api.def.xgpz.service.ApiXgpzService;
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


@Api(tags = "巡更记录管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/xgjl/apiService")
public class ApiXgjlController extends RestApiBaseController<ApiXgjlService> {

    @Autowired
    private ApiXgjlService apiService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询巡更记录", notes = "查询巡更记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增巡更记录", notes = "新增巡更记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, XgjlBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改巡更记录", notes = "修改办巡更记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, XgjlBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除巡更记录", notes = "删除巡更记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }



    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "巡更记录列表查询", notes = "巡更记录列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = XgjlBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, XgjlParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @RequestMapping(value = "/sdxg", method = RequestMethod.POST)
    @ApiOperation(value = "手动巡更", notes = "手动巡更")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> sdxg(HttpServletRequest request, SdXgBean bean) {
        return (ApiReturnResult<String>) apiService.sddk(bean);
    }
}
