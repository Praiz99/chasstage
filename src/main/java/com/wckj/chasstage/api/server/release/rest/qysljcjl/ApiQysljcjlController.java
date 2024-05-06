package com.wckj.chasstage.api.server.release.rest.qysljcjl;

/**
 * @author kcm
 * @Title: 办案区区域管理功能
 * @Package
 * @Description:
 * @date 2020-9-39:57
 */


import com.wckj.chasstage.api.def.qygl.model.QyglBean;
import com.wckj.chasstage.api.def.qygl.service.ApiQyglService;
import com.wckj.chasstage.api.def.qysljcjl.service.ApiQysljcjlService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "审讯室流程管控终端")
@RestController
@RequestMapping(value = "/api/rest/chasstage/qysljcjl/apiQysljcjlService")
public class ApiQysljcjlController extends RestApiBaseController<ApiQysljcjlService> {

    @ApiAccessNotValidate
    @RequestMapping(value = "/findQyjcjl", method = RequestMethod.GET)
    @ApiOperation(value = "获取审讯室进出记录信息", notes = "获取审讯室进出记录信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:500,message:错误信息},data中的属性参照下方Model")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qyid", value = "区域id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "qylx", value = "区域类型", required = true, dataType = "int", paramType = "query")
    })
    public ApiReturnResult<?> findQyjcjl(HttpServletRequest request) {
        return service.findQyjcjl(request);
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/saveQysljcjl", method = RequestMethod.POST)
    @ApiOperation(value = "保存审讯室进出记录信息", notes = "获取区域")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:500,message:错误信息},data中的属性参照下方Model")})
    @ApiImplicitParams({
    })
    public ApiReturnResult<?> saveQysljcjl(HttpServletRequest request) {
        return service.saveQysljcjl(request);
    }
}
