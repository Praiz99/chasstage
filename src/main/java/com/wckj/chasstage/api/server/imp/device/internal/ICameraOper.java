package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

/**
 * 摄像头控制接口
 */
public interface ICameraOper {
    /**
     * 拍照
     * @param baqid 办案区编号
     * @param deviceType 设备功能
     * @param ids 设备编号 jsonarray 字符串 ['1','2']
     * @return
     */
    DevResult cameraCapture(String baqid, String org, String deviceType, String ids, String bizId);
}
