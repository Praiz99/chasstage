package com.wckj.chasstage.modules.tjdj.dao;

import com.wckj.chasstage.modules.tjdj.entity.ChasTjdj;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasTjdjMapper extends IBaseDao<ChasTjdj> {

    ChasTjdj getTjdjByRyid(@Param("ryid")String ryid);

    ChasTjdj getTjdjByRybh(@Param("rybh")String rybh);
}
