package com.wckj.chasstage.api.server.imp.ythcj;

import com.wckj.chasstage.api.def.ythcj.model.YthcjParam;
import com.wckj.chasstage.api.def.ythcj.service.ApiYthcjService;
import com.wckj.chasstage.api.server.imp.yjxx.ApiYjxxServiceImpl;
import com.wckj.chasstage.common.util.JsonUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ParamUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjShgx;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjWlxx;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjService;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjShgxService;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjWlxxService;
import com.wckj.framework.api.ApiReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiYthcjServiceImpl implements ApiYthcjService {
    private static final Logger log = LoggerFactory.getLogger(ApiYthcjServiceImpl.class);

    @Autowired
    private ChasYthcjService ythcjService;
    @Autowired
    private ChasYthcjShgxService shgxService;
    @Autowired
    private ChasYthcjWlxxService wlxxService;

    @Override
    public ApiReturnResult<String> saveYthcjRyxx(ChasYthcj ythcj,YthcjParam param) {
        try {
            Map<String,Object> result = new HashMap<>();
            result = ythcjService.saveYthcjRyxx(ythcj,param.getNetJson(),param.getSocietyJson(),param.getDelNetIds(),param.getDelSocietyIds(),param.getFromSign());
            if ((boolean)result.get("success")) {
                return ResultUtil.ReturnSuccess((String)result.get("msg"));
            }else{
                return ResultUtil.ReturnError((String)result.get("msg"));
            }
        } catch (Exception e) {
            log.error("saveYthcjRyxx",e);
            return ResultUtil.ReturnError("系统异常:"+e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getYthcjRyxx(String rybh) {
        List<ChasYthcj> ythcjList = ythcjService.findList(ParamUtil.builder().accept("rybh",rybh).toMap(),null);
        if(ythcjList.isEmpty()){
            return ResultUtil.ReturnSuccess("暂无数据!");
        }
        Map<String,Object> result = new HashMap<>();
        ChasYthcj ythcj = ythcjList.get(0);
        List<ChasYthcjShgx> shgxes = shgxService.findList(ParamUtil.builder().accept("rybh",rybh).toMap()," lrsj");
        result.put("shgxs",shgxes);
        List<ChasYthcjWlxx> wlxxes = wlxxService.findList(ParamUtil.builder().accept("rybh",rybh).toMap()," lrsj");
        result.put("wlxxs",wlxxes);
        result.put("ythcj",ythcj);
        return ResultUtil.ReturnSuccess("请求成功!",result);
    }
}
