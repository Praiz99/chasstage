package com.wckj.chasstage.api.server.release.rest.zyry;

import com.wckj.chasstage.api.def.baq.model.BaqBean;
import com.wckj.chasstage.api.def.yybb.model.YybbParam;
import com.wckj.chasstage.api.def.zyry.model.ZyryParam;
import com.wckj.chasstage.api.def.zyry.service.ApiZyryyjService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: QYT
 * @Date: 2023/6/16 9:20 上午
 * @Description:在押人员预警
 */
@Api(tags = "在押人员预警")
@RestController
@RequestMapping(value = "/api/rest/chasstage/zyry/apiZyryyjService")
public class ApiZyryyjController extends RestApiBaseController<ApiZyryyjService> {

    @RequestMapping(value = "/pushZyryAlarm", method = RequestMethod.POST)
    @ApiOperation(value = "推送在押人员预警", notes = "推送在押人员预警")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '推送成功'},data中的属性参照下方Model", response = BaqBean.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "办案区id", value = "baqid", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "入区时间范围开始时间", value = "kssj", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "入区时间范围结束时间", value = "jssj", required = true, dataType = "string", paramType = "query")
    })
    public ApiReturnResult<String> pushZyryAlarm(HttpServletRequest request, ZyryParam zyryParam) {
        return this.service.pushZyryAlarm(zyryParam);
    }
}
