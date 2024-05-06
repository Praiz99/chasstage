package com.wckj.chasstage.modules.ryxl.entity;

import java.util.Date;

/**
 * 人员心率信息
 */
public class ChasYwRyxl {

    private String id;

    private Integer isdel;


    private String dataFlag;

    private String lrrSfzh;

    private Date lrsj;

    private String xgrSfzh;

    private Date xgsj;

    private String baqid;

    private String baqmc;

    private String wdbhH;

    private String wdbhL;

    private String rybh;

    private String ryxm;

    private Date bcsj;

    private Integer ryxl;

    private String qyid;

    private String qymc;

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


    public String getRybh() {
        return rybh;
    }


    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }

    public String getRyxm() {
        return ryxm;
    }


    public void setRyxm(String ryxm) {
        this.ryxm = ryxm == null ? null : ryxm.trim();
    }

    public Date getBcsj() {
        return bcsj;
    }


    public void setBcsj(Date bcsj) {
        this.bcsj = bcsj;
    }

    public Integer getRyxl() {
        return ryxl;
    }


    public void setRyxl(Integer ryxl) {
        this.ryxl = ryxl;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }
}