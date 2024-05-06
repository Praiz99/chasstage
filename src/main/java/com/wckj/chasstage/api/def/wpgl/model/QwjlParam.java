package com.wckj.chasstage.api.def.wpgl.model;

import io.swagger.annotations.ApiParam;

public class QwjlParam {
    @ApiParam(value = "储物柜id",required = true)
    private String cabId;
    @ApiParam(value = "民警身份证号",required = true)
    private String mjsfz;
    @ApiParam(value = "处理类型",required = true)
    private String czlx;
    @ApiParam(value = "mjxm",required = true)
    private String mjxm;

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getMjsfz() {
        return mjsfz;
    }

    public void setMjsfz(String mjsfz) {
        this.mjsfz = mjsfz;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }
}
