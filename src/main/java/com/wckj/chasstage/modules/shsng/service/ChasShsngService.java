package com.wckj.chasstage.modules.shsng.service;

import com.wckj.chasstage.modules.shsng.dao.ChasSngMapper;
import com.wckj.chasstage.modules.shsng.entity.ChasSng;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

/**
 * @author:zengrk
 */
public interface ChasShsngService extends IBaseService<ChasSngMapper, ChasSng> {
    void getShxkDatas(String baqid) throws Exception;

    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    Integer selectZg();

    void yjcl();

    Map<String, Object> sdgh(String id);

    ChasSng findeByWdbhL(String kzcs2);

    void sngUpdate(ChasSng sng);
}
