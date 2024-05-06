package com.wckj.chasstage.api.server.release.rest.common;


import com.wckj.chasstage.api.def.common.model.FileInfo;
import com.wckj.chasstage.api.def.common.model.ProcessParam;
import com.wckj.chasstage.api.def.common.model.WlsxtBean;
import com.wckj.chasstage.api.def.common.service.ApiCommonService;
import com.wckj.chasstage.api.def.dhsgl.service.ApiDhsglService;
import com.wckj.chasstage.api.def.qtdj.model.RyxxParam;
import com.wckj.chasstage.api.def.qtdj.service.ApiQtdjService;
import com.wckj.chasstage.api.def.wpgl.service.ApiSswpService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.ApiResourceController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 公共接口
 * @Package 公共接口
 * @Description: 公共接口
 * @date 2020-9-2210:22
 */
@Api(tags = "公共基础接口")
@RestController
@RequestMapping(value = "/api/rest/chasstage/common/apiCommonService")
public class ApiCommonController extends RestApiBaseController<ApiCommonService> {
    @Autowired
    private ApiSswpService sswpService;

    @Autowired
    private ApiQtdjService qtdjService;

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "照片上传", notes = "照片上传")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:{},data中的属性参照下方Model")})
    @RequestMapping(value = "/uploadpz", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "bizId", value = "业务Id", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "base64", value = "图片数据", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "sfsc", value = "1：替换老照片，更新为本次上传的照片；0 或者不传 ：在原来的基础上添加照片", dataType = "string",
            paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> uploadpz(HttpServletRequest request, String bizId, String base64, String bizType, String sfsc) {
        try {
            String[] split = base64.split("base64,");
            if (split.length > 1) {
                base64 = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sswpService.uploadPz(bizId, base64, bizType, sfsc);

    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "查看照片", notes = "查看照片")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:{},data中的属性参照下方Model")})
    @RequestMapping(value = "/showImg", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "bizId", value = "业务Id", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "string",
            paramType = "query")})
    @ResponseBody
    public ApiReturnResult<?> showImg(HttpServletRequest request, HttpServletResponse response, String bizId, String bizType) {
        boolean check = false;
        try {
            OutputStream out = response.getOutputStream();
            // 获取人员编号对应的数据
            if (StringUtils.isNotEmpty(bizId)) {
                if (StringUtil.isNotEmpty(bizType)) {
                    List<FileInfoObj> infoObjs = FrwsApiForThirdPart.getFileInfoListByBizIdBizType(bizId, bizType);
                    if (!infoObjs.isEmpty()) {
                        FileInfoObj fileInfoObj = infoObjs.get(0);
                        IByteFileObj fileobj = FrwsApiForThirdPart.downloadByteFileByFileId(fileInfoObj.getId());
                        if (fileobj != null) {
                            out.write(fileobj.getBytes());
                            check = true;
                        }
                    }
                } else {
                    List<FileInfoObj> fileInfoList = FrwsApiForThirdPart.getFileInfoList(bizId);
                    if(fileInfoList.size()>0){
                        FileInfoObj fileInfoObj = fileInfoList.get(0);
                        IByteFileObj fileObj = FrwsApiForThirdPart.downloadByteFileByFileId(fileInfoObj.getId());
                        out.write(fileObj.getBytes());
                        if (fileObj != null) {
                            out.write(fileObj.getBytes());
                            check = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (check == false) {

        }
        return null;
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取照片文件信息", notes = "获取照片文件信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:{},data中的属性参照下方Model",response = FileInfo.class)})
    @RequestMapping(value = "/getZpinfo", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "bizId", value = "业务Id", dataType = "string",
            paramType = "query"), @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "string",
            paramType = "query")})
    @ResponseBody
    public ApiReturnResult<List<FileInfo>> getZpwjInfo(HttpServletRequest request, HttpServletResponse response, String bizId, String bizType) {
        ApiReturnResult<List<FileInfo>> list = this.service.getZpwjInfo(bizId, bizType);
        return list;
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取32位UUID", notes = "获取32位UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:'4D3C9CE2DC48420F8EB26E85F9DA5066',data中的属性参照下方Model")})
    @RequestMapping(value = "/get32UUID", method = RequestMethod.GET)
    @ResponseBody
    public ApiReturnResult<?> get32UUID(HttpServletRequest request) {
        ApiReturnResult<String> apiReturnResult = new ApiReturnResult<>();
        apiReturnResult.setCode("200");
        apiReturnResult.setMessage("获取成功");
        apiReturnResult.setData(StringUtils.getGuid32());
        return apiReturnResult;
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "根据角色获取网络摄像头信息", notes = "根据角色获取网络摄像头信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = WlsxtBean.class)})
    @RequestMapping(value = "/getWlsxtxxByFunType", method = RequestMethod.GET)
    @ResponseBody
    @ApiAccessNotValidate
    public ApiReturnResult<?> getWlsxtxxByFunType(HttpServletRequest request,String baqid ,String funType) {
        ApiReturnResult<?> returnResult = this.service.getWlsxtxxByFunType(baqid,funType);
        return returnResult;
    }

    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "根据客户端IP获取网络摄像头信息", notes = "根据客户端IP获取网络摄像头信息")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = WlsxtBean.class)})
    @RequestMapping(value = "/getWlsxtxxByFunTypeClientIp", method = RequestMethod.GET)
    @ResponseBody
    @ApiAccessNotValidate
    public ApiReturnResult<?> getWlsxtxxByFunTypeClientIp(HttpServletRequest request,String baqid ,String funType,String clientIp) {
        ApiReturnResult<?> returnResult = this.service.getWlsxtxxByFunTypeClientIp(baqid,funType,clientIp);
        return returnResult;
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "测试连接", notes = "测试连接")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = String.class)})
    @RequestMapping(value = "/TestConnection", method = {RequestMethod.POST})
    @ResponseBody
    public ApiReturnResult<?> TestConnection() {
        return ResultUtil.ReturnSuccess("连接成功!");
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取办案区人员列表", notes = "获取办案区人员列表")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model,如果办案区id为空，则获取当前登录办案区设备",response = String.class)})
    @RequestMapping(value = "/getRyxxDataGrid", method = {RequestMethod.POST})
    @ResponseBody
    public ApiReturnResult<?> getRyxxDataGrid(RyxxParam ryxxParam) {
        return qtdjService.getBaqryxxDataGrid(ryxxParam);
    }

    @ApiAccessNotValidate
    @ApiOperationSupport(author = "wutl")
    @ApiOperation(value = "获取民警角色", notes = "获取民警角色")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "资源不存在"),
            @ApiResponse(code = 500, message = "系统后台错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:200,data:,data中的属性参照下方Model",response = String.class)})
    @RequestMapping(value = "/getMjRoles", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfzh", value = "民警身份证", required = true, dataType = "String", paramType = "query")
    })
    public ApiReturnResult<?> getMjRoles(String sfzh) {
        return service.getMjRoles(sfzh);
    }
}
