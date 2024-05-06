package com.wckj.chasstage.modules.qwjl.service.impl;


import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qwjl.dao.ChasQwjlMapper;
import com.wckj.chasstage.modules.qwjl.entity.ChasQwjl;
import com.wckj.chasstage.modules.qwjl.service.ChasQwjlService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasQwjlServiceImpl extends BaseService<ChasQwjlMapper, ChasQwjl> implements ChasQwjlService {


    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ChasSswpxgService sswpxgService;

	@Override
	public void deleteByPrimaryRbyh(String rybh) {
		baseDao.deleteByPrimaryRbyh(rybh);
	}

    @Override
    public PageDataResultSet<Map<String, Object>> getQwjlApiPageData(int pageNo, int pageSize, Object param, String orderBy) {
        MybatisPageDataResultSet<Map<String,Object>> mybatisPageData = this.baseDao.getQwjlApiPageData(pageNo, pageSize, param, orderBy);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }

    @Override
    public Map<String, Object> saveQwjl(String cabId, String mjsfz, String czlx) throws Exception {
	    Map<String,Object> result = new HashMap<>();

        if(StringUtils.isEmpty(cabId) || StringUtil.isEmpty(mjsfz) || StringUtils.isEmpty(czlx)){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","参数不能为空!");
            return result;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("sjgId",cabId);
        params.put("ryzt",1);
        List<ChasRyjl> ryjls = ryjlService.findList(params,null);
        if(ryjls.isEmpty()){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","根据手机柜ID查询不到人员记录!");
            return result;
        }

        ChasRyjl ryjl = ryjls.get(0);
        ChasBaqryxx baqryxx = baqryxxService.findRyxxBywdbhL(ryjl.getWdbhL());

        JdoneSysUser user = userService.findSysUserByIdCard(mjsfz);
        if(user == null){
            result.put("code",-1);
            result.put("data",null);
            result.put("message","获取不到民警信息!");
            return result;
        }

        ChasQwjl chasQwjl = new ChasQwjl();
        chasQwjl.setId(StringUtils.getGuid32());
        chasQwjl.setBaqid(baqryxx.getBaqid());
        chasQwjl.setBaqmc(baqryxx.getBaqmc());
        chasQwjl.setCyr(baqryxx.getRyxm());
        chasQwjl.setCzmjSfzh(user.getIdCard());
        chasQwjl.setCzmjXm(user.getName());
        chasQwjl.setLrrSfzh(user.getIdCard());
        chasQwjl.setLrsj(new Date());
        chasQwjl.setCzsj(new Date());
        chasQwjl.setDataFlag("0");
        chasQwjl.setDwxtbh(DicUtil.translate("JDONE_SYS_SYSCODE",baqryxx.getZbdwBh()));
        chasQwjl.setZbdwBh(baqryxx.getZbdwBh());
        chasQwjl.setIsdel(SYSCONSTANT.ALL_DATA_MARK_NORMAL_I);
        chasQwjl.setRybh(baqryxx.getRybh());
        chasQwjl.setCzlx(czlx);
        chasQwjl.setCabId(cabId);
        //保存物品柜编号
        ChasSswpxg sswpxg = sswpxgService.findById(cabId);
        chasQwjl.setCabBh(sswpxg.getBh());
        save(chasQwjl);

        result.put("code",0);
        result.put("data",null);
        result.put("message","取物记录保存成功!");
        return result;
    }
}
