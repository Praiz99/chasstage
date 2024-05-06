package com.wckj.chasstage.api.def.qtdj.service;

import com.wckj.api.def.zfba.model.ApiGgJqxx;
import com.wckj.chasstage.api.def.qtdj.model.*;
import com.wckj.chasstage.api.def.sys.model.MjxxParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface ApiQtdjService extends IApiBaseService {

    ApiReturnResult<?> getJqxxPageData(JqxxParam param);

    ApiReturnResult<?> getAjxxPageData(AjxxParam param);

    ApiReturnResult<?> validateWd(WdParam param);

    ApiReturnResult<?> getBoxNoByType(String boxType,String baqid,String type);

    ApiReturnResult<?> SaveWithUpdate(RyxxBean param);

    ApiReturnResult<?> getBaqryxxDataGrid(RyxxParam param);

    ApiReturnResult<?> getDataByYwbh(TaryParam param);

    ApiReturnResult<?> SaveWithUpdateSswpByList(String json,String del);

    ApiReturnResult<?> saveYthcjQk(YthcjParam param);

    ApiReturnResult<?> getYthcjQk(String rybh);

    ApiReturnResult<?> loginByWdbh(String wdbhL, String rybh);

    ApiReturnResult<?> capture(SswpPzParam param);

    ApiReturnResult<?> findByRybh(String rybh);

    ApiReturnResult<?> SaveAqjc(AqjcBean aqjcBean);

    ApiReturnResult<?> getRyxxAllByRybh(String rybh);

    /**
     * 流程状态修改
     * @param rybh
     * @param lczt
     * @return
     */
    ApiReturnResult<?> updateStaffProcessStatus(String rybh, String lczt);

    ApiReturnResult<?> Securitycheck(String rybh);

    /**
     * 语音播报环节
     * @param baqid
     * @param bbhj
     * @return
     */
    ApiReturnResult<?> yybbByQtdj(String baqid, String bbhj,String rybh);

    ApiReturnResult<?> getRyxxByImg(HttpServletRequest request, String baqid, String base64);
}
