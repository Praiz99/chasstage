package com.wckj.chasstage.api.server.imp.njxx;

import com.wckj.chasstage.api.def.njxx.bean.NjxxBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxParam;
import com.wckj.chasstage.api.def.njxx.service.ApiNjxxService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.njxx.entity.ChasYwNj;
import com.wckj.chasstage.modules.njxx.service.ChasYwNjService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wutl
 * @Title: 尿检信息服务
 * @Package 尿检信息服务
 * @Description: 尿检信息服务
 * @date 2020-9-1617:02
 */
@Service
public class ApiNjxxServiceImp implements ApiNjxxService {
    @Autowired
    private ChasYwNjService njService;

    @Override
    public ApiReturnResult<String> getPageData(NjxxParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            DataQxbsUtil.getSelectAll(njService,params);
            Map<String, Object> objectMap = njService.selectAll(param.getPage(), param.getRows(), params, "");
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
    public ApiReturnResult<String> saveOrUpdate(NjxxBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        try {
            ChasYwNj nj = new ChasYwNj();
            nj.setId(StringUtils.getGuid32());

            int num = njService.saveNjxx(nj);
            if (num == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage("保存成功");
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("保存失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> deletes(String ids) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        if (StringUtil.isNotEmpty(ids)) {
            int num = njService.deleteByIds(ids);
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
    public ApiReturnResult<?> getNjPdfBase64(String njid, String type) {
        Map<String, Object> res = njService.getNjPdfBase64(njid, type);
        boolean flag = Boolean.valueOf(res.get("success").toString());
        if (flag) {
            return ResultUtil.ReturnSuccess("操作成功!", res.get("data"));
        } else {
            return ResultUtil.ReturnError("pdf数据获取失败");
        }

    }

    @Override
    public ApiReturnResult<String> findNjxxId(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            ChasYwNj record = njService.findNj(id);
            if (oConvertUtils.isNotEmpty(record)) {
                apiReturnResult.setData(record);
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage("查询数据成功");
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("数据为空");
            }

        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }
}
