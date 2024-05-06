package com.wckj.chasstage.modules.znpz.service.impl;

import com.wckj.chasstage.api.def.baq.model.BaqznpzBean;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.znpz.dao.ChasXtBaqznpzMapper;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChasXtBaqznpzServiceImpl extends BaseService<ChasXtBaqznpzMapper, ChasXtBaqznpz> implements ChasXtBaqznpzService {
    @Override
    public BaqConfiguration findByBaqid(String baqid) {
        ChasXtBaqznpz baqznpz = baseDao.findByBaqid(baqid);
        if(baqznpz == null){  //默认开启
            baqznpz = new ChasXtBaqznpz();
        }
        return new BaqConfiguration(baqznpz.getConfiguration());
    }

    @Override
    public ChasXtBaqznpz findByBaqid2(String baqid) {
        return baseDao.findByBaqid(baqid);
    }

    @Override
    public void saveBaqznpz(BaqznpzBean obj) throws Exception{
        if(StringUtils.isNotEmpty(obj.getId())){
            ChasXtBaqznpz baqznpz1 = findById(obj.getId());
            baqznpz1.setConfiguration(obj.getConfiguration());
            update(baqznpz1);
        }else{
            ChasXtBaqznpz baqznpz = new ChasXtBaqznpz();
            baqznpz.setId(StringUtils.getGuid32());
            MyBeanUtils.copyBeanNotNull2Bean(obj,baqznpz);
            save(baqznpz);
        }
    }

    @Override
    public Map<?, ?> getBaqznpzPageData(int page, int rows, Map<String, Object> params, String order) {
        Map<String,Object> result = new HashMap<>();
        PageDataResultSet<ChasXtBaqznpz> list = getEntityPageData(page,rows,params,order);
        result.put("total",list.getTotal());
        result.put("rows",list.getData());
        return result;
    }
}
