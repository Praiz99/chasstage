package com.wckj.chasstage.api.server.release.rest.qtdj;


import com.wckj.chasstage.api.def.qtdj.model.*;
import com.wckj.chasstage.api.def.qtdj.service.ApiQtdjService;
import com.wckj.chasstage.api.def.sys.model.MjxxParam;
import com.wckj.chasstage.api.def.sys.model.OrgParam;
import com.wckj.chasstage.api.def.sys.service.ApiJdoneSysService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 前台登记业务
 * @Package 前台登记业务
 * @Description: 前台登记业务
 * @date 2020-9-516:16
 */

@Api(tags = "前台登记（岗位一、二）")
@RestController
@RequestMapping(value = "/api/rest/chasstage/qtdj/qtdjService")
public class ApiQtdjController extends RestApiBaseController<ApiQtdjService> {

    @Autowired
    private ApiQtdjService qtdjService;
    @Autowired
    private ApiJdoneSysService apiJdoneSysService;

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "民警信息查询", notes = "民警信息查询")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = UserBean.class)})
    @RequestMapping(value = "/getMjxx", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> getMjxx(HttpServletRequest request, MjxxParam mjxxParam) {
        return apiJdoneSysService.getMjxxPageData(request, mjxxParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "机构信息查询", notes = "机构信息查询")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = OrgBean.class)})
    @RequestMapping(value = "/getOrgxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getOrgxx(HttpServletRequest request, OrgParam orgParam) {
        return apiJdoneSysService.getOrgPageData(request,orgParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "岗位腕带编号登录", notes = "根据腕带编号登录岗位")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/loginByWdbh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "wdbhl", value = "低频腕带编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rybh", value = "人员编号", required = false, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> loginByWdbh(HttpServletRequest request,String wdbhl, String rybh) {
        return qtdjService.loginByWdbh(wdbhl, rybh);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取警情信息", notes = "获取警情信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = JqxxBean.class)})
    @RequestMapping(value = "/getJqxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getJqxx(HttpServletRequest request, JqxxParam jqxxParam) {
        return qtdjService.getJqxxPageData(jqxxParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取案件信息", notes = "获取案件信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = AjxxBean.class)})
    @RequestMapping(value = "/getAjxx", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getAjxx(HttpServletRequest request, AjxxParam ajxxParam) {
        return qtdjService.getAjxxPageData(ajxxParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "校验腕带", notes = "校验腕带")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/validateWd", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> validateWd(HttpServletRequest request, WdParam wdParam) {
        return qtdjService.validateWd(wdParam);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "预分配储物柜", notes = "预分配储物柜")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = Yfpwpgxx.class)})
    @RequestMapping(value = "/getBoxNoByType", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "箱子类型 boxType为true的 type为：小3 中2 大1 超大4 ; boxType为false type传的 1 物品柜，40手机柜 ", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "boxType", value = "是否大小柜（true 是 false 否）", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getBoxNoByType(HttpServletRequest request, String baqid,String type,String boxType) {
        return qtdjService.getBoxNoByType(boxType,baqid,type);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存人员信息", notes = "保存人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/SaveWithUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> SaveWithUpdate(HttpServletRequest request, RyxxBean ryxxBean) {
        return qtdjService.SaveWithUpdate(ryxxBean);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取同案人员信息", notes = "获取同案人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = TaryBean.class)})
    @RequestMapping(value = "/getDataByYwbh", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> getDataByYwbh(HttpServletRequest request, TaryParam param) {
        return qtdjService.getDataByYwbh(param);
    }

    /*@ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存随身物品信息", notes = "保存随身物品信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/SaveWithUpdateSswpByList", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "json格式的随身物品信息", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "del", value = "XXXX", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> SaveWithUpdateSswpByList(HttpServletRequest request, String json,String del) {
        return qtdjService.SaveWithUpdateSswpByList(json, del);
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "随身物品拍照", notes = "随身物品拍照")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model")})
    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> capture(HttpServletRequest request, SswpPzParam param) {
        return qtdjService.capture(param);
    }*/

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存一体化采集情况", notes = "保存一体化采集情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @RequestMapping(value = "/saveYthcjQk", method = RequestMethod.POST)
    @ResponseBody
    public ApiReturnResult<?> saveYthcjQk(HttpServletRequest request, YthcjParam param) {
        return qtdjService.saveYthcjQk(param);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查询一体化采集情况", notes = "人员编号查询一体化采集情况")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = YthcjBean.class)})
    @RequestMapping(value = "/getYthcjQk", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getYthcjQk(HttpServletRequest request,String rybh) {
        return qtdjService.getYthcjQk(rybh);
    }


    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "人员编号查询人身安全检查", notes = "人员编号查询人身安全检查")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = AqjcBean.class)})
    @RequestMapping(value = "/getAqjcByRybh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getAqjcByRybh(HttpServletRequest request,String rybh) {
        return qtdjService.findByRybh(rybh);
    }

    //checked
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "保存人身安全检查", notes = "保存人身安全检查")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/saveAqjc", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> saveAqjc(HttpServletRequest request,AqjcBean aqjcBean) {
        return qtdjService.SaveAqjc(aqjcBean);
    }


    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取人员信息", notes = "获取人员信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxResult.class)})
    @RequestMapping(value = "/getRyxxAllByRybh", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> getRyxxAllByRybh(HttpServletRequest request,String rybh) {
        return qtdjService.getRyxxAllByRybh(rybh);
    }

    //checked
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "修改人员流程状态", notes = "修改人员流程状态")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = RyxxResult.class)})
    @RequestMapping(value = "/updateStaffProcessStatus", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lczt", value = "流程状态【人生安全检查（03）保存后 修改流程为04（随身物品）    随身物品保存后 修改流程为07 （一体化采集） 一体化采集完成后 修改流程状态为01（信息登记）】", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> UpdateStaffProcessStatus(HttpServletRequest request,String rybh,String lczt) {
        return qtdjService.updateStaffProcessStatus(rybh,lczt);
    }
    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取安全检查人员关联伤情照片信息", notes = "获取安全检查人员关联伤情照片信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/securitycheck", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> Securitycheck(HttpServletRequest request, String rybh) {
        return qtdjService.Securitycheck(rybh);
    }


    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "前台语音播报", notes = "前台语音播报")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/yybbByQtdj", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bbhj", value = "播报环节（02安全检查 03 随身物品）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "String", paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> yybbByQtdj(HttpServletRequest request, String baqid, String bbhj,String rybh) {
        return qtdjService.yybbByQtdj(baqid,bbhj,rybh);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "ssj")
    @ApiOperation(value = "人脸识别", notes = "人脸识别接口")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    @RequestMapping(value = "/getRyxxByImg", method = RequestMethod.POST)
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "baqid", value = "办案区ID", required = true, dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "base64", value = "人脸base64", required = true, dataType = "String", paramType = "query")}
    )
    @ResponseBody
    public ApiReturnResult<?> getRyxxByImg(HttpServletRequest request, String baqid, String base64) {
        return service.getRyxxByImg(request, baqid, base64);
    }
}
