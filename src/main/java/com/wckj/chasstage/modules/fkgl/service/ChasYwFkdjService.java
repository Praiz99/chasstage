package com.wckj.chasstage.modules.fkgl.service;

import com.wckj.chasstage.modules.fkgl.dao.ChasYwFkdjMapper;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwFkdjService extends IBaseService<ChasYwFkdjMapper, ChasYwFkdj> {

    String delete(String[] idstr);

    boolean saveOrUpdate(ChasYwFkdj fkdj) throws Exception;

    ChasYwFkdj findFkdjByRybh(String baqid, String rybh);

    ChasYwFkdj isFkzq(String sfzh);

    Map<String,Object> saveOrUpdateFkRldw(ChasYwFkdj chasYwFkdj);
}
