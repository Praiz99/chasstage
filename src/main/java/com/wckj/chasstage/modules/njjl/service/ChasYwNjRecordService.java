package com.wckj.chasstage.modules.njjl.service;

import com.wckj.chasstage.modules.njjl.dao.ChasYwNjRecordMapper;
import com.wckj.chasstage.modules.njjl.entity.ChasYwNjRecord;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwNjRecordService extends IBaseService<ChasYwNjRecordMapper, ChasYwNjRecord> {
    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    int saveNjjl(ChasYwNjRecord record);


    ChasYwNjRecord findNjjl(String id);

}
