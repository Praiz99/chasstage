package com.wckj.chasstage.api.server.imp.pcry;

import com.wckj.chasstage.api.def.pcry.model.PcryBean;
import com.wckj.chasstage.api.def.pcry.model.PcryParam;
import com.wckj.chasstage.api.def.pcry.service.ApiPcryService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.pcry.service.ChasYwPcryService;
import com.wckj.framework.api.ApiReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 盘查人员
 */
@Service
public class ApiPcryServiceImpl implements ApiPcryService {

    @Autowired
    private ChasYwPcryService pcryService;

    @Override
    public ApiReturnResult<?> getPageData(PcryParam param) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            DataQxbsUtil.getSelectAll(pcryService,params);
            params.put("baqid", null);
            Map<String, Object> objectMap = pcryService.selectAll(param.getPage(), param.getRows(), params, "");
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> saveOrUpdate(PcryBean bean) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = pcryService.saveOrupdate(bean);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage(map.get("msg").toString());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(map.get("msg").toString());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {


        ApiReturnResult apiReturnResult = new ApiReturnResult();
        if (StringUtil.isNotEmpty(ids)) {
            int num = pcryService.delete(ids.split(","));
            if (num == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage("删除成功");
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("删除失败");

            }
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("id为空!");
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getTaRyxx(String rybh, String ryxm) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = pcryService.getTaryxx(rybh, ryxm);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            apiReturnResult.setCode("200");
            apiReturnResult.setData(map.get("data"));
            apiReturnResult.setMessage(map.get("msg").toString());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(map.get("msg").toString());
        }
        return apiReturnResult;
    }


    @Override
    public ApiReturnResult<?> operRs(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = pcryService.ryrsxx(id);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            apiReturnResult.setCode("200");
            apiReturnResult.setData(map.get("data"));
            apiReturnResult.setMessage(map.get("msg").toString());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(map.get("msg").toString());
        }
        return apiReturnResult;
    }
}
