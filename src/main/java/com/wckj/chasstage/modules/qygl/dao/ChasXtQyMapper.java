package com.wckj.chasstage.modules.qygl.dao;


import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasXtQyMapper extends IBaseDao<ChasXtQy> {
    List<ChasXtQy> findByParams(Map<String, Object> map) ;
    List<ChasXtQy> findKfpDhs(Map<String, Object> map) ;
    //查找不同案并同性别
    List<ChasXtQy> findbtaAndtxbDhs(Map<String, Object> map) ;
    List<ChasXtQy> getDhsByRybh(String rybh);
    List<ChasXtQy> getDhsRoomData(Map<String, Object> map);
    ChasXtQy findByYsid(String areaId);
    List<ChasXtQy> findfreeSxs(@Param("baqid") String baqid);
    List<ChasXtQy> findByYsids(@Param("ysids") String[] ysids);
    int clearByBaq(@Param("baqid") String baqid);
}