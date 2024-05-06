package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author wutl
 * @Title: 安全检查对象
 * @Package
 * @Description:
 * @date 2020-9-2617:21
 */
public class AqjcBean {

    @ApiModelProperty("人员id")
    @ApiParam(value = "人员id")
    private String id;

    @ApiModelProperty("安全检查开始时间")
    @ApiParam(value = "安全检查开始时间")
    private Date aqjckssj;

    @ApiModelProperty("安全检查结束时间")
    @ApiParam(value = "安全检查结束时间")
    private Date aqjcjssj;

    @ApiModelProperty("是否饮酒")
    @ApiParam(value = "是否饮酒")
    private Integer rSfyj;

    @ApiModelProperty("有无伤情")
    @ApiParam(value = "有无伤情")
    private Integer rSfssjc;

    @ApiModelProperty("伤情照片id")
    @ApiParam(value = "伤情照片id")
    private String ssclid;

    @ApiModelProperty("是否严重疾病")
    @ApiParam(value = "是否严重疾病")
    private Integer rSfyzjb;

    @ApiModelProperty("自述症状")
    @ApiParam(value = "自述症状")
    private String rYzjb;

    @ApiModelProperty("伤势描述")
    @ApiParam(value = "伤势描述")
    private String rSsms;

    @ApiModelProperty("人员编号")
    @ApiParam(value = "人员编号")
    private String rybh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getAqjckssj() {
        return aqjckssj;
    }

    public void setAqjckssj(Date aqjckssj) {
        this.aqjckssj = aqjckssj;
    }

    public Date getAqjcjssj() {
        return aqjcjssj;
    }

    public void setAqjcjssj(Date aqjcjssj) {
        this.aqjcjssj = aqjcjssj;
    }

    public Integer getrSfyj() {
        return rSfyj;
    }

    public void setrSfyj(Integer rSfyj) {
        this.rSfyj = rSfyj;
    }

    public Integer getrSfssjc() {
        return rSfssjc;
    }

    public void setrSfssjc(Integer rSfssjc) {
        this.rSfssjc = rSfssjc;
    }

    public String getSsclid() {
        return ssclid;
    }

    public void setSsclid(String ssclid) {
        this.ssclid = ssclid;
    }

    public Integer getrSfyzjb() {
        return rSfyzjb;
    }

    public void setrSfyzjb(Integer rSfyzjb) {
        this.rSfyzjb = rSfyzjb;
    }

    public String getrYzjb() {
        return rYzjb;
    }

    public void setrYzjb(String rYzjb) {
        this.rYzjb = rYzjb;
    }

    public String getrSsms() {
        return rSsms;
    }

    public void setrSsms(String rSsms) {
        this.rSsms = rSsms;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
}
