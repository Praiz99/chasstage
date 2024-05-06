package com.wckj.chasstage.api.server.release.rest.baqry;

import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act.core.annotation.NodeProAfterHandleMark;
import com.wckj.jdone.modules.act.core.annotation.NodeProHandleMark;
import com.wckj.jdone.modules.act.core.handle.AbstractNodeProHandleFactory;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 办案区亲情驿站审批控制器
 */
@NodeProHandleMark(actKey = "dzxbaqry", bizType = "dzxbaqryAct")
public class ChasRycsActHandler extends AbstractNodeProHandleFactory {
    private Logger logger = LoggerFactory.getLogger(ChasRycsActHandler.class);
    @Autowired
    private ChasBaqryxxService baqryxxService;

    @NodeProAfterHandleMark(node = "**", nodeType = "03", usePrefixMatch = true)
    public void startAfter(NodeProcessCmdObj cmdObj) throws Exception {
        process(cmdObj);
    }

    private void process(NodeProcessCmdObj cmdObj) throws Exception{
        try {
            String result = cmdObj.getProResultType();
            String bizId = cmdObj.getBizId();
            String spyj = cmdObj.getProOpinion();
            String spzt = "1";
            JdoneSysUserService jdoneSysUserService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
            JdoneSysUser spr = jdoneSysUserService.findSysUserByIdCard(cmdObj.getSessionUser().getIdCard());
            ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
            if ("approve".equals(result)) {//同意
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            } else if ("disagree".equals(result)) {//不同意
                spzt = "-1";
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            } else {//退回
                spzt = "-1";
                baqryxxService.rydeparture(bizId, spzt, spyj, spr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("executeProcess:", e);
        }
    }

    @NodeProAfterHandleMark(node = "**", nodeType = "02", usePrefixMatch = true)
    public void rejectAfter(NodeProcessCmdObj cmdObj) throws Exception {
        process(cmdObj);
    }
}
