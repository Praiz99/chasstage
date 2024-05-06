package com.wckj.chasstage.api.def.yjlb.service;

import com.wckj.chasstage.api.def.baq.model.BaqBean;
import com.wckj.chasstage.api.def.baq.model.BaqParam;
import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 预警类别
 */
public interface ApiYjlbService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(YjlbBean bean);
    ApiReturnResult<?> update(YjlbBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(YjlbParam param);

}
