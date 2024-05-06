package com.wckj.chasstage.api.def.baq.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

public class WszhBean {
    @ApiParam(value = "id")
    @ApiModelProperty(value = "id")
    private String id;
    @ApiParam(value = "是否删除",hidden = true)
    @ApiModelProperty(value = "是否删除")
    private Integer isdel;
    @ApiParam(value = "数据标识",hidden = true)
    @ApiModelProperty(value = "数据标识")
    private String dataflag;
    @ApiParam(value = "录入身份证",hidden = true)
    @ApiModelProperty(value = "录入身份证")
    private String lrrSfzh;
    @ApiParam(value = "录入时间",hidden = true)
    @ApiModelProperty(value = "录入时间")
    private Date lrsj;
    @ApiParam(value = "修改人身份证",hidden = true)
    @ApiModelProperty(value = "修改人身份证")
    private String xgrSfzh;
    @ApiParam(value = "修改时间",hidden = true)
    @ApiModelProperty(value = "修改时间")
    private Date xgsj;
    @ApiParam(value = "办案区id",required = true)
    @ApiModelProperty(value = "办案区id")
    private String baqid;
    @ApiParam(value = "办案区名称",hidden = true)
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;
    /**
     * 字号头
     */
    @ApiParam(value = "字号头",required = true)
    @ApiModelProperty(value = "字号头")
    private String zht;
    /**
     * 字号
     */
    @ApiParam(value = "字号",required = true)
    @ApiModelProperty(value = "字号")
    private String zh;
    /**
     * 机构简称
     */
    @ApiParam(value = "机构简称",required = true)
    @ApiModelProperty(value = "机构简称")
    private String jgjc;
    /**
     * 区域字
     */
    @ApiParam(value = "区域字",required = true)
    @ApiModelProperty(value = "区域字")
    private String qyz;
    public WszhBean(){
        this.isdel=0;
        this.dataflag="0";
    }


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


    public String getDataflag() {
        return dataflag;
    }


    public void setDataflag(String dataflag) {
        this.dataflag = dataflag == null ? null : dataflag.trim();
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


    public String getZht() {
        return zht;
    }

    public void setZht(String zht) {
        this.zht = zht == null ? null : zht.trim();
    }


    public String getZh() {
        return zh;
    }


    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
    }

    public String getJgjc() {
        return jgjc;
    }

    public void setJgjc(String jgjc) {
        this.jgjc = jgjc;
    }

    public String getQyz() {
        return qyz;
    }

    public void setQyz(String qyz) {
        this.qyz = qyz;
    }
}
