package com.wckj.chasstage.api.def.face.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 民警照片库
 */
public class MjzpkBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("单位代码")
    private String dwdm;
    @ApiModelProperty("单位名称")
    private String dwmc;
    @ApiModelProperty("单位系统编号")
    private String dwxtbh;
    @ApiModelProperty("民警姓名")
    private String mjxm;
    @ApiModelProperty("民警身份证号")
    private String mjsfzh;
    @ApiModelProperty("民警警号")
    private String mjjh;
    @ApiModelProperty("照片id")
    private String zpid;
    @ApiModelProperty("审批人警号")
    private String sprjh;
    @ApiModelProperty("审批人姓名")
    private String sprxm;
    @ApiModelProperty("审批人单位")
    private String sprdwdm;
    @ApiModelProperty("审批意见")
    private String spyj;
    @ApiModelProperty("审批状态")
    private String spzt;
    @ApiModelProperty("联系人")
    private String lxr;
    @ApiModelProperty("联系电话")
    private String lxdh;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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


    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}