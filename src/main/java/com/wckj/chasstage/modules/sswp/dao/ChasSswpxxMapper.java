package com.wckj.chasstage.modules.sswp.dao;

import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChasSswpxxMapper extends IBaseDao<ChasSswpxx> {
    void deleteByPrimaryRbyh(String rybh);
}