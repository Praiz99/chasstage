package com.wckj.chasstage.modules.sxdp.dao;

import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasSxdpMapper extends IBaseDao<ChasSxdp> {

	void startMission(ChasSxdp chasSxdp);

	
}