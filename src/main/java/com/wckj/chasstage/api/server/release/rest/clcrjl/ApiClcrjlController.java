package com.wckj.chasstage.api.server.release.rest.clcrjl;


import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlBean;
import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlParam;
import com.wckj.chasstage.api.def.clcrjl.service.ApiClcrjlService;
import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
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


@Api(tags = "车辆出入记录")
@RestController
@RequestMapping(value = "/api/rest/chasstage/clcrjl/apiService")
public class ApiClcrjlController extends RestApiBaseController<ApiCldjService> {

    @Autowired
    private ApiClcrjlService apiService;

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询车辆出入记录", notes = "查询车辆出入记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
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
    @ApiOperation(value = "车辆出入记录列表查询", notes = "车辆出入记录列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ClcrjlBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, ClcrjlParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

}
