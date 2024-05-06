package com.wckj.chasstage.modules.wszh.entity;

import java.util.Date;

/**
 * 文书字号
 */
public class ChasWszh {

    private String id;

    private Integer isdel;

    private String dataflag;

    private String lrrSfzh;

    private Date lrsj;

    private String xgrSfzh;

    private Date xgsj;

    private String baqid;

    private String baqmc;
    /**
     * 字号头
     */
    private String zht;
    /**
     * 字号
     */
    private String zh;
    /**
     * 机构简称
     */
    private String jgjc;
    /**
     * 区域字
     */
    private String qyz;
    public ChasWszh(){
        this.isdel=0;
        this.dataflag="0";
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public Integer getIsdel() {
        return isdel;
    }


    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }


    public String getDataflag() {
        return dataflag;
    }


    public void setDataflag(String dataflag) {
        this.dataflag = dataflag == null ? null : dataflag.trim();
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


    public String getZht() {
        return zht;
    }

    public void setZht(String zht) {
        this.zht = zht == null ? null : zht.trim();
    }


    public String getZh() {
        return zh;
    }


    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
    }

    public String getJgjc() {
        return jgjc;
    }

    public void setJgjc(String jgjc) {
        this.jgjc = jgjc;
    }

    public String getQyz() {
        return qyz;
    }

    public void setQyz(String qyz) {
        this.qyz = qyz;
    }
}