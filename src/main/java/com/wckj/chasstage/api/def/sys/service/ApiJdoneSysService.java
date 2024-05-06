package com.wckj.chasstage.api.def.sys.service;

import com.wckj.chasstage.api.def.sys.model.*;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;

public interface ApiJdoneSysService extends IApiBaseService {

    ApiReturnResult<?> getMjxxPageData(HttpServletRequest request, MjxxParam param);

    ApiReturnResult<?> getOrgPageData(HttpServletRequest request ,OrgParam orgParam);
}
