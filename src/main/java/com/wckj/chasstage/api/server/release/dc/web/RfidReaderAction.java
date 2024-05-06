package com.wckj.chasstage.api.server.release.dc.web;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.service.IRfidEventService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刷卡事件通知
 */
@Controller
@RequestMapping(value = "/device/interface/rfidReader")
public class RfidReaderAction {
	final static Logger log = Logger.getLogger(RfidReaderAction.class);
	@Autowired
	private IRfidEventService rfidEventService;
	@Autowired
	private ChasSbService sbService;
	@RequestMapping(params = "cardReaded", produces = "application/json")
	@ResponseBody
	public DevResult cardReaded(String org, String sbgn, String cardNo ) {
		DevResult wr = new DevResult();
		log.debug(String.format("单位[%s]设备功能[%s]读取到卡号：[%s]", org, sbgn,cardNo));
		try {
			rfidEventService.cardScan(org, cardNo,sbgn);
			wr.setCode(1);
			wr.setMessage("读卡处理正常");
		} catch (Exception e) {
			log.error("cardReaded:",e);
			wr.setCode(0);
			wr.setMessage("读卡处理异常{}"+e.getMessage());
		}
		return wr;
	}

	@RequestMapping(params = "cardReadedFpsxs", produces = "application/json")
	@ResponseBody
	public DevResult cardReadedFp(String wdbhL) {
		DevResult wr = new DevResult();
		log.debug(String.format("读取到卡号：[%s]", wdbhL));
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("sblx", "6");
			map.put("kzcs2", wdbhL);
			List<ChasSb> list = sbService.findList(map, null);
			if(list==null||list.isEmpty()){
				return DevResult.error("无法找到设备信息");
			}
			ChasSb sb = list.get(0);
			wr = rfidEventService.cardScan(sb.getBaqid(), wdbhL, "5");
			//wr.setCode(1);
			//wr.setMessage("读卡分配审讯室处理正常");
			return wr;
		} catch (Exception e) {
			log.error("cardReadedFp:",e);;
			wr.setCode(0);
			wr.setMessage("读卡分配审讯室处理正常{}"+e.getMessage());
		}
		return wr;
	}

}
