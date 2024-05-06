package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

import java.math.BigDecimal;

public class HikPathInfo {

    private BigDecimal longitude;

    private BigDecimal latitude;

    private BigDecimal altitude;

    private BigDecimal originalX;

    private BigDecimal originalY;

    private BigDecimal originalZ;

    private String time;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }

    public BigDecimal getOriginalX() {
        return originalX;
    }

    public void setOriginalX(BigDecimal originalX) {
        this.originalX = originalX;
    }

    public BigDecimal getOriginalY() {
        return originalY;
    }

    public void setOriginalY(BigDecimal originalY) {
        this.originalY = originalY;
    }

    public BigDecimal getOriginalZ() {
        return originalZ;
    }

    public void setOriginalZ(BigDecimal originalZ) {
        this.originalZ = originalZ;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
