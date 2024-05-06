package com.wckj.chasstage.api.server.imp.device.internal.impl;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public abstract class BaseDeviceOper {

    @Autowired
    private DeviceProxy deviceProxy;

    protected DevResult processOper(String baqid , String method, Object[] param){
        return deviceProxy.invoke(baqid, method, param);
    }

    protected DevResult processOperNew(String baqid , String method, LinkedHashMap<String,Object> param){
        return deviceProxy.invokeNew(baqid, method, param);
    }
}
