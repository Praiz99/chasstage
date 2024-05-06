package com.wckj.chasstage.modules.dhssyjl.service;

import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhssyjl.dao.chasYwDhssyjlMapper;
import com.wckj.chasstage.modules.dhssyjl.entity.ChasYwDhssyjl;
import com.wckj.chasstage.modules.sxsgbjl.dao.ChasSxsGbjlMapper;
import com.wckj.chasstage.modules.sxsgbjl.entity.ChasSxsGbjl;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

/**
 * @author:zengrk
 */
public interface ChasDhsSyjlService  extends IBaseService<chasYwDhssyjlMapper, ChasYwDhssyjl> {
    Boolean saveSyjl(ChasDhsKz chasDhsKz);
    Boolean updateDhsJl(ChasDhsKz chasDhsKz);

    ApiReturnResult<?> saveOrUpdateSyjl(ChasYwDhssyjl dhssyjl);

}
