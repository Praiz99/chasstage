package com.wckj.chasstage.api.def.baqry.service;

import com.wckj.chasstage.api.def.baqry.model.TjdjBean;
import com.wckj.chasstage.api.def.baqry.model.UnProcessActParam;
import com.wckj.chasstage.api.def.qtdj.model.RyxxBean;
import com.wckj.chasstage.api.def.qtdj.model.UserParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ApiBaqryService extends IApiBaseService {

    ApiReturnResult<?> deleteBaqryxx(String ids);

    ApiReturnResult<RyxxBean> getRyxxIdByWdbhL(String wdbhL);

    ApiReturnResult<String> PersonnelReturn(String ryid);

    ApiReturnResult<String> getPhotoByRybh(String rybh);

    ApiReturnResult<String> Departure(String rybh,String qwid,String fhzt);

    ApiReturnResult<String> canleaveByRybh(String rybh);

    ApiReturnResult<?> startProcess(NodeProcessCmdObj2 node);

    ApiReturnResult<?> executeProcess(NodeProcessCmdObj cmdObj);

    ApiReturnResult<?> SaveWithUpdateByForm(RyxxBean ryxxBean);

    ApiReturnResult<?> getMjData(UserParam user);

    /**
     * 进出情况
     * @param page
     * @param rows
     * @param param
     * @return
     */
    ApiReturnResult<Map<String,Object>> getRycrqk(int page, int rows, Map<String, Object> param);

    /**
     * 根据人员Id获取人员体检登记
     * @param ryid
     * @return
     */
    ApiReturnResult<TjdjBean> getTytjdj(String ryid);

    /**
     * 保存体检登记
     * @param tjdjBean
     * @return
     */
    ApiReturnResult<String> saveTytjdj(TjdjBean tjdjBean);

    ApiReturnResult<?> confirmLeavingunprocess(UnProcessActParam unProcessActParam);

    ApiReturnResult<?> deavebatchBaqry(String rybhs,String csyy,String qtyy);

    /**
     * 关联警情
     * @param rybh
     * @param jqbh
     * @return
     */
    ApiReturnResult<String> associationJq(String rybh, String jqbh);

    /**
     * 修改人员审批流程状态
     * @param rybh
     * @param ryzt
     * @return
     */
    ApiReturnResult<String> updateRyztByRybh(String rybh, String ryzt);

    ApiReturnResult<?> getwgsjtj(String sysOrgCode);

    ApiReturnResult<RyxxBean> findByRybh(String rybh);

    /**
     * 确认出所
     * @param cssj
     * @param rybh
     * @param bizId
     * @return
     */
    ApiReturnResult<?> saveConfirmLeaving(String cssj, String rybh, String bizId);

    /**
     * 出所开柜
     * @param baqid
     * @param rybh
     * @param cwgbh
     * @param sjgbh
     * @return
     */
    ApiReturnResult<?> saveDepartOpencwg(String baqid, String rybh, String cwgbh, String sjgbh);

    /**
     * 查询腕带最新使用人员信息
     * @param wdbhl
     * @return
     */
    ApiReturnResult<RyxxBean> findCurrentByWdbhl(String wdbhl);

    /**
     * 人员信息修改（无业务）
     * @param ryxxBean
     * @return
     */
    ApiReturnResult<?> updateRyxx(RyxxBean ryxxBean);

    ApiReturnResult<?> getSfzhyyxxData(String sfzh,String xm,String type,Integer page,Integer rows);

    /***
     * 获取出所摄像头信息
     * @param baqid
     * @return
     */
    ApiReturnResult<?> getCsSxtInfo(String baqid);
}
