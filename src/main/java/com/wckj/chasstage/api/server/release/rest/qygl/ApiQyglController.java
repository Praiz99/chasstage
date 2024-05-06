package com.wckj.chasstage.api.server.release.rest.qygl;

/**
 * @author kcm
 * @Title: 办案区区域管理功能
 * @Package
 * @Description:
 * @date 2020-9-39:57
 */


import com.wckj.chasstage.api.def.qygl.model.QyglBean;
import com.wckj.chasstage.api.def.qygl.service.ApiQyglService;
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
 * @Title: 办案区区域管理
 * @Package
 * @Description:
 * @date 2020-9-516:29
 */
@Api(tags = "办案区区域管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/baq/apiQyglService")
public class ApiQyglController extends RestApiBaseController<ApiQyglService> {


    /**
     * @param request
     * @param qyglParam
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 查询区域数据//描述
     * @author lcm //作者
     * @date 2020/9/8 19:34 //时间
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/getQyglageData", method = RequestMethod.GET)
    @ApiOperation(value = "查询区域数据", notes = "查询区域数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = QyglBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "int", paramType = "query")})
    public ApiReturnResult<String> getQyglageData(HttpServletRequest request, QyglBean qyglParam) {

        ApiReturnResult<String> apiReturnResult = service.getQyglApiPageData(qyglParam);
        return apiReturnResult;
    }

    /**
     * @param request
     * @param baqid
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 同步办案区区域数据 //描述
     * @author lcm //作者
     * @date 2020/9/9 11:58 //时间
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/tbbaqQyxx", method = RequestMethod.POST)
    @ApiOperation(value = "同步办案区区域数据", notes = "同步办案区区域数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response =
                    ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string", paramType = "query")})
    public ApiReturnResult<String> tbbaqQyxx(HttpServletRequest request, String baqid) {

        ApiReturnResult<String> apiReturnResult = service.tbBaqqy(baqid);
        return apiReturnResult;
    }

    /**
     * @param request
     * @param ids
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 同步办案区区域数据 //描述
     * @author lcm //作者
     * @date 2020/9/9 11:58 //时间
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/deleteBaqqy", method = RequestMethod.GET)
    @ApiOperation(value = "删除办案区区域数据", notes = "删除办案区区域数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "区域id数组", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> deleteBaqqy(HttpServletRequest request, String ids) {
        ApiReturnResult<String> apiReturnResult = service.deleteBaqByIds(ids);
        return apiReturnResult;
    }

    @RequestMapping(value = "/getBaqDicByUser", method = RequestMethod.GET)
    @ApiOperation(value = "根据办案区获取区域字典", notes = "根据办案区获取区域字典")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {msg:'同步设备成功',cab:{code:1,message:同步储物柜数量:13},tag:{code:1,message:同步设备数量:55}},isSuccess: true,message: '同步设备成功'},data中的属性参照下方Model", response = QyglBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "queryValue", value = "查询字典", required = false, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "fjlx", value = "房间类型，审讯室传3", required = false, dataType = "string",
            paramType = "query")})
    @ApiAccessNotValidate
    public ApiReturnResult<?> getQyDicByBaq(HttpServletRequest request, String baqid,String queryValue,String fjlx) {
        return service.getQyDicByBaq(baqid,queryValue,fjlx);
    }

}
