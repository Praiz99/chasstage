package com.wckj.chasstage.modules.pcry.service;


import com.wckj.chasstage.api.def.pcry.model.PcryBean;
import com.wckj.chasstage.modules.pcry.dao.ChasYwPcryMapper;
import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ChasYwPcryService extends IBaseService<ChasYwPcryMapper, ChasYwPcry> {
    int delete(String[] idstr);

    Map<String, Object> saveOrupdate(PcryBean bean);
//	public ChasYthcj getRyxxFormPcry(String rybh);

	Map<String,Object> ryrsxx(String id);
    Map<String,Object> getTaryxx(String rybh,String ryxm);


    Map<String, Object> selectAll(Integer pageNo, Integer pageSize, Map<String, Object> param, String orderBy);

    ChasYwPcry findByRybh(String rybh);
}
