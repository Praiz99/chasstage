package com.wckj.chasstage.api.def.znqt.service;

import com.wckj.chasstage.api.def.znqt.model.ZnqtParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

import java.util.Map;

/**
 * @author wutl
 * @Title: 智能墙体服务
 * @Package
 * @Description:
 * @date 2020-10-2010:02
 */
public interface ApiZnqtService extends IApiBaseService {
    /**
     * 智能墙体分页查询
     * @param page
     * @param rows
     * @param baqid
     * @param wzmc
     * @return
     */
    ApiReturnResult<Map<String, Object>> findZnqtPageData(int page, int rows, String baqid, String wzmc);

    /**
     * 保存智能墙体
     * @param znqtParam
     * @return
     */
    ApiReturnResult<String> saveZnqt(ZnqtParam znqtParam);

    /**
     * 修改智能墙体
     * @param znqtParam
     * @return
     */
    ApiReturnResult<String> updateZnqt(ZnqtParam znqtParam);

    /**
     * 打开智能墙体
     * @param baqid
     * @param qyid
     * @param sbbh
     * @return
     */
    ApiReturnResult<String> openZnqt(String baqid, String qyid, String sbbh);

    /**
     * 删除智能墙体
     * @param id
     * @return
     */
    ApiReturnResult<String> deleteZnqt(String id);

}
