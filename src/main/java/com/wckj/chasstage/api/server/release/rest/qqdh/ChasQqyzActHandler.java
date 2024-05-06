package com.wckj.chasstage.api.server.release.rest.qqdh;

import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act.core.annotation.NodeProAfterHandleMark;
import com.wckj.jdone.modules.act.core.annotation.NodeProHandleMark;
import com.wckj.jdone.modules.act.core.handle.AbstractNodeProHandleFactory;

import java.util.Date;
import java.util.Map;

/**
 * 办案区亲情驿站审批控制器
 */
@NodeProHandleMark(actKey = "chasstageqqyz", bizType = "baqqqyzAct")
public class ChasQqyzActHandler extends AbstractNodeProHandleFactory {
    private Logger logger = LoggerFactory.getLogger(ChasQqyzActHandler.class);

    @NodeProAfterHandleMark(node = "**", nodeType = "03", usePrefixMatch = true)
    public void startAfter(NodeProcessCmdObj cmdObj) throws Exception {
         String result = cmdObj.getProResultType();
        String spyj = cmdObj.getProOpinion();
        Map<String,Object> map = cmdObj.getBizFormData();
        //String ryid = (String)map.get("ryid");
        SessionUser user = cmdObj.getSessionUser();
        ChasQqyzService qqyzService = ServiceContext.getServiceByClass(ChasQqyzService.class);
        ChasQqyz qqyz = qqyzService.findById(cmdObj.getBizId());
        if(qqyz!=null){
            qqyz.setSpyj(spyj);
            if(user!=null){
                qqyz.setSpr(user.getName());
            }
            qqyz.setSpsj(new Date());
            if ("approve".equals(result)) {  //审批同意
                qqyz.setZt(SYSCONSTANT.QQTEL_SPTG);
            } else {  //
                qqyz.setZt(SYSCONSTANT.QQTEL_SPBTG);
            }
            qqyzService.update(qqyz);
        }

    }
}
