package com.wckj.chasstage.modules.clcrjl.entity;

import java.util.Date;

/**
 * 车辆出入记录
 */
public class ChasYwClcrjl {
    /**
     *id
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
     *办案区id
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;
    /**
     * 车辆id
     */
    private String clid;
    /**
     *单位系统编号
     */
    private String dwxtbh;
    /**
     *单位名称
     */
    private String dwmc;
    /**
     * 品牌
     */
    private String clBrand;

    /**
     * 型号
     */
    private String clModel;

    /**
     * 车牌号
     */
    private String clNumber;

    /**
     * 责任人身份证号
     */
    private String zrrSfzh;

    /**
     * 责任人姓名
     */
    private String zrrXm;

    /**
     * 出区时间
     */
    private Date cqsj;

    /**
     * 入区时间
     */
    private Date rqsj;
    /**
     * 车辆名称
     */
    private String name;

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

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid == null ? null : clid.trim();
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


    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getDwmc() {
        return dwmc;
    }


    public void setDwmc(String dwmc) {
        this.dwmc = dwmc == null ? null : dwmc.trim();
    }

    public String getClBrand() {
        return clBrand;
    }


    public void setClBrand(String clBrand) {
        this.clBrand = clBrand == null ? null : clBrand.trim();
    }


    public String getClModel() {
        return clModel;
    }


    public void setClModel(String clModel) {
        this.clModel = clModel == null ? null : clModel.trim();
    }


    public String getClNumber() {
        return clNumber;
    }


    public void setClNumber(String clNumber) {
        this.clNumber = clNumber == null ? null : clNumber.trim();
    }


    public String getZrrSfzh() {
        return zrrSfzh;
    }


    public void setZrrSfzh(String zrrSfzh) {
        this.zrrSfzh = zrrSfzh == null ? null : zrrSfzh.trim();
    }


    public String getZrrXm() {
        return zrrXm;
    }


    public void setZrrXm(String zrrXm) {
        this.zrrXm = zrrXm == null ? null : zrrXm.trim();
    }


    public Date getCqsj() {
        return cqsj;
    }


    public void setCqsj(Date cqsj) {
        this.cqsj = cqsj;
    }


    public Date getRqsj() {
        return rqsj;
    }


    public void setRqsj(Date rqsj) {
        this.rqsj = rqsj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}