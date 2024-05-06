package com.wckj.chasstage.api.def.sy.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 首页实体对象
 * @Package
 * @Description:
 * @date 2020-11-213:43
 */
public class SyBean {
    @ApiModelProperty("当日出所人员")
    private String cs;
    @ApiModelProperty("临时出区人员")
    private String lscs;
    @ApiModelProperty("办案区审批")
    private String dsp;
    @ApiModelProperty("当日入所人数")
    private String rs;
    @ApiModelProperty("预警信息")
    private String yj;
    @ApiModelProperty("情亲驿站")
    private String qqyz;

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getLscs() {
        return lscs;
    }

    public void setLscs(String lscs) {
        this.lscs = lscs;
    }

    public String getDsp() {
        return dsp;
    }

    public void setDsp(String dsp) {
        this.dsp = dsp;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getYj() {
        return yj;
    }

    public void setYj(String yj) {
        this.yj = yj;
    }

    public String getQqyz() {
        return qqyz;
    }

    public void setQqyz(String qqyz) {
        this.qqyz = qqyz;
    }
}
