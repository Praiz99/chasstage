package com.wckj.chasstage.api.def.zyry.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: QYT
 * @Date: 2023/6/16 9:33 上午
 * @Description:在押人员预警参数
 */
public class ZyryParam {

    @ApiModelProperty("办案区id")
    private String baqid;

    @ApiModelProperty("入区时间范围开始时间")
    private String kssj;

    @ApiModelProperty("入区时间范围结束时间")
    private String jssj;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }
}
