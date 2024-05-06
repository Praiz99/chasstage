package com.wckj.chasstage.modules.rygj.service;


import com.wckj.chasstage.modules.rygj.dao.ChasRygjSnapMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasYwRygjSnapService extends IBaseService<ChasRygjSnapMapper, ChasRygjSnap> {
    int deleteByRybh(String rybh);
}
