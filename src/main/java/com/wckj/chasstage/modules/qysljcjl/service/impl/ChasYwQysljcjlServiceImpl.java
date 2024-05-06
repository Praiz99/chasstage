package com.wckj.chasstage.modules.qysljcjl.service.impl;

import com.wckj.chasstage.modules.qysljcjl.dao.ChasYwQysljcjlMapper;
import com.wckj.chasstage.modules.qysljcjl.entity.ChasYwQysljcjl;
import com.wckj.chasstage.modules.qysljcjl.service.ChasYwQysljcjlService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChasYwQysljcjlServiceImpl extends BaseService<ChasYwQysljcjlMapper, ChasYwQysljcjl> implements ChasYwQysljcjlService {
    @Override
    public int leaveRecordsBasedOnZoneId(String qyid) {
        return baseDao.leaveRecordsBasedOnZoneId(qyid);
    }

    @Override
    public PageDataResultSet<Map<String,Object>> getEntityPageDataNew(int var1, int var2, Object var3, String var4) {
        MybatisPageDataResultSet<Map<String,Object>> mybatisPageData = this.baseDao.getEntityPageDataNew(var1, var2, var3, var4);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }
}
