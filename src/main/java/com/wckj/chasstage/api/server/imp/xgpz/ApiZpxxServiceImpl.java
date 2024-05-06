package com.wckj.chasstage.api.server.imp.xgpz;

import com.wckj.chasstage.api.def.zpxx.model.ZpxxParam;
import com.wckj.chasstage.api.def.zpxx.sercive.ApiZpxxService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.api.ApiReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 巡更配置
 */
@Service
public class ApiZpxxServiceImpl implements ApiZpxxService {

    @Autowired
    private ChasSswpZpxxService zpxxService;
    @Override
    public ApiReturnResult<?> zpxxSave(ZpxxParam param) {
        DevResult result = zpxxService.saveZpxxWithFile(param);

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        if (result.getCode() == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage(result.getMessage());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(result.getMessage());
        }
        return apiReturnResult;
    }
}
