package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

public class RyxxResult {

    @ApiModelProperty("办案区人员信息")
    private Map<String,Object> baqryxx;

    @ApiModelProperty("储物柜ID")
    private String cwgid;

    @ApiModelProperty("储物柜编号")
    private String cwgbh;

    @ApiModelProperty("储物柜类型")
    private String cwglx;

    @ApiModelProperty("同案人员姓名")
    private String taryxm;

    @ApiModelProperty("腕带编号H")
    private String wdbhH;

    @ApiModelProperty("腕带编号L")
    private String wdbhL;

    @ApiModelProperty("手机柜ID")
    private String sjgId;

    @ApiModelProperty("手机柜编号")
    private String sjgBh;

    @ApiModelProperty("业务编号")
    private String ywbh;

    public Map<String,Object> getBaqryxx() {
        return baqryxx;
    }

    public void setBaqryxx(Map<String,Object> baqryxx) {
        this.baqryxx = baqryxx;
    }

    public String getCwgid() {
        return cwgid;
    }

    public void setCwgid(String cwgid) {
        this.cwgid = cwgid;
    }

    public String getCwgbh() {
        return cwgbh;
    }

    public void setCwgbh(String cwgbh) {
        this.cwgbh = cwgbh;
    }

    public String getCwglx() {
        return cwglx;
    }

    public void setCwglx(String cwglx) {
        this.cwglx = cwglx;
    }

    public String getTaryxm() {
        return taryxm;
    }

    public void setTaryxm(String taryxm) {
        this.taryxm = taryxm;
    }

    public String getWdbhH() {
        return wdbhH;
    }

    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getSjgId() {
        return sjgId;
    }

    public void setSjgId(String sjgId) {
        this.sjgId = sjgId;
    }

    public String getSjgBh() {
        return sjgBh;
    }

    public void setSjgBh(String sjgBh) {
        this.sjgBh = sjgBh;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }
}
