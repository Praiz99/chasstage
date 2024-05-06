package com.wckj.chasstage.api.def.yjlb.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;


public class YjlbBean {

    @ApiParam(value = "id")
    @ApiModelProperty("id")
    private String id;
    @ApiParam(value = "办案区id",required = true)
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiParam(value = "办案区名称",required = true)
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiParam(value = "预警类别",required = true)
    @ApiModelProperty("预警类别")
    private String yjlb;
    @ApiParam(value = "预警时长",required = true)
    @ApiModelProperty("预警时长")
    private Integer yjsc;
    @ApiParam(value = "触发方式",required = true)
    @ApiModelProperty("触发方式")
    private String cffs;
    @ApiParam(value = "预警级别",required = true)
    @ApiModelProperty("预警级别")
    private String yjjb;
    @ApiParam(value = "预警方式",required = true)
    @ApiModelProperty("预警方式")
    private String yjfs;
    @ApiParam(value = "刑事案件预警时间（时）")
    @ApiModelProperty("刑事案件预警时间（时）")
    private String xsajyjsj;
    @ApiParam(value = "行政案件预警时间（时）")
    @ApiModelProperty("行政案件预警时间（时）")
    private String xzajyjsj;
    @ApiParam(value = "刑事提前预警时间（时）")
    @ApiModelProperty("刑事提前预警时间（时）")
    private String xstqyjsj;
    @ApiParam(value = "行政提前预警时间（时）")
    @ApiModelProperty("行政提前预警时间（时）")
    private String xztqyjsj;

    public String getYjfs() {
        return yjfs;
    }

    public void setYjfs(String yjfs) {
        this.yjfs = yjfs;
    }

    public String getXsajyjsj() {
        return xsajyjsj;
    }

    public void setXsajyjsj(String xsajyjsj) {
        this.xsajyjsj = xsajyjsj;
    }

    public String getXzajyjsj() {
        return xzajyjsj;
    }

    public void setXzajyjsj(String xzajyjsj) {
        this.xzajyjsj = xzajyjsj;
    }

    public String getXstqyjsj() {
        return xstqyjsj;
    }

    public void setXstqyjsj(String xstqyjsj) {
        this.xstqyjsj = xstqyjsj;
    }

    public String getXztqyjsj() {
        return xztqyjsj;
    }

    public void setXztqyjsj(String xztqyjsj) {
        this.xztqyjsj = xztqyjsj;
    }

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

    public String getYjlb() {
        return yjlb;
    }

    public void setYjlb(String yjlb) {
        this.yjlb = yjlb;
    }

    public Integer getYjsc() {
        return yjsc;
    }

    public void setYjsc(Integer yjsc) {
        this.yjsc = yjsc;
    }

    public String getCffs() {
        return cffs;
    }

    public void setCffs(String cffs) {
        this.cffs = cffs;
    }

    public String getYjjb() {
        return yjjb;
    }

    public void setYjjb(String yjjb) {
        this.yjjb = yjjb;
    }
}
