package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class TaryBean {
    @ApiModelProperty("照片url")
    private String zpid;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("人员姓名")
    private String ryxm;

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }
}
