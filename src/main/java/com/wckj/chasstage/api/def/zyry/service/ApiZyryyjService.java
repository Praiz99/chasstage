package com.wckj.chasstage.api.def.zyry.service;

import com.wckj.chasstage.api.def.zyry.model.ZyryParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @Author: QYT
 * @Date: 2023/6/16 9:24 上午
 * @Description:在押人员预警service
 */
public interface ApiZyryyjService extends IApiBaseService {

    ApiReturnResult<String> pushZyryAlarm(ZyryParam zyryParam);
}
