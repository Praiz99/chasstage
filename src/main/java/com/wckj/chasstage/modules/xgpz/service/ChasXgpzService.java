package com.wckj.chasstage.modules.xgpz.service;


import com.wckj.chasstage.modules.xgpz.dao.ChasXgpzMapper;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasXgpzService extends IBaseService<ChasXgpzMapper, ChasXgpz> {

    void SaveWithUpdate(ChasXgpz xgpz)throws Exception;
}
