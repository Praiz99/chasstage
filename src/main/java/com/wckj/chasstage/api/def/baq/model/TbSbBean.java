package com.wckj.chasstage.api.def.baq.model;


import io.swagger.annotations.ApiModelProperty;

public class TbSbBean {
    @ApiModelProperty("天线数量")
    private Integer ant;
    @ApiModelProperty("储物柜数量")
    private Integer cab;
    @ApiModelProperty("读卡器数量")
    private Integer reader;
    @ApiModelProperty("NVR数量")
    private Integer nvr;
    @ApiModelProperty("基站数量")
    private Integer equip;
    @ApiModelProperty("摄像头数量")
    private Integer camera;
    @ApiModelProperty("继电器数量")
    private Integer relay;
    @ApiModelProperty("电子水牌数量")
    private Integer dzsp;
    @ApiModelProperty("LED数量")
    private Integer led;
    @ApiModelProperty("标签数量")
    private Integer tag;

    public Integer getAnt() {
        return ant;
    }

    public void setAnt(Integer ant) {
        this.ant = ant;
    }

    public Integer getCab() {
        return cab;
    }

    public void setCab(Integer cab) {
        this.cab = cab;
    }

    public Integer getReader() {
        return reader;
    }

    public void setReader(Integer reader) {
        this.reader = reader;
    }

    public Integer getNvr() {
        return nvr;
    }

    public void setNvr(Integer nvr) {
        this.nvr = nvr;
    }

    public Integer getEquip() {
        return equip;
    }

    public void setEquip(Integer equip) {
        this.equip = equip;
    }

    public Integer getCamera() {
        return camera;
    }

    public void setCamera(Integer camera) {
        this.camera = camera;
    }

    public Integer getRelay() {
        return relay;
    }

    public void setRelay(Integer relay) {
        this.relay = relay;
    }

    public Integer getDzsp() {
        return dzsp;
    }

    public void setDzsp(Integer dzsp) {
        this.dzsp = dzsp;
    }

    public Integer getLed() {
        return led;
    }

    public void setLed(Integer led) {
        this.led = led;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
