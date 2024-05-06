package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;


public interface IJdqService {
    //打开或关闭审讯室继电器
    DevResult openOrColseXxs(String baqid, String qyid, Integer onoff);
    DevResult openOrClose(String baqid, Integer onoff, String sbbh);
    DevResult openRelayBytime(String baqid, String sbbh, long hms);
    //关闭报警器
    DevResult closeAlarm(String baqid);
    //打开报警器
    DevResult openAlarm(String baqid, long time);
    //获取亲情电话录音文件url
    DevResult getQqdhRec(ChasQqyz qqyz);
    //打开或关闭审讯室排风扇
    DevResult openOrColsePfs(String baqid, String qyid, Integer onoff);

    boolean sendYjxxmsg(ChasYjxx chasYjxx, String yjmc, String yjfs, Integer yjsc);

    }
