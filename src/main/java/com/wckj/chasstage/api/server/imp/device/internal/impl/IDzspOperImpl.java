package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.IDzspOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.springframework.stereotype.Component;

@Component
public class IDzspOperImpl extends BaseDeviceOper implements IDzspOper {
    @Override
    public DevResult sendInfo(String baqid, String sbbh, String zpid, String ryxm, String zbmj, String sxsname, Integer status, String msg) {
        return processOper(baqid,"sendDzspMsg", new Object[]{baqid,sbbh,zpid,ryxm,zbmj,sxsname,status,msg});
    }
}
