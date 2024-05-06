package com.wckj.chasstage.common.delaytask;


import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.internal.ILedOper;
import com.wckj.chasstage.common.task.SxsOutTimeTask;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 审讯室延时断电
 */
public class DelayTask extends TaskBase{
    private static final Logger log = Logger.getLogger(SxsOutTimeTask.class);

    public static DelayTask buildTask(String id){
        String minxlStr = SysUtil.getParamValue("SXS_YSDD_TIME");
        //String maxxlStr = SysUtil.getParamValue("SXS_JXYD_YSDD_TIME");
        int times = 0;//核对笔录后多久结束审讯室电源
        //int ddtimes = 0;//核对笔录后并继续用电多久结束审讯室电源
        try {
            times = Integer.parseInt(minxlStr);

        } catch (Exception e) {
            times = 10;
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id", id);
        return new DelayTask(id, data, times*60*1000);
    }
    public DelayTask(String id, Map<String, Object> data, long expire) {
        super(id, data, expire);
    }
    @Override
    public void run() {
        try{
            outTime();
        }catch (Exception e){
            e.printStackTrace();
            log.error("延时断电出错"+toString(), e);
        }
    }


    public void outTime() {
        if(data.get("id")==null){
            log.debug("参数错误");
            return;
        }
        ChasSxsKzService chasSxsKzService= ServiceContext.getServiceByClass(ChasSxsKzService .class);
        ChasSxsglService chasSxsglService= ServiceContext.getServiceByClass(ChasSxsglService .class);
        ChasDhsglService chasDhsglService= ServiceContext.getServiceByClass(ChasDhsglService .class);
        ChasBaqryxxService chasBaqryxxService= ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        ILedService ledService= ServiceContext.getServiceByClass(ILedService .class);
        IJdqService jdqService= ServiceContext.getServiceByClass(IJdqService .class);
        String sxskzid=data.get("id").toString();
        ChasSxsKz sxskz = chasSxsKzService.findAllById(sxskzid);
        if(sxskz!=null){
            String fpzt = sxskz.getFpzt();
            if(SYSCONSTANT.SXSZT_KX.equals(fpzt)){
                return;
            }
            String baqid = sxskz.getBaqid();
            String rybh = sxskz.getRybh();
            ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);

            if (chasSxsKzService.findcountByQyid(sxskz.getQyid()) > 0) {//当前审讯室正在被使用，不能关闭电源
                sxskz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                sxskz.setFpzt(SYSCONSTANT.SXSZT_KX);
                sxskz.setDyzt(SYSCONSTANT.SXSZT_KX);
                sxskz.setXgrSfzh(baqryxx.getMjSfzh());
                chasSxsKzService.update(sxskz);
            } else {
                chasSxsglService.sxsJc(baqid, rybh, SYSCONSTANT.Y_B);
                chasDhsglService.dhsFp(baqid, rybh, null);
                sxskz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                sxskz.setFpzt(SYSCONSTANT.SXSZT_KX);
                sxskz.setDyzt(SYSCONSTANT.SXSZT_KX);
                sxskz.setXgrSfzh(baqryxx.getMjSfzh());
                chasSxsKzService.update(sxskz);
                log.info("延时关闭继电器:"+new Date());
                jdqService.openOrColseXxs(baqid, sxskz.getQyid(), 1);
                ledService.refreshSxsLedBySxskz(baqid, sxskz.getQyid(), sxskz.getId());
            }
        }
    }


}
