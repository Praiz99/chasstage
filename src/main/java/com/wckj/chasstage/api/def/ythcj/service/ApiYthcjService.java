package com.wckj.chasstage.api.def.ythcj.service;

import com.wckj.chasstage.api.def.ythcj.model.YthcjParam;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

public interface ApiYthcjService extends IApiBaseService {

    ApiReturnResult<String> saveYthcjRyxx(ChasYthcj ythcj,YthcjParam param);

    ApiReturnResult<?> getYthcjRyxx(String rybh);
}
