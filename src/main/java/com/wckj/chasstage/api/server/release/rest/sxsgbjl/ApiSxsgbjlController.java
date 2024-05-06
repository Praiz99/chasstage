package com.wckj.chasstage.api.server.release.rest.sxsgbjl;


import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlBean;
import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlParam;
import com.wckj.chasstage.api.def.sxsgbjl.service.ApiSxsgbjlService;
import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.chasstage.api.def.yjlb.service.ApiYjlbService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.annotation.ApiAccessValidate;
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


@Api(tags = "审讯室关闭记录管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/sxsgbjl/apiService")
public class ApiSxsgbjlController extends RestApiBaseController<ApiSxsgbjlService> {

    @Autowired
    private ApiSxsgbjlService apiService;

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询审讯室关闭记录", notes = "查询审讯室关闭记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增审讯室关闭记录", notes = "新增审讯室关闭记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, SxsgbjlBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改审讯室关闭记录", notes = "修改审讯室关闭记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, SxsgbjlBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除审讯室关闭记录", notes = "删除审讯室关闭记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "审讯室关闭记录列表查询", notes = "审讯室关闭记录列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = SxsgbjlBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, SxsgbjlParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }
    @ApiAccessValidate
    @RequestMapping(value = "/getSyjlPageData", method = RequestMethod.GET)
    @ApiOperation(value = "审讯室使用记录记录列表查询", notes = "审讯室使用记录记录列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = SxsgbjlBean.class)})
    public ApiReturnResult<String> getSyjlPageData(HttpServletRequest request, SxsgbjlParam param) {
        return (ApiReturnResult<String>) apiService.getSyjlPageData(param);
    }

}
