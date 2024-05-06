package com.wckj.chasstage.api.def.sxsgbjl.service;

import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlBean;
import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlParam;
import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 审讯室关闭记录
 */
public interface ApiSxsgbjlService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(SxsgbjlBean bean);
    ApiReturnResult<?> update(SxsgbjlBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(SxsgbjlParam param);
    ApiReturnResult<?> getSyjlPageData(SxsgbjlParam param);
}
