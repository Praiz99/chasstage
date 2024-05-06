package com.wckj.chasstage.modules.wpxg.service;


import com.wckj.chasstage.modules.wpxg.dao.ChasSswpxgMapper;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasSswpxgService extends IBaseService<ChasSswpxgMapper, ChasSswpxg> {
    Map<String, Object> saveSswpxg(String sswpxgsJson, String delIds, String sswpgId) throws Exception;

    ChasSswpxg findBySbid(Map<String, Object> map);

    void clearByWpg(String wpgid);

    UnusedLockers getUnusedLockers(List<String> baqids);


    PageDataResultSet<ChasSswpxg> getWpgPageData(Integer page, Integer rows, Map<String, Object> params, String lrsj_desc);
}