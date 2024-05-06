package com.wckj.chasstage.api.server.release.rest.jhrw;


import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.fkgl.service.ApiFkglService;
import com.wckj.chasstage.api.def.jhrw.model.*;
import com.wckj.chasstage.api.def.jhrw.service.ApiJhrwService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.jhrw.entity.ChasYwJhrw;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.jdone.modules.sys.util.DicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Api(tags = "戒护任务管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/jhrwgl/apiService")
public class ApiJhrwglController extends RestApiBaseController<ApiJhrwService> {

    @Autowired
    private ApiJhrwService apiService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询戒护任务信息", notes = "查询戒护任务信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @RequestMapping(value = "/saveJhry", method = RequestMethod.POST)
    @ApiOperation(value = "分配人员", notes = "分配人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> saveJhry(HttpServletRequest request, JhrwFpryParam bean) {
        return (ApiReturnResult<String>) apiService.saveJhry(bean);
    }

    @RequestMapping(value = "/accpectJhrw", method = RequestMethod.POST)
    @ApiOperation(value = "领取任务", notes = "领取任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> accpectJhrw(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.accpectJhrw(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除戒护任务信息", notes = "删除戒护任务信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "戒护任务列表查询", notes = "戒护任务列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = JhrwglBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, JhrwParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @RequestMapping(value = "/getUserData", method = RequestMethod.GET)
    @ApiOperation(value = "获取监护人员列表", notes = "获取监护人员列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性姓名,身份证号的字符串列表",response = ApiReturnResult.class)})
    public ApiReturnResult<String> getUserData(HttpServletRequest request) {
        return (ApiReturnResult<String>) apiService.getUserData();
    }

    @RequestMapping(value = "/getJhrwjlByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "查询办案区人员列表流转记录", notes = "查询办案区人员列表流转记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性姓名,身份证号的字符串列表",response = JhrwjlInfoBean.class)})
    public ApiReturnResult<String> getJhrwjlByRybh(HttpServletRequest request,String rybh) {
        return (ApiReturnResult<String>) apiService.getJhrwjlByRybh(rybh);
    }
    @RequestMapping(value = "/getjlData", method = RequestMethod.GET)
    @ApiOperation(value = "查看节点", notes = "查看节点")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性姓名,身份证号的字符串列表",response = JhrwjlBean.class)})
    public ApiReturnResult<String> getjlData(HttpServletRequest request, JhrwjlParam param) {
        return (ApiReturnResult<String>) apiService.getjlData(param);
    }

    @RequestMapping(value = "/getJhry", method = RequestMethod.GET)
    @ApiOperation(value = "获取戒护任务分配的监护人员列表", notes = "获取戒护任务分配的监护人员列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性姓名,身份证号的字符串列表",response = JhryBean.class)})
    public ApiReturnResult<String> getJhry(HttpServletRequest request, String jhrwId) {
        return (ApiReturnResult<String>) apiService.getJhry(jhrwId);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getDpData", method = RequestMethod.GET)
    @ApiOperation(value = "戒护大屏获取戒护任务", notes = "戒护大屏获取戒护任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性姓名,身份证号的字符串列表",response = JhrwglBean.class)})
    public ApiReturnResult<String> getDpData(HttpServletRequest request, JhrwParam p) {
        return (ApiReturnResult<String>) apiService.getDpData(p);
    }
}
