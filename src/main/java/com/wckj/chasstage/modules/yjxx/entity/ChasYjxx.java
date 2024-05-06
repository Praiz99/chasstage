package com.wckj.chasstage.modules.yjxx.entity;

import java.util.Date;

/**
 * 预警信息
 */
public class ChasYjxx {
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
     * 办案区id
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
     * 预警描述
     */
    private String jqms;
    /**
     * 预警状态
     */
    private String yjzt;
    /**
     * 预警级别
     */
    private String yjjb;
    /**
     * 触发时间
     */
    private Date cfsj;
    /**
     * 处理人姓名
     */
    private String clrxm;
    /**
     * 处理时间
     */
    private Date clsj;
    /**
     * 触发人姓名
     */
    private String cfrxm;
    /**
     * 触发人id
     */
    private String cfrid;
    /**
     * 触发区域名称
     */
    private String cfqymc;
    /**
     * 触发设备id
     */
    private String cfsbid;
    /**
     * 触发区域id
     */
    private String cfqyid;
    
    public ChasYjxx(){
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


    public String getYjlb() {
        return yjlb;
    }


    public void setYjlb(String yjlb) {
        this.yjlb = yjlb == null ? null : yjlb.trim();
    }


    public String getJqms() {
        return jqms;
    }


    public void setJqms(String jqms) {
        this.jqms = jqms == null ? null : jqms.trim();
    }


    public String getYjzt() {
        return yjzt;
    }


    public void setYjzt(String yjzt) {
        this.yjzt = yjzt == null ? null : yjzt.trim();
    }


    public String getYjjb() {
        return yjjb;
    }


    public void setYjjb(String yjjb) {
        this.yjjb = yjjb == null ? null : yjjb.trim();
    }


    public Date getCfsj() {
        return cfsj;
    }


    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }


    public String getClrxm() {
        return clrxm;
    }


    public void setClrxm(String clrxm) {
        this.clrxm = clrxm == null ? null : clrxm.trim();
    }


    public Date getClsj() {
        return clsj;
    }


    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }


    public String getCfrxm() {
        return cfrxm;
    }


    public void setCfrxm(String cfrxm) {
        this.cfrxm = cfrxm == null ? null : cfrxm.trim();
    }


    public String getCfrid() {
        return cfrid;
    }


    public void setCfrid(String cfrid) {
        this.cfrid = cfrid == null ? null : cfrid.trim();
    }


    public String getCfqymc() {
        return cfqymc;
    }


    public void setCfqymc(String cfqymc) {
        this.cfqymc = cfqymc == null ? null : cfqymc.trim();
    }


    public String getCfsbid() {
        return cfsbid;
    }


    public void setCfsbid(String cfsbid) {
        this.cfsbid = cfsbid == null ? null : cfsbid.trim();
    }


    public String getCfqyid() {
        return cfqyid;
    }


    public void setCfqyid(String cfqyid) {
        this.cfqyid = cfqyid == null ? null : cfqyid.trim();
    }
}