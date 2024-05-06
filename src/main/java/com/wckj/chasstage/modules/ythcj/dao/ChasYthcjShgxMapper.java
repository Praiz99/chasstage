package com.wckj.chasstage.modules.ythcj.dao;

import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjShgx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYthcjShgxMapper extends IBaseDao<ChasYthcjShgx> {
	void deleteByPrimaryRbyh(String rybh);
}