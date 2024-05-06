package com.wckj.chasstage.modules.dhssyjl.service.impl;

import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhssyjl.dao.chasYwDhssyjlMapper;
import com.wckj.chasstage.modules.dhssyjl.entity.ChasYwDhssyjl;
import com.wckj.chasstage.modules.dhssyjl.service.ChasDhsSyjlService;
import com.wckj.chasstage.modules.jhrw.service.impl.ChasYwJhrwServiceImpl;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author:zengrk
 */
@Service
public class ChasDhsSyjlServiceImpl  extends BaseService<chasYwDhssyjlMapper, ChasYwDhssyjl> implements ChasDhsSyjlService {
    protected Logger log = LoggerFactory.getLogger(ChasYwJhrwServiceImpl.class);

    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private JdoneSysUserService userService;
    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Override
    public Boolean saveSyjl(ChasDhsKz chasDhsKz) {
        //SessionUser user = WebContext.getSessionUser();
        ChasYwDhssyjl chasYwDhssyjl=new ChasYwDhssyjl();
        chasYwDhssyjl.setId(StringUtils.getGuid32());
        chasYwDhssyjl.setIsdel((short) 0);
        chasYwDhssyjl.setDataFlag("0");
        //chasYwDhssyjl.setLrrSfzh(user.getIdCard());
        chasYwDhssyjl.setLrsj(new Date());
        chasYwDhssyjl.setXgsj(new Date());
        //chasYwDhssyjl.setXgrSfzh(user.getIdCard());
        chasYwDhssyjl.setBaqid(chasDhsKz.getBaqid());
        chasYwDhssyjl.setBaqmc(chasDhsKz.getBaqmc());
        chasYwDhssyjl.setKssj(new Date());
        chasYwDhssyjl.setQyid(chasDhsKz.getQyid());
        chasYwDhssyjl.setXyrxm(chasDhsKz.getRyxm());
        chasYwDhssyjl.setRybh(chasDhsKz.getRybh());
        chasYwDhssyjl.setRylx("1");//嫌疑人
        ChasXtQy xtQy = qyService.findByYsid(chasDhsKz.getQyid());
        if(xtQy!=null){
            chasYwDhssyjl.setQymc(xtQy.getQymc());
            return save(chasYwDhssyjl)>0;
        }
        return false;
    }

    @Override
    public Boolean updateDhsJl(ChasDhsKz chasDhsKz) {
        //SessionUser user = WebContext.getSessionUser();
        ChasYwDhssyjl dhssyjl = this.baseDao.getDhssyjlByRybh(chasDhsKz.getRybh());
        if(!Objects.isNull(dhssyjl)){
            dhssyjl.setXgsj(new Date());
            //dhssyjl.setXgrSfzh(user.getIdCard());
            dhssyjl.setJssj(new Date());
            this.baseDao.updateByPrimaryKey(dhssyjl);
        }
        return saveSyjl(chasDhsKz);
    }

    @Override
    public ApiReturnResult<?> saveOrUpdateSyjl(ChasYwDhssyjl dhssyjl) {
        try {
            dhssyjl.setId(StringUtils.getGuid32());
            dhssyjl.setLrsj(new Date());
            dhssyjl.setDataFlag("0");
            dhssyjl.setIsdel((short) 0);
            dhssyjl.setKssj(new Date());
            JdoneSysUser sysUser = userService.findSysUserByIdCard(dhssyjl.getLrrSfzh());
            if(sysUser != null){
                dhssyjl.setDwxtbh(sysUser.getOrgSysCode());
                dhssyjl.setUsername(sysUser.getName());
            }
            if("1".equals(dhssyjl.getRylx())){
                //嫌疑人
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(dhssyjl.getRybh());
                if(baqryxx != null){
                    dhssyjl.setXyrxm(baqryxx.getRyxm());
                }
            }
            if("2".equals(dhssyjl.getRylx())){
                //民警
                JdoneSysUser user = userService.findSysUserByIdCard(dhssyjl.getMjsfzh());
                if(user != null){
                    dhssyjl.setXyrxm(user.getName());
                }
            }
            baseDao.insert(dhssyjl);
            return ResultUtil.ReturnSuccess("成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("saveSyjl:",e);
            return ResultUtil.ReturnError("系统异常");
        }
    }


}
