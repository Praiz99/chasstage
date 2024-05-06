package com.wckj.chasstage.api.def.dhsgl.model;

import io.swagger.annotations.ApiModelProperty;

public class DhsRyBean {
    @ApiModelProperty(value = "人员信息表id")
    private String ryid;
    @ApiModelProperty(value = "人员记录表id")
    private String ryjlid;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "图片url")
    private String imgUrl;
    @ApiModelProperty(value = "手环编号")
    private String tagNo;
    //
    @ApiModelProperty(value = "心率是否正常")
    private String xlsfzc;
    @ApiModelProperty(value = "心率值")
    private String xl;
    @ApiModelProperty(value = "人员姓名")
    private String ryxm;
    @ApiModelProperty(value = "人员性别")
    private String ryxb;
    @ApiModelProperty(value = "证件类型")
    private String zjlx;
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    @ApiModelProperty(value = "出生日期")
    private String csrq;
    @ApiModelProperty(value = "户籍所在地")
    private String hjszd;
    @ApiModelProperty(value = "入区原因")
    private String rqyy;
    @ApiModelProperty(value = "入区时间")
    private String rqsj;
    @ApiModelProperty(value = "到案方式")
    private String dafs;
    @ApiModelProperty(value = "人员类型")
    private String rylx;
    @ApiModelProperty(value = "特殊群体")
    private String tsqt;
    @ApiModelProperty(value = "主办民警")
    private String zbmj;
    @ApiModelProperty(value = "人员状态")
    private String ryzt;
    @ApiModelProperty(value = "分配状态")
    private String fpzt;
    @ApiModelProperty(value = "业务编号")
    private String ywbh;
    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getRyjlid() {
        return ryjlid;
    }

    public void setRyjlid(String ryjlid) {
        this.ryjlid = ryjlid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getXlsfzc() {
        return xlsfzc;
    }

    public void setXlsfzc(String xlsfzc) {
        this.xlsfzc = xlsfzc;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
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

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }

    public String getZbmj() {
        return zbmj;
    }

    public void setZbmj(String zbmj) {
        this.zbmj = zbmj;
    }

    public String getRyzt() {
        return ryzt;
    }

    public void setRyzt(String ryzt) {
        this.ryzt = ryzt;
    }

    public String getFpzt() {
        return fpzt;
    }

    public void setFpzt(String fpzt) {
        this.fpzt = fpzt;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }
}
