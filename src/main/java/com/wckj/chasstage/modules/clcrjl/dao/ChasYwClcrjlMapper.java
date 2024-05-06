package com.wckj.chasstage.modules.clcrjl.dao;


import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasYwClcrjlMapper extends IBaseDao<ChasYwClcrjl> {
    ChasYwClcrjl findByParams(Map<String, Object> map);
}