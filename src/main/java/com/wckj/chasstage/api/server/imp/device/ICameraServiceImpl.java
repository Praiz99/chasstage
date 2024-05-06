package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.api.server.device.ICameraService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ICameraServiceImpl extends BaseDevService implements ICameraService {
    private static final Logger log = Logger.getLogger(ICameraServiceImpl.class);


    @Override
    public DevResult cameraCapture(String baqid, String org, String sbgn, String sbbh, String bizId) {
        if(cameraOper == null){
            init();
        }
        String msg = String.format("办案区：%s 摄像头功能:%s 摄像头编号:%s", baqid, sbgn,
                sbbh);
        log.debug(msg);
        return cameraOper.cameraCapture(baqid,org, sbgn,sbbh,bizId);
    }

    @Override
    public DevResult capture(String rybh, String cameraFun,String bizId,String gw) {
        if(chasRyjlService == null||chasSbService == null){
            init();
        }
        SessionUser user = WebContext.getSessionUser();
        String roleCode = user.getRoleCode();
        String rolename = user.getRoleName();
        log.debug(String.format("人员编号{} 相机类型{}：", rybh, cameraFun));
        DevResult wr = new DevResult();
        String msg;
        Map<String, Object> map = new HashMap<>();
        map.put("rybh", rybh);
        map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        ChasRyjl chasRyjl = chasRyjlService.findByParams(map);
        if (chasRyjl == null) {
            msg = rybh+"人员记录不存在";
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        String baqid = chasRyjl.getBaqid();
        ChasBaq chasBaq = chasBaqService.findById(baqid);
        map.clear();
        map.put("baqid", baqid);
        map.put("sbgn", cameraFun);
        if(!StringUtils.equals(gw,"false")){  //不采用岗位模式
            map.put("kzcs6", roleCode);
        }
        List<ChasSb> sblist = chasSbService.findByParams(map);
        if (sblist.isEmpty()) {
            msg = String.format("岗位角色:%s 未关联随身物品摄像头", rolename);
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            return wr;
        }
        String sbbh = null;
        try {
            sbbh = sblist.get(0).getSbbh();
            DevResult result = cameraCapture(baqid,chasBaq.getDwdm(), cameraFun, sbbh,bizId);
            if (result.getCode() != 1) {
                log.error(String.format("办案区:%s  调用网络相机类型:%s 异常%s", baqid,
                        cameraFun, result.getMessage()));
                wr.setCode(0);
                wr.setMessage(result.getMessage());
                return wr;
            } else {
                wr.setCode(1);
                String biz=  result.get("bizId").toString();
                String imageUrl=  result.get("imageUrl").toString();
                wr.add("bizId",biz );
                wr.add("imageUrl",imageUrl );
            }
        } catch (Exception e) {
            msg = String.format("办案区:%s  调用网络相机类型:%s 异常%s", baqid, cameraFun,
                    e.getMessage());
            wr.setCode(0);
            wr.setMessage(msg);
            log.error(msg , e);
            return wr;
        }
        return wr;
    }
}
