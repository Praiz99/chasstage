package com.wckj.chasstage.modules.wpg.service;



import com.wckj.chasstage.modules.wpg.dao.ChasSswpgMapper;
import com.wckj.chasstage.modules.wpg.entity.ChasSswpg;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasSswpgService extends IBaseService<ChasSswpgMapper, ChasSswpg> {
	Map<String, Object> saveOrUpdate(ChasSswpg sswpg, String id) throws Exception;
}
