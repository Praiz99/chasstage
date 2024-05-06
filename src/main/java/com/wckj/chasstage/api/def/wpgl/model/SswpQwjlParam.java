package com.wckj.chasstage.api.def.wpgl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class SswpQwjlParam extends BaseParam {

    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "手机柜ID")
    private String cabId;
    @ApiParam(value = "操作类型(04 取  05 存)")
    private String czlx;

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }
}
