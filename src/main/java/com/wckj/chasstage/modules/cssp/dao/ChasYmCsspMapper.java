package com.wckj.chasstage.modules.cssp.dao;


import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYmCsspMapper extends IBaseDao<ChasYmCssp> {
	public void deleteByPrimaryRybh(String rybh);
}