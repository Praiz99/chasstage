package com.wckj.chasstage.common.task;


import com.wckj.api.def.zfba.model.sjjh.SjjhDingtalkMsg;
import com.wckj.api.def.zfba.service.gg.ApiGgAjxxService;
import com.wckj.api.def.zfba.service.sjjh.ApiSjjhDingtalkMsgService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人员入区未审讯监测
 */
@Component("rqwsxTask")
public class RqwsxTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(RqwsxTask.class);
	@Autowired
	private ChasSxsKzService sxsKzService;
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
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_RQWSX);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			//Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				//String baqid = yjlb.getBaqid();
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
		String dafs = ryxx.getDafs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(ryxx.getRRssj());
		if("01".equals(dafs)||"06".equals(dafs)||"09".equals(dafs)||"10".equals(dafs)){
			if(!Objects.isNull(yjlb.getXsajyjsj())&&!Objects.isNull(yjlb.getXstqyjsj())){
				//刑事案件（超时时间根据配置）
				if(now.getTime()-ryxx.getRRssj().getTime()>(Double.valueOf(yjlb.getXsajyjsj())-Double.valueOf(yjlb.getXstqyjsj()))*hour){
					processXsajInfo(ryxx,yjlb,start,"即将超过"+yjlb.getXsajyjsj()+"小时未讯问");
				}
			}else {
				//刑事案件12小时未讯问报警(提前3小时预警)
				if (now.getTime() - ryxx.getRRssj().getTime() > 9 * hour) {
					processXsajInfo(ryxx, yjlb, start, "即将超过12小时未讯问");
				}
			}
		}else{//02,03,04,05,07,08,99,11,12,13,14,,15,16,17,18,19
			//行政案件（超时时间根据配置）
			if(!Objects.isNull(yjlb.getXzajyjsj())&&!Objects.isNull(yjlb.getXztqyjsj())){
				if(now.getTime()-ryxx.getRRssj().getTime()>(Double.valueOf(yjlb.getXzajyjsj())-Double.valueOf(yjlb.getXztqyjsj()))*hour){
					processXsajInfo(ryxx,yjlb,start,"即将超过"+yjlb.getXzajyjsj()+"小时未询问");
				}
			}else {
				//行政案件8小时未询问报警(提前2小时预警)
				if(now.getTime()-ryxx.getRRssj().getTime()>6*hour){
					processXsajInfo(ryxx,yjlb,start,"即将超过8小时未询问");
				}
			}

		}
	}

	//处理刑事案件信息
	private void processXsajInfo(ChasBaqryxx ryxx,ChasYjlb yjlb ,String hour,String msg){
		if(StringUtils.isNotEmpty(hour)){
			int count = sxsKzService.getCountByRybh(ryxx.getBaqid(), ryxx.getRybh(), hour);
			if(count>=1){
				return; 
			}
		}
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
		chasYjxx.setYjlb(SYSCONSTANT.YJLB_RQWSX);
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
			if(isOpenAlarm(yjlb)){
				jdqService.sendYjxxmsg(chasYjxx,"入区未审讯",
						yjlb.getYjfs(),yjlb.getYjsc());
			}
		}
		saveYjxx(chasYjxx);
	}

}
