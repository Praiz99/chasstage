package com.wckj.chasstage.modules.rygj.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 定位快照数据，只保存人员最新位置，用于检测预警信息，2.5D地图等业务
 */
public class ChasRygjSnap {
    /**
     * 主键，同rybh
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
    private Date kssj;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    /**
     * 特殊群体，2.5D地图中使用，不在数据表字段中
     */
    private String tsqt;


    public ChasRygjSnap(){
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

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid == null ? null : ryid.trim();
    }


    public String getWdbh() {
        return wdbh;
    }


    public void setWdbh(String wdbh) {
        this.wdbh = wdbh == null ? null : wdbh.trim();
    }


    public String getQyid() {
        return qyid;
    }


    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }


    public String getXm() {
        return xm;
    }


    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
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
        this.fzrxm = fzrxm == null ? null : fzrxm.trim();
    }


    public String getHdnr() {
        return hdnr;
    }


    public void setHdnr(String hdnr) {
        this.hdnr = hdnr == null ? null : hdnr.trim();
    }


    public String getSbid() {
        return sbid;
    }


    public void setSbid(String sbid) {
        this.sbid = sbid == null ? null : sbid.trim();
    }


    public String getQymc() {
        return qymc;
    }


    public void setQymc(String qymc) {
        this.qymc = qymc == null ? null : qymc.trim();
    }


    public String getRybh() {
        return rybh;
    }


    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
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

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }
}