package com.wckj.chasstage.api.def.clcrjl.service;

import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlBean;
import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlParam;
import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 车辆出入记录
 */
public interface ApiClcrjlService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(ClcrjlBean bean);
    ApiReturnResult<?> update(ClcrjlBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(ClcrjlParam param);

}
