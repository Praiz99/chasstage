package com.wckj.chasstage.modules.yy.dao;

import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasYwYyMapper extends IBaseDao<ChasYwYy> {
    List<ChasYwYy> findByParams(Map<String, Object> map);

    /**
     * 分页查询预约信息，关联民警照片库
     * @param pageNo
     * @param pageSize
     * @param param
     * @param orderBy
     * @return
     */
   // MybatisPageDataResultSet<ChasYwYy> getYyxxAndMjzpkPageData(@Param("pageNo")  int pageNo, @Param("pageSize")int pageSize, @Param("param")Map<String, Object> param, @Param("orderBy") String orderBy);
}
