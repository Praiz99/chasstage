package com.wckj.chasstage.api.def.cldj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 车辆登记（管理）
 */
public class CldjBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("单位系统编号")
    private String dwxtbh;
    @ApiModelProperty("单位名称")
    private String dwmc;
    @ApiModelProperty("品牌")
    private String clBrand;
    @ApiModelProperty("型号")
    private String clModel;
    @ApiModelProperty("车牌号")
    private String clNumber;
    @ApiModelProperty("责任人身份证号")
    private String zrrSfzh;
    @ApiModelProperty("责任人姓名")
    private String zrrXm;
    @ApiModelProperty("车辆图片id")
    private String clPhoto;
    @ApiModelProperty("认证时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date qssj;
    @ApiModelProperty("到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date jssj;
    @ApiModelProperty("车辆使用状态 0 空闲 1 使用中")
    private String clsyzt;
    @ApiModelProperty("车辆类型 1预约 2押送")
    private String cllx;
    @ApiModelProperty("车载基站编号")
    private String czjzbh;
    @ApiModelProperty("车载摄像头编号")
    private String czsxtbh;
    @ApiModelProperty("人员数量阈值")
    private Integer zdrysl;
    @ApiModelProperty("是否选择 0未选择 1已选择")
    private String selected;
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

    public String getClsyzt() {
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