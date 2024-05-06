package com.wckj.chasstage.api.server.release.dc;

/**
 * 常量信息
 */
public interface LSConstant {
    /**
     *巡更打卡
     */
    int EVENT_TYPE_XGDK=1;
    /**
     * 人员逃脱
     */
    int EVENT_TYPE_RYTT=2;
    /**
     * 心率异常
     */
    int EVENT_TYPE_XLYC=3;
    /**
     * 低电量
     */
    int EVENT_TYPE_DDL=4;
    /**
     * 人员聚集
     */
    int EVENT_TYPE_RYJJ=5;
    /**
     * 离开审讯室
     */
    int EVENT_TYPE_LKSXS=6;
    /**
     * 到达等候室
     */
    int EVENT_TYPE_DDDHS=7;

    /**
     * 人员刷脸事件
     */
    int EVENT_TYPE_FACE=9;
}
