package com.wckj.chasstage.api.def.sxsgl.service;

import com.wckj.chasstage.api.def.sxsgl.model.SxsFpParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 审讯室管理
 */
public interface ApiSxsglService extends IApiBaseService {
    //办案区审讯室使用情况
    ApiReturnResult<?> getSxsInfo(String baqid,String appSfzh,int page,int rows);
    //打开审讯室
    ApiReturnResult<?> open(String id);
    //关闭审讯室
    ApiReturnResult<?> close(String id);
    //获取审讯室摄像头信息
    ApiReturnResult<?> getSxsSxtInfo(String id);
    //审讯室调整
    ApiReturnResult<?> sxsfp(SxsFpParam param, HttpServletRequest request);

    //打开审讯室排风扇
    ApiReturnResult<?> openPfs(String id);
    //关闭审讯室排风扇
    ApiReturnResult<?> closePfs(String id);

    ApiReturnResult<?> findSxsqkByQyid(String id);

    ApiReturnResult<?> obtainPersonnelInformationBySxsId(String qyid);

    ApiReturnResult<?> getAreaByBaqid(int page, int rows, String baqid, String sxsZt);
}
