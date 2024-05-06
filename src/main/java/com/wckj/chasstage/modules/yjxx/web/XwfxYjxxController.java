package com.wckj.chasstage.modules.yjxx.web;

import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping(value = "api/chasstage/xwfx")
public class XwfxYjxxController {

    final static Logger log = Logger.getLogger(XwfxYjxxController.class);

    @Autowired
    private IJdqService jdqService;
    @Autowired
    private ChasYjxxService chasYjxxService;

    @Autowired
    private ChasYjlbService chasYjlbService;

    @Autowired
    private ChasSxsKzService chasSxsKzService;

    @Autowired
    private ChasXtQyService chasQyService;

    @RequestMapping(value = "/alarm")
    @ResponseBody
    public void alarm(String qyid, HttpServletRequest request, String baqid) {
        log.info("触发行为分析！！");
        Document doc = null;
        LocationEventInfo locationEventInfo = new LocationEventInfo();
        Map<String, Object> params = new HashMap<>(16);
        ChasYjxx chasYjxx = new ChasYjxx();
        ChasXtQy qy = new ChasXtQy();
        try {
            request.setCharacterEncoding("UTF-8");
            ServletInputStream in = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder content = new StringBuilder();

            char[] b = new char[1024];
            int lens = -1;
            while ((lens = reader.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            String strcont = URLDecoder.decode(content.toString(), "UTF-8");// 内容
//            log.debug("content=========》" + content.toString());
            log.info("行为分析内容=========》" + strcont);

            if (strcont.startsWith("{")) {
                Map<String, Object> strMap = JSONObject.parseObject(strcont, Map.class);

                Map<String, Object> targetAttrs = (Map<String, Object>) strMap.get("targetAttrs");
                String eventType = captureName((String) strMap.get("eventType"));
                log.debug("行为分析eventType：========》" + eventType);
                Map<String, Object> eventName = (Map<String, Object>) strMap.get(eventType);
                Map<String, Object> TaskInfo = (Map<String, Object>) eventName.get("TaskInfo");

                locationEventInfo.setEquipNo((String) targetAttrs.get("deviceId")); //"deviceId":"314c8892dce3499584730c88aa37c7fb",
                locationEventInfo.setAreaId("");
                String sxt = (String) targetAttrs.get("taskname");
                locationEventInfo.setDisc(sxt.split("-")[0] + "监控到：" + (String) TaskInfo.get("ruleCustomName"));// "ruleCustomName":"折线攀高"

                qyid = sxt.split("-")[1];
                params.put("qyid", qyid);
                qy = chasQyService.findById(qyid);
                if(null == qy){
                    log.error("查询失败，区域id：" + qyid + "查询不到审讯室信息");
                    return;
                }
                params.put("qyid",qy.getYsid());
                List<ChasSxsKz> chasSxsKzList = chasSxsKzService.findList(params, " xgsj desc");

                chasYjxx.setId(StringUtils.getGuid32());
                String baqmc = qy.getBaqmc();
                chasYjxx.setBaqid(baqid);
                chasYjxx.setBaqmc(baqmc);
                chasYjxx.setLrsj(new Date());
                chasYjxx.setCfqyid(qyid);
                chasYjxx.setCfqymc(qy.getQymc());
                chasYjxx.setYjjb(SYSCONSTANT.YJJB_YJ);

                /**
                 * 拼接公共的预警内容
                 */
                chasYjxx.setJqms("区域:" + qy.getQymc() + "出现" + (String) TaskInfo.get("ruleCustomName") + "！");


                locationEventInfo.setTagNo((String) targetAttrs.get("taskname"));//"taskname":"123-监所",
//                locationEventInfo.setExpandParm("");//行为图片预览地址
                if (StringUtils.equals(eventType, "ViolentMotion")) {
                    //剧烈运动
                    locationEventInfo.setEventType(115); //行为分析预警
                } else if (StringUtils.equals(eventType, "GetUp")) {
                    //起身检测
                    locationEventInfo.setEventType(116);
                } else if (StringUtils.equals(eventType, "KeyPersonGetUp")) {
                    //重点人员起身检测
                    locationEventInfo.setEventType(117);
                } else if (StringUtils.equals(eventType, "LeavePosition")) {
                    //离岗检测
                    locationEventInfo.setEventType(118);
                } else if (StringUtils.equals(eventType, "StandUp")) {
                    //人员站立检测
                    locationEventInfo.setEventType(119);
                } else if (StringUtils.equals(eventType, "AudioAbnormal")) {
                    //声强突变检测
                    locationEventInfo.setEventType(120);
                } else if (StringUtils.equals(eventType, "AdvReachHeight")) {
                    //折线攀高检测
                    locationEventInfo.setEventType(121);
                    chasYjxx.setJqms("区域:" + qy.getQymc() + "出现攀高行为！");
                } else if (StringUtils.equals(eventType, "ToiletTarry")) {
                    //如厕超时检测
                    locationEventInfo.setEventType(122);
                } else if (StringUtils.equals(eventType, "YardTarry")) {
                    //放风场滞留检测
                    locationEventInfo.setEventType(123);
                } else if (StringUtils.equals(eventType, "PeopleNumChange")) {
                    //人数异常
                    locationEventInfo.setEventType(124);//人员聚集
                } else if (StringUtils.equals(eventType, "SitQuietly")) {
                    //静坐检测
                    locationEventInfo.setEventType(125);
                } else if (StringUtils.equals(eventType, "PeopleNumCounting")) {
                    //人数统计
                    locationEventInfo.setEventType(15);//YJLB  单人审讯
                    if (chasSxsKzList.size() <= 0) {
                        log.error("查询失败，区域id：" + qyid + "查询不到审讯室控制信息");
//                            Log4JUtil.WritetoDatabase("HikISXwfx","查询失败，区域id："+qyid+"查询不到审讯室控制信息","error",null);
                        locationEventInfo.setEventType(-1);
                        return;
                    }
                    if ((Integer) eventName.get("framesPeopleCountingNum") < 3) {
                    	
                    	if(chasSxsKzList.get(0)!=null){
                    		String rybh = chasSxsKzList.get(0).getRybh();
                    		Date hdsj = chasSxsKzList.get(0).getHdsj();
                    		if(StringUtils.isNotEmpty(rybh)&&hdsj==null){
                    			log.info("出现单人审讯"+qyid);
                                chasYjxx.setJqms("审讯室:" + qy.getQymc() + "出现单人审讯！");
                    		}
                    	}else{
                    		log.error("未找到审讯室分配记录:"+qyid);
                    	}
                      
//                        chasYjxx.setYjjb(SYSCONSTANT.YJJB_EJ);
                    } else {
                        locationEventInfo.setEventType(-1);
                    }
                } else if (StringUtils.equals(eventType, "PlayCellphone")) {
                    //玩手机
                    locationEventInfo.setEventType(127);
                } else if (StringUtils.equals(eventType, "NonPoliceIntrusion")) {
                    //非警察人员入侵
                    locationEventInfo.setEventType(128);
                } else if (StringUtils.equals(eventType, "PoliceAbsent")) {
                    //警察不在场
                    locationEventInfo.setEventType(129);
                } else if (StringUtils.equals(eventType, "FailDown")) {
                    //人员倒地
                    locationEventInfo.setEventType(130);
                } else if (StringUtils.equals(eventType, "Tossing")) {
                    //抛物
                    locationEventInfo.setEventType(131);
                } else if (StringUtils.equals(eventType, "PhysicalConfront")) {
                    //肢体冲突
//                    chasYjxx.setYjjb(SYSCONSTANT.YJJB_TJ);
                    chasYjxx.setJqms("区域" + qy.getQymc() + "发生肢体冲突！");
                    locationEventInfo.setEventType(132);
                } else {
                    locationEventInfo.setEventType(-1);
                }

                log.debug("locationEventInfo:=========>>" + JSONObject.toJSONString(locationEventInfo));
//                chasYjxx.setJqms(String.format("%s心率异常，正常范围[%s - %s]，人员当前心率为:%s",ryjl.getXm(),xls[0],xls[1],xls[2]));
            } else {
                doc = DocumentHelper.parseText(strcont);
                Element rootElt = doc.getRootElement(); // 获取根节点
                Element ElementEventType = rootElt.element("eventType");
                Iterator iter = rootElt.elementIterator("TaskInfo"); // 获取根节点下的子节点head
                Map<String, Object> targetAttrs = JSONObject.parseObject(rootElt.element("targetAttrs").getText(), Map.class);
                String ruleCustomName = "";
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    ruleCustomName = recordEle.elementTextTrim("ruleCustomName");
                }
                locationEventInfo.setEquipNo((String) targetAttrs.get("deviceId")); //"deviceId":"314c8892dce3499584730c88aa37c7fb",
                locationEventInfo.setAreaId("");
                String sxt = (String) targetAttrs.get("taskname");
                locationEventInfo.setDisc("设备" + sxt + "监控到：" + ruleCustomName);// "ruleCustomName":"折线攀高"
                locationEventInfo.setTagNo((String) targetAttrs.get("taskname"));//"taskname":"123-监所",
//                locationEventInfo.setExpandParm("");//行为图片预览地址
                String eventType = ElementEventType.getText();

                qyid = sxt.split("-")[1];
                params.put("qyid", qyid);
                qy = chasQyService.findById(qyid);
                if(null == qy){
                    log.error("查询失败，区域id：" + qyid + "查询不到审讯室信息");
                    return;
                }
                List<ChasSxsKz> chasSxsKzList = chasSxsKzService.findList(params, " xgsj desc");

                chasYjxx.setId(StringUtils.getGuid32());
                String baqmc = qy.getBaqmc();
                chasYjxx.setBaqid(baqid);
                chasYjxx.setBaqmc(baqmc);
                chasYjxx.setLrsj(new Date());
                chasYjxx.setCfqyid(qyid);
                chasYjxx.setCfqymc(qy.getQymc());
                chasYjxx.setYjjb(SYSCONSTANT.YJJB_YJ);

                /**
                 * 拼接公共的预警内容
                 */
                chasYjxx.setJqms("区域:" + qy.getQymc() + "出现" + ruleCustomName + "！");
                log.debug("行为分析eventType：========》" + eventType);
                if (StringUtils.equals("leavePosition", eventType)) {
                    //离岗检测
                    if (chasSxsKzList.size() <= 0) {
                        log.info("查询失败，区域id：" + qyid + "查询不到审讯室控制信息");
                    } else {
                        chasYjxx.setCfrxm(chasSxsKzList.get(0).getSymj());
                    }
                    Element elementMode = rootElt.element("mode");
                    if (StringUtils.equals("leave", elementMode.getText())) {
                        chasYjxx.setJqms("人员脱岗");
                    } else if (StringUtils.equals("sleep", elementMode.getText())) {
                        chasYjxx.setJqms("人员睡岗");
                    } else if (StringUtils.equals("leaveAndSleep", elementMode.getText())) {
                        chasYjxx.setJqms("人员离睡岗");
                    }
//                        locationEventInfo.setEventType(118);
                    locationEventInfo.setEventType(10);// YJLB  民警脱岗
                } else if (StringUtils.equals(eventType, "peopleNumChange")) {
                    //人数异常
                    locationEventInfo.setEventType(124);
                } else if (StringUtils.equals(eventType, "failDown")) {
                    //人员倒地
                    locationEventInfo.setEventType(130);
                } else if (StringUtils.equals(eventType, "violentMotion")) {
                    //剧烈运动
                    locationEventInfo.setEventType(115); //行为分析预警
                } else if (StringUtils.equals(eventType, "group")) {
                    //人员聚集
//                    locationEventInfo.setEventType(133); //行为分析预警
                    locationEventInfo.setEventType(7); //行为分析预警
                    chasYjxx.setJqms("区域:" + qy.getQymc() + "出现人员聚集！");
                } else {
                    locationEventInfo.setEventType(-1);
                }
                log.info("locationEventInfo:=========>>" + JSONObject.toJSONString(locationEventInfo));
            }
            log.info("locationEventInfo.getEventType:"+locationEventInfo.getEventType());
            chasYjxx.setYjlb(String.valueOf(locationEventInfo.getEventType()));
            chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
            chasYjxx.setCfsj(new Date());

            if (locationEventInfo.getEventType() != -1) {
                params.clear();
                params.put("baqid", qy.getBaqid());
                params.put("yjlb", locationEventInfo.getEventType());
                List<ChasYjlb> yjlbList = chasYjlbService.findList(params, " xgsj desc");
                if (yjlbList.size() > 0) {
                    ChasYjlb yjlb = yjlbList.get(0);
                    chasYjxx.setYjjb(yjlbList.get(0).getYjjb());
                    params.clear();
                    params.put("cfqyid", qyid);
                    params.put("baqid", baqid);
                    params.put("yjzt", SYSCONSTANT.YJZT_WCL);
                    params.put("yjlb", locationEventInfo.getEventType());
                    List<ChasYjxx> yjxxList = chasYjxxService.findList(params, " CFSJ desc");
                    if (yjxxList.isEmpty()) {
                        chasYjxxService.save(chasYjxx);
                    }else {
                    	ChasYjxx yjxx =yjxxList.get(0);
                        yjxx.setYjjb(yjlb.getYjjb());
                    	yjxx.setCfsj(new Date());
                    	yjxx.setXgsj(new Date());
						chasYjxxService.update(yjxx);
						log.info("找到未处理预警，修改原记录:"+yjxx.getId());
					}
                    jdqService.sendYjxxmsg(chasYjxx, DicUtil.translate("YJLB", yjlb.getYjlb()),
                            yjlb.getYjfs(), yjlb.getYjsc());
                }else {
                    log.error("未找到对应的预警配置！" );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("行为分析出错："+e.getMessage());
        }
    }


    private String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
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
