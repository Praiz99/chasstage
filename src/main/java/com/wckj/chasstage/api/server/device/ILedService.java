package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

import java.util.List;

public interface ILedService {
    //刷新等候室LED
    DevResult refreshDhsLed(String baqid, String qyid);
    //刷新等候室大屏LED
    DevResult refreshDhsDp(String baqid);
    //刷新审讯室LED
    DevResult refreshSxsLed(String baqid, String qyId, String rybh);
    DevResult refreshSxsLedBySxskz(String baqid, String qyId, String sxskz);
    //刷新审讯室大小屏LED
    DevResult refreshSxsDxp(String baqid, String text);
    //刷新民警休息室LED
    DevResult refreshMjRoom(String baqid);
    //刷新亲情电话LED
    DevResult refreshStation(String baqid, String text);
    //刷新看守区大屏
    DevResult refreshKsqDp(List<ChasSb> led, String text, long delay, String kxtext);
}
