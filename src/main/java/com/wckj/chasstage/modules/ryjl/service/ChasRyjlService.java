package com.wckj.chasstage.modules.ryjl.service;

import com.wckj.chasstage.modules.ryjl.dao.ChasRyjlMapper;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

/**
 * @author {XL-SaiAren}
 * @date 创建时间：2017年11月21日 下午1:55:34
 * @version V2.0.0 类说明
 */
public interface ChasRyjlService extends IBaseService<ChasRyjlMapper, ChasRyjl> {

	Map<String,Object> saveOrUp(ChasRyjl chasRyjl) throws Exception;

	ChasRyjl findByParams(Map<String, Object> map);

	void deleteByPrimaryRbyh(String rybh);

	ChasRyjl findRyjlByRybh(String baqid, String rybh);

	ChasRyjl findUsedLockerById(String wpgId);
	ChasRyjl findByWdbhL(String baqid,String wdbhL);
	int clearByRybh(String rybh);

	ChasRyjl findRyjlByRybhUndel(String baqid,String rybh);

	ChasRyjl findByWdbhLUndel(String wdbhl);

	ChasRyjl findRyjlByRybh(String baqid, String rybh, String s);
}
