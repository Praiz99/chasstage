package com.wckj.chasstage.api.def.qtdj.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class WdParam {

    @ApiParam("办案区ID")
    private String baqid;
    @ApiParam("腕带编号")
    private String wdbhL;
    @ApiParam("人员编号")
    private String rybh;
    @ApiParam("是否胸卡 1 腕带 2 胸卡")
    private String type;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

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
}
