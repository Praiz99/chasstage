package com.wckj.chasstage.api.def.baq.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class WszhParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    @ApiParam(value = "字号头")
    private String zht;

    @ApiParam(value = "字号")
    private String zh;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getZht() {
        return zht;
    }

    public void setZht(String zht) {
        this.zht = zht;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }
}
