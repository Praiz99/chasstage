package com.wckj.chasstage.modules.clsyjl.service.impl;


import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.clsyjl.dao.ChasYwClsyjlMapper;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.chasstage.modules.clsyjl.service.ChasYwClsyjlService;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class ChasYwClsyjlServiceImpl extends BaseService<ChasYwClsyjlMapper, ChasYwClsyjl> implements ChasYwClsyjlService {

    @Override
    public int getClsyryslByjzbh(String jzbh) {
        if(StringUtil.isNotEmpty(jzbh)){
            return getClsyrysl(jzbh,"","");
        }
        return 0;
    }

    @Override
    public int getClsyryslByclNum(String clNum) {
        if(StringUtil.isNotEmpty(clNum)){
            return getClsyrysl("","",clNum);
        }
        return 0;
    }

    @Override
    public int getClsyryslByclid(String clid) {
        if(StringUtil.isNotEmpty(clid)){
            return getClsyrysl("",clid,"");
        }
        return 0;
    }

    private int getClsyrysl(String jzbh,String clid,String clNum){
        Map<String,Object> map = new HashMap<>();
        map.put("jzbh", jzbh);
        map.put("clid", clid);
        map.put("clNumber", clNum);
        return baseDao.getClsyrysl(map);
    }

    @Override
    public int unBindsyjlByClid(String clid, String rybh) {
        Map<String,Object> map = new HashMap<>();
        map.put("rybh", rybh);
        map.put("clid", clid);
        return baseDao.unBindsyjlByClid(map);
    }
}
