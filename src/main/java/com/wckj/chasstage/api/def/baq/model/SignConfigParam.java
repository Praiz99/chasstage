package com.wckj.chasstage.api.def.baq.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class SignConfigParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }
}
