package com.wckj.chasstage.api.def.baqry.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 体检登记对象
 * @Package 体检登记对象
 * @Description: 体检登记对象
 * @date 2020-9-2215:53
 */
public class TjdjBean {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "入所体检")
    private String rstj;
    @ApiModelProperty(value = "胸部正位")
    private String xbzw;
    @ApiModelProperty(value = "B超检查")
    private String bcjc;
    @ApiModelProperty(value = "心电图")
    private String xdt;
    @ApiModelProperty(value = "血常规检查")
    private String xcg;
    @ApiModelProperty(value = "其他")
    private String qt;
    @ApiModelProperty(value = "血压检查单")
    private String xyjc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getRstj() {
        return rstj;
    }

    public void setRstj(String rstj) {
        this.rstj = rstj;
    }

    public String getXbzw() {
        return xbzw;
    }

    public void setXbzw(String xbzw) {
        this.xbzw = xbzw;
    }

    public String getBcjc() {
        return bcjc;
    }

    public void setBcjc(String bcjc) {
        this.bcjc = bcjc;
    }

    public String getXdt() {
        return xdt;
    }

    public void setXdt(String xdt) {
        this.xdt = xdt;
    }

    public String getXcg() {
        return xcg;
    }

    public void setXcg(String xcg) {
        this.xcg = xcg;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getXyjc() {
        return xyjc;
    }

    public void setXyjc(String xyjc) {
        this.xyjc = xyjc;
    }
}
