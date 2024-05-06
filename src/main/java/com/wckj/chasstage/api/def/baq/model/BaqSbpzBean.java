package com.wckj.chasstage.api.def.baq.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 办案区设备配置
 * @Package 办案区设备配置
 * @Description: 办案区设备配置
 * @date 2020-10-1113:57
 */
public class BaqSbpzBean {
    @ApiModelProperty(value = "信息登记终端-卧室登记终端")
    private String xxdjzd_wsdjzd;
    @ApiModelProperty(value = "信息登记终端-立式登记终端")
    private String xxdjzd_lsdjzd;
    @ApiModelProperty(value = "信息登记终端-无登记终端")
    private String xxdjzd_wdjzd;

    @ApiModelProperty(value = "随身物品柜-标准单面柜")
    private String sswpg_bzdmg;
    @ApiModelProperty(value = "随身物品柜-非标准单面柜")
    private String sswpg_fbzdmg;
    @ApiModelProperty(value = "随身物品柜-标准双面柜")
    private String sswpg_bzsmg;
    @ApiModelProperty(value = "随身物品柜-非标准双面柜")
    private String sswpg_fbzsmg;

    @ApiModelProperty(value = "交互终端-有")
    private String jhdz_y;
    @ApiModelProperty(value = "交互终端-无")
    private String jhdz_w;

    public String getXxdjzd_wsdjzd() {
        return xxdjzd_wsdjzd;
    }

    public void setXxdjzd_wsdjzd(String xxdjzd_wsdjzd) {
        this.xxdjzd_wsdjzd = xxdjzd_wsdjzd;
    }

    public String getXxdjzd_lsdjzd() {
        return xxdjzd_lsdjzd;
    }

    public void setXxdjzd_lsdjzd(String xxdjzd_lsdjzd) {
        this.xxdjzd_lsdjzd = xxdjzd_lsdjzd;
    }

    public String getXxdjzd_wdjzd() {
        return xxdjzd_wdjzd;
    }

    public void setXxdjzd_wdjzd(String xxdjzd_wdjzd) {
        this.xxdjzd_wdjzd = xxdjzd_wdjzd;
    }

    public String getSswpg_bzdmg() {
        return sswpg_bzdmg;
    }

    public void setSswpg_bzdmg(String sswpg_bzdmg) {
        this.sswpg_bzdmg = sswpg_bzdmg;
    }

    public String getSswpg_fbzdmg() {
        return sswpg_fbzdmg;
    }

    public void setSswpg_fbzdmg(String sswpg_fbzdmg) {
        this.sswpg_fbzdmg = sswpg_fbzdmg;
    }

    public String getSswpg_bzsmg() {
        return sswpg_bzsmg;
    }

    public void setSswpg_bzsmg(String sswpg_bzsmg) {
        this.sswpg_bzsmg = sswpg_bzsmg;
    }

    public String getSswpg_fbzsmg() {
        return sswpg_fbzsmg;
    }

    public void setSswpg_fbzsmg(String sswpg_fbzsmg) {
        this.sswpg_fbzsmg = sswpg_fbzsmg;
    }

    public String getJhdz_y() {
        return jhdz_y;
    }

    public void setJhdz_y(String jhdz_y) {
        this.jhdz_y = jhdz_y;
    }

    public String getJhdz_w() {
        return jhdz_w;
    }

    public void setJhdz_w(String jhdz_w) {
        this.jhdz_w = jhdz_w;
    }
}
