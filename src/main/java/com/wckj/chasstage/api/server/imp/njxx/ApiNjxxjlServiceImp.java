package com.wckj.chasstage.api.server.imp.njxx;

import com.wckj.chasstage.api.def.njxx.bean.NjxxjlBean;
import com.wckj.chasstage.api.def.njxx.bean.NjxxjlParam;
import com.wckj.chasstage.api.def.njxx.service.ApiNjxxjlService;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.oConvertUtils;
import com.wckj.chasstage.modules.njjl.entity.ChasYwNjRecord;
import com.wckj.chasstage.modules.njjl.service.ChasYwNjRecordService;
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
public class ApiNjxxjlServiceImp implements ApiNjxxjlService {
    @Autowired
    private ChasYwNjRecordService recordService;

    @Override
    public ApiReturnResult<String> getPageData(NjxxjlParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            Map<String, Object> objectMap = recordService.selectAll(param.getPage(), param.getRows(), params, "");
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
    public ApiReturnResult<String> findNjjlById(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            ChasYwNjRecord record = recordService.findNjjl(id);
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



    @Override
    public ApiReturnResult<String> saveOrUpdate(NjxxjlBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        try {
            ChasYwNjRecord nj = new ChasYwNjRecord();
            MyBeanUtils.copyBeanNotNull2Bean(bean, nj);
            nj.setId(StringUtils.getGuid32());

            int num = recordService.saveNjjl(nj);
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


}
