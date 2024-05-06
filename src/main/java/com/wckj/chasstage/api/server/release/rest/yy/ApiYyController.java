package com.wckj.chasstage.api.server.release.rest.yy;


import com.wckj.chasstage.api.def.cldj.service.ApiCldjService;
import com.wckj.chasstage.api.def.dhsgl.model.DhsBean;
import com.wckj.chasstage.api.def.dhsgl.service.ApiDhsglService;
import com.wckj.chasstage.api.def.mjzpk.model.MjzpkBean;
import com.wckj.chasstage.api.def.shsp.model.RmdMsgBean;
import com.wckj.chasstage.api.def.shsp.model.ShcomitParam;
import com.wckj.chasstage.api.def.shsp.model.ShspParam;
import com.wckj.chasstage.api.def.shsp.service.ApiShspService;
import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.sxsgl.service.ApiSxsglService;
import com.wckj.chasstage.api.def.yygl.model.YyglBean;
import com.wckj.chasstage.api.def.yygl.model.YyglParam;
import com.wckj.chasstage.api.def.yygl.model.YyjyParam;
import com.wckj.chasstage.api.def.yygl.service.ApiYyglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 预约控制器
 * @Package
 * @Description:
 * @date 2020-9-1710:15
 */
@Api(tags = "预约管理")
@RestController
@RequestMapping("/api/rest/chasstage/yy/apiYyService")
public class ApiYyController extends RestApiBaseController<ApiYyglService> {

    @Autowired
    private ApiYyglService apiService;
    @Autowired
    private ApiDhsglService dhsglService;

    @Autowired
    private ApiSxsglService sxsglService;
    @Autowired
    private ApiShspService shspService;
    @Autowired
    private ApiCldjService cldjService;
    @ApiAccessNotValidate
    @RequestMapping(value = "/getSxsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询审讯室使用情况", notes = "查询审讯室使用情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baqid", value = "办案区Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appSfzh", value = "app用户身份证", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页数", required = true, dataType = "int", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = SxsBean.class)})
    public ApiReturnResult<String> getSxsInfo(HttpServletRequest request, String baqid, String appSfzh,int page, int rows) {
        return (ApiReturnResult<String>) sxsglService.getSxsInfo(baqid,appSfzh, page, rows);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getDhsInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询等候室分配情况", notes = "查询等候室分配情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 0,data: {rows:'',total:''},isSuccess: true,message: '查询成功'},rows中的属性参照下方Model", response = DhsBean.class)})
    public ApiReturnResult<?> getDhsInfo(HttpServletRequest request, String baqid) {
        return dhsglService.getDhsInfo(baqid);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询预约信息", notes = "查询预约信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增预约信息", notes = "新增预约信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, YyglBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除预约信息", notes = "删除预约信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "预约信息列表查询", notes = "预约信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = YyglBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, YyglParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/yyjy", method = RequestMethod.POST)
    @ApiOperation(value = "预约校验", notes = "预约校验")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = MjzpkBean.class)})
    public ApiReturnResult<String> yyjy(HttpServletRequest request, YyjyParam param) {
        return (ApiReturnResult<String>) apiService.yyjy(param);
    }

    /**
     * @param request
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/getRmgPageData", method = RequestMethod.POST)
    @ApiOperation(value = "获取审批消息", notes = "获取审批消息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RmdMsgBean.class)})
    public ApiReturnResult<?> getRmgPageData(HttpServletRequest request, ShspParam shspParam) {
        ApiReturnResult<?> returnResult = shspService.getRmgDataByParamMap(shspParam.getPage(), shspParam.getRows(), shspParam, "CREATE_TIME DESC");
        return returnResult;
    }


    /**
     * @param request
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/getCqspXq", method = RequestMethod.POST)
    @ApiOperation(value = "获取出区审批详情", notes = "获取出区审批详情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "业务id", required = false, dataType = "string", paramType = "query")}
    )
    public ApiReturnResult<?> getCqspXq(HttpServletRequest request, String id) {
        ApiReturnResult<?> returnResult = shspService.getCqspXq(id);
        return returnResult;
    }

    /**
     * @param request
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/docqsp", method = RequestMethod.POST)
    @ApiOperation(value = "出区审批", notes = "出区审批")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<?> docqsp(HttpServletRequest request, ShcomitParam shcomitParam) {
        ApiReturnResult<?> returnResult = shspService.docqsp(shcomitParam);
        return returnResult;
    }


    /**
     * @param request
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/getNextTask", method = RequestMethod.POST)
    @ApiOperation(value = "节点详情", notes = "节点详情")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    public ApiReturnResult<?> getNextTaskNodeInfo(HttpServletRequest request, String bizId,String bizType) {
        ApiReturnResult<?> returnResult = shspService.getNextTaskNodeInfo(bizId,bizType);
        return returnResult;
    }


    /**
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/checkYyCphm", method ={ RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "车牌预约校验", notes = "车牌预约校验")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "cphm", value = "车牌号码", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "baqid", value = "办案区Id", dataType = "string",
            paramType = "query"),@ApiImplicitParam(name = "crlx", value = "1进，0出", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> checkYyCphm( String baqid,String cphm,String crlx) {
        ApiReturnResult<?> returnResult = apiService.checkYyCphm(baqid,cphm,crlx);
        if("1".equals(crlx)){
            cldjService.unBindSyclBycph(baqid, cphm, crlx);
        }
        return returnResult;
    }


    /**
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @ApiAccessNotValidate
    @RequestMapping(value = "/getBaqrefByOrgcode", method ={ RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "获取关联办案区", notes = "获取关联办案区")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "orgCode", value = "单位代码", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> getBaqrefByOrgcode(String orgCode) {
        ApiReturnResult<?> returnResult = apiService.getBaqrefByOrgcode(orgCode);
        return returnResult;
    }
}
