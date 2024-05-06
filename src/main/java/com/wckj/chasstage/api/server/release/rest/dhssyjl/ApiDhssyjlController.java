package com.wckj.chasstage.api.server.release.rest.dhssyjl;

import com.wckj.chasstage.api.def.dhsgl.model.DhsBean;
import com.wckj.chasstage.api.def.dhsgl.service.ApiDhsglService;
import com.wckj.chasstage.api.def.dhssyjl.model.DhssyjlParam;
import com.wckj.chasstage.api.def.dhssyjl.service.ApiDhssyjService;
import com.wckj.chasstage.modules.dhssyjl.entity.ChasYwDhssyjl;
import com.wckj.chasstage.modules.dhssyjl.service.ChasDhsSyjlService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:zengrk
 */
@Api(tags = "等候室管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/dhssyjl/apiService")
public class ApiDhssyjlController  extends RestApiBaseController<ApiDhsglService> {

    @Autowired
    private ApiDhssyjService apiService;
    @Autowired
    private ChasDhsSyjlService dhsSyjlService;

    @CrossOrigin
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查看等候室使用记录", notes = "查看等候室使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model")})
    public ApiReturnResult<?> get(HttpServletRequest request, DhssyjlParam Param) {
        return apiService.getDhssyjlPageData(Param);
    }

    @CrossOrigin
    @ApiAccessNotValidate
    @RequestMapping(value = "/saveOrUpdateSyjl", method = RequestMethod.POST)
    @ApiOperation(value = "保存等候室使用记录", notes = "保存等候室使用记录")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model")})
    public ApiReturnResult<?> saveOrUpdateSyjl(HttpServletRequest request, ChasYwDhssyjl dhssyjl) {
        return dhsSyjlService.saveOrUpdateSyjl(dhssyjl);
    }
}
