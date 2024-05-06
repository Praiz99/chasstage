package com.wckj.chasstage.api.server.release.dc.service.impl;



import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.ILSLocationEventService;
import com.wckj.chasstage.api.server.release.dc.service.gj.IRygjProcessor;
import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.dhsgl.service.impl.ChasDhsKzServiceImpl;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sxsgl.service.impl.ChasSxsKzServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ILSLocationEventServiceImpl implements ILSLocationEventService {
    private static Logger log = Logger.getLogger(ILSLocationEventServiceImpl.class);

    @Autowired
    @Qualifier("xyrgjProcessor")
    private IRygjProcessor xyrgjProcessor;
    @Autowired
    @Qualifier("mjgjProcessor")
    private IRygjProcessor mjgjProcessor;
    @Autowired
    @Qualifier("fkgjProcessor")
    private IRygjProcessor fkgjProcessor;
    @Autowired
    private ChasXtQyService chasQyService;

    @Lazy
    @Autowired
    @Qualifier("webSocketHandler")
    private SpringWebSocketHandler webSocketHandler
            ;



    @Override
    public void dealLocationMessageWckj(String baqid, TagLocationInfo content) {
        String tagType = content.getTagType();
        String equipFun = content.getEquipFun();
        String rybh = content.getRybh();
        if(StringUtil.isEmpty(rybh)||StringUtil.isEmpty(content.getArea())
                ||StringUtil.isEmpty(content.getTagId())){
            return;
        }
        if("1".equals(tagType)){//嫌疑人
            xyrgjProcessor.process(baqid, content);
            //海宁专用 判断是否海宁专用走廊定位
            String areaId = content.getArea();
            ChasXtQy chasXtQy = chasQyService.findByYsid(areaId);
            String qyid = chasXtQy.getId();
            if (ChasDhsKzServiceImpl.DHS_ZL_QY_IDS.contains(qyid)) {
                //等候室
                webSocketHandler.sendLocaltion(baqid, "haiNingCorridorLocationWaiting");
            } else if (ChasSxsKzServiceImpl.SXS_ZL_QY_IDS.contains(qyid)) {
                //审讯室
                webSocketHandler.sendLocaltion(baqid, "haiNingCorridorLocationInterrogation");
            }
        }else{
            //非嫌疑人，先尝试从民警入区表中查询人员信息，如果没有则从访客登记表中查找人员信息
            if(mjgjProcessor.process(baqid, content)||
                    fkgjProcessor.process(baqid, content)){
                log.info("保存胸卡轨迹信息成功");
            }
        }


    }




}
