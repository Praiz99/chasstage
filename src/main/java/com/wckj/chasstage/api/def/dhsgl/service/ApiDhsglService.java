package com.wckj.chasstage.api.def.dhsgl.service;

import com.wckj.chasstage.api.def.dhsgl.model.DhsBean;
import com.wckj.chasstage.api.def.dhsgl.model.DhsFpParam;
import com.wckj.chasstage.api.def.dhsgl.model.DhsParam;
import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.def.qqdh.model.QqdhParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import java.util.List;

/**
 * 等候室管理
 */
public interface ApiDhsglService extends IApiBaseService {
    //办案区等候室分配情况
    ApiReturnResult<?> getDhsInfo(String baqid);
    //等候室分配人员列表
    ApiReturnResult<?> getDhsRyInfo(DhsParam param);
    //等候室调整时，预分配结果
    ApiReturnResult<?> getYfpInfo(DhsFpParam param);
    //等候室调整或分配
    ApiReturnResult<?> dhsfp(DhsFpParam param);
}
