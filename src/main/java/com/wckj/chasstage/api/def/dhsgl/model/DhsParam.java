package com.wckj.chasstage.api.def.dhsgl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;


public class DhsParam{

    @ApiParam(value = "等候室ID",required = true)
    private String dhsId;
    @ApiParam(value = "人员姓名")
    private String ryxm;
    @ApiParam(value = "人员性别")
    private String ryxb;
    @ApiParam(value = "证件类型")
    private String zjlx;
    @ApiParam(value = "证件号码")
    private String zjhm;
    @ApiParam(value = "出生日期")
    private String csrq;
    @ApiParam(value = "户籍所在地")
    private String hjszd;
    @ApiParam(value = "入区原因")
    private String rqyy;
    @ApiParam(value = "入区时间")
    private String rqsj;
    @ApiParam(value = "到案方式")
    private String dafs;
    @ApiParam(value = "人员类型")
    private String rylx;
    @ApiParam(value = "主办民警")
    private String zbmj;
    @ApiParam(value = "特殊群体")
    private String tsqt;
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "app用户身份证")
    private String appSfzh;
    @ApiParam(value = "出生日期开始时间")
    private String csrq1;
    @ApiParam(value = "出生日期结束时间")
    private String csrq2;
    @ApiParam(value = "入区时间开始时间")
    private String rqsj1;
    @ApiParam(value = "入区时间结束时间")
    private String rqsj2;

    public String getDhsId() {
        return dhsId;
    }

    public void setDhsId(String dhsId) {
        this.dhsId = dhsId;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRyxb() {
        return ryxb;
    }

    public void setRyxb(String ryxb) {
        this.ryxb = ryxb;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getHjszd() {
        return hjszd;
    }

    public void setHjszd(String hjszd) {
        this.hjszd = hjszd;
    }

    public String getRqyy() {
        return rqyy;
    }

    public void setRqyy(String rqyy) {
        this.rqyy = rqyy;
    }

    public String getRqsj() {
        return rqsj;
    }

    public void setRqsj(String rqsj) {
        this.rqsj = rqsj;
    }

    public String getDafs() {
        return dafs;
    }

    public void setDafs(String dafs) {
        this.dafs = dafs;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getZbmj() {
        return zbmj;
    }

    public void setZbmj(String zbmj) {
        this.zbmj = zbmj;
    }

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getAppSfzh() {
        return appSfzh;
    }

    public void setAppSfzh(String appSfzh) {
        this.appSfzh = appSfzh;
    }

    public String getCsrq1() {
        return csrq1;
    }

    public void setCsrq1(String csrq1) {
        this.csrq1 = csrq1;
    }

    public String getCsrq2() {
        return csrq2;
    }

    public void setCsrq2(String csrq2) {
        this.csrq2 = csrq2;
    }

    public String getRqsj1() {
        return rqsj1;
    }

    public void setRqsj1(String rqsj1) {
        this.rqsj1 = rqsj1;
    }

    public String getRqsj2() {
        return rqsj2;
    }

    public void setRqsj2(String rqsj2) {
        this.rqsj2 = rqsj2;
    }
}
