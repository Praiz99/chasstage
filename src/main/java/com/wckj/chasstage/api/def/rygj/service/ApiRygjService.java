package com.wckj.chasstage.api.def.rygj.service;


import com.wckj.chasstage.api.def.rygj.model.RygjBean;
import com.wckj.chasstage.api.def.rygj.model.RygjParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import java.util.Date;


public interface ApiRygjService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(RygjBean bean);
    ApiReturnResult<?> update(RygjBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getBaqPageData(RygjParam rygjParam);

    ApiReturnResult<?> getRygjUrl(RygjParam rygjParam);

    ApiReturnResult<?> getRtspValByQyid(String qyid);

    /**
     * 根据人员编号获取轨迹视频xml
     * @param rybh
     * @param kssj
     * @param jssj
     * @param areaNo
     * @return
     */
    ApiReturnResult<?> getRygjXmlByRybh(String rybh, String kssj, String jssj, String areaNo);

    /**
     * 人员出区时，通知vms
     * @param baqid 办案区id
     * @param rybh 人员编号
     * @param qyid 区域id（原始id，dc数据库主键）
     * @return
     */
    ApiReturnResult<?> sendLastVmsInfo(String baqid,String rybh,String qyid);

    /**
     * 轨迹后录制
     * @param baqid
     * @param rybh
     * @param qyid
     * @return
     */
    ApiReturnResult<?> sendRecVmsInfo(Date kssj, Date jssj, String baqid, String rybh, String qyid);
}
