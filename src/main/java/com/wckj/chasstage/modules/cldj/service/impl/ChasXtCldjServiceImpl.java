package com.wckj.chasstage.modules.cldj.service.impl;


import com.wckj.chasstage.modules.cldj.dao.ChasXtCldjMapper;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChasXtCldjServiceImpl extends BaseService<ChasXtCldjMapper, ChasXtCldj> implements ChasXtCldjService {

    @Autowired
    ChasXtCldjMapper cldjMapper;

    /**
     * 根据车牌号码查询车辆
     *
     * @param cphm
     * @return
     */
    @Override
    public ChasXtCldj findByCphm(String cphm) {
        Map<String, Object> param = new HashMap<>();
        param.put("cphm", cphm);
        List<ChasXtCldj> cldjList = cldjMapper.selectAll(param, "lrsj desc");
        if (cldjList.size() > 0) {
            ChasXtCldj chasXtCldj = cldjList.get(0);
            return chasXtCldj;
        }
        return null;
    }
}
