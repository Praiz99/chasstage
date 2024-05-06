package com.wckj.chasstage.modules.signconfig.dao;

import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasXtSignConfigMapper extends IBaseDao<ChasXtSignConfig> {

    ChasXtSignConfig findByBaqid(@Param("baqid") String baqid);
}