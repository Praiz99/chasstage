package com.wckj.chasstage.modules.fkgl.dao;

import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYwFkdjMapper extends IBaseDao<ChasYwFkdj> {

    ChasYwFkdj isFkzq(@Param("mjsfzh") String sfzh);
}