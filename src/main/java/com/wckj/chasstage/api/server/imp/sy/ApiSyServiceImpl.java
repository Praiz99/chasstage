package com.wckj.chasstage.api.server.imp.sy;

import com.wckj.chasstage.api.def.sy.service.ApiSyService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.dao.ChasBaqMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import com.wckj.jdone.modules.rmd.service.JdoneRmdMsgService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiSyServiceImpl implements ApiSyService {

    @Autowired
    private ChasBaqMapper mapper;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private JdoneRmdMsgService jdoneRmdMsgService;

    @Override
    public ApiReturnResult<?> getStatisticsDataByBaqid(String baqid) {
        SessionUser sessionUser = WebContext.getSessionUser();
        if (StringUtils.isEmpty(baqid)) {
            ApiReturnResult<?> apiReturnResult = new ApiReturnResult<>();
            String orgCode = sessionUser.getCurrentOrgCode();
            Map<String, Object> param = new HashMap<>();
            param.put("sydwdm", orgCode);
            List<ChasBaq> baqList = baqService.findListByParams(param);
            ChasBaq byParams = null;
            if (baqList.size() > 0) {
                byParams = baqList.get(0);
                baqid = byParams.getId();
            }
            if (byParams == null) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("当前登录人单位，未配置办案区！");
                return apiReturnResult;
            }
        }

        JdoneRmdMsg rmdMsg = new JdoneRmdMsg();
        long baqry = 0,qqyz = 0;
        try{
            //办案区人员
            Map<String,Object> params = new HashMap<>();
            rmdMsg.setMsgType("dzxbaqry");
            rmdMsg.setBizType("dzxbaqryAct");
            rmdMsg.setRecObjMark(sessionUser.getIdCard());
            rmdMsg.setIsNotRmd(0);
            params = JsonUtil.jsonStringToMap(JsonUtil.getJsonString(rmdMsg));
            params.put("recObjRoleMark", sessionUser.getRoleCode());
            PageDataResultSet<JdoneRmdMsg> list = jdoneRmdMsgService.getRmgDataByParamMap(1, 10, params, "CREATE_TIME DESC");
            baqry = list.getTotal();


            //亲情驿站
            params.clear();
            rmdMsg = new JdoneRmdMsg();
            rmdMsg.setMsgType("baqqqyz");
            rmdMsg.setBizType("baqqqyzAct");
            rmdMsg.setRecObjMark(sessionUser.getIdCard());
            rmdMsg.setIsNotRmd(0);
            params = JsonUtil.jsonStringToMap(JsonUtil.getJsonString(rmdMsg));
            params.put("recObjRoleMark", sessionUser.getRoleCode());
            list = jdoneRmdMsgService.getRmgDataByParamMap(1, 10, params, "CREATE_TIME DESC");
            qqyz = list.getTotal();
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String, String> result = mapper.getStatisticsBy(baqid);
        result.put("dsp",baqry+"");
        result.put("qqyz",qqyz+"");
        return ResultUtil.ReturnSuccess("操作成功!", result);
    }
}
