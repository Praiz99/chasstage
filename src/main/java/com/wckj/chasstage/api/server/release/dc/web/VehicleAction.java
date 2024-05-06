package com.wckj.chasstage.api.server.release.dc.web;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.dto.ClcrInfo;
import com.wckj.chasstage.api.server.release.dc.service.IVehicleService;
import com.wckj.chasstage.common.util.JsonUtil;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 车辆出入事件处理
 */
@Controller
@RequestMapping(value = "/device/interface/vehicle")
public class VehicleAction {
    final static Logger log = Logger.getLogger(LocationAction.class);
    @Autowired
    private IVehicleService vehicleService;

    @RequestMapping(params = "dealVehicleMessage", produces = "application/json")
    @ResponseBody
    public DevResult dealVehicleMessage(String org, String data) {
        DevResult wr = new DevResult();
        log.error(String.format("接收到车辆出入事件，办案区:%s的参数：%s",org,data));
        try {
            if(StringUtils.isEmpty(data)){
                return DevResult.error("参数错误");
            }
            //TODO 根据传过来的格式修改参数类型
            ClcrInfo content = JsonUtil.parse(data, ClcrInfo.class);
            vehicleService.dealVehicleMessage(org, content);
            wr.setCode(1);
        } catch (Exception e) {
            log.error("dealVehicleMessage:",e);
            wr.setCode(0);
        }
        return wr;

    }
}
