package com.wckj.chasstage.api.def.spjl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * @author:zengrk
 */
public class SpjlParam  extends BaseParam {
    @ApiParam("业务类型")
    private String bizType;
    @ApiParam("发起单位")
    private String fqdw;
    @ApiParam("发起人")
    private String fqr;
    @ApiParam("流程状态，1.已完成;0.处理中")
    private Integer lczt;
    @ApiParam("发起开始时间")
    private String fqkssj;
    @ApiParam("发起结束时间")
    private String fqjssj;
    @ApiParam("审批单位")
    private String spdw;
    @ApiParam("审批人")
    private String spr;
    @ApiParam("对象编号")
    private String dxbh;
    @ApiParam("对象名称")
    private String dxmc;
    @ApiParam("审批开始时间")
    private String spkssj;
    @ApiParam("审批结束时间")
    private String spjssj;

    public String getSpdw() {
        return spdw;
    }

    public void setSpdw(String spdw) {
        this.spdw = spdw;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFqdw() {
        return fqdw;
    }

    public void setFqdw(String fqdw) {
        this.fqdw = fqdw;
    }

    public String getFqr() {
        return fqr;
    }

    public void setFqr(String fqr) {
        this.fqr = fqr;
    }


    public Integer getLczt() {
        return lczt;
    }

    public void setLczt(Integer lczt) {
        this.lczt = lczt;
    }

    public String getFqkssj() {
        return fqkssj;
    }

    public void setFqkssj(String fqkssj) {
        this.fqkssj = fqkssj;
    }

    public String getFqjssj() {
        return fqjssj;
    }

    public void setFqjssj(String fqjssj) {
        this.fqjssj = fqjssj;
    }

    public String getSpr() {
        return spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }

    public String getDxbh() {
        return dxbh;
    }

    public void setDxbh(String dxbh) {
        this.dxbh = dxbh;
    }

    public String getDxmc() {
        return dxmc;
    }

    public void setDxmc(String dxmc) {
        this.dxmc = dxmc;
    }

    public String getSpkssj() {
        return spkssj;
    }

    public void setSpkssj(String spkssj) {
        this.spkssj = spkssj;
    }

    public String getSpjssj() {
        return spjssj;
    }

    public void setSpjssj(String spjssj) {
        this.spjssj = spjssj;
    }
}
