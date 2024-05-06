package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class ILedServiceImpl extends BaseDevService implements ILedService {
    private static final Logger log = Logger.getLogger(ILedServiceImpl.class);

    /**
     * 刷新等候室LED
     * @param baqid 办案区id
     * @param qyid 等候室id
     * @return
     */
    @Override
    public DevResult refreshDhsLed(String baqid, String qyid) {
        if(chasQyService == null){
            init();
        }
        log.debug(String.format("ILedServiceImpl.refreshDhsLed 办案区:{%s}开始更新{%s}等候室LED",baqid,qyid));
        DevResult wr = new DevResult();
        String msg;
        //log.debug("办案区:{"+baqid+"} 开始更新等候室LED:{"+qyid+"}");
        //获取等候室led设备
        List<ChasSb> deviceList = getQySbsByLxAndGn(qyid, SYSCONSTANT.SBLX_LED,SYSCONSTANT.SBGN_DHSLED);
        if (deviceList == null || deviceList.isEmpty()) {
            msg = "该等候室{"+qyid+"} 未配置LED";
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        //ChasXtQy chasXtQy = chasQyService.findById(qyid);
        String content = buildWaitingRoomLedText(qyid);
        String ids = deviceList.get(0).getSbbh();
        msg = "办案区:{"+baqid+"} 等候室的led数{"+deviceList.size()+"} ids {"+ids+"},ledTexts{"+content+"}";
        log.debug(msg);
        try {
            //等候室Led第一行固定显示，不需要传值，审讯室（大屏）第一行可变，需要传值
            //DicUtil.translate("dhslx", chasXtQy.getKzlx())chasXtQy.getYsbh(),
            DevResult result=DevResult.success("刷新LED成功");
            assembly(baqid, "1", ids, 0,  content);
            //wr.add(ids,result);
            wr.setCode(result.getCode());
            if(result.getCode()==1){
                wr.setMessage(String.format("办案区:%s 等候室%s 刷新LED成功", baqid,qyid));
            }else{
                log.error(String.format("办案区:%s 等候室%s 刷新LED失败，原因%s",
                        baqid,qyid,result.getMessage()));
                wr.setMessage(result.getMessage());
            }

        } catch (DeviceException e) {
            log.error("refreshDhsLed:", e);
            wr.add("refreshDhsLed:", e);
            wr.setCode(0);
            wr.setMessage(String.format("办案区:%s 等候室 刷新LED失败", baqid));
        }
        return wr;
    }

    /**
     * 刷新等候室大屏
     * @param baqid
     * @return
     */
    @Override
    public DevResult refreshDhsDp(String baqid) {
        DevResult wr = new DevResult();
        String msg = "";
        List<ChasSb> chasSbs = getBaqSbsByLxAndGn(baqid,SYSCONSTANT.SBLX_LED,SYSCONSTANT.SBGN_DP);
        msg = "办案区:{"+baqid+"}  大屏led数"+chasSbs.size();
        log.debug(msg);
        if (chasSbs == null || chasSbs.size() == 0) {
            log.error("办案区:{"+baqid+"}未配置大屏LED");
            wr.setCode(0);
            wr.setMessage("未配置大屏LED");
            return wr;
        }
        String content = buildDhsDptext(baqid);
        for (ChasSb ad : chasSbs) {
            String ledid = ad.getSbbh();
            try {
                msg = "办案区:{"+baqid+"} 等候室 刷新大屏LED{"+ledid+"}成功";
                ledOper.setRowContent(baqid, ledid, content, 2, 0);
                log.debug(msg);
            } catch (DeviceException e) {
                log.error("refreshDhsDp:", e);
                wr.add("refreshDhsDp:", e);
            }
        }
        wr.setCode(1);
        wr.setMessage(String.format("办案区:%s 等候室 刷新大屏LED成功", baqid));
        return wr;
    }

    /**
     * 刷新审讯室LED
     * @param baqid 办案区id
     * @param qyId 审讯室id
     * @param rybh 人员编号
     * @return
     */
    @Override
    public DevResult refreshSxsLed(String baqid, String qyId, String rybh){
        log.debug(String.format("refreshSxsLed 办案区{%s}审讯室{%s}人员{%s}",baqid,qyId,rybh));
        DevResult wr = new DevResult();
        ChasRyjl ryjl = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if(ryjl == null){
            String msg=String.format("办案区%s 人员信息列表未找到编号%s",baqid, rybh);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        String sxsbh = ryjl.getSxsBh();
        if(StringUtils.isEmpty(sxsbh)){
            String msg = String.format("办案区%s 人员%s 未找到分配的审讯室",baqid, rybh);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        return refreshSxsLedBySxskz(baqid,qyId,sxsbh);
    }
    /**
     * 刷新审讯室LED
     * @param baqid 办案区id
     * @param qyId 审讯室id
     * @param sxskzId 审讯室人员分配情况id
     * @return
     */
    public DevResult refreshSxsLedBySxskz(String baqid, String qyId, String sxskzId) {
        if(chasQyService == null || chasSxsKzService == null){
            init();
        }
        Map<String,String> retData= new HashMap<>();
        DevResult wr = new DevResult();
        String msg;
        log.debug(String.format("办案区:%s 开始更新审讯室LED:%s", baqid, qyId));
        List<ChasSb> deviceList = getQySbsByLxAndGn(qyId, SYSCONSTANT.SBLX_LED, SYSCONSTANT.SBGN_SXSLED);
        if (deviceList == null || deviceList.size() == 0) {
            msg = String.format("该审讯室%s 未配置LED", qyId);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        log.debug(String.format("办案区:%s 审讯室的led数%d", baqid, deviceList.size()));
        String ids = deviceList.get(0).getSbbh();
        ChasXtQy chasXtQy = chasQyService.findByYsid(qyId);
        ChasSxsKz sxs = chasSxsKzService.findAllById(sxskzId);
        // 打包审讯室信息
        String ledText = buildSxsLedText( sxs);
        retData.put("symj",sxs.getSymj());
        log.debug(String.format("办案区:%s  审讯室LED信息:%s", baqid, ledText));
        //String title= buildSxsTitle(baqid, chasXtQy, sxs,retData);
        String title= chasXtQy.getQymc();
        retData.put("sxsmc",title);
        retData.put("xyrxm",sxs.getRyxm());
        retData.put("kssj", DateTimeUtils.getDateStr(sxs.getKssj(), 15));
        try {
            assembly(baqid,"1",ids,0,title, ledText);
            msg = String.format("办案区:%s 刷新审讯室LED%s成功", baqid, ids);
            wr.add("retData", retData);
            wr.setCode(1);
        } catch (DeviceException e) {
            log.error("refreshSxsLedBySxskz:", e);
            msg = String.format("办案区:%s 刷新审讯室LED%s 失败%s", baqid, ids, e.getMessage());
            wr.setCode(0);
        }
        wr.setMessage(msg);
        return wr;
    }

    /**
     * 根据已分配人员情况组装审讯室LED显示内容
     * @param sxskz
     * @return
     */
    private String buildSxsLedText(ChasSxsKz sxskz) {
        StringBuffer sb = new StringBuffer();
        if(chasSxsKzService.findcountByQyid(sxskz.getQyid())>0){
            //当前审讯室正在使用
            Map<String,Object> map = new HashMap<>();
            map.put("baqid", sxskz.getBaqid());
            map.put("qyid", sxskz.getQyid());
            List<ChasSxsKz> list = chasSxsKzService.findList(map, "lrsj desc");
            if(list == null||list.isEmpty()){
                //不应该出现此情况
                return "空闲";
            }
            sxskz = list.get(0);
            if (SYSCONSTANT.N_I.equals(sxskz.getIsdel())) {
                if (!StringUtils.isEmpty(sxskz.getRybh())) {
                    // 查询人员信息
                    String ryxm = StringUtils.isEmpty(sxskz.getRyxm()) ? "身份不明" : sxskz
                            .getRyxm();
                    sb.append(String.format("人员：%s 主办民警：%s 开始时间:%s ", ryxm,
                            sxskz.getSymj(),
                            DateTimeUtils.getDateStr(sxskz.getKssj(), 15)));
                } else {
                    sb.append(String.format("   民警:%s 正在办公  ", sxskz.getSymj()));
                }
                return sb.toString();
            }else{
                //不应该出现此情况
                return "空闲";
            }

        }else{
            return "空闲";
        }
    }

    //刷新审讯室大屏LED
    @Override
    public DevResult refreshSxsDxp(String baqid,String text) {
        if(chasQyService == null){
            init();
        }
        log.debug(String.format("refreshSxsDxp 办案区:%s 进入分配审讯室后刷新看守区Led屏方法", baqid));
        DevResult wr = new DevResult();
        String msg = "";
        List<ChasSb> sblist = new ArrayList<>();
        //大屏
        //sblist.addAll(getBaqSbsByLxAndGn(baqid, SYSCONSTANT.SBLX_LED, SYSCONSTANT.SBGN_DP));
        //小屏
        sblist.addAll(getBaqSbsByLxAndGn(baqid, SYSCONSTANT.SBLX_LED, SYSCONSTANT.SBGN_XP));
        log.debug(String.format("办案区:%s 大小屏的led数%d", baqid, sblist.size()));
        if (sblist == null || sblist.size() == 0) {
            msg = String.format("办案区:%s  未配置大小屏LED", baqid);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        String content = text;
//        if(StringUtils.isEmpty(content)){
//            content = buildSxsDxpLedText(baqid);
//        }
        for (ChasSb chasSb : sblist) {
            log.debug(String.format("办案区:{} 刷新审讯室大小屏LED:{}", baqid,
                    chasSb.getSbbh()));
            // 刷新看守区led灯 大小屏
            try {
                if (SYSCONSTANT.SBGN_DP.equals(chasSb.getSbgn())) {
                    sendDpled(baqid, chasSb.getSbbh(),
                            content, 3, 0);
                } else {
                    sendDpled(baqid, chasSb.getSbbh(),
                            content, 2, 0);
                }
                msg = String.format("办案区:%s 刷新审讯室大小屏LED完成！",baqid);
                wr.setCode(1);
            } catch (DeviceException e) {
                log.error("refreshSxsDxp:", e);
                wr.setCode(0);
                msg = String.format("办案区:%s 刷新审讯室大小屏LED失败", baqid);
            }
        }
        wr.setMessage(msg);
        return wr;
    }

    @Override
    public DevResult refreshKsqDp(List<ChasSb> ledList, String text,long delay,String kxtext) {
        if(ledList==null||ledList.isEmpty()|| StringUtils.isEmpty(text)){
            return DevResult.error("参数错误");
        }
        ledList.stream().forEach(led->{
            sendDpled(led.getBaqid(), led.getSbbh(),
                    text, 2, 0);

        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ledList.stream().forEach(led->{
                     sendDpled(led.getBaqid(), led.getSbbh(),
                            kxtext, 2, 0);
                });
            }
        }, delay);
        return DevResult.success("刷新看守区大屏成功");
    }

    /**
     * 组装办案区大小屏LED显示信息
     * @param baqid 办案区id
     * @return
     */
    private String buildSxsDxpLedText(String baqid){
        // 组装办案区下所有审讯室状态文字
        StringBuffer sbdp = new StringBuffer();
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_SXS);
        // 框架分页 每页条数问题
//        PageDataResultSet<ChasXtQySxs> list = chasQyService.getSxsRoomList(0,4, map, "kssj desc");
        List<ChasSxsKz> list = chasSxsKzService.findList(map, "kssj desc");
        int i = 0;
        for (ChasSxsKz sxs : list) {
            String rybh = sxs.getRybh();
            if (StringUtils.isNotEmpty(rybh)) {
                sbdp.append(sxs.getQymc());
                String ryxm = sxs.getRyxm();

                ryxm = StringUtils.isNotEmpty(ryxm) ? ryxm : "身份不明";
                sbdp.append(String.format(" 人员：%s 主办民警：%s 开始时间:%s ", ryxm,
                        sxs.getSymj(),
                        DateTimeUtils.getDateStr(sxs.getKssj(), 15)));
                i++;
            }
            if (i > 2) {
                break;
            }
        }
        if (StringUtils.isEmpty(sbdp.toString())) {
            sbdp.append("暂无审讯");
        }
        return sbdp.toString();
    }

    /**
     * 刷新民警房间的LED 显示审讯室使用情况
     * @param baqid 办案区id
     * @return
     */
    @Override
    public DevResult refreshMjRoom(String baqid) {
        DevResult wr = new DevResult();
        String msg = "";
        log.debug(String.format("refreshMjRoom 办案区:%s 进入分配民警休息室Led屏方法", baqid));
        //查找民警休息室led设备
        List<ChasSb> sblist = new ArrayList<>();
        sblist.addAll(getBaqSbsByLxAndGn(baqid, SYSCONSTANT.SBLX_LED, SYSCONSTANT.SBGN_MJXXSLED));
        log.debug(String.format("办案区:%s 民警休息室Led屏的led数%d", baqid,
                sblist.size()));
        if (sblist == null || sblist.size() == 0) {
            msg = String.format("办案区:%s  未配置民警休息室Led屏", baqid);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        // 组装办案区下所有审讯室状态文字
        String content =buildSxsDxpLedText(baqid);
        for (ChasSb chasSb : sblist) {
            log.debug(String.format("办案区:%s 刷新民警休息室LED:%s", baqid,
                    chasSb.getSbbh()));
            try {
                assembly(baqid, "1", chasSb.getSbbh(), 0, "审讯室分配情况",content);
            } catch (DeviceException e) {
                log.error("refreshMjRoom:", e);
            }
        }
        wr.setCode(1);
        wr.setMessage(String.format("办案区:%s 刷新民警休息室LED成功", baqid));
        return wr;
    }

    /**
     * 刷新亲情驿站LED
     * @param baqid
     * @param text
     * @return
     */
    @Override
    public DevResult refreshStation(String baqid, String text) {
        DevResult wr = new DevResult();
        String msg = "";
        log.debug(String.format("refreshStation 办案区:%s 刷新亲情驿站Led屏方法", baqid));
        //查找所有小屏Led设备
        List<ChasSb> sblist = new ArrayList<>();
        sblist.addAll(getBaqSbsByLxAndGn(baqid, SYSCONSTANT.SBLX_LED, SYSCONSTANT.SBGN_XP));
        log.debug(String.format("办案区:%s 小屏的led数%d", baqid, sblist.size()));
        if (sblist == null || sblist.isEmpty()) {
            msg = String.format("办案区:%s  未配置亲情驿站小屏LED", baqid);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        for (ChasSb chasSb : sblist) {
            log.debug(String.format("办案区:{%s} 刷新亲情驿站LED:{%s}", baqid,chasSb.getSbbh()));
            // 刷新看守区led灯 大小屏
            try {
                DevResult result = DevResult.success("成功");
                sendDpled(baqid, chasSb.getSbbh(), text, 1, 0);
                wr.add(chasSb.getSbbh(), result);
            } catch (DeviceException e) {
                log.error("refreshStation:", e);
            }
        }
        wr.setCode(1);
        wr.setMessage(String.format("办案区:%s 刷新亲情驿站LED成功", baqid));
        return wr;
    }

    /**
     * 根据类型和功能查找区域关联设备
     * @param qyid 区域编号
     * @return
     */
    private List<ChasSb> getQySbsByLxAndGn(String qyid,String lx,String gn) {
        Map<String, Object> map = new HashMap<>();
        map.put("qyid", qyid);
        map.put("sblx", lx);
        map.put("sbgn", gn);
        if(chasSbService == null){
            chasSbService= ServiceContext.getServiceByClass(ChasSbService.class);
        }
        return chasSbService.findByParams(map);
    }

    /**
     * 根据类型和功能查找办案区域设备
     * @param baqid 办案区id
     * @param lx 设备类型
     * @param gn 设备功能
     * @return
     */
    private List<ChasSb> getBaqSbsByLxAndGn(String baqid,String lx,String gn) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", lx);
        map.put("sbgn", gn);
        if(chasSbService == null){
            init();
        }
        return chasSbService.findByParams(map);
    }

    /**
     * 组装办案区等候室大屏LED显示信息
     * @param baqid 办案区id
     * @return
     */
    private String buildDhsDptext(String baqid){
        Map<String, Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        map.clear();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        //办案区所有等候室
        List<ChasXtQy> qyList = chasQyService.findByParams(map);
        if(qyList != null && !qyList.isEmpty()){
            qyList.stream().forEach(qy->{
                String areaname = DicUtil.translate("dhslx", qy.getKzlx())
                        + qy.getYsbh();
                String id = qy.getYsid();
                //查询等候室分配情况
                map.clear();
                map.put("qyid", id);
                map.put("isdel", SYSCONSTANT.ALL_DATA_MARK_NORMAL);
                //等候室分配情况
                List<ChasDhsKz> dhslist = chasDhsKzService.findByParams(map);
                String text = buildDhsLedText(dhslist);
                if (!StringUtils.isEmpty(text)) {
                    if ((sb.toString().length() + text.length()) < 110) {
                        sb.append(areaname + "：" + text);
                    }
                }
            });
        }

        if (StringUtils.isEmpty(sb.toString())) {
            sb.append("看守区：空闲");
        }
        return sb.toString();
    }

    /**
     * 根据等候室分配情况组装LED显示内容信息
     * @param qyid 等候室id
     * @return
     */
    private String buildWaitingRoomLedText(String qyid){
        Map<String, Object> map = new HashMap<>();
        map.put("qyid", qyid);
        map.put("isdel", 0);
        List<ChasDhsKz> dhslist = chasDhsKzService.findList(map, null);
        return buildDhsLedText(dhslist);
    }

    /**
     * 根据等候室分配情况组装LED显示内容信息
     * @param dhslist 等候室分配人员情况
     * @return
     */
    private String buildDhsLedText(List<ChasDhsKz> dhslist){
        StringBuffer sbf = new StringBuffer();
        if (dhslist != null && !dhslist.isEmpty()) {
            dhslist.stream().filter(dhs ->SYSCONSTANT.DHSTZ_FP.equals(dhs.getFpzt())&&dhs.getIsdel()==0)
                    .forEach(dhs ->{
                        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(dhs.getRybh());
                        if(baqryxx!=null&&"01".equals(baqryxx.getRyzt())){
                            if(!StringUtils.isEmpty(dhs.getRyxm())){
                                sbf.append(baqryxx.getRyxm() + " ");
                            }else {
                                sbf.append("身份不明  ");
                            }
                        }
                    });
        }
        if (sbf.length() == 0) {
            sbf.append("空闲");
        }
        return sbf.toString();
    }

    /**
     *
     * @param org
     * @param deviceType
     * @param id
     * @param showTime
     * @param texts
     * @return
     */
    private Future<DevResult> assembly(String org, String deviceType, String id, Integer showTime, String... texts){
        List<String> textList = new ArrayList<>();
        for (String text : texts) {
            textList.add(text);
        }

        return CompletableFuture.supplyAsync(()->ledOper.ledSend(org, deviceType, id, textList, showTime));
    }

    private Future<DevResult> sendDpled(String baqid, String sbbh,String  text,int line , int show){
        return CompletableFuture.supplyAsync(()->ledOper.setRowContent(baqid, sbbh, text, line, show));
    }
}
