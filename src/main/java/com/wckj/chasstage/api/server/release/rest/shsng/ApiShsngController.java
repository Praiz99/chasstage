package com.wckj.chasstage.api.server.release.rest.shsng;

import com.wckj.chasstage.api.def.shsng.model.ShsngBean;
import com.wckj.chasstage.api.def.shsng.model.ShsngParam;
import com.wckj.chasstage.api.def.shsng.service.ApiShsngService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:zengrk
 */
@Api(tags = "手环收纳柜")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baq/apiShsngService")
public class ApiShsngController extends RestApiBaseController<ApiShsngService> {
    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "查询手环收纳柜", notes = "查询手环收纳柜")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = ShsngBean.class)})
    public ApiReturnResult<String> getShsngPageData(HttpServletRequest request, ShsngParam shsngParam) {
        return service.getShsngPageData(shsngParam);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getZgNum", method = RequestMethod.GET)
    @ApiOperation(value = "查询收纳柜存量", notes = "查询收纳柜存量")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = Integer.class)})
    public ApiReturnResult<String> getZgNum(HttpServletRequest request) {
        return service.getSngNum();
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/yjcl", method = RequestMethod.GET)
    @ApiOperation(value = "收纳柜再柜全部取出", notes = "收纳柜再柜全部取出")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> yjcl(HttpServletRequest request) {
        return service.yjcl();
    }

    @RequestMapping(value = "/sdgh", method = RequestMethod.GET)
    @ApiOperation(value = "手环手动归还", notes = "手环手动归还")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> sdgh(HttpServletRequest request, String id) {
        return service.sdgh(id);
    }
}
