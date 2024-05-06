package com.wckj.chasstage.modules.wpxg.entity;

import java.util.Date;

/**
 * 随身物品小柜（盒子，分配给人员的）
 */
public class ChasSswpxg {
    /**
     *主键
     */
    private String id;

    /**
     *逻辑删除
     */
    private Integer isdel;

    /**
     *版本
     */
    private String dataflag;

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
     *办案区
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *随身物品柜组id ChasSswpg.id
     */
    private String sswpgid;

    /**
     *箱子编号
     */
    private String bh;

    /**
     *名称
     */
    private String mc;

    /**
     *关联的人员编号
     */
    private String rybh;

    /**
     *设备编号（dc数据库主键）
     */
    private String sbId;
    /**
     *设备功能
     */
    private String sbgn;
    /**
     *柜子类型 大中小
     */
    private String boxlx;
    private String xm;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
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

    public String getSswpgid() {
        return sswpgid;
    }

    public void setSswpgid(String sswpgid) {
        this.sswpgid = sswpgid;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getSbId() {
        return sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    public String getSbgn() {
        return sbgn;
    }

    public void setSbgn(String sbgn) {
        this.sbgn = sbgn;
    }

    public String getBoxlx() {
        return boxlx;
    }

    public void setBoxlx(String boxlx) {
        this.boxlx = boxlx;
    }
}