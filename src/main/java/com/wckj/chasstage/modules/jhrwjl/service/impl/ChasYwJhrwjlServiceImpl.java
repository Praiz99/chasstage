package com.wckj.chasstage.modules.jhrwjl.service.impl;

import com.wckj.chasstage.modules.jhrwjl.dao.ChasYwJhrwjlMapper;
import com.wckj.chasstage.modules.jhrwjl.entity.ChasYwJhrwjl;
import com.wckj.chasstage.modules.jhrwjl.service.ChasYwJhrwjlService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChasYwJhrwjlServiceImpl extends BaseService<ChasYwJhrwjlMapper, ChasYwJhrwjl> implements ChasYwJhrwjlService {
	protected Logger log = LoggerFactory.getLogger(ChasYwJhrwjlServiceImpl.class);

	@Override
	public List<ChasYwJhrwjl> getJhrwjlByRybh(String rybh) {
		return baseDao.getJhrwjlByRybh(rybh);
	}

	@Override
	public List<ChasYwJhrwjl> getJhrwjlByJhrwbhAndRwzt(String jhrwbh, String rwzt) {
		return baseDao.getJhrwjlByJhrwbhAndRwzt(jhrwbh,rwzt);
	}
}
