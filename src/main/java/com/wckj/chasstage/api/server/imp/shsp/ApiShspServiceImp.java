package com.wckj.chasstage.api.server.imp.shsp;

import com.wckj.chasstage.api.def.shsp.model.ShcomitParam;
import com.wckj.chasstage.api.def.shsp.model.ShspParam;
import com.wckj.chasstage.api.def.shsp.service.ApiShspService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForFrwsClient;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act.entity.JdoneActInstNodePro;
import com.wckj.jdone.modules.act.entity.JdoneActInstPendTask;
import com.wckj.jdone.modules.act.service.JdoneActInstPendTaskService;
import com.wckj.jdone.modules.act.service.JdoneActInstanceService;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.act2.service.ProcessEngine2Service;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import com.wckj.jdone.modules.rmd.service.JdoneRmdMsgService;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 审核审批
 * @Package
 * @Description:
 * @date 2020-10-2914:44
 */
@Service
public class ApiShspServiceImp implements ApiShspService {

    private static Logger logger = LoggerFactory.getLogger(ApiShspServiceImp.class);

    @Autowired
    private JdoneRmdMsgService jdoneRmdMsgService;
    @Autowired
    private ProcessEngine2Service processEngineService;
    @Autowired
    private JdoneActInstanceService actInstanceService;
    @Autowired
    private ChasYmCsspService csspService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private JdoneRmdMsgService msgService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private JdoneActInstPendTaskService actinstpendtask;

