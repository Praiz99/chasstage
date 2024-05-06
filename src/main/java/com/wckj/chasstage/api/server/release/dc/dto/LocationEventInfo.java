package com.wckj.chasstage.api.server.release.dc.dto;

public class LocationEventInfo {
    private String tagNo;
    private String areaId;
    private String equipNo;
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

    public String getEquipNo() {
        return equipNo;
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo;
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
