package com.wckj.chasstage.modules.qygl.entity;

import java.util.Date;

/**
 * 区域信息
 */
public class ChasXtQy {

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String qymc;
    /**
     *逻辑删除
     */
    private Integer isdel;

    /**
     * 版本
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
     *人员数量
     */
    private Integer rysl;

    /**
     *人员滞留时间
     */
    private Integer ryzlsj;

    /**
     *排序编号
     */
    private Integer pxbh;

    /**
     *是否功能室
     */
    private Integer sfgns;

    /**
     *房间类型
     */
    private String fjlx;

    /**
     *原始编号
     */
    private String ysbh;

    /**
     *扩展类型
     */
    private String kzlx;
    /**
     * 原始id （dc数据库主键）
     */
    private String ysid;
    /**
     * mac地址
     */
    private String mac;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
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

    public Integer getRysl() {
        return rysl;
    }

    public void setRysl(Integer rysl) {
        this.rysl = rysl;
    }

    public Integer getRyzlsj() {
        return ryzlsj;
    }

    public void setRyzlsj(Integer ryzlsj) {
        this.ryzlsj = ryzlsj;
    }

    public Integer getPxbh() {
        return pxbh;
    }

    public void setPxbh(Integer pxbh) {
        this.pxbh = pxbh;
    }

    public Integer getSfgns() {
        return sfgns;
    }

    public void setSfgns(Integer sfgns) {
        this.sfgns = sfgns;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }

    public String getKzlx() {
        return kzlx;
    }

    public void setKzlx(String kzlx) {
        this.kzlx = kzlx;
    }

    public String getYsid() {
        return ysid;
    }

    public void setYsid(String ysid) {
        this.ysid = ysid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}