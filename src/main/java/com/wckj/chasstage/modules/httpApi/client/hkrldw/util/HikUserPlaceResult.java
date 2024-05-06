package com.wckj.chasstage.modules.httpApi.client.hkrldw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikMapBasicInfo;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikPlaceInfo;

import java.util.List;

public class HikUserPlaceResult {

    private Boolean status;

    private String msg;

    private List<HikPlaceInfo> places;

    public static HikUserPlaceResult success(String msg, String json){
        HikUserPlaceResult hikUserPlaceResult = new HikUserPlaceResult();
        hikUserPlaceResult.setStatus(true);
        hikUserPlaceResult.setMsg(msg);
        List<HikPlaceInfo> places = JSON.parseArray(json, HikPlaceInfo.class);
        JSONArray array = JSON.parseArray(json);
        for(int i = 0; i < array.size(); i++){
            JSONObject data = array.getJSONObject(i);
            List<HikMapBasicInfo> maps = JSON.parseArray(data.getString("maps"), HikMapBasicInfo.class);
            places.get(i).setMaps(maps);
        }
        hikUserPlaceResult.setPlaces(places);
        return hikUserPlaceResult;
    }

    public static HikUserPlaceResult fail(String msg){
        HikUserPlaceResult hikUserPlaceResult = new HikUserPlaceResult();
        hikUserPlaceResult.setStatus(false);
        hikUserPlaceResult.setMsg(msg);
        return hikUserPlaceResult;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<HikPlaceInfo> getPlaces() {
        return places;
    }

    public void setPlaces(List<HikPlaceInfo> places) {
        this.places = places;
    }
}
