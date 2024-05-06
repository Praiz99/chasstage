package com.wckj.chasstage.api.def.fkgl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class FkglParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("性别")
    private String xb;
    @ApiParam("访客姓名")
    private String fkxm;
    @ApiParam("访客类别")
    private String fklb;
    @ApiParam("证件号码")
    private String fksfzh;
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

    public String getFkxm() {
        return fkxm;
    }

    public void setFkxm(String fkxm) {
        this.fkxm = fkxm;
    }

    public String getFklb() {
        return fklb;
    }

    public void setFklb(String fklb) {
        this.fklb = fklb;
    }

    public String getFksfzh() {
        return fksfzh;
    }

    public void setFksfzh(String fksfzh) {
        this.fksfzh = fksfzh;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }
}
