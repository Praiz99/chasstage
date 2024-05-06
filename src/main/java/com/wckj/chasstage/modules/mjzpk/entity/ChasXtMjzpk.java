package com.wckj.chasstage.modules.mjzpk.entity;

import java.util.Date;

/**
 * 民警照片库
 */
public class ChasXtMjzpk {
    /**
     * id
     */
    private String id;
    /**
     * 逻辑删除
     */
    private Short isdel;
    /**
     * 数据版本
     */
    private String dataFlag;
    /**
     * 录入人身份证号
     */
    private String lrrSfzh;
    /**
     * 录入时间
     */
    private Date lrsj;
    /**
     * 修改人身份证号
     */
    private String xgrSfzh;
    /**
     * 修改时间
     */
    private Date xgsj;
    /**
     * 办案区id
     */
    private String baqid;
    /**
     * 办案区名称
     */
    private String baqmc;
    /**
     * 单位代码
     */
    private String dwdm;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 单位系统编号
     */
    private String dwxtbh;
    /**
     * 民警姓名
     */
    private String mjxm;
    /**
     * 民警身份证号
     */
    private String mjsfzh;
    /**
     * 民警警号
     */
    private String mjjh;
    /**
     * 照片id
     */
    private String zpid;
    /**
     * 审批人警号
     */
    private String sprjh;
    /**
     * 审批人姓名
     */
    private String sprxm;
    /**
     * 审批人单位代码
     */
    private String sprdwdm;
    /**
     * 审批意见
     */
    private String spyj;
    /**
     * 审批状态
     */
    private String spzt;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 工作单位
     */
    private String gzdw;
    /**
     * 备注
     */
    private String bz;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 人员类型
     */
    private String rylx;

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
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

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm == null ? null : dwdm.trim();
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc == null ? null : dwmc.trim();
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh == null ? null : dwxtbh.trim();
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm == null ? null : mjxm.trim();
    }

    public String getMjsfzh() {
        return mjsfzh;
    }

    public void setMjsfzh(String mjsfzh) {
        this.mjsfzh = mjsfzh == null ? null : mjsfzh.trim();
    }


    public String getMjjh() {
        return mjjh;
    }


    public void setMjjh(String mjjh) {
        this.mjjh = mjjh == null ? null : mjjh.trim();
    }


    public String getZpid() {
        return zpid;
    }


    public void setZpid(String zpid) {
        this.zpid = zpid == null ? null : zpid.trim();
    }


    public String getSprjh() {
        return sprjh;
    }


    public void setSprjh(String sprjh) {
        this.sprjh = sprjh == null ? null : sprjh.trim();
    }


    public String getSprxm() {
        return sprxm;
    }


    public void setSprxm(String sprxm) {
        this.sprxm = sprxm == null ? null : sprxm.trim();
    }


    public String getSprdwdm() {
        return sprdwdm;
    }


    public void setSprdwdm(String sprdwdm) {
        this.sprdwdm = sprdwdm == null ? null : sprdwdm.trim();
    }


    public String getSpyj() {
        return spyj;
    }


    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }


    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt == null ? null : spzt.trim();
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}