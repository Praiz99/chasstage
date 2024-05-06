package com.wckj.chasstage.modules.wpg.service.impl;



import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.wpg.dao.ChasSswpgMapper;
import com.wckj.chasstage.modules.wpg.entity.ChasSswpg;
import com.wckj.chasstage.modules.wpg.service.ChasSswpgService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChasSswpgServiceImpl extends BaseService<ChasSswpgMapper, ChasSswpg> implements ChasSswpgService {
	protected Logger log = LoggerFactory.getLogger(ChasSswpgServiceImpl.class);
	@Override
	public Map<String, Object> saveOrUpdate(ChasSswpg sswpg,String id) throws Exception {
		String idCard = WebContext.getSessionUser().getIdCard();
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(id)){ //新增
				sswpg.setLrrSfzh(idCard);
				sswpg.setXgrSfzh(idCard);
				sswpg.setIsdel(SYSCONSTANT.N_I);
				sswpg.setDataflag(SYSCONSTANT.N);
				sswpg.setId(StringUtils.getGuid32());
				save(sswpg);
				result.put("success", true);
				result.put("msg", "保存成功!");
			}else{//修改
				sswpg.setXgrSfzh(idCard);
				ChasSswpg chasSswpg = findById(id);
				MyBeanUtils.copyBeanNotNull2Bean(sswpg, chasSswpg);
				update(chasSswpg);
				result.put("success", true);
				result.put("msg", "修改成功!");
			}
		} catch (Exception e) {
			log.error("saveOrUpdate:",e);
			result.put("success", false);
			result.put("msg", "系统异常:"+e.getMessage());
			throw e;
		}
		return result;
	}
}
