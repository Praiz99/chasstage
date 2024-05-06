package com.wckj.chasstage.modules.syrw.service.impl;

import com.wckj.chasstage.modules.syrw.dao.ChasYwSyrwMapper;
import com.wckj.chasstage.modules.syrw.entity.ChasSyrw;
import com.wckj.chasstage.modules.syrw.service.ChasYwSyrwService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ChasYwSyrwServiceImpl extends BaseService<ChasYwSyrwMapper, ChasSyrw> implements ChasYwSyrwService {

	@Override
	public Map<String, Object> missionStart(ChasSyrw chasSyrw) {
		// TODO Auto-generated method stub
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			chasSyrw.setCreateTime(new Date());
			chasSyrw.setId(StringUtils.getGuid32());
			chasSyrw.setSyzt("1");
			baseDao.insert(chasSyrw);
			res.put("code", "0");
			res.put("msg", "创建送押任务成功！");
			} catch (Exception e) {
				res.put("code", "-1");
				res.put("msg", e.getMessage());
		}
		return res;
	}

	@Override
	public void endMission(ChasSyrw chasSyrw) {
		baseDao.endMission(chasSyrw);
	}

	
}
