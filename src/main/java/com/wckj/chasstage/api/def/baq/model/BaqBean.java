package com.wckj.chasstage.api.def.baq.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

public class BaqBean {

    @ApiParam(value = "id")
    @ApiModelProperty(value = "id")
    private String id;

    @ApiParam(value = "是否逻辑删除",hidden = true)
    @ApiModelProperty(value = "是否逻辑删除")
    private Integer isdel;

    @ApiParam(value = "数据版本号",hidden = true)
    @ApiModelProperty(value = "数据版本号")
    private String dataflag;

    @ApiParam(value = "录入人身份证号",hidden = true)
    @ApiModelProperty(value = "录入人身份证号")
    private String lrrSfzh;

    @ApiParam(value = "录入时间",hidden = true)
    @ApiModelProperty(value = "录入时间")
    private Date lrsj;

    @ApiParam(value = "修改人身份证号",hidden = true)
    @ApiModelProperty(value = "修改人身份证号")
    private String xgrSfzh;

    @ApiParam(value = "修改时间",hidden = true)
    @ApiModelProperty(value = "修改时间")
    private Date xgsj;

    @ApiParam(value = "是否默认办案区")
    @ApiModelProperty(value = "是否默认办案区")
    private Integer isDefault;

    @ApiParam(value = "办案区名称")
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;

    @ApiParam(value = "单位代码",hidden = true)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiParam(value = "单位名称",hidden = true)
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiParam(value = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiParam(value = "DeviceCenter IP")
    @ApiModelProperty(value = "DeviceCenter IP")
    private String ip;

    @ApiParam(value = "DeviceCenter 端口")
    @ApiModelProperty(value = "DeviceCenter 端口")
    private String port;

    @ApiParam(value = "DeviceCenter 项目名称")
    @ApiModelProperty(value = "DeviceCenter 项目名称")
    private String xtmc;

    @ApiParam(value = "是否定位")
    @ApiModelProperty(value = "是否定位")
    private Integer sfdw;

    @ApiParam(value = "是否智能办案区")
    @ApiModelProperty(value = "是否智能办案区")
    private Integer sfznbaq;

    @ApiParam(value = "单位系统编号")
    @ApiModelProperty(value = "单位系统编号")
    private String dwxtbh;

    @ApiParam(value = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiParam(value = "是否虚拟办案区 1 是 0 否")
    @ApiModelProperty(value = "是否虚拟办案区 1 是 0 否")
    private String virtual;

    @ApiParam(value = "是否大中心办案区")
    @ApiModelProperty(value = "是否大中心办案区")
    private Integer sfdzx;

    public BaqBean() {
        this.isdel = 0;
        this.dataflag = "0";
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh == null ? null : dwxtbh.trim();
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


    public Integer getIsDefault() {
        return isDefault;
    }


    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }


    public Date getXgsj() {
        return xgsj;
    }


    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
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


    public String getBz() {
        return bz;
    }


    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }


    public String getXtmc() {
        return xtmc;
    }


    public void setXtmc(String xtmc) {
        this.xtmc = xtmc == null ? null : xtmc.trim();
    }


    public Integer getSfdw() {
        return sfdw;
    }


    public void setSfdw(Integer sfdw) {
        this.sfdw = sfdw;
    }


    public Integer getSfznbaq() {
        return sfznbaq;
    }


    public void setSfznbaq(Integer sfznbaq) {
        this.sfznbaq = sfznbaq;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

    public Integer getSfdzx() {
        return sfdzx;
    }

    public void setSfdzx(Integer sfdzx) {
        this.sfdzx = sfdzx;
    }
}
