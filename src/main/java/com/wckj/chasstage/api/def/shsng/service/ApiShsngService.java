package com.wckj.chasstage.api.def.shsng.service;

import com.wckj.chasstage.api.def.shsng.model.ShsngParam;
import com.wckj.chasstage.api.def.wpgl.model.SswpParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author:zengrk
 */
public interface ApiShsngService extends IApiBaseService {
    ApiReturnResult<String> getShsngPageData(ShsngParam param);

    ApiReturnResult<String> getSngNum();

    ApiReturnResult<String> yjcl();

    ApiReturnResult<String> sdgh(String id);
}
