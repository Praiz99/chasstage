package com.wckj.chasstage.api.server.release.rest.yjlb;


import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.chasstage.api.def.yjlb.service.ApiYjlbService;
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


@Api(tags = "预警类别管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/yjlb/apiService")
public class ApiYjlbController extends RestApiBaseController<ApiYjlbService> {

    @Autowired
    private ApiYjlbService apiService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询预警类别", notes = "查询预警类别")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增预警类别", notes = "新增预警类别")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, YjlbBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改预警类别", notes = "修改办预警类别")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, YjlbBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除预警类别", notes = "删除预警类别")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }



    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "预警类别列表查询", notes = "预警类别列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = YjlbBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, YjlbParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

}
