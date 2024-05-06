package com.wckj.chasstage.api.def.yjxx.model;


/**
 * 大屏预警统计数据
 */
public class DpDataBean {
    private String title="";
    private Integer type;
    private String monitorUrl;
    private String typeName;
    private String cfr;
    private String yjzt;
    private String yjztName;
    private String qy;
    private String sj;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCfr() {
        return cfr;
    }

    public void setCfr(String cfr) {
        this.cfr = cfr;
    }

    public String getYjzt() {
        return yjzt;
    }

    public void setYjzt(String yjzt) {
        this.yjzt = yjzt;
    }

    public String getYjztName() {
        return yjztName;
    }

    public void setYjztName(String yjztName) {
        this.yjztName = yjztName;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }
}
