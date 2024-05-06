package com.wckj.chasstage.api.def.clsyjl.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 车辆使用记录
 */
public class ClsyjlBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("车辆id")
    private String clid;
    @ApiModelProperty("车牌号")
    private String clNumber;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("人员姓名")
    private String ryxm;
    @ApiModelProperty("手环低频编号")
    private String wdbhL;
    @ApiModelProperty("手环高频编号")
    private String wdbhH;
    @ApiModelProperty("使用批次编号")
    private String sypcbh;
    @ApiModelProperty("是否使用结束")
    private String isend;
    @ApiModelProperty("基站编号")
    private String jzbh;
    @ApiModelProperty("开始时间")
    private Date kssj;
    @ApiModelProperty("结束时间")
    private Date jssj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getWdbhH() {
        return wdbhH;
    }

    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH;
    }

    public String getSypcbh() {
        return sypcbh;
    }

    public void setSypcbh(String sypcbh) {
        this.sypcbh = sypcbh;
    }

    public String getIsend() {
        return isend;
    }

    public void setIsend(String isend) {
        this.isend = isend;
    }

    public String getJzbh() {
        return jzbh;
    }

    public void setJzbh(String jzbh) {
        this.jzbh = jzbh;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }
}