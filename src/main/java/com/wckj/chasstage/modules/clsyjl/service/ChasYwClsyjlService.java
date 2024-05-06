package com.wckj.chasstage.modules.clsyjl.service;


import com.wckj.chasstage.modules.clsyjl.dao.ChasYwClsyjlMapper;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.framework.orm.mybatis.service.IBaseService;


public interface ChasYwClsyjlService extends IBaseService<ChasYwClsyjlMapper, ChasYwClsyjl> {
    int getClsyryslByjzbh(String jzbh);
    int getClsyryslByclNum(String clNum);
    int getClsyryslByclid(String clid);
    int unBindsyjlByClid(String clid,String rybh);
}
