package com.wckj.chasstage.modules.ythcj.service;

import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjWlxxMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjWlxx;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasYthcjWlxxService extends IBaseService<ChasYthcjWlxxMapper, ChasYthcjWlxx> {
	void deleteByPrimaryRbyh(String rybh);
}
