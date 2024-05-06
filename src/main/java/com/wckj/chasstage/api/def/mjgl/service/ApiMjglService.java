package com.wckj.chasstage.api.def.mjgl.service;

import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.mjgl.model.MjglBean;
import com.wckj.chasstage.api.def.mjgl.model.MjglParam;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import java.util.Map;

/**
 * 民警管理
 */
public interface ApiMjglService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(MjglBean bean);
    ApiReturnResult<?> update(MjglBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(MjglParam param);
    ApiReturnResult<?> leave(String id);

    /**
     * 根据腕带编号获取民警信息
     * @param wdbhl
     * @return
     */
    ApiReturnResult<?> getMjxxByWdbhl(String wdbhl);
}
