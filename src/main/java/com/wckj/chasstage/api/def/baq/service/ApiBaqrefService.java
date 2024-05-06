package com.wckj.chasstage.api.def.baq.service;

import com.wckj.chasstage.api.def.baq.model.BaqrefBean;
import com.wckj.chasstage.api.def.baq.model.BaqrefParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wutl
 * @Title: 办案区引用
 * @Package 办案区引用
 * @Description: 办案区引用
 * @date 2020-9-920:46
 */
@Service
public interface ApiBaqrefService extends IApiBaseService {

    ApiReturnResult<?> saveBaqRef(BaqrefBean baqref);

    ApiReturnResult<?> saveBatchRef(String baqid,String orgs);

    ApiReturnResult<?> deleteBaqRef(String ids);

    ApiReturnResult<?> getBaqrefPageData(BaqrefParam baqrefParam);
    ApiReturnResult<?> getOrgsByBaqid( String baqid);
    public ApiReturnResult<?> getOrgPageData(String baqid, String org,int page, int rows);
}
