package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

public class HikMapBasicInfo {

    private String name;

    private int mapId;

    private int locationType;

    private int vrModelId;

    private int vrFloorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public int getVrModelId() {
        return vrModelId;
    }

    public void setVrModelId(int vrModelId) {
        this.vrModelId = vrModelId;
    }

    public int getVrFloorId() {
        return vrFloorId;
    }

    public void setVrFloorId(int vrFloorId) {
        this.vrFloorId = vrFloorId;
    }
}
