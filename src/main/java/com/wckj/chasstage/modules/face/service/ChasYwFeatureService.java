package com.wckj.chasstage.modules.face.service;


import com.wckj.chasstage.modules.face.dao.ChasYwFeatureMapper;
import com.wckj.chasstage.modules.face.entity.ChasYwFeature;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwFeatureService extends IBaseService<ChasYwFeatureMapper, ChasYwFeature> {
    /**
     * 查询所有（保护已删除的数据）
     * @param pageNo
     * @param pageSize
     * @param map
     * @param order
     * @return
     */
    PageDataResultSet<ChasYwFeature> getAllPageData(int pageNo, int pageSize, Map<String, Object> map, String order);
}
