package com.wckj.chasstage.modules.gwzz.service.impl;

import com.wckj.chasstage.api.def.gwzz.bean.GwzzBean;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.gwzz.dao.ChasXtGwlcMapper;
import com.wckj.chasstage.modules.gwzz.entity.ChasXtGwlc;
import com.wckj.chasstage.modules.gwzz.service.ChasXtGwlcService;
import com.wckj.chasstage.modules.gwzz.service.DutiesManager;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangBoo
 * @Description 岗位登记
 * @create 2019-07-26 15:30
 */
@Service
public class ChasXtGwlcServiceImpl extends BaseService<ChasXtGwlcMapper, ChasXtGwlc> implements ChasXtGwlcService {

    @Autowired
    private ChasBaqrefService baqrefService;

    @Override
    public void saveOrUpdate(ChasXtGwlc gwlc) {
        SessionUser user = WebContext.getSessionUser();
        ChasXtGwlc temp = findById(gwlc.getId());
        if(temp==null){
            //新增
            gwlc.setId(StringUtils.getGuid32());
            gwlc.setIsdel(0);
            gwlc.setDataFlag("0");
            gwlc.setLrsj(new Date());
            gwlc.setLrrSfzh(user.getIdCard());
            gwlc.setXgsj(new Date());
            gwlc.setXgrSfzh(user.getIdCard());
            save(gwlc);
        }else{//修改
            gwlc.setXgsj(new Date());
            gwlc.setXgrSfzh(user.getIdCard());
            update(gwlc);
        }
    }

    @Override
    public void saveOrUpdate(GwzzBean bean) throws Exception {
        ChasXtGwlc gwlc = new ChasXtGwlc();
        MyBeanUtils.copyBeanNotNull2Bean(bean,gwlc);
        saveOrUpdate(gwlc);
    }

    @Override
    public Map<String, Object> getGwzzPageData(int page, int rows, Map<String, String> params, String order) {
        Map<String,Object> result = new HashMap<>();
        String baqid = "";
        if(StringUtils.isNotEmpty(params.get("orgCode")) && StringUtils.isEmpty(params.get("baqid"))){
            Map<String,Object> params_ = new HashMap<>();
            params_.put("sydwdm",params.get("orgCode"));
            List<ChasBaqref> baqrefList = baqrefService.findList(params_,null);
            if(!baqrefList.isEmpty()){
                String userRoleId = params.get("roleCode");
                if (!"0101".equals(userRoleId)) {
                    baqid = baqrefList.get(0).getBaqid();
                    params.put("baqid",baqid);
                }
            }
        }else if(StringUtils.isNotEmpty(params.get("baqid"))){
            params.put("baqid",params.get("baqid"));
        }
        PageDataResultSet<ChasXtGwlc> list = getEntityPageData(page, rows,params,order);
        result.put("total",list.getTotal());
        result.put("rows",list.getData());
        return result;
    }

    @Override
    public void deleteGwlcs(String[] ids) {
        for (String id : ids) {
            if(StringUtils.isNotEmpty(id)){
                deleteById(id);
            }
        }
    }

    @Override
    public DutiesManager getDuties(String baqid) {
        Map<String,Object> params = new HashMap<>();
        params.put("baqid",baqid);
        //params.put("jsdm","");  //角色代码
        List<ChasXtGwlc> gwlcList = baseDao.selectAll(params,null);
        return new DutiesManager(){
            @Override
            public boolean isHaveDutyByRoleCode(String roleCode) {
                for (ChasXtGwlc gwlc : gwlcList) {
                    if(StringUtils.contains(gwlc.getJsdm().split(","),roleCode)){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean isHaveDutyByDutyCode(String dutyCode) {
                for (ChasXtGwlc gwlc : gwlcList) {
                    if(StringUtils.contains(gwlc.getLczzdm().split(","),dutyCode)){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean isHaveDutyByDutyCodeAndRoleCode(String dutyCode, String roleCode) {
                for (ChasXtGwlc gwlc : gwlcList) {
                    if(StringUtils.contains(gwlc.getJsdm().split(","),roleCode) &&
                    StringUtils.contains(gwlc.getLczzdm().split(","),dutyCode)){
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
