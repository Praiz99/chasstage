package com.wckj.chasstage.api.def.dhssyjl.service;

import com.wckj.chasstage.api.def.dhssyjl.model.DhssyjlParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author:zengrk
 * 等候室使用记录
 */

public interface ApiDhssyjService  extends IApiBaseService {
    //办案区等候室分配情况
    ApiReturnResult<?> getDhssyjlPageData(DhssyjlParam Param);
}
