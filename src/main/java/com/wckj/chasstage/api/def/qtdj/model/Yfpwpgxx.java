package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;

public class Yfpwpgxx {
    @ApiModelProperty("箱子编号")
    private String boxNo;
    @ApiModelProperty("箱子id")
    private String cabid;

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getCabid() {
        return cabid;
    }

    public void setCabid(String cabid) {
        this.cabid = cabid;
    }
}
