package com.wckj.chasstage.api.def.jhrw.model;

import io.swagger.annotations.ApiModelProperty;


public class JhryBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("人员姓名")
    private String ryxm;
    @ApiModelProperty("戒护任务编号")
    private String jhrwbh;
    @ApiModelProperty("人员身份证号")
    private String rybh;
    @ApiModelProperty("民警警号")
    private String mjjh;
    @ApiModelProperty("单位代码")
    private String dwdm;
    @ApiModelProperty("单位名称")
    private String dwmc;

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

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getJhrwbh() {
        return jhrwbh;
    }

    public void setJhrwbh(String jhrwbh) {
        this.jhrwbh = jhrwbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
}
