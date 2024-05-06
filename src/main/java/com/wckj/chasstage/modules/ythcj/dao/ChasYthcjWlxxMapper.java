package com.wckj.chasstage.modules.ythcj.dao;

import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjWlxx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYthcjWlxxMapper extends IBaseDao<ChasYthcjWlxx> {

	void deleteByPrimaryRbyh(String rybh);
	
}