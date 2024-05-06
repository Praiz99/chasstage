package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

/**
 * 闸机下发接口
 */
public interface IBrakeOper {
    /**
     * 下发人脸信息
     * @param baqid 办案区编号
     * @param rybh 人员编号
     * @param zpid 照片id(bizid)
     * @param ryxm 人员姓名
     * @return
     */
    DevResult sendRyFaceInfo(String baqid, String rybh, String zpid, String ryxm);

}
