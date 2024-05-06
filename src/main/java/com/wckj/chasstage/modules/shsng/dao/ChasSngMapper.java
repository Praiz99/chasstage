package com.wckj.chasstage.modules.shsng.dao;


import com.wckj.chasstage.modules.shsng.entity.ChasSng;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasSngMapper extends IBaseDao<ChasSng> {
    ChasSng findDataBySbbh(@Param("shbh")String shbh);

    Integer selectZg();

    void yjcl();

    ChasSng findeByWdbhL(@Param("kzcs2")String kzcs2);

    void sngUpdate(ChasSng sng);
}