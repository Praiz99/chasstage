package com.wckj.chasstage.api.def.baq.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class BaqrefParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    @ApiParam(value = "使用单位系统编号")
    private String sydwxtbh;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getSydwxtbh() {
        return sydwxtbh;
    }

    public void setSydwxtbh(String sydwxtbh) {
        this.sydwxtbh = sydwxtbh;
    }
}
