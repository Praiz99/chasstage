package com.wckj.chasstage.api.def.khd.service;

import com.wckj.chasstage.api.def.qtdj.model.RyxxParam;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 信息登记终端服务
 * @Package
 * @Description:
 * @date 2020-11-49:52
 */
public interface ApiXxdjzdService extends IApiBaseService {

    /**
     * 信息登记终端查询办案区人员信息
     * @param ryxxParam
     * @return
     */
    ApiReturnResult<?> getBaqryxxDataGrid(RyxxParam ryxxParam);

    List<ChasBaqryxx> getBaqryxxList(Map<String,Object> param);

    /**
     * 腕带编号
     * @param wdbhl
     * @param baqid
     * @param syzt
     * @return
     */
    ApiReturnResult<?> findSyryByWdbhl(String wdbhl, String baqid, String syzt);

    /**
     * 解除腕带
     * @param wdbhl
     * @return
     */
    ApiReturnResult<?> jcwd(String wdbhl,String baqid);

    /**
     * 根据人员编号解除腕带编号
     * @param rybh
     * @return
     */
    ApiReturnResult<?> jcdwByRybh(String rybh,String baqid);

    /**
     * 获取客户端版本更新数据
     * @param versionNo
     * @param clientType
     * @return
     */
    ApiReturnResult<?> getClientVersion(String versionNo,String clientType);

    /**
     *获取国籍等引用表字典
     * @param dicMark
     * @param checkDate
     * @return
     */
    ApiReturnResult<?> getRefDicDataByName(String dicMark,String checkDate);

    /**
     * 获取有柜子的在区嫌疑人信息
     * @param page
     * @param rows
     * @param baqid
     * @return
     */
    ApiReturnResult<?> getYfpwpgBaqryxx(Integer page,Integer rows,String baqid);

    /**
     * 分页获取办案区物品柜
     * @param page
     * @param rows
     * @param baqid
     * @return
     */
    ApiReturnResult<?> getWpgPageData(Integer page, Integer rows, String baqid);
}
