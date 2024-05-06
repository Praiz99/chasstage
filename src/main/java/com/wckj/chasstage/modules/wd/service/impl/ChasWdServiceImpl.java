package com.wckj.chasstage.modules.wd.service.impl;


import com.wckj.chasstage.modules.wd.dao.ChasWdMapper;
import com.wckj.chasstage.modules.wd.entity.ChasWd;
import com.wckj.chasstage.modules.wd.service.ChasWdService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasWdServiceImpl extends BaseService<ChasWdMapper, ChasWd> implements ChasWdService {
	
	// 高频watchNo查询出对应的低频RealWatchNo
	public String queryRealWatchNo(String watchNo){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("wdbhH", watchNo);
		if(StringUtils.isNotEmpty(watchNo)){
			List<ChasWd> wds=this.findList(params, null);
			if(!wds.isEmpty()){
				return wds.get(0).getWdbhL();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	//低频腕带查询出高频腕带
	public String queryHWatchNo(String watchNo){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("wdbhL", watchNo);
		if(StringUtils.isNotEmpty(watchNo)){
			List<ChasWd> wds=this.findList(params, null);
			if(!wds.isEmpty()){
				return wds.get(0).getWdbhH();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

}
