package com.wckj.chasstage.modules.ythcj.service.impl;

import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjShgxMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjShgx;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjShgxService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ChasYthcjShgxServiceImpl extends BaseService<ChasYthcjShgxMapper, ChasYthcjShgx> implements ChasYthcjShgxService {

	@Override
	public void deleteByPrimaryRbyh(String rybh) {
		baseDao.deleteByPrimaryRbyh(rybh);
	}
}
