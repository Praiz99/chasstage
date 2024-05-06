package com.wckj.chasstage.api.def.yybb.service;

import com.wckj.chasstage.api.def.yybb.model.YybbParam;
import com.wckj.chasstage.api.def.yybb.model.YybbSendParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author wutl
 * @Title: 语音播报服务
 * @Package
 * @Description:
 * @date 2020-12-2211:20
 */
public interface ApiYybbService extends IApiBaseService {

    ApiReturnResult<String> save(YybbParam yybbParam);

    ApiReturnResult<String> update(YybbParam yybbParam);

    ApiReturnResult<String> delete(String id);

   ApiReturnResult<?> findList(int page, int rows, YybbParam yybbParam, String order);
}
