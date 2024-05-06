package com.wckj.chasstage.api.server.release.rest.tjdj;

import com.wckj.chasstage.api.def.tjdj.model.TjdjBean;
import com.wckj.chasstage.api.def.tjdj.service.ApiTjdjService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "体检登记")
@RestController
@RequestMapping(value = "/api/rest/chasstage/tjdj/tjdjService")
public class ApiTjdjController extends RestApiBaseController<ApiTjdjService> {


    @ApiAccessNotValidate
    @RequestMapping(value = "/getTjdjByRybh", method = RequestMethod.GET)
    @ApiOperation(value = "获取体检登记（id为空，则新增，有id则修改）", notes = "获取体检登记")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = TjdjBean.class)})
    public ApiReturnResult<TjdjBean> getTjdjByRybh(HttpServletRequest request, String  rybh) {
        ApiReturnResult<TjdjBean> returnResult = this.service.getTjdjByRybh(rybh);
        return returnResult;
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/saveTjdj", method = RequestMethod.GET)
    @ApiOperation(value = "保存体检登记", notes = "保存体检登记")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> saveTjdj(HttpServletRequest request, TjdjBean  tjdjBean) {
        ApiReturnResult<String> returnResult = this.service.saveTjdj(tjdjBean);
        return returnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/updateTjdj", method = RequestMethod.GET)
    @ApiOperation(value = "修改体检登记", notes = "修改体检登记")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> updateTjdj(HttpServletRequest request, TjdjBean  tjdjBean) {
        ApiReturnResult<String> returnResult = this.service.updateTjdj(tjdjBean);
        return returnResult;
    }


}
