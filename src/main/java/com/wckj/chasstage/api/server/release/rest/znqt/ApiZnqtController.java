package com.wckj.chasstage.api.server.release.rest.znqt;

import com.wckj.chasstage.api.def.znqt.model.ZnqtBean;
import com.wckj.chasstage.api.def.znqt.model.ZnqtParam;
import com.wckj.chasstage.api.def.znqt.service.ApiZnqtService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wutl
 * @Title: 智能墙体控制
 * @Package
 * @Description:
 * @date 2020-10-2011:42
 */
@Api(tags = "智能墙体")
@RestController
@RequestMapping("/api/rest/chasstage/znqt/apiZnqtService")
public class ApiZnqtController  extends RestApiBaseController<ApiZnqtService> {

    @RequestMapping(value = "/findZnqtPageData", method = RequestMethod.GET)
    @ApiOperation(value = "智能墙体查询", notes = "智能墙体查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ZnqtBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "rows", value = "分页数", dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "baqid", value = "办案区id", dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "wzmc", value = "区域名称", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<Map<String,Object>> findZnqtPageData(HttpServletRequest request, int page,int rows,String baqid,String wzmc) {
        ApiReturnResult<Map<String,Object>> result = this.service.findZnqtPageData(page,rows,baqid,wzmc);
        return result;
    }


    @RequestMapping(value = "/saveZnqt", method = RequestMethod.POST)
    @ApiOperation(value = "新增智能墙体", notes = "新增智能墙体")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> saveZnqt(HttpServletRequest request, ZnqtParam znqtParam) {
        ApiReturnResult<String> result = this.service.saveZnqt(znqtParam);
        return result;
    }


    @RequestMapping(value = "/updateZnqt", method = RequestMethod.POST)
    @ApiOperation(value = "修改智能墙体", notes = "修改智能墙体")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    public ApiReturnResult<String> updateZnqt(HttpServletRequest request, ZnqtParam znqtParam) {
        ApiReturnResult<String> result = this.service.updateZnqt(znqtParam);
        return result;
    }

    @RequestMapping(value = "/openZnqt", method = RequestMethod.GET)
    @ApiOperation(value = "打开智能墙体", notes = "打开智能墙体")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ZnqtBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "qyid", value = "区域id", dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "sbbh", value = "设备编号（多个逗号隔开）", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> openZnqt(HttpServletRequest request,String baqid,String qyid,String sbbh) {
        ApiReturnResult<String> result = this.service.openZnqt(baqid,qyid,sbbh);
        return result;
    }

    @RequestMapping(value = "/deleteZnqt", method = RequestMethod.GET)
    @ApiOperation(value = "删除智能墙体", notes = "删除智能墙体")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> deleteZnqt(HttpServletRequest request,String id) {
        ApiReturnResult<String> result = this.service.deleteZnqt(id);
        return result;
    }


}
