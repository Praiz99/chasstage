package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface IDzspOper {

    /**
     * 控制电子水牌显示信息
     * @param baqid 办案区编号
     * @param sbbh 设备编号
     * @param zpid 照片id
     * @param ryxm 人员姓名
     * @param zbmj 主办民警
     * @param sxsname 审讯室名称
     * @param status 状态 -1异常 0.空闲 1.待审讯 2.审讯中
     * @param msg 语音描述信息
     * @return
     */
    DevResult sendInfo(String baqid, String sbbh, String zpid, String ryxm, String zbmj, String sxsname, Integer status, String msg);


}
