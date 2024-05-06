package com.wckj.chasstage.modules.zpxx.entity;

import java.util.Date;

public class ChasSswpZpxx {
    /**
     *id
     */
    private String id;

    /**
     *逻辑删除
     */
    private Integer isdel;

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
     *人员编号
     */
    private String rybh;

    /**
     *物品id
     */
    private String wpid;

    /**
     *照片id
     */
    private String bizId;

    /**
     *照片类型
     */
    private String zplx;

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


    public String getWpid() {
        return wpid;
    }


    public void setWpid(String wpid) {
        this.wpid = wpid == null ? null : wpid.trim();
    }


    public String getBizId() {
        return bizId;
    }


    public void setBizId(String bizId) {
        this.bizId = bizId == null ? null : bizId.trim();
    }


    public String getZplx() {
        return zplx;
    }


    public void setZplx(String zplx) {
        this.zplx = zplx == null ? null : zplx.trim();
    }
}