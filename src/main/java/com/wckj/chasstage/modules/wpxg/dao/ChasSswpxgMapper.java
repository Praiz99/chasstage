package com.wckj.chasstage.modules.wpxg.dao;



import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasSswpxgMapper extends IBaseDao<ChasSswpxg> {
	ChasSswpxg findBySbid(Map<String, Object> map);

	int clearByWpg(@Param("sswpgid") String sswpgid);

	List<ChasSswpxg> findUnusedByBaqids(@Param("baqids") List<String> baqids);

	MybatisPageDataResultSet<ChasSswpxg> getWpgPageData(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
}