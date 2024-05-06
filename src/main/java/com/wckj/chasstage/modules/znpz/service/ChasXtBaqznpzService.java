package com.wckj.chasstage.modules.znpz.service;

import com.wckj.chasstage.api.def.baq.model.BaqznpzBean;
import com.wckj.chasstage.modules.znpz.dao.ChasXtBaqznpzMapper;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasXtBaqznpzService extends IBaseService<ChasXtBaqznpzMapper, ChasXtBaqznpz> {

    BaqConfiguration findByBaqid(String baqid);

    ChasXtBaqznpz findByBaqid2(String baqid);

    void saveBaqznpz(BaqznpzBean obj) throws Exception;

    Map<?,?> getBaqznpzPageData(int page, int rows, Map<String,Object> params, String order);
}
