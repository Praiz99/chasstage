package com.wckj.chasstage.modules.znpz.dao;

import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasXtBaqznpzMapper extends IBaseDao<ChasXtBaqznpz> {

    ChasXtBaqznpz findByBaqid(@Param("baqid") String baqid);
}