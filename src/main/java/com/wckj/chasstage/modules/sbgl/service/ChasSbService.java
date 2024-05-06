package com.wckj.chasstage.modules.sbgl.service;


import com.wckj.chasstage.api.def.sbgl.model.SbglBean;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sbgl.dao.ChasSbMapper;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasSbService extends IBaseService<ChasSbMapper, ChasSb> {

    /** 查询继电器 by areaid **/
    List<ChasSb> findByParams(Map<String, Object> map);

    DevResult openOrClose(String id, Integer parseInt);
    //根据类型和功能查找办案区设备
    List<ChasSb> getBaqSbsByLxAndGn(String baqid, String lx, String gn);

    String queryHWatchNo(String wdbhL);

    void clearByBaqAndLx(String baqid, String type);

    Map getRtspValByQyid(String qyid);
    //获取随身拍照摄像头，实现预览功能
    ChasSb getPreviewCamera(String baqid, String gw);

    //查询终端设备
    List<ChasSb> findZDSB();



    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    /**
     * 获取办案区空闲胸卡
     *
     * @param baqid
     * @return
     */
    List<ChasSb> getKxxk(String baqid);


    int saveSbgl(SbglBean bean);

    int updateSbgl(SbglBean bean);

    int deleteSbgl(String id);

    ChasSb getSbByLxAndQy(String sblx,String qyid);
}
