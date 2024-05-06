package com.wckj.chasstage.common.task;


import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 单人审讯监测
 */
@Component("drsxTask")
public class DrsxTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(DrsxTask.class);
	@Autowired
	private ChasSxsKzService sxsKzService;
	@Autowired
	private ChasYwRygjService rygjService;


	public void sxAlarm() {
		log.info("开始执行单人审讯定时任务");
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_DRSX);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				String baqid = yjlb.getBaqid();
				Map<String, Object> params = new HashMap<>();
				params.put("yjlb", SYSCONSTANT.YJLB_DRSX_XWFX);
				params.put("baqid", baqid);
				List<ChasYjlb> list = chasYjlbService.findList(params, null);
				//该办案区如果配置了单人审讯（行为分析）预警，则轨迹的单人审讯任务不检测此办案区
				if(list.size() > 0){
					return;
				}
				//log.debug("开始轮询,单人审讯预警" + baqid);
				//if(!processed.contains(yjlb.getBaqid())){
					processBaqSx(yjlb);
					//processed.add(yjlb.getBaqid());
				//}
			});
		} catch (Exception e) {
			log.error("单人审讯预警", e);
			e.printStackTrace();
		}
	}


	private void processBaqSx(ChasYjlb yjlb){
		//1、获取当前办案区正在使用的审讯室
		//2、获取每个审讯室当前定位的人员列表
		//3、判断审讯室是否存在嫌疑人和民警
		//4、如果审讯室只有一个民警和嫌疑人，则获取审讯室中途离开的轨迹信息
		//5、判断民警在审讯室单独审讯是否超过10min
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
				return;
			}else{
				log.debug(sxs.getId()+"没有定位到嫌疑人,只有民警");
				return;
			}
		}
		if(rygjList.size()>=3){
			log.debug(sxs.getId()+"定位到多位嫌疑人或民警");
			return;
		}
		boolean hasXyr = false;
		for (ChasRygj rygj:rygjList){
			if(rygj.getRybh().equals(sxs.getRybh())){
				hasXyr = true;
			}
		}
		if(!hasXyr){
			log.debug(sxs.getId()+"没有定位到嫌疑人");
			return;
		}
		long tlsjjg = getddsxtlsj();
		for (ChasRygj rygj:rygjList){
			if(!rygj.getRybh().equals(sxs.getRybh())){//民警
				//查询从民警进入审讯室后，中途离开审讯室的定位信息
				List<ChasRygj> ylkgj=getMjgjList(sxs,rygj);//中途离开的轨迹信息列表
				//计算单独审讯的开始时间

				Date ksjcsj = null;
				if(ylkgj != null&& !ylkgj.isEmpty()){
					log.debug("中途离开审讯的定位信息数量:"+ylkgj.size());
					//中途有人离开，单独审讯的开始时间从最后离开的时间开始计算
					ksjcsj = ylkgj.get(0).getJssj();
				}else{
					//中途没有人离开，即该民警从进入审讯室后，一直是一个民警
					ksjcsj = rygj.getKssj();
				}
				Date now = new Date();
				if(now.getTime()-ksjcsj.getTime()>tlsjjg){
					//单独审讯时间超过单独审讯停留时间间隔，触发预警
					processSxsddsxInfo(sxs,rygj,yjlb);
				}
			}
		}
	}
	//
	private List<ChasRygj> getMjgjList(ChasSxsKz sxs,ChasRygj rygj){
		Map<String,Object> map = new HashMap<>();
		map.put("baqid", sxs.getBaqid());
		map.put("qyid",sxs.getQyid());
		map.put("rybh",sxs.getRybh());
		map.put("startsj", DateTimeUtils.getDateStr(rygj.getKssj(),15));
		map.put("dqsj",DateTimeUtils.getDateStr(new Date(),15));
		return rygjService.selectrygjByMj(map);
	}
	//获取单独审讯停留时间
	private long getddsxtlsj(){
		long tatlsj =0;
		try {
			String closeTime = SysUtil.getParamValue("drsxtlsj");
			tatlsj=Long.valueOf(closeTime);
			tatlsj=tatlsj*60*1000;
		} catch (NumberFormatException e) {
			tatlsj=10*60*1000;
		}
		return tatlsj;
	}
	//处理审讯室单独审讯信息
	private void processSxsddsxInfo(ChasSxsKz sxs,ChasRygj rygj,ChasYjlb yjlb){

		ChasYjxx chasYjxx = new ChasYjxx();
		chasYjxx.setId(StringUtils.getGuid32());
		chasYjxx.setBaqid(sxs.getBaqid());
		chasYjxx.setBaqmc(sxs.getBaqmc());
		chasYjxx.setLrsj(new Date());
		chasYjxx.setXgsj(new Date());
		chasYjxx.setJqms(String.format("民警:%s正在审讯室:%s单独审讯嫌疑人:%s",rygj.getXm(),rygj.getQymc(),sxs.getRyxm()));
		chasYjxx.setYjjb(yjlb.getYjjb());
		chasYjxx.setYjlb(SYSCONSTANT.YJLB_DRSX);
		chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
		chasYjxx.setCfsj(new Date());
		chasYjxx.setCfqyid(sxs.getQyid());
		chasYjxx.setCfqymc(rygj.getQymc());
		chasYjxx.setCfrid(rygj.getRybh());
		chasYjxx.setCfrxm(rygj.getXm());

		if(isNeedSave(chasYjxx)){
			//ChasRygjmapService rygjmapService = ServiceContext.getServiceByClass(ChasRygjmapService.class);
			//rygjmapService.buildDdsxInfo(chasYjxx);
			jdqService.sendYjxxmsg(chasYjxx,"单独审讯",
					yjlb.getYjfs(),yjlb.getYjsc());
		}
		saveYjxx(chasYjxx);
	}
}
