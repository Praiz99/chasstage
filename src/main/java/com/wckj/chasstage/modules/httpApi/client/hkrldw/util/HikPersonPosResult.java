package com.wckj.chasstage.modules.httpApi.client.hkrldw.util;

import com.alibaba.fastjson.JSON;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikPersonPosition;

public class HikPersonPosResult {

    private Boolean status;

    private String msg;

    private HikPersonPosition hikPersonPosition;

    public static HikPersonPosResult success(String msg, String json){
        HikPersonPosResult hikPersonPosResult = new HikPersonPosResult();
        hikPersonPosResult.setStatus(true);
        hikPersonPosResult.setMsg(msg);
        HikPersonPosition hikPersonPosition = JSON.parseObject(json, HikPersonPosition.class);
        hikPersonPosResult.setHikPersonPosition(hikPersonPosition);
        return hikPersonPosResult;
    }

    public static HikPersonPosResult fail(String msg){
        HikPersonPosResult hikPersonPosResult = new HikPersonPosResult();
        hikPersonPosResult.setStatus(false);
        hikPersonPosResult.setMsg(msg);
        return hikPersonPosResult;
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

    public HikPersonPosition getHikPersonPosition() {
        return hikPersonPosition;
    }

    public void setHikPersonPosition(HikPersonPosition hikPersonPosition) {
        this.hikPersonPosition = hikPersonPosition;
    }
}
