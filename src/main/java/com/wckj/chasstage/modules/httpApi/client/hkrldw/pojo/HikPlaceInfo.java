package com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo;

import java.util.List;

public class HikPlaceInfo {

    private String placeCode;

    private String placeName;

    private String relatedOrgCode;

    private String relatedOrgName;

    private List<HikMapBasicInfo> maps;

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getRelatedOrgCode() {
        return relatedOrgCode;
    }

    public void setRelatedOrgCode(String relatedOrgCode) {
        this.relatedOrgCode = relatedOrgCode;
    }

    public String getRelatedOrgName() {
        return relatedOrgName;
    }

    public void setRelatedOrgName(String relatedOrgName) {
        this.relatedOrgName = relatedOrgName;
    }

    public List<HikMapBasicInfo> getMaps() {
        return maps;
    }

    public void setMaps(List<HikMapBasicInfo> maps) {
        this.maps = maps;
    }
}
