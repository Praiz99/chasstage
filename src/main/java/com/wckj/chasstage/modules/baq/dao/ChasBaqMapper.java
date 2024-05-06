package com.wckj.chasstage.modules.baq.dao;


import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasBaqMapper extends IBaseDao<ChasBaq> {

	ChasBaq findByParams(Map<String, Object> map);

	Map<String,String> getStatisticsBy(@Param("baqid") String baqid);

	List<ChasBaq> findListByParams(Map<String, Object> map);

}
