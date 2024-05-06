package com.wckj.chasstage.common.delaytask;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class TaskBase implements Delayed,Runnable {
    protected String id;
    protected Map<String,Object> data;
    protected long expire;

    public TaskBase(String id,Map<String,Object> data,long expire) {
        this.id=id;
        this.data =data;
        this.expire = expire + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        long delta = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (int) delta;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DelayTask) {
            return this.getId().equals(((DelayTask) obj).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{id:"+this.getId()+",data:" + JSON.toJSONString(data) + ",expire:" + new Date(expire) + "}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public long getExpire() {
        return expire;
    }


}
