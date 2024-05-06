package com.wckj.chasstage.common.util;

import java.util.HashMap;
import java.util.Map;

public class MemUtil {
    public static Map key_value=new HashMap();
    public static Map<Object,Long> key_time=new HashMap<Object,Long>();//过期时间
    public static void put(Object key,Object value,int millisecond){
        key_value.put(key,value);
        key_time.put(key,System.currentTimeMillis()+millisecond);
    }

    public static Object get(Object key){
        Long expirationTime = key_time.get(key);
        long currentTimeMillis = System.currentTimeMillis();
        if(expirationTime==null||expirationTime<currentTimeMillis){
            remove(key);
            return null;
        }else {
            return key_value.get(key);
        }
    }

    public static void remove(Object key){
        key_value.remove(key);
        key_time.remove(key);
    }
}
