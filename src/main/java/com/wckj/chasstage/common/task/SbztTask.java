package com.wckj.chasstage.common.task;


import com.wckj.chasstage.common.util.NetworkUtil;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备网络状态和工作状态监测
 */
@Component("sbztTask")
public class SbztTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(SbztTask.class);
	@Autowired
	private ChasSbService sbService;

	public void sxAlarm() {
		log.info("开始执行设备网络状态和工作状态监测定时任务"+System.currentTimeMillis());
		try {
			Map<String, Object> params = new HashMap<>();
			List<ChasSb> sbList = sbService.findList(params, null);
			if(sbList != null && !sbList.isEmpty()){
				sbList.parallelStream().forEach(sb->{
					processSb(sb);
				});
			}else{
				log.error("未找到设备");
			}
		} catch (Exception e) {
			log.error("设备网络状态和工作状态监测", e);
			e.printStackTrace();
		}
		log.info("设备网络状态和工作状态监测定时任务结束"+System.currentTimeMillis());
	}
	private void processSb(ChasSb sb){
		String sblx = sb.getSblx();
		if(sblx.startsWith("01")){
			return;
		}
		boolean result = true;
		if("1".equals(sb.getSblx())){//LED
			result = checkIps(sb.getKzcs1());
		}else if("2".equals(sb.getSblx())){//摄像头
			result = checkIps(sb.getKzcs2());
		}else if("3".equals(sb.getSblx())){//继电器
			result = checkIps(sb.getKzcs1());
		}else if("4".equals(sb.getSblx())){//储物柜
			result = checkIps(sb.getKzcs1(),sb.getKzcs2());
		}else if("6".equals(sb.getSblx())){//标签
			return;
		}else if("7".equals(sb.getSblx())){//天线
			return;
		}else if("8".equals(sb.getSblx())){//基站
			return;
		}else if("10".equals(sb.getSblx())){//nvr
			result = checkIps(sb.getKzcs1());
		}
		if(result){
			sb.setWlzt("正常");
			sb.setGzzt("正常");
			sb.setSfzx("1");
			sb.setSfdb("0");
		}else{
			sb.setWlzt("异常");
			sb.setGzzt("异常");
			sb.setSfzx("0");
			sb.setSfdb("1");
		}
		sbService.update(sb);
	}
	private boolean checkIps(String... ips){
		boolean result = true;
		for(String ip:ips){
			if(NetworkUtil.isIpAddressStr(ip)){
				result = NetworkUtil.isHostReachable(ip, 5000);
				if(!result){
					break;
				}
			}
		}
		return result;
	}
}
