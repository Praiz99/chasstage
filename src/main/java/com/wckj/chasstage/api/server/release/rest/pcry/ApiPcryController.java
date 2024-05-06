package com.wckj.chasstage.api.server.release.rest.pcry;

import com.wckj.chasstage.api.def.pcry.model.PcryBean;
import com.wckj.chasstage.api.def.pcry.model.PcryParam;
import com.wckj.chasstage.api.def.pcry.service.ApiPcryService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "盘查人员管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/pcry/apiPcryService")
public class ApiPcryController extends RestApiBaseController<ApiPcryService> {



    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增盘查人员", notes = "新增盘查人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, PcryBean bean) {
        return (ApiReturnResult<String>) service.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改盘查人员", notes = "修改盘查人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, PcryBean bean) {
        return (ApiReturnResult<String>) service.saveOrUpdate(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除盘查人员", notes = "删除盘查人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键id数组", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) service.deletes(id);
    }


    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "查询盘查人员", notes = "查询盘查人员")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = PcryBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, PcryParam param) {
        return (ApiReturnResult<String>) service.getPageData(param);
    }

}
