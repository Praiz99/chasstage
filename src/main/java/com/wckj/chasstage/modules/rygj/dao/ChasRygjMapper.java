package com.wckj.chasstage.modules.rygj.dao;


import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasRygjMapper extends IBaseDao<ChasRygj> {
    ChasRygj findzhlocation(Map<String, Object> map);

    List<ChasRygj> findzhlocations(Map<String, Object> map);

    List<ChasRygj> findbyparams(Map<String, Object> map);

    MybatisPageDataResultSet<Map> selectAllByFk(@Param("pageNo") int var1, @Param("pageSize") int var2, @Param("param") Object var3, @Param("orderBy") String var4);
    List<ChasRygj> selectrygjBysxs(Map<String, Object> map);
    //查询从民警进入审讯室后，中途离开审讯室的定位信息
    List<ChasRygj> selectrygjByMj(Map<String, Object> map);

    String findcountByBaqid(@Param("baqid") String baqid);
    int deleteByRbyh(@Param("rybh") String rybh);
}