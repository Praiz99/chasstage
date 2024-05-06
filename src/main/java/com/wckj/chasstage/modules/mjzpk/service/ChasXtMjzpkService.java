package com.wckj.chasstage.modules.mjzpk.service;


import com.wckj.chasstage.modules.mjzpk.dao.ChasXtMjzpkMapper;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasXtMjzpkService extends IBaseService<ChasXtMjzpkMapper, ChasXtMjzpk> {

	List<ChasXtMjzpk> findByParams(Map<String, Object> map);
	String delete(String[] ids);
	Map<String, Object> saveOrUpdate(ChasXtMjzpk mjzpk) throws Exception;
	List<ChasXtMjzpk> findDelMjxx();
	ChasXtMjzpk findBySfzh(String sfzh);
}