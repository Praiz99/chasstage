package com.wckj.chasstage.common.util;

import com.wckj.api.def.qgc.model.QgcGgWjApi;
import com.wckj.api.def.qgc.model.QgcGgWjglApi;
import com.wckj.api.def.qgc.servive.QgcApiGgwjService;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BaqrysypUtil {
    private static final Logger log = LoggerFactory.getLogger(BaqrysypUtil.class);

    /**
     * 办案区人员音视频保存方法
     * @param ryxx
     * @param fjlx  房间类型  6 办案区入口  7 办案区出口  8 办案区出口（临时出所） 9 办案区入口（人员归所）
     * @param gllx  关联类型  300...
     */
    public static void uplodsaveAudioVideoByRyjc(ChasBaqryxx ryxx, String fjlx, String gllx){
        //保存人员进出音视频
        QgcApiGgwjService ggwjService = ServiceContext.getServiceByClass(QgcApiGgwjService.class);
        ChasXtQyService qyService = ServiceContext.getServiceByClass(ChasXtQyService.class);
        ChasBaqService chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        JdoneSysUserService userService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
        String fjlx_str = fjlx;
        if(fjlx.equals("8")){
            fjlx_str = "7";
        }
        if(fjlx.equals("9")){
            fjlx_str = "6";
        }
        try {
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("baqid",ryxx.getBaqid());
            params.put("fjlx",fjlx_str);  //6 办案区入口，7 办案区出口
            String qyid = "",qymc = "";
            List<ChasXtQy> qyList = qyService.findList(params,null);
            if(!qyList.isEmpty()){
                ChasXtQy qy = qyList.get(0);
                qyid = qy.getId();
                qymc = qy.getQymc();
            }

            QgcGgWjApi wjApi = new QgcGgWjApi();
            wjApi.setDwdm(ryxx.getZbdwBh());
            wjApi.setDwmc(ryxx.getZbdwMc());
            wjApi.setDwxtbh(ryxx.getDwxtbh());
            //使用该办案区责任单位代码
            ChasBaq baq = chasBaqService.findById(ryxx.getBaqid());
            if(baq != null){
                wjApi.setDwdm(baq.getDwdm());
                wjApi.setDwmc(baq.getDwmc());
                wjApi.setDwxtbh(baq.getDwxtbh());
            }

            Date kssj = new Date();
            Date jssj = new Date();
            if(StringUtil.equals(fjlx,"6")){  //入所
                if(ryxx.getRRssj() != null){
                    kssj = ryxx.getRRssj();
                }
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(kssj);
                rightNow.add(Calendar.MINUTE,-10);
                kssj = rightNow.getTime();

                if(ryxx.getRRssj() != null){
                    jssj = ryxx.getRRssj();
                }
                rightNow.setTime(jssj);
                rightNow.add(Calendar.MINUTE,10);
                jssj = rightNow.getTime();

                wjApi.setSc(Integer.parseInt(jssj.getTime() - kssj.getTime()+""));
            }
            if(StringUtil.equals(fjlx,"7")){  //出所
                kssj = ryxx.getCCssj();
                if(ryxx.getCCssj() == null){
                    kssj = new Date();
                }

                jssj = ryxx.getCCssj();
                if(ryxx.getCCssj() == null){
                    jssj = new Date();
                }
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(jssj);
                rightNow.add(Calendar.MINUTE,30);
                jssj = rightNow.getTime();

                wjApi.setSc(Integer.parseInt(jssj.getTime() - kssj.getTime()+""));
            }

            if(StringUtils.equals(fjlx,"8")){  //临时出所
                kssj = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(kssj);
                calendar.add(Calendar.MINUTE,-10);
                kssj = calendar.getTime();

                jssj = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(jssj);
                calendar.add(Calendar.MINUTE,10);
                jssj = calendar.getTime();

                wjApi.setSc(Integer.parseInt(jssj.getTime() - kssj.getTime()+""));
            }

            if(StringUtils.equals(fjlx,"9")){  //人员归所（临时出所,入所）
                kssj = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(kssj);
                calendar.add(Calendar.MINUTE,-10);
                kssj = calendar.getTime();

                jssj = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(jssj);
                calendar.add(Calendar.MINUTE,10);
                jssj = calendar.getTime();

                wjApi.setSc(Integer.parseInt(jssj.getTime() - kssj.getTime()+""));
            }

            wjApi.setKssj(kssj);
            wjApi.setJssj(jssj);
            wjApi.setBcqx("001");  //保存期限  6个月
            wjApi.setCjfs("001");  //采集方式  监控设备
            wjApi.setDd(qymc);
            if(StringUtils.equals(fjlx,"6") || StringUtils.equals(fjlx,"9")){
                wjApi.setWjmc(ryxx.getRyxm()+"_入所音视频");
            }
            if(StringUtils.equals(fjlx,"7") || StringUtils.equals(fjlx,"8")){
                wjApi.setWjmc(ryxx.getRyxm()+"_人员出所音视频");
            }
            QgcGgWjglApi wjglApi = new QgcGgWjglApi();
            wjglApi.setGllx(gllx);  //300
            wjglApi.setYwbh(ryxx.getRybh());
            wjglApi.setYwlx("001");  //视音频
            JdoneSysUser user = userService.findSysUserByIdCard(ryxx.getMjSfzh());
            Map<String,Object> objectMap = ggwjService.saveWjAndWjglForRyjc(wjApi,wjglApi,qyid,user.getId());
           log.info("上传人员进出音视频成功!"+ryxx.getRyxm()+"_"+fjlx);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传保存人员进出音视频失败:",e);
        }
    }

    public static void uploadSaveAudioVideoByAqjc(ChasBaqryxx ryxx, String fjlx, String gllx){
        //保存人员安全检查音视频
        QgcApiGgwjService ggwjService = ServiceContext.getServiceByClass(QgcApiGgwjService.class);
        ChasXtQyService qyService = ServiceContext.getServiceByClass(ChasXtQyService.class);
        ChasBaqService chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        JdoneSysUserService userService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
        try {
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("baqid",ryxx.getBaqid());
            params.put("fjlx",fjlx); //信息采集
            String qyid = "",qymc = "";
            List<ChasXtQy> qyList = qyService.findList(params,null);
            if(!qyList.isEmpty()){
                ChasXtQy qy = qyList.get(0);
                qyid = qy.getId();
                qymc = qy.getQymc();
            }
            QgcGgWjApi wjApi = new QgcGgWjApi();
            wjApi.setDwdm(ryxx.getZbdwBh());
            wjApi.setDwmc(ryxx.getZbdwMc());
            wjApi.setDwxtbh(ryxx.getDwxtbh());
            //使用该办案区责任单位代码
            ChasBaq baq = chasBaqService.findById(ryxx.getBaqid());
            if(baq != null){
                wjApi.setDwdm(baq.getDwdm());
                wjApi.setDwmc(baq.getDwmc());
                wjApi.setDwxtbh(baq.getDwxtbh());
            }
            wjApi.setKssj(ryxx.getAqjckssj());
            wjApi.setJssj(ryxx.getAqjcjssj());
            wjApi.setSc(Integer.parseInt(ryxx.getAqjcjssj().getTime() - ryxx.getAqjckssj().getTime()+""));
            wjApi.setBcqx("001");  //保存期限  6个月
            wjApi.setCjfs("001");  //采集方式  监控设备
            wjApi.setDd(qymc);
            wjApi.setWjmc(ryxx.getRyxm()+"_安全检查音视频");
            QgcGgWjglApi wjglApi = new QgcGgWjglApi();
            wjglApi.setGllx(gllx);
            wjglApi.setYwbh(ryxx.getRybh());
            wjglApi.setYwlx("001");  //视音频
            JdoneSysUser user = userService.findSysUserByIdCard(ryxx.getMjSfzh());
            Map<String,Object> result = ggwjService.saveWjAndWjglForRyjc(wjApi,wjglApi,qyid,user.getId());
            if((boolean)result.get("success")){
                log.info("上传人员安全检查音视频成功!"+ryxx.getRyxm()+"_"+fjlx);
            }else{
                log.info("上传人员安全检查音视频失败:"+result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传保存人员安全检查音视频失败:",e);
        }
    }

    /**
     * 保存音视频记录根据人员轨迹信息
     * @param rygj
     */
    public static void uplodsaveAudioVideoByRygj(ChasRygj rygj){
        //人员轨迹
        QgcApiGgwjService qgcApiGgwjService = ServiceContext.getServiceByClass(QgcApiGgwjService.class);
        ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        JdoneSysUserService userService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
        try {
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("rybh",rygj.getRybh());
            List<ChasBaqryxx> baqryxxes = baqryxxService.findList(params,"");
            if(!baqryxxes.isEmpty()){
                ChasBaqryxx baqryxx = baqryxxes.get(0);
                QgcGgWjApi wjApi = new QgcGgWjApi();
                wjApi.setDwdm(baqryxx.getZbdwBh());
                wjApi.setDwmc(baqryxx.getZbdwMc());
                wjApi.setDwxtbh(baqryxx.getDwxtbh());
                wjApi.setKssj(rygj.getKssj());
                wjApi.setJssj(rygj.getJssj());
                wjApi.setSc(Integer.parseInt(rygj.getJssj().getTime() - rygj.getKssj().getTime()+""));
                wjApi.setBcqx("001");   //6个月
                wjApi.setCjfs("001");  //监控设备
                wjApi.setWjmc(baqryxx.getRyxm()+"_"+rygj.getQymc()+"_轨迹音视频");
                wjApi.setDd(rygj.getQymc());
                QgcGgWjglApi wjglApi = new QgcGgWjglApi();
                wjglApi.setGllx("302");  //人员轨迹
                wjglApi.setYwbh(baqryxx.getRybh());
                wjglApi.setYwlx("001");  //视音频
                JdoneSysUser user = userService.findSysUserByIdCard(baqryxx.getMjSfzh());
                Map<String,Object> objectMap = qgcApiGgwjService.saveWjAndWjglForRygj(wjApi,wjglApi,rygj.getQyid(),user.getId());
                log.info("上传人员轨迹音视频成功!"+baqryxx.getRyxm()+"_"+rygj.getQymc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存人员轨迹视音频失败:",e);
        }
    }
}
