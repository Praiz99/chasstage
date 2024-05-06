package com.wckj.chasstage.modules.rygj.dao;


import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnapExt;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用于检测同案混关聚集等预警
 */
@Repository
public interface ChasRygjSnapMapper extends IBaseDao<ChasRygjSnap> {
    //获取办案区所有在区人员最新定位信息
    List<ChasRygjSnapExt> getRywzxxBybaqid(@Param("baqid") String baqid);
    List<ChasRygjSnap> getMjwzxxBybaqid(@Param("baqid") String baqid);
    List<ChasRygjSnap> getFkwzxxBybaqid(@Param("baqid") String baqid);
    List<ChasRygjSnap> getDjjwzxxBybaqid(@Param("baqid") String baqid);
    int deleteByRybh(@Param("rybh") String rybh);
}