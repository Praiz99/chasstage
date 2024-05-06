package com.wckj.chasstage.modules.sxsgbjl.service;


import com.wckj.chasstage.modules.sxsgbjl.dao.ChasSxsGbjlMapper;
import com.wckj.chasstage.modules.sxsgbjl.entity.ChasSxsGbjl;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasSxsGbjlService extends IBaseService<ChasSxsGbjlMapper, ChasSxsGbjl> {
    boolean saveOrUpdate(ChasSxsKz sxsKz);
}
