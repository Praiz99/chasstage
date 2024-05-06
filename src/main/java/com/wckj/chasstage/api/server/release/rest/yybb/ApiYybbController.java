package com.wckj.chasstage.api.server.release.rest.yybb;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.yybb.model.YybbParam;
import com.wckj.chasstage.api.def.yybb.service.ApiYybbService;
import com.wckj.chasstage.api.def.yygl.service.ApiYyglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 语音播报控制
 * @Package
 * @Description:
 * @date 2020-12-2311:46
 */
@Api(tags = "语音播报管理")
@RestController
@RequestMapping("/api/rest/chasstage/yybb/apiYybbService")
public class ApiYybbController extends RestApiBaseController<ApiYybbService> {


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存语音播报", notes = "保存语音播报")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, YybbParam yybbParam) {
        return (ApiReturnResult<String>) this.service.save(yybbParam);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改语音播报", notes = "修改语音播报")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, YybbParam yybbParam) {
        return (ApiReturnResult<String>) this.service.update(yybbParam);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除语音播报", notes = "删除语音播报")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "int", paramType = "query")
    })
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) this.service.delete(id);
    }


    @RequestMapping(value = "/findList", method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "查询语音播报", notes = "查询语音播报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页数", required = true, dataType = "int", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> findList(HttpServletRequest request, int page,int rows ,YybbParam yybbParam) {
        return (ApiReturnResult<String>) this.service.findList(page,rows,yybbParam,"lrsj desc");
    }

}
