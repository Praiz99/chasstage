package com.wckj.chasstage.modules.rygj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 人员轨迹信息
 */
public class ChasRygj {
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lrsj;
    /**
     * 修改人身份证号
     */
    private String xgrSfzh;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
     * 人员信息表id,民警入区id,访客登记id,对讲机id
     */
    private String ryid;
    /**
     * 腕带高频编号
     */
    private String wdbh;
    /**
     * 区域表ysid（dc数据库主键）
     */
    private String qyid;
    /**
     * 人员姓名
     */
    private String xm;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date kssj;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date jssj;
    /**
     * 负责人姓名
     */
    private String fzrxm;
    /**
     * 活动内容
     */
    private String hdnr;
    /**
     * 设备ysid
     */
    private String sbid;
    /**
     * 区域名称
     */
    private String qymc;
    /**
     * 人员编号
     */
    private String rybh;
    /**
     * 备注
     */
    private String bz;
    /**
     * 人员类型 嫌疑人（xyr）民警（mj）访客（fk）对讲机（djj）
     */
    private String rylx;

    public ChasRygj(){
        this.isdel=0;
        this.dataflag="0";
    }

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

    public String getDataflag() {
        return dataflag;
    }

    public void setDataflag(String dataflag) {
        this.dataflag = dataflag;
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

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getWdbh() {
        return wdbh;
    }

    public void setWdbh(String wdbh) {
        this.wdbh = wdbh;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getFzrxm() {
        return fzrxm;
    }

    public void setFzrxm(String fzrxm) {
        this.fzrxm = fzrxm;
    }

    public String getHdnr() {
        return hdnr;
    }

    public void setHdnr(String hdnr) {
        this.hdnr = hdnr;
    }

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }
}