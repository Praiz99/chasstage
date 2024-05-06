package com.wckj.chasstage.api.def.xgpz.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class XgpzParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }
}
