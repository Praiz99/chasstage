package com.wckj.chasstage.api.server.release.rest.qqdh;


import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.def.qqdh.model.QqdhParam;
import com.wckj.chasstage.api.def.qqdh.service.ApiQqdhService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "亲情电话管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/qqdh/apiQqdhService")
public class ApiQqdhController extends RestApiBaseController<ApiQqdhService> {

    @Autowired
    private ApiQqdhService apiService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增亲情电话", notes = "新增亲情电话", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, QqdhBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改亲情电话", notes = "修改亲情电话", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, QqdhBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除亲情电话", notes = "删除亲情电话", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键id数组", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "查询亲情电话", notes = "查询亲情电话", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = QqdhBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, QqdhParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }



    @ApiAccessNotValidate
    @RequestMapping(value = "/open", method = RequestMethod.GET)
    @ApiOperation(value = "开启电话", notes = "开启电话", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ryid", value = "人员id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> open(HttpServletRequest request, String ryid, String id) {
        return (ApiReturnResult<String>) apiService.open(ryid, id);
    }

    /*@ApiAccessNotValidate
    @RequestMapping(value = "/qqdhsq", method = RequestMethod.GET)
    @ApiOperation(value = "电话申请", notes = "电话申请", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> qqdhsq(HttpServletRequest request,QqdhBean bean) {
        NodeProcessCmdObj2 cmdObj2=new NodeProcessCmdObj2();
        return (ApiReturnResult<String>) apiService.qqdhsq(bean,cmdObj2);
    }*/

    @ApiAccessNotValidate
    @RequestMapping(value = "/close", method = RequestMethod.GET)
    @ApiOperation(value = "挂断电话", notes = "挂断电话", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ryid", value = "人员id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> close(HttpServletRequest request, String ryid, String id) {
        return (ApiReturnResult<String>) apiService.close(ryid, id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/qqdhtqly", method = RequestMethod.GET)
    @ApiOperation(value = "听取录音文件", notes = "听取录音文件", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{data:'录音文件url'}]},data中的属性参照下方Model",
                    response =
                    ApiReturnResult.class)})
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> qqdhtqly(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.qqdhtqly(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/valideApprove", method = RequestMethod.GET)
    @ApiOperation(value = "校验审批状态", notes = "校验审批状态", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ryid", value = "人员id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> valideApprove(HttpServletRequest request, String ryid) {
        return (ApiReturnResult<String>) apiService.valideApprove(ryid);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "启动审批流程", notes = "启动审批流程")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> startProcess(NodeProcessCmdObj2 nodeProcessCmdObj2) {
        return apiService.startProcess(nodeProcessCmdObj2);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "执行审批流程", notes = "执行审批流程")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/executeProcess", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj) {
        return apiService.executeProcess(cmdObj);
    }
}
