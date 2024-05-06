package com.wckj.chasstage.modules.jhry.entity;

import java.util.Date;

/**
 * 戒护人员
 */
public class ChasYwJhry {
    /**
     *id
     */
    private String id;

    /**
     *逻辑删除
     */
    private int isdel;

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
     *办案区id
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     * 人员姓名
     */
    private String ryxm;

    /**
     * 监护任务编号
     */
    private String jhrwbh;

    /**
     * 人员身份证号
     */
    private String rybh;

    /**
     * 民警警号
     */
    private String mjjh;

    /**
     * 单位代码
     */
    private String dwdm;

    /**
     * 单位名称
     */
    private String dwmc;

    private Date sjbq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
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

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getJhrwbh() {
        return jhrwbh;
    }

    public void setJhrwbh(String jhrwbh) {
        this.jhrwbh = jhrwbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public Date getSjbq() { return sjbq; }

    public void setSjbq(Date sjbq) { this.sjbq = sjbq; }
}