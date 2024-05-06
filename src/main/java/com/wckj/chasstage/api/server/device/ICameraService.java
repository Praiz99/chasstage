package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface ICameraService {
    DevResult cameraCapture(String baqid, String org, String sbgn, String sbbh, String bizId);
    DevResult capture(String rybh, String cameraFun, String bizId, String gw);
}
