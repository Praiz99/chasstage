package com.wckj.chasstage.modules.gnpz.service.impl;

import com.wckj.chasstage.modules.gnpz.dao.ChasXtGnpzMapper;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wutl
 * @Title: 功能配置服务
 * @Package 功能配置服务
 * @Description: 功能配置服务
 * @date 2020-10-11 13:53
 */
@Service
public class ChasXtGnpzServiceImp extends BaseService<ChasXtGnpzMapper, ChasXtGnpz> implements ChasXtGnpzService {
    @Override
    public ChasXtGnpz findByBaqid(String baqid) {
        return baseDao.findByBaqid(baqid);
    }
}
