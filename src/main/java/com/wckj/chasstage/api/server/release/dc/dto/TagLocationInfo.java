package com.wckj.chasstage.api.server.release.dc.dto;

public class TagLocationInfo {
    private String tagId;
    private String tagType;
    private String areaName;
    /**
     * 区域原始id
     */
    private String area;
    private String equipNo;
    //设备Id
    private String zsid;
    //棒功能
    private String equipFun;
    //人员编号
    private String rybh;
    // 定位方式，1：激活棒 2：2.4G定位
    private String locationType;
    // 第一次定位到此位置的时间
    private long startTime;
    private long sendTime;
    private long locationTime;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getEquipNo() {
        return equipNo;
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }

    public String getEquipFun() {
        return equipFun;
    }

    public void setEquipFun(String equipFun) {
        this.equipFun = equipFun;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
