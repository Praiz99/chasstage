package com.wckj.chasstage.modules.rygj.service;


import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.rygj.dao.ChasRygjMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasYwRygjService extends IBaseService<ChasRygjMapper, ChasRygj> {
    ChasRygj findzhlocation(Map<String, Object> map);

    List<ChasRygj> findzhlocations(Map<String, Object> map);

    void delete(String[] ids);

    List<ChasRygj> findbyparams(Map<String, Object> map);

    PageDataResultSet<Map> selectAllByFk(int var1, int var2, Object var3, String var4);
    List<ChasRygj> selectrygjBysxs(String baqid, String qyid, String kssj);
    List<ChasRygj> selectrygjByMj(Map<String, Object> map);
    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);

    String findcountByBaqid(String baqid);

    Map<String, Object>  getRtspValByQyid(String qyid);

    void manualAddRygj(ChasBaqryxx ryxx);
    void manualAddRygj(ChasYwMjrq ryxx);
    void manualAddRygj(ChasYwFkdj ryxx);
}
