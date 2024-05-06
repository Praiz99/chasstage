package com.wckj.chasstage.modules.jhry.service.impl;



import com.wckj.chasstage.modules.jhry.dao.ChasYwJhryMapper;
import com.wckj.chasstage.modules.jhry.entity.ChasYwJhry;
import com.wckj.chasstage.modules.jhry.service.ChasYwJhryService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChasYwJhryServiceImpl extends BaseService<ChasYwJhryMapper, ChasYwJhry> implements ChasYwJhryService {
	protected Logger log = LoggerFactory.getLogger(ChasYwJhryServiceImpl.class);

	@Override
	public List<ChasYwJhry> getJhryByJhrwbh(String jhrwbh) {
		return baseDao.getJhryByJhrwbh(jhrwbh);
	}
}
