package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.IBrakeOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.springframework.stereotype.Component;

@Component
public class IBrakeOperImpl extends BaseDeviceOper implements IBrakeOper {
    @Override
    public DevResult sendRyFaceInfo(String baqid, String rybh, String zpid, String ryxm) {
        return processOper(baqid, "sendRyFaceInfo", new Object[]{baqid,rybh,zpid,ryxm});
    }
}
