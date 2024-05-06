package com.wckj.chasstage.modules.jhry.service;



import com.wckj.chasstage.modules.jhry.dao.ChasYwJhryMapper;
import com.wckj.chasstage.modules.jhry.entity.ChasYwJhry;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;

public interface ChasYwJhryService extends IBaseService<ChasYwJhryMapper, ChasYwJhry> {
    List<ChasYwJhry> getJhryByJhrwbh(String id);
}
