package com.wckj.chasstage.modules.jhrwjl.dao;

import com.wckj.chasstage.modules.jhrwjl.entity.ChasYwJhrwjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChasYwJhrwjlMapper extends IBaseDao<ChasYwJhrwjl> {

    List<ChasYwJhrwjl> getJhrwjlByRybh(String rybh);

    List<ChasYwJhrwjl> getJhrwjlByJhrwbhAndRwzt(@Param("jhrwbh") String jhrwbh, @Param("rwzt") String rwzt);
}
