package com.wckj.chasstage.modules.njxx.service;

import com.wckj.chasstage.modules.njxx.dao.ChasYwNjMapper;
import com.wckj.chasstage.modules.njxx.entity.ChasYwNj;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwNjService extends IBaseService<ChasYwNjMapper, ChasYwNj> {

    int deleteByIds(String ids);

    int saveNjxx(ChasYwNj nj);
    ChasYwNj findNj(String id);

    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    Map<String, Object> getNjPdfBase64(String njid,String type);


}
