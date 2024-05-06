package com.wckj.chasstage.api.def.baqry.model;

import io.swagger.annotations.ApiParam;

public class ActParam {

    @ApiParam("人员ID")
    private String ryid;
    @ApiParam("出所原因")
    private String csyy;
    @ApiParam("出所原因名称")
    private String csyyName;
    @ApiParam("临时出所 或 正式出所  采用RYZT字典值")
    private String type;
    @ApiParam("其他原因")
    private String qtyy;
    @ApiParam("物品状态json数据")
    private String wpztData;
    @ApiParam("审批页面地址")
    private String msgDealUrl;

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMsgDealUrl() {
        return msgDealUrl;
    }

    public void setMsgDealUrl(String msgDealUrl) {
        this.msgDealUrl = msgDealUrl;
    }
}
