package com.wckj.chasstage.modules.znqt.entity;

import java.util.Date;

/**
 * 智能墙体
 */
public class ChasZnqt {
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
     *位置代码
     */
    private String wzdm;

    /**
     *位置名称
     */
    private String wzmc;

    /**
     *ip
     */
    private String ip;

    /**
     *端口
     */
    private Integer port;

    /**
     *最大连接数
     */
    private Integer zdlj;

    /**
     *墙面1连接数
     */
    private Integer lj1;

    /**
     *墙面2连接数
     */
    private Integer lj2;

    /**
     *墙面3连接数
     */
    private Integer lj3;

    /**
     *墙面4连接数
     */
    private Integer lj4;

    /**
     *状态
     */
    private String zt;

    /**
     *备注
     */
    private String bz;
    
    public ChasZnqt(){
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

    public String getWzdm() {
        return wzdm;
    }

    public void setWzdm(String wzdm) {
        this.wzdm = wzdm;
    }

    public String getWzmc() {
        return wzmc;
    }

    public void setWzmc(String wzmc) {
        this.wzmc = wzmc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getZdlj() {
        return zdlj;
    }

    public void setZdlj(Integer zdlj) {
        this.zdlj = zdlj;
    }

    public Integer getLj1() {
        return lj1;
    }

    public void setLj1(Integer lj1) {
        this.lj1 = lj1;
    }

    public Integer getLj2() {
        return lj2;
    }

    public void setLj2(Integer lj2) {
        this.lj2 = lj2;
    }

    public Integer getLj3() {
        return lj3;
    }

    public void setLj3(Integer lj3) {
        this.lj3 = lj3;
    }

    public Integer getLj4() {
        return lj4;
    }

    public void setLj4(Integer lj4) {
        this.lj4 = lj4;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}