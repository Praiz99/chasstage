package com.wckj.chasstage.modules.gwzz.service;

import com.wckj.chasstage.api.def.gwzz.bean.GwzzBean;
import com.wckj.chasstage.modules.gwzz.dao.ChasXtGwlcMapper;
import com.wckj.chasstage.modules.gwzz.entity.ChasXtGwlc;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

/**
 * @author HuangBo
 * @Description 岗位流程
 * @create 2019-07-26 15:28
 */
public interface ChasXtGwlcService extends IBaseService<ChasXtGwlcMapper, ChasXtGwlc> {

    /**
     * 新增或保存
     * @param gwlc 数据信息
     * @return 处理结果
     */
    void saveOrUpdate(ChasXtGwlc gwlc) throws Exception;

    void saveOrUpdate(GwzzBean gwlc) throws Exception;

    Map<?,?> getGwzzPageData(int page,int rows,Map<String,String> params,String order);

    /**
     * 批量删除数据
     * @param ids 待删除ids
     * @return 操作结果
     */
    void deleteGwlcs(String[] ids) throws Exception;

    DutiesManager getDuties(String baqid);
}
