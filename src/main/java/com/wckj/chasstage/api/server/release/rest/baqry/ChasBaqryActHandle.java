package com.wckj.chasstage.api.server.release.rest.baqry;

import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.chasstage.modules.cssp.service.impl.ChasYmCsspServiceImpl;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act.core.annotation.NodeProAfterHandleMark;
import com.wckj.jdone.modules.act.core.annotation.NodeProHandleMark;
import com.wckj.jdone.modules.act.core.handle.AbstractNodeProHandleFactory;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 二期
 * 办案区人员流程控制
 */
@NodeProHandleMark(actKey="baqry",bizType="baqryAct")
public class ChasBaqryActHandle extends AbstractNodeProHandleFactory {

    private Logger logger = Logger.getLogger(ChasBaqryActHandle.class);

    /**
     * 流程审批后置事件
     * @param cmdObj
     * @throws Exception
     */
    @NodeProAfterHandleMark(node="**", nodeType="03",usePrefixMatch=true)
    public void spAfter(NodeProcessCmdObj cmdObj) throws Exception {
        processor(cmdObj);
    }

    public void processor(NodeProcessCmdObj cmdObj) throws Exception{
        try {
            String result = cmdObj.getProResultType();
            Map<String, Object> bizData = JsonUtil.jsonStringToMap(cmdObj.getBizData());
            Map<String,Object> comNodePro = (Map<String, Object>) bizData.get("comNodePro");
            String id = (String) comNodePro.get("bizId");
            String spyj = (String) comNodePro.get("proOpinion");
            String spzt = "1";

            JdoneSysUserService jdoneSysUserService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
            JdoneSysUser spr = jdoneSysUserService.findSysUserByIdCard(cmdObj.getSessionUser().getIdCard());
            ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
            if ("approve".equals(result)) {//同意
                baqryxxService.rydeparture(id,spzt,spyj,spr);
            } else if ("disagree".equals(result)) {//不同意
                spzt = "-1";
                baqryxxService.rydeparture(id,spzt,spyj,spr);
            } else {//退回
                spzt = "-1";
                baqryxxService.rydeparture(id,spzt,spyj,spr);
            }
        } catch (Exception e) {
            logger.error("流程后置处理失败["+cmdObj.getBizId()+"]", e);
            throw e;
        }
    }

    /**
     * 退回后置事件
     * @param cmdObj
     * @throws Exception
     */
    @NodeProAfterHandleMark(node="**", nodeType="02",usePrefixMatch=true)
    public void rejectAfter(NodeProcessCmdObj cmdObj) throws Exception {
        processor(cmdObj);
    }
}
