package com.wckj.chasstage.modules.baq.service;

import com.wckj.chasstage.modules.baq.dao.ChasBaqrefMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasBaqrefService extends IBaseService<ChasBaqrefMapper, ChasBaqref> {

    void saveBaqRef(ChasBaqref baqref) throws Exception;

    void saveBaqRef(Object baqref) throws Exception;

    Map<String, Object> saveBatch(String baqid, String orgs);

    Map<?, ?> getBaqrefPageData(int page, int rows, Map<String, String> params, String order);

    void deleteByids(String ids);

    String findBaqId();

    String getBaqidByUser();
}
