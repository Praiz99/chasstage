package com.wckj.chasstage.modules.sxsgbjl.service.impl;


import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sxsgbjl.dao.ChasSxsGbjlMapper;
import com.wckj.chasstage.modules.sxsgbjl.entity.ChasSxsGbjl;
import com.wckj.chasstage.modules.sxsgbjl.service.ChasSxsGbjlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ChasSxsGbjlServiceImpl extends BaseService<ChasSxsGbjlMapper, ChasSxsGbjl> implements ChasSxsGbjlService {
    protected Logger log = LoggerFactory.getLogger(ChasSxsGbjlServiceImpl.class);
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private JdoneSysUserService userService;

    @Autowired
    private ChasBaqService chasBaqService;

    @Override
    public boolean saveOrUpdate(ChasSxsKz sxsKz) {
        if(sxsKz==null){
            return false;
        }
        ChasSxsGbjl gbjl = new ChasSxsGbjl();
        gbjl.setId(StringUtils.getGuid32());
        gbjl.setBaqid(sxsKz.getBaqid());
        SessionUser sessionUser = WebContext.getSessionUser();
        if(sessionUser !=null){
            String xm=sessionUser.getName();
            String sfzh = sessionUser.getIdCard();
            String dwxtbh = sessionUser.getOrgSysCode();
            gbjl.setDataflag("");
            gbjl.setIsdel(0);
            gbjl.setLrrSfzh(sfzh);
            gbjl.setLrsj(new Date());
            gbjl.setXgrSfzh(sfzh);
            gbjl.setXgsj(new Date());
            gbjl.setBaqmc(sxsKz.getBaqmc());
            ChasXtQy xtQy = qyService.findByYsid(sxsKz.getQyid());
            if(xtQy!=null){
                gbjl.setQyid(sxsKz.getQyid());
                gbjl.setQymc(xtQy.getQymc());
                gbjl.setXm(xm);
                gbjl.setDwxtbh(dwxtbh);
                gbjl.setSyid(sxsKz.getId());
                gbjl.setUsername(sessionUser.getLoginId());
                gbjl.setXyrxm(sxsKz.getRyxm());
                return save(gbjl)>0;
            }
        }

        return false;
    }
}