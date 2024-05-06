package com.wckj.chasstage.modules.face.dao;


import com.wckj.chasstage.modules.face.entity.ChasYwFeature;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasYwFeatureMapper extends IBaseDao<ChasYwFeature> {

    /**
     * 查询所有 包含已删除
     * @param pageNo
     * @param pageSize
     * @param param
     * @param  orderBy
     * @return
     */
    MybatisPageDataResultSet<ChasYwFeature> selectAllInAll(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize, @Param("param") Map<String, Object> param, @Param("orderBy")String orderBy);

}
