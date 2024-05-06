package com.wckj.chasstage.api.def.dhsgl.model;

import io.swagger.annotations.ApiParam;

public class DhsFpParam {
    @ApiParam(value = "人员ID",required = true)
    private String ryid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "等候室ID",required = true)
    private String qyid;

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }
}
