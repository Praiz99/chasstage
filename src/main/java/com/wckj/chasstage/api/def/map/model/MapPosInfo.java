package com.wckj.chasstage.api.def.map.model;



import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapPosInfo {
    private String qyid;
    private List<Map<String,Object>> list= new ArrayList<>();

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public List<Map<String,Object>> getList() {
        return list;
    }
}
