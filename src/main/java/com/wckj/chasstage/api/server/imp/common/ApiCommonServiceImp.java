package com.wckj.chasstage.api.server.imp.common;

import com.wckj.chasstage.api.def.common.model.FileInfo;
import com.wckj.chasstage.api.def.common.model.ProcessParam;
import com.wckj.chasstage.api.def.common.model.WlsxtBean;
import com.wckj.chasstage.api.def.common.service.ApiCommonService;
import com.wckj.chasstage.api.server.imp.qysljcjl.ApiQysljcjlServiceImpl;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUserRole;
import com.wckj.jdone.modules.sys.service.JdoneSysUserRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 公共服务
 * @Package 公共服务
 * @Description: 公共服务
 * @date 2020-9-2314:34
 */
@Service
public class ApiCommonServiceImp implements ApiCommonService {
    protected Logger log = LoggerFactory.getLogger(ApiQysljcjlServiceImpl.class);

    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasSswpxxService sswpxxService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysUserRoleService userRoleService;

    /**
     * 根据文件bizId和bizType获取文件信息
     * @param bizId
     * @param bizType
     * @return
     */
    @Override
    public ApiReturnResult<List<FileInfo>> getZpwjInfo(String bizId, String bizType) {
        ApiReturnResult<List<FileInfo>> result = new ApiReturnResult<>();
        try {
            List<FileInfo> list = new ArrayList<>();
            List<FileInfoObj> fileInfoList = null;
            if(StringUtils.isNotEmpty(bizType)&&!"null".equals(bizType)){
                fileInfoList = FrwsApiForThirdPart.getFileInfoListByBizIdBizType(bizId, bizType);
            }else{
                fileInfoList  = FrwsApiForThirdPart.getFileInfoList(bizId);
            }
            if(fileInfoList != null){
                for (int i = fileInfoList.size()-1; i >-1; i--) {
                    FileInfoObj fileInfoObj = fileInfoList.get(i);
                    FileInfo fileInfo = new FileInfo();
                    MyBeanUtils.copyBeanNotNull2Bean(fileInfoObj,fileInfo);
                    list.add(fileInfo);
                }
            }
            result.setCode("200");
            result.setData(list);
            result.setMessage("获取成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 根据办案区id和功能类型，角色为当前登录人
     * @param baqid
     * @param funType
     * @return
     */
    @Override
    public ApiReturnResult<?> getWlsxtxxByFunType(String baqid, String funType) {
        if(StringUtils.isEmpty(baqid)||StringUtils.isEmpty(funType)){
            return ResultUtil.ReturnError("参数错误");
        }
        SessionUser sessionUser = WebContext.getSessionUser();
        if(sessionUser==null){
            return ResultUtil.ReturnError("未登陆");
        }
        String roleCode = sessionUser.getRoleCode();
        if(StringUtils.isEmpty(roleCode)){
            return ResultUtil.ReturnError("当前用户未关联角色");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", SYSCONSTANT.SBLX_SXT);
        map.put("sbgn", funType);
        map.put("kzcs6",roleCode);
        List<ChasSb> sbList = chasSbService.findByParams(map);
        if(sbList==null||sbList.isEmpty()){
            return ResultUtil.ReturnError("当前用户角色还未配置摄像头");
        }
        WlsxtBean bean = new WlsxtBean();
        bean.setSbbh(sbList.get(0).getSbbh());
        bean.setIp(sbList.get(0).getKzcs2());
        bean.setPort(sbList.get(0).getKzcs3());
        if(StringUtils.isNotEmpty(sbList.get(0).getKzcs4())){
            int index = sbList.get(0).getKzcs4().indexOf(":");
            if(index>-1){
                bean.setUserName(sbList.get(0).getKzcs4().substring(0, index));
                bean.setPassword(sbList.get(0).getKzcs4().substring(index+1));
            }
        }
        bean.setChannel(sbList.get(0).getKzcs5());
        return ResultUtil.ReturnSuccess(bean);
    }

    /**
     * 根据客户端Ip查询绑定的摄像头
     *
     * @param baqid
     * @param funType
     * @param clientIp
     * @return
     */
    @Override
    public ApiReturnResult<?> getWlsxtxxByFunTypeClientIp(String baqid, String funType, String clientIp) {
        if(StringUtils.isEmpty(baqid)||StringUtils.isEmpty(funType)){
            return ResultUtil.ReturnError("参数错误");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", SYSCONSTANT.SBLX_SXT);
        map.put("sbgn", funType);
        map.put("kzcs6Like",clientIp);
        List<ChasSb> sbList = chasSbService.findByParams(map);
        if(sbList==null||sbList.isEmpty()){
            return ResultUtil.ReturnError("当前IP["+clientIp+"]还未配置入所拍照摄像头");
        }
        List<WlsxtBean> wlsxtList = new ArrayList<>();
        for (int i = 0; i < sbList.size(); i++) {
            WlsxtBean bean = new WlsxtBean();
            ChasSb chasSb = sbList.get(i);
            bean.setSbbh(chasSb.getSbbh());
            bean.setIp(chasSb.getKzcs2());
            bean.setPort(chasSb.getKzcs3());
            if(StringUtils.isNotEmpty(chasSb.getKzcs4())){
                int index = chasSb.getKzcs4().indexOf(":");
                if(index>-1){
                    bean.setUserName(chasSb.getKzcs4().substring(0, index));
                    bean.setPassword(chasSb.getKzcs4().substring(index+1));
                }
            }
            bean.setSbmc(chasSb.getSbmc());
            bean.setChannel(chasSb.getKzcs5());
            wlsxtList.add(bean);
        }
        return ResultUtil.ReturnSuccess(wlsxtList);
    }

    @Override
    public ApiReturnResult<?> getMjRoles(String sfzh) {
        log.debug(String.format("身份证号[%s]", sfzh));
        try {
            if (StringUtils.isEmpty(sfzh)) {
                return ResultUtil.ReturnError("民警身份证不能为空");
            }
            JdoneSysUser user = userService.findSysUserByIdCard(sfzh);
            if (user == null) {
                return ResultUtil.ReturnError("用户信息不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            List<JdoneSysUserRole> roles = userRoleService.findList(map, null);
            if (roles == null || roles.isEmpty()) {
                return ResultUtil.ReturnError("用户未分配角色");
            }
            return ResultUtil.ReturnSuccess("获取角色成功",roles);
        } catch (Exception e) {
            log.error("getMjRoles:", e);
            return ResultUtil.ReturnError("系统异常"+e.getMessage());
        }
    }
}
