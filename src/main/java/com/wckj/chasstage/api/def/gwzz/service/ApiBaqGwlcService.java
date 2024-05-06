package com.wckj.chasstage.api.def.gwzz.service;

import com.wckj.chasstage.api.def.gwzz.bean.GwzzBean;
import com.wckj.chasstage.api.def.gwzz.bean.GwzzParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * @author wutl
 * @Title: 岗位职责
 * @Package
 * @Description:
 * @date 2020-9-920:42
 */
public interface ApiBaqGwlcService extends IApiBaseService {

    ApiReturnResult<?> getGwzzPageData(GwzzParam gwzzParam);

    ApiReturnResult<?> saveGwlc(GwzzBean gwzzBean);

    ApiReturnResult<?> deleteGwlc(String ids);
}
