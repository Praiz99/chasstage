package com.wckj.chasstage.api.server.release.dc.web;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.dto.LSLocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.dto.TBaseTagDis;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;
import com.wckj.chasstage.api.server.release.dc.service.ILSAlarmEventService;
import com.wckj.chasstage.api.server.release.dc.service.ILSBaseDisService;
import com.wckj.chasstage.api.server.release.dc.service.ILSLocationEventService;
import com.wckj.chasstage.common.util.JsonUtil;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * localsense定位事件通知
 */
@Controller
@RequestMapping(value = "/device/interface/localsense/location")
public class LSLocationController {
	final static Logger log = Logger.getLogger(LSLocationController.class);
	@Autowired
	private ILSLocationEventService locationEventService;
	@Autowired
	private ILSAlarmEventService alarmEventService;
	@Autowired
	private ILSBaseDisService baseDisService;

	@RequestMapping(params = "dealLocationMessageWckj", produces = "application/json")
	@ResponseBody
	public DevResult dealLocationMessageWckj(String org, String data) {
		DevResult wr = new DevResult();
		log.debug(String.format("威灿定位接收到办案区:%s的参数：%s",org,data));
		TagLocationInfo content;
		try {
			content = JsonUtil.parse(data, TagLocationInfo.class);
			locationEventService.dealLocationMessageWckj(org, content);
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
			LSLocationEventInfo content = JsonUtil.parse(data, LSLocationEventInfo.class);
			alarmEventService.dealWckjEvent(org, content);
			wr.setCode(1);
		} catch (Exception e) {
			log.error("dealWckjEvent:",e);
			wr.setCode(0);
		}
		return wr;
	}
	@ApiAccessNotValidate
	@RequestMapping(params = "dealBaseDisEvent", produces = "application/json")
	@ResponseBody
	public DevResult dealBaseDisEvent(String data) {
		//处理基站距离消息
		DevResult wr = new DevResult();
		log.debug(String.format("执行到了的dealBaseDisEvent方法,数据：%s",data));
		try {
			TBaseTagDis content = JsonUtil.parse(data, TBaseTagDis.class);
			baseDisService.dealBaseDisEvent(content);
			wr.setCode(1);
		} catch (Exception e) {
			log.error("dealWckjEvent:",e);
			wr.setCode(0);
		}
		return wr;
	}
	
}
