package com.wckj.chasstage.api.def.cldj.service;

import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.chasstage.api.def.cldj.model.ClunbindParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 车辆登记
 */
public interface ApiCldjService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(CldjBean bean);
    ApiReturnResult<?> update(CldjBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(CldjParam param);
    ApiReturnResult<?> getUsableSycl(CldjParam param);
    ApiReturnResult<?> deleteSycl(ClunbindParam param);

    /**
     * 根据id解绑使用车辆
     * @param id 车辆id
     * @return
     */
    ApiReturnResult<?> unBindSycl(String id);

    /**
     * 根据车牌号解绑使用车辆
     * @param baqid 办案区id
     * @param cphm 车牌号
     * @param crlx 车辆类型
     * @return
     */
    ApiReturnResult<?> unBindSyclBycph(String baqid,String cphm,String crlx);
}
