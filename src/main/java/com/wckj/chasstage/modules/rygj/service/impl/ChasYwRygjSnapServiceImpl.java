package com.wckj.chasstage.modules.rygj.service.impl;


import com.wckj.chasstage.modules.rygj.dao.ChasRygjSnapMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ChasYwRygjSnapServiceImpl extends BaseService<ChasRygjSnapMapper, ChasRygjSnap> implements ChasYwRygjSnapService {
    @Override
    public int deleteByRybh(String rybh) {
        if(StringUtils.isNotEmpty(rybh)){
            return baseDao.deleteByRybh(rybh);
        }
        return 0;
    }
}
