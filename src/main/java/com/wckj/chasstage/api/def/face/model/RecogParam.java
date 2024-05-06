package com.wckj.chasstage.api.def.face.model;

import io.swagger.annotations.ApiParam;

/**
 * 人脸识别参数
 */
public class RecogParam {
    @ApiParam(value = "人脸图片Base64字符串",required = true)
    private String base64Str;
    @ApiParam(value = "人员类型",hidden = true)
    private String rylx;
    @ApiParam(value = "单位系统编号")
    private String dwxtbh;
    @ApiParam(value = "办案区id")
    private String baqid;
    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }
}
