package com.wckj.chasstage.api.server.imp.yjxx;

import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.yjxx.model.DpDataBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.chasstage.api.def.yjxx.service.ApiYjxxService;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * 预警信息
 */
@Service
public class ApiYjxxServiceImpl implements ApiYjxxService {
    private static final Logger log = LoggerFactory.getLogger(ApiYjxxServiceImpl.class);

    @Autowired
    private ChasYjxxService apiService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasBaqrefService baqrefService;
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
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYjxx yjxx = apiService.findById(id);
        if(yjxx!=null){
            return ResultUtil.ReturnSuccess(yjxx);
        }
        return ResultUtil.ReturnError("无法根据id找到预警信息");
    }

    @Override
    public ApiReturnResult<?> save(YjxxBean bean) {
        try {
            ChasYjxx yjlb = new ChasYjxx();
            MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
            yjlb.setId(StringUtils.getGuid32());
            yjlb.setIsdel(0);
            yjlb.setLrsj(new Date());
            yjlb.setXgsj(new Date());
            if(apiService.save(yjlb)>0){
                return ResultUtil.ReturnSuccess("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(YjxxBean bean) {
        try {
            ChasYjxx yjlb = apiService.findById(bean.getId());
            if(yjlb!=null){
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                if("1".equals(yjlb.getYjzt())||"2".equals(yjlb.getYjzt())){//处理预警
                    SessionUser user = WebContext.getSessionUser();
                    if(user!=null){
                        yjlb.setClrxm(user.getName());
                    }
                    yjlb.setClsj(new Date());
                }
                if("1".equals(yjlb.getYjzt())){
                    if (SYSCONSTANT.YJJB_TJ.equals(yjlb.getYjjb())) {
                        try {
                            jdqService.closeAlarm(yjlb.getBaqid());
                        } catch (Exception e) {
                            log.error("关闭报警器出错", e);
                            e.printStackTrace();
                        }
                    }
                }
                if(apiService.update(yjlb)>0){
                    return ResultUtil.ReturnSuccess("修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("修改失败");
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id->apiService.deleteById(id));
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(YjxxParam param) {
        if(StringUtil.isEmpty(param.getBaqid())){
            String baqid =baqService.getZrBaqid();
            if(StringUtil.isEmpty(baqid)){
                return ResultUtil.ReturnError("当前登录人单位，未配置办案区！");
            }
            param.setBaqid(baqid);
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        map.put("yjsj1",map.get("startYjsj"));
        map.put("yjsj2",map.get("endYjsj"));
//        DataQxbsUtil.getSelectAll(apiService, map);
//        if(WebContext.getSessionUser()!=null){
//            String userRoleId = WebContext.getSessionUser().getRoleCode();
//            if (!"0101".equals(userRoleId)) {
//                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
//                map.put("sydwdm", orgCode);
//            }
//        }
        PageDataResultSet<ChasYjxx> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "cfsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData(), new String[] {
                        "YJLB", "YJZT", "YJJB" },
                new String[] { "yjlb", "yjzt", "yjjb" }));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getBigScreenData(String variables) {
        if(StringUtils.isEmpty(variables)){
            return ResultUtil.ReturnError("variables参数不能为空");
        }
        String[] vars=variables.split(",");
        if(vars!=null&&vars.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String[] strings = vars[0].split(":");
        if(strings==null||strings.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String orgCodeStr = strings[0];
        String orgCode = "";
        if("orgCode".equals(orgCodeStr)){
            orgCode = strings[1];
            orgCode=orgCode.replaceAll("[\"',\\s]", "");
        }
        if(StringUtils.isEmpty(orgCode)){
            return ResultUtil.ReturnError("单位编号为必传参数");
        }
        strings = vars[1].split(":");
        if(strings==null||strings.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String serviceTypeStr = strings[0];
        String serviceType="";
        if("serviceType".equals(serviceTypeStr)){
            serviceType = strings[1];
        }
        try {
            Map<String,Object> map= new HashMap<>();
            map.put("dwdm", orgCode);
            ChasBaq baq = null;
            List<ChasBaq> baqList = baqService.findListByParams(map);
            if (baqList.size() > 0) {
                baq = baqList.get(0);
            }
            if(baq==null){
                return ResultUtil.ReturnError(orgCode+"单位编号没有关联办案区");
            }
            List<DpDataBean> beans = getBigScreenDataByBaqid(baq.getId(),serviceType);
            return ResultUtil.ReturnSuccess(beans);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取大屏预警统计数据出错", e);
        }
        return ResultUtil.ReturnError("获取大屏预警统计数据出错");
    }

    @Override
    public ApiReturnResult<?> getXsBigScreenData(String variables) {
        if(StringUtils.isEmpty(variables)){
            return ResultUtil.ReturnError("variables参数不能为空");
        }
        String[] vars=variables.split(",");
        if(vars!=null&&vars.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String[] strings = vars[0].split(":");
        if(strings==null||strings.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String orgCodeStr = strings[0];
        String orgCode = "";
        if("orgCode".equals(orgCodeStr)){
            orgCode = strings[1];
            orgCode=orgCode.replaceAll("[\"',\\s]", "");
        }
        if(StringUtils.isEmpty(orgCode)){
            return ResultUtil.ReturnError("单位编号为必传参数");
        }
        strings = vars[1].split(":");
        if(strings==null||strings.length<2){
            return ResultUtil.ReturnError("variables参数格式错误");
        }
        String serviceTypeStr = strings[0];
        String serviceType="";
        if("serviceType".equals(serviceTypeStr)){
            serviceType = strings[1];
        }
        try {
            Map<String,Object> map= new HashMap<>();
            map.put("dwdm", orgCode);
            ChasBaq baq = null;
            List<ChasBaq> baqList = baqService.findListByParams(map);
            if (baqList.size() > 0) {
                baq = baqList.get(0);
            }
            if(baq==null){
                return ResultUtil.ReturnError(orgCode+"单位编号没有关联办案区");
            }
            List<DpDataBean> beans = getXsBigScreenDataByBaqid(baq.getId(),serviceType);
            return ResultUtil.ReturnSuccess(beans);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取大屏预警统计数据出错", e);
        }
        return ResultUtil.ReturnError("获取大屏预警统计数据出错");
    }

    @Override
    public void saveAlarm(String qyid, HttpServletRequest request, String baqid) {
        log.error("触发行为分析！！");
        Document doc = null;
        LocationEventInfo locationEventInfo = new LocationEventInfo();
        Map<String, Object> params = new HashMap<>(16);
        ChasYjxx chasYjxx = new ChasYjxx();
        ChasXtQy qy = new ChasXtQy();
//        params.put("qyid", qyid);
        try {
//            ChasXtQy qy = chasQyService.findById(qyid);
//            if (null == qy) {
//                log.error("查询失败，区域id：" + qyid + "查询不到审讯室信息");
//                return;
//            }
//            List<ChasSxsKz> chasSxsKzList = chasSxsKzService.findList(params, " xgsj desc");

//            chasYjxx.setId(StringUtils.getGuid32());
//            String baqid = qy.getBaqid();
//            String baqmc = qy.getBaqmc();
//            chasYjxx.setBaqid(baqid);
//            chasYjxx.setBaqmc(baqmc);
//            chasYjxx.setLrsj(new Date());
////            chasYjxx.setCfrid(chasSxsKzList.get(0).getRybh());
////            chasYjxx.setCfrxm(chasSxsKzList.get(0).getXm());
//            chasYjxx.setCfqyid(qyid);
//            chasYjxx.setCfqymc(qy.getQymc());
//            chasYjxx.setYjjb(SYSCONSTANT.YJJB_YJ);

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
            log.error("content=========》" + content.toString());
            log.error("行为分析内容=========》" + strcont);

            if (strcont.startsWith("{")) {
                Map<String, Object> strMap = JSONObject.parseObject(strcont, Map.class);

                Map<String, Object> targetAttrs = (Map<String, Object>) strMap.get("targetAttrs");
                String eventType = captureName((String) strMap.get("eventType"));
                log.error("行为分析eventType：========》" + eventType);
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
                    if (chasSxsKzList.size() <= 0) {
                        log.error("查询失败，区域id：" + qyid + "查询不到审讯室控制信息");
//                            Log4JUtil.WritetoDatabase("HikISXwfx","查询失败，区域id："+qyid+"查询不到审讯室控制信息","error",null);
                        locationEventInfo.setEventType(-1);
                    }
                    if ((Integer) eventName.get("framesPeopleCountingNum") < 3 && null == chasSxsKzList.get(0).getHdsj()) {
//                            locationEventInfo.setEventType(126);//单人审讯
                        locationEventInfo.setEventType(9);//YJLB  单人审讯
                        chasYjxx.setJqms("审讯室:" + qy.getQymc() + "出现单人审讯！");
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

                log.info("locationEventInfo:=========>>" + JSONObject.toJSONString(locationEventInfo));
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
                log.info("行为分析eventType：========》" + eventType);
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
                        // 预警级别>=2.则关闭继电器
                        if(isOpenAlarm(yjlb)){
                            jdqService.openAlarm(baqid,yjlb.getYjsc()*60000);
                        }
                    }
                }else {
                    log.error("未找到对应的预警配置！" );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("行为分析出错："+e.getMessage());
        }
    }

    private List<DpDataBean> getBigScreenDataByBaqid(String baqid,String fac){
        Map<String,Object> map= new HashMap<>();
        map.put("baqid", baqid);
        map.put("yjzt","0");
        List<DpDataBean> data = new ArrayList<>();
        List<ChasYjxx> yjxxList = apiService.findList(map, "xgsj desc");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        yjxxList.forEach(yjxx->{
            DpDataBean bean = new DpDataBean();
            if("1".equals(yjxx.getYjlb())){//男女混关
                bean.setType(1);
                bean.setTypeName("男女混关");
                bean.setTitle(buildTitle("男女混关",yjxx.getCfqymc()));
            }else if("2".equals(yjxx.getYjlb())){//同案
                bean.setType(2);
                bean.setTypeName("同案接触");
                bean.setTitle(buildTitle("同案接触",yjxx.getCfqymc()));
            }else if("5".equals(yjxx.getYjlb())){//人员逃脱
                bean.setType(4);
                bean.setTypeName("人员逃脱");
                bean.setTitle(buildTitle("人员逃脱",yjxx.getCfqymc()));
            }else if("12".equals(yjxx.getYjlb())){//入区未审讯
                bean.setType(3);
                bean.setTypeName("入区未审讯");
                bean.setTitle(buildTitle("入区未审讯",yjxx.getCfqymc()));
            }else if("13".equals(yjxx.getYjlb())){//入区超时
                bean.setType(5);
                bean.setTypeName("超24小时未离办案区");
                bean.setTitle(buildTitle("超24小时未离办案区",yjxx.getCfqymc()));
            }else if("9".equals(yjxx.getYjlb())){//单人审讯
                bean.setType(6);
                bean.setTypeName("单人审讯");
                bean.setTitle(buildTitle("单人审讯",yjxx.getCfqymc()));
            }
            bean.setYjzt(yjxx.getYjzt());
            bean.setYjztName(getYjztName(yjxx.getYjzt()));
            bean.setCfr(yjxx.getCfrxm());
            bean.setQy(yjxx.getCfqymc());
            bean.setSj(null != yjxx.getCfsj()? simpleDateFormat.format(yjxx.getCfsj()) : "");
            if(bean.getType()!=null&&bean.getType()>0){
                bean.setMonitorUrl(buildJkUrl(yjxx,fac));
                data.add(bean);
            }
        });
        return data;
    }

    private String getYjztName(String yjzt){
        String yjztName = "";
        switch (yjzt){
            case "0":
                yjztName = "未处理";
                break;
            case "1":
                yjztName = "已处理";
                break;
            case "2":
                yjztName = "已忽略";
                break;
        }
        return yjztName;
    }

    private List<DpDataBean> getXsBigScreenDataByBaqid(String baqid,String fac){
        Map<String,Object> map= new HashMap<>();
        map.put("baqid", baqid);
        map.put("yjzt","0");
        List<DpDataBean> data = new ArrayList<>();
        List<ChasYjxx> yjxxList = apiService.findList(map, "xgsj desc");

        yjxxList.forEach(yjxx->{
            DpDataBean bean = new DpDataBean();
            if("1".equals(yjxx.getYjlb())){//男女混关
                bean.setType(1);
                bean.setTitle(buildTitle("男女混关",yjxx.getCfqymc()));
            }else if("2".equals(yjxx.getYjlb())){//同案
                bean.setType(2);
                bean.setTitle(buildTitle("同案接触",yjxx.getCfqymc()));
            }else if("3".equals(yjxx.getYjlb())){//巡更
                bean.setType(8);
                bean.setTitle(buildTitle("巡更睡岗异常",yjxx.getCfqymc()));
            }else if("5".equals(yjxx.getYjlb())){//人员逃脱
                bean.setType(4);
                bean.setTitle(buildTitle("人员逃脱",yjxx.getCfqymc()));
            }else if("12".equals(yjxx.getYjlb())){//入区未审讯
                /*bean.setType(3);
                bean.setTitle(buildTitle("入区未审讯",yjxx.getCfqymc()));*/
            }else if("13".equals(yjxx.getYjlb())){//入区超时
                bean.setType(5);
                bean.setTitle(buildTitle("超24小时未离办案区",yjxx.getCfqymc()));
            }else if("9".equals(yjxx.getYjlb())){//单人审讯
                bean.setType(6);
                bean.setTitle(buildTitle("单人审讯",yjxx.getCfqymc()));
            }
            if(bean.getType()!=null&&bean.getType()>0){
                bean.setMonitorUrl(buildJkUrl(yjxx,fac));
                data.add(bean);
            }
        });

        yjxxList = apiService.findXzYjxx(baqid);
        yjxxList.forEach(yjxx -> {
            DpDataBean bean = new DpDataBean();
            bean.setType(3);
            bean.setTitle(buildTitle("行政8小时未询问",yjxx.getCfqymc()));
            if(bean.getType()!=null&&bean.getType()>0){
                bean.setMonitorUrl(buildJkUrl(yjxx,fac));
                data.add(bean);
            }
        });
        yjxxList = apiService.findXsYjxx(baqid);
        yjxxList.forEach(yjxx -> {
            DpDataBean bean = new DpDataBean();
            bean.setType(7);
            bean.setTitle(buildTitle("刑事12小时未讯问",yjxx.getCfqymc()));
            if(bean.getType()!=null&&bean.getType()>0){
                bean.setMonitorUrl(buildJkUrl(yjxx,fac));
                data.add(bean);
            }
        });
        return data;
    }

    public String buildTitle(String yjName,String qymc){
        if(!StringUtils.isEmpty(qymc)){
           return yjName +" - "+qymc;
        }
        return yjName;
    }
    public String buildJkUrl(ChasYjxx yjxx,String fac){
            if(StringUtils.isNotEmpty(yjxx.getCfqyid())){
                String url=getmonitorUrlByQyid(yjxx.getCfqyid(),fac);
                if(StringUtils.isNotEmpty(url)){
                    return url;
                }
            }

        return "";
    }
    public String  getmonitorUrlByQyid(String qyid,String fac){
        Map<String,Object> result = new HashMap<>();
        Map map = new HashMap();
        map.put("qyid",qyid);
        map.put("sblx", SYSCONSTANT.SBLX_SXT);
        map.put("sbgn","42");
        List<ChasSb> list = sbService.findByParams(map);
        String url="";
        if(list != null && !list.isEmpty()){
            if(StringUtils.isNotEmpty(list.get(0).getKzcs1())){//配置了nvr,使用nvr播放
                map.clear();
                map.put("sbbh",list.get(0).getKzcs1());
                List<ChasSb> nvrList = sbService.findByParams(map);
                if(nvrList != null&&!nvrList.isEmpty()){
                    ChasSb nvr =nvrList.get(0);
                    HashMap sbResult = new HashMap<>();
                    sbResult.put("kzcs1", nvr.getKzcs1());
                    sbResult.put("kzcs2",nvr.getKzcs2() );
                    sbResult.put("kzcs3", nvr.getKzcs3());
                    sbResult.put("kzcs4", nvr.getKzcs4());
                    sbResult.put("kzcs5",list.get(0).getKzcs5());
                    result.put("data", sbResult);
                    result.put("playType", "nvr");
                    result.put("success",true);
                    if("0".equalsIgnoreCase(fac)){
                        return  "rtsp://"+nvr.getKzcs3()+":"+nvr.getKzcs4()+"@"+nvr.getKzcs1()+":554/h264/CH"+list.get(0).getKzcs5()+"/main/av_stream";//海康
                    }else{
                        return "rtsp://"+nvr.getKzcs3()+":"+nvr.getKzcs4()+"@"+nvr.getKzcs1()+":554/cam/realmonitor?channel="+list.get(0).getKzcs5()+"&subtype=0";//大华
                    }
                }
            }else{//使用摄像头

                if("0".equalsIgnoreCase(fac)){
                    return "rtsp://"+list.get(0).getKzcs4()+"@"+list.get(0).getKzcs2()+":554/h264/ch1/main/av_stream";
                }else{
                    return "rtsp://"+list.get(0).getKzcs4()+"@"+list.get(0).getKzcs2()+":554/cam/realmonitor?channel=1&subtype=0";
                }

            }
        }
        return url;
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
