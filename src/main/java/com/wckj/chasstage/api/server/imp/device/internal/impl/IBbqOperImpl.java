package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.IBbqOper;
import com.wckj.chasstage.api.server.imp.device.internal.IWpgOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
public class IBbqOperImpl extends BaseDeviceOper implements IBbqOper {
    @Override
    public DevResult play(String baqid, String qyid, String msg, String sbgn) {
        if(StringUtil.isEmpty(baqid)||StringUtil.isEmpty(qyid)||StringUtil.isEmpty(msg)){
            return DevResult.error("参数错误");
        }
        if(StringUtil.isEmpty(sbgn)){
            sbgn="";
        }
        return processOper(baqid, "sendSoundInfo", new Object[]{baqid,qyid,msg,sbgn});
    }
}
