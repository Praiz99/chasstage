package com.wckj.chasstage.api.server.imp.rygj;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wckj.chasstage.api.def.rygj.model.RygjBean;
import com.wckj.chasstage.api.def.rygj.model.RygjParam;
import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Stream;

@Service
public class ApiRygjServiceImpl implements ApiRygjService {
    private static final Logger log = LoggerFactory.getLogger(ApiRygjServiceImpl.class);
    @Autowired
    private ChasYwRygjService rygjService;
    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private ChasYwFkdjService fkdjService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private HikFaceLocationService faceLocationService;

    @Override
    public ApiReturnResult<?> get(String id) {
        ChasRygj xgpz = rygjService.findById(id);
        if (xgpz != null) {
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到轨迹信息");
    }

    @Override
    public ApiReturnResult<?> save(RygjBean bean) {
        try {
            if (StringUtils.isEmpty(bean.getRyid())) {
                return ResultUtil.ReturnError("ryid为空");
            }
            ChasRygj chasRygj = new ChasRygj();
            MyBeanUtils.copyBeanNotNull2Bean(bean, chasRygj);

            ChasBaqryxx ryxx = baqryxxService.findById(chasRygj.getRyid());
            if (ryxx == null) {
                return ResultUtil.ReturnError("找不到人员信息");
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("rybh", ryxx.getRybh());
            ChasRyjl ryjl = ryjlService.findByParams(params);
            if (ryjl == null) {
                return ResultUtil.ReturnError("找不到人员记录信息");
            }
            chasRygj.setId(StringUtils.getGuid32());
            chasRygj.setLrsj(new Date());
            chasRygj.setXgsj(new Date());
            SessionUser user = WebContext.getSessionUser();
            chasRygj.setLrrSfzh(user == null ? "" : user.getIdCard());
            chasRygj.setXgrSfzh(user == null ? "" : user.getIdCard());
            chasRygj.setBaqid(ryxx.getBaqid());
            chasRygj.setBaqmc(ryxx.getBaqmc());
            chasRygj.setXm(ryxx.getRyxm());
            chasRygj.setRylx("xyr");
            chasRygj.setWdbh(ryjl.getWdbhL());
            rygjService.save(chasRygj);
            return ResultUtil.ReturnSuccess("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存嫌疑人轨迹信息出错", e);
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(RygjBean bean) {
        try {
            ChasRygj rygj = rygjService.findById(bean.getId());
            MyBeanUtils.copyBeanNotNull2Bean(bean, rygj);
            rygj.setXgsj(new Date());
            SessionUser user = WebContext.getSessionUser();
            rygj.setXgrSfzh(user == null ? "" : user.getIdCard());
            rygjService.update(rygj);
            return ResultUtil.ReturnSuccess("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改嫌疑人轨迹信息出错", e);
        }
        return ResultUtil.ReturnError("修改失败");
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id -> rygjService.deleteById(id));
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除轨迹信息出错", e);
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getBaqPageData(RygjParam param) {
        if (StringUtils.isEmpty(param.getBaqid())
                && StringUtils.isEmpty(param.getRybh())
                && StringUtils.isEmpty(param.getRyid())) {
            return ResultUtil.ReturnError("参数错误");
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(rygjService, map);
        PageDataResultSet<ChasRygj> pageData = rygjService.getEntityPageData(param.getPage(), param.getRows(), map, "kssj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows", pageData.getData());
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getRygjUrl(RygjParam param) {
        if (StringUtils.isEmpty(param.getBaqid())
                || StringUtils.isEmpty(param.getRybh())
                || StringUtils.isEmpty(param.getRylx())) {
            return ResultUtil.ReturnError("参数错误");
        }
        String resCode = "";
        if(StringUtils.equals(param.getRylx(), "xyr")){
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(param.getRybh());
            resCode = baqryxx.getRegisterCode();
        }else if(StringUtils.equals(param.getRylx(), "mj")){
            ChasYwMjrq mjrq = mjrqService.findMjrqByRybh(param.getBaqid(), param.getRybh());
            resCode = mjrq.getRegisterCode();
        }else if(StringUtils.equals(param.getRylx(), "fk")){
            ChasYwFkdj fkdj = fkdjService.findFkdjByRybh(param.getBaqid(), param.getRybh());
            resCode = fkdj.getRegisterCode();
        }
        if(StringUtils.isEmpty(resCode)){
            return ResultUtil.ReturnError("未查询到该人员开启了人脸定位，无法获取轨迹视频");
        }else{
            Map<String, Object> map = faceLocationService.getHistoricalUrl(param.getBaqid(), resCode);
            if((Boolean) map.get("status")){
                Map<String, Object> result = new HashMap<>();
                result.put("url", map.get("url"));
                return ResultUtil.ReturnSuccess(result);
            }else{
                return ResultUtil.ReturnError(String.valueOf(map.get("msg")));
            }
        }
    }

    @Override
    public ApiReturnResult<?> getRtspValByQyid(String qyid) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {

            Map<String, Object> objectMap = rygjService.getRtspValByQyid(qyid);
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getRygjXmlByRybh(String rybh, String kssj, String jssj, String areaNo) {
        //后续优化新增，根据轨迹id获取编号和时间、区域，办案区的信息的查询接口
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        if (StringUtils.isEmpty(rybh)) {
            throw new BizDataException("人员编号为NULL");
        }
        String rylx = rybh.substring(0, 1);
        String baqid = null;
        if ("R".equals(rylx)) {
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
            if (baqryxx == null) {
                returnResult.setCode("500");
                returnResult.setMessage("不存在该人员");
                return returnResult;
            }
            baqid = baqryxx.getBaqid();
        }
        if ("M".equals(rylx)) {
            ChasYwMjrq mjrqByRybh = mjrqService.findMjrqByRybh(baqid, rybh);
            if (mjrqByRybh == null) {
                returnResult.setCode("500");
                returnResult.setMessage("不存在该民警");
                return returnResult;
            }
            baqid = mjrqByRybh.getBaqid();
        }
        if ("F".equals(rylx)) {
            ChasYwFkdj fkdjByRybh = fkdjService.findFkdjByRybh(baqid, rybh);
            if (fkdjByRybh == null) {
                returnResult.setCode("500");
                returnResult.setMessage("不存在该访客");
                return returnResult;
            }
            baqid = fkdjByRybh.getBaqid();
        }
        BaqConfiguration znpz = baqznpzService.findByBaqid(baqid);
        String baqVmsurl = znpz.getBaqVmsurl();
        String url ="";
        if (StringUtils.isEmpty(baqVmsurl)) {
            throw new BizDataException("VMS地址不存在！");
        }
        if(baqVmsurl.contains("2.0")){
        	baqVmsurl = baqVmsurl.replace("2.0", "");	
        	url = baqVmsurl + "/api/ext/player/trail";	
        }else{
        	url = baqVmsurl + "/api/int/trail/play";
        }
        Part[] parts = new Part[4];
        parts[0] = new StringPart("startTime", kssj);
        parts[1] = new StringPart("endTime", jssj);
        parts[2] = new StringPart("peopleNo", rybh);
        if (StringUtils.isEmpty(areaNo) || "null".equals(areaNo)) {
            areaNo = "";
        }
        parts[3] = new StringPart("areaNo", areaNo);
        try {
            String getBody = com.wckj.taskClient.util.HttpClientUtil.postMethod(url, parts);
            returnResult.setCode("200");
            returnResult.setData(getBody);
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("获取异常" + e.getMessage());
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> sendLastVmsInfo(String baqid, String rybh, String qyid) {
        log.info("发送出区vms信息参数" + baqid + "," + rybh + "," + qyid);
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        if (StringUtils.isEmpty(baqid) ||
                StringUtils.isEmpty(rybh) ||
                StringUtils.isEmpty(qyid)) {
            return ResultUtil.ReturnError("参数错误！");
        }
        try {
            ChasBaq baq = baqService.findById(baqid);
            ChasXtQy qy = qyService.findByYsid(qyid);
            BaqConfiguration znpz = baqznpzService.findByBaqid(baqid);
            String baqVmsurl = znpz == null ? "" : znpz.getBaqVmsurl();
            Part[] parts = null;
            if (StringUtils.isEmpty(baqVmsurl)) {
                log.error("VMS地址不存在！");
                return ResultUtil.ReturnError("VMS地址不存在！");
            }
            String vmsUrl = baqVmsurl.replace("2.0", "");
            if(baqVmsurl.contains("2.0")){
            	parts = new Part[5];
            	log.info("vms2.0人员出所结束轨迹!"+rybh);
            	parts[0] = new StringPart("peopleNo", rybh);
                log.info("peopleNo=="+rybh);
                parts[1] = new StringPart("org", baq.getDwdm());
                log.info("org=="+baq.getDwdm());
                parts[2] = new StringPart("areaNo", qyid);
                log.info("areaNo=="+qyid);
                parts[3] = new StringPart("custodyStatus", "1");
                log.info("custodyStatus=="+"1");
                parts[4] = new StringPart("endTime", DateTimeUtils.getDateStr(new Date(), 15));
                log.info("endTime=="+DateTimeUtils.getDateStr(new Date(), 15));
                vmsUrl = vmsUrl+"/api/ext/trail/end";
                log.info("发送出区vms地址:"+vmsUrl); 	
            }else{
            	log.info("vms1.0人员出所结束轨迹!"+rybh);
            	parts =new Part[7];
                Map map = new HashMap();
                map.put("baqid", baq.getId());
                map.put("qyid", qyid);
                map.put("sblx", SYSCONSTANT.SBLX_SXT);
                map.put("sbgn", "42");
                List<ChasSb> list = sbService.findByParams(map);
                if (list == null || list.isEmpty()) {
                    log.error("无法获取到摄像头" + qyid);
                    return ResultUtil.ReturnError("无法获取到摄像头！");
                }
                String nvrid = "";
                ChasSb sxt = null;
                for (ChasSb sb : list) {
                    if (StringUtil.isEmpty(nvrid)) {
                        nvrid = sb.getKzcs1();
                        sxt = sb;
                        break;
                    }
                }
                map.clear();
                map.put("baqid", baq.getId());
                map.put("sbbh", nvrid);
                List<ChasSb> nvrList = sbService.findByParams(map);
                if (nvrList == null || nvrList.isEmpty()) {
                    log.error("无法获取到nvr！");
                    return ResultUtil.ReturnError("无法获取到nvr！");

                }
                ChasSb nvr = nvrList.get(0);
                HashMap sbResult = new HashMap<>();
                sbResult.put("ip", nvr.getKzcs1());
                sbResult.put("port", nvr.getKzcs2());
                sbResult.put("username", nvr.getKzcs3());
                sbResult.put("password", nvr.getKzcs4());
                sbResult.put("channel", sxt.getKzcs5());
                JSONObject jsonObject2 = JSONObject.fromObject(sbResult);
                parts[0] = new StringPart("peopleNo", rybh);
                log.info("peopleNo=="+rybh);
                parts[1] = new StringPart("orgCode", baq.getDwdm());
                log.info("orgCode=="+baq.getDwdm());
                parts[2] = new StringPart("areaNo", qyid);
                log.info("areaNo=="+qyid);
                parts[3] = new StringPart("custodyStatus", "1");
                log.info("custodyStatus=="+"1");
                parts[4] = new StringPart("areaName", qy.getQymc());
                log.info("areaName=="+qy.getQymc());
                parts[5] = new StringPart("endTime", DateTimeUtils.getDateStr(new Date(), 15));
                log.info("endTime=="+DateTimeUtils.getDateStr(new Date(), 15));
                parts[6] = new StringPart("vFrags", "[" + jsonObject2.toString() + "]");
                log.info("vFrags=="+"[" + jsonObject2.toString() + "]");
                log.info("发送出区vms,orgCode:"+baq.getDwdm());
                vmsUrl = vmsUrl + "/api/int/trail/end";
                log.info("发送出区vms地址:"+vmsUrl);
            	
            }

            String getBody = com.wckj.taskClient.util.HttpClientUtil.postMethod(vmsUrl, parts);
            log.info("响应结果:" + getBody);
            returnResult.setCode("200");
            returnResult.setData(getBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送出区vms信息出错", e);
            returnResult.setCode("500");
            returnResult.setMessage("获取异常" + e.getMessage());
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> sendRecVmsInfo(Date kssj, Date jssj, String baqid, String rybh, String qyid) {
        log.info("发送轨迹后录制vms信息参数" + baqid + "," + rybh + "," + qyid);
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        if (StringUtils.isEmpty(baqid) ||
                StringUtils.isEmpty(rybh) ||
                StringUtils.isEmpty(qyid)) {
            return ResultUtil.ReturnError("参数错误！");
        }
        try {
            ChasBaq baq = baqService.findById(baqid);
            ChasXtQy qy = qyService.findByYsid(qyid);
            BaqConfiguration znpz = baqznpzService.findByBaqid(baqid);
            String baqVmsurl = znpz == null ? "" : znpz.getBaqVmsurl();
            Part[] parts = null;
            if (StringUtils.isEmpty(baqVmsurl)) {
                log.error("VMS地址不存在！");
                return ResultUtil.ReturnError("VMS地址不存在！");
            }
            String vmsUrl = baqVmsurl.replace("2.0", "");

            if(baqVmsurl.contains("2.0")) {
                parts = new Part[7];
                log.info("vms2.0轨迹后录制!"+rybh);
                parts[0] = new StringPart("org", baq.getDwdm());
                log.info("org=="+baq.getDwdm());
                parts[1] = new StringPart("areaNo", qyid);
                log.info("areaNo=="+qyid);
                parts[2] = new StringPart("areaName", qy.getQymc());
                log.info("areaName=="+qy.getQymc());
                parts[3] = new StringPart("peopleNo", rybh);
                log.info("peopleNo=="+rybh);
                //摄像头信息
                Map map = new HashMap();
                map.put("baqid", baq.getId());
                map.put("qyid", qyid);
                map.put("sblx", SYSCONSTANT.SBLX_SXT);
                map.put("sbgn", "42");
                List<ChasSb> list = sbService.findByParams(map);
                if (list == null || list.isEmpty()) {
                    log.error("无法获取到摄像头" + qyid);
                    return ResultUtil.ReturnError("无法获取到摄像头！");
                }
                String nvrid = "";
                ChasSb sxt = null;
                for (ChasSb sb : list) {
                    if (StringUtil.isEmpty(nvrid)) {
                        nvrid = sb.getKzcs1();
                        sxt = sb;
                        break;
                    }
                }
                map.clear();
                map.put("baqid", baq.getId());
                map.put("sbbh", nvrid);
                List<ChasSb> nvrList = sbService.findByParams(map);
                if (nvrList == null || nvrList.isEmpty()) {
                    log.error("无法获取到nvr！");
                    return ResultUtil.ReturnError("无法获取到nvr！");

                }
                ChasSb nvr = nvrList.get(0);
                HashMap sbResult = new HashMap<>();
                sbResult.put("ip", nvr.getKzcs1());
                sbResult.put("port", nvr.getKzcs2());
                sbResult.put("username", nvr.getKzcs3());
                sbResult.put("password", nvr.getKzcs4());
                sbResult.put("channel", sxt.getKzcs5());
                JSONObject jsonObject2 = JSONObject.fromObject(sbResult);
                parts[4] = new StringPart("cameras", "[" + jsonObject2.toString() + "]");
                log.info("cameras=="+"[" + jsonObject2.toString() + "]");
                parts[5] = new StringPart("startTime", DateTimeUtils.getDateStr(kssj, 15));
                log.info("startTime=="+DateTimeUtils.getDateStr(kssj, 15));
                parts[6] = new StringPart("endTime", DateTimeUtils.getDateStr(jssj, 15));
                log.info("endTime=="+DateTimeUtils.getDateStr(jssj, 15));
                vmsUrl = vmsUrl+"/api/ext/trail";
                log.info("发送轨迹后录制vms地址:"+vmsUrl);
            } else {
                log.info("vms1.0轨迹后录制!"+rybh);
                parts = new Part[6];
                parts[0] = new StringPart("orgCode", baq.getDwdm());
                log.info("orgCode=="+baq.getDwdm());
                parts[1] = new StringPart("orgName", baq.getDwmc());
                log.info("orgCode=="+baq.getDwmc());
                parts[2] = new StringPart("areaNo", qyid);
                log.info("areaNo=="+qyid);
                parts[3] = new StringPart("peopleNo", rybh);
                log.info("peopleNo=="+rybh);
                //摄像头信息
                Map map = new HashMap();
                map.put("baqid", baq.getId());
                map.put("qyid", qyid);
                map.put("sblx", SYSCONSTANT.SBLX_SXT);
                map.put("sbgn", "42");
                List<ChasSb> list = sbService.findByParams(map);
                if (list == null || list.isEmpty()) {
                    log.error("无法获取到摄像头" + qyid);
                    return ResultUtil.ReturnError("无法获取到摄像头！");
                }
                String nvrid = "";
                ChasSb sxt = null;
                for (ChasSb sb : list) {
                    if (StringUtil.isEmpty(nvrid)) {
                        nvrid = sb.getKzcs1();
                        sxt = sb;
                        break;
                    }
                }
                map.clear();
                map.put("baqid", baq.getId());
                map.put("sbbh", nvrid);
                List<ChasSb> nvrList = sbService.findByParams(map);
                if (nvrList == null || nvrList.isEmpty()) {
                    log.error("无法获取到nvr！");
                    return ResultUtil.ReturnError("无法获取到nvr！");

                }
                ChasSb nvr = nvrList.get(0);
                HashMap sbResult = new HashMap<>();
                sbResult.put("ip", nvr.getKzcs1());
                sbResult.put("port", nvr.getKzcs2());
                sbResult.put("username", nvr.getKzcs3());
                sbResult.put("password", nvr.getKzcs4());
                sbResult.put("channel", sxt.getKzcs5());
                sbResult.put("startTime", DateTimeUtils.getDateStr(kssj, 15));
                sbResult.put("endTime", DateTimeUtils.getDateStr(jssj, 15));
                JSONObject jsonObject2 = JSONObject.fromObject(sbResult);
                parts[4] = new StringPart("vFrags", "[" + jsonObject2.toString() + "]");
                log.info("vFrags=="+"[" + jsonObject2.toString() + "]");
                parts[5] = new StringPart("timeLimit", "3");
                log.info("timeLimit==3");
                vmsUrl = vmsUrl+"/api/int/trail/v1/end";
                log.info("发送轨迹后录制vms地址:"+vmsUrl);
            }
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("send-message2-pool-%d").build();
            ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            String finalVmsUrl = vmsUrl;
            Part[] finalParts = parts;
            singleThreadPool.execute(() -> {
                try {
                    String getBody = com.wckj.taskClient.util.HttpClientUtil.postMethod(finalVmsUrl, finalParts);
                    log.info("响应结果:" + getBody);
                    returnResult.setCode("200");
                    returnResult.setData(getBody);
                } catch (Exception e) {
                    log.error("发送消息数据异常:{}", e);
                    e.printStackTrace();
                }
            });
            singleThreadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送轨迹后录制vms信息出错", e);
            returnResult.setCode("500");
            returnResult.setMessage("获取异常" + e.getMessage());
        }
        return returnResult;
    }
}
