package com.wckj.chasstage.api.def.yygl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * 预约检验
 */
public class YyjyParam extends BaseParam {
    @ApiParam(value = "身份证号码",required = true)
    private String sfzh;
    @ApiParam("单位代码")
    private String dwdm;

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }
}
