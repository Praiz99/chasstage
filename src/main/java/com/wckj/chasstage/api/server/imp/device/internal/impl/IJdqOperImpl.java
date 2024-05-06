package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.IJdqOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.springframework.stereotype.Component;

@Component
public class IJdqOperImpl extends BaseDeviceOper implements IJdqOper {
    @Override
    public DevResult openOrClose(String baqid, String sbbh, Integer onOrOff) {
        return processOper(baqid, "relayOper",
                new Object[] { baqid,sbbh, onOrOff });
    }

    @Override
    public DevResult openDelay(String baqid, String sbbh, long delayTime) {
        return processOper(baqid, "relayOperBytime",
                new Object[] { baqid,sbbh, delayTime });
    }

    @Override
    public DevResult getQqdhRec(String baqid,  String phoneNo, String thkssj, String thjssj) {
        return processOper(baqid,"getQqdhRec",new Object[]{baqid,"1",phoneNo,thkssj,thjssj});
    }
}
