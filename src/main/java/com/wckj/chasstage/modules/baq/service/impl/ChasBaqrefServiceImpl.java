package com.wckj.chasstage.modules.baq.service.impl;


import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.baq.dao.ChasBaqrefMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasBaqrefServiceImpl extends BaseService<ChasBaqrefMapper, ChasBaqref> implements ChasBaqrefService {

    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private ChasBaqService baqService;

    @Override
    public void saveBaqRef(ChasBaqref baqref) throws Exception {
        if (StringUtils.isEmpty(baqref.getId())) { // 新增
            JdoneSysOrg org = orgService.findByCode(baqref.getDwdm());
            if (org != null) {
                baqref.setDwxtbh(org.getSysCode());
            }
            org = orgService.findByCode(baqref.getSydwdm());
            if (org != null) {
                baqref.setSydwxtbh(org.getSysCode());
            }
            baqref.setId(StringUtils.getGuid32());
            save(baqref);
        } else { // 修改
            JdoneSysOrg sysOrg = orgService.findBySysCode(baqref.getSydwxtbh());
            baqref.setSydwdm(sysOrg.getCode());
            ChasBaqref ref = findById(baqref.getId());
            MyBeanUtils.copyBeanNotNull2Bean(baqref, ref);
            update(ref);
        }
    }

    @Override
    public void saveBaqRef(Object baqref) throws Exception {
        ChasBaqref chasBaqref = new ChasBaqref();
        MyBeanUtils.copyBeanNotNull2Bean(baqref, chasBaqref);
        saveBaqRef(chasBaqref);
    }

    /**
     * 为办案区添加使用单位
     */
    @Override
    public Map<String, Object> saveBatch(String baqid, String orgs) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        int sum = 0;
        ChasBaq baq = chasBaqService.findById(baqid);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sysCodes", orgs.split(","));
        List<JdoneSysOrg> orgList = orgService.findList(params, null);
        if (baq != null) {
            String[] orgArray = orgs.split(",");
            List<ChasBaqref> refList = new ArrayList<ChasBaqref>();
            for (int i = 0, len = orgArray.length; i < len; i++) {
                refList.clear();
                param.put("dwxtbh", baq.getDwxtbh());
                param.put("sydwxtbh", orgArray[i]);
                refList = findList(param, "");
                if (refList.size() > 0) {
                    result.put("success", true);
                    result.put("msg", "保存成功!");
                } else {
                    ChasBaqref baqRef = new ChasBaqref();
                    baqRef.setId(StringUtils.getGuid32());
                    baqRef.setBaqid(baq.getId());
                    baqRef.setDwdm(baq.getDwdm());
                    baqRef.setDwxtbh(baq.getDwxtbh());
                    //baqRef.setSydwdm(orgArray[i]);
                    baqRef.setSydwxtbh(orgArray[i]);
                    for (JdoneSysOrg sysOrg : orgList) {
                        if (StringUtils.equals(baqRef.getSydwxtbh(), sysOrg.getSysCode())) {
                            baqRef.setSydwdm(sysOrg.getCode());
                        }
                    }
                    save(baqRef);
                    sum = sum + 1;
                    result.put("success", true);
                    result.put("msg", "保存成功!");
                }
            }
        } else {
            result.put("success", false);
            result.put("msg", "办案区不存在,请重新选择!!!");
        }
        result.put("sum", sum);
        return result;
    }

    @Override
    public Map<?, ?> getBaqrefPageData(int page, int rows, Map<String, String> params, String order) {
        Map<String, Object> result = new HashMap<>();

        String baqid = params.get("baqid");
        ChasBaq baq = baqService.findById(baqid);

        if (baq != null) {
            params.put("baqid", baq.getId());
            params.put("dwxtbh", baq.getDwxtbh());
        }
        PageDataResultSet<ChasBaqref> list = getEntityPageData(page, rows, params, order);

        // 赋值办案区名字
        List<ChasBaqref> reflist = list.getData();
        if (reflist != null && reflist.size() > 0) {
            for (ChasBaqref baqref : reflist) {
                baqref.setBaqmc(baq.getBaqmc());
            }
        }

        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(reflist, new String[]{"JDONE_SYS_ORG", "JDONE_SYS_ORG"}, new String[]{"dwdm", "sydwdm"}));
        return result;
    }

    @Override
    public void deleteByids(String ids) {
        String[] idstr = ids.split(",");
        for (String id : idstr) {
            deleteById(id);
        }
    }

    @Override
    public String findBaqId() {
        String baqId = "";
        Map<String, Object> param = new HashMap<String, Object>();
        SessionUser sessionUser = WebContext.getSessionUser();
        param.put("sydwdm", sessionUser.getOrgCode());
        List<ChasBaq> list = baqService.findList(param, null);
        if (list != null && !list.isEmpty()) {
            baqId = list.get(0).getId();
        }
        return baqId;
    }

    public String getBaqidByUser() {
        SessionUser user = WebContext.getSessionUser();
        if (user == null) {
            return "notlogin";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sydwdm", user.getOrgCode());
        List<ChasBaqref> list = findList(params, null);
        if (list == null || list.isEmpty()) {
            return "notrefbaq";
        }
        return list.get(0).getBaqid();
    }

}
