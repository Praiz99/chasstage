package com.wckj.chasstage.modules.syrw.dao;


import com.wckj.chasstage.modules.syrw.entity.ChasSyrw;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasYwSyrwMapper extends IBaseDao<ChasSyrw> {

	void endMission(ChasSyrw chasSyrw);

}
