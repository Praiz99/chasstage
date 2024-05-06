package com.wckj.chasstage.modules.yy.service;

import com.wckj.chasstage.modules.yy.dao.ChasYwYyMapper;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasYwYyService extends IBaseService<ChasYwYyMapper, ChasYwYy> {
    List<ChasYwYy> findByParams(Map<String, Object> map);
    String delete(String[] ids);
    Map<String, Object> saveOrUpdate(ChasYwYy mjzpk) throws Exception;
}
