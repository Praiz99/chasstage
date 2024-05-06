package com.wckj.chasstage.api.server.imp.spjl;

import com.wckj.chasstage.api.def.spjl.model.SpjlParam;
import com.wckj.chasstage.api.def.spjl.service.ApiSpjlService;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act2.service.JdoneAct2InstanceService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:zengrk
 */
@Service
public class ApiSpjlServiceImp implements ApiSpjlService {
    @Autowired
    private JdoneAct2InstanceService jdoneAct2InstanceService;

    @Override
    public ApiReturnResult<String> getSpjlPageData(SpjlParam spjlParam) {
        SessionUser user = WebContext.getSessionUser();
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        Map<String,Object> result=new HashMap<>();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(spjlParam);
            if(StringUtils.isEmpty(spjlParam.getFqkssj())){
                params.remove("fqkssj");
            }else{
                params.put("fqkssj", DateUtil.parseDate(spjlParam.getFqkssj()));
            }
            if(StringUtils.isEmpty(spjlParam.getFqjssj())){
                params.remove("fqjssj");
            }else{
                params.put("fqjssj", DateUtil.parseDate(spjlParam.getFqjssj()));
            }
            if(StringUtils.isEmpty(spjlParam.getSpkssj())){
                params.remove("spkssj");
            }else{
                params.put("spkssj", DateUtil.parseDate(spjlParam.getSpkssj()));
            }
            if(StringUtils.isEmpty(spjlParam.getSpjssj())){
                params.remove("spjssj");
            }else{
                params.put("spjssj", DateUtil.parseDate(spjlParam.getSpjssj()));
            }
            if(StringUtils.isEmpty(spjlParam.getBizType())){
                params.put("bizTypeList",new String[]{"dzxbaqryAct","baqqqyzAct"});
            }
            if(StringUtils.isEmpty(spjlParam.getFqdw())){
                String rangeType = SysUtil.getDataRangeType("JdoneAct2SelectAll","");
                params.put("qxbs", rangeType);
                params.put("sysCode", user.getOrgSysCode());
            }
            PageDataResultSet<Map<String, Object>> pageDataResultSet = jdoneAct2InstanceService.getHistoryDataByParam(spjlParam.getPage(),spjlParam.getRows(),params,"start_time desc");
            result.put("rows",
                    DicUtil.translate(pageDataResultSet.getData(),
                            new String[]{"CHASSTAGE_SPYWLX","ZD_SYS_ORG_SYSCODE_SNAME","ZD_SYS_USER_ID","ZD_SYS_USER_ID","ZD_SYS_ORG_SYSCODE_SNAME","ZD_SYS_USER_ID"},
                            new String[]{"biz_type","start_org_sys_code","start_user_id","complete_user_id","complete_org_sys_code","node_pro_user_id"}));
            result.put("total",pageDataResultSet.getTotal());
            apiReturnResult.setData(result);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }
}
