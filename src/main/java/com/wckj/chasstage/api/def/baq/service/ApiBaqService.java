package com.wckj.chasstage.api.def.baq.service;

import com.wckj.chasstage.api.def.baq.model.*;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wutl
 * @Title: 办案区服务
 * @Package 办案区服务
 * @Description: 办案区服务
 * @date 2020-9-815:16
 */
@Service
public interface ApiBaqService extends IApiBaseService {

    ApiReturnResult<?> SaveWithUpdate(BaqBean baq);

    ApiReturnResult<?> deletes(String ids);

    ApiReturnResult<?> getBaqPageData(BaqParam baqParam);

    ApiReturnResult<?> saveSignConfig(SignConfigBean sign);

    ApiReturnResult<?> getSignConfigPageData(SignConfigParam signConfigParam);

    ApiReturnResult<?> getWszhPageData(WszhParam wszhParam);
    ApiReturnResult<?> getWszhPData(String baqid);

      ApiReturnResult<?>  getBaqDicByUser(String queryValue, int page, int pagesize);

    ApiReturnResult<?> saveWithUpdateInstrument(WszhBean wszh);

    ApiReturnResult<?> deleteWszh(String ids);

    ApiReturnResult<?> getBaqznpzPageData(BaqznpzParam baqznpzParam);
    ApiReturnResult<?> getBaqPzData(String baqid);

    ApiReturnResult<?> getBaqSingnData(String baqid);


    ApiReturnResult<?> saveBaqznpz(BaqznpzBean baqznpz);
    ApiReturnResult<?> tbBaqqy(String baqId);
    ApiReturnResult<?> tbSb(String baqId);

    ApiReturnResult<?> baqtb(String baqid);

    /**
     * 根据当前登录人获取办案区信息
     * @return
     */
    ApiReturnResult<BaqBean> getBaqByLogin();

    /**
     * 根据办案区id获取办案区配置
     * @param baqid
     * @return
     */
    ApiReturnResult<BaqFunCfg> getBaqcfgByBaqid(String baqid);

    ApiReturnResult<List<BaqGnglBean>> getBaqgnglByBaqid(String baqid);

    ApiReturnResult<?> saveBaqgnglByBaqid(BaqGnglBean baqGnglBean);

    ApiReturnResult<?> deleteBaqgnglByBaqid(String id);

    ApiReturnResult<?> checkInBaqpz(String baqid,String gnpzId);

    /**
     * 根据当前登录人获取下面办案区（市级20 区县30 基层40）
     * @return
     */
    ApiReturnResult<?> getBaqDicByLogin();

    ApiReturnResult< ? > getStartBhByLogin();

    ApiReturnResult<?> xgqzlx(String id, String[] checkedCities);
}
