package com.wckj.chasstage.modules.yjxx.service.impl;



import com.wckj.chasstage.modules.yjxx.dao.ChasYjxxMapper;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChasYjxxServiceImpl extends BaseService<ChasYjxxMapper, ChasYjxx> implements ChasYjxxService {

    @Override
    public List<ChasYjxx> findXzYjxx(String baqid) {
        return baseDao.findXzYjxx(baqid);
    }

    @Override
    public List<ChasYjxx> findXsYjxx(String baqid) {
        return baseDao.findXsYjxx(baqid);
    }
}
