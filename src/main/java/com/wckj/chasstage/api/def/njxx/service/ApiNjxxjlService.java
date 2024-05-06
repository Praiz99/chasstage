package com.wckj.chasstage.api.def.njxx.service;

import com.wckj.chasstage.api.def.njxx.bean.NjxxjlBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author wutl
 * @Title: 尿检信息服务
 * @Package 尿检信息服务
 * @Description: 尿检信息服务
 * @date 2020-9-1616:25
 */
public interface ApiNjxxjlService extends IApiBaseService {
    ApiReturnResult<String> getPageData(NjxxjlParam param);

    ApiReturnResult<String> saveOrUpdate(NjxxjlBean bean);

    ApiReturnResult<String> findNjjlById(String id);



}
