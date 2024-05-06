package com.wckj.chasstage.api.server.imp.gwzz;

import com.wckj.chasstage.api.def.gwzz.bean.GwzzBean;
import com.wckj.chasstage.api.def.gwzz.bean.GwzzParam;
import com.wckj.chasstage.api.def.gwzz.service.ApiBaqGwlcService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.gwzz.service.ChasXtGwlcService;
import com.wckj.framework.api.ApiReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wutl
 * @Title: 办案区岗位职责
 * @Package 办案区岗位职责
 * @Description: 办案区岗位职责
 * @date 2020-9-920:54
 */
@Service
public class ApiBaqGwlcServiceImp implements ApiBaqGwlcService {
    private static final Logger log = LoggerFactory.getLogger(ApiBaqGwlcServiceImp.class);

    @Autowired
    private ChasXtGwlcService gwlcService;

    /**
     * 获取岗位职业的分页数据
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getGwzzPageData(GwzzParam param) {
        //查询本单位办案区岗位流程
        Map<String,String> params = new HashMap<>();
        params.put("orgCode",param.getOrgCode());
        params.put("baqid",param.getBaqid());
        params.put("roleCode",param.getRoleCode());
        return ResultUtil.ReturnSuccess(gwlcService.getGwzzPageData(param.getPage(),param.getRows(),params,"xgsj desc"));
    }

    /**
     * 保存岗位职责信息
     * @param gwzzBean
     * @return
     */
    @Override
    public ApiReturnResult<?> saveGwlc(GwzzBean gwzzBean) {
        try{
            gwlcService.saveOrUpdate(gwzzBean);
            return ResultUtil.ReturnSuccess("操作成功!");
        }catch (Exception e){
            log.error("saveGwlc:",e);
            return ResultUtil.ReturnError("操作失败!");
        }
    }

    /**
     * 删除岗位职责根据ID
     * @param ids
     * @return
     */
    @Override
    public ApiReturnResult<?> deleteGwlc(String ids) {
        try{
            String[] idstr = ids.split(",");
            gwlcService.deleteGwlcs(idstr);
        }catch (Exception e){
            log.error("deleteGwlc:",e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }
}
