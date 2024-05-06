package com.wckj.chasstage.modules.dhssyjl.dao;


import com.wckj.chasstage.modules.dhssyjl.entity.ChasYwDhssyjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface chasYwDhssyjlMapper  extends IBaseDao<ChasYwDhssyjl> {
    ChasYwDhssyjl getDhssyjlByRybh(@Param("rybh") String rybh);
}