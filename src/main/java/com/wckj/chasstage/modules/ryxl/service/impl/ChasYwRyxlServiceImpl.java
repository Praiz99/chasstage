package com.wckj.chasstage.modules.ryxl.service.impl;


import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.ryxl.dao.ChasYwRyxlMapper;
import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.chasstage.modules.ryxl.service.ChasYwRyxlService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYwRyxlServiceImpl extends BaseService<ChasYwRyxlMapper, ChasYwRyxl> implements ChasYwRyxlService {

    private static Logger log = Logger.getLogger(ChasYwRyxlServiceImpl.class);
    @Autowired
    private ChasYjxxService chasYjxxService;
    @Autowired
    private IJdqService jdqService;
    @Autowired
    private ChasYjlbService chasYjlbService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasXtGnpzService gnpzService;
    @Override
    public void processRyxlYj(ChasYwRyxl ryxl) {
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("yjlb", SYSCONSTANT.YJLB_XL);
            params.put("baqid", ryxl.getBaqid());
            List<ChasYjlb> yjlbs = chasYjlbService.findList(params, null);
            if (yjlbs.isEmpty()){
                return;
            }
            ChasYjlb yjlb = yjlbs.get(0);
            String minxlStr = "60";
            String maxxlStr = "100";
            minxlStr = SysUtil.getParamValue("RYXL_MIN_VALUE");
            maxxlStr = SysUtil.getParamValue("RYXL_MAX_VALUE");
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(yjlb.getBaqid());
            if(baqznpz != null && StringUtils.isNotEmpty(baqznpz.getGnpzid())){
                ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
                minxlStr = gnpz.getZdypzVule("RYXL_MIN_VALUE") == null ? minxlStr : gnpz.getZdypzVule("RYXL_MIN_VALUE");
                maxxlStr = gnpz.getZdypzVule("RYXL_MAX_VALUE") == null ? maxxlStr : gnpz.getZdypzVule("RYXL_MAX_VALUE");
            }
            int minval = Integer.parseInt(minxlStr);
            int maxval = Integer.parseInt(maxxlStr);
            if((ryxl.getRyxl()>=minval)&&(ryxl.getRyxl()<=maxval)){
                return;
            }
            ChasYjxx chasYjxx = new ChasYjxx();
            chasYjxx.setId(StringUtils.getGuid32());
            chasYjxx.setBaqid(ryxl.getBaqid());
            chasYjxx.setBaqmc(ryxl.getBaqmc());
            chasYjxx.setLrsj(new Date());
            chasYjxx.setJqms(String.format("%s心率异常，正常范围[%d - %d]，人员当前心率为:%d",
                    ryxl.getRyxm(),minval,maxval,ryxl.getRyxl()));
            chasYjxx.setYjjb(yjlb.getYjjb());
            chasYjxx.setYjlb(SYSCONSTANT.YJLB_XL);
            chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
            chasYjxx.setCfsj(new Date());
            chasYjxx.setCfrid(ryxl.getRybh());
            chasYjxx.setCfrxm(ryxl.getRyxm());
            chasYjxx.setCfqyid(ryxl.getQyid());
            chasYjxx.setCfqymc(ryxl.getQymc());
            if(isNeedSaveAlarmByRy(chasYjxx)){
                chasYjxxService.save(chasYjxx);
                if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())
                        &&yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                    jdqService.openAlarm(ryxl.getBaqid(),yjlb.getYjsc()*60000);
                }
            }else{
                //更新触发时间
                ChasYjxx lastYjxx = findLastYjxx(chasYjxx);
                if(lastYjxx == null){
                    chasYjxxService.save(chasYjxx);
                    if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())
                            &&yjlb.getYjsc()!=null&&yjlb.getYjsc()>0){
                        jdqService.openAlarm(ryxl.getBaqid(),yjlb.getYjsc()*60000);
                    }
                }
                lastYjxx.setCfsj(new Date());
                chasYjxxService.update(lastYjxx);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理人员信息预警出错", e);
        }
    }

    public boolean isNeedSaveAlarmByRy(ChasYjxx yjxx){
        //判断区域、触发人员是否存在预警信息
        // 人员id不为空
        if (StringUtils.isNotEmpty(yjxx.getCfrid())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("baqid", yjxx.getBaqid());
            params.put("cfrid", yjxx.getCfrid());
            params.put("yjzt", yjxx.getYjzt());
            params.put("yjlb", yjxx.getYjlb());
            List<ChasYjxx> yjxxs = chasYjxxService.findList(params, null);
            if (yjxxs!= null&&!yjxxs.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ChasYjxx findLastYjxx(Map<String, Object> params){
        List<ChasYjxx> yjxxs = chasYjxxService.findList(params, "cfsj desc");
        if(yjxxs != null&&!yjxxs.isEmpty()){
            return yjxxs.get(0);
        }
        return null;
    }
    public ChasYjxx findLastYjxx(ChasYjxx yjxx){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", yjxx.getBaqid());
        params.put("cfrid", yjxx.getCfrid());
        params.put("yjzt", yjxx.getYjzt());
        params.put("yjlb", yjxx.getYjlb());
        return findLastYjxx(params);
    }
    @Override
    public ChasYwRyxl getLastestRyxlByRybh(String rybh) {
        return baseDao.getLastestRyxlByRybh(rybh);
    }
}
