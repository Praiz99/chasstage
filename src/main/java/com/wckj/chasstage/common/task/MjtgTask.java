package com.wckj.chasstage.common.task;


import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 民警脱岗监测
 */
@Component("mjtgTask")
public class MjtgTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(MjtgTask.class);
	@Autowired
	private ChasSxsKzService sxsKzService;
	@Autowired
	private ChasYwRygjService rygjService;


	public void sxAlarm() {
		log.info("开始执行民警脱岗定时任务");
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_MJTG);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				String baqid = yjlb.getBaqid();
				//log.debug("开始轮询,民警脱岗预警" + baqid);
				//if(!processed.contains(yjlb.getBaqid())){
					processBaqSx(yjlb);
					//processed.add(yjlb.getBaqid());
				//}
			});
		} catch (Exception e) {
			log.error("民警脱岗预警", e);
			e.printStackTrace();
		}
	}


	private void processBaqSx(ChasYjlb yjlb){
		//1、获取当前办案区正在使用的审讯室
		//2、获取每个审讯室当前定位的人员列表
		//3、判断审讯室是否只存在嫌疑人
		//4、如果审讯室只有一个嫌疑人，则触发民警脱岗预警信息

		List<ChasSxsKz> lists = getUsingSxs(yjlb.getBaqid());
		if (lists == null || lists.size() == 0) {
			log.debug("审讯区未审讯人员");
			return;
		}
		log.debug("正在使用的审讯室数：" + lists.size());


		for(ChasSxsKz sxs:lists){
			processSxs(sxs,yjlb);
		}
	}
	//获取当前正在使用的审讯室
	private List<ChasSxsKz> getUsingSxs(String baqid){
		Map<String,Object> map = new HashMap<>();
		map.put("baqid",baqid);
		List<ChasSxsKz> sxsKzList = sxsKzService.findByParams(map);
		if(sxsKzList != null&&!sxsKzList.isEmpty()){
			//过滤没有嫌疑人的记录
			sxsKzList=	sxsKzList.stream()
					.filter(sxs-> StringUtils.isNotEmpty(sxs.getRybh()))
					.collect(Collectors.toList());
		}
		return sxsKzList;
	}
	//判断审讯室是否存在单独审讯情况
	private void processSxs(ChasSxsKz sxs,ChasYjlb yjlb){
		//获取审讯室当前定位的人员列表
		String kssj = DateTimeUtils.getDateStr(sxs.getKssj(), 15);
		List<ChasRygj> rygjList = rygjService.selectrygjBysxs(sxs.getBaqid(), sxs.getQyid(),kssj);
		if(rygjList == null||rygjList.isEmpty()){
			log.debug(sxs.getId()+"没有定位到人员或民警");
			return;
		}
		if(rygjList.size() == 1){
			ChasRygj gj = rygjList.get(0);
			if(gj.getRybh().equals(sxs.getRybh())){
				log.debug(sxs.getId()+"没有定位到民警,只有嫌疑人");
				processSxsMjtgInfo(sxs,gj,yjlb);
			}else{
				log.debug(sxs.getId()+"没有定位到嫌疑人,只有民警");
				return;
			}
		}
	}

	//处理审讯室民警脱岗信息
	private void processSxsMjtgInfo(ChasSxsKz sxs,ChasRygj rygj,ChasYjlb yjlb){
		ChasYjxx chasYjxx = new ChasYjxx();
		chasYjxx.setId(StringUtils.getGuid32());
		chasYjxx.setBaqid(sxs.getBaqid());
		chasYjxx.setBaqmc(sxs.getBaqmc());
		chasYjxx.setLrsj(new Date());
		chasYjxx.setXgsj(new Date());
		chasYjxx.setJqms(String.format("发现民警脱岗。嫌疑人:%s独自留在%s",sxs.getRyxm(),rygj.getQymc()));
		chasYjxx.setYjjb(yjlb.getYjjb());
		chasYjxx.setYjlb(SYSCONSTANT.YJLB_MJTG);
		chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
		chasYjxx.setCfsj(new Date());
		chasYjxx.setCfqyid(sxs.getQyid());
		chasYjxx.setCfqymc(rygj.getQymc());
		chasYjxx.setCfrid(sxs.getRybh());
		chasYjxx.setCfrxm(sxs.getRyxm());
		if(isNeedSave(chasYjxx)){
			jdqService.sendYjxxmsg(chasYjxx,"民警脱岗",
					yjlb.getYjfs(),yjlb.getYjsc());
		}
		saveYjxx(chasYjxx);
	}
}
