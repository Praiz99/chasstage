package com.wckj.chasstage.modules.httpApi.client.hkrldw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikAreaPointOrder;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikPathInfo;

import java.util.List;

public class HikPathPositionResult {

    private Boolean status;

    private String msg;

    private List<HikAreaPointOrder> areaPointOrders;

    private List<HikPathInfo> paths;

    public static HikPathPositionResult success(String msg, String json){
        HikPathPositionResult hikPathPositionResult = new HikPathPositionResult();
        hikPathPositionResult.setStatus(true);
        hikPathPositionResult.setMsg(msg);
        JSONObject data = JSON.parseObject(json);
        List<HikAreaPointOrder> areaPointOrders = JSON.parseArray(data.getString("areaPointOrders"), HikAreaPointOrder.class);
        List<HikPathInfo> paths = JSON.parseArray(data.getString("path"), HikPathInfo.class);
        hikPathPositionResult.setAreaPointOrders(areaPointOrders);
        hikPathPositionResult.setPaths(paths);
        return hikPathPositionResult;
    }

    public static HikPathPositionResult fail(String msg){
        HikPathPositionResult hikPathPositionResult = new HikPathPositionResult();
        hikPathPositionResult.setStatus(false);
        hikPathPositionResult.setMsg(msg);
        return hikPathPositionResult;
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

    public List<HikAreaPointOrder> getAreaPointOrders() {
        return areaPointOrders;
    }

    public void setAreaPointOrders(List<HikAreaPointOrder> areaPointOrders) {
        this.areaPointOrders = areaPointOrders;
    }

    public List<HikPathInfo> getPaths() {
        return paths;
    }

    public void setPaths(List<HikPathInfo> paths) {
        this.paths = paths;
    }
}
