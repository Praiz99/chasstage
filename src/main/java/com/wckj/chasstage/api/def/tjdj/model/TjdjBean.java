package com.wckj.chasstage.api.def.tjdj.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 体检登记对象
 * @Package
 * @Description:
 * @date 2020-10-2414:27
 */
public class TjdjBean {

    @ApiModelProperty(value = "id主键")
    private String id;
    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "入所体检")
    private String rstj;
    @ApiModelProperty(value = "胸部正位图")
    private String xbzw;
    @ApiModelProperty(value = "B超检查片")
    private String bcjc;
    @ApiModelProperty(value = "心电图结果")
    private String xdt;
    @ApiModelProperty(value = "血常规结果")
    private String xcg;
    @ApiModelProperty(value = "血压检查单")
    private String xyjc;
    @ApiModelProperty(value = "其他")
    private String qt;
    @ApiModelProperty(value = "是否异常(1 是：异常 0 否)")
    private Integer sfyc;
    @ApiModelProperty(value = "异常说明")
    private String ycsm;
    @ApiModelProperty(value = "人员编号")
    private String rybh;

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

    public String getXyjc() {
        return xyjc;
    }

    public void setXyjc(String xyjc) {
        this.xyjc = xyjc;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public Integer getSfyc() {
        return sfyc;
    }

    public void setSfyc(Integer sfyc) {
        this.sfyc = sfyc;
    }

    public String getYcsm() {
        return ycsm;
    }

    public void setYcsm(String ycsm) {
        this.ycsm = ycsm;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
}
