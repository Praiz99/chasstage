package com.wckj.chasstage.api.server.release.rest.rlbd;

import com.wckj.chasstage.api.def.qygl.model.QyglBean;
import com.wckj.chasstage.api.def.qygl.service.ApiQyglService;
import com.wckj.chasstage.api.def.rlbd.model.RlbdParam;
import com.wckj.chasstage.api.def.rlbd.model.RlbdResult;
import com.wckj.chasstage.api.def.rlbd.service.ApiRlbdService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 人脸比对控制器
 * @Package
 * @Description:
 * @date 2020-12-1711:23
 */
@Api(tags = "人脸大数据比对")
@RestController
@RequestMapping(value = "/api/rest/chasstage/rlbd/ApiRlbdService")
public class ApiRlbdController extends RestApiBaseController<ApiRlbdService> {

    @ApiAccessNotValidate
    @RequestMapping(value = "/getRlbdData", method = RequestMethod.POST)
    @ApiOperation(value = "人脸大数据比对", notes = "人脸大数据比对")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = RlbdResult.class)})
    public ApiReturnResult<?> getRlbdData(HttpServletRequest request, RlbdParam rlbdParam) {
        ApiReturnResult<?> apiReturnResult = this.service.getRlbdData(rlbdParam);
        return apiReturnResult;
    }

    /**
     * 人员身份证和姓名校验
     * @param request
     * @param sfzh
     * @param xm
     * @return
     */
    @RequestMapping(value = "/checkSfzhAndXm", method = RequestMethod.GET)
    @ApiOperation(value = "人员身份证和姓名校验", notes = "人员身份证和姓名校验")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "sfzh", value = "人员身份证号", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "xm", value = "人员姓名", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> checkSfzhAndXm(HttpServletRequest request,String sfzh,String xm) {
        ApiReturnResult<?> apiReturnResult = this.service.checkSfzhAndXm(request,sfzh,xm);
        return apiReturnResult;
    }
}
