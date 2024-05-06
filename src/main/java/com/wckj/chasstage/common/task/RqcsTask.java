package com.wckj.chasstage.common.task;


import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 人员入区超时监测
 */
@Component("rqcsTask")
public class RqcsTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(RqcsTask.class);

	@Autowired
	private ChasBaqryxxService baqryxxService;
	@Autowired
	private ChasRyjlService ryjlService;
	@Autowired
	private ChasDhsKzService dhsKzService;
	@Autowired
	private ChasXtQyService qyService;
	public void sxAlarm() {
		log.info("开始执行人员入区时间定时任务");
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_RQCS);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				String baqid = yjlb.getBaqid();
				//log.debug("开始轮询,单人审讯预警" + baqid);
				//if(!processed.contains(yjlb.getBaqid())){
					processBaqRqsj(yjlb);
					//processed.add(yjlb.getBaqid());
				//}
			});
		} catch (Exception e) {
			log.error("人员入区时间预警", e);
			e.printStackTrace();
		}
	}


	private void processBaqRqsj(ChasYjlb yjlb){
		//1、获取当前办案区在区人员
		//2、根据
		List<ChasBaqryxx> lists = getZqry(yjlb.getBaqid());
		if (lists == null || lists.size() == 0) {
			log.debug("办案区没有在区人员");
			return;
		}
		log.debug("办案区在区人员数量：" + lists.size());
		lists.stream().forEach(ryxx->processRyxx(ryxx,yjlb));
	}
	//获取当前办案区在区人员
	private List<ChasBaqryxx> getZqry(String baqid){
		Map<String,Object> map = new HashMap<>();
		map.put("baqid",baqid);
		map.put("ryzt","01");
		return baqryxxService.findList(map, null);
	}
	//判断审讯室是否存在单独审讯情况
	private void processRyxx(ChasBaqryxx ryxx,ChasYjlb yjlb){
		Date now = new Date();
		long hour = 1000*60*60;
		if(now.getTime()-ryxx.getRRssj().getTime()>23*hour){
			//传唤24小时仍未离开办案区的的报警
			processXsajInfo(ryxx,yjlb,"","即将传唤24小时仍未离开办案区");
		}
	}

	//处理刑事案件信息
	private void processXsajInfo(ChasBaqryxx ryxx,ChasYjlb yjlb,String hour,String msg){
		try {
			String qyid="";
			ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(), ryxx.getRybh());
			if(ryjl!=null&& StringUtils.isNotEmpty(ryjl.getDhsBh())){
				ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
				if(dhsKz!=null){
					qyid=dhsKz.getQyid();
				}
			}

			ChasYjxx chasYjxx = new ChasYjxx();
			chasYjxx.setId(StringUtils.getGuid32());
			chasYjxx.setBaqid(ryxx.getBaqid());
			chasYjxx.setBaqmc(ryxx.getBaqmc());
			chasYjxx.setLrsj(new Date());
			chasYjxx.setXgsj(new Date());
			chasYjxx.setJqms(ryxx.getRyxm()+msg);
			chasYjxx.setYjjb(yjlb.getYjjb());
			chasYjxx.setYjlb(SYSCONSTANT.YJLB_RQCS);
			chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
			chasYjxx.setCfsj(new Date());
			if(StringUtils.isNotEmpty(qyid)){
				ChasXtQy qy = qyService.findByYsid(qyid);
				chasYjxx.setCfqyid(qy.getYsid());
				chasYjxx.setCfqymc(qy.getQymc());
			}
			chasYjxx.setCfrid(ryxx.getRybh());
			chasYjxx.setCfrxm(ryxx.getRyxm());
			if(isNeedSave(chasYjxx)){
				jdqService.sendYjxxmsg(chasYjxx,"入区超时",
						yjlb.getYjfs(),yjlb.getYjsc());
			}
			saveYjxx(chasYjxx);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("预警信息生成失败",e);
		}
	}
}
