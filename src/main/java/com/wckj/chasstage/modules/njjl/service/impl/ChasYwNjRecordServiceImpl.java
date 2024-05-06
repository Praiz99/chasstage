package com.wckj.chasstage.modules.njjl.service.impl;

import com.wckj.chasstage.modules.njjl.dao.ChasYwNjRecordMapper;
import com.wckj.chasstage.modules.njjl.entity.ChasYwNjRecord;
import com.wckj.chasstage.modules.njjl.service.ChasYwNjRecordService;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ChasYwNjRecordServiceImpl extends BaseService<ChasYwNjRecordMapper, ChasYwNjRecord> implements ChasYwNjRecordService {


    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<>();
        MybatisPageDataResultSet<ChasYwNjRecord> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{"CHAS_ZD_CZLX"}, new String[]{"czlx"}));

        return result;
    }

    @Override
    public int saveNjjl(ChasYwNjRecord record) {
        return baseDao.insert(record);
    }

    @Override
    public ChasYwNjRecord findNjjl(String id) {
        return baseDao.selectByPrimaryKey(id);
    }
}


