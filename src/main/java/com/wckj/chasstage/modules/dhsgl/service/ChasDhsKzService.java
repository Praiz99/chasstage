package com.wckj.chasstage.modules.dhsgl.service;


import com.wckj.chasstage.api.def.dhsgl.model.DhsParam;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.dhsgl.dao.ChasDhsKzMapper;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasDhsKzService extends IBaseService<ChasDhsKzMapper, ChasDhsKz> {
    /*** 此方法专用 必填baqid fjlx ryzt **/
    List<Map<String, Object>> selectDhsForyj(Map<String, Object> map);
    List<Map<String, Object>> selectForryjj(Map<String, Object> map);
    List<ChasDhsKz> findByParams(Map<String, Object> map);
    String findcountByBaqid(String baqid);

    void updateXmXbByRybh(String ryxm, String ryxb, String rybh);
    //用于正式出区逻辑删除等候室分配信息
    int clearByrybh(String rybh);
    //List<ChasBaqryxx> selectRyxxBydhs(DhsRyFormData param);
    //获取等候室同案人数
    int getTaRs(String qyid, String ywbh, String baqid);
    //获取等候室混关人数
    int getHgRs(String qyid, String xb,String baqid);
    /**
     * 计算等候室当前分配人数
     * @param baqid
     * @param qyid
     * @return
     */
    int findcountByBaqidAndQyid(String baqid, String qyid);
    List<ChasBaqryxx> selectRyxxBydhs(DhsParam param);
    // 解除等候室 逻辑删除
    DevResult dhsJc(String baqid, String rybh, boolean dxled);

    Map<String, Object> findDhsryList(String baqid);

}
