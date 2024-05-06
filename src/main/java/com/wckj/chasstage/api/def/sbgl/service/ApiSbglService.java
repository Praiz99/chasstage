package com.wckj.chasstage.api.def.sbgl.service;

import com.wckj.chasstage.api.def.sbgl.model.SbglBean;
import com.wckj.chasstage.api.def.sbgl.model.SbglParam;
import com.wckj.chasstage.api.def.sbgl.model.WdParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.stereotype.Service;

@Service
public interface ApiSbglService extends IApiBaseService {

    ApiReturnResult<String> checkWdbh(WdParam wdParam);
    ApiReturnResult<String> openOrClose(String id,int dc);



    ApiReturnResult<String> getSbglApiPageData(SbglParam param);


    ApiReturnResult<String> saveSbgl(SbglBean bean);

    ApiReturnResult<String> updateSbgl(SbglBean bean);

    ApiReturnResult<String> deleteSbgl(String id);

    ApiReturnResult<String> getSbByLxAndQy(SbglParam param);
}
