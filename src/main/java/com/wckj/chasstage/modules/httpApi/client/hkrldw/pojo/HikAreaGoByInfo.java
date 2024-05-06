package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

import java.math.BigDecimal;

public class HikAreaGoByInfo {

    private Integer mapAreaId;

    private String areaName;

    private String enterTime;

    private String leaveTime;

    private Long staySeconds;

    private String cameraIndexCode;

    private BigDecimal indexPointX;

    private BigDecimal indexPointY;

    public Integer getMapAreaId() {
        return mapAreaId;
    }

    public void setMapAreaId(Integer mapAreaId) {
        this.mapAreaId = mapAreaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Long getStaySeconds() {
        return staySeconds;
    }

    public void setStaySeconds(Long staySeconds) {
        this.staySeconds = staySeconds;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public BigDecimal getIndexPointX() {
        return indexPointX;
    }

    public void setIndexPointX(BigDecimal indexPointX) {
        this.indexPointX = indexPointX;
    }

    public BigDecimal getIndexPointY() {
        return indexPointY;
    }

    public void setIndexPointY(BigDecimal indexPointY) {
        this.indexPointY = indexPointY;
    }
}
