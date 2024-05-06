package com.wckj.chasstage.modules.jjrw.service.impl;

import com.wckj.chasstage.modules.jjrw.dao.ChasYwJjrwMapper;
import com.wckj.chasstage.modules.jjrw.entity.ChasYwJjrw;
import com.wckj.chasstage.modules.jjrw.service.ChasYwJjrwService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChasYwJjrwServiceImpl extends BaseService<ChasYwJjrwMapper, ChasYwJjrw> implements ChasYwJjrwService {
	protected Logger log = LoggerFactory.getLogger(ChasYwJjrwServiceImpl.class);

}
