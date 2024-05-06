package com.wckj.chasstage.modules.face.service.impl;


import com.wckj.chasstage.modules.face.dao.ChasYwFeatureMapper;
import com.wckj.chasstage.modules.face.entity.ChasYwFeature;
import com.wckj.chasstage.modules.face.service.ChasYwFeatureService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChasYwFeatureServiceImpl extends BaseService<ChasYwFeatureMapper, ChasYwFeature> implements ChasYwFeatureService {

    @Autowired ChasYwFeatureMapper featureMapper;

    /**
     * 查询所有（保护已删除的数据）
     * @param pageNo
     * @param pageSize
     * @param map
     * @param order
     * @return
     */
    @Override
    public PageDataResultSet<ChasYwFeature> getAllPageData(int pageNo, int pageSize, Map<String, Object> map, String order) {
        MybatisPageDataResultSet<ChasYwFeature> chasYwFeatures = featureMapper.selectAllInAll(pageNo, pageSize, map, order);
        return chasYwFeatures.convert2PageDataResultSet();
    }
}
