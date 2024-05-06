package com.wckj.chasstage.api.server.release.rest.sbgl;

/**
 * @author kcm
 * @Title: 办案区设备管理功能
 * @Package
 * @Description:
 * @date 2020-9-39:57
 */


import com.wckj.chasstage.api.def.sbgl.model.SbglBean;
import com.wckj.chasstage.api.def.sbgl.model.SbglParam;
import com.wckj.chasstage.api.def.sbgl.service.ApiSbglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lcm
 * @Title: 出所管理
 * @Package
 * @Description:
 * @date 2020-9-516:29
 */
@Api(tags = "办案区设备管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baq/apiSbglService")
public class ApiSbglController extends RestApiBaseController<ApiSbglService> {

    /**
     * @param request
     * @param bean
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 保存设备//描述
     * @author lcm //作者
     * @date 2020/9/8 19:36 //时间
     */
    @RequestMapping(value = "/saveSbgl", method = RequestMethod.POST)
    @ApiOperation(value = "保存设备", notes = "保存设备")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> saveSbgl(HttpServletRequest request, SbglBean bean) {
        ApiReturnResult<String> apiReturnResult = service.saveSbgl(bean);
        return apiReturnResult;
    }

    /**
     * @param request
     * @param bean
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 更新设备//描述
     * @author lcm //作者
     * @date 2020/9/8 19:35 //时间
     */
    @RequestMapping(value = "/updateSbgl", method = RequestMethod.POST)
    @ApiOperation(value = "更新设备", notes = "更新设备")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> updateSbgl(HttpServletRequest request, SbglBean bean) {

        ApiReturnResult<String> apiReturnResult = service.updateSbgl(bean);
        return apiReturnResult;
    }

    /**
     * @param request
     * @param id
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 删除设备//描述
     * @author lcm //作者
     * @date 2020/9/8 19:35 //时间
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/deleteSbgl", method = RequestMethod.GET)
    @ApiOperation(value = "删除设备", notes = "删除设备")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response =
                    ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键id数组", required = true, dataType = "string",
            paramType =
            "query")})
    public ApiReturnResult<String> deleteSbgl(HttpServletRequest request, String id) {

        ApiReturnResult<String> apiReturnResult = service.deleteSbgl(id);
        return apiReturnResult;
    }
    @RequestMapping(value = "/getSbByLxAndQy", method = RequestMethod.GET)
    @ApiOperation(value = "根据设备类型和区域", notes = "根据设备类型和区域")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response =
                    SbglBean.class)})
    public ApiReturnResult<String> getSbByLxAndQy(HttpServletRequest request, SbglParam sbglParam) {

        ApiReturnResult<String> apiReturnResult = service.getSbByLxAndQy(sbglParam);
        return apiReturnResult;
    }


    /**
     * @param request
     * @param sbglParam
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 查询设备数据//描述
     * @author lcm //作者
     * @date 2020/9/8 19:34 //时间
     */
    @RequestMapping(value = "/getSbglPageData", method = RequestMethod.GET)
    @ApiOperation(value = "查询设备数据", notes = "查询设备数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response =
                    SbglBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query")})
    public ApiReturnResult<String> getSbglPageData(HttpServletRequest request, SbglParam sbglParam) {

        ApiReturnResult<String> apiReturnResult = service.getSbglApiPageData(sbglParam);
        return apiReturnResult;
    }


    /**
     * @param request
     * @param id
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 查询设备数据//描述
     * @author lcm //作者
     * @date 2020/9/8 19:34 //时间
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/closeSb", method = RequestMethod.GET)
    @ApiOperation(value = "关闭设备", notes = "关闭设备")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data:{},data中的属性参照下方Model", response =
                    SbglBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "设备id", required = true, dataType = "int", paramType = "query")})
    public ApiReturnResult<String> closeSb(HttpServletRequest request, String id) {
        return service.openOrClose(id, 0);
    }

}
