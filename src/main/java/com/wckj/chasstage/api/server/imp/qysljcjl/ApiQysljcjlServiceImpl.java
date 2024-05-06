package com.wckj.chasstage.api.server.imp.qysljcjl;

import com.wckj.chasstage.api.def.qygl.service.ApiQyglService;
import com.wckj.chasstage.api.def.qysljcjl.service.ApiQysljcjlService;
import com.wckj.chasstage.api.server.imp.sxsgl.ApiSxsglServiceImpl;
import com.wckj.chasstage.common.util.ParamUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qysljcjl.entity.ChasYwQysljcjl;
import com.wckj.chasstage.modules.qysljcjl.service.ChasYwQysljcjlService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiQysljcjlServiceImpl implements ApiQysljcjlService {
    protected Logger log = LoggerFactory.getLogger(ApiQysljcjlServiceImpl.class);

    @Autowired
    private ChasSxsglService sxsglService;
    @Autowired
    private ChasYwQysljcjlService qysljcjlService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private JdoneSysUserService userService;

    @Override
    public ApiReturnResult<?> findQyjcjl(HttpServletRequest request) {
        String qyid = request.getParameter("qyid");
        String qylx = request.getParameter("qylx");
        String empty = checkIsNotEmpty(request, "qyid", "qylx");
        if(StringUtils.isNotEmpty(empty)){
            return ResultUtil.ReturnError(empty);
        }
        String check = checkParameterValue(request, "qylx", "3", "4");
        if(StringUtils.isNotEmpty(check)){
            return ResultUtil.ReturnError(check);
        }
        Map<String, Object> params = ParamUtil.builder()
                .accept("qyid", qyid)
                .accept("qylx", qylx)
                .accept("zt","01")
                .toMap();
        List<ChasYwQysljcjl> list = qysljcjlService.findList(params, null);
        if(list.isEmpty()){
            return ResultUtil.ReturnSuccess("查询数据为空");
        }
        return ResultUtil.ReturnSuccess(list);
    }

    @Override
    public ApiReturnResult<?> saveQysljcjl(HttpServletRequest request) {
        String mjSfzh = request.getParameter("mjSfzh");  // 人员类型为民警时传入
        String rybh = request.getParameter("rybh");  // 人员类型为嫌疑人时传入
        String baqid = request.getParameter("baqid"); // 人员类型为民警时传入 办案区ID
        String zt = request.getParameter("zt");  // 01 进入  02 离去
        String rylx = request.getParameter("rylx");  // 1 嫌疑人  2 民警
        String qylx = request.getParameter("qylx");  // 3 审讯室  4 等候室
        String qyid = request.getParameter("qyid");  // 区域ID
        String qymc = request.getParameter("qymc");  // 区域名称
        String khdip = request.getParameter("khdip");  // 客户端IP

        // 校验所有参数非空
        String empty = checkIsNotEmpty(request, "zt", "rylx", "qylx", "qyid", "qymc", "khdip");
        if(StringUtils.isNotEmpty(empty)){
            return ResultUtil.ReturnError(empty);
        }
        empty = checkIsNotEmpty2(request, "mjSfzh", "rybh");
        if(StringUtils.isNotEmpty(empty)){
            return ResultUtil.ReturnError(empty);
        }

        // 校验所有参数的合法性
        String check = checkParameterValue(request, "zt", "01", "02");
        if(StringUtils.isNotEmpty(check)){
            return ResultUtil.ReturnError(check);
        }
        check = checkParameterValue(request, "rylx", "1", "2");
        if(StringUtils.isNotEmpty(check)){
            return ResultUtil.ReturnError(check);
        }
        check = checkParameterValue(request, "qylx", "3", "4");
        if(StringUtils.isNotEmpty(check)){
            return ResultUtil.ReturnError(check);
        }

        // 校验参数的业务关联性
        if(StringUtils.equals(rylx,"1") && StringUtils.isEmpty(rybh)){
            return ResultUtil.ReturnError("人员类型关联的rybh参数值不能为空");
        }
        if(StringUtils.equals(rylx,"2") && (StringUtils.isEmpty(baqid) || StringUtils.isEmpty(mjSfzh))){
            return ResultUtil.ReturnError("人员类型关联的baqid,mjSfzh参数值不能为空");
        }

        try {
            ChasYwQysljcjl qysljcjl = new ChasYwQysljcjl();
            qysljcjl.setId(StringUtils.getGuid32Old());
            qysljcjl.setIsdel((short) 0);
            qysljcjl.setLrsj(new Date());
            qysljcjl.setXgsj(new Date());
            if(StringUtils.equals(rylx,"1")){
                // 嫌疑人
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                if(baqryxx == null){
                    return ResultUtil.ReturnError("根据rybh查询不到人员信息");
                }
                // 业务幂等校验
                List<ChasYwQysljcjl> list = qysljcjlService.findList(ParamUtil.builder()
                        .accept("sfzh", baqryxx.getRysfzh())
                        .accept("zt", "01")
                        .accept("qyid", qyid)
                        .toMap(), null);
                if(!list.isEmpty()){
                    ChasYwQysljcjl qysljcjl1 = list.get(0);
                    return ResultUtil.ReturnError(String.format("嫌疑人%s，已进入%s",qysljcjl1.getRyxm(),qysljcjl1.getQymc()));
                }
                qysljcjl.setLrrSfzh(baqryxx.getLrrSfzh());
                qysljcjl.setXgrSfzh(baqryxx.getTjxgrsfzh());
                qysljcjl.setBaqid(baqryxx.getBaqid());
                qysljcjl.setBaqmc(baqryxx.getBaqmc());
                qysljcjl.setDwdm(baqryxx.getZbdwBh());
                qysljcjl.setDwxtbh(baqryxx.getDwxtbh());
                qysljcjl.setRyxm(baqryxx.getRyxm());
                qysljcjl.setRybh(baqryxx.getRybh());
                qysljcjl.setSfzh(baqryxx.getRysfzh());
                qysljcjl.setCzr(baqryxx.getMjXm());
            }
            if(StringUtils.equals(rylx,"2")){
                // 民警
                JdoneSysUser sysUserByIdCard = userService.findSysUserByIdCard(mjSfzh);
                if(sysUserByIdCard == null){
                    return ResultUtil.ReturnError("根据mjSfzh查询不到用户信息");
                }
                ChasBaq baq = baqService.findById(baqid);
                if(baq == null){
                    return ResultUtil.ReturnError("根据baqid查询不到办案区信息");
                }
                // 业务幂等校验
                List<ChasYwQysljcjl> list = qysljcjlService.findList(ParamUtil.builder()
                        .accept("sfzh", sysUserByIdCard.getIdCard())
                        .accept("zt", "01")
                        .accept("qyid", qyid)
                        .toMap(), null);
                if(!list.isEmpty()){
                    ChasYwQysljcjl qysljcjl1 = list.get(0);
                    return ResultUtil.ReturnError(String.format("民警%s，已进入%s",qysljcjl1.getRyxm(),qysljcjl1.getQymc()));
                }
                qysljcjl.setBaqid(baq.getId());
                qysljcjl.setBaqmc(baq.getBaqmc());
                qysljcjl.setDwdm(baq.getDwdm());
                qysljcjl.setDwxtbh(baq.getDwxtbh());
                qysljcjl.setCzr(sysUserByIdCard.getName());
                qysljcjl.setLrrSfzh(mjSfzh);
                qysljcjl.setXgrSfzh(mjSfzh);
                qysljcjl.setRyxm(sysUserByIdCard.getName());
                qysljcjl.setSfzh(sysUserByIdCard.getIdCard());
            }
            qysljcjl.setZt(zt);
            qysljcjl.setRylx(rylx);
            qysljcjl.setQylx(qylx);
            qysljcjl.setQyid(qyid);
            qysljcjl.setQymc(qymc);
            qysljcjl.setCzsj(new Date());
            qysljcjl.setJrsj(new Date());
            //qysljcjl.setLksj(new Date());
            qysljcjl.setKhdip(khdip);
            qysljcjlService.save(qysljcjl);
        } catch (Exception e) {
            log.error("saveQysljcjl:",e);
            return ResultUtil.ReturnError("保存失败");
        }
        return ResultUtil.ReturnSuccess("保存成功");
    }

    /**
     * 校验所有参数都不能为空
     * @param request
     * @param keys
     * @return
     */
    public String checkIsNotEmpty(HttpServletRequest request,String... keys){
        String result = "";
        for (String key : keys) {
            String parameter = request.getParameter(key);
            if(StringUtils.isEmpty(parameter)){
                result += key+",";
            }
        }
        return StringUtils.isEmpty(result) ? "" : result.substring(0,result.length()-1)+"不能为空";
    }
    /**
     * 二选一，有一个不为空即可
     * @param request
     * @param keys
     * @return
     */
    public String checkIsNotEmpty2(HttpServletRequest request,String... keys){
        String result = "";
        boolean greenLight = false;
        for (String key : keys) {
            String parameter = request.getParameter(key);
            if(StringUtils.isEmpty(parameter)){
                result += key+",";
            }else{
                greenLight = true;
            }
        }
        if(greenLight){
            return "";
        }
        return result.substring(0,result.length()-1)+"不能为空";
    }

    /**
     * 校验所有参数值是否符合给定的值
     * @param request
     * @param key
     * @param values
     * @return
     */
    public String checkParameterValue(HttpServletRequest request,String key,String... values){
        String parameter = request.getParameter(key);
        if(StringUtils.isEmpty(parameter)){
            return "";
        }
        boolean greenLight = false;
        for (String value : values) {
            if(StringUtils.equals(value,parameter)){
                greenLight = true;
            }
        }
        return greenLight ? "" : key+"参数值未知";
    }
}
