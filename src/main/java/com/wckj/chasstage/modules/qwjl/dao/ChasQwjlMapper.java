package com.wckj.chasstage.modules.qwjl.dao;


import com.wckj.chasstage.modules.qwjl.entity.ChasQwjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasQwjlMapper extends IBaseDao<ChasQwjl> {
	public void deleteByPrimaryRbyh(String rybh);

	MybatisPageDataResultSet<Map<String,Object>> getQwjlApiPageData(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
}