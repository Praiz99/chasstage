package com.wckj.chasstage.modules.qygl.service;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.qygl.dao.ChasXtQyMapper;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasXtQyService extends IBaseService<ChasXtQyMapper, ChasXtQy> {
    List<ChasXtQy> findKfpDhs(Map<String, Object> map);

    List<ChasXtQy> findbtaAndtxbDhs(Map<String, Object> map);

    List<ChasXtQy> getDhsByRybh(String rybh);

    List<ChasXtQy> findfreeSxs(String baqid);

    ChasXtQy findByYsid(String ysid);

    List<ChasXtQy> findByParams(Map<String, Object> map);

    Map<String, Object> saveOrUpdate(ChasXtQy xtqy, String id) throws Exception;

    DevResult getQyDicByBaq(String baqid, String fjlx, String queryValue, int page, int pagesize);

    int delete(String[] idstr);

    List<ChasXtQy> getDhsRoomData(Map<String, Object> params);

    void clearAll(String baqid);

    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    Map<String, Object> tbBaqQy(String baqid);

}
