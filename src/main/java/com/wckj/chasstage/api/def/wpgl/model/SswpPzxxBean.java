package com.wckj.chasstage.api.def.wpgl.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 随身物品照片信息
 * @Package 随身物品照片信息
 * @Description: 随身物品照片信息
 * @date 2020-9-2617:11
 */
public class SswpPzxxBean {
    @ApiModelProperty(value = "照片Id")
    private String id;
    @ApiModelProperty(value = "是否删除")
    private Integer isdel;
    @ApiModelProperty(value = "数据标识")
    private String dataFlag;
    @ApiModelProperty(value = "录入人身份证")
    private String lrrSfzh;
    @ApiModelProperty(value = "录入时间")
    private Date lrsj;
    @ApiModelProperty(value = "修改人身份证")
    private String xgrSfzh;
    @ApiModelProperty(value = "修改时间")
    private Date xgsj;
    @ApiModelProperty(value = "办案区id")
    private String baqid;
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "物品id")
    private String wpid;
    @ApiModelProperty(value = "文件id")
    private String bizId;
    @ApiModelProperty(value = "照片类型")
    private String zplx;

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

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWpid() {
        return wpid;
    }

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getZplx() {
        return zplx;
    }

    public void setZplx(String zplx) {
        this.zplx = zplx;
    }
}
