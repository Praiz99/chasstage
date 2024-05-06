package com.wckj.chasstage.modules.gnpz.dao;

import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChasXtGnpzMapper extends IBaseDao<ChasXtGnpz> {

    ChasXtGnpz findByBaqid(@Param("baqid")String baqid);
}
