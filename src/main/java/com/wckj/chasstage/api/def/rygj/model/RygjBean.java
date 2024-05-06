package com.wckj.chasstage.api.def.rygj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RygjBean {

    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "办案区")
    private String baqid;
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;
    /**
     * 人员信息表id,民警入区id,访客登记id,对讲机id
     */
    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "腕带高频编号")
    private String wdbh;
    @ApiModelProperty(value = "区域ysid")
    private String qyid;
    @ApiModelProperty(value = "人员姓名")
    private String xm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "开始时间")
    private Date kssj;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date jssj;
    @ApiModelProperty(value = "负责人姓名")
    private String fzrxm;
    @ApiModelProperty(value = "区域名称")
    private String qymc;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "人员类型",notes="嫌疑人（xyr）民警（mj）访客（fk）对讲机（djj）")
    private String rylx;
    @ApiModelProperty(value = "活动内容")
    private String hdnr;

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

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getWdbh() {
        return wdbh;
    }

    public void setWdbh(String wdbh) {
        this.wdbh = wdbh;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getFzrxm() {
        return fzrxm;
    }

    public void setFzrxm(String fzrxm) {
        this.fzrxm = fzrxm;
    }



    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getHdnr() {
        return hdnr;
    }

    public void setHdnr(String hdnr) {
        this.hdnr = hdnr;
    }
}
