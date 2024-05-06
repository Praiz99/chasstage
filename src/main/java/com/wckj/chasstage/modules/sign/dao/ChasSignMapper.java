package com.wckj.chasstage.modules.sign.dao;

import com.wckj.chasstage.modules.sign.entity.ChasSign;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasSignMapper extends IBaseDao<ChasSign> {

    int deleteByPrimaryRybh(@Param("rybh") String rybh, @Param("signType") String signType);

    ChasSign findByType(@Param("type")String type,@Param("rybh")String rybh);
}
