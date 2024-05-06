package com.wckj.chasstage.api.server.release.rest.spjl;

import com.wckj.chasstage.api.def.shsp.model.RmdMsgBean;
import com.wckj.chasstage.api.def.shsp.model.ShspParam;
import com.wckj.chasstage.api.def.shsp.service.ApiShspService;
import com.wckj.chasstage.api.def.spjl.model.SpjlParam;
import com.wckj.chasstage.api.def.spjl.service.ApiSpjlService;
import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:zengrk
 */
@Api(tags = "审批记录")
@RestController
@RequestMapping(value = "/api/rest/chasstage/spjl/apiSpjlService")
public class ApiSpjlController  extends RestApiBaseController<ApiSpjlService> {

    @RequestMapping(value = "/getSpjlPageData", method = RequestMethod.GET)
    @ApiOperation(value = "获取审批记录", notes = "获取审批记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RmdMsgBean.class)})
    public ApiReturnResult<?> getSpjlPageData(HttpServletRequest request, SpjlParam spjlParam) {
        ApiReturnResult<?> returnResult =  this.service.getSpjlPageData(spjlParam);
        return returnResult;
    }
}
