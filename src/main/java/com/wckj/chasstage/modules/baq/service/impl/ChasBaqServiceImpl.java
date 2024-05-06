package com.wckj.chasstage.modules.baq.service.impl;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.dao.ChasBaqMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.postgresql.jdbc.PreferQueryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ChasBaqServiceImpl extends BaseService<ChasBaqMapper, ChasBaq> implements ChasBaqService {

    @Autowired
    private ChasBaqrefService chasBaqrefService;
    //@Autowired
    //private ChasBaqryxxService ryxxService;
    @Autowired
    private JdoneSysOrgService orgService;

    @Autowired
    private ChasBaqMapper baqMapper;

    @Override
    public ChasBaq findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public List<ChasBaq> findListByParams(Map<String, Object> map) {
        return baseDao.findListByParams(map);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String[] ids) {
        if (ids == null || ids.length == 0) {
            return "传入的参数为空";
        }
        Map<String, Object> params = new HashMap<>();
        for (String id : ids) {
            params.clear();
            params.put("baqid", id);
            //List<ChasBaqryxx> ryxxList = ryxxService.findList(params, "");
            //if(ryxxList.size()>0) {
            //    return "该办案区下有未删人员!!";
            //}
            params.clear();
            // 删除该办案区所有关联单位
            ChasBaq baq = findById(id);
            params.put("baqid", baq.getId());
            params.put("dwdm", baq.getDwdm());
            List<ChasBaqref> reflist = chasBaqrefService.findList(params, null);
            if (reflist != null && reflist.size() > 0) {
                for (ChasBaqref ref : reflist) {
                    chasBaqrefService.deleteById(ref.getId());
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
    public Map<String, Object> saveOrUpdate(ChasBaq chasBaq) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String id = chasBaq.getId();
        if (StringUtils.isEmpty(id)) {
            // 新增
            chasBaq.setId(StringUtils.getGuid32());
            SessionUser user = WebContext.getSessionUser();
            chasBaq.setLrsj(new Date());
            chasBaq.setXgsj(new Date());
            chasBaq.setLrrSfzh(user.getIdCard());
            chasBaq.setXgrSfzh(user.getIdCard());
            if (StringUtils.isNotEmpty(chasBaq.getDwxtbh())) {
                JdoneSysOrg sysOrg = orgService.findBySysCode(chasBaq.getDwxtbh());
                chasBaq.setDwdm(sysOrg.getCode());
                chasBaq.setDwmc(sysOrg.getName());
            }
            if (chasBaq.getSfdw() == null) {
                chasBaq.setSfdw(0);
            }
            if (save(chasBaq) == 1) {
                ChasBaqref baqRef = new ChasBaqref();
                baqRef.setId(StringUtils.getGuid32());
                baqRef.setBaqid(chasBaq.getId());
                baqRef.setBaqmc(chasBaq.getBaqmc());
                baqRef.setDwdm(chasBaq.getDwdm());
                baqRef.setSydwdm(chasBaq.getDwdm());
                if (StringUtils.isNotEmpty(baqRef.getDwdm())) {
                    JdoneSysOrg sysOrg = orgService.findByCode(chasBaq.getDwdm());
                    baqRef.setDwxtbh(sysOrg.getSysCode());
                }
                if (StringUtils.isNotEmpty(baqRef.getSydwdm())) {
                    JdoneSysOrg sysOrg = orgService.findByCode(chasBaq.getDwdm());
                    baqRef.setSydwxtbh(sysOrg.getSysCode());
                }
                chasBaqrefService.save(baqRef);
                result.put("success", true);
                result.put("msg", "保存成功!");
            }
        } else {
            // 修改
            ChasBaq baq = findById(id);
            MyBeanUtils.copyBeanNotNull2Bean(chasBaq, baq);
            baq.setXgsj(new Date());
            baq.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            if (baq.getSfdw() == null) {
                baq.setSfdw(0);
            }
            if (StringUtils.isNotEmpty(chasBaq.getDwxtbh())) {
                JdoneSysOrg sysOrg = orgService.findBySysCode(chasBaq.getDwxtbh());
                baq.setDwdm(sysOrg.getCode());
                baq.setDwmc(sysOrg.getName());
            }
            update(baq);
            //修改办案区关联单位信息
            Map<String, Object> params = new HashMap<>();
            params.put("baqid", baq.getId());
            List<ChasBaqref> reflist = chasBaqrefService.findList(params, null);
            if (reflist != null && reflist.size() > 0) {
                for (ChasBaqref ref : reflist) {
                    ref.setDwdm(baq.getDwdm());
                    ref.setDwxtbh(baq.getDwxtbh());
                    chasBaqrefService.update(ref);
                }
            }
            result.put("success", true);
            result.put("msg", "修改成功!");
        }
        return result;
    }

    @Override
    public Map<String, Object> saveOrUpdate(Object baq) throws Exception {
        ChasBaq chasBaq = new ChasBaq();
        MyBeanUtils.copyBeanNotNull2Bean(baq, chasBaq);
        return saveOrUpdate(chasBaq);
    }
    @Override
    public DevResult getBaqDicByUser(String queryValue, int page, int pagesize) {
        DevResult w = new DevResult();
        Map<String, Object> result = new HashMap<>(16);
        if (StringUtils.isNotEmpty(queryValue)) {
            ChasBaq chasBaq = findById(queryValue);
            if (chasBaq != null) {
                result.put("Total", 1);
                result.put("Rows", chasBaq);
                w.setData(result);
                w.setCode(1);
            }
        } else {
            SessionUser user = WebContext.getSessionUser();
            Map<String, Object> param = new HashMap<>();
            if (!"0101".equals(user.getRoleCode())) {//管理员返回全部办案区信息
                param.put("sydwdm", user.getOrgCode());
            }
            PageDataResultSet<ChasBaq> data = getEntityPageData(page, pagesize, param, "");
            if (data.getTotal() == 0) {
                w.error("数据为空");
            } else {
                result.put("Total", data.getTotal());
                result.put("Rows", data.getData());
                w.setData(result);
                w.setCode(1);
            }

        }

        return w;
    }


    @Override
    public Map<?, ?> getBaqPageData(int page, int rows, Map<String, Object> params, String order) {
        Map<String, Object> result = new HashMap<>();
        if(StringUtil.isNotEmpty((String) params.get("baqid"))){
            String baqid = (String) params.get("baqid");
            params.put("baqids",baqid.split(","));
        }
        PageDataResultSet<ChasBaq> list = getEntityPageData(page, rows, params, order);
        if (list.getData() != null && list.getData().size() > 0) {
            for (ChasBaq baq : list.getData()) {
                baq.setSfdws(String.valueOf(baq.getSfdw()));
                baq.setSfznbaqs(String.valueOf(baq.getSfznbaq()));
            }
        }
        /*if(page == 1){  //第一页时，添加固定数据
            List<ChasBaq> chasBaqs = list.getData();
            Iterator iterator = chasBaqs.iterator();
            while (iterator.hasNext()){
                ChasBaq baq = (ChasBaq) iterator.next();
                if(StringUtil.equals(baq.getId(),"BF74D78678CD466AA5AA9156F46F1FA9")){
                    iterator.remove();
                }
            }
            ChasBaq baq = findById("BF74D78678CD466AA5AA9156F46F1FA9");
            if(baq != null){
                chasBaqs.add(baq);
            }
            list.setData(chasBaqs);
        }*/
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{"ZD_SYS_SF", "ZD_SYS_SF", "ZD_SYS_SF"}, new String[]{"isDefault", "sfdw", "sfznbaq"}));
        return result;
    }

    @Override
    public List<ChasBaq> getBaqDicByLogin(String sysCodeLike) {
        Map<String, Object> param = new HashMap<>();
        param.put("sysCode", sysCodeLike);
        List<ChasBaq> baqList = baqMapper.findListByParams(param);
        return baqList;
    }

    /**
     * 根据单位获取单位关联所有办案区
     *
     * @param orgCode
     * @return
     */
    @Override
    public List<ChasBaq> getBaqByOrgCode(String orgCode) {
        if(StringUtils.isEmpty(orgCode)){
            throw new BizDataException("单位不能为NULL");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("sydwdm", orgCode);
        List<ChasBaq> baqList = baqMapper.findListByParams(param);
        return baqList;
    }

    @Override
    public String getZrBaqid() {
        SessionUser sessionUser = WebContext.getSessionUser();
        if(sessionUser!=null){
            String orgCode = sessionUser.getCurrentOrgCode();
            Map<String,Object> param = new HashMap<>();
            //优先取配置了大中心标记的区县级别办案区座位登录人办案区
            String qxdm = sessionUser.getCurrentOrgSysCode().substring(0, 6);
            param.put("sysCode", qxdm);
            param.put("sfdzx", 1);
            List<ChasBaq> dzxbaqList = baqMapper.findListByParams(param);
            if (dzxbaqList.size() > 0) {
                return dzxbaqList.get(0).getId();
            }
            param.clear();
            //param.put("dwdm", orgCode.substring(0,6 )+"000000");
            param.put("dwdm", orgCode);
            List<ChasBaq> list = findListByParams(param);
            if(list!=null&&!list.isEmpty()){
                return list.get(0).getId();
            }
            //param.put("dwdm", orgCode.substring(0,6 )+"180000");
            param.put("dwdm", orgCode);

            list = findListByParams(param);
            if(list!=null&&!list.isEmpty()){
                return list.get(0).getId();
            }
        }
        return null;
    }

    @Override
    public ChasBaq getBaqByLoginedUser() {
        SessionUser user = WebContext.getSessionUser();
        if(user!=null){
            String orgCode = user.getOrgCode();
            Map<String,Object> params = new HashMap<>();
            params.put("dwdm", orgCode);
            ChasBaq baq = findByParams(params);
            if(baq!=null){
                return baq;
            }
            params.clear();
            params.put("sydwdm", orgCode);
            List<ChasBaqref> list = chasBaqrefService.findList(params, null);
            if(list!=null&&!list.isEmpty()){
                return findById(list.get(0).getBaqid());
            }
        }
        ChasBaq baq = new ChasBaq();
        baq.setId("noexsit");
        return baq;
    }
}
