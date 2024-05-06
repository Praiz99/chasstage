package com.wckj.chasstage.api.server.imp.qygl;

import com.wckj.chasstage.api.def.qygl.model.QyglBean;
import com.wckj.chasstage.api.def.qygl.service.ApiQyglService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.framework.api.ApiReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName : ApiSbglService  //类名
 * @Description : 设备管理接口  //描述
 * @Author : lcm  //作者
 * @Date: 2020-09-08 16:19  //时间
 */
@Service
public class ApiQyglServiceImp implements ApiQyglService {
    @Autowired
    private ChasXtQyService qyService;


    @Override
    public ApiReturnResult<String> getQyglApiPageData(QyglBean param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            Map<String, Object> objectMap = qyService.selectAll(param.getPage(), param.getRows(), params, "");
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("区域查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> tbBaqqy(String baqId) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> data = qyService.tbBaqQy(baqId);
        try {
            apiReturnResult.setData(data);
            apiReturnResult.setCode("200");

        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> deleteBaqByIds(String Ids) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        int n = qyService.delete(Ids.split(","));
        try {
            if (n != 0) {
                apiReturnResult.setCode("200");

            } else {
                apiReturnResult.setCode("500");
            }
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getQyDicByBaq(String baqid,String queryValue,String fjlx) {
        DevResult result = qyService.getQyDicByBaq(baqid, fjlx, queryValue, -1, -1);
        if (result.getCode() == 1) {
            return ResultUtil.ReturnSuccess("获取数据成功", result.getData());
        } else {
            return ResultUtil.ReturnError(result.getMessage());
        }
    }
}
