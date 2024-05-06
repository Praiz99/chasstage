package com.wckj.chasstage.modules.sxsgl.dao;


import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasSxsKzMapper extends IBaseDao<ChasSxsKz> {
    List<ChasSxsKz> findByParams(Map<String, Object> map);
//    List<ChartSxskz> getChartsxskz(Map<String, Object> map);
//    List<Map<String, Object>> findAllSxsLedByBaq(Map<String, Object> map);
    List<ChasSxsKz> getSyqk(@Param("param") Object paramObject, @Param("orderBy") String paramString);
    ChasSxsKz findAllById(String id);
    void deleteByPrimaryRbyh(String rybh);
    List<ChasSxsKz> getSxsKzByRybh(Map<String, Object> map);
    String findcountByBaqid(@Param("baqid") String baqid);
    List<ChasSxsKz> selectByysdd(Map<String, Object> map);
    Integer findcountByQyid(@Param("qyid") String qyid);

    void updateRyxmByRybh(@Param("ryxm") String ryxm, @Param("rybh") String rybh);
    int clearByrybh(@Param("rybh") String rybh);
//    List<SxsSyqkEntity> sxssyqk(@Param("baqid") String baqid);
    //获取继续用电审讯室分配记录
    ChasSxsKz getJxydSxs(@Param("qyid") String qyid);
    int getCountByRybh(@Param("baqid") String baqid, @Param("rybh") String rybh, @Param("start") String start);
    MybatisPageDataResultSet<ChasSxsKz> selectSyjl(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
    int selectSyjlSize(Map<String, Object> map);
    ChasSxsKz findTheLastFpjl(@Param("baqid") String baqid);

    List<Map<String, Object>> selectSxsDpData(@Param("baqid") String baqid);

}
