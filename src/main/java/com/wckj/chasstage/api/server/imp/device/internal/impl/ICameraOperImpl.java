package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.ICameraOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.springframework.stereotype.Component;

@Component
public class ICameraOperImpl extends BaseDeviceOper implements ICameraOper {
    @Override
    public DevResult cameraCapture(String baqid, String org, String deviceType, String id, String bizId) {

        return processOper(baqid,"cameraCaptureS",
                new Object[] { baqid,org,deviceType, id ,bizId});
    }
}
