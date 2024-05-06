package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.ILedOper;
import com.wckj.chasstage.api.server.imp.device.internal.asp.Retryable;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ILedOperImpl extends BaseDeviceOper implements ILedOper {

    //@Autowired
    //private ChasXtBaqznpzService baqznpzService;

    @Override
    @Retryable(times = 3,waitTime = 500L)
    public DevResult ledSend(String org, String deviceType, String sbbh, List<String> ledTexts, Integer showTime) {
        //BaqConfiguration baqznpz = baqznpzService.findByBaqid(org);
        JSONArray idArray = new JSONArray();
        idArray.add(sbbh);
        JSONArray textArray = new JSONArray();
        textArray.add(ledTexts);

        //if(!baqznpz.getLed()){
        //    return DevResult.success();
        //}

        DevResult result= processOper(org, "generalOpenLED",
                new Object[] { org, deviceType, idArray.toString(), textArray.toString(), showTime});
        if(result==null||result.getCode()!=1){
            throw new DeviceException("调用刷新led失败");
        }
        return result;
    }

    @Override
    @Retryable(times = 3,waitTime = 500L)
    public DevResult setRowContent(String org, String id, String text, Integer line, Integer showTime) {
        //BaqConfiguration baqznpz = baqznpzService.findByBaqid(org);
        //if(!baqznpz.getLed()){
        //    return DevResult.success();
        //}
        DevResult result= processOper(org, "specialOpenLED", new Object[] { org, id, text, line, showTime});
        //return DevResult.success("该接口暂不可用");
        if(result==null||result.getCode()!=1){
            throw new DeviceException("调用刷新led失败");
        }
        return result;
    }
}
