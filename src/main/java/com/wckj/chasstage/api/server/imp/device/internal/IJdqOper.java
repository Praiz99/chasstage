package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface IJdqOper {

    /**
     * 继电器的开关
     * @param baqid 办案区编号
     * @param sbbh 设备编号
     * @param onOrOff 0 开启 1 关闭
     * @return
     */
    DevResult openOrClose(String baqid, String sbbh, Integer onOrOff);

    /** 打开继电器后延时关闭 */
    /**
     *
     * @param baqid  办案区编号
     * @param sbbh 设备编号
     * @param delayTime 延时时间 单位毫秒
     * @return
     */
    DevResult openDelay(String baqid, String sbbh, long delayTime);
    DevResult getQqdhRec(String org, String phoneNo, String thkssj, String thjssj);
}
