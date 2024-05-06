package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

/**
 * 定位设备接口
 */
public interface ILocationOper {


    /**
     * 腕带定位绑定
     * @param baqid 办案区编号
     * @param baqmc 办案区名称
     * @param wdbhL 腕带低频卡号
     * @param tagtypeXyr 标签类型类型 犯罪嫌疑人 民警 警车 访客
     * @param wdbhH 腕带高频卡号
     * @param xm 人员姓名
     * @param rybh 人员编号
     * @return
     */
    DevResult wdBd(String baqid, String baqmc, String wdbhL,
                   String tagtypeXyr, String wdbhH, String xm, String rybh);

    /**
     * 腕带定位解除
     * @param baqid 办案区编号
     * @param wdbhL 腕带低频卡号
     * @param rybh 人员编号
     * @return
     */
    DevResult wdJc(String baqid, String wdbhL, String rybh);

    /**
     * 检测手环或者胸卡是否低电量
     * @param baqid
     * @param wd
     * @return
     */
    DevResult wdDdl(String baqid, ChasSb wd);
}
