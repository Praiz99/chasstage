package com.wckj.chasstage.modules.baqry.dao;


import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasBaqryxxMapper extends IBaseDao<ChasBaqryxx> {

    ChasBaqryxx getBaqryBybaqid(@Param("baqid") String baqid, @Param("bh") String bh);

    ChasBaqryxx findRyxxBywdbhL(@Param("wdbhL") String wdbhl);

    List<ChasBaqryxx> findTaryByRyjlYwbh(@Param("ywbh") String ywbh);

    String getRyxxCount(@Param("baqid") String baqid, @Param("ryzt") String ryzt);

    List<Map<String,Object>> getDataByYwbh(Map<String, Object> params);

    ChasBaqryxx findBaqryxBySfzh(@Param("sfzh") String sfzh);

    ChasBaqryxx findBaqryxByRybh(@Param("rybh") String rybh);
    ChasBaqryxx findBaqryxByRybhAll(@Param("rybh") String rybh,@Param("baqid")String baqid);
    ChasBaqryxx findBaqryxBySfzhAll(@Param("sfzh") String sfzh,@Param("baqid")String baqid);

    List<ChasBaqryxx> findBaqryxxByRybhs(@Param("rybhs") String rybhs);

    MybatisPageDataResultSet<ChasBaqryxxBq> getEntityPageDataBecauseQymc(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
    int clearByRybh(@Param("rybh") String rybh);

    ChasBaqryxx getSyryByWdbhl(@Param("wdbhL") String wdbhl, @Param("sysCodeLike")String sysCodeLike);

    Map<String,String> getwgsjtj(@Param("sysOrgCode") String sysOrgCode);

    ChasBaqryxx findCurrentByWdbhl(@Param("wdbhL")String wdbhl);

    MybatisPageDataResultSet<ChasBaqryxx> getYfwpgBaqryxx(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
}
