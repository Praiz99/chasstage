package com.wckj.chasstage.api.def.yjlb.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class YjlbParam extends BaseParam {
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "预警类别")
    private String yjlb;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getYjlb() {
        return yjlb;
    }

    public void setYjlb(String yjlb) {
        this.yjlb = yjlb;
    }
}
