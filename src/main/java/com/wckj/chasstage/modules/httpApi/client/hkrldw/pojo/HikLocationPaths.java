package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

import java.util.List;

public class HikLocationPaths {

    private String enterTime;

    private String leaveTime;

    private Long staySeconds;

    private List<HikMapGoByInfo> maps;

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

    public List<HikMapGoByInfo> getMaps() {
        return maps;
    }

    public void setMaps(List<HikMapGoByInfo> maps) {
        this.maps = maps;
    }
}
