package com.wckj.chasstage.api.server.imp.dhssyjl;

import com.wckj.chasstage.api.def.dhssyjl.model.DhssyjlParam;
import com.wckj.chasstage.api.def.dhssyjl.service.ApiDhssyjService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.dhssyjl.entity.ChasYwDhssyjl;
import com.wckj.chasstage.modules.dhssyjl.service.ChasDhsSyjlService;
import com.wckj.chasstage.modules.sxsgbjl.entity.ChasSxsGbjl;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:zengrk
 */
@Service
public class ApiDhsyjlServiceImpl  implements ApiDhssyjService {
    @Autowired
    private ChasDhsSyjlService apiSerivce;
    @Override
    public ApiReturnResult<?> getDhssyjlPageData(DhssyjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        if(param.getPage()==null){
            param.setPage(1);
        }
        if(param.getRows()==null){
            param.setRows(10);
        }
        DataQxbsUtil.getSelectAll(apiSerivce, map);
        PageDataResultSet<ChasYwDhssyjl> pageData = apiSerivce.getEntityPageData(param.getPage(),
                param.getRows(), map, "kssj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows",  DicUtil.translate(pageData.getData(),new String[]{"GNSSYRYLX"},new String[]{"rylx"}));
        return ResultUtil.ReturnSuccess(result);
    }
}
