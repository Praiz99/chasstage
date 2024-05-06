package com.wckj.chasstage.api.server.release.rest.shsp;


import com.wckj.chasstage.api.def.shsp.model.RmdMsgBean;
import com.wckj.chasstage.api.def.shsp.model.ShspParam;
import com.wckj.chasstage.api.def.shsp.service.ApiShspService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act.entity.JdoneActInstNodePro;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import com.wckj.jdone.modules.rmd.service.JdoneRmdMsgService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 审核审批
 * @Package
 * @Description:
 * @date 2020-10-2818:16
 */
@Api(tags = "审核审批")
@RestController
@RequestMapping(value = "/api/rest/chasstage/shsp/apiShspService")
public class ApiShspController extends RestApiBaseController<ApiShspService> {


    /**
     * @param request
     * @return
     * @throws //异常
     * @description
     * @author lcm //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @RequestMapping(value = "/getRmgPageData", method = RequestMethod.POST)
    @ApiOperation(value = "获取审批消息", notes = "获取审批消息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RmdMsgBean.class)})
    public ApiReturnResult<?> getRmgPageData(HttpServletRequest request, ShspParam shspParam) {
        ApiReturnResult<?> returnResult =  this.service.getRmgDataByParamMap(shspParam.getPage(), shspParam.getRows(), shspParam, "CREATE_TIME DESC");
        return returnResult;
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "执行审批流程", notes = "执行审批流程")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/executeProcess", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj) {
        return this.service.executeProcess(cmdObj);
    }


    /**
     * 获取审批流程信息
     *
     * @return
     */
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "审批流程查询", notes = "审批流程查询")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/cqsplc", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "bizId", value = "业务id", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> cqsplc(String bizId, String bizType) {
        return this.service.cqsplc(bizId,bizType);

    }

    /**
     * 获取审批流程信息
     *
     * @return
     */
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "不再提醒", notes = "不再提醒")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = ApiReturnResult.class)})
    @RequestMapping(value = "/noPrompt", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "msgId", value = "消息Id", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> noPrompt(String msgId) {
        return this.service.noPrompt(msgId);

    }

}
