package com.wckj.chasstage.modules.ythcj.dao;

import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYthcjMapper extends IBaseDao<ChasYthcj> {
	void deleteByPrimaryRbyh(String rybh);
    
}