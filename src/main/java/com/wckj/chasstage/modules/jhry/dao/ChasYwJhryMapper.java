package com.wckj.chasstage.modules.jhry.dao;


import com.wckj.chasstage.modules.jhry.entity.ChasYwJhry;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChasYwJhryMapper extends IBaseDao<ChasYwJhry> {

    List<ChasYwJhry> getJhryByJhrwbh(String jhrwbh);
}
