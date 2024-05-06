package com.wckj.chasstage.api.server.imp.mjzpk;


import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.framework.spring.event.EventHandle;
import com.wckj.framework.spring.event.EventHandleMark;
import com.wckj.framework.spring.event.FrameworkEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EventHandleMark(events = {"MJZPK_SAVE_ALL"})
public class ApiMjzpkHandle implements EventHandle {

    private Logger logger = Logger.getLogger(ApiMjzpkHandle.class);
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private FaceInvokeService faceInvokeService;
    @Override
    public void done(FrameworkEvent event) {
        ChasXtMjzpk mjzpk = (ChasXtMjzpk) event.getTranArgs()[0];
        boolean r = faceInvokeService.saveMjFeature(mjzpk.getZpid(),mjzpk.getDwxtbh(),mjzpk.getMjsfzh());
        if(!r){
            logger.error("生成民警【"+mjzpk.getMjsfzh()+"】照片特失败！");
        }else{
            try {
                mjzpkService.saveOrUpdate(mjzpk);
                logger.error("生成民警【"+mjzpk.getMjsfzh()+"】成功！");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("保存民警照片库失败", e);
            }
        }
    }

}
