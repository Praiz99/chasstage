package com.wckj.chasstage.modules.mjgl.dao;

import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasYwMjrqMapper extends IBaseDao<ChasYwMjrq> {

    ChasYwMjrq findByParams(Map<String, Object> map);
    ChasYwMjrq isMjzq(@Param("mjsfzh") String sfzh);
}