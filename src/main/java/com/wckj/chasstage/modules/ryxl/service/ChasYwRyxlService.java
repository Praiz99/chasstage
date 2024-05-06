package com.wckj.chasstage.modules.ryxl.service;


import com.wckj.chasstage.modules.ryxl.dao.ChasYwRyxlMapper;
import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.framework.orm.mybatis.service.IBaseService;

public interface ChasYwRyxlService extends IBaseService<ChasYwRyxlMapper, ChasYwRyxl> {
    //处理人员心率异常预警
    void processRyxlYj(ChasYwRyxl ryxl);
    ChasYwRyxl getLastestRyxlByRybh(String rybh);
}
