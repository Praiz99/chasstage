package com.wckj.chasstage.modules.httpApi.client.hkrldw.util;

import com.alibaba.fastjson.JSON;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikMapPersonLocation;

import java.util.List;

public class HikMapPersonResult {

    private Boolean status;

    private String msg;

    private List<HikMapPersonLocation> mapPersonList;

    public static HikMapPersonResult success(String msg, String json){
        HikMapPersonResult hikMapPersonResult = new HikMapPersonResult();
        hikMapPersonResult.setStatus(true);
        hikMapPersonResult.setMsg(msg);
        List<HikMapPersonLocation> mapPersonList = JSON.parseArray(json, HikMapPersonLocation.class);
        hikMapPersonResult.setMapPersonList(mapPersonList);
        return hikMapPersonResult;
    }

    public static HikMapPersonResult fail(String msg){
        HikMapPersonResult hikMapPersonResult = new HikMapPersonResult();
        hikMapPersonResult.setStatus(false);
        hikMapPersonResult.setMsg(msg);
        return hikMapPersonResult;
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

    public List<HikMapPersonLocation> getMapPersonList() {
        return mapPersonList;
    }

    public void setMapPersonList(List<HikMapPersonLocation> mapPersonList) {
        this.mapPersonList = mapPersonList;
    }
}
