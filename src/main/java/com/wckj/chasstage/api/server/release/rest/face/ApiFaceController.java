package com.wckj.chasstage.api.server.release.rest.face;

import com.wckj.chasstage.api.def.baqry.service.ApiBaqryService;
import com.wckj.chasstage.api.def.face.model.MjzpkBean;
import com.wckj.chasstage.api.def.face.service.ApiFaceService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "人脸识别管理")
@RestController
@RequestMapping(value = "/api/rest/chasstage/face/apiService")
public class ApiFaceController extends RestApiBaseController<ApiFaceService> {

    @Autowired
    private ApiFaceService apiService;
    @Autowired
    private ApiBaqryService baqryService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;


    @ApiAccessNotValidate
    @RequestMapping(value = "/xyr/recognition", method = RequestMethod.POST)
    @ApiOperation(value = "嫌疑人人脸识别", notes = "嫌疑人人脸识别")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = RyxxBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<RyxxBean> xyrRecognition(HttpServletRequest request, String rybh) {
        ApiReturnResult<RyxxBean> byRybh = baqryService.findByRybh(rybh);
        return byRybh;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/mj/recognition", method = RequestMethod.POST)
    @ApiOperation(value = "民警人脸识别", notes = "民警人脸识别")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = MjzpkBean.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "sfzh", value = "身份证", required = true, dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "type", value = "民警：mj,其他：qt", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<?> mjRecognition(HttpServletRequest request, String sfzh, String type) {
        if(StringUtils.isEmpty(sfzh)){
            ResultUtil.ReturnError("身份证不能为空！");
        }
        ChasXtMjzpk bySfzh = mjzpkService.findBySfzh(sfzh);
        if (bySfzh == null) {
            ResultUtil.ReturnError("不存在民警信息");
        }
        return ResultUtil.ReturnSuccess(bySfzh);
    }

    @RequestMapping(value = "/comparePicFq", method = RequestMethod.POST)
    @ApiOperation(value = "人脸比对", notes = "考勤打卡")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "base64Str", value = "照片base64数据", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> comparePicFq(HttpServletRequest request, String base64Str) {
        return null;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/checkXyrInfo", method = RequestMethod.POST)
    @ApiOperation(value = "防误放人脸接口", notes = "防误放人脸接口")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query"),
            @ApiImplicitParam(name = "data", value = "人脸识别结果（json字符串）", required = true, dataType = "string",
                    paramType = "query")})
    public ApiReturnResult<?> checkXyrInfo(HttpServletRequest request, String baqid, String data) {
        ApiReturnResult<?> result = this.service.checkXyrInfo(baqid, data);
        return result;
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/xyr/recognitionAll", method = RequestMethod.POST)
    @ApiOperation(value = "速裁、送押人脸识别", notes = "素裁、送押人脸识别")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", required = true, dataType = "string",
            paramType = "query"),
            @ApiImplicitParam(name = "data", value = "人脸识别结果", required = true, dataType = "string",
                    paramType = "query"), @ApiImplicitParam(name = "sblx", value = "识别类型：1 速裁，2送押", required = true, dataType = "string",
                    paramType = "query")})
    public ApiReturnResult<?> recognitionAll(HttpServletRequest request, String baqid, String data,String sblx) {
        if("1".equals(sblx)){
            return this.service.recognitionScry(baqid, data,sblx );
        }else{
            ApiReturnResult<?> result = this.service.recognitionAll(baqid, data,sblx);
            return result;
        }

    }

}
