package com.wckj.chasstage.modules.zpxx.dao;

import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasSswpZpxxMapper extends IBaseDao<ChasSswpZpxx> {

    void deleteByWpid(@Param("wpid") String wpid);

    void deleteBybizId(@Param("bizId") String bizId);

    ChasSswpZpxx findZpxxByBizId(@Param("bizId") String bizId);
}