package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiParam;

public class SswpPzParam {
    @ApiParam(value = "人员编号",required = true)
    private String rybh;
    @ApiParam(value = "摄像头功能",required = true)
    private String cameraFun;
    @ApiParam(value = "照片bizId",required = true)
    private String bizId;
    @ApiParam(value = "是否采用岗位模式")
    private String  gw;

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getCameraFun() {
        return cameraFun;
    }

    public void setCameraFun(String cameraFun) {
        this.cameraFun = cameraFun;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }
}
