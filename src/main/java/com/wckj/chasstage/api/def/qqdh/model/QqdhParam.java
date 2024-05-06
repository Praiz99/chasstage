package com.wckj.chasstage.api.def.qqdh.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;


public class QqdhParam extends BaseParam {

    @ApiParam(value = "办案区")
    private String baqid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "申请人")
    private String sqrxm;
    @ApiParam(value = "人员id",required = true)
    private String ryid;
    @ApiParam(value = "申请状态")
    private String zt;
    @ApiParam(value = "联系人")
    private String lxr;
    @ApiParam(value = "联系电话")
    private String lxrdh;
    @ApiParam(value = "单位系统编号")
    private String dwxtbh;
    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getSqrxm() {
        return sqrxm;
    }

    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxrdh() {
        return lxrdh;
    }

    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }
}
