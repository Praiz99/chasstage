package com.wckj.chasstage.modules.baq.entity;

/**
 * 办案区关联
 */
public class ChasBaqref {
    /**
     *主键
     */
    private String id;

    /**
     *办案区
     */
    private String baqid;
    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *单位代码
     */
    private String dwdm;
    /**
     *系统单位代码
     */
    private String dwxtbh;

    /**
     *使用单位代码
     */
    private String sydwdm;

    /**
     *使用系统单位代码
     */
    private String sydwxtbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getSydwdm() {
        return sydwdm;
    }

    public void setSydwdm(String sydwdm) {
        this.sydwdm = sydwdm;
    }

    public String getSydwxtbh() {
        return sydwxtbh;
    }

    public void setSydwxtbh(String sydwxtbh) {
        this.sydwxtbh = sydwxtbh;
    }
}