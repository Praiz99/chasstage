package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiParam;

public class YthcjParam {
    @ApiParam(value = "ID")
    private String id;
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "办案区名称")
    private String baqmc;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "人员姓名")
    private String ryxm;
    @ApiParam(value = "是否指纹采集")
    private String zwcj;
    @ApiParam(value = "是否DNA采集")
    private String dnacj;
    @ApiParam(value = "是否毛发采集")
    private String mfcj;
    @ApiParam(value = "涉毒情况")
    private String sdqk;
    @ApiParam(value = "尿液检查")
    private String nyjc;
    @ApiParam(value = "办案区人员必填 (baqryxx)，盘查人员(pcryxx)")
    private String fromSign;

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

    public String getFromSign() {
        return fromSign;
    }

    public void setFromSign(String fromSign) {
        this.fromSign = fromSign;
    }
}
