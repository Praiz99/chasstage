package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

import java.util.List;

public class HikMapGoByInfo {

    private Integer id;

    private Integer mapId;

    private String mapName;

    private String enterTime;

    private String leaveTime;

    private Long staySeconds;

    private Integer vrFloorId;

    private List<HikAreaGoByInfo> areas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
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

    public Integer getVrFloorId() {
        return vrFloorId;
    }

    public void setVrFloorId(Integer vrFloorId) {
        this.vrFloorId = vrFloorId;
    }

    public List<HikAreaGoByInfo> getAreas() {
        return areas;
    }

    public void setAreas(List<HikAreaGoByInfo> areas) {
        this.areas = areas;
    }
}
