package com.wckj.chasstage.modules.baqry.service;


import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.modules.baqry.dao.ChasBaqryxxMapper;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.impl.ChasYwMjrqServiceImpl;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;

import java.util.List;
import java.util.Map;

public interface ChasBaqryxxService extends IBaseService<ChasBaqryxxMapper, ChasBaqryxx> {

    Map<String,Object> SaveWithUpdate(RyxxBean param) throws Exception;

    Map<String,Object> SaveWithUpdateByForm(RyxxBean rxxBean) throws Exception;

    /**人员出所*/
    void rydeparture(String csspId, String spzt, String spyj, JdoneSysUser spr) throws Exception;

    void startProcess(String ryId, String csyy, String csyyName, String type, String qtyy, NodeProcessCmdObj2 cmdObj) throws  Exception;

    Map<String,Object> PersonnelReturn(String ryid);

    ChasBaqryxx findRyxxBywdbhL(String wdbhL);

    Map<String,Object> Departure(String rybh,String qwid,String fhzt) throws Exception;

    void DepartureBynotDevice(ChasBaqryxx baqryxx, String qwid) throws Exception;

    void DepartureBynotProcess(ChasBaqryxx baqryxx, ChasYmCssp cssp, String wpztData) throws Exception;

    List<ChasBaqryxx> findTaryByRyjlYwbh(String ywbh);

    String getRyxxCount(String baqid, String ryzt);

    public ChasBaqryxx getRyxxObj(ChasBaqryxx ryxx);

    void deleteBaqryxxs(String id) throws Exception;

    List<Map<String,Object>> getDataByYwbh(Map<String, Object> params);

    ChasBaqryxx findByRysfzh(String sfzh);

    ChasBaqryxx findByRybh(String rybh);

    Map<String,String> getwgsjtj(String sysOrgCode);

    //去掉isdel =0查询条件
    ChasBaqryxx findBaqryxByRybhAll(String rybh,String baqid);

    ChasBaqryxx findBaqryxBySfzhAll(String sfzh,String baqid);

    void deavebatchBaqry(String rybhs, String csyy, String qtyy);

    PageDataResultSet<ChasBaqryxxBq> getEntityPageDataBecauseQymc(int var1, int var2, Object var3, String var4);

    /**
     * 根据人员信息判断该人员是否是未成年
     * @param baqryxx
     * @return
     */
    boolean isWcn(ChasBaqryxx baqryxx);
    int clearByRybh(String rybh);

    /**
     * 根据腕带编号获取送押人员信息
     * @param wdbhl
     * @param baqid
     * @return
     */
    ChasBaqryxx getSyryByWdbhl(String wdbhl, String baqid);

    /**
     * 获取当前最新腕带使用人信息
     * @param wdbhl
     * @return
     */
    ChasBaqryxx findCurrentByWdbhl(String wdbhl);

    Map<String,Object> saveOrUpdateRldw(ChasBaqryxx chasBaqryxx);

    void endRldw(String baqid, String rybh, String registerCode);

    PageDataResultSet<ChasBaqryxx> getYfpwpgBaqryxx(Integer page,Integer rows,Map<String, Object> param,String orderBy);
}
