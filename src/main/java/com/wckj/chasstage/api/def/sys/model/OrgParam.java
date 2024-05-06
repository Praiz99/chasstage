package com.wckj.chasstage.api.def.sys.model;


import com.wckj.chasstage.api.def.common.model.BaseParam;

/**
 * @author wutl
 * @Title: 机构查询参数
 * @Package
 * @Description:
 * @date 2020-9-310:27
 */
public class OrgParam extends BaseParam {

    private String orgCode;
    private String sysCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}
