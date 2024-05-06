package com.wckj.chasstage.api.def.clsyjl.service;

import com.wckj.chasstage.api.def.clsyjl.model.AddClsyjlParam;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlBean;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 车辆使用记录
 */
public interface ApiClsyjlService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(AddClsyjlParam bean);
    ApiReturnResult<?> update(ClsyjlBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(ClsyjlParam param);
    ApiReturnResult<?> getBindCarByRybh(ClsyjlParam param);
}
