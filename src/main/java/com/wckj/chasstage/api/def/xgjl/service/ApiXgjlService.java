package com.wckj.chasstage.api.def.xgjl.service;

import com.wckj.chasstage.api.def.xgjl.model.SdXgBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlParam;
import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 巡更记录
 */
public interface ApiXgjlService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(XgjlBean bean);
    ApiReturnResult<?> update(XgjlBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(XgjlParam param);
    ApiReturnResult<?> sddk(SdXgBean bean);
}
