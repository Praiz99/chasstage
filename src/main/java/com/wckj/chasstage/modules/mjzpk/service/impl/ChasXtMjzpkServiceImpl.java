package com.wckj.chasstage.modules.mjzpk.service.impl;


import com.wckj.chasstage.api.def.face.model.FaceTzlx;
import com.wckj.chasstage.api.def.face.model.RegisterParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.mjzpk.dao.ChasXtMjzpkMapper;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasXtMjzpkServiceImpl extends BaseService<ChasXtMjzpkMapper, ChasXtMjzpk> implements ChasXtMjzpkService {

    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private BaqFaceService baqFaceService;
    @Autowired
    private ChasBaqrefService baqrefService;

    @Override
    public List<ChasXtMjzpk> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String[] ids) {
        if (ids==null||ids.length==0){
            return "传入的参数为空";
        }
        for (String id : ids) {
            ChasXtMjzpk mjzpk = findById(id);
            if(mjzpk!=null&& StringUtils.isNotEmpty(mjzpk.getMjsfzh())){
                Map<String, Object> param = new HashMap<>();
                if(StringUtils.isNotEmpty(mjzpk.getDwxtbh())){
                    param.put("sydwxtbh", mjzpk.getDwxtbh());
                    List<ChasBaqref> list = baqrefService.findList(param, null);
                    for (int i = 0; i < list.size(); i++) {
                        ChasBaqref chasBaqref = list.get(i);
                        baqFaceService.delete(chasBaqref.getBaqid(),mjzpk.getMjsfzh(), FaceTzlx.MJ.getCode());
                    }
                }
            }
            deleteById(id);
        }
        return "0";
    }

    /**
     * 保存(修改)办案区信息，同时保存时将信息添加到ref表
     */
    @Override
    public Map<String, Object> saveOrUpdate(ChasXtMjzpk mjzpk) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String id = mjzpk.getId();
        SessionUser user = WebContext.getSessionUser();
        if (StringUtils.isEmpty(id)) {
            // 新增
            mjzpk.setId(StringUtils.getGuid32());
            mjzpk.setIsdel((short)0);
            mjzpk.setLrsj(new Date());
            mjzpk.setXgsj(new Date());
            if(StringUtils.isEmpty(mjzpk.getSpzt())){
                mjzpk.setSpzt("0");
            }
            mjzpk.setLrrSfzh(user!=null?user.getIdCard():"");
            if(StringUtils.isNotEmpty(mjzpk.getDwxtbh())){
                JdoneSysOrg sysOrg = orgService.findBySysCode(mjzpk.getDwxtbh());
                if(sysOrg==null){
                    result.put("success", false);
                    result.put("msg", "找不到单位信息!");
                    return result;
                }
                mjzpk.setDwdm(sysOrg.getCode());
                mjzpk.setDwmc(sysOrg.getName());
            }
            save(mjzpk);
            result.put("success", true);
            result.put("msg", "新增成功!");
        } else {
            // 修改
            if(StringUtils.isNotEmpty(mjzpk.getDwxtbh())){
                JdoneSysOrg sysOrg = orgService.findBySysCode(mjzpk.getDwxtbh());
                if(sysOrg==null){
                    result.put("success", false);
                    result.put("msg", "找不到单位信息!");
                    return result;
                }
                mjzpk.setDwdm(sysOrg.getCode());
                mjzpk.setDwmc(sysOrg.getName());
            }
            ChasXtMjzpk baq = findById(id);
            MyBeanUtils.copyBeanNotNull2Bean(mjzpk, baq);
            baq.setXgsj(new Date());
            baq.setXgrSfzh(user!=null?user.getIdCard():"");
            if(StringUtils.isNotEmpty(mjzpk.getDwxtbh())){
                JdoneSysOrg sysOrg = orgService.findBySysCode(mjzpk.getDwxtbh());
                baq.setDwdm(sysOrg.getCode());
            }
            update(baq);
            result.put("success", true);
            result.put("msg", "修改成功!");
        }
        return result;
    }
    @Override
    public List<ChasXtMjzpk> findDelMjxx(){
        return baseDao.findDelMjxx();
    }

    @Override
    public ChasXtMjzpk findBySfzh(String sfzh) {
        Map<String,Object> map = new HashMap<>();
        map.put("mjsfzh", sfzh);
        List<ChasXtMjzpk> list = findByParams(map);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
