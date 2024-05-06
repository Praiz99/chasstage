package com.wckj.chasstage.api.def.fkgl.service;

import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.xgjl.model.XgjlBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 访客管理
 */
public interface ApiFkglService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(FkglBean bean);
    ApiReturnResult<?> update(FkglBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(FkglParam param);
    ApiReturnResult<?> leave(String id);
}
