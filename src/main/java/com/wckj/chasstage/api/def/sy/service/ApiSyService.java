package com.wckj.chasstage.api.def.sy.service;

import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.stereotype.Service;

public interface ApiSyService extends IApiBaseService {

    ApiReturnResult<?> getStatisticsDataByBaqid(String baqid);
}
