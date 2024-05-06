package com.wckj.chasstage.common.task;


import com.wckj.api.def.zfba.model.sjjh.SjjhDingtalkMsg;
import com.wckj.api.def.zfba.service.sjjh.ApiSjjhDingtalkMsgService;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public abstract class BaseAlarmService {
    private static final Logger log = LoggerFactory.getLogger(RqcsTask.class);

    @Autowired
    protected ChasYjlbService chasYjlbService;
    @Autowired
    protected ChasYjxxService chasYjxxService;
    @Autowired
    protected ChasDhsKzService chasDhskzService;
    @Autowired
    protected ChasXtQyService qyService;
    @Autowired
    private ChasYwRygjSnapService rygjSnapService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    protected IJdqService jdqService;
    @Autowired
    private JdoneSysUserService userService;
    //根据预警类别获取预警配置信息
    protected List<ChasYjlb> getYjlbListByLb(String lb){
        Map<String, Object> params = new HashMap<>();
        params.put("yjlb", lb);
        return chasYjlbService.findList(params, null);
    }

    private Map<String, Object> getRyxxBygj(ChasRygjSnap gj){
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(gj.getRybh());
        if(baqryxx == null||!"01".equals(baqryxx.getRyzt())){
            return null;
        }
        Map<String, Object> rec = new HashMap<>();
        rec.put("rybh",baqryxx.getRybh() );
        rec.put("ryxm",baqryxx.getRyxm() );
        rec.put("xb",baqryxx.getXb() );
        String ywbh = baqryxx.getAjbh();
        if(StringUtils.isEmpty(ywbh)){
            ywbh = baqryxx.getJqbh();
        }
        if(StringUtils.isEmpty(ywbh)){
            ywbh = StringUtils.getGuid32();
        }
        rec.put("ywbh", ywbh);
        rec.put("qymc",gj.getQymc() );
        rec.put("qyid",gj.getQyid() );
        rec.put("kssj",gj.getKssj() );
        return rec;
    }
    protected List<Map<String, Object>> getDhsYjInfos(String baqid){
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("fjlx", SYSCONSTANT.FJLX_DHS);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        //查询办案区下所有等候室
        Set<String> rybhSet = new HashSet<>();
        List<ChasXtQy> dhsList = qyService.findByParams(params);
        if(dhsList!=null&&!dhsList.isEmpty()){
            params.clear();
            //查询每个等候室当前定位的人员
            for(ChasXtQy dhs:dhsList){
                if(StringUtils.isEmpty(dhs.getYsid())){
                    continue;
                }
                params.put("baqid", baqid);
                params.put("qyid",dhs.getYsid());
                List<ChasRygjSnap> gjList = rygjSnapService.findList(params, null);
                if(gjList!=null&&!gjList.isEmpty()){
                    for(ChasRygjSnap gj:gjList){
                        Map<String, Object> rec = getRyxxBygj(gj);
                        if(rec !=null&&rybhSet.add(gj.getRybh())){
                            result.add(rec);
                        }
                    }
                }
            }

        }
        return result;
    }
    //根据人员定位信息，获取办案区等候室人员信息
    protected List<Map<String, Object>> getDhsYjInfosOld(String baqid){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("fjlx", SYSCONSTANT.FJLX_DHS);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        return chasDhskzService.selectDhsForyj(params);
    }

    protected List<Map<String, Object>> getRyjujiYjInfos(String baqid){
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);

        //查询办案区下所有区域
        List<ChasXtQy> dhsList = qyService.findByParams(params);
        if(dhsList!=null&&!dhsList.isEmpty()){
            params.clear();
            //查询每个区域当前定位的人员
            for(ChasXtQy dhs:dhsList){
                if(dhs.getSfgns()==null||dhs.getSfgns()!=1){
                    continue;
                }
                params.put("baqid", baqid);
                params.put("qyid",dhs.getYsid());
                List<ChasRygjSnap> gjList = rygjSnapService.findList(params, null);
                if(gjList!=null&&!gjList.isEmpty()){
                    for(ChasRygjSnap gj:gjList){
                        Map<String, Object> rec = getJjxxBygjandQy(dhs, gj);
                        if (rec!=null){
                            result.add(rec);
                        }
                    }
                }
            }
        }
        return result;
    }

    private Map<String, Object> getJjxxBygjandQy( ChasXtQy dhs, ChasRygjSnap gj) {
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(gj.getRybh());
        if(baqryxx == null||!"01".equals(baqryxx.getRyzt())){
            return null;
        }
        Map<String, Object> rec = new HashMap<>();
        rec.put("id", dhs.getId());
        rec.put("qymc", dhs.getQymc());
        rec.put("rysl", dhs.getRysl()==null?0:dhs.getRysl());
        rec.put("rybh", baqryxx.getRybh());
        rec.put("kssj", gj.getKssj());
        return rec;
    }

    protected List<Map<String, Object>> getRyjujiYjInfosOld(String baqid){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        return chasDhskzService.selectForryjj(params);
    }
    //保存预警信息
    protected boolean saveYjxx(ChasYjxx yjxx){
        if(isNeedSave(yjxx)){//保存预警信息
          return   chasYjxxService.save(yjxx)>0;
        }else {
            //更新触发时间
            ChasYjxx lastYjxx = findLastYjxx(yjxx);
            if(lastYjxx == null){
                return   chasYjxxService.save(yjxx)>0;
            }
            lastYjxx.setCfsj(new Date());
            if(StringUtils.isNotEmpty(yjxx.getCfrid())){
                lastYjxx.setCfrid(yjxx.getCfrid());
            }
            if(StringUtils.isNotEmpty(yjxx.getCfrxm())){
                lastYjxx.setCfrxm(yjxx.getCfrxm());
            }
            if(StringUtils.isNotEmpty(yjxx.getJqms())){
                lastYjxx.setJqms(yjxx.getJqms());
            }
          return   chasYjxxService.update(lastYjxx)>0;
        }
    }
    //是否需要保存预警信息
    protected boolean isNeedSave(ChasYjxx yjxx){
        return isNeedSaveAlarmByQyRy(yjxx);
    }
    public boolean isNeedSaveAlarmByQyRy(ChasYjxx yjxx){
        //判断区域、触发人员是否存在预警信息
        // 人员id不为空
        if (StringUtils.isNotEmpty(yjxx.getCfqyid())||
                StringUtils.isNotEmpty(yjxx.getCfrid())) {
            Map<String, Object> params = new HashMap<>();
            params.put("baqid", yjxx.getBaqid());
            params.put("cfqyid", yjxx.getCfqyid());
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
        params.put("cfqyid", yjxx.getCfqyid());
        params.put("cfrid", yjxx.getCfrid());
        params.put("yjzt", yjxx.getYjzt());
        params.put("yjlb", yjxx.getYjlb());
        return findLastYjxx(params);
    }
    //检查是否全部正常分配情况，即在dhskz表中有分配记录。全部正常，不要预警
    protected boolean isAllZcFp(String baqid,List<Map<String, Object>> mapList){
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        for (int i=0;i<mapList.size();++i){
            Map<String, Object> map = mapList.get(i);
            params.put("rybh", map.get("rybh"));
            params.put("qyid", map.get("qyid"));
            List<ChasDhsKz> dhsKzs = chasDhskzService.findByParams(params);
            if(dhsKzs == null||dhsKzs.isEmpty()){
                return false;
            }
        }
        return true;
    }

    protected boolean isOpenAlarm(ChasYjlb yjlb){
        if(yjlb ==null){
            return false;
        }
        if(yjlb.getYjsc()==null||yjlb.getYjsc()<0){
            yjlb.setYjsc(5);
        }
        if(StringUtils.isEmpty(yjlb.getYjjb())){//兼容二期
            return true;
        }
        if(SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())){//大中心的
            return true;
        }
        return false;
    }

}
