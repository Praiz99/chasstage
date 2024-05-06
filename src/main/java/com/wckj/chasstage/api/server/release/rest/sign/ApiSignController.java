package com.wckj.chasstage.api.server.release.rest.sign;

/**
 * @author lcm
 * @Title: 签字捺印功能
 * @Package
 * @Description:
 * @date 2020-9-39:57
 */

import com.wckj.chasstage.api.def.sign.model.SignData;
import com.wckj.chasstage.api.def.sign.service.ApiSignService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.common.util.pdf.PdfComprehensiveUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "签字捺印")
@RestController
@RequestMapping(value = "/api/rest/chasstage/sign/apiSignService")
public class ApiSignController extends RestApiBaseController<ApiSignService> {

    @ApiAccessNotValidate
    @RequestMapping(value = "/getSignData",method = RequestMethod.POST)
    @ApiOperation(value = "查询签字捺印数据", notes = "查询签字捺印数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = SignData.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "signType", value = "签字类型",  dataType = "string", paramType = "query")})
    public ApiReturnResult<String> getSignData(HttpServletRequest request, String rybh, String signType) {
        ApiReturnResult<String> apiReturnResult = service.getSignByRybhOrsignType(rybh, signType);
        return apiReturnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getSignDataComprehensive",method = RequestMethod.POST)
    @ApiOperation(value = "查询签字捺印数据综合版", notes = "查询签字捺印数据")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = SignData.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签字类型",  dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remove", value = "是否删除捺印数据",  dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "ignore", value = "是否过滤已有数据",  dataType = "boolean", paramType = "query")})
    public ApiReturnResult<?> getSignDataZz(HttpServletRequest request, String rybh, String signType,boolean remove,boolean ignore) {
        ApiReturnResult<?> apiReturnResult = service.getSignDataComprehensive(rybh, signType,remove,ignore);
        return apiReturnResult;
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/deleteBaqqy",method = RequestMethod.GET)
    @ApiOperation(value = "根据人员编号删除签字捺印数据", notes = "根据人员编号删除签字捺印数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签字标识",  dataType = "string",paramType = "query")})
    public ApiReturnResult<String> deleteBaqqy(HttpServletRequest request, String rybh,String inxs) {
        ApiReturnResult<String> apiReturnResult = service.deleteSignByRybhKhd(rybh,inxs);
        return apiReturnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getSignDataKhd",method = RequestMethod.POST)
    @ApiOperation(value = "查询签字捺印数据客户端", notes = "查询签字捺印数据客户端")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code: 200,data: {},isSuccess: true,message: '保存成功'},data中的属性参照下方Model", response = SignData.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "signType", value = "签字类型",  dataType = "string", paramType = "query")})
    public ApiReturnResult<String> getSignDataKhd(HttpServletRequest request, String rybh, String signType) {
        ApiReturnResult<String> apiReturnResult = service.getSignByRybhOrsignTypeKhd(rybh, signType);
        return apiReturnResult;
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/saveSign",method = RequestMethod.POST)
    @ApiOperation(value = "保存签字数据", notes = "保存签字数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "signType", value = "签字类型",  dataType = "string", paramType = "query")})
    public ApiReturnResult<String> saveSign(HttpServletRequest request, String rybh, String imgBody, String inxs) {
        ApiReturnResult<String> apiReturnResult = service.saveSignKhd(rybh,imgBody,inxs);
        return apiReturnResult;
    }


    @ApiAccessNotValidate
    @RequestMapping(value = "/saveWebSign",method = RequestMethod.POST)
    @ApiOperation(value = "保存签字数据(网页版本)", notes = "保存签字数据(网页版本)")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",
            paramType = "form"), @ApiImplicitParam(name = "data", value = "签字数据",  dataType = "string", paramType = "form")})
    public ApiReturnResult<String> saveWebSign(HttpServletRequest request, String rybh, String data) {
        //{"status":-4,"data":[{"Id":2,"ImgData":"","ImgPath":"","DelFlag":1}]}
        return service.saveWebSign(rybh, data);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getRydjbContainImg",method = RequestMethod.GET)
    @ApiOperation(value = "获取人员登记表（包含签字捺印）", notes = "获取人员登记表（包含签字捺印）")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",paramType = "form")})
    public ApiReturnResult<String> getRydjbContainImg(HttpServletRequest request, String rybh) {
        if(StringUtil.isNotEmpty(rybh)){
            try {
                byte[] wspdf = PdfComprehensiveUtil.buildMsgObjectRyxx_Interface(rybh,true);
                BASE64Encoder encoder = new BASE64Encoder();
                return ResultUtil.ReturnSuccess("获取成功!", encoder.encode(wspdf));
            } catch (Exception e) {
                return ResultUtil.ReturnError(e.getMessage());
            }
        }else{
            return ResultUtil.ReturnError("人员编号不能为空!");
        }
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/getSignDataKhdIgnoreOfBase64",method = RequestMethod.GET)
    @ApiOperation(value = "获取签字捺印（客户端版-忽略已签字捺印的数据）", notes = "获取签字捺印（客户端版-忽略已签字捺印的数据）")
    @ApiImplicitParams({@ApiImplicitParam(name = "rybh", value = "人员编号",  dataType = "string",paramType = "form"),
            @ApiImplicitParam(name = "signType", value = "签字类型",  dataType = "string", paramType = "query")})
    public ApiReturnResult<String> getSignDataKhdIgnoreOfBase64(HttpServletRequest request, String rybh,String signType) {
        ApiReturnResult<String> apiReturnResult = service.getSignByRybhOrsignTypeKhdIgnoreOfBase64(rybh, signType);
        return apiReturnResult;
    }
}
