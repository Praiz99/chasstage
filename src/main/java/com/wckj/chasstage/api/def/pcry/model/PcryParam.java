package com.wckj.chasstage.api.def.pcry.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class PcryParam extends BaseParam {

    @ApiParam(value = "人员姓名")
    private String ryxm;

    @ApiParam(value = "人员编号")
    private String rybh;

    @ApiParam(value = "性别")
    private String xb;
    @ApiParam(value = "开始采集时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kscjsj;
    @ApiParam(value = "结束采集时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jscjsj;

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public Date getKscjsj() {
        return kscjsj;
    }

    public void setKscjsj(Date kscjsj) {
        this.kscjsj = kscjsj;
    }

    public Date getJscjsj() {
        return jscjsj;
    }

    public void setJscjsj(Date jscjsj) {
        this.jscjsj = jscjsj;
    }
}
