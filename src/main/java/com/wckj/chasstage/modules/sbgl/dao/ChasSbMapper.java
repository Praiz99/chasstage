package com.wckj.chasstage.modules.sbgl.dao;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasSbMapper extends IBaseDao<ChasSb> {
    List<ChasSb> findByParams(Map<String, Object> map);
    int clearByBaqAndLx(@Param("baqid") String baqid, @Param("lx") String lx);
    Map getRtspValByQyid(@Param("qyid") String qyid);
    List<ChasSb> findZDSB();
    ChasSb getSbByLxAndQy(@Param("sblx")String sblx,@Param("qyid")String qyid);
}
