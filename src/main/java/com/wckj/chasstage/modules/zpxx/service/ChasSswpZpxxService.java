package com.wckj.chasstage.modules.zpxx.service;

import com.wckj.chasstage.api.def.zpxx.model.ZpxxParam;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.zpxx.dao.ChasSswpZpxxMapper;
import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;

public interface ChasSswpZpxxService extends IBaseService<ChasSswpZpxxMapper, ChasSswpZpxx> {

    DevResult saveZpxxWithFile(ZpxxParam param);

    DevResult uploadpz(String bizId, String base64,String bizType);

    void deleteByWpid(String wpid);

    List<ChasSswpZpxx> findByWpid(String wpid);

    void deleteBybizId(String bizid);

    ChasSswpZpxx findZpxxByBizId(String bizId);

    /**
     * 根据人员编号获取合照信息
     * @param rybh
     * @return
     */
    ChasSswpZpxx findHzpzxxByRybh(String rybh);
}
