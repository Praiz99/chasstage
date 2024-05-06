package com.wckj.chasstage.api.def.clcrjl.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 车辆出入记录
 */
public class ClcrjlBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("车辆id")
    private String clid;
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
    @ApiModelProperty("出区时间")
    private Date cqsj;
    @ApiModelProperty("入区时间")
    private Date rqsj;
    @ApiModelProperty("车辆名称")
    private String name;

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

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
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