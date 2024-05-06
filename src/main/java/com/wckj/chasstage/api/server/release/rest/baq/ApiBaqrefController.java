package com.wckj.chasstage.api.server.release.rest.baq;


import com.wckj.chasstage.api.def.baq.model.BaqrefBean;
import com.wckj.chasstage.api.def.baq.model.BaqrefParam;
import com.wckj.chasstage.api.def.baq.service.ApiBaqrefService;
import com.wckj.chasstage.api.def.qtdj.model.OrgBean;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 办案区引用关联单位
 * @Package 办案区引用关联单位
 * @Description: 办案区引用关联单位
 * @date 2020-9-517:16
 */
@Api(tags = "办案区关联单位管理")
@RestController
@RequestMapping("/api/rest/chasstage/baqref/apiBaqrefService")
public class ApiBaqrefController extends RestApiBaseController<ApiBaqrefService> {

    @Autowired
    private ApiBaqrefService apiBaqrefService;

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "新增办案区引用", notes = "新增办案区引用")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/saveBaqref", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "orgs", value = "系统机构代码集合，逗号隔开", required = true, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "baqid", value = "办案区Id", required = true, dataType = "string",paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> saveBaqref(HttpServletRequest request, String orgs,String baqid) {
        SessionUser sessionUser = WebContext.getSessionUser();
        System.out.println(sessionUser);
        return apiBaqrefService.saveBatchRef(baqid,orgs);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改办案区引用", notes = "修改办案区引用")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/updateBaqref", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> updateBaqref(HttpServletRequest request, BaqrefBean baqrefBean) {
        if(StringUtil.isEmpty(baqrefBean.getId())){
            return ResultUtil.ReturnError("id不能为空");
        }
        return apiBaqrefService.saveBaqRef(baqrefBean);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "删除办案区引用", notes = "删除办案区引用")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/deleteBaqref", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "办案区引用id", required = true, dataType = "string",paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> deleteBaqref(HttpServletRequest request, String id) {
        return apiBaqrefService.deleteBaqRef(id);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查询办案区引用", notes = "查询办案区引用")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model",response = BaqrefBean.class)})
    @RequestMapping(value = "/getBaqrefPageData", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getBaqrefPageData(HttpServletRequest request, BaqrefParam baqrefParam) {
        return apiBaqrefService.getBaqrefPageData(baqrefParam);
    }


    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取引用单位集合", notes = "获取引用单位集合")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model",response = OrgBean.class)})
    @RequestMapping(value = "/getOrgPageData", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "org", value = "系统机构代码",  dataType = "string"),
            @ApiImplicitParam(name = "baqid", value = "办案区Id", required = true, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "页数", required = true, dataType = "int",paramType = "query")})
    public ApiReturnResult<?> getOrgPageData(HttpServletRequest request,String baqid,String org,int page,int rows) {
        return apiBaqrefService.getOrgPageData(baqid, org, page, rows);
    }

}
