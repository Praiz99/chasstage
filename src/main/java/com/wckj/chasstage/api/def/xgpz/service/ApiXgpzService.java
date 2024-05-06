package com.wckj.chasstage.api.def.xgpz.service;

import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 巡更配置
 */
public interface ApiXgpzService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(XgpzBean bean);
    ApiReturnResult<?> update(XgpzBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(XgpzParam param);

}
