package com.wckj.chasstage.api.def.clcrjl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;


public class ClcrjlParam extends BaseParam {

    @ApiParam("车辆品牌")
    private String clBrand;
    @ApiParam("出区开始时间")
    private String cqsj1;
    @ApiParam("出区结束时间")
    private String cqsj2;
    @ApiParam("入区开始时间")
    private String rqsj1;
    @ApiParam("入区结束时间")
    private String rqsj2;
    @ApiParam("车牌号")
    private String clNumber;
    @ApiParam("所属单位")
    private String dwxtbh;
    @ApiParam("责任民警")
    private String zrrXm;

    public String getClBrand() {
        return clBrand;
    }

    public void setClBrand(String clBrand) {
        this.clBrand = clBrand;
    }

    public String getCqsj1() {
        return cqsj1;
    }

    public void setCqsj1(String cqsj1) {
        this.cqsj1 = cqsj1;
    }

    public String getCqsj2() {
        return cqsj2;
    }

    public void setCqsj2(String cqsj2) {
        this.cqsj2 = cqsj2;
    }

    public String getRqsj1() {
        return rqsj1;
    }

    public void setRqsj1(String rqsj1) {
        this.rqsj1 = rqsj1;
    }

    public String getRqsj2() {
        return rqsj2;
    }

    public void setRqsj2(String rqsj2) {
        this.rqsj2 = rqsj2;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getZrrXm() {
        return zrrXm;
    }

    public void setZrrXm(String zrrXm) {
        this.zrrXm = zrrXm;
    }
}
