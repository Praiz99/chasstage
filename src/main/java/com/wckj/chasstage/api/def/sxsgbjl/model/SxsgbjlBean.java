package com.wckj.chasstage.api.def.sxsgbjl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 审讯室关闭记录
 */
public class SxsgbjlBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lrsj;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("区域id")
    private String qyid;
    @ApiModelProperty("审讯室名称")
    private String  qymc;
    @ApiModelProperty("操作人姓名")
    private String xm;
    @ApiModelProperty("单位系统编号")
    private String dwxtbh;
    @ApiModelProperty("审讯室分配记录id")
    private String syid;
    @ApiModelProperty("操作账号")
    private String username;
    @ApiModelProperty("嫌疑人姓名")
    private String xyrxm;

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }

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

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getSyid() {
        return syid;
    }

    public void setSyid(String syid) {
        this.syid = syid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXyrxm() {
        return xyrxm;
    }

    public void setXyrxm(String xyrxm) {
        this.xyrxm = xyrxm;
    }

    public Date getLrsj() {
        return lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }
}