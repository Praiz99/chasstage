package com.wckj.chasstage.modules.wd.service;


import com.wckj.chasstage.modules.wd.dao.ChasWdMapper;
import com.wckj.chasstage.modules.wd.entity.ChasWd;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasWdService extends IBaseService<ChasWdMapper, ChasWd> {
	
	// 高频watchNo查询出对应的低频RealWatchNo
	String queryRealWatchNo(String watchNo);
	
	//低频腕带查询出对应的高频腕带
	String queryHWatchNo(String watchNo);

}
