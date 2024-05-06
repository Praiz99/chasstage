package com.wckj.chasstage.api.def.dhsgl.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 等候室界面数据
 */
public class DhsBean {
    @ApiModelProperty(value = "等候室(区域)id")
    private String id;
    @ApiModelProperty(value = "等候室名称")
    private String qymc;
    @ApiModelProperty(value = "房间类型")
    private String fjlx;
    @ApiModelProperty(value = "扩展类型")
    private String kzlx;
    @ApiModelProperty(value = "最大容量人数")
    private int rysl;
    @ApiModelProperty(value = "已分配人数")
    private int dqrs;
    @ApiModelProperty(value = "未成年人数")
    private int wcnrs;
    @ApiModelProperty(value = "特殊群体人数")
    private int tsqtrs;
    @ApiModelProperty(value = "重点监护人数")
    private int zdjhrs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getKzlx() {
        return kzlx;
    }

    public void setKzlx(String kzlx) {
        this.kzlx = kzlx;
    }

    public int getRysl() {
        return rysl;
    }

    public void setRysl(int rysl) {
        this.rysl = rysl;
    }

    public int getDqrs() {
        return dqrs;
    }

    public void setDqrs(int dqrs) {
        this.dqrs = dqrs;
    }

    public int getWcnrs() {
        return wcnrs;
    }

    public void setWcnrs(int wcnrs) {
        this.wcnrs = wcnrs;
    }

    public int getTsqtrs() {
        return tsqtrs;
    }

    public void setTsqtrs(int tsqtrs) {
        this.tsqtrs = tsqtrs;
    }

    public int getZdjhrs() {
        return zdjhrs;
    }

    public void setZdjhrs(int zdjhrs) {
        this.zdjhrs = zdjhrs;
    }
}
