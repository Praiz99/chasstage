package com.wckj.chasstage.api.def.clsyjl.model;

import io.swagger.annotations.ApiParam;

public class AddClsyjlParam {
    @ApiParam("车辆id")
    private String clid;
    @ApiParam("人员编号 手环低频编号2选1")
    private String rybh;
    @ApiParam("手环低频编号 人员编号2选1")
    private String wdbhL;

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }
}
