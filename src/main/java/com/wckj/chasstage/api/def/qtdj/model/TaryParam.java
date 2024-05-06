package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class TaryParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("人员编号")
    private String rybh;
    @ApiParam("人员姓名")
    private String ryxm;
    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
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
