package com.wckj.chasstage.modules.dhsgl.dao;


import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasDhsKzMapper extends IBaseDao<ChasDhsKz> {

    List<Map<String, Object>> selectDhsForyj(Map<String, Object> map);
    //人员聚集
    List<Map<String, Object>> selectForryjj(Map<String, Object> map);
    List<ChasDhsKz> findByParams(Map<String, Object> map);
    void deleteByPrimaryId(String id);

    void deleteByPrimaryRybh(String rybh);
    List<Map<String, Object>> selectDhsRy(Map<String, Object> map);

    String findcountByBaqid(@Param("baqid") String baqid);

    void updateXmXbByRybh(@Param("ryxm") String ryxm, @Param("ryxb") String ryxb, @Param("rybh") String rybh);
    int clearByrybh(@Param("rybh") String rybh);
    List<ChasBaqryxx> selectRyxxBydhsId(Map<String, Object> map);
    //获取等候室同案人数
    int getTaRs(@Param("qyid") String qyid, @Param("ywbh") String ywbh, @Param("baqid") String baqid);
    //获取等候室混关人数
    int getHgRs(@Param("qyid") String qyid, @Param("xb") String xb, @Param("baqid") String baqid);
    int findcountByBaqidAndQyid(@Param("baqid") String baqid, @Param("qyid") String qyid);

    List<Map<String, Object>> selectDhsDpData(@Param("baqid") String baqid);
}
