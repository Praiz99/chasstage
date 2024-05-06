package com.wckj.chasstage.api.def.yjxx.service;

import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * 预警信息
 */
public interface ApiYjxxService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(YjxxBean bean);
    ApiReturnResult<?> update(YjxxBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(YjxxParam param);
    ApiReturnResult<?> getBigScreenData(String variables);
    ApiReturnResult<?> getXsBigScreenData(String variables);
    void saveAlarm(String qyid, HttpServletRequest request, String baqid);
}
