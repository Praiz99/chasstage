package com.wckj.chasstage.modules.qqdh.dao;



import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasQqyzMapper extends IBaseDao<ChasQqyz> {

	List<ChasQqyz> findbyparams(Map<String, Object> map);

	int deleteByPrimaryRbyh(String rybh);

}