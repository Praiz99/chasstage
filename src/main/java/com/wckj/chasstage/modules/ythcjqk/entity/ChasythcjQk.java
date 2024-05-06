package com.wckj.chasstage.modules.ythcjqk.entity;

import java.util.Date;

public class ChasythcjQk {
    /**
     *主键
     */
    private String id;

    /**
     *逻辑删除
     */
    private Integer isdel;

    /**
     *版本
     */
    private String dataFlag;

    /**
     *录入人身份证号
     */
    private String lrrSfzh;

    /**
     *录入时间
     */
    private Date lrsj;

    /**
     *修改人身份证号
     */
    private String xgrSfzh;

    /**
     *修改时间
     */
    private Date xgsj;

    /**
     *办案区
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *人员编号
     */
    private String rybh;

    /**
     *人员姓名
     */
    private String ryxm;

    /**
     *是否指纹采集
     */
    private String zwcj;

    /**
     *是否DNA采集
     */
    private String dnacj;

    /**
     *是否毛发采集
     */
    private String mfcj;

    /**
     *涉毒情况
     */
    private String sdqk;

    /**
     *尿液检查
     */
    private String nyjc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getLrrSfzh() {
        return lrrSfzh;
    }

    public void setLrrSfzh(String lrrSfzh) {
        this.lrrSfzh = lrrSfzh;
    }

    public Date getLrsj() {
        return lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    public String getXgrSfzh() {
        return xgrSfzh;
    }

    public void setXgrSfzh(String xgrSfzh) {
        this.xgrSfzh = xgrSfzh;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
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