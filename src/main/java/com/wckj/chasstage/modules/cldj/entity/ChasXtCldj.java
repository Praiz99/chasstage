package com.wckj.chasstage.modules.cldj.entity;

import java.util.Date;

/**
 * 车辆登记（管理）
 */
public class ChasXtCldj {
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
     *单位系统编号
     */
    private String dwxtbh;

    /**
     *单位名称
     */
    private String dwmc;

    /**
     *品牌
     */
    private String clBrand;

    /**
     *型号
     */
    private String clModel;

    /**
     *车牌号
     */
    private String clNumber;

    /**
     *责任人身份证号
     */
    private String zrrSfzh;

    /**
     *责任人姓名
     */
    private String zrrXm;

    /**
     *车辆图片
     */
    private String clPhoto;

    /**
     *认证时间
     */
    private Date qssj;

    /**
     *到期时间
     */
    private Date jssj;

    /**
     *车辆使用状态 0 空闲 1 使用中
     */
    private String clsyzt;

    /**
     *车辆类型 1预约 2押送
     */
    private String cllx;
    /**
     *车载基站编号
     */
    private String czjzbh;
    /**
     *车载摄像头编号
     */
    private String czsxtbh;
    /**
     *最大人员数量
     */
    private Integer zdrysl;
    //是否选择,不保存在数据库中
    private String selected="0";
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
        this.dwmc = dwmc;
    }

    public String getClBrand() {
        return clBrand;
    }

    public void setClBrand(String clBrand) {
        this.clBrand = clBrand;
    }

    public String getClModel() {
        return clModel;
    }

    public void setClModel(String clModel) {
        this.clModel = clModel;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public String getZrrSfzh() {
        return zrrSfzh;
    }

    public void setZrrSfzh(String zrrSfzh) {
        this.zrrSfzh = zrrSfzh;
    }

    public String getZrrXm() {
        return zrrXm;
    }

    public void setZrrXm(String zrrXm) {
        this.zrrXm = zrrXm;
    }

    public String getClPhoto() {
        return clPhoto;
    }

    public void setClPhoto(String clPhoto) {
        this.clPhoto = clPhoto;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String  getClsyzt() {
        return clsyzt;
    }

    public void setClsyzt(String clsyzt) {
        this.clsyzt = clsyzt;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getCzjzbh() {
        return czjzbh;
    }

    public void setCzjzbh(String czjzbh) {
        this.czjzbh = czjzbh;
    }

    public String getCzsxtbh() {
        return czsxtbh;
    }

    public void setCzsxtbh(String czsxtbh) {
        this.czsxtbh = czsxtbh;
    }

    public Integer getZdrysl() {
        return zdrysl;
    }

    public void setZdrysl(Integer zdrysl) {
        this.zdrysl = zdrysl;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}