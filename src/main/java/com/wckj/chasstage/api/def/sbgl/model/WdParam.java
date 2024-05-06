package com.wckj.chasstage.api.def.sbgl.model;

import io.swagger.annotations.ApiParam;

public class WdParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    @ApiParam(value = "腕带编号")
    private String wdbh;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getWdbh() {
        return wdbh;
    }

    public void setWdbh(String wdbh) {
        this.wdbh = wdbh;
    }
}
