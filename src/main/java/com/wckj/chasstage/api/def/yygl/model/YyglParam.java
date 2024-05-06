package com.wckj.chasstage.api.def.yygl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class YyglParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("姓名")
    private String yyrxm;
    @ApiParam("预约类型")
    private String yylx;
    @ApiParam("证件号码")
    private String yyrsfzh;
    @ApiParam("胸卡编号")
    private String sbbh;
    @ApiParam("预约状态 01预约 02入区 03出区")
    private String crqzt;
    @ApiParam("角色代码")
    private String roleCode;
    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getYyrxm() {
        return yyrxm;
    }

    public void setYyrxm(String yyrxm) {
        this.yyrxm = yyrxm;
    }

    public String getYylx() {
        return yylx;
    }

    public void setYylx(String yylx) {
        this.yylx = yylx;
    }

    public String getYyrsfzh() {
        return yyrsfzh;
    }

    public void setYyrsfzh(String yyrsfzh) {
        this.yyrsfzh = yyrsfzh;
    }

    public String getSbbh() {
        return sbbh;
    }

    public void setSbbh(String sbbh) {
        this.sbbh = sbbh;
    }

    public String getCrqzt() {
        return crqzt;
    }

    public void setCrqzt(String crqzt) {
        this.crqzt = crqzt;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
