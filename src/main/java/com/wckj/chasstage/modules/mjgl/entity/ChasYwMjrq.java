package com.wckj.chasstage.modules.mjgl.entity;

import java.util.Date;

/**
 * 民警入区信息
 */
public class ChasYwMjrq {
    /**
     * id
     */
    private String id;
    /**
     * 逻辑删除
     */
    private Integer isdel;
    /**
     * 版本
     */
    private String dataFlag;
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
     * 民警身份证号
     */
    private String mjsfzh;
    /**
     * 民警姓名
     */
    private String mjxm;

    /**
     * 民警警号
     */
    private String mjjh;

    /**
     * 手环高频编号
     */
    private String wdbhH;

    /**
     * 手环低频编号
     */
    private String wdbhL;

    /**
     * 进入时间
     */
    private Date jrsj;
    /**
     * 出区时间
     */
    private Date cqsj;
    /**
     * 人员编号
     */
    private String rybh;

    /**
     * 证件类型
     */
    private String zjlx;
    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 访问时限
     */
    private Short fwsx;

    /**
     * 访问原因
     */
    private String fwyy;

    /**
     * 照片id
     */
    private String zpid;

    /**
     * 状态
     */
    private String zt;
    /**
     * 性别
     */
    private String xb;
    /**
     * 胸卡类型
     */
    private String xklx;
    /**
     * 海康人脸定位绑定code
     */
    private String registerCode;

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

    public String getMjsfzh() {
        return mjsfzh;
    }

    public void setMjsfzh(String mjsfzh) {
        this.mjsfzh = mjsfzh;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getWdbhH() {
        return wdbhH;
    }

    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public Date getJrsj() {
        return jrsj;
    }

    public void setJrsj(Date jrsj) {
        this.jrsj = jrsj;
    }

    public Date getCqsj() {
        return cqsj;
    }

    public void setCqsj(Date cqsj) {
        this.cqsj = cqsj;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public Short getFwsx() {
        return fwsx;
    }

    public void setFwsx(Short fwsx) {
        this.fwsx = fwsx;
    }

    public String getFwyy() {
        return fwyy;
    }

    public void setFwyy(String fwyy) {
        this.fwyy = fwyy;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXklx() {
        return xklx;
    }

    public void setXklx(String xklx) {
        this.xklx = xklx;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
}