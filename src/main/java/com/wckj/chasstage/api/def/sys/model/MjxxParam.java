package com.wckj.chasstage.api.def.sys.model;


import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 民警信息入参
 * @Package
 * @Description:
 * @date 2020-9-210:05
 */
public class MjxxParam extends BaseParam {
    @ApiParam("姓名")
    private String name;
    @ApiParam("单位名称")
    private String dwmc;
    @ApiParam("单位代码")
    private String orgCode;
    @ApiParam("单位系统代码")
    private String sysCode;
    @ApiParam("身份证号")
    private String sfzh;
    @ApiParam("警号")
    private String jh;
    @ApiParam("单位代码(用于模糊查询)")
    private String orgCode1 ;
    @ApiParam("单位代码(用于筛选)")
    private String orgCodes ;

    public String getOrgCode1() {
        return orgCode1;
    }

    public void setOrgCode1(String orgCode1) {
        this.orgCode1 = orgCode1;
    }



    public String getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(String orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

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

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }
}
