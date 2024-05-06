package com.wckj.chasstage.modules.khdbb.dao;

import com.wckj.chasstage.modules.khdbb.entity.ChasXtBbkz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasXtBbkzMapper extends IBaseDao<ChasXtBbkz> {

    public void updateByClientType(@Param("clientType")String clientType);
}