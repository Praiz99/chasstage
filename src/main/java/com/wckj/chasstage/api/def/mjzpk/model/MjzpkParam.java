package com.wckj.chasstage.api.def.mjzpk.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class MjzpkParam extends BaseParam {
    private String baqid;
    @ApiParam("单位系统编号")
    private String dwxtbh;
    @ApiParam("民警姓名")
    private String mjxm;
    @ApiParam("民警身份证号")
    private String mjsfzh;
    @ApiParam("民警警号")
    private String mjjh;
    @ApiParam("开始时间")
    private String startDate;
    @ApiParam("结束时间")
    private String endDate;
    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getMjsfzh() {
        return mjsfzh;
    }

    public void setMjsfzh(String mjsfzh) {
        this.mjsfzh = mjsfzh;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
