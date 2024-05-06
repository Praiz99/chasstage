package com.wckj.chasstage.api.server.release.rest.sy;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.sy.model.SyBean;
import com.wckj.chasstage.api.def.sy.service.ApiSyService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "首页")
@RestController
@RequestMapping(value = "/api/rest/chasstage/sy/apiService")
public class ApiSyController extends RestApiBaseController<ApiSyService> {

    @Autowired
    private ApiSyService syService;

    @RequestMapping(value = "/getStatisticsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询首页统计信息", notes = "查询首页统计信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区Id(不传则默认当前登录人所在办案区)", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = SyBean.class)})
    public ApiReturnResult<String> getStatisticsInfo(HttpServletRequest request, String  baqid) {
        return (ApiReturnResult<String>) syService.getStatisticsDataByBaqid(baqid);
    }
}
