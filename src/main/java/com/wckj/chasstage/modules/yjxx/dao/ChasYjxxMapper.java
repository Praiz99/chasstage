package com.wckj.chasstage.modules.yjxx.dao;



import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChasYjxxMapper extends IBaseDao<ChasYjxx> {

    List<ChasYjxx> findXzYjxx(@Param("baqid") String baqid);

    List<ChasYjxx> findXsYjxx(@Param("baqid") String baqid);
}