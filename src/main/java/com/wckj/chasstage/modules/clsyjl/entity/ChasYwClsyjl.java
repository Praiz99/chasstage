package com.wckj.chasstage.modules.clsyjl.entity;

import java.util.Date;

/**
 * 车辆使用记录
 */
public class ChasYwClsyjl {
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
     *车辆id chas_xt_cldj id
     */
    private String clid;

    /**
     *车牌号
     */
    private String clNumber;


    /**
     *人员编号
     */
    private String rybh;

    /**
     *人员姓名
     */
    private String ryxm;

    /**
     *手环低频编号
     */
    private String wdbhL;

    /**
     *手环高频编号
     */
    private String wdbhH;

    /**
     *使用批次编号
     */
    private String sypcbh;

    /**
     *是否使用结束
     */
    private String isend;
    /**
     *基站编号
     */
    private String jzbh;
    /**
     *开始时间
     */
    private Date kssj;
    /**
     *结束时间
     */
    private Date jssj;
    /**
     * 绑定状态 1 已绑定 2已解绑 3取消绑定
     */
    private String bindstat;
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

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getWdbhH() {
        return wdbhH;
    }

    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH;
    }

    public String getSypcbh() {
        return sypcbh;
    }

    public void setSypcbh(String sypcbh) {
        this.sypcbh = sypcbh;
    }

    public String getIsend() {
        return isend;
    }

    public void setIsend(String isend) {
        this.isend = isend;
    }

    public String getJzbh() {
        return jzbh;
    }

    public void setJzbh(String jzbh) {
        this.jzbh = jzbh;
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

    public String getBindstat() {
        return bindstat;
    }

    public void setBindstat(String bindstat) {
        this.bindstat = bindstat;
    }
}