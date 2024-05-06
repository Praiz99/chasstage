package com.wckj.chasstage.modules.yy.service.impl;

import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.yy.dao.ChasYwYyMapper;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYwYyServiceImpl extends BaseService<ChasYwYyMapper, ChasYwYy> implements ChasYwYyService {
    final static Logger log = Logger.getLogger(ChasYwYyServiceImpl.class);
//    @Autowired
//    private ChasXtMjzpkService mjzpkService;
//    @Autowired
//    private IWdService iWdService;
//    @Autowired
//    private ChasYwMjrqService mjrqService;
//    @Autowired
//    private ApiGgYwbhService jdoneComYwbhService;
//    @Autowired
//    private JdoneSysUserService userService;
//    @Autowired
//    private ChasBaqService baqService;
//    @Autowired
//    private ChasSbService chasSbService;
    @Override
    public List<ChasYwYy> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String[] ids) {
        if (ids==null||ids.length==0){
            return "传入的参数为空";
        }
        for (String id : ids) {
            deleteById(id);
        }
        return "0";
    }

    /**
     * 保存(修改)办案区信息，同时保存时将信息添加到ref表
     */
    @Override
    public Map<String, Object> saveOrUpdate(ChasYwYy mjzpk) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String id = mjzpk.getId();
        SessionUser user = WebContext.getSessionUser();
        if (StringUtils.isEmpty(id)) {
            // 新增
            mjzpk.setId(StringUtils.getGuid32());
            mjzpk.setIsdel((short)0);
            mjzpk.setLrsj(new Date());
            mjzpk.setXgsj(new Date());
            mjzpk.setLrrSfzh(user!=null?user.getIdCard():"");
            save(mjzpk);
            result.put("success", true);
            result.put("msg", "新增成功!");
        } else {
            // 修改
            ChasYwYy baq = findById(id);
            MyBeanUtils.copyBeanNotNull2Bean(mjzpk, baq);
            baq.setXgsj(new Date());
            baq.setXgrSfzh(user!=null?user.getIdCard():"");
            update(baq);
            result.put("success", true);
            result.put("msg", "修改成功!");
        }
        return result;
    }
}
