package com.wckj.chasstage.modules.znqt.service;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.znqt.dao.ChasZnqtMapper;
import com.wckj.chasstage.modules.znqt.entity.ChasZnqt;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasZnqtService extends IBaseService<ChasZnqtMapper, ChasZnqt> {
    Map<String,Object> saveZnqt(ChasZnqt znqt, String id) throws Exception;
    //打开智能墙体
    DevResult znqtJdq(String baqid, String qyid, String... sbbh);
}
