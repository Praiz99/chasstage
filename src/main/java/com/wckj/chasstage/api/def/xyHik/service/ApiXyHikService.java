package com.wckj.chasstage.api.def.xyHik.service;

import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 审讯室管理
 */
public interface ApiXyHikService extends IApiBaseService {
    //办案区审讯室使用情况
    ApiReturnResult<?> getSxsInfo(String baqid,String appSfzh,int page,int rows);

    ApiReturnResult<?> getBaqryxxDataGrid(String dwdm, String rssj1, String rssj2, int page, int rows);

    ApiReturnResult<?> getAlarmData(String dwdm);

    ApiReturnResult<?> getRyxxDataTotal(String dwdm);
}
