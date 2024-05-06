package com.wckj.chasstage.modules.clcrjl.service.impl;


import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.clcrjl.dao.ChasYwClcrjlMapper;
import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.chasstage.modules.clcrjl.service.ChasYwClcrjlService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChasYwClcrjlServiceImpl extends BaseService<ChasYwClcrjlMapper, ChasYwClcrjl> implements ChasYwClcrjlService {


    @Override
    public String delete(String[] ids) {
        if (ids==null||ids.length==0){
            return "传入的参数为空";
        }
        for (String id : ids) {
            deleteById(id);
        }
        return SYSCONSTANT.N;
    }

    @Override
    public ChasYwClcrjl findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public Map<String, Object> saveOrUpdate(ChasYwClcrjl crjl) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String id = crjl.getId();
        if (StringUtils.isEmpty(id)) {
            // 新增
            crjl.setIsdel(0);
            crjl.setId(StringUtils.getGuid32());
            if(crjl.getLrsj() == null){
                crjl.setLrsj(new Date());
            }
            if(crjl.getXgsj() == null){
                crjl.setXgsj(new Date());
            }
           // crjl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
           // crjl.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            if (save(crjl) == 1) {
                result.put("success", true);
                result.put("msg", "保存成功!");
            }
        } else {
            // 修改
            ChasYwClcrjl baq = findById(id);
            MyBeanUtils.copyBeanNotNull2Bean(crjl, baq);
            baq.setXgsj(new Date());
            baq.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            update(baq);
            result.put("success", true);
            result.put("msg", "修改成功!");
        }
        return result;
    }
}
