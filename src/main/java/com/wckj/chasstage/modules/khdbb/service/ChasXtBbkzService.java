package com.wckj.chasstage.modules.khdbb.service;

import com.wckj.chasstage.modules.khdbb.dao.ChasXtBbkzMapper;
import com.wckj.chasstage.modules.khdbb.entity.ChasXtBbkz;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasXtBbkzService extends IBaseService<ChasXtBbkzMapper, ChasXtBbkz> {

    public Map<String,Object> saveOrUpdate(String id,ChasXtBbkz bbkz);

    public Map<String,Object> deleteClient(String[] ids);

    Map<String,Object> getClientVersion(String verisonNo,String clientType);
}
