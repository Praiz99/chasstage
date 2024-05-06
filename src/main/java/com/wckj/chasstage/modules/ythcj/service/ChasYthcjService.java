package com.wckj.chasstage.modules.ythcj.service;

import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ChasYthcjService extends IBaseService<ChasYthcjMapper, ChasYthcj> {
	public Map<String,Object> saveYthcjRyxx(ChasYthcj formYthcj,String netJson,String societyJson,String delNetIds,String delSocietyIds,String fromSign) throws Exception;
	public void deleteByPrimaryRbyh(String rybh);
}
