package com.wckj.chasstage.modules.ythcjqk.service;

import com.wckj.chasstage.api.def.qtdj.model.YthcjParam;
import com.wckj.chasstage.modules.ythcjqk.dao.ChasythcjQkMapper;
import com.wckj.chasstage.modules.ythcjqk.entity.ChasythcjQk;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYthcjQkService extends IBaseService<ChasythcjQkMapper, ChasythcjQk> {

    void saveOrUpdate(YthcjParam param)throws Exception;
}
