package com.wckj.chasstage.modules.xgpz.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 巡更配置
 */
public class ChasXgpz {
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
     *巡更开始时间（小时）
     */
    private Integer jckssj;
    /**
     *巡更开始时间（分钟）
     */
    private Integer jckssjfz;
    /**
     *巡更结束时间（小时）
     */
    private Integer jcjssj;
    /**
     *巡更结束时间（分钟）
     */
    private Integer jcjssjfz;
    /**
     *巡更间隔时间（分钟）
     */
    private Integer jcjg;
    private String jckssj1;
    private String jcjssj1;
    public ChasXgpz(){
        this.isdel=0;
        this.dataflag="0";
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

    public Integer getJckssj() {
        return jckssj;
    }

    public void setJckssj(Integer jckssj) {
        this.jckssj = jckssj;
    }

    public Integer getJckssjfz() {
        return jckssjfz;
    }

    public void setJckssjfz(Integer jckssjfz) {
        this.jckssjfz = jckssjfz;
    }

    public Integer getJcjssj() {
        return jcjssj;
    }

    public void setJcjssj(Integer jcjssj) {
        this.jcjssj = jcjssj;
    }

    public Integer getJcjssjfz() {
        return jcjssjfz;
    }

    public void setJcjssjfz(Integer jcjssjfz) {
        this.jcjssjfz = jcjssjfz;
    }

    public Integer getJcjg() {
        return jcjg;
    }

    public void setJcjg(Integer jcjg) {
        this.jcjg = jcjg;
    }

    public String getJckssj1() {
        return jckssj1;
    }

    public void setJckssj1(String jckssj1) {
        this.jckssj1 = jckssj1;
    }

    public String getJcjssj1() {
        return jcjssj1;
    }

    public void setJcjssj1(String jcjssj1) {
        this.jcjssj1 = jcjssj1;
    }
}