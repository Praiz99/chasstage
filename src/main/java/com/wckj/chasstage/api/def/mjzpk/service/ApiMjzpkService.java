package com.wckj.chasstage.api.def.mjzpk.service;

import com.wckj.chasstage.api.def.mjzpk.model.MjzpkBean;
import com.wckj.chasstage.api.def.mjzpk.model.MjzpkParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 民警照片库管理
 */
public interface ApiMjzpkService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(MjzpkBean bean);
    ApiReturnResult<?> update(MjzpkBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(MjzpkParam param);
    ApiReturnResult<?> genFeature(String id);
    ApiReturnResult<?> getMjzpkList(MjzpkParam param);

    /**
     * 批量导入民警照片库
     * @param request
     * @param file
     * @return
     */
    ApiReturnResult<String> plupload(HttpServletRequest request, CommonsMultipartFile file);

    /**
     * 根据文件bizId上传民警照片库
     * @param request
     * @param bizId
     * @return
     */
    ApiReturnResult<String> pluploadByBizId(HttpServletRequest request, String bizId);

    ApiReturnResult<?> mjzpkReg(MjzpkBean bean,String lrrsfzh);

    ApiReturnResult<?> getMjxxByImg(String baqid,String base64);

    ApiReturnResult<?> findBySfzh(String sfzh);
}
