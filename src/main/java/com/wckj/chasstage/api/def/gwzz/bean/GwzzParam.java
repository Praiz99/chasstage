package com.wckj.chasstage.api.def.gwzz.bean;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class GwzzParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    @ApiParam(value = "角色代码")
    private String roleCode;

    @ApiParam(value = "机构代码")
    private String orgCode;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
