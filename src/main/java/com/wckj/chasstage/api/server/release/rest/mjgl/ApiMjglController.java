package com.wckj.chasstage.api.server.release.rest.mjgl;


import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.fkgl.service.ApiFkglService;
import com.wckj.chasstage.api.def.mjgl.model.MjglBean;
import com.wckj.chasstage.api.def.mjgl.model.MjglParam;
import com.wckj.chasstage.api.def.mjgl.service.ApiMjglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Api(tags = "民警入区管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/mjgl/apiService")
public class ApiMjglController extends RestApiBaseController<ApiMjglService> {

    @Autowired
    private ApiMjglService apiService;

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询民警入区信息", notes = "查询民警入区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增民警入区信息", notes = "新增民警入区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, MjglBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改民警入区信息", notes = "修改民警入区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, MjglBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除民警入区信息", notes = "删除民警入区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }



    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "民警入区信息列表查询", notes = "民警入区信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = MjglBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, MjglParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    @ApiOperation(value = "民警出区", notes = "民警出区")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> leave(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.leave(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getMjxxByWdbhl", method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "根据腕带获取民警入区信息", notes = "根据腕带获取民警入区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "wdbhl", value = "腕带编号", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> getMjxxByWdbhl(HttpServletRequest request, String wdbhl) {
        return apiService.getMjxxByWdbhl(wdbhl);
    }
}
