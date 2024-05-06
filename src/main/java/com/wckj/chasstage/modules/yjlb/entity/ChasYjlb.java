package com.wckj.chasstage.modules.yjlb.entity;

import java.util.Date;

/**
 * 预警类别
 */
public class ChasYjlb {
    /**
     * 主键
     */
    private String id;

    /**
     * 逻辑删除
     */
    private Integer isdel;

    /**
     * 版本
     */
    private String dataflag;
    /**
     * 录入人身份证号
     */
    private String lrrSfzh;

    /**
     * 录入时间
     */
    private Date lrsj;

    /**
     * 修改人身份证号
     */
    private String xgrSfzh;

    /**
     * 修改时间
     */
    private Date xgsj;

    /**
     * 办案区
     */
    private String baqid;

    /**
     * 办案区名称
     */
    private String baqmc;

    /**
     * 预警类别
     */
    private String yjlb;
    /**
     * 预警时长
     */
    private Integer yjsc;
    /**
     * 触发方式
     */
    private String cffs;
    /**
     * 预警级别
     */
    private String yjjb;
    /**
     * 刑事案件预警时间（时）
     */
    private String xsajyjsj;
    /**
     * 行政案件预警时间（时）
     */
    private String xzajyjsj;
    /**
     * 刑事提前预警时间（时）
     */
    private String xstqyjsj;
    /**
     * 行政提前预警时间（时）
     */
    private String xztqyjsj;
    /**
     * 预警方式
     */
    private String yjfs;

    public String getYjfs() {
        return yjfs;
    }

    public void setYjfs(String yjfs) {
        this.yjfs = yjfs;
    }

    public ChasYjlb(){
        this.isdel=0;
        this.dataflag="0";
    }

    public String getXsajyjsj() {
        return xsajyjsj;
    }

    public void setXsajyjsj(String xsajyjsj) {
        this.xsajyjsj = xsajyjsj;
    }

    public String getXzajyjsj() {
        return xzajyjsj;
    }

    public void setXzajyjsj(String xzajyjsj) {
        this.xzajyjsj = xzajyjsj;
    }

    public String getXstqyjsj() {
        return xstqyjsj;
    }

    public void setXstqyjsj(String xstqyjsj) {
        this.xstqyjsj = xstqyjsj;
    }

    public String getXztqyjsj() {
        return xztqyjsj;
    }

    public void setXztqyjsj(String xztqyjsj) {
        this.xztqyjsj = xztqyjsj;
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


    public String getYjlb() {
        return yjlb;
    }


    public void setYjlb(String yjlb) {
        this.yjlb = yjlb == null ? null : yjlb.trim();
    }

    public Integer getYjsc() {
        return yjsc;
    }

    public void setYjsc(Integer yjsc) {
        this.yjsc = yjsc;
    }

    public String getCffs() {
        return cffs;
    }

    public void setCffs(String cffs) {
        this.cffs = cffs;
    }

    public String getYjjb() {
        return yjjb;
    }

    public void setYjjb(String yjjb) {
        this.yjjb = yjjb;
    }
}