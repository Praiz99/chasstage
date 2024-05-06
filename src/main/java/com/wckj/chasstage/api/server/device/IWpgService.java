package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface IWpgService {
    //预分配储物柜
    DevResult fpCwg(String baqid, String boxtype);
    //预分配手机柜
    DevResult fpSjg(String baqid, String boxtype);
    //打开储物柜
    DevResult openCwg(String baqid, String rybh);
    DevResult openCwg(String baqid, String rybh, String boxbh,String sbgn);
    //打开储物柜后门
    DevResult openBgCwg(String baqid, String rybh,String boxbh);
    //解除储物柜使用关系
    DevResult jcCwg(String baqid, String rybh);
    //解除手机柜
    DevResult jcSjg(String baqid, String rybh);
    DevResult jcZng(String baqid, String rybh, String boxbh);
    //绑定储物柜使用关系
    DevResult cwgBd(String baqid, String rybh);
    //绑定手机柜
    DevResult sjgBd(String baqid, String rybh);
    //DevResult cwgLsOpen(String baqid, String rybh, String boxbh);
   //绑定储物柜使用关系
   DevResult cwgBd(String baqid, String rybh, String wpgbh);
    //绑定手机柜
    DevResult sjgBd(String baqid, String rybh, String sjbh);
}
