package com.wckj.chasstage.modules.wszh.service;

import com.wckj.chasstage.api.def.baq.model.WszhBean;
import com.wckj.chasstage.modules.wszh.dao.ChasWszhMapper;
import com.wckj.chasstage.modules.wszh.entity.ChasWszh;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasWszhService extends IBaseService<ChasWszhMapper, ChasWszh> {

    void SaveWithUpdate(ChasWszh wszh)throws Exception;

    void SaveWithUpdate(WszhBean wszh)throws Exception;

    void deleteByids(String ids) throws Exception;

    Map<?,?> getWszhPageData(int page, int rows, Map<String,Object> params, String order);


}
