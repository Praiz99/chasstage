package com.wckj.chasstage.modules.ythcj.service.impl;

import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjWlxxMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjWlxx;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjWlxxService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ChasYthcjWlxxServiceImpl extends BaseService<ChasYthcjWlxxMapper, ChasYthcjWlxx> implements ChasYthcjWlxxService {

	@Override
	public void deleteByPrimaryRbyh(String rybh) {
		baseDao.deleteByPrimaryRbyh(rybh);
	}
}
