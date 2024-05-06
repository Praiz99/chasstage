package com.wckj.chasstage.api.server.release.dc.dto;

public class LSLocationEventInfo {
    private String tagNo;
    private String areaId;
    private String fenceId;
    private String fenceName;
    private int eventType;
    private String disc;
    private String expandParm;
    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getExpandParm() {
        return expandParm;
    }

    public void setExpandParm(String expandParm) {
        this.expandParm = expandParm;
    }
}
