package com.wckj.chasstage.modules.qysljcjl.service;

import com.wckj.chasstage.modules.qygl.dao.ChasXtQyMapper;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qysljcjl.dao.ChasYwQysljcjlMapper;
import com.wckj.chasstage.modules.qysljcjl.entity.ChasYwQysljcjl;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwQysljcjlService extends IBaseService<ChasYwQysljcjlMapper, ChasYwQysljcjl> {
    int leaveRecordsBasedOnZoneId(String qyid);

    PageDataResultSet<Map<String,Object>> getEntityPageDataNew(int var1, int var2, Object var3, String var4);

}
