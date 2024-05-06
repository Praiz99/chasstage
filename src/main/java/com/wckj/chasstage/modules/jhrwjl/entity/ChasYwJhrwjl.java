package com.wckj.chasstage.modules.jhrwjl.entity;

import java.util.Date;

/**
 * 监护任务记录
 */
public class ChasYwJhrwjl {
    /**
     *
     */
    private String id;

    /**
     *
     */
    private int isdel;

    /**
     *
     */
    private String dataFlag;

    /**
     *
     */
    private String lrrSfzh;

    /**
     *
     */
    private Date lrsj;

    /**
     *
     */
    private String xgrSfzh;

    /**
     *
     */
    private Date xgsj;

    /**
     *
     */
    private String baqid;

    /**
     *
     */
    private String baqmc;

    /**
     * 戒护人员姓名
     */
    private String ryxm;

    /**
     * 嫌疑人员编号
     */
    private String rybh;

    /**
     * 戒护任务编号
     */
    private String jhrwbh;

    /**
     * 任务状态
     */
    private String rwzt;

    /**
     * 记录时间
     */
    private Date jlsj;

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

    public String getRwzt() {
        return rwzt;
    }

    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }

    public Date getJlsj() {
        return jlsj;
    }

    public void setJlsj(Date jlsj) {
        this.jlsj = jlsj;
    }

    public Date getSjbq() { return sjbq;}

    public void setSjbq(Date sjbq) { this.sjbq = sjbq; }

    public String getRybh() { return rybh; }

    public void setRybh(String rybh) { this.rybh = rybh; }
}