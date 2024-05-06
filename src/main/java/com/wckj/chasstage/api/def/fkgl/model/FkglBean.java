package com.wckj.chasstage.api.def.fkgl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FkglBean {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("访客姓名")
    private String fkxm;
    @ApiModelProperty("性别")
    private String xb;
    @ApiModelProperty("证件类型")
    private String zjlx;
    @ApiModelProperty("证件号码")
    private String fksfzh;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期")
    private Date csrq;
    @ApiModelProperty("户籍所在地")
    private String hjdxz;
    @ApiModelProperty("访客类别")
    private String fklb;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("联系电话")
    private String lxdh;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("进入时间")
    private Date jrsj;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("出区时间")
    private Date cqsj;
    @ApiModelProperty("访问时限")
    private Integer fwsx;
    @ApiModelProperty("访问原因")
    private String fwyy;
    @ApiModelProperty("照片id")
    private String zpid;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("状态 01 预约 02入区 03出区")
    private String zt;
    @ApiModelProperty("胸卡编号")
    private String wdbhL;
    @ApiModelProperty("胸卡类型")
    private String xklx;
    @ApiModelProperty("访问人数")
    private Integer fwrs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFkxm() {
        return fkxm;
    }

    public void setFkxm(String fkxm) {
        this.fkxm = fkxm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getFksfzh() {
        return fksfzh;
    }

    public void setFksfzh(String fksfzh) {
        this.fksfzh = fksfzh;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getHjdxz() {
        return hjdxz;
    }

    public void setHjdxz(String hjdxz) {
        this.hjdxz = hjdxz;
    }

    public String getFklb() {
        return fklb;
    }

    public void setFklb(String fklb) {
        this.fklb = fklb;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
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

    public Integer getFwsx() {
        return fwsx;
    }

    public void setFwsx(Integer fwsx) {
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

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getXklx() {
        return xklx;
    }

    public void setXklx(String xklx) {
        this.xklx = xklx;
    }

    public Integer getFwrs() {
        return fwrs;
    }

    public void setFwrs(Integer fwrs) {
        this.fwrs = fwrs;
    }
}
