package com.wckj.chasstage.modules.fkgl.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 访客登记信息
 */
public class ChasYwFkdj {
    /**
     *id
     */
    private String id;

    /**
     *逻辑删除
     */
    private Integer isdel;

    /**
     *数据版本
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
     *访客姓名
     */
    private String fkxm;

    /**
     *性别
     */
    private String xb;

    /**
     *证件类型
     */
    private String zjlx;

    /**
     *访客身份证号
     */
    private String fksfzh;

    /**
     *出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date csrq;

    /**
     *户籍所在地
     */
    private String hjdxz;

    /**
     *访客类别
     */
    private String fklb;

    /**
     *人员编号
     */
    private String rybh;

    /**
     *联系电话
     */
    private String lxdh;

    /**
     *进入时间
     */
    private Date jrsj;

    /**
     *出区时间
     */
    private Date cqsj;

    /**
     *访问时限
     */
    private Integer fwsx;

    /**
     *访问原因
     */
    private String fwyy;

    /**
     *照片id
     */
    private String zpid;

    /**
     *办案区id
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *状态 01 预约 02入区 03出区
     */
    private String zt;


    /**
     *胸卡低频编号
     */
    private String wdbhL;

    /**
     *胸卡高频编号
     */
    private String wdbhH;
    /**
     *胸卡类型
     */
    private String xklx;

    /**
     * 访问人数
     */
    private Integer fwrs;

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

    public String getFkxm() {
        return fkxm;
    }

    public void setFkxm(String fkxm) {
        this.fkxm = fkxm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getFksfzh() {
        return fksfzh;
    }

    public void setFksfzh(String fksfzh) {
        this.fksfzh = fksfzh;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getHjdxz() {
        return hjdxz;
    }

    public void setHjdxz(String hjdxz) {
        this.hjdxz = hjdxz;
    }

    public String getFklb() {
        return fklb;
    }

    public void setFklb(String fklb) {
        this.fklb = fklb;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
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

    public Integer getFwsx() {
        return fwsx;
    }

    public void setFwsx(Integer fwsx) {
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

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
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

    public String getXklx() {
        return xklx;
    }

    public void setXklx(String xklx) {
        this.xklx = xklx;
    }

    public Integer getFwrs() {
        return fwrs;
    }

    public void setFwrs(Integer fwrs) {
        this.fwrs = fwrs;
    }

    private String registerCode;

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
}