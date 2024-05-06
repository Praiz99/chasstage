package com.wckj.chasstage.api.def.tjdj.service;

import com.wckj.chasstage.api.def.tjdj.model.TjdjBean;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author wutl
 * @Title: 体检登记接口
 * @Package 体检登记接口
 * @Description: 体检登记接口
 * @date 2020-10-2414:26
 */
public interface ApiTjdjService extends IApiBaseService {
    /**
     * 根据人员编号获取体检登记（如果不存在，则生成默认信息，提供给保存）
     * @param rybh
     * @return
     */
    ApiReturnResult<TjdjBean> getTjdjByRybh(String rybh);

    /**
     * 保存体检登记
     * @param tjdjBean
     * @return
     */
    ApiReturnResult<String> saveTjdj(TjdjBean tjdjBean);

    /**
     * 修改体检登记
     * @param tjdjBean
     * @return
     */
    ApiReturnResult<String> updateTjdj(TjdjBean tjdjBean);
}
