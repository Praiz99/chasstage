package com.wckj.chasstage.api.server.release.rest.gwzz;


import com.wckj.chasstage.api.def.gwzz.bean.GwzzBean;
import com.wckj.chasstage.api.def.gwzz.bean.GwzzParam;
import com.wckj.chasstage.api.def.gwzz.service.ApiBaqGwlcService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 岗位职责关联单位
 * @Package 岗位职责关联单位
 * @Description: 岗位职责关联单位
 * @date 2020-9-517:16
 */
@Api(tags = "岗位职责")
@RestController
@RequestMapping("/api/rest/chasstage/gwzz/apiGwzzService")
public class ApiGwzzController extends RestApiBaseController<ApiBaqGwlcService> {

    @Autowired
    private ApiBaqGwlcService gwlcService;

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "新增岗位职责", notes = "新增岗位职责")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/saveGwzz", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> saveGwzz(HttpServletRequest request, GwzzBean gwzzBean) {
        return gwlcService.saveGwlc(gwzzBean);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改岗位职责", notes = "修改岗位职责")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/updateGwzz", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> updateGwzz(HttpServletRequest request,GwzzBean gwzzBean) {
        return gwlcService.saveGwlc(gwzzBean);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "删除办岗位职责", notes = "删除岗位职责")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/deleteGwzz", method = RequestMethod.GET)

    @ResponseBody
    public ApiReturnResult<?> deleteGwzz(HttpServletRequest request, String id) {
        return gwlcService.deleteGwlc(id);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查询岗位职责", notes = "查询岗位职责")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model",response = GwzzBean.class)})
    @RequestMapping(value = "/getGwzzPageData", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getGwzzPageData(HttpServletRequest request, GwzzParam gwzzParam) {
        return gwlcService.getGwzzPageData(gwzzParam);
    }
}
