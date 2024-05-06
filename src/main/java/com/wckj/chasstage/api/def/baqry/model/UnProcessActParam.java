package com.wckj.chasstage.api.def.baqry.model;

import io.swagger.annotations.ApiModelProperty;


public class UnProcessActParam {

    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "出所类型 05 临时出所 02 正式出区")
    private String type;
    @ApiModelProperty(value = "出所原因 字典 CSYY")
    private String csyy;
    @ApiModelProperty(value = "出所原因名称 CSYY 字典名称")
    private String csyyName;
    @ApiModelProperty(value = "其他原因")
    private String qtyy;
    @ApiModelProperty(value = " 字典 WPCLZT 物品处理状态数据[{id:物品id,wpclzt:01},{id:物品id,wpclzt:03}]")
    private String wpztData;
    @ApiModelProperty(value = "根据办案区配置获取，1 页面出所，0 刷卡出所")
    private String csfs;

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCsyy() {
        return csyy;
    }

    public void setCsyy(String csyy) {
        this.csyy = csyy;
    }

    public String getCsyyName() {
        return csyyName;
    }

    public void setCsyyName(String csyyName) {
        this.csyyName = csyyName;
    }

    public String getQtyy() {
        return qtyy;
    }

    public void setQtyy(String qtyy) {
        this.qtyy = qtyy;
    }

    public String getWpztData() {
        return wpztData;
    }

    public void setWpztData(String wpztData) {
        this.wpztData = wpztData;
    }

    public String getCsfs() {
        return csfs;
    }

    public void setCsfs(String csfs) {
        this.csfs = csfs;
    }
}
