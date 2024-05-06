package com.wckj.chasstage.modules.signconfig.service;

import com.wckj.chasstage.api.def.baq.model.SignConfigBean;
import com.wckj.chasstage.modules.signconfig.dao.ChasXtSignConfigMapper;
import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasXtSignConfigService extends IBaseService<ChasXtSignConfigMapper, ChasXtSignConfig> {

    ChasXtSignConfig findByBaqid(String baqid);

    void saveSignConfig(SignConfigBean bean) throws Exception;

    Map<?,?> getSignConfigPageData(int page, int rows, Map<String,Object> params, String order);

    boolean xgqzlx(String id, String[] checkedCities);
}
