package com.wckj.chasstage.modules.httpApi.client.hkrldw.service;

import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.util.*;

import java.util.Map;

/**
 * 海康人脸定位接口
 */
public interface HikFaceLocationService {


    /**
     * 开始海康人脸定位
     * @param hikFaceLocationRyxx
     * @return
     */
    Map<String, Object> startLocation(HikFaceLocationRyxx hikFaceLocationRyxx);

    /**
     * 更新海康人脸定位人员信息
     * @param hikFaceLocationRyxx
     * @param registerCode 海康人脸定位唯一标识
     * @return
     */
    Map<String, Object> updateHikLocationRyxx(HikFaceLocationRyxx hikFaceLocationRyxx, String registerCode);

    /**
     * 结束海康人脸定位
     * @param baqid
     * @param registerCode
     * @return
     */
    Map<String, Object> endLocation(String baqid, String registerCode);

    /**
     * 获取人员历史轨迹页面免登录 URL
     * @param baqid
     * @param registerCode
     * @return
     */
    Map<String, Object> getHistoricalUrl(String baqid, String registerCode);

    /**
     * 获取人员实时定位页面免登录 URL
     * @param baqid
     * @return
     */
    Map<String, Object> getRealTimeUrl(String baqid);

    /**
     * 获取正在被定位人员的位置信息
     * @param baqid
     * @param registerCode
     * @return
     */
    HikPersonPosResult getPersonPosition(String baqid, String registerCode);

    /**
     * 获取地图上正在被定位人员信息
     * @param baqid
     * @param mapId
     * @return
     */
    HikMapPersonResult getMapPersionPos(String baqid, int mapId);

    /**
     * 获取人员经过区域列表
     * @param baqid
     * @param registerCode
     * @param beginTime 时间格式yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * @param endTime
     * @return
     */
    HikLocationPathsResult getLocationPaths(String baqid, String registerCode, String beginTime, String endTime);

    /**
     * 获取定位轨迹详细坐标点位信息
     * @param baqid
     * @param pathMapId 轨迹地图图层记录 id
     * @param isFilter 是否过滤轨迹点位，为 true 时每个区域轨迹会限制在 100 个点以内，避免出现点位太多时造成显示压力
     * @param beginTime 时间格式yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * @param endTime
     * @return
     */
    HikPathPositionResult getPathPositions(String baqid, int pathMapId, Boolean isFilter, String beginTime, String endTime);

    /**
     * 获取用户有权限的场所信息
     * @param baqid
     * @return
     */
    HikUserPlaceResult getPlacesByUser(String baqid);
}
