package com.wckj.chasstage.api.server.imp.shsng;

import com.wckj.chasstage.api.def.shsng.model.ShsngParam;
import com.wckj.chasstage.api.def.shsng.service.ApiShsngService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.shsng.service.ChasShsngService;
import com.wckj.framework.api.ApiReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author:zengrk
 */
@Service
public class ApiShsngServiceImp implements ApiShsngService {
    private static final Logger log = LoggerFactory.getLogger(ApiShsngServiceImp.class);
    @Autowired
    private ChasShsngService chasShsngService;


    @Override
    public ApiReturnResult<String> getShsngPageData(ShsngParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            DataQxbsUtil.getSelectAll(chasShsngService, params);
            chasShsngService.getShxkDatas((String) params.get("baqid"));
            Map<String, Object> objectMap = chasShsngService.selectAll(param.getPage(), param.getRows(), params, "xgsj desc");
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
    public ApiReturnResult<String> getSngNum() {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Integer zgNum = chasShsngService.selectZg();
            apiReturnResult.setData(zgNum);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        }catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> yjcl() {
        try {
            chasShsngService.yjcl();
            return ResultUtil.ReturnSuccess("取出成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e + "取出失败");
        }
        return ResultUtil.ReturnError("取出失败");
    }

    @Override
    public ApiReturnResult<String> sdgh(String id) {
        try {
            Map<String, Object> map = chasShsngService.sdgh(id);
            boolean flag = Boolean.valueOf(map.get("success").toString());
            if(flag){
                return ResultUtil.ReturnSuccess("归还成功");
            }else {
                String errMessage = map.get("message").toString();
                return ResultUtil.ReturnError(errMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e + "归还失败");
        }
        return ResultUtil.ReturnError("归还失败");
    }
}
