package com.wckj.chasstage.api.server.release.rest.rygj;

import com.wckj.chasstage.api.def.baq.model.BaqBean;
import com.wckj.chasstage.api.def.baq.model.BaqParam;
import com.wckj.chasstage.api.def.baq.service.ApiBaqService;
import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.rygj.model.RygjBean;
import com.wckj.chasstage.api.def.rygj.model.RygjParam;
import com.wckj.chasstage.api.def.rygj.model.RygjspBean;
import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "人员轨迹管理", produces = "application/json", protocols = "http")
@RestController
@RequestMapping(value = "/api/rest/chasstage/rygj/apiRygjService")
public class ApiRygjController extends RestApiBaseController<ApiRygjService> {

    @Autowired
    private ApiRygjService apiRygjService;
    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询轨迹信息", notes = "查询轨迹信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiRygjService.get(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增嫌疑人轨迹信息", notes = "新增嫌疑人轨迹信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, RygjBean bean) {
        return (ApiReturnResult<String>) apiRygjService.save(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改嫌疑人轨迹信息", notes = "修改嫌疑人轨迹信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, RygjBean bean) {
        return (ApiReturnResult<String>) apiRygjService.update(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除轨迹信息", notes = "删除轨迹信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiRygjService.deletes(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getRygjPageData", method = RequestMethod.GET)
    @ApiOperation(value = "人员轨迹列表查询", notes = "人员轨迹列表查询", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RygjBean.class)})
    public ApiReturnResult<String> getBaqPageData(HttpServletRequest request, RygjParam rygjParam) {
        return (ApiReturnResult<String>) apiRygjService.getBaqPageData(rygjParam);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getRygjUrl", method = RequestMethod.GET)
    @ApiOperation(value = "海康人脸定位人员轨迹url查询", notes = "海康人脸定位人员轨迹url查询", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RygjBean.class)})
    public ApiReturnResult<String> getRygjUrl(HttpServletRequest request, RygjParam rygjParam) {
        return (ApiReturnResult<String>) apiRygjService.getRygjUrl(rygjParam);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getRtspValByQyid", method = RequestMethod.GET)
    @ApiOperation(value = "人员轨迹视频接口", notes = "人员轨迹视频接口", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RygjspBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "qyid", value = "区域id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> getRtspValByQyid(HttpServletRequest request, String qyid) {
        return (ApiReturnResult<String>) apiRygjService.getRtspValByQyid(qyid);

    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/getRygjXmlByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "人员轨迹视频XML", notes = "人员轨迹视频XML", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "kssj", value = "开始时间", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "jssj", value = "结束时间", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "areaNo", value = "区域id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> getRygjXmlByRybh(HttpServletRequest request, String rybh,String kssj,String jssj,String areaNo) {
        return (ApiReturnResult<?>) apiRygjService.getRygjXmlByRybh(rybh,kssj,jssj,areaNo);
    }

}
