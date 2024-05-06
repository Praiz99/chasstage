package com.wckj.chasstage.api.def.cldj.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;


public class ClunbindParam {
    @ApiParam(value = "车辆ID")
    private String clid;
    @ApiParam("人员编号")
    private String rybh;

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
}
