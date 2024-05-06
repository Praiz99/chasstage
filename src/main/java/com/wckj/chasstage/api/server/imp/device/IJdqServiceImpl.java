package com.wckj.chasstage.api.server.imp.device;


import com.wckj.api.def.zfba.model.sjjh.SjjhDingtalkMsg;
import com.wckj.api.def.zfba.service.sjjh.ApiSjjhDingtalkMsgService;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import com.wckj.jdone.modules.rmd.service.JdoneRmdMsgService;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IJdqServiceImpl extends BaseDevService implements IJdqService {
    private static final Logger log = Logger.getLogger(IJdqServiceImpl.class);
    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private JdoneSysUserService userService;
    /**
     * 消息提醒
     */
    private static String YJFS_SYSMSG = "02";
    /**
     * 报警器提醒
     */
    private static String YJFS_BJQTX = "03";
    /**
     * 钉消息提醒
     */
    private static String YJFS_DXXTX = "06";
    /**
     * 预警信息提醒模版
     */
    private static String YJXX_MODEL = "{0}预警消息：触发人 {1} 于 触发区域 {2} 触发 {3} 预警，请管理员及时处理。";
    /**
     * 预警
     */
    public static String XXLX_YJXX = "XXLX_YJXX";
    /**
     * 打开或关闭审讯室继电器
     * @param baqid 办案区id
     * @param onoff 0 开启 1 关闭
     * @param qyid 区域id
     * @return
     */
    @Override
    public DevResult openOrColseXxs(String baqid, String qyid, Integer onoff) {
        if(chasSbService == null){
            init();
        }
        DevResult wr = new DevResult();
        log.debug(String.format("IJdqServiceImpl.openOrColseXxs 办案区:{%s} 区域{%s} 打开继电器:{%d}", baqid, qyid,onoff));
        //查找该审讯室继电器设备
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", SYSCONSTANT.SBLX_JDQ);
        map.put("qyid", qyid);
        map.put("nosbgn1",SYSCONSTANT.SBGN_ZNQT);//非智能墙体
        map.put("nosbgn2",SYSCONSTANT.SBGN_DKQQDH);//非亲情电话
        map.put("nosbgn3","10");//非排风扇
        List<ChasSb> chasSblist = chasSbService.findByParams(map);
        if (chasSblist == null||chasSblist.isEmpty()) {
            String msg = String.format("办案区{%s}审讯室{%s}未配置继电器", baqid, qyid);
            log.error(msg);
            wr.setCodeMessage(0,msg);
            return wr;
        }
        //控制每个继电器设备
        chasSblist.stream().forEach(sb -> {
            DevResult jdq = jdqOper.openOrClose(baqid, sb.getSbbh(), onoff);
            if(jdq.getCode()==1){
                sb.setKzcs3(""+onoff);
                chasSbService.update(sb);
            }
            wr.add(sb.getSbbh(), jdq);
        });
        wr.setCodeMessage(1,String.format("办案区:{%s}审讯室{%s} 控制继电器完成", baqid,qyid));
        return wr;
    }

    /**
     * 打开单个或多个继电器
     * @param baqid
     * @param onoff
     * @param sbbh
     * @return
     */
    @Override
    public DevResult openOrClose(String baqid, Integer onoff, String sbbh) {
        if(jdqOper == null){
            init();
        }
        DevResult wr = new DevResult();
        //for (String onesbbh:sbbh) {
            //wr.add(onesbbh, jdqOper.openOrClose(baqid, onesbbh, onoff));
        //}
        return jdqOper.openOrClose(baqid, sbbh, onoff);
    }


    /**
     * 打开并延时关闭继电器
     * @param baqid
     * @param sbbh
     * @param hms  延时关闭时间
     * @return
     */
    @Override
    public DevResult openRelayBytime(String baqid, String sbbh,long hms) {
        if(jdqOper == null){
            init();
        }
        log.debug(String.format("IJdqServiceImpl.openRelayBytime 办案区{%s} 设备{%s} 时间{%d}",baqid,sbbh,hms));
        return jdqOper.openDelay(baqid, sbbh, hms);
    }

    /**
     * 打开或者关闭继电器报警
     * @param baqid
     * @param onoff 开关
     * @return
     */
    private DevResult alarmRelay(String baqid, Integer onoff) {
        if(jdqOper == null){
            init();
        }
        log.debug(String.format("IJdqServiceImpl.alarmRelay 办案区{%s} 开关{%d}",baqid,onoff));
        DevResult wr = DevResult.success("关闭报警器成功");
        //查找办案区所有报警器
        List<ChasSb> chasSblist = chasSbService.getBaqSbsByLxAndGn(baqid,SYSCONSTANT.SBLX_JDQ,SYSCONSTANT.SBGN_YJJB);
        if (chasSblist.isEmpty()) {
            String msg =String.format("办案区:%s  未配置警报继电器", baqid);
            log.error(msg);
            return DevResult.error(msg);
        }
        //控制每个继电器设备
        chasSblist.stream().forEach(sb -> {
            DevResult jdq = jdqOper.openOrClose(baqid, sb.getSbbh(), onoff);
            if(jdq.getCode()==1){
                sb.setKzcs3(""+onoff);
                chasSbService.update(sb);
            }
            wr.add(sb.getSbbh(), jdq);
        });
        return wr;
    }


    @Override
    public DevResult closeAlarm(String baqid) {
        return alarmRelay(baqid, SYSCONSTANT.OFF);
    }

    @Override
    public DevResult openAlarm(String baqid,long time) {
        if(jdqOper == null){
            init();
        }
        log.debug(String.format("IJdqServiceImpl.openAlarm 办案区{%s} 打开报警",baqid));
        DevResult wr = DevResult.success("打开报警器成功");
        //查找办案区所有报警器
        List<ChasSb> chasSblist = chasSbService.getBaqSbsByLxAndGn(baqid,SYSCONSTANT.SBLX_JDQ,SYSCONSTANT.SBGN_YJJB);
        if (chasSblist.isEmpty()) {
            String msg =String.format("办案区:%s  未配置警报继电器", baqid);
            log.error(msg);
            return DevResult.error(msg);
        }
        //String alarmTimeStr = SysUtil.getParamValue("OPEN_ALARM_TIME");
        //long time = Long.parseLong(alarmTimeStr)*1000;
        chasSblist.stream().map(chasSb-> chasSb.getSbbh()).forEach(sbbh->
                wr.add(sbbh,openRelayBytime(baqid, sbbh,time))
        );
        return wr;
    }

    @Override
    public DevResult getQqdhRec(ChasQqyz qqyz) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String thkssj=sdf.format(qqyz.getThkssj());
        String thjssj=sdf.format(qqyz.getThjssj());
        return jdqOper.getQqdhRec(qqyz.getBaqid(), qqyz.getLxrdh(), thkssj, thjssj);
    }

    @Override
    public DevResult openOrColsePfs(String baqid, String qyid, Integer onoff) {
        if(chasSbService == null){
            init();
        }
        DevResult wr = new DevResult();
        log.debug(String.format("IJdqServiceImpl.openOrColsePfs 办案区:{%s} 区域{%s} 打开排风扇继电器:{%d}", baqid, qyid,onoff));
        //查找该审讯室继电器设备
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", SYSCONSTANT.SBLX_JDQ);
        map.put("qyid", qyid);
        map.put("sbgn","10");//排风扇
        List<ChasSb> chasSblist = chasSbService.findByParams(map);
        if (chasSblist == null||chasSblist.isEmpty()) {
            String msg = String.format("办案区{%s}审讯室{%s}未配置排风扇继电器", baqid, qyid);
            log.error(msg);
            msg="未配置排风扇继电器";
            wr.setCodeMessage(0,msg);
            return wr;
        }
        //控制每个继电器设备
        chasSblist.stream().forEach(sb -> {
            DevResult jdq = jdqOper.openOrClose(baqid, sb.getSbbh(), onoff);
            if(jdq.getCode()==1){
                sb.setKzcs3(""+onoff);
                chasSbService.update(sb);
            }
            wr.add(sb.getSbbh(), jdq);
        });
        wr.setCodeMessage(1,String.format("办案区:{%s}审讯室{%s} 控制继电器完成", baqid,qyid));
        return wr;
    }

    /**
     * 发送预警消息提醒
     * @param chasYjxx
     * @param yjmc
     * @param yjfs
     * @param yjsc
     * @return
     */
    @Override
    public  boolean sendYjxxmsg(ChasYjxx chasYjxx, String yjmc,String yjfs,Integer yjsc) {
        try {
            String paramLog = MessageFormat.format("baqid:{0}, 触发{1},预警方式{2}", chasYjxx.getBaqid(), yjmc, yjfs);
            log.debug(paramLog);
            if (StringUtils.isEmpty(yjfs)) {
                log.debug(yjmc + "预警方式为空，无需触发");
                return false;
            }
            String msg = "";
            if ("巡更".equals(yjmc)) {
                msg = MessageFormat.format("{0}预警消息：触发区域 {1} 触发 {2} 预警，请管理员及时处理。", yjmc, chasYjxx.getCfqymc(), yjmc);
            }else if ("人员聚集".equals(yjmc)) {
                msg = MessageFormat.format("{0}警消息：{1}当前有{2}，请管理员及时处理。", yjmc, chasYjxx.getCfqymc(), chasYjxx.getCfrxm());
            } else {
                msg = MessageFormat.format(YJXX_MODEL, yjmc, chasYjxx.getCfrxm(), chasYjxx.getCfqymc(), yjmc);
            }
            if (yjfs.indexOf(YJFS_SYSMSG) > -1) {
                sendRmdMsg(yjmc + "预警消息提醒", msg, XXLX_YJXX, yjmc + "消息", chasYjxx.getId(), chasYjxx.getBaqid(),chasYjxx.getYjlb());
            }
            if (yjfs.indexOf(YJFS_BJQTX) > -1) {
                // 报警器报警
                if (yjsc == null || yjsc < 0) {
                    yjsc = 5;
                }
                openAlarm(chasYjxx.getId(), yjsc * 60000);
            }
            if (yjfs.indexOf(YJFS_DXXTX) > -1) {
                sendZztMsgToZfba(chasYjxx,yjmc);

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(yjmc + "消息提醒失败", e);
        }
        return false;
    }
    protected void  sendZztMsgToZfba(ChasYjxx chasYjxx,String yjmc){
        try {
            ApiSjjhDingtalkMsgService apiSjjhDingtalkMsgService = ServiceContext.getServiceByClass(ApiSjjhDingtalkMsgService.class);
            SjjhDingtalkMsg dingtalk=new SjjhDingtalkMsg();
            ChasBaqryxx ryxx = baqryxxService.findByRybh(chasYjxx.getCfrid());
            JdoneSysUser user = userService.findSysUserByIdCard(ryxx.getMjSfzh());
            dingtalk.setTitle("预警消息提醒");
            dingtalk.setContent(ryxx.getRyxm()+"于"+ DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss")+yjmc+"，请及时处理。");
            dingtalk.setTsxx(chasYjxx.getJqms());
            dingtalk.setSjch(user.getPhone());
            String jjcd="";
            switch (chasYjxx.getYjjb()) {
                case "4":jjcd="1";break;
                case "3":jjcd="2";break;
                case "2":jjcd="3";break;
                case "1":jjcd="4";break;
                default:jjcd="0";break;
            }
            dingtalk.setJjcd(jjcd);
            dingtalk.setIsdel(0);
            dingtalk.setIscl("1");
            dingtalk.setDataFlag("");
            dingtalk.setId(StringUtils.getGuid32());
            dingtalk.setIsdx("1");
            dingtalk.setLrsj(new Date());
            dingtalk.setXgsj(new Date());
            dingtalk.setXxlx("1");
            dingtalk.setXxtype("1");
            dingtalk.setXxval(ryxx.getRysfzh());
            apiSjjhDingtalkMsgService.saveDingtalkMsgData(dingtalk);
            log.debug(yjmc + "预警开始发送钉消息，办案区【" + chasYjxx.getId() + "】");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendZztMsgToZfba错误:",e);
        }
    }
    /**
     * 消息发送
     *
     * @param title       标题
     * @param content     内容
     * @param msgType     消息类型
     * @param msgTypeName 消息类型名称
     * @param bizId       业务id
    `    * @param yjlb        预警类别(判断强制提醒)
     */
    private static void sendRmdMsg(String title, String content, String msgType, String msgTypeName, String bizId, String baqid,String yjlb) {
        List<JdoneSysUser> userList = new ArrayList<>();
        log.debug("办案区{" + baqid + "}，发送" + msgTypeName + "：" + content);
        if (StringUtils.isEmpty(bizId)) {
            bizId = StringUtils.getGuid32();
        }
        JdoneRmdMsgService jdoneRmdMsgService = ServiceContext.getServiceByClass(JdoneRmdMsgService.class);
        userList = getNeedSendUsers(baqid);
        for (int i = 0; i < userList.size(); i++) {
            JdoneSysUser jdoneSysUser = userList.get(i);
            JdoneRmdMsg rmdMsg = new JdoneRmdMsg();
            rmdMsg.setId(StringUtils.getGuid32());
            rmdMsg.setBizId(bizId);
            rmdMsg.setBizType(msgType);
            rmdMsg.setClrDwbh(jdoneSysUser.getOrgCode());
            rmdMsg.setClrDwmc(jdoneSysUser.getOrgName());
            rmdMsg.setClrDwxtbh(jdoneSysUser.getOrgSysCode());
            rmdMsg.setClrXm(jdoneSysUser.getName());
            rmdMsg.setClrSfzh(jdoneSysUser.getIdCard());
            rmdMsg.setCreateTime(new Date());
            rmdMsg.setMsgTitle(title);
            rmdMsg.setMsgContent(content);
            rmdMsg.setMsgStatus("00");
            rmdMsg.setMsgType(msgType);
            rmdMsg.setMsgTypeName(msgTypeName);
            rmdMsg.setIsdel(0);
            rmdMsg.setDataFlag("0");
            rmdMsg.setSendObjName(jdoneSysUser.getName());
            rmdMsg.setSendObjMark(jdoneSysUser.getIdCard());
            rmdMsg.setSendOrgCode(jdoneSysUser.getOrgCode());
            rmdMsg.setSendOrgName(jdoneSysUser.getOrgName());
            rmdMsg.setSendOrgSysCode(jdoneSysUser.getOrgSysCode());
            rmdMsg.setRecObjName(jdoneSysUser.getName());
            rmdMsg.setRecObjMark(jdoneSysUser.getIdCard());
            rmdMsg.setRecOrgCode(jdoneSysUser.getOrgCode());
            rmdMsg.setRecOrgName(jdoneSysUser.getOrgName());
            rmdMsg.setRecOrgSysCode(jdoneSysUser.getOrgSysCode());
            jdoneRmdMsgService.saveRmdMsg(rmdMsg);
            log.debug("办案区{" + baqid + "}，发送" + msgTypeName + ",给【" + jdoneSysUser.getName() + "】成功");
        }
        log.debug("办案区{" + baqid + "}，发送" + msgTypeName + "成功");
    }
    private static List<JdoneSysUser> getNeedSendUsers(String baqid) {
        JdoneSysUserService userService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
        ChasBaqService baqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        ChasBaq baq = baqService.findById(baqid);
        if (baq == null) {
            throw new BizDataException("办案区【" + baqid + "】不存在");
        }
        log.debug("开始查询办案区【" + baqid + "】角色代码400008的用户");
        List<JdoneSysUser> userList = userService.findUsersByRegAndRole(new String[]{"400008"}, baq.getDwxtbh());
        if (userList != null) {
            log.debug("开始查询办案区【" + baqid + "】用户结果" + userList.size());
        } else {
            log.debug("开始查询办案区【" + baqid + "】用户结果为空");
        }
        return userList;
    }
}
