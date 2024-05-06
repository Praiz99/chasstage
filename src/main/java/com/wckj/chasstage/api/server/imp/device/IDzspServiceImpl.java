package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.api.server.device.IDzspService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IDzspServiceImpl extends BaseDevService implements IDzspService {
    private static final Logger log = Logger.getLogger(IDzspServiceImpl.class);

    @Override
    public DevResult sendErrorInfo(String baqid, String qyid, String msg) {
        if(chasSbService == null){
            init();
        }
        log.info("发送异常信息给电子水牌,参数["+baqid+","+qyid+","+msg+"]");
        ChasXtQy qy = chasQyService.findByYsid(qyid);
        List<ChasSb> sbList = getDzspByqyid(baqid, qy.getYsid());
        if(sbList == null||sbList.isEmpty()){
            String info = String.format("办案区{%s}审讯室{%s}未配置电子水牌", baqid, qyid);
            log.error(info);
            return DevResult.error(info);
        }

        String sxsmc = (qy==null|| StringUtils.isEmpty(qy.getQymc())) ?"":qy.getQymc();
        ChasSb dzsp = sbList.get(0);
        return dzspOper.sendInfo(baqid, dzsp.getSbbh(), "", "","",sxsmc, -1, msg);
    }

    @Override
    public DevResult sendIdleInfo(String baqid, String qyid) {
        if(chasSbService == null){
            init();
        }
        log.info("发送空闲信息给电子水牌,参数["+baqid+","+qyid+"]");
        ChasXtQy qy = chasQyService.findByYsid(qyid);
        List<ChasSb> sbList = getDzspByqyid(baqid, qy.getYsid());
        if(sbList == null||sbList.isEmpty()){
            String info = String.format("办案区{%s}审讯室{%s}未配置电子水牌", baqid, qyid);
            log.error(info);
            return DevResult.error(info);
        }
        String sxsmc = (qy==null|| StringUtils.isEmpty(qy.getQymc())) ?"":qy.getQymc();
        ChasSb dzsp = sbList.get(0);
        return dzspOper.sendInfo(baqid, dzsp.getSbbh(), "", "","",sxsmc, 0, "");
    }

    @Override
    public DevResult sendTrialInfo(ChasSxsKz sxsKz) {
        return sendDzspInfoByStatus(sxsKz,1);
    }

    @Override
    public DevResult sendUsingInfo(ChasSxsKz sxsKz) {
        return sendDzspInfoByStatus(sxsKz,2);
    }
    private DevResult sendDzspInfoByStatus(ChasSxsKz sxsKz,Integer status){
        if(sxsKz==null){
            return DevResult.error("参数错误,不能发送电子水牌信息");
        }
        if(chasSbService == null){
            init();
        }
        log.info("发送待审讯室信息给电子水牌,参数["+sxsKz.getId()+","+status+"]");
        ChasXtQy qy = chasQyService.findByYsid(sxsKz.getQyid());
        List<ChasSb> sbList = getDzspByqyid(sxsKz.getBaqid(), qy.getYsid());
        if(sbList == null||sbList.isEmpty()){
            String info = String.format("办案区{%s}审讯室{%s}未配置电子水牌", sxsKz.getBaqid(), sxsKz.getQyid());
            log.error(info);
            return DevResult.error(info);
        }
        ChasSb dzsp = sbList.get(0);
        ChasBaqryxx ryxx = chasBaqryxxService.findByRybh(sxsKz.getRybh());
        if(ryxx==null){
            String info = String.format("办案区{%s}找不到{%s}人员信息", sxsKz.getBaqid(), sxsKz.getRybh());
            log.error(info);
            return DevResult.error(info);
        }

        String sxsmc = (qy==null|| StringUtils.isEmpty(qy.getQymc())) ?"":qy.getQymc();
        return dzspOper.sendInfo(sxsKz.getBaqid(), dzsp.getSbbh(), ryxx.getZpid()==null?"":ryxx.getZpid(),
                ryxx.getRyxm(),ryxx.getMjXm(),sxsmc, status, "");
    }

    private List<ChasSb> getDzspByqyid(String baqid,String qyid){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", SYSCONSTANT.SBLX_DZSP);
        map.put("qyid", qyid);
        return chasSbService.findByParams(map);
    }

    @Override
    public DevResult refresh(String baqid, String qyid, String rybh) {
        if(chasSxsKzService.findcountByQyid(qyid)>0){//审讯室正在使用
            //当前审讯室正在使用
            Map<String,Object> map = new HashMap<>();
            map.put("baqid", baqid);
            map.put("qyid", qyid);
            List<ChasSxsKz> list = chasSxsKzService.findList(map, "lrsj desc");
            if(list == null||list.isEmpty()){
                //不应该出现此情况
                return DevResult.error("数据错误");
            }
            ChasSxsKz sxskz = list.get(0);
            if (SYSCONSTANT.N_I.equals(sxskz.getIsdel())) {
                if (rybh.equals(sxskz.getRybh())) {
                    return sendIdleInfo(baqid, qyid);
                } else {
                    return DevResult.error("审讯室正在使用,不应该刷新");
                }

            }else{
                //不应该出现此情况
                return DevResult.error("数据错误");
            }
        }else{
            return sendIdleInfo(baqid, qyid);
        }
    }
}
