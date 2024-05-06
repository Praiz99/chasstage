package com.wckj.chasstage.api.server.imp.qqdh;

import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.def.qqdh.model.QqdhParam;
import com.wckj.chasstage.api.def.qqdh.service.ApiQqdhService;
import com.wckj.chasstage.api.server.imp.baqry.ApiBaqryServiceImpl;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.act2.service.ProcessEngine2Service;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 亲情电话
 */
@Service
public class ApiQqdhServiceImpl implements ApiQqdhService {
    protected Logger log = LoggerFactory.getLogger(ApiQqdhServiceImpl.class);

    @Autowired
    private ChasQqyzService qqyzService;
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ProcessEngine2Service processEngineService;

    @Override
    public ApiReturnResult<?> getPageData(QqdhParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            DataQxbsUtil.getSelectAll(qqyzService, params);
            if(WebContext.getSessionUser()!=null){
                String userRoleId = WebContext.getSessionUser().getRoleCode();
                if (!"0101".equals(userRoleId)) {
                    String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                    params.put("sydwdm", orgCode);
                }
            }
            Map<String, Object> objectMap = qqyzService.selectAll(param.getPage(), param.getRows(), params, "");
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
    public ApiReturnResult<?> qqdhtqly(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        try {
            DevResult result = qqyzService.qqdhTqly(id);
            if (result.getCode() == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setData(result.getData());
                apiReturnResult.setMessage(result.getMessage());
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(result.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> save(QqdhBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        try {
            ChasQqyz qqyz = new ChasQqyz();
            MyBeanUtils.copyBeanNotNull2Bean(bean, qqyz);
            DevResult result = qqyzService.saveOrUpdate(qqyz,false);
            if (result.getCode() == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage(result.getMessage());
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(result.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> update(QqdhBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        try {
            ChasQqyz qqyz = new ChasQqyz();
            MyBeanUtils.copyBeanNotNull2Bean(bean, qqyz);
            DevResult result = qqyzService.saveOrUpdate(qqyz,false);
            if (result.getCode() == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage(result.getMessage());
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(result.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        if (StringUtil.isNotEmpty(ids)) {
            int num = qqyzService.deleteByqqid(ids);
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
    public ApiReturnResult<?> valideApprove(String ryid) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        if (StringUtil.isNotEmpty(ryid)) {
            Map<String, Object> map = qqyzService.valideApprove(ryid);
            boolean flag = Boolean.valueOf(map.get("success").toString());
            if (flag) {
                apiReturnResult.setCode("200");
                apiReturnResult.setData(map);
                apiReturnResult.setMessage("校验成功");
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("校验失败");

            }
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("参数为空!");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> open(String ryid, String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        try {
            ChasBaqryxx ryxx = baqryxxService.findById(ryid);
            DevResult result = qqyzService.openQqdhById(ryxx.getBaqid(), ryxx.getRybh(), id);
            if (result.getCode() == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage(result.getMessage());
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(result.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> close(String ryid, String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        try {
            ChasBaqryxx ryxx = baqryxxService.findById(ryid);
            DevResult result = qqyzService.closeQqdhById(ryxx.getBaqid(), ryxx.getRybh(), id);
            if (result.getCode() == 1) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage(result.getMessage());
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(result.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> startProcess(NodeProcessCmdObj2 cmdObj) {
        try {
            Map<String,Object> map = cmdObj.getBizFormData();
            String ryid = (String)map.get("ryid");
            String lxry = (String) map.get("lxr");
            String sjhm = (String) map.get("lxrdh");
            //String zbmj = (String) map.get("zbmj");
            //String zbmjsfzh = (String) map.get("zbmjsfzh");
            String thsj = (String) map.get("thsj");
            String sqyy = (String) map.get("sqyy");

            SessionUser user = WebContext.getSessionUser();

            ChasBaqryxx baqryxx = chasBaqryxxService.findById(ryid);

            //设置关联业务数据
            Map<String,Object> json = new HashMap<>();
            json.put("ryxm",baqryxx.getRyxm());
            map.put("glywData", com.wckj.framework.json.jackson.JsonUtil.getJsonString(json));

            ChasQqyz qqyz = new ChasQqyz();
            qqyz.setId(cmdObj.getBizId());
            qqyz.setDataflag("");
            if(baqryxx != null){
                qqyz.setBaqid(baqryxx.getBaqid());
                qqyz.setBaqmc(baqryxx.getBaqmc());
                qqyz.setRybh(baqryxx.getRybh());
                qqyz.setRyid(baqryxx.getId());
            }
            qqyz.setZt(0);
            qqyz.setLrrSfzh(user.getIdCard());
            qqyz.setLrsj(new Date());
            qqyz.setXgrSfzh(user.getIdCard());
            qqyz.setXgsj(new Date());
            qqyz.setDwxtbh(user.getCurrentOrgSysCode());
            qqyz.setIsdel(0);
            qqyz.setLxr(lxry);
            qqyz.setThsj(Integer.valueOf(thsj));
            qqyz.setLxrdh(sjhm);
            qqyz.setSqyy(sqyy);
            qqyz.setSqrxm(baqryxx.getRyxm());
            qqyz.setSqsj(new Date());
            qqyz.setXb(baqryxx.getXb());
            qqyz.setZbdwbh(user.getCurrentOrgCode());
            qqyz.setZbdwmc(user.getCurrentOrgName());

            DevResult result = qqyzService.saveOrUpdate(qqyz,true);
            if(result.getCode() == 1){
                cmdObj.setCurrentProUser(WebContext.getSessionUser());
                cmdObj.setDxmc(baqryxx.getRyxm());
                cmdObj.setDxbh(baqryxx.getRybh());
                processEngineService.startProcess(cmdObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("startProcess:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("提交审核成功!");
    }

    @Override
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj) {
        try {
            cmdObj.setCurrentProUser(WebContext.getSessionUser());
            processEngineService.completeTask(cmdObj);
        } catch (Exception e) {
            log.error("executeProcess:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("审批成功!");
    }
}
