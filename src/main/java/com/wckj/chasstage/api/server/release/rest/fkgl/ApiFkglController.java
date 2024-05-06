package com.wckj.chasstage.api.server.release.rest.fkgl;


import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.fkgl.service.ApiFkglService;
import com.wckj.framework.api.ApiReturnResult;
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


@Api(tags = "访客管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/fkgl/apiService")
public class ApiFkglController extends RestApiBaseController<ApiFkglService> {

    @Autowired
    private ApiFkglService apiService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询访客信息", notes = "查询访客信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增访客信息", notes = "新增访客信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, FkglBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改访客信息", notes = "修改访客信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, FkglBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除访客信息", notes = "删除访客信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "访客信息列表查询", notes = "访客信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = FkglBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, FkglParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    @ApiOperation(value = "访客出区", notes = "访客出区")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> leave(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.leave(id);
    }
}
