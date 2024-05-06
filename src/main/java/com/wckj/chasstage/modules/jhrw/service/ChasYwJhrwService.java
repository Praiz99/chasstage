package com.wckj.chasstage.modules.jhrw.service;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.jhrw.dao.ChasYwJhrwMapper;
import com.wckj.chasstage.modules.jhrw.entity.ChasYwJhrw;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasYwJhrwService extends IBaseService<ChasYwJhrwMapper, ChasYwJhrw> {

    List<ChasYwJhrw> getJhrwByRybhAndRwlx(String rybh, String rwlx);

    DevResult saveJhrw(String yjr, String orgCode, ChasBaqryxx baqry, String rwlx, String jhrwqd, String jhrwzd);

    DevResult changeJhrwZt(String jsr, String orgCode, ChasBaqryxx baqry, String rwlx, String rwzt);

    PageDataResultSet<ChasYwJhrw> getDpData(int page, int rows, Map<String, Object> params);

    List<ChasYwJhrw> getJhrwByrwzt(String rwzt);

    boolean accpectJhrw(String id);
    boolean deleteBatch(String ids);
    boolean saveJhry(String jhrwId,String rybhStr,String names);
}
