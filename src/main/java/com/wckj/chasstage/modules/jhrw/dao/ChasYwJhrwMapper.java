package com.wckj.chasstage.modules.jhrw.dao;

import com.wckj.chasstage.modules.jhrw.entity.ChasYwJhrw;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasYwJhrwMapper extends IBaseDao<ChasYwJhrw> {

    List<ChasYwJhrw> getJhrwByRybhAndRwlx(@Param("rybh") String rybh, @Param("rwlx") String rwlx);

    PageDataResultSet<ChasYwJhrw> getDpData(int page, int rows, Map<String, Object> params);

    List<ChasYwJhrw> getJhrwByrwzt();

    List<ChasYwJhrw> getJhrwByrwzt(String rwzt);
}
