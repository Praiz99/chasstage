package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.ILocationOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import org.springframework.stereotype.Component;

@Component
public class ILocationOperImpl extends BaseDeviceOper implements ILocationOper {
    @Override
    public DevResult wdBd(String baqid, String baqmc, String wdbhL, String tagtype, String wdbhH, String xm, String rybh) {
        return processOper(baqid, "locationAdd",
                new Object[] { baqid, baqmc,
                wdbhL, tagtype, wdbhH, xm, rybh });
    }

    @Override
    public DevResult wdJc(String baqid, String wdbhL, String rybh) {
        return processOper(baqid, "locationDelete", new Object[] { baqid,
                wdbhL, rybh });
    }

    @Override
    public DevResult wdDdl(String baqid, ChasSb wd) {
        return processOper(baqid, "getDeviceByid", new Object[] { baqid,
                wd.getSbbh(), wd.getSblx() });
    }
}
