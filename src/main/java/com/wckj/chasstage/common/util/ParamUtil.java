package com.wckj.chasstage.common.util;

import java.util.HashMap;
import java.util.Map;

public final class ParamUtil {
    private ParamUtil(){}

    public static Builder builder(){
        return new Builder();
    }

    public static Map<String,Object> getDataGrid(Long total,Object rows){
        return builder().accept("total",total).accept("rows",rows).toMap();
    }

    public static class Builder{
        private Map<String,Object> param = new HashMap<>();

        public Builder accept(String key,Object value){
            this.param.put(key,value);
            return this;
        }

        public Map<String,Object> toMap(){
            return param;
        }
    }
}
