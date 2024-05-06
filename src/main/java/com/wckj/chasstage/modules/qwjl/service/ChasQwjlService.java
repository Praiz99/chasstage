package com.wckj.chasstage.modules.qwjl.service;


import com.wckj.chasstage.modules.qwjl.dao.ChasQwjlMapper;
import com.wckj.chasstage.modules.qwjl.entity.ChasQwjl;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasQwjlService extends IBaseService<ChasQwjlMapper, ChasQwjl> {
	public void deleteByPrimaryRbyh(String rybh);

	PageDataResultSet<Map<String,Object>> getQwjlApiPageData(int pageNo, int pageSize, Object param, String orderBy);

	Map<String,Object> saveQwjl(String cabId, String mjsfz, String czlx) throws Exception;
}
