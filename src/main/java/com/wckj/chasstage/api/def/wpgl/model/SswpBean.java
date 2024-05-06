package com.wckj.chasstage.api.def.wpgl.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;

public class SswpBean {

    @ApiModelProperty(value = "主键ID 主键ID,新增为空，修改必填")
    private String id;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "物品类别")
    private String lb;


    @ApiModelProperty(value = "持有人")
    private String cyr;


    @ApiModelProperty(value = "物品单位")
    private String dw;

    @ApiModelProperty(value = "物品数量")
    private String sl;

    @ApiModelProperty(value = "物品特征")
    private String tz;


    @ApiModelProperty(value = "价值")
    private BigDecimal jz;

    @ApiModelProperty(value = "存放位置id")
    private String cfwz;

    @ApiModelProperty(value = "存放位置名称")
    private String cfwzName;

    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "文件Id")
    private String wjid;

    @ApiModelProperty(value = "类别名称")
    private String lbmc;
    @ApiModelProperty(value = "录入时间")
    private String lrsjk;

    @ApiModelProperty(value = "数据类型 01 合照(传rybh和wjid即可) 02 明细数据（正常数据字段保存）")
    @ApiParam(value = "数据类型 数据类型 01 合照(传rybh和wjid即可) 02 明细数据（正常数据字段保存）",required = true)
    private String dataType;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public BigDecimal getJz() {
        return jz;
    }

    public void setJz(BigDecimal jz) {
        this.jz = jz;
    }

    public String getCfwz() {
        return cfwz;
    }

    public void setCfwz(String cfwz) {
        this.cfwz = cfwz;
    }

    public String getCfwzName() {
        return cfwzName;
    }

    public void setCfwzName(String cfwzName) {
        this.cfwzName = cfwzName;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid;
    }

    public String getLbmc() {
        return lbmc;
    }

    public void setLbmc(String lbmc) {
        this.lbmc = lbmc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLrsjk() {
        return lrsjk;
    }

    public void setLrsjk(String lrsjk) {
        this.lrsjk = lrsjk;
    }
}
