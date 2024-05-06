package com.wckj.chasstage.api.def.common.model;

import io.swagger.annotations.ApiModelProperty;

public class ProcessParam {

    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "出所原因")
    private String csyy;
    @ApiModelProperty(value = "出所原因名称")
    private String csyyName;
    @ApiModelProperty(value = "出所类型（05 临时出所  02 正式出所）")
    private String type;
    @ApiModelProperty(value = "其他原因")
    private String qtyy;
    @ApiModelProperty(value = "物品状态数据")
    private String wpztData;

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
}
