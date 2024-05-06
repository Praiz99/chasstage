package com.wckj.chasstage.modules.njxx.service.impl;

import com.wckj.chasstage.common.util.pdf.PdfComprehensiveUtil;
import com.wckj.chasstage.modules.njxx.dao.ChasYwNjMapper;
import com.wckj.chasstage.modules.njxx.entity.ChasYwNj;
import com.wckj.chasstage.modules.njxx.service.ChasYwNjService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ChasYwNjServiceImpl extends BaseService<ChasYwNjMapper, ChasYwNj> implements ChasYwNjService {
    protected Logger log = LoggerFactory.getLogger(ChasYwNjServiceImpl.class);


    @Override
    public int deleteByIds(String ids) {
        int b = 0;
        for (String id : ids.split(",")) {
            b = deleteById(id);
        }


        return b;
    }

    @Override
    public int saveNjxx(ChasYwNj nj) {

        return baseDao.insert(nj);
    }

    @Override
    public ChasYwNj findNj(String id) {
        return baseDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<>();
        MybatisPageDataResultSet<ChasYwNj> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        result.put("total", list.getTotal());
        result.put("rows", list.getData());
        return result;
    }

    @Override
    public Map<String, Object> getNjPdfBase64(String njid, String type) {
        Map<String, Object> result = new HashMap<>();

        try {
            byte[] by = PdfComprehensiveUtil.buildMsgObjectNj(njid, type);
            BASE64Encoder encoder = new BASE64Encoder();
            result.put("data", encoder.encode(by));
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
}
