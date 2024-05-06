package com.wckj.chasstage.api.def.xgpz.model;


import io.swagger.annotations.ApiModelProperty;

public class XgpzBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty(value = "巡更开始时间（小时）",hidden = true)
    private Integer jckssj;
    @ApiModelProperty(value = "巡更开始时间（分钟）",hidden = true)
    private Integer jckssjfz;
    @ApiModelProperty(value = "巡更结束时间（小时）",hidden = true)
    private Integer jcjssj;
    @ApiModelProperty(value = "巡更结束时间（分钟）",hidden = true)
    private Integer jcjssjfz;
    @ApiModelProperty(value = "巡更间隔时间（分钟）")
    private Integer jcjg;
    @ApiModelProperty(value = "巡更开始时间（小时:分钟）")
    private String jckssj1;
    @ApiModelProperty(value = "巡更结束时间（小时:分钟）")
    private String jcjssj1;
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

    public Integer getJckssj() {
        return jckssj;
    }

    public void setJckssj(Integer jckssj) {
        this.jckssj = jckssj;
    }

    public Integer getJckssjfz() {
        return jckssjfz;
    }

    public void setJckssjfz(Integer jckssjfz) {
        this.jckssjfz = jckssjfz;
    }

    public Integer getJcjssj() {
        return jcjssj;
    }

    public void setJcjssj(Integer jcjssj) {
        this.jcjssj = jcjssj;
    }

    public Integer getJcjssjfz() {
        return jcjssjfz;
    }

    public void setJcjssjfz(Integer jcjssjfz) {
        this.jcjssjfz = jcjssjfz;
    }

    public Integer getJcjg() {
        return jcjg;
    }

    public void setJcjg(Integer jcjg) {
        this.jcjg = jcjg;
    }

    public String getJckssj1() {
        return jckssj1;
    }

    public void setJckssj1(String jckssj1) {
        this.jckssj1 = jckssj1;
    }

    public String getJcjssj1() {
        return jcjssj1;
    }

    public void setJcjssj1(String jcjssj1) {
        this.jcjssj1 = jcjssj1;
    }
}
