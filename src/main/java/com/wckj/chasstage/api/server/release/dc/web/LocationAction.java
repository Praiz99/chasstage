package com.wckj.chasstage.api.server.release.dc.web;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.ILSLocationEventService;
import com.wckj.chasstage.api.server.release.dc.service.ILocationEventService;
import com.wckj.chasstage.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 定位事件通知
 */
@Controller
@RequestMapping(value = "/device/interface/location")
public class LocationAction {
	final static Logger log = Logger.getLogger(LocationAction.class);
	@Autowired
	private ILocationEventService locationEventService;
	@Autowired
	private ILSLocationEventService lslocationEventService;

	@RequestMapping(params = "deviceError", produces = "application/json")
	@ResponseBody
	public DevResult deviceError(String org, String data) {
		DevResult wr = new DevResult();
		log.debug(String.format("办案区：%s接收到的参数：%s", org,data));
		wr.setCode(1);
		wr.setMessage("成功");
		return wr;
	}

	@RequestMapping(params = "dealLocationMessageWckj", produces = "application/json")
	@ResponseBody
	public DevResult dealLocationMessageWckj(String org, String data) {
		DevResult wr = new DevResult();
		log.debug(String.format("威灿定位接收到办案区:%s的参数：%s",org,data));
		TagLocationInfo content;
		try {
			content = JsonUtil.parse(data, TagLocationInfo.class);
			lslocationEventService.dealLocationMessageWckj(org, content);
			wr.setCode(1);
		} catch (Exception e) {
			log.error("dealLocationMessageWckj:",e);
			wr.setCode(0);
		}
		return wr;
	}
	
	@RequestMapping(params = "dealWckjEvent", produces = "application/json")
	@ResponseBody
	public DevResult dealWckjEvent(String org, String data) {
		//处理人员逃脱 巡更打卡 手环没电 人员心率 等事件
		DevResult wr = new DevResult();
		log.debug(String.format("执行到了的dealWckjEvent 方法,办案区：%s 数据：%s",org, data));
		try {
			LocationEventInfo content = JsonUtil.parse(data, LocationEventInfo.class);
			locationEventService.dealWckjEvent(org, content);
			wr.setCode(1);
		} catch (Exception e) {
			log.error("dealWckjEvent:",e);
			wr.setCode(0);
		}
		return wr;
	}

	@RequestMapping(params = "heartRateEvent",produces = "application/json")
	@ResponseBody
	public DevResult heartRateEvent(String org, String data,String expandParm){
		DevResult wr = new DevResult();
		try {
			LocationEventInfo content = JsonUtil.parse(data, LocationEventInfo.class);
			content.setExpandParm(expandParm);
			log.debug(String.format("执行到了的heartRateEvent 方法,办案区：%s 数据：%s",org, data));
			locationEventService.heartRateEvent(org,content);
			wr.setCode(1);
		} catch (IOException e) {
			wr.setCode(0);
			log.error("heartRateEvent:",e);
		}
		return wr;
	}
	
}
