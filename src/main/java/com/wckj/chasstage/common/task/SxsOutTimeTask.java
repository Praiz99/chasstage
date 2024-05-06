package com.wckj.chasstage.common.task;



import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.jdone.modules.sys.util.SysUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SxsOutTimeTask {
    private static final Logger log = LoggerFactory.getLogger(SxsOutTimeTask.class);
    @Autowired
    private ChasSxsKzService chasSxsKzService;
    @Autowired
    private ChasSxsglService chasSxsglService;
    @Autowired
    private ChasDhsKzService chasDhsKzService;
    @Autowired
    private ChasDhsglService chasDhsglService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private IJdqService jdqService;
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;

    public void outTime() {
        String minxlStr = SysUtil.getParamValue("SXS_YSDD_TIME");
        String maxxlStr = SysUtil.getParamValue("SXS_JXYD_YSDD_TIME");
        int times = 0;//核对笔录后多久结束审讯室电源
        int ddtimes = 0;//核对笔录后并继续用电多久结束审讯室电源
        try {
            times = Integer.parseInt(minxlStr);

        } catch (NumberFormatException e) {
            times = 10;
        }
        try {
            ddtimes = Integer.parseInt(maxxlStr);
        } catch (NumberFormatException e) {
            ddtimes = 30;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        // 查询分配状态下已完结笔录 等待定位触发
        map.put("fpzt", SYSCONSTANT.SXSZT_TY);
        map.put("hdsj", "is not null");
        List<ChasSxsKz> sxslist = chasSxsKzService.selectByysdd(map);
        map.put("fpzt", SYSCONSTANT.SXSZT_JX);
        map.put("hdsj", "is not null");
        List<ChasSxsKz> sxsJxlist = chasSxsKzService.selectByysdd(map);
        if (sxslist == null) {
            sxslist = new ArrayList<>();
        }
        //sxslist.addAll(sxsJxlist);
        if (sxslist == null || sxslist.size() == 0)
            return;
        Boolean bol = false;
        Set<String> cache = new HashSet<>();
        Long date = new Date().getTime();
        for (ChasSxsKz sxskz : sxslist) {
            String fpzt = sxskz.getFpzt();
            String baqid = sxskz.getBaqid();
            String rybh = sxskz.getRybh();
            // 继续用电在结束笔录后的倒计时
            if (SYSCONSTANT.SXSZT_JX.equals(fpzt)) {
                //嫌疑人审讯室分配记录
                if (sxskz.getHdsj() != null && (date - sxskz.getHdsj().getTime() > ddtimes * 60000))
                    bol = true;
            } else {// 其他状态在笔录结束后倒计时
                if (sxskz.getHdsj() != null && (date - sxskz.getHdsj().getTime() > times * 60000))
                    bol = true;
            }
            if (bol) {
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
                    if (cache.add(sxskz.getQyid())) {
                        //chasSxsKzService.sxsOpenOrClose(sxskz.getBaqid(), sxskz.getQyid(), SYSCONSTANT.OFF);
                        jdqService.openOrColseXxs(baqid, sxskz.getQyid(), 1);
                        ledService.refreshSxsLedBySxskz(baqid, sxskz.getQyid(), sxskz.getId());
                    }
                }
                bol = false;
            }
        }
    }
}
