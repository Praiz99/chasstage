package com.wckj.chasstage.modules.sxsgbjl.entity;

import java.util.Date;

/**
 * 审讯室关闭记录
 */
public class ChasSxsGbjl {
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
     *办案区id
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;
    /**
     *区域id
     */
    private String qyid;
    /**
     * 审讯室
     */
    private String  qymc;
    /**
     * 操作人姓名
     */
    private String xm;
    /**
     * 操作人姓名
     */
    private String dwxtbh;
    /**
     * 审讯室分配记录id
     */
    private String syid;
    /**
     * 操作人账号
     */
    private String username;
    /**
     * 嫌疑人姓名
     */
    private String xyrxm;

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
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

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getSyid() {
        return syid;
    }

    public void setSyid(String syid) {
        this.syid = syid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXyrxm() {
        return xyrxm;
    }

    public void setXyrxm(String xyrxm) {
        this.xyrxm = xyrxm;
    }
}