package com.wckj.chasstage.modules.httpApi.client.hkrldw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikAreaGoByInfo;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikLocationPaths;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikMapGoByInfo;

import java.util.List;

public class HikLocationPathsResult {

    private Boolean status;

    private String msg;

    private HikLocationPaths paths;

    public static HikLocationPathsResult success(String msg, String json){
        HikLocationPathsResult hikLocationPathsResult = new HikLocationPathsResult();
        hikLocationPathsResult.setStatus(true);
        hikLocationPathsResult.setMsg(msg);
        JSONObject paths = JSON.parseObject(json);
        JSONArray maps = paths.getJSONArray("maps");
        HikLocationPaths hikLocationPaths = JSON.parseObject(json, HikLocationPaths.class);
        List<HikMapGoByInfo> mapList = JSON.parseArray(paths.getString("maps"), HikMapGoByInfo.class);
        for(int i = 0; i < maps.size(); i++){
            JSONObject map = maps.getJSONObject(i);
            List<HikAreaGoByInfo> areaList = JSON.parseArray(map.getString("details"), HikAreaGoByInfo.class);
            mapList.get(i).setAreas(areaList);
        }
        hikLocationPaths.setMaps(mapList);
        hikLocationPathsResult.setPaths(hikLocationPaths);
        return hikLocationPathsResult;
    }

    public static HikLocationPathsResult fail(String msg){
        HikLocationPathsResult hikLocationPathsResult = new HikLocationPathsResult();
        hikLocationPathsResult.setStatus(false);
        hikLocationPathsResult.setMsg(msg);
        return hikLocationPathsResult;
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

    public HikLocationPaths getPaths() {
        return paths;
    }

    public void setPaths(HikLocationPaths paths) {
        this.paths = paths;
    }
}
