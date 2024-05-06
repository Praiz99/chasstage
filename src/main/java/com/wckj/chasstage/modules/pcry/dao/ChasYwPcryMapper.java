package com.wckj.chasstage.modules.pcry.dao;


import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYwPcryMapper extends IBaseDao<ChasYwPcry> {

    ChasYwPcry findByRybh(@Param("rybh")String rybh);
}