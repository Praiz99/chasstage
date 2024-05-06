package com.wckj.chasstage.modules.clcrjl.service;


import com.wckj.chasstage.modules.clcrjl.dao.ChasYwClcrjlMapper;
import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwClcrjlService extends IBaseService<ChasYwClcrjlMapper, ChasYwClcrjl> {
    String delete(String[] idstr);
    ChasYwClcrjl findByParams(Map<String, Object> map);
    Map<String, Object> saveOrUpdate(ChasYwClcrjl crjl) throws Exception;
}
