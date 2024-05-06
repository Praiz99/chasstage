package com.wckj.chasstage.api.server.release.rest.khd;

import com.wckj.chasstage.api.def.wpgl.model.QwjlParam;
import com.wckj.chasstage.api.def.wpgl.service.ApiSswpService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wutl
 * @Title: 智能柜终端接口
 * @Package 智能柜终端接口
 * @Description: 智能柜终端接口
 * @date 2020-9-1713:55
 */
@Api(tags = "智能柜终端")
@RestController
@RequestMapping("/api/rest/chasstage/zngzd/apiZngzdService")
public class ApiZngzdController extends RestApiBaseController<ApiSswpService> {
    @Autowired
    private ApiSswpService apiSswpService;
    @ApiAccessNotValidate
    @RequestMapping(value = "/qwcz",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "取物操作", notes = "取物操作")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> qwcz(HttpServletRequest request, QwjlParam param) {
        return apiSswpService.saveQwjl(param);
    }
}
