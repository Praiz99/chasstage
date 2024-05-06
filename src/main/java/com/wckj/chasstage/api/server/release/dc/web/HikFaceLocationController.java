package com.wckj.chasstage.api.server.release.dc.web;

import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.dto.CameraLocationInfo;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.HikFaceLocationEventService;
import com.wckj.chasstage.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/device/interface/hkrldw/location")
public class HikFaceLocationController {

    final static Logger log = Logger.getLogger(HikFaceLocationController.class);

    @Autowired
    private HikFaceLocationEventService hikFaceLocationEventService;

    @RequestMapping(params = "dealLocationMessageWckj", produces = "application/json")
    @ResponseBody
    public DevResult dealLocationMessageWckj(String org, String data) {
        DevResult wr = new DevResult();
        log.debug(String.format("海康人脸定位发送到办案区:%s的参数：%s",org,data));
        CameraLocationInfo content;
        try {
            content = JsonUtil.parse(data, CameraLocationInfo.class);
            hikFaceLocationEventService.dealLocationMessageWckj(org, content);
            wr.setCode(1);
        } catch (Exception e) {
            log.error("dealLocationMessageWckj:",e);
            wr.setCode(0);
        }
        return wr;

    }
}
