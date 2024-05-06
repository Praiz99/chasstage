package com.wckj.chasstage.api.def.xgjl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class XgjlParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("打卡人(卡号)")
    private String kh;
    @ApiParam("设备名称")
    private String sbmc;
    @ApiParam("区域id")
    private String qyid;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }
}
