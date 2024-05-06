package com.wckj.chasstage.api.def.pcry.service;

import com.wckj.chasstage.api.def.pcry.model.PcryBean;
import com.wckj.chasstage.api.def.pcry.model.PcryParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 盘查人员
 */
public interface ApiPcryService extends IApiBaseService {

    ApiReturnResult<?> getPageData(PcryParam param);
    ApiReturnResult<?> saveOrUpdate(PcryBean bean);

    ApiReturnResult<?> deletes(String ids);
    //入所
    ApiReturnResult<?> operRs(String id);

    ApiReturnResult<?> getTaRyxx(String rybh, String ryxm);





}
