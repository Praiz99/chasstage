package com.wckj.chasstage.api.def.yjxx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class YjxxBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("预警类别")
    private String yjlb;
    @ApiModelProperty("预警描述")
    private String jqms;
    @ApiModelProperty("预警状态 0未处理 1已处理 2忽略")
    private String yjzt;
    @ApiModelProperty("预警级别")
    private String yjjb;
    @ApiModelProperty("触发时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cfsj;
    @ApiModelProperty("处理人姓名")
    private String clrxm;
    @ApiModelProperty("处理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clsj;
    @ApiModelProperty("触发人姓名")
    private String cfrxm;
    @ApiModelProperty("触发人id")
    private String cfrid;
    @ApiModelProperty("触发区域名称")
    private String cfqymc;
    @ApiModelProperty("触发设备id")
    private String cfsbid;
    @ApiModelProperty("触发区域id")
    private String cfqyid;

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

    public String getJqms() {
        return jqms;
    }

    public void setJqms(String jqms) {
        this.jqms = jqms;
    }

    public String getYjzt() {
        return yjzt;
    }

    public void setYjzt(String yjzt) {
        this.yjzt = yjzt;
    }

    public String getYjjb() {
        return yjjb;
    }

    public void setYjjb(String yjjb) {
        this.yjjb = yjjb;
    }

    public Date getCfsj() {
        return cfsj;
    }

    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }

    public String getClrxm() {
        return clrxm;
    }

    public void setClrxm(String clrxm) {
        this.clrxm = clrxm;
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    public String getCfrxm() {
        return cfrxm;
    }

    public void setCfrxm(String cfrxm) {
        this.cfrxm = cfrxm;
    }

    public String getCfrid() {
        return cfrid;
    }

    public void setCfrid(String cfrid) {
        this.cfrid = cfrid;
    }

    public String getCfqymc() {
        return cfqymc;
    }

    public void setCfqymc(String cfqymc) {
        this.cfqymc = cfqymc;
    }

    public String getCfsbid() {
        return cfsbid;
    }

    public void setCfsbid(String cfsbid) {
        this.cfsbid = cfsbid;
    }

    public String getCfqyid() {
        return cfqyid;
    }

    public void setCfqyid(String cfqyid) {
        this.cfqyid = cfqyid;
    }
}
