package com.wckj.chasstage.modules.cssp.service.impl;

import com.wckj.chasstage.modules.cssp.dao.ChasYmCsspMapper;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChasYmCsspServiceImpl extends BaseService<ChasYmCsspMapper, ChasYmCssp> implements ChasYmCsspService {

    private static final Logger log = LoggerFactory.getLogger(ChasYmCsspServiceImpl.class);

}
