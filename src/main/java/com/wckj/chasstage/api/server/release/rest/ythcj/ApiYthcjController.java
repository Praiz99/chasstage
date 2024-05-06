package com.wckj.chasstage.api.server.release.rest.ythcj;

import com.wckj.chasstage.api.def.ythcj.model.YthcjParam;
import com.wckj.chasstage.api.def.ythcj.service.ApiYthcjService;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "一体化采集管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/ythcjtemp/apiService")
public class ApiYthcjController extends RestApiBaseController<ApiYthcjService> {

    @ApiAccessNotValidate
    @RequestMapping(value = "/saveYthcjRyxx", method = RequestMethod.POST)
    @ApiOperation(value = "保存一体化采集信息", notes = "保存一体化采集信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @ResponseBody
    public ApiReturnResult<String> saveYthcjRyxx(HttpServletRequest request, ChasYthcj ythcj,YthcjParam ythcjParam) {
        return this.service.saveYthcjRyxx(ythcj,ythcjParam);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getYthcjRyxx", method = RequestMethod.GET)
    @ApiOperation(value = "获取一体化采集信息", notes = "获取一体化采集信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @ResponseBody
    public ApiReturnResult<?> getYthcjRyxx(HttpServletRequest request, String rybh) {
        return this.service.getYthcjRyxx(rybh);
    }
}
