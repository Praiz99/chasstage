package com.wckj.chasstage.api.def.cldj.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;


public class CldjParam extends BaseParam {
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam("单位系统编号")
    private String dwxtbh;
    @ApiParam("品牌")
    private String clBrand;
    @ApiParam("型号")
    private String clModel;
    @ApiParam("车牌号")
    private String clNumber;
    @ApiParam("车辆使用状态 0 空闲 1 使用中")
    private Short clsyzt;
    @ApiParam("车辆类型 1预约 2押送")
    private String cllx;
    @ApiParam("人员编号")
    private String rybh;
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

    public String getClBrand() {
        return clBrand;
    }

    public void setClBrand(String clBrand) {
        this.clBrand = clBrand;
    }

    public String getClModel() {
        return clModel;
    }

    public void setClModel(String clModel) {
        this.clModel = clModel;
    }

    public String getClNumber() {
        return clNumber;
    }

    public void setClNumber(String clNumber) {
        this.clNumber = clNumber;
    }

    public Short getClsyzt() {
        return clsyzt;
    }

    public void setClsyzt(Short clsyzt) {
        this.clsyzt = clsyzt;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
}
