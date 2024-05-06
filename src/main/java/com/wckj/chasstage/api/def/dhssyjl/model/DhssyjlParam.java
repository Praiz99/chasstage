package com.wckj.chasstage.api.def.dhssyjl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author:zengrk
 */
public class DhssyjlParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam(value = "等候室id")
    private String qyid;
    @ApiParam("嫌疑人")
    private String ryxm;
    @ApiParam("开始使用时间")
    private String sysj1;
    @ApiParam("结束使用时间")
    private String sysj2;
    @ApiParam("人员编号")
    private String rybh;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getSysj1() {
        return sysj1;
    }

    public void setSysj1(String sysj1) {
        this.sysj1 = sysj1;
    }

    public String getSysj2() {
        return sysj2;
    }

    public void setSysj2(String sysj2) {
        this.sysj2 = sysj2;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
}
