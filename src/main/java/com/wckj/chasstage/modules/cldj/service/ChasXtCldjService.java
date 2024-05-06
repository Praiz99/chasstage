package com.wckj.chasstage.modules.cldj.service;



import com.wckj.chasstage.modules.cldj.dao.ChasXtCldjMapper;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.framework.orm.mybatis.service.IBaseService;


public interface ChasXtCldjService extends IBaseService<ChasXtCldjMapper, ChasXtCldj> {

    /**
     * 根据车牌号码查询车辆
     * @param cphm
     * @return
     */
    ChasXtCldj findByCphm(String cphm);
}
