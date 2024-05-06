package com.wckj.chasstage.api.server.release.dc.service;

import com.wckj.chasstage.api.server.imp.device.util.DevResult;

import java.util.Date;

/**
 * @author:zengrk
 */
public interface IHikBrakeService {
    public DevResult IssuedToBrakeByFaceAsyn(String type, String ryid, String ryxm, String zpId,String baqid, Date startTime);
    public DevResult deleteIssuedToBrakeByFaceAsyn(String type,String ryid, String ryxm, String zpId,String baqid, Date startTime);

}
