package com.wckj.chasstage.modules.wszh.service.impl;

import com.wckj.chasstage.api.def.baq.model.WszhBean;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.wszh.dao.ChasWszhMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.wszh.entity.ChasWszh;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.wszh.service.ChasWszhService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChasWszhServiceImpl extends BaseService<ChasWszhMapper, ChasWszh> implements ChasWszhService {

    @Autowired
    private ChasBaqService baqService ;

    @Override
    public void SaveWithUpdate(ChasWszh chasWszh) throws Exception {
        if(StringUtils.isEmpty(chasWszh.getId())){  //新增
            ChasBaq baq = baqService.findById(chasWszh.getBaqid());
            chasWszh.setBaqmc(baq.getBaqmc());
            chasWszh.setId(StringUtils.getGuid32());
            chasWszh.setLrsj(new Date());
            chasWszh.setXgsj(new Date());
            chasWszh.setLrrSfzh(WebContext.getSessionUser().getIdCard());
            chasWszh.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            save(chasWszh);
        }else{  //修改
            ChasWszh wszh = findById(chasWszh.getId());
            MyBeanUtils.copyBeanNotNull2Bean(chasWszh, wszh);
            wszh.setXgsj(new Date());
            wszh.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            update(wszh);
        }
    }

    @Override
    public void SaveWithUpdate(WszhBean wszh) throws Exception {
        ChasWszh chasWszh = new ChasWszh();
        MyBeanUtils.copyBeanNotNull2Bean(wszh,chasWszh);
        SaveWithUpdate(chasWszh);
    }

    @Override
    public void deleteByids(String ids) throws Exception {
        String[] idstr = ids.split(",");
        for(String id : idstr){
            deleteById(id);
        }
    }

    @Override
    public Map<?, ?> getWszhPageData(int page, int rows, Map<String, Object> params, String order) {
        Map<String,Object> result = new HashMap<>();
        PageDataResultSet<ChasWszh> list = getEntityPageData(page,rows,params,order);
        result.put("total",list.getTotal());
        result.put("rows",list.getData());
        return result;
    }
}
