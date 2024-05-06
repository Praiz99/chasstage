package com.wckj.chasstage.modules.tjdj.service;

import com.wckj.chasstage.modules.tjdj.dao.ChasTjdjMapper;
import com.wckj.chasstage.modules.tjdj.entity.ChasTjdj;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasTjdjService extends IBaseService<ChasTjdjMapper, ChasTjdj> {

    public ChasTjdj getTjdjByRyid(String ryid);

    /**
     * 获取体检登记根据人员编号
     * @param rybh 人员编号
     * @return
     */
    ChasTjdj getTjdjByRybh(String rybh);
}
