package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

/**
 * 物品柜接口
 */
public interface IWpgOper {
    /**
     * 根据箱子类型 分配储物箱子
     * @param baqid 办案区编号
     * @param boxtype 箱子类型
     * @param sbgn 储物柜或者手机柜 手机柜时 boxtype 为小
     * @return
     */
    DevResult fpCwg(String baqid, String boxtype, String sbgn);

    /**
     * 打开储物箱子
     * @param baqid 办案区编号
     * @param rybh 人员编号
     * @param boxno 箱子编号
     * @return
     */
    DevResult openCwg(String baqid, String rybh, String boxno,String sbgn);

    /**
     * 打开储物箱子（后门）
     * @param baqid 办案区编号
     * @param rybh 人员编号
     * @param boxno 箱子编号
     * @return
     */
    DevResult openCwgBg(String baqid, String rybh, String boxno,String sbgn);

    /**
     * 解除储物箱子使用关系
     * @param baqid 办案区编号
     * @param rybh 人员编号
     * @param boxno 箱子编号
     * @return
     */
    DevResult jcCwg(String baqid, String rybh, String boxno,String sbgn);

    /**
     * 绑定储物箱子使用关系
     * @param baqid 办案区编号
     * @param rybh 人员编号
     * @param boxno 箱子编号
     * @param xm 人员姓名
     * @param wdbh 腕带编号
     * @return
     */
    DevResult cwgBd(String baqid, String rybh, String boxno, String xm, String wdbh,String sbgn);


}
