package com.wckj.chasstage.modules.sxsgl.service;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sxsgl.dao.ChasSxsKzMapper;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 审讯室分配记录Service
 */
public interface ChasSxsKzService extends IBaseService<ChasSxsKzMapper, ChasSxsKz> {
    List<ChasSxsKz> findByParams(Map<String, Object> map);
    List<ChasSxsKz> getSxsKzByRybh(Map<String, Object> map);
    //List<ChartSxskz> getChartsxskz(Map<String, Object> map);
    List<ChasSxsKz> selectByysdd(Map<String, Object> map);


    ChasSxsKz findAllById(String id);

    void deleteByPrimaryRbyh(String rybh);
    String findcountByBaqid(String baqid);

    int findcountByQyid(String qyid);

    void updateRyxmByRybh(String ryxm, String rybh);

    int clearByrybh(String rybh);
    //List<SxsSyqkEntity> sxssyqk(String baqid);
    ChasSxsKz getJxydSxs(String qyid);

    //查询人员1天内审讯次数
    int getCountByRybh(String baqid, String rybh, String hour);
    //查询审讯室使用记录
    PageDataResultSet<ChasSxsKz> getSxssyjlApiPageData(int pageNo, int pageSize, Object param, String orderBy);
    //查询审讯室使用记录数量
    int selectSyjlSize(Map<String, Object> map);
    // 解除审讯室分配记录 刷新LED(LED在另一端控制)
    DevResult sxsJc(String baqid, String rybh, boolean dxled);

    //查找当前最后分配的记录
    ChasSxsKz findTheLastFpjl(String baqid);

    Map<String, Object> findSxsryList(String baqid);

}
