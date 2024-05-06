package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class YthcjBean {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("人员姓名")
    private String ryxm;
    @ApiModelProperty("是否指纹采集")
    private String zwcj;
    @ApiModelProperty("是否DNA采集")
    private String dnacj;
    @ApiModelProperty("是否毛发采集")
    private String mfcj;
    @ApiModelProperty("涉毒情况")
    private String sdqk;
    @ApiModelProperty("尿液检查")
    private String nyjc;

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

    public String getZwcj() {
        return zwcj;
    }

    public void setZwcj(String zwcj) {
        this.zwcj = zwcj;
    }

    public String getDnacj() {
        return dnacj;
    }

    public void setDnacj(String dnacj) {
        this.dnacj = dnacj;
    }

    public String getMfcj() {
        return mfcj;
    }

    public void setMfcj(String mfcj) {
        this.mfcj = mfcj;
    }

    public String getSdqk() {
        return sdqk;
    }

    public void setSdqk(String sdqk) {
        this.sdqk = sdqk;
    }

    public String getNyjc() {
        return nyjc;
    }

    public void setNyjc(String nyjc) {
        this.nyjc = nyjc;
    }
}