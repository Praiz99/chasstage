package com.wckj.chasstage.modules.jjrw.entity;

import java.util.Date;

/**
 * 交接任务
 */
public class ChasYwJjrw implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 6621082367624208119L;

    /**
     *id
     */
    private String id;

    /**
     *数据是否逻辑删除
     */
    private Integer isdel;

    /**
     *数据标识
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
     * 单位代码
     */
    private String dwdm;

    /**
     * 系统单位代码
     */
    private String xtdwdm;

    /**
     * 单位名称
     */
    private String dwmc;

    /**
     * 人员编号
     */
    private String rybh;

    /**
     * 人员名称
     */
    private String rymc;

    /**
     * 移交人
     */
    private String yjr;

    /**
     * 接收人
     */
    private String jsr;

    /**
     * 移交时间
     */
    private Date yjsj;

    /**
     * 接收时间
     */
    private Date jssj;

    /**
     * 任务类型 字典
     */
    private String rwlx;

    private Date sjbq;

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

    public String getDwdm(String orgCode) {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getXtdwdm() {
        return xtdwdm;
    }

    public void setXtdwdm(String xtdwdm) {
        this.xtdwdm = xtdwdm;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getYjr() {
        return yjr;
    }

    public void setYjr(String yjr) {
        this.yjr = yjr;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public Date getYjsj() {
        return yjsj;
    }

    public void setYjsj(Date yjsj) {
        this.yjsj = yjsj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }
}
