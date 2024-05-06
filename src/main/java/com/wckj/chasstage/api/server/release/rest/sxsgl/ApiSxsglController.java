package com.wckj.chasstage.api.server.release.rest.sxsgl;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.sxsgl.model.SxsFpParam;
import com.wckj.chasstage.api.def.sxsgl.model.SxsSxtBean;
import com.wckj.chasstage.api.def.sxsgl.service.ApiSxsglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "审讯室管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/sxsgl/apiService")
public class ApiSxsglController extends RestApiBaseController<ApiSxsglService> {

    @Autowired
    private ApiSxsglService apiService;



    @RequestMapping(value = "/getSxsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询审讯室使用情况", notes = "查询审讯室使用情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baqid", value = "办案区Id", required = false, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页数", required = true, dataType = "int",paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = SxsBean.class)})
    public ApiReturnResult<String> getSxsInfo(HttpServletRequest request, String  baqid,int page,int rows) {
        return (ApiReturnResult<String>) apiService.getSxsInfo(baqid, "", page, rows);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/open", method = RequestMethod.GET)
    @ApiOperation(value = "打开审讯室", notes = "打开审讯室")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> open(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.open(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/close",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "关闭审讯室", notes = "关闭审讯室")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> close(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.close(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getSxsSxtInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取审讯室摄像头信息", notes = "获取审讯室摄像头信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = SxsSxtBean.class)})
    public ApiReturnResult<String> getSxsSxtInfo(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.getSxsSxtInfo(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getDhsfp", method = RequestMethod.GET)
    @ApiOperation(value = "审讯室调整", notes = "审讯室调整")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 可正常分配,其它为非正常分配,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},message为分配结果提示信息",response = String.class)})
    public ApiReturnResult<String> dhsfp(HttpServletRequest request, SxsFpParam param) {
        return (ApiReturnResult<String>) apiService.sxsfp(param,request);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/openPfs", method = RequestMethod.GET)
    @ApiOperation(value = "打开排风扇", notes = "打开排风扇")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> openPfs(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.openPfs(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/closePfs", method = RequestMethod.GET)
    @ApiOperation(value = "关闭排风扇", notes = "关闭排风扇")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> closePfs(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.closePfs(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getAreaByBaqid", method = RequestMethod.GET)
    @ApiOperation(value = "获取区域(审讯室状态)", notes = "获取区域")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sxsZt", value = "审讯室状态", required = true, dataType = "int", paramType = "query")
    })
    public ApiReturnResult<?> getAreaByBaqid(HttpServletRequest request,int page,int rows,String baqid,String sxsZt) {
        return apiService.getAreaByBaqid(page,rows,baqid,sxsZt);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/findSxsqkByQyid", method = RequestMethod.GET)
    @ApiOperation(value = "根据区域原始id查询审讯室使用情况", notes = "根据区域id查询审讯室使用情况")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model")})
    public ApiReturnResult<?> findSxsqkByQyid(HttpServletRequest request, String qyid) {
        return apiService.findSxsqkByQyid(qyid);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/obtainPersonnelInformationBySxsId", method = RequestMethod.GET)
    @ApiOperation(value = "根据区域标识获取人员信息和民警信息", notes = "根据区域标识获取人员信息和民警信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "审讯室id", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model")})
    public ApiReturnResult<?> obtainPersonnelInformationBySxsId(HttpServletRequest request, String qyid) {
        return apiService.obtainPersonnelInformationBySxsId(qyid);
    }
}
