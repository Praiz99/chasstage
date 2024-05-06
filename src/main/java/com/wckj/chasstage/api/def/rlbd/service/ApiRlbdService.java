package com.wckj.chasstage.api.def.rlbd.service;

import com.wckj.chasstage.api.def.rlbd.model.RlbdParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 人脸大数据比对
 * @Package
 * @Description:
 * @date 2020-12-1711:21
 */
public interface ApiRlbdService extends IApiBaseService {
    /**
     * 获取人脸大数据平台数据
     * @param rlbdParam
     * @return
     */
    ApiReturnResult<?> getRlbdData(RlbdParam rlbdParam);

    ApiReturnResult<?> checkSfzhAndXm(HttpServletRequest request,String sfzh, String xm);

}
