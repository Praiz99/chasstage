package com.wckj.chasstage.modules.qwjl.entity;

import java.util.Date;

public class ChasQwjl {
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
     *办案区
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *备注
     */
    private String bz;

    /**
     *操作民警身份证号
     */
    private String czmjSfzh;

    /**
     *操作民警姓名
     */
    private String czmjXm;

    /**
     *单位系统编号
     */
    private String dwxtbh;

    /**
     *操作时间
     */
    private Date czsj;

    /**
     *持有人
     */
    private String cyr;

    /**
     *人员编号
     */
    private String rybh;

    /**
     *操作类型
     */
    private String czlx;

    /**
     *物品柜id
     */
    private String cabId;

    /**
     *主办单位编号
     */
    private String zbdwBh;
    /**
     *物品柜编号
     */
    private String cabBh;
    /**
     * 物品编号
     */
    private String wpbh;
    /**
     * 物品id
     */
    private String sswpxxid;
    /**
     * 领回物品
     */
    private String lhwp;
    /**
     * 未领回物品
     */
    private String wlhwp;

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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCzmjSfzh() {
        return czmjSfzh;
    }

    public void setCzmjSfzh(String czmjSfzh) {
        this.czmjSfzh = czmjSfzh;
    }

    public String getCzmjXm() {
        return czmjXm;
    }

    public void setCzmjXm(String czmjXm) {
        this.czmjXm = czmjXm;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getCabBh() {
        return cabBh;
    }

    public void setCabBh(String cabBh) {
        this.cabBh = cabBh;
    }

    public String getWpbh() {
        return wpbh;
    }

    public void setWpbh(String wpbh) {
        this.wpbh = wpbh;
    }

    public String getSswpxxid() {
        return sswpxxid;
    }

    public void setSswpxxid(String sswpxxid) {
        this.sswpxxid = sswpxxid;
    }

    public String getLhwp() {
        return lhwp;
    }

    public void setLhwp(String lhwp) {
        this.lhwp = lhwp;
    }

    public String getWlhwp() {
        return wlhwp;
    }

    public void setWlhwp(String wlhwp) {
        this.wlhwp = wlhwp;
    }
}