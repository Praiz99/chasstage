package com.wckj.chasstage.modules.shsng.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ChasSng {

    private String id;

    private Short isdel;

    private String dataFlag;

    private String lrrSfzh;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lrsj;

    private String xgrSfzh;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xgsj;

    private String shbh;

    private String zgqk;

    private String sngid;

    private String baqid;

    private String baqmc;

    private String kzcs1;

    private String kzcs2;

    private String sblx;

    private String sbmc;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ffsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ghsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sjbq;

    private String zt;
    private String kzcs3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag == null ? null : dataFlag.trim();
    }

    public String getLrrSfzh() {
        return lrrSfzh;
    }

    public void setLrrSfzh(String lrrSfzh) {
        this.lrrSfzh = lrrSfzh == null ? null : lrrSfzh.trim();
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
        this.xgrSfzh = xgrSfzh == null ? null : xgrSfzh.trim();
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getShbh() {
        return shbh;
    }

    public void setShbh(String shbh) {
        this.shbh = shbh == null ? null : shbh.trim();
    }

    public String getZgqk() {
        return zgqk;
    }

    public void setZgqk(String zgqk) {
        this.zgqk = zgqk == null ? null : zgqk.trim();
    }

    public String getSngid() {
        return sngid;
    }

    public void setSngid(String sngid) {
        this.sngid = sngid == null ? null : sngid.trim();
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc == null ? null : baqmc.trim();
    }

    public String getKzcs1() {
        return kzcs1;
    }

    public void setKzcs1(String kzcs1) {
        this.kzcs1 = kzcs1 == null ? null : kzcs1.trim();
    }

    public String getKzcs2() {
        return kzcs2;
    }

    public void setKzcs2(String kzcs2) {
        this.kzcs2 = kzcs2 == null ? null : kzcs2.trim();
    }

    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx == null ? null : sblx.trim();
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc == null ? null : sbmc.trim();
    }

    public Date getFfsj() {
        return ffsj;
    }

    public void setFfsj(Date ffsj) {
        this.ffsj = ffsj;
    }

    public Date getGhsj() {
        return ghsj;
    }

    public void setGhsj(Date ghsj) {
        this.ghsj = ghsj;
    }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    public String getKzcs3() {
        return kzcs3;
    }

    public void setKzcs3(String kzcs3) {
        this.kzcs3 = kzcs3;
    }
}