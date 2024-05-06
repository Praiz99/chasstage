package com.wckj.chasstage.api.server.release.dc.dto;

public class CameraLocationInfo {

    /**
     * 摄像头编号
     */
    private String cameraId;
    /**
     * 摄像头名称
     */
    private String cameraName;
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * 摄像头功能
     */
    private String equipFun;
    /**
     * 人员编号
     */
    private String rybh;
    /**
     * 人员类型
     */
    private String rylx;
    // 第一次定位到此位置的时间
    private long startTime;
    private long sendTime;
    private long locationTime;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
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

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }
}
