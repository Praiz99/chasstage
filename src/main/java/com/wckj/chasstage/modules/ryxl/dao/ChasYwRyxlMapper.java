package com.wckj.chasstage.modules.ryxl.dao;


import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYwRyxlMapper extends IBaseDao<ChasYwRyxl> {
    ChasYwRyxl getLastestRyxlByRybh(@Param("rybh") String rybh);
}