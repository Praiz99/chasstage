package com.wckj.chasstage.api.def.njxx.service;

import com.wckj.chasstage.api.def.njxx.bean.NjxxBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author wutl
 * @Title: 尿检信息服务
 * @Package 尿检信息服务
 * @Description: 尿检信息服务
 * @date 2020-9-1616:25
 */
public interface ApiNjxxService extends IApiBaseService {
    ApiReturnResult<String> getPageData(NjxxParam param);

    ApiReturnResult<String> saveOrUpdate(NjxxBean bean);
    ApiReturnResult<String> deletes(String ids);

    ApiReturnResult<?> getNjPdfBase64(String njid,String type);


    ApiReturnResult<String> findNjxxId(String id);



}
