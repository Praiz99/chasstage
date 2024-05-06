package com.wckj.chasstage.modules.sxsgl.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SxskzUtil {
    private static SxskzUtil sxskzUtil = null;

    //用于更新看守区小屏信息
    private Map<String, List<RySxsFpxx>> sxsFpCache = new ConcurrentHashMap<>();

    public static SxskzUtil getInstance(){
        if(sxskzUtil == null){
            sxskzUtil = new SxskzUtil();
            return sxskzUtil;
        }else{
            return sxskzUtil;
        }
    }

    //将审讯分配信息添加到缓存
    public void addSxsFpxx(String baqid,String rybh,String content){
        if(!sxsFpCache.containsKey(baqid)){
            sxsFpCache.put(baqid, new ArrayList<>());
        }
        RySxsFpxx fpxx = new RySxsFpxx(rybh,content);

        synchronized (this){
            List<RySxsFpxx> list = sxsFpCache.get(baqid);
            for(int i=0;i<list.size();i++){
                RySxsFpxx o = list.get(i);
                if(o.getRybh().equals(rybh)){
                    list.remove(i);
                    break;
                }
            }
            list.add(fpxx);
        }
    }
    //将审讯分配信息从缓存中移除
    public void removeSxsFpxx(String baqid,String rybh){
        if(sxsFpCache.containsKey(baqid)){
            synchronized (this){
                List<RySxsFpxx> list = sxsFpCache.get(baqid);
                for(int i=0;i<list.size();i++){
                    RySxsFpxx o = list.get(i);
                    if(o.getRybh().equals(rybh)){
                        list.remove(i);
                        break;
                    }
                }
            }
        }
    }
    public String getLastSxsFpxx(String baqid){
        if(sxsFpCache.containsKey(baqid)){
            synchronized (this){
                List<RySxsFpxx> list = sxsFpCache.get(baqid);
                if(list.isEmpty()){
                    return "暂无人员分配到审讯室";
                }else{
                    return list.get(list.size()-1).getContent();
                }
            }
        }
        return "暂无人员分配到审讯室";
    }
}
