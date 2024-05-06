package com.wckj.chasstage.modules.mjgl.service;


import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.dao.ChasYwMjrqMapper;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwMjrqService extends IBaseService<ChasYwMjrqMapper, ChasYwMjrq> {

    boolean insert(ChasYwMjrq fkdj)throws Exception;
    boolean edit(ChasYwMjrq fkdj)throws Exception;
    String delete(String[] idstr);
    ChasYwMjrq findByParams(Map<String, Object> map);

    ChasYwMjrq findMjrqByRybh(String baqid, String rybh);
    //民警出区
    Map<String,Object> mjChuqu(String id);

    /**
     * 客户端预约时，插入民警入区信息，以便进行民警定位
     * @param yy
     * @return
     */
    int insertMjrq(ChasYwYy yy, String zpid)throws Exception;
    ChasYwMjrq isMjzq(String sfzh);

    Map<String,Object> saveOrUpdateMjRldw(ChasYwMjrq chasYwMjrq);
}
