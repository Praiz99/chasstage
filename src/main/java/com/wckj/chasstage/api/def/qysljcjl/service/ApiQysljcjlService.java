package com.wckj.chasstage.api.def.qysljcjl.service;

import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import javax.servlet.http.HttpServletRequest;

public interface ApiQysljcjlService extends IApiBaseService {
    ApiReturnResult<?> findQyjcjl(HttpServletRequest request);

    ApiReturnResult<?> saveQysljcjl(HttpServletRequest request);
}
