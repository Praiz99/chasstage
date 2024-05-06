package com.wckj.chasstage.modules.ryjl.dao;

import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasRyjlMapper extends IBaseDao<ChasRyjl> {
	public ChasRyjl findByParams(Map<String, Object> map) ;

	public void deleteByPrimaryRbyh(String rybh);

	ChasRyjl findUsedLockerById(@Param("wpgId")String wpgId);
	ChasRyjl findByWdbhL(@Param("baqid") String baqid,@Param("wdbhL") String wdbhL);
	int clearByRybh(@Param("rybh") String rybh);

	ChasRyjl findRyjlByRybhUndel(@Param("baqid")String baqid,@Param("rybh")String rybh);

	ChasRyjl findByWdbhLUndel(@Param("wdbhl")String wdbhl);
}
