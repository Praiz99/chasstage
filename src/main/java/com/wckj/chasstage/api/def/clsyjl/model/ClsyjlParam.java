package com.wckj.chasstage.api.def.clsyjl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class ClsyjlParam extends BaseParam {
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam("车辆id")
    private String clid;
    @ApiParam("车牌号")
    private String clNumber;
    @ApiParam("人员编号")
    private String rybh;
    @ApiParam("手环低频编号")
    private String wdbhL;
    @ApiParam("使用批次编号")
    private String sypcbh;
    @ApiParam("是否使用结束")
    private String isend;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getSypcbh() {
        return sypcbh;
    }

    public void setSypcbh(String sypcbh) {
        this.sypcbh = sypcbh;
    }

    public String getIsend() {
        return isend;
    }

    public void setIsend(String isend) {
        this.isend = isend;
    }
}
