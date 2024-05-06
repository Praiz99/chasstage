package com.wckj.chasstage.modules.xgpz.service.impl;


import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.xgpz.dao.ChasXgpzMapper;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChasXgpzServiceImpl extends BaseService<ChasXgpzMapper, ChasXgpz> implements ChasXgpzService {

    @Override
    public void SaveWithUpdate(ChasXgpz chasXgpz) throws Exception {
        if(StringUtils.isEmpty(chasXgpz.getId())){  //新增
            chasXgpz.setId(StringUtils.getGuid32());
            chasXgpz.setLrsj(new Date());
            chasXgpz.setXgsj(new Date());
            chasXgpz.setLrrSfzh(WebContext.getSessionUser().getIdCard());
            chasXgpz.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            save(chasXgpz);
        }else{  //修改
            ChasXgpz xgpz = findById(chasXgpz.getId());
            MyBeanUtils.copyBeanNotNull2Bean(chasXgpz, xgpz);
            xgpz.setXgsj(new Date());
            xgpz.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            update(xgpz);
        }
    }
}
