package com.wckj.chasstage.modules.httpApi.client.hkrldw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wckj.chasstage.common.util.HttpClientUtils;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.util.*;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class HikFaceLocationServiceImpl implements HikFaceLocationService {

    private static final Logger log = Logger.getLogger(HikFaceLocationServiceImpl.class);

    @Autowired
    private ChasXtBaqznpzService baqznpzService;

    private static String ARTEMIS_PATH = "/artemis";

    private static String url = "";

    private static String placeCode = "";

    private static Map<String, String> path = new HashMap<>();

    private static String contentType = "application/json";

    @Override
    public Map<String, Object> startLocation(HikFaceLocationRyxx hikFaceLocationRyxx) {
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("——————开始海康人脸定位——————");
            setParams(hikFaceLocationRyxx.getPlaceCode(), "/api/positionService/v1/register/start");
            hikFaceLocationRyxx.setPlaceCode(placeCode);
            log.info("开始海康人脸定位参数：" + JSON.toJSONString(hikFaceLocationRyxx));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(hikFaceLocationRyxx), null,
                    null, contentType , null);
            log.info("开始人脸定位接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                Map<String, Object> data = resultObj.getObject("data", Map.class);
                result.put("registerCode", data.get("registerCode"));
                result.put("msg", "调用开始海康人脸定位接口成功");
                result.put("status", true);
                log.info("调用开始海康人脸定位接口成功");
            } else {
                result.put("msg", "调用开始海康人脸定位接口失败");
                result.put("status", false);
                log.info("调用开始海康人脸定位接口失败");
            }
        } catch (Exception e) {
            result.put("msg", "调用开始海康人脸定位接口失败");
            result.put("status", false);
            log.info("调用开始海康人脸定位接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> updateHikLocationRyxx(HikFaceLocationRyxx hikFaceLocationRyxx, String registerCode){
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("——————更新海康人脸定位人员信息——————");
            log.info("唯一标识：" + registerCode);
            setParams(hikFaceLocationRyxx.getPlaceCode(), "/api/positionService/v1/register/update");
            hikFaceLocationRyxx.setPlaceCode(placeCode);
            Map<String, Object> params = JSON.parseObject(JSON.toJSONString(hikFaceLocationRyxx));
            params.put("registerCode", registerCode);
            log.info("更新海康人脸定位参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("更新海康人脸定位人员信息接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                Map<String, Object> data = resultObj.getObject("data", Map.class);
                result.put("registerCode", data.get("registerCode"));
                result.put("msg", "调用海康更新人员信息接口成功");
                result.put("status", true);
                log.info("调用海康更新人员信息接口成功");
            } else {
                result.put("msg", "调用更新海康人脸定位接口失败");
                result.put("status", false);
                log.info("调用海康更新人员信息接口失败");
            }
        } catch (Exception e) {
            result.put("msg", "调用海康更新人员信息接口失败");
            result.put("status", false);
            log.info("调用海康更新人员信息接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> endLocation(String baqid, String registerCode){
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("——————结束海康人脸定位——————");
            log.info("办案区id：" + baqid);
            log.info("唯一标识：" + registerCode);
            Map<String, Object> params = new HashMap<>();
            params.put("registerCode", registerCode);
            setParams(baqid, "/api/positionService/v1/register/end");
            log.info("结束海康人脸定位参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("结束人脸定位接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                result.put("msg", "调用结束海康人脸定位接口成功");
                result.put("status", true);
                log.info("调用结束海康人脸定位接口成功");
            } else {
                result.put("msg", "调用结束海康人脸定位接口失败");
                result.put("status", false);
                log.info("调用结束海康人脸定位接口失败");
            }
        } catch (Exception e) {
            result.put("msg", "调用结束海康人脸定位接口失败");
            result.put("status", false);
            log.info("调用结束海康人脸定位接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> getHistoricalUrl(String baqid, String registerCode){
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("——————获取人员历史轨迹页面免登录URL——————");
            log.info("办案区id：" + baqid);
            log.info("唯一标识：" + registerCode);
            Map<String, Object> params = new HashMap<>();
            params.put("userName", "admin");
            params.put("registerIndexCode", registerCode);
            setParams(baqid, "/api/positionService/v1/sso/historical/url");
            log.info("获取人员历史轨迹页面免登录URL参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取人员历史轨迹页面免登录URL接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                result.put("url", resultObj.getString("data"));
                result.put("msg", "调用获取人员历史轨迹页面免登录URL位接口成功");
                result.put("status", true);
                log.info("调用获取人员历史轨迹页面免登录URL位接口成功");
            } else {
                result.put("msg", "调用获取人员历史轨迹页面免登录URL位接口失败");
                result.put("status", false);
                log.info("调用获取人员历史轨迹页面免登录URL位接口失败");
            }
        } catch (Exception e) {
            result.put("msg", "调用获取人员历史轨迹页面免登录URL位接口失败");
            result.put("status", false);
            log.info("调用获取人员历史轨迹页面免登录URL位接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> getRealTimeUrl(String baqid){
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("——————获取人员实时定位页面免登录URL——————");
            Map<String, Object> params = new HashMap<>();
            params.put("userName", "admin");
            setParams(baqid, "/api/positionService/v1/sso/real/url");
            log.info("获取人员实时定位页面免登录URL参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取人员实时定位面免登录URL接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                result.put("url", resultObj.getString("data"));
                result.put("msg", "调用获取人员实时定位页面免登录URL接口成功");
                result.put("status", true);
                log.info("调用获取人员实时定位页面免登录URL接口成功");
            } else {
                result.put("msg", "调用获取人员实时定位页面免登录URL接口失败");
                result.put("status", false);
                log.info("调用获取人员实时定位页面免登录URL接口失败");
            }
        } catch (Exception e) {
            result.put("msg", "调用获取人员实时定位页面免登录URL接口失败");
            result.put("status", false);
            log.info("调用获取人员实时定位页面免登录URL接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HikPersonPosResult getPersonPosition(String baqid, String registerCode){
        try {
            log.info("——————获取正在被定位人员的位置信息——————");
            log.info("唯一标识：" + registerCode);
            Map<String, Object> params = new HashMap<>();
            params.put("registerCode", registerCode);
            setParams(baqid, "/api/positionService/v1/location/personpos");
            log.info("获取正在被定位人员的位置信息接口参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取正在被定位人员的位置信息接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                log.info("调用获取正在被定位人员的位置信息接口成功");
                return HikPersonPosResult.success("调用获取正在被定位人员的位置信息接口成功", resultObj.getString("data"));
            } else {
                log.info("调用获取正在被定位人员的位置信息接口失败");
                return HikPersonPosResult.fail("调用获取正在被定位人员的位置信息接口失败");
            }
        } catch (Exception e) {
            log.info("调用获取正在被定位人员的位置信息接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return HikPersonPosResult.fail("调用获取正在被定位人员的位置信息接口失败");
    }

    @Override
    public HikMapPersonResult getMapPersionPos(String baqid, int mapId){
        try {
            log.info("——————获取地图上正在被定位人员信息——————");
            log.info("地图id：" + mapId);
            Map<String, Object> params = new HashMap<>();
            params.put("mapId", mapId);
            setParams(baqid, "/api/positionService/v1/location/map/persons");
            log.info("获取地图上正在被定位人员信息接口参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取地图上正在被定位人员信息接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                log.info("调用获取地图上正在被定位人员信息接口成功");
                return HikMapPersonResult.success("调用获取地图上正在被定位人员信息接口成功", resultObj.getString("data"));
            } else {
                log.info("调用获取地图上正在被定位人员信息接口失败");
                return HikMapPersonResult.fail("调用获取地图上正在被定位人员信息接口失败");
            }
        } catch (Exception e) {
            log.info("调用获取地图上正在被定位人员信息接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return HikMapPersonResult.fail("调用获取地图上正在被定位人员信息接口失败");
    }

    @Override
    public HikLocationPathsResult getLocationPaths(String baqid, String registerCode, String beginTime, String endTime){
        try {
            log.info("——————获取人员经过区域列表——————");
            log.info("唯一标识：" + registerCode);
            log.info("开始时间：" + beginTime);
            log.info("结束时间：" + endTime);
            Map<String, Object> params = new HashMap<>();
            params.put("registerCode", registerCode);
            params.put("beginTime", beginTime);
            params.put("endTime", endTime);
            setParams(baqid, "/api/positionService/v1/location/paths");
            log.info("获取人员经过区域列表接口参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取人员经过区域列表接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                log.info("调用获取人员经过区域列表接口成功");
                return HikLocationPathsResult.success("调用获取人员经过区域列表接口成功", resultObj.getString("data"));
            } else {
                log.info("调用获取人员经过区域列表接口失败");
                return HikLocationPathsResult.fail("调用获取人员经过区域列表接口失败");
            }
        } catch (Exception e) {
            log.info("调用获取人员经过区域列表接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return HikLocationPathsResult.fail("调用获取人员经过区域列表接口失败");
    }

    @Override
    public HikPathPositionResult getPathPositions(String baqid, int pathMapId, Boolean isFilter, String beginTime, String endTime){
        try {
            log.info("——————获取定位轨迹详细坐标点位信息——————");
            log.info("轨迹地图图层记录id：" + pathMapId);
            log.info("是否过滤轨迹点位：" + isFilter);
            log.info("开始时间：" + beginTime);
            log.info("结束时间：" + endTime);
            Map<String, Object> params = new HashMap<>();
            params.put("pathMapId", pathMapId);
            params.put("isFilter", isFilter);
            params.put("beginTime", beginTime);
            params.put("endTime", endTime);
            setParams(baqid, "/api/positionService/v1/location/path/positions");
            log.info("获取定位轨迹详细坐标点位信息接口参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取定位轨迹详细坐标点位信息接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                log.info("调用获取定位轨迹详细坐标点位信息接口成功");
                return HikPathPositionResult.success("调用获取定位轨迹详细坐标点位信息接口成功", resultObj.getString("data"));
            } else {
                log.info("调用获取定位轨迹详细坐标点位信息接口失败");
                return HikPathPositionResult.fail("调用获取定位轨迹详细坐标点位信息接口失败");
            }
        } catch (Exception e) {
            log.info("调用获取定位轨迹详细坐标点位信息接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return HikPathPositionResult.fail("调用获取定位轨迹详细坐标点位信息接口失败");
    }

    @Override
    public HikUserPlaceResult getPlacesByUser(String baqid){
        SessionUser user = WebContext.getSessionUser();
        try {
            log.info("——————获取用户有权限的场所信息——————");
            Map<String, Object> params = new HashMap<>();
            params.put("userId", user.getLoginId());
            setParams(baqid, "/api/positionService/v1/places");
            log.info("获取用户有权限的场所信息接口参数：" + JSON.toJSONString(params));
            String resultStr = ArtemisHttpUtil.doPostStringArtemis(path, JSON.toJSONString(params), null,
                    null, contentType , null);
            log.info("获取用户有权限的场所信息接口返回内容:" + resultStr);
            JSONObject resultObj = null;
            if(StringUtils.isNotEmpty(resultStr)){
                resultObj = JSON.parseObject(resultStr);
            }
            if (resultObj != null && "0".equals(resultObj.getString("code"))) {
                log.info("调用获取用户有权限的场所信息接口成功");
                return HikUserPlaceResult.success("调用获取用户有权限的场所信息接口成功", resultObj.getString("data"));
            } else {
                log.info("调用获取用户有权限的场所信息接口失败");
                return HikUserPlaceResult.fail("调用获取用户有权限的场所信息接口失败");
            }
        } catch (Exception e) {
            log.info("调用获取用户有权限的场所信息接口失败:" + e.getMessage());
            e.printStackTrace();
        }
        return HikUserPlaceResult.fail("调用获取用户有权限的场所信息接口失败");
    }



    public String getHikRldwUrl(String baqid){
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        BaqConfiguration baqConfiguration = baqznpz.getBaqConfiguration();
        return baqConfiguration.getHkrldwUrl();
    }

    public String getHikRldwParams(String baqid){
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        BaqConfiguration baqConfiguration = baqznpz.getBaqConfiguration();
        return baqConfiguration.getHkrldwParam();
    }

    public void setParams(String baqid, String serverAddress){
        log.info("设置链接参数baqid：" + baqid);
        log.info("设置链接参数地址：" + serverAddress);
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        BaqConfiguration baqConfiguration = baqznpz.getBaqConfiguration();
        String address = baqConfiguration.getHkrldwUrl();
        String params = baqConfiguration.getHkrldwParam();
        String hikPlaceCode = baqConfiguration.getHkrldwPlaceCode();
        log.info("海康人脸定位地址：" + address);
        log.info("海康人脸定位配置参数：" + params);
        log.info("海康人脸定位场所编码：" + hikPlaceCode);
        Map<String, String> map = JSON.parseObject(params, Map.class);
        ArtemisConfig.host = address; // 平台的 ip 端口
        ArtemisConfig.appKey = map.get("appKey"); // 密钥 appkey
        ArtemisConfig.appSecret = map.get("appSecret");// 密钥 appSecret
        ARTEMIS_PATH = map.get("path");
        url = ARTEMIS_PATH + serverAddress;
        placeCode = hikPlaceCode;
        path.clear();
        path.put(map.get("method"), url);
    }
}

