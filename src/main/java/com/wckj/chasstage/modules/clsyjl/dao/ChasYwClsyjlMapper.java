package com.wckj.chasstage.modules.clsyjl.dao;


import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ChasYwClsyjlMapper extends IBaseDao<ChasYwClsyjl> {
    //根据基站编号或者车牌或clid查询车辆使用人员数量
    int getClsyrysl(Map<String,Object> param);
    //根据车牌或clid和人员编号解绑车辆
    int unBindsyjlByClid(Map<String,Object> param);
}