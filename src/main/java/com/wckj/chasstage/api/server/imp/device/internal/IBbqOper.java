package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

/**
 * 语音播报器
 */
public interface IBbqOper {
    /**
     * 下发语音信息
     * @param baqid 办案区编号
     * @param qyid 区域原始id
     * @param msg 语音内容
     * @param sbgn 设备功能，默认为 "",保留参数，兼容以后需求
     * @return
     */
    DevResult play(String baqid, String qyid,String msg, String sbgn);


}
