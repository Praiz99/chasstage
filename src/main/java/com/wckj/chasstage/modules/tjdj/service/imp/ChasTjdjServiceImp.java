package com.wckj.chasstage.modules.tjdj.service.imp;

import com.wckj.chasstage.modules.tjdj.dao.ChasTjdjMapper;
import com.wckj.chasstage.modules.tjdj.entity.ChasTjdj;
import com.wckj.chasstage.modules.tjdj.service.ChasTjdjService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ChasTjdjServiceImp extends BaseService<ChasTjdjMapper, ChasTjdj> implements ChasTjdjService {
    @Override
    public ChasTjdj getTjdjByRyid(String ryid) {
        return baseDao.getTjdjByRyid(ryid);
    }

    @Override
    public ChasTjdj getTjdjByRybh(String rybh) {
        return baseDao.getTjdjByRybh(rybh);
    }
}
