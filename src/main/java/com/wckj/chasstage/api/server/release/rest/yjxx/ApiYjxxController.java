package com.wckj.chasstage.api.server.release.rest.yjxx;


import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.chasstage.api.def.yjlb.service.ApiYjlbService;
import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.chasstage.api.def.yjxx.service.ApiYjxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "预警信息管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/yjxx/apiService")
public class ApiYjxxController extends RestApiBaseController<ApiYjxxService> {

    @Autowired
    private ApiYjxxService apiService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询预警信息", notes = "查询预警信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增预警信息", notes = "新增预警信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, YjxxBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改预警信息", notes = "修改办预警信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, YjxxBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除预警信息", notes = "删除预警信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }



    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "预警信息列表查询", notes = "预警信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = YjxxBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, YjxxParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getAlarmData", method = RequestMethod.GET)
    @ApiOperation(value = "查询大屏预警统计信息", notes = "查询大屏预警统计信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    //@ApiImplicitParams({@ApiImplicitParam(name = "variables", value = "orgCode:机构编号", required = true, dataType = "string",
     //       paramType = "query")})
    public ApiReturnResult<String> getAlarmData(HttpServletRequest request,String variables) {
        return (ApiReturnResult<String>)apiService.getBigScreenData(variables);
    }

    @CrossOrigin
    @ApiAccessNotValidate
    @RequestMapping(value = "/getXsAlarmData", method = RequestMethod.GET)
    @ApiOperation(value = "查询大屏预警统计信息", notes = "查询大屏预警统计信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> getXsAlamData(HttpServletRequest request,String variables) {
        return (ApiReturnResult<String>)apiService.getXsBigScreenData(variables);
    }


    @ApiAccessNotValidate
    @ApiOperation(value = "查询大屏预警统计信息", notes = "查询大屏预警统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baqid", value = "baqid", required = true, dataType = "string",paramType = "query")
    })
    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public void alarm(HttpServletRequest request, String qyid, String baqid){
        try {
            apiService.saveAlarm(qyid, request, baqid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
