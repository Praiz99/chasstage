package com.wckj.chasstage.api.def.mjgl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class MjglParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("性别")
    private String xb;
    @ApiParam("民警姓名")
    private String ryxm;
    @ApiParam("证件号码")
    private String rySfzh;
    @ApiParam("胸卡编号")
    private String wdbhL;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRySfzh() {
        return rySfzh;
    }

    public void setRySfzh(String rySfzh) {
        this.rySfzh = rySfzh;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }
}
