package com.wckj.chasstage.api.def.yygl.service;

import com.wckj.chasstage.api.def.yygl.model.YyglBean;
import com.wckj.chasstage.api.def.yygl.model.YyglParam;
import com.wckj.chasstage.api.def.yygl.model.YyjyParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 预约管理
 */
public interface ApiYyglService extends IApiBaseService {
    ApiReturnResult<?> get(String id);
    ApiReturnResult<?> save(YyglBean bean);
    ApiReturnResult<?> deletes(String ids);
    ApiReturnResult<?> getPageData(YyglParam param);
    ApiReturnResult<?> yyjy(YyjyParam param);

    /**
     * 检测车牌是否预约
     * @param baqid
     * @param cphm
     * @param crlx
     * @return
     */
    ApiReturnResult<?> checkYyCphm(String baqid, String cphm, String crlx);

    ApiReturnResult<?> getBaqrefByOrgcode(String orgCode);
}
