package com.wckj.chasstage.api.def.njxx.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 尿检信息
 * @Package 尿检信息
 * @Description:
 * @date 2020-9-1616:15
 */
public class NjxxBean {
    @ApiModelProperty(value = "尿检id")
    private String id;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "人员姓名")
    private String ryxm;
    @ApiModelProperty(value = "主办人身份证")
    private String zbrSfzh;
    @ApiModelProperty(value = "主办人姓名")
    private String zbrXm;
    @ApiModelProperty(value = "填发时间")
    private String tfsj;
    @ApiModelProperty(value = "文书号")
    private String wsh;
    @ApiModelProperty(value = "现场检测时间")
    private String xcjcsj;
    @ApiModelProperty(value = "现场检测地点")
    private String xcjcdd;
    @ApiModelProperty(value = "现场检测方法")
    private String xcjcff;
    @ApiModelProperty(value = "现场检测结果")
    private String xcjcjg;
    @ApiModelProperty(value = "检测时间")
    private String jcsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getZbrSfzh() {
        return zbrSfzh;
    }

    public void setZbrSfzh(String zbrSfzh) {
        this.zbrSfzh = zbrSfzh;
    }

    public String getZbrXm() {
        return zbrXm;
    }

    public void setZbrXm(String zbrXm) {
        this.zbrXm = zbrXm;
    }

    public String getTfsj() {
        return tfsj;
    }

    public void setTfsj(String tfsj) {
        this.tfsj = tfsj;
    }

    public String getWsh() {
        return wsh;
    }

    public void setWsh(String wsh) {
        this.wsh = wsh;
    }

    public String getXcjcsj() {
        return xcjcsj;
    }

    public void setXcjcsj(String xcjcsj) {
        this.xcjcsj = xcjcsj;
    }

    public String getXcjcdd() {
        return xcjcdd;
    }

    public void setXcjcdd(String xcjcdd) {
        this.xcjcdd = xcjcdd;
    }

    public String getXcjcff() {
        return xcjcff;
    }

    public void setXcjcff(String xcjcff) {
        this.xcjcff = xcjcff;
    }

    public String getXcjcjg() {
        return xcjcjg;
    }

    public void setXcjcjg(String xcjcjg) {
        this.xcjcjg = xcjcjg;
    }

    public String getJcsj() {
        return jcsj;
    }

    public void setJcsj(String jcsj) {
        this.jcsj = jcsj;
    }
}
