package com.wckj.chasstage.api.def.mjgl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MjglBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("民警身份证号")
    private String mjsfzh;
    @ApiModelProperty("民警姓名")
    private String mjxm;
    @ApiModelProperty("民警警号")
    private String mjjh;
    @ApiModelProperty("手环高频编号")
    private String wdbhH;
    @ApiModelProperty("手环低频编号")
    private String wdbhL;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("进入时间")
    private Date jrsj;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("出区时间")
    private Date cqsj;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("证件类型")
    private String zjlx;
    @ApiModelProperty("联系电话")
    private String lxdh;
    @ApiModelProperty("访问时限")
    private Short fwsx;
    @ApiModelProperty("访问原因")
    private String fwyy;
    @ApiModelProperty("照片id")
    private String zpid;
    @ApiModelProperty("状态01 预约 02入区 03出区(字典：FSARRQZT)")
    private String zt;
    @ApiModelProperty("性别")
    private String xb;
    @ApiModelProperty("胸卡类型")
    private String xklx;

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

    public String getMjsfzh() {
        return mjsfzh;
    }

    public void setMjsfzh(String mjsfzh) {
        this.mjsfzh = mjsfzh;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
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

    public Date getJrsj() {
        return jrsj;
    }

    public void setJrsj(Date jrsj) {
        this.jrsj = jrsj;
    }

    public Date getCqsj() {
        return cqsj;
    }

    public void setCqsj(Date cqsj) {
        this.cqsj = cqsj;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public Short getFwsx() {
        return fwsx;
    }

    public void setFwsx(Short fwsx) {
        this.fwsx = fwsx;
    }

    public String getFwyy() {
        return fwyy;
    }

    public void setFwyy(String fwyy) {
        this.fwyy = fwyy;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXklx() {
        return xklx;
    }

    public void setXklx(String xklx) {
        this.xklx = xklx;
    }
}
