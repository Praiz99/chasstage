package com.wckj.chasstage.api.def.rygj.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 人员轨迹视频nvr信息
 */
public class RygjspBean {
    @ApiModelProperty("ip")
    private String kzcs1;
    @ApiModelProperty("端口")
    private String kzcs2;
    @ApiModelProperty("用户名")
    private String kzcs3;
    @ApiModelProperty("密码")
    private String kzcs4;
    @ApiModelProperty("通道号")
    private String kzcs5;

    public String getKzcs1() {
        return kzcs1;
    }

    public void setKzcs1(String kzcs1) {
        this.kzcs1 = kzcs1;
    }

    public String getKzcs2() {
        return kzcs2;
    }

    public void setKzcs2(String kzcs2) {
        this.kzcs2 = kzcs2;
    }

    public String getKzcs3() {
        return kzcs3;
    }

    public void setKzcs3(String kzcs3) {
        this.kzcs3 = kzcs3;
    }

    public String getKzcs4() {
        return kzcs4;
    }

    public void setKzcs4(String kzcs4) {
        this.kzcs4 = kzcs4;
    }

    public String getKzcs5() {
        return kzcs5;
    }

    public void setKzcs5(String kzcs5) {
        this.kzcs5 = kzcs5;
    }
}