    /**
     * @param
     * @return
     * @throws //异常
     * @description
     * @author wtl //作者
     * @date 2020/9/8 19:36
     * Why I will accept  the unreasonable task , because for living
     */
    @Override
    public ApiReturnResult<?> getRmgDataByParamMap(Integer page, Integer rows, ShspParam shspParam, String createTimeDesc) {
        SessionUser user = WebContext.getSessionUser();
        String idCard = null;
        String roleCode = null;
        if (user == null) {
            idCard = shspParam.getIdCard();
            roleCode = shspParam.getRoleCode();
        } else {
            idCard = user.getIdCard();
            roleCode = user.getRoleCode();
        }
        Map<String, Object> params = new HashMap<>();
        JdoneRmdMsg rmdMsg = new JdoneRmdMsg();
        try {
            rmdMsg.setMsgType(shspParam.getMsgType());
            rmdMsg.setBizType(shspParam.getBizType());
            rmdMsg.setRecObjMark(idCard);
            rmdMsg.setMsgStatus(shspParam.getMsgStatus());
            params = JsonUtil.jsonStringToMap(JsonUtil.getJsonString(rmdMsg));
            params.put("recObjRoleMark", roleCode);
            params.put("createStartTime", shspParam.getCreateStartTime());
            params.put("createEndTime", shspParam.getCreateEndTime());
            PageDataResultSet<JdoneRmdMsg> list = jdoneRmdMsgService.getRmgDataByParamMap(shspParam.getPage(), shspParam.getRows(), params, "CREATE_TIME DESC");
            Map<String, Object> result = new HashMap<>();
            result.put("total", list.getTotal());
            result.put("rows", DicUtil.translate(list.getData(), new String[]{"ZD_SYS_ORG_CODE_SNAME"}, new String[]{"sendOrgCode"}));
            return ResultUtil.ReturnSuccess(result);
        } catch (Exception e) {
            return ResultUtil.ReturnError("查询异常:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj) {
        try {
            cmdObj.setCurrentProUser(WebContext.getSessionUser());
            processEngineService.completeTask(cmdObj);
        } catch (Exception e) {
            logger.error("executeProcess:", e);
            return ResultUtil.ReturnError("系统异常:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("审批成功!");
    }

    @Override
    public ApiReturnResult<?> cqsplc(String bizId, String bizType) {
        ApiReturnResult<List<Map<String, Object>>> apiReturnResult = new ApiReturnResult<>();
        try {
            List<JdoneActInstNodePro> rs = actInstanceService.findProHisByBizIdAndType(bizId, bizType);
            List<Map<String, Object>> rsListMap = com.wckj.framework.core.dic.DicUtil.translate(rs, new String[]{"ZD_SYS_USER_ID", "ZD_SYS_ORG_SYSCODE_SNAME"}, new String[]{"proUserId", "proOrgSysCode"});
            if (rsListMap.isEmpty()) {
                apiReturnResult.setMessage("根据审批ID,未获取到审批记录!");
                apiReturnResult.setCode("500");
                return apiReturnResult;
            }
            apiReturnResult.setMessage("获取审批数据成功!");
            apiReturnResult.setCode("200");
            apiReturnResult.setData(rsListMap);
        } catch (Exception e) {
            apiReturnResult.setMessage("获取数据异常" + e.getMessage());
            apiReturnResult.setCode("500");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getCqspXq(String bizId) {
        ApiReturnResult<Map<String, Object>> result = new ApiReturnResult<>();
        ChasYmCssp cssp = csspService.findById(bizId);
        try {
            Map<String, Object> data = new HashMap<>();
            if (cssp == null) {
                result.setMessage("根据审批ID,未获取到审批记录!");
                result.setCode("500");
                return result;
            }

            ChasBaqryxx baqryxx = baqryxxService.findByRybh(cssp.getRybh());
            data.put("id", cssp.getId());
            data.put("baqmc", cssp.getBaqmc());
            data.put("name", cssp.getRyxm());
            if (baqryxx != null && StringUtils.isNotEmpty( baqryxx.getZpid())) {
                try {
                    FileInfoObj fileInfoByBizId = FrwsApiForThirdPart.getFileInfoByBizId(baqryxx.getZpid());
                    if (fileInfoByBizId != null) {
                        data.put("zp", fileInfoByBizId.getDownUrl());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("获取人员照片失败", e);
                }
            }
            data.put("rqsj", baqryxx.getRRssj());
            data.put("rqyy", baqryxx.getRyzaymc());
            data.put("bz", "");
            data.put("cqsj", "");
            data.put("cqyy", cssp.getLkyy());
            data.put("sqmj", cssp.getZbrXm());
            data.put("spzt", StringUtil.equals(cssp.getSpzt(), "-1") ? "2" : cssp.getSpzt());
            data.put("spsj", cssp.getSpsj());
            data.put("sprxm", cssp.getSprXm());

            result.setMessage("获取审批数据成功!");
            result.setCode("200");
            result.setData(data);
        } catch (Exception e) {
            result.setMessage("获取数据异常:" + e.getMessage());
            result.setCode("200");
            return result;
        }
        return result;
    }

    @Override
    public ApiReturnResult<?> docqsp(ShcomitParam shcomitParam) {
        ApiReturnResult<?> apiReturnResult = new ApiReturnResult<>();
        String spzt = shcomitParam.getSpzt();
        String bizId = shcomitParam.getId();
        JdoneSysUser user = userService.findSysUserByIdCard(shcomitParam.getSfzh());
        String msgId = shcomitParam.getMsgId();  //消息id
        String instanceId = shcomitParam.getInstId();
        String taskId = shcomitParam.getTaskId();  //任务id
        String proOpinion = shcomitParam.getProOpinion();  //审批意见
        String proType = shcomitParam.getProType();  //审批类型
        String nodeId = shcomitParam.getNodeId();  //节点id
        String nodeMark = shcomitParam.getNodeMark();  //节点标识
        String proResultType = shcomitParam.getProResultType();  //审批返回类型
        try {

            if (user == null) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("根据sfzh未查询到民警信息!");
                return apiReturnResult;
            }

            JdoneSysOrg org = orgService.findBySysCode(user.getOrgSysCode());
            if (org == null) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("根据orgCode未查询到机构信息!");
                return apiReturnResult;
            }

            if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(instanceId) || StringUtils.isEmpty(taskId)) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("流程信息ID不能为空!");
                return apiReturnResult;
            }

            if (StringUtil.equals(spzt, "2")) {
                spzt = "-1";
                if (StringUtil.isEmpty(proOpinion)) {
                    proOpinion = "不同意";
                }
            }

            if (StringUtil.isEmpty(proOpinion)) {
                proOpinion = "同意";
            }

            baqryxxService.rydeparture(bizId, spzt, proOpinion, user);

            //同步修改审批消息状态
            JdoneRmdMsg msg = new JdoneRmdMsg();
            msg.setId(msgId);
            msg.setBizId(bizId);
            msg.setBizType("dzxbaqryAct");
            msg.setClrXm(user.getName());
            msg.setClrSfzh(user.getIdCard());
            msg.setClrDwmc(user.getOrgName());
            msg.setClrDwbh(user.getOrgCode());
            msg.setClrDwxtbh(user.getOrgSysCode());
            msg.setClsj(new Date());
            msg.setMsgStatus("01");  //已处理
            msgService.updateMsgStatus(msg);

            NodeProcessCmdObj cmdObj2 = new NodeProcessCmdObj();
            cmdObj2.setActKey("dzxbaqry");
            cmdObj2.setBizType("dzxbaqryAct");
            cmdObj2.setBizId(bizId);
            cmdObj2.setActInstId(instanceId);
            cmdObj2.setTaskId(taskId);
            cmdObj2.setProOpinion(proOpinion);
            cmdObj2.setProType(proType);
            cmdObj2.setCurrentNodeId(nodeId);
            cmdObj2.setCurrentNodeMark(nodeMark);
            cmdObj2.setIsOnlyCompleteTask(true);
            cmdObj2.setProResultType(proResultType);

            SessionUser sessionUser = new SessionUser();
            sessionUser.setIdCard(user.getIdCard());
            sessionUser.setName(user.getName());
            sessionUser.setOrgName(user.getOrgName());
            sessionUser.setOrgSysCode(user.getOrgSysCode());
            sessionUser.setUserId(user.getId());
            sessionUser.setOrgCode(user.getOrgCode());
            sessionUser.setCurrentOrgCode(user.getOrgCode());
            sessionUser.setCurrentOrgSysCode(user.getOrgSysCode());
            sessionUser.setCurrentOrgName(user.getOrgName());
            sessionUser.setDwlx(org.getJzfl());
            sessionUser.setCurrentDwlx(org.getJzfl());
            sessionUser.setOrgLevel(org.getLevel());
            cmdObj2.setSessionUser(sessionUser);
            cmdObj2.setCurrentProUser(sessionUser);
            cmdObj2.setCurrentProUserId(user.getId());
            cmdObj2.setCurrentProOrgSysCode(user.getOrgSysCode());
            Map<String, Object> actResult = actInstanceService.completeProcess(cmdObj2, msg);
            if ((boolean) actResult.get("success")) {
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage("成功");
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage(actResult.get("msg").toString());
            }
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("出区审批异常:" + e.getMessage());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getNextTaskNodeInfo(String bizId, String bizType) {
        ApiReturnResult<JdoneActInstPendTask> apiReturnResult = new ApiReturnResult<>();
        // 0调用成功，1：接口异常
        apiReturnResult.setCode("200");
        apiReturnResult.setMessage("接口调用成功");
        if (StringUtils.isEmpty(bizId) || StringUtils.isEmpty(bizType)) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("bizId、bizType不能为空!");
            return apiReturnResult;
        }
        try {
            JdoneActInstPendTask pendTask = new JdoneActInstPendTask();
            pendTask.setBizId(bizId);
            pendTask.setBizType(bizType);
            JdoneActInstPendTask actInstPendTask = actinstpendtask.findBizIdAndType(pendTask);
            apiReturnResult.setData(actInstPendTask);
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("获取数据失败：" + e.getMessage());
        }
        return apiReturnResult;
    }

    /**
     * 不再处理
     *
     * @param msgId
     * @return
     */
    @Override
    public ApiReturnResult<?> noPrompt(String msgId) {
        ApiReturnResult<?> returnResult = new ApiReturnResult<>();
        JdoneRmdMsg newRmdMsg = (JdoneRmdMsg)this.jdoneRmdMsgService.findById(msgId);
        if (newRmdMsg != null) {
            newRmdMsg.setIsNotRmd(1);
            this.jdoneRmdMsgService.update(newRmdMsg);
            returnResult.setCode("200");
            returnResult.setMessage("操作成功");
        }else{
            returnResult.setCode("500");
            returnResult.setMessage("消息不存在！");
        }
        return returnResult;
    }
}
