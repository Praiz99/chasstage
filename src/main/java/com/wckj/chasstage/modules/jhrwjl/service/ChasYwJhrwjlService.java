package com.wckj.chasstage.modules.jhrwjl.service;


import com.wckj.chasstage.modules.jhrwjl.dao.ChasYwJhrwjlMapper;
import com.wckj.chasstage.modules.jhrwjl.entity.ChasYwJhrwjl;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;

public interface ChasYwJhrwjlService extends IBaseService<ChasYwJhrwjlMapper, ChasYwJhrwjl> {

    List<ChasYwJhrwjl> getJhrwjlByRybh(String rybh);

    List<ChasYwJhrwjl> getJhrwjlByJhrwbhAndRwzt(String jhrwbh, String rwzt);
}
