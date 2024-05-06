package com.wckj.chasstage.modules.ryjl.entity;

import java.util.Date;

public class ChasRyjl {

    private String id;


    private Integer isdel;


    private String dataflag;


    private String lrrSfzh;


    private Date lrsj;


    private String xgrSfzh;


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
     * 人员编号
     */
    private String rybh;

    /**
     * 高频腕带编号
     */
    private String wdbhH;

    /**
     * 低频腕带编号
     */
    private String wdbhL;

    /**
     * 人员状态 1，进行中 2，结束
     */
    private String ryzt;

    /**
     * 业务编号
     */
    private String ajbh;
    /**
     * 储物柜id
     */
    private String cwgId;
    /**
     * 储物柜类型（字典）
     */
    private String cwgLx;

    /**
     * 储物柜盒子编号
     */
    private String cwgBh;
    /**
     * 存放随身物品时间
     */
    private Date cfsj;

    /**
     * 取走随身物品时间
     */
    private Date qzsj;

    /**
     * 性别
     */
    private String xb;

    /**
     * 人员姓名
     */
    private String xm;

    /**
     * 人员类别
     */
    private String rylb;

    /**
     * 同案预警(0,否，1是)
     */
    private Integer sftayj;

    /**
     * 等候室编号
     */
    private String dhsBh;

    /**
     * 审讯室编号
     */
    private String sxsBh;

    private String sjgId;

    private String sjgBh;

    private String lczt;

    private String ywbh;

    public ChasRyjl(){
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


    public String getRybh() {
        return rybh;
    }


    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }


    public String getWdbhH() {
        return wdbhH;
    }


    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH == null ? null : wdbhH.trim();
    }


    public String getWdbhL() {
        return wdbhL;
    }


    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL == null ? null : wdbhL.trim();
    }


    public String getRyzt() {
        return ryzt;
    }


    public void setRyzt(String ryzt) {
        this.ryzt = ryzt == null ? null : ryzt.trim();
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getCwgId() {
        return cwgId;
    }
    public String getCwgLx() {
        return cwgLx;
    }


    public void setCwgId(String cwgId) {
        this.cwgId = cwgId == null ? null : cwgId.trim();
    }
    public void setCwgLx(String cwgLx) {
        this.cwgLx = cwgLx == null ? null : cwgLx.trim();
    }


    public String getCwgBh() {
        return cwgBh;
    }


    public void setCwgBh(String cwgBh) {
        this.cwgBh = cwgBh == null ? null : cwgBh.trim();
    }


    public Date getCfsj() {
        return cfsj;
    }


    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }


    public Date getQzsj() {
        return qzsj;
    }


    public void setQzsj(Date qzsj) {
        this.qzsj = qzsj;
    }


    public String getXb() {
        return xb;
    }


    public void setXb(String xb) {
        this.xb = xb == null ? null : xb.trim();
    }


    public String getXm() {
        return xm;
    }


    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }


    public String getRylb() {
        return rylb;
    }


    public void setRylb(String rylb) {
        this.rylb = rylb == null ? null : rylb.trim();
    }


    public Integer getSftayj() {
        return sftayj;
    }


    public void setSftayj(Integer sftayj) {
        this.sftayj = sftayj;
    }


    public String getDhsBh() {
        return dhsBh;
    }


    public void setDhsBh(String dhsBh) {
        this.dhsBh = dhsBh == null ? null : dhsBh.trim();
    }


    public String getSxsBh() {
        return sxsBh;
    }


    public void setSxsBh(String sxsBh) {
        this.sxsBh = sxsBh == null ? null : sxsBh.trim();
    }

    public String getSjgId() {
        return sjgId;
    }

    public void setSjgId(String sjgId) {
        this.sjgId = sjgId;
    }

    public String getSjgBh() {
        return sjgBh;
    }

    public void setSjgBh(String sjgBh) {
        this.sjgBh = sjgBh;
    }

    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }
}