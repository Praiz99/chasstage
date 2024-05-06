package com.wckj.chasstage.api.def.spjl.service;

import com.wckj.chasstage.api.def.spjl.model.SpjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author:zengrk
 */
public interface ApiSpjlService  extends IApiBaseService {
    ApiReturnResult<String> getSpjlPageData(SpjlParam spjlParam);
}
