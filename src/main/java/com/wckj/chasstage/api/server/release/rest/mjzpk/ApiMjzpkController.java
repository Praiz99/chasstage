package com.wckj.chasstage.api.server.release.rest.mjzpk;

import com.wckj.chasstage.api.def.mjzpk.model.MjzpkBean;
import com.wckj.chasstage.api.def.mjzpk.model.MjzpkParam;
import com.wckj.chasstage.api.def.mjzpk.service.ApiMjzpkService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.api.annotation.ApiAccessValidate;
import com.wckj.framework.api.rest.RestApiBaseController;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 民警照片控制器
 * @Package
 * @Description:
 * @date 2020-9-1710:15
 */
@Api(tags = "民警照片库")
@RestController
@RequestMapping("/api/rest/chasstage/mjzpk/apiMjzpkService")
public class ApiMjzpkController extends RestApiBaseController<ApiMjzpkService> {

    private static Logger log = LoggerFactory.getLogger(ApiMjzpkController.class);

    @Autowired
    private ApiMjzpkService apiService;
    @ApiAccessNotValidate
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询民警照片信息", notes = "查询民警照片信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> get(HttpServletRequest request,String id) {
        return (ApiReturnResult<String>) apiService.get(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增民警照片信息", notes = "新增民警照片信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> save(HttpServletRequest request, MjzpkBean bean) {
        return (ApiReturnResult<String>) apiService.save(bean);
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改民警照片信息", notes = "修改民警照片信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> update(HttpServletRequest request, MjzpkBean bean) {
        return (ApiReturnResult<String>) apiService.update(bean);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除民警照片信息", notes = "删除民警照片信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> delete(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.deletes(id);
    }


    @RequestMapping(value = "/getPageData", method = RequestMethod.GET)
    @ApiOperation(value = "民警照片信息列表查询", notes = "民警照片信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = MjzpkBean.class)})
    public ApiReturnResult<String> getPageData(HttpServletRequest request, MjzpkParam param) {
        return (ApiReturnResult<String>) apiService.getPageData(param);
    }

    @RequestMapping(value = "/genFeature", method = RequestMethod.POST)
    @ApiOperation(value = "生成特征数据", notes = "生成特征数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<String> genFeature(HttpServletRequest request, String id) {
        return (ApiReturnResult<String>) apiService.genFeature(id);
    }
    @ApiAccessNotValidate
    @RequestMapping(value = "/getMjzpkList", method = RequestMethod.GET)
    @ApiOperation(value = "客户端民警照片信息列表查询", notes = "客户端民警照片信息列表查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = MjzpkBean.class)})
    public ApiReturnResult<String> getMjzpkList(HttpServletRequest request, MjzpkParam param) {
        return (ApiReturnResult<String>) apiService.getMjzpkList(param);
    }

    @RequestMapping(value = "/importData",method = RequestMethod.POST)
    @ApiOperation(value = "民警照片库导入", notes = "民警照片库导入")
    @ResponseBody
    public ApiReturnResult<String> importData(HttpServletRequest request, @RequestParam(name="file") CommonsMultipartFile file) {
        ApiReturnResult<String> apiReturnResult = apiService.plupload(request,file);
        return apiReturnResult;
    }

    @RequestMapping(value = "/importDataByBizId",method = RequestMethod.POST)
    @ApiOperation(value = "民警照片库导入(文件服务器)", notes = "民警照片库导入(文件服务器)")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "bizId", value = "文件bizId", required = true, dataType = "string",
            paramType = "query")})
    public ApiReturnResult<String> importDataByBizId(HttpServletRequest request, String bizId) {
        ApiReturnResult<String> apiReturnResult = apiService.pluploadByBizId(request,bizId);
        return apiReturnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/mjzpkReg",method = RequestMethod.GET)
    @ApiOperation(value = "民警注册", notes = "民警注册")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lrrsfzh", value = "用户身份证", dataType = "string", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> mjzpkReg(HttpServletRequest request, MjzpkBean bean,String lrrsfzh) {
        ApiReturnResult<?> apiReturnResult = apiService.mjzpkReg(bean,lrrsfzh);
        return apiReturnResult;
    }

    @RequestMapping(value = "/checkMjZpByBase64",method = RequestMethod.POST)
    @ApiOperation(value = "校验民警照片是否匹配", notes = "校验民警照片是否匹配")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "baqid", value = "办案区id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "base64", value = "人脸base64", required = true, dataType = "String", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> checkMjZpByBase64(HttpServletRequest request, String baqid,String base64) {
        ApiReturnResult<?> apiReturnResult = apiService.getMjxxByImg(baqid,base64);
        return apiReturnResult;
    }

    @ApiAccessNotValidate
    @RequestMapping(value = "/findMjZpBySfzh",method = RequestMethod.GET)
    @ApiOperation(value = "通过身份证查询民警照片库信息", notes = "通过身份证查询民警照片库信息")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "sfzh", value = "身份证号", dataType = "string", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model",response = ApiReturnResult.class)})
    public ApiReturnResult<?> findMjBySfzh(HttpServletRequest request, String sfzh) {
        ApiReturnResult<?> apiReturnResult = apiService.findBySfzh(sfzh);
        return apiReturnResult;
    }
}
