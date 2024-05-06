package com.wckj.chasstage.modules.ythcj.service;

import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjShgxMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjShgx;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasYthcjShgxService extends IBaseService<ChasYthcjShgxMapper, ChasYthcjShgx> {
	void deleteByPrimaryRbyh(String rybh);
}
