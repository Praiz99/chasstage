package com.wckj.chasstage.api.server.imp.sys;

import com.wckj.chasstage.api.def.sys.model.*;
import com.wckj.chasstage.api.def.sys.service.ApiJdoneSysService;
import com.wckj.chasstage.common.util.ParamUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiJdoneSysServiceImp implements ApiJdoneSysService {

    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private JdoneSysOrgService jdoneSysOrgService;
    @Override
    public ApiReturnResult<?> getMjxxPageData(HttpServletRequest request, MjxxParam param) {
        Map<String,Object> params = new HashMap<>();
        params.put("name",param.getName());
        params.put("orgName",param.getDwmc());
        params.put("orgSysCode",param.getSysCode());
        params.put("idCard",param.getSfzh());
        params.put("loginId",param.getJh());


        PageDataResultSet<JdoneSysUser> pg=new PageDataResultSet<>();

        if(StringUtil.isNotEmpty(param.getOrgCode1())){
            List<JdoneSysUser> jdoneSysUsers=new ArrayList<>();
            Long total=0L;
            Integer Rows=0;
            String[] orgCodeArr = param.getOrgCodes().split(",");
            for (String orgCode:orgCodeArr){
                if(orgCode.contains(param.getOrgCode1())){
                    params.put("orgCode",orgCode);
                    PageDataResultSet<JdoneSysUser> data = userService.getEntityPageData(param.getPage(), param.getRows(), params, null);
                    jdoneSysUsers.addAll(data.getData());
                    total+=data.getTotal();
                }
            }
            pg.setData(jdoneSysUsers);
            pg.setTotal(total);
        }
        else {
            params.put("orgCode",param.getOrgCode());
            pg=userService.getEntityPageData(param.getPage(), param.getRows(), params, null);
        }
//        PageDataResultSet<JdoneSysUser> pg = userService.getEntityPageData(param.getPage(),param.getRows(),params,null);
//        if(StringUtil.isNotEmpty(param.getOrgCode1())){
//            pg.setData(pg.getData().stream().filter(e->e.getOrgCode().contains(param.getOrgCode1())).collect(Collectors.toList()));
//        }

        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(pg.getTotal(),pg.getData()));
    }

    @Override
    public ApiReturnResult<?> getOrgPageData(HttpServletRequest request, OrgParam orgParam) {
        Map<String,Object> params = new HashMap<>();
        params.put("code",orgParam.getOrgCode());
        params.put("sysCode",orgParam.getSysCode());
        PageDataResultSet<JdoneSysOrg> pg = orgService.getPageDataByMap(orgParam.getPage(),orgParam.getRows(),params,null);
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(pg.getTotal(),pg.getData()));
    }

}
