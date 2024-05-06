package com.wckj.chasstage.common.task;


import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.xgjl.entity.ChasXgjl;
import com.wckj.chasstage.modules.xgjl.service.ChasXgjlService;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

//巡更打卡检测
@Component("dutyTask")
public class DutyTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(DutyTask.class);

	@Autowired
	private ChasXgpzService chasXgpzService;
	@Autowired
	private ChasXgjlService chasXgjlService;
	@Autowired
	private ChasBaqryxxService baqryxxService;

	public void dutyAlarm() {
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_XG);
			if(yjlbs==null||yjlbs.isEmpty()){
				return;
			}
			//防止办案区重复配置而多次进行预警监测
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				//if(!processed.contains(yjlb.getBaqid())){
					processBaqXg(yjlb);
					//processed.add(yjlb.getBaqid());
				//}
			});
		} catch (Exception e) {
			log.error("巡更定时任务出错", e);
			e.printStackTrace();
		}

	}

	private void processBaqXg(ChasYjlb yjlb){
		Map<String, Object> params = new HashMap<>();
		log.debug("开始轮询,巡更预警" + yjlb.getBaqmc() + yjlb.getBaqid());
		/*params.clear();
		params.put("baqid", yjlb.getBaqid());
		params.put("fpzt", SYSCONSTANT.DHSTZ_FP);
		List<ChasDhsKz> lists = chasDhskzService.findList(params, null);
		if (lists.isEmpty()) {
			log.warn(yjlb.getBaqmc() + yjlb.getBaqid()+"看守区没有人员");
			return;
		}*/
		params.clear();
		params.put("baqid", yjlb.getBaqid());
		params.put("ryzt", "01");
		List<ChasBaqryxx> baqryxxList = baqryxxService.findList(params, null);
		if (baqryxxList.isEmpty()) {
			log.warn(yjlb.getBaqmc() + yjlb.getBaqid()+"看守区没有人员");
			return;
		}
		log.debug(yjlb.getBaqmc() + yjlb.getBaqid()+"有" + baqryxxList.size() + "人在看守区：");
		params.clear();
		params.put("baqid", yjlb.getBaqid());
		List<ChasXgpz> configList = chasXgpzService.findList(params, null);
		// 根据ORG查询出单位检测时间
		ChasXgpz dc = null;
		if (configList.isEmpty()) {
			log.debug("当前单位，未配置打卡检测时间" + yjlb.getBaqmc() + yjlb.getBaqid());
			return;
		}
		dc = configList.get(0);
		if(dc.getJckssj()==null||dc.getJcjssj()==null){
			log.debug("巡更配置错误");
			return;
		}
		// 获取时间 0.00 - 24.00
		Calendar c = Calendar.getInstance();
		if(dc.getJckssjfz()==null){
			dc.setJckssjfz(0);
		}
		if(dc.getJcjssjfz()==null){
			dc.setJcjssjfz(0);
		}
//		Double jcstart = Double.valueOf(dc.getJckssj() + "."
//				+ (dc.getJckssjfz()<10?"0"+dc.getJckssjfz():dc.getJckssjfz()));
//		Double jcend = Double.valueOf(dc.getJcjssj() + "."
//				+ (dc.getJcjssjfz()<10?"0"+dc.getJcjssjfz():dc.getJcjssjfz()));
		Double current = Double.valueOf(c.get(Calendar.HOUR_OF_DAY) + "."
				+ (c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)));// 举例：18.6
		String msg = yjlb.getBaqmc() + yjlb.getBaqid()+"系统当前时间为" + current + ",打卡开始时间为" + dc.getJckssj()+ "点,结束时间为" + dc.getJcjssj() + "点";
		log.debug(msg);
		// 如果开始时间大于结束时间就启用跨夜模式 反正 正常时间模式
		if (dc.getJckssj() > dc.getJcjssj()) {
			// 只在规定的时间内执行任务, 判断是否在检查时间内
			if ((current <= 24 && current >= (dc.getJckssj() ))
					|| (current >= 0 && current <= dc.getJcjssj())) {
				log.debug(yjlb.getBaqmc() + yjlb.getBaqid()+"跨夜模式");
				timingTesting(current, dc, yjlb);
			}
		} else {
			// 只在规定的时间内执行任务, 判断是否在检查时间内
			if (current > (dc.getJckssj())
					&& current < dc.getJcjssj()) {
				log.debug(yjlb.getBaqmc() + yjlb.getBaqid()+"正常时间模式");
				timingTesting(current, dc, yjlb);
			}
		}
	}
	public void timingTesting(double dq, ChasXgpz dc, ChasYjlb yjlb) {
		log.debug(yjlb.getBaqmc()+yjlb.getBaqid()+"开始检测打卡时间");
		//小时毫秒数
		int xs = 60 * 60 * 1000;
		int fz = 60 * 1000;
		// 获得当前打卡 开始时间 结束时间
		int stime = dc.getJckssj() * xs+dc.getJckssjfz()*fz;//从今天零点开始，流逝的毫秒数
		int ejgtime = stime;
		int etime = dc.getJcjssj() * xs+dc.getJcjssjfz()*fz;
		int dqtime = (int) (dq * 100) / 100 * xs + (int) (dq * 100) % 100 * 60
				* 1000;

		// 开始时间大于 结束时间 跨夜模式
		if (dc.getJckssj() > dc.getJcjssj()) {
			// 当前时间跨夜 并且到了第二天 时间比 检查开始时间小
			etime += 24 * xs;
			if (dqtime < dc.getJckssj()) {
				dqtime += 24 * xs;
			}
		}
		if(dqtime<stime||dqtime>etime){//不在时间范围内
			return;
		}
		int i = 1, jg = dc.getJcjg();
		int lsjg = jg * 60 * 1000;
		i = (dqtime-stime)/lsjg;
		if(i<=0){
			i=0;
		}
		for (;; i++) {
			ejgtime = stime + lsjg * i;
			// 在某个当间隔结束时间内时
			if (dqtime < ejgtime) {
				// 第一阶段不检查
				if (i == 1) {
					return;
				}
				etime = ejgtime - lsjg;
				stime = ejgtime - lsjg * 2;

				xgalarm(yjlb, stime, etime, xs, dq);
				break;
			}
			// 当间隔时间超过 结束检查时间时
			if (ejgtime > etime) {
				stime = ejgtime - lsjg;
				// ejgtime=etime;
				xgalarm(yjlb, stime, etime, xs, dq);
				break;
			}
		}
	}

	public void xgalarm(ChasYjlb yjlb,int stime ,int etime,int xs,double dq){
		Date kstime = null;
		Date jstime = null;
		// 开始时间 不跨夜 0 跨夜-1
		if (stime < 24 * xs) {
			kstime = getxgtime(stime, 0);
		} else {
			stime -= 24 * xs;
			kstime = getxgtime(stime, 0);
		}
		if (etime < 24 * xs) {
			jstime = getxgtime(etime, 0);
		} else {
			etime -= 24 * xs;
			jstime = getxgtime(etime, 0);
		}
		if(kstime.getTime()>jstime.getTime()){//凌晨时开始时间为昨天
			kstime.setTime(kstime.getTime()-24*60*60*1000);
		}
		String msg = yjlb.getBaqmc()+yjlb.getBaqid()+"当前检查巡更开始时间 " + DateTimeUtils.getDateStr(kstime,6)+ " 当前检查巡更结束时间 "+DateTimeUtils.getDateStr(jstime,6) +" 当前时间"+dq;
		log.info(msg);
		Map<String, Object> params = new HashMap<>();
		params.put("baqid", yjlb.getBaqid());
		params.put("kstime", DateTimeUtils.getDateStr(kstime, 15));
		params.put("jstime", DateTimeUtils.getDateStr(jstime,15));

		PageDataResultSet<ChasXgjl> xgjllist = chasXgjlService
				.getEntityPageData(1, 1, params, "lrsj desc");
		if (!xgjllist.getData().isEmpty()) {
			log.info(yjlb.getBaqmc()+yjlb.getBaqid()+"打卡间隔内已打卡");
			return;
		} else {
			// 查询间隔内的报警记录
			// 间隔内 已报警未处理 则不报警
			// 间隔内 已报警已处理 则不报警
			// 间隔内 未报警则报警 并记录
			params.clear();
			params.put("baqid", yjlb.getBaqid());
			params.put("kstime", DateTimeUtils.getDateStr(kstime, 15));
			//params.put("yjzt", SYSCONSTANT.YJZT_WCL);
			params.put("yjlb", SYSCONSTANT.YJLB_XG);
			PageDataResultSet<ChasYjxx> yjxxlist = chasYjxxService
					.getEntityPageData(1, 1, params, "lrsj desc");
			if (!yjxxlist.getData().isEmpty()) {
				log.info(yjlb.getBaqmc()+yjlb.getBaqid()+"巡更间隔内已警报过，不再触发。");
				return;
			}

			ChasYjxx chasYjxx = new ChasYjxx();
			chasYjxx.setId(StringUtils.getGuid32());
			chasYjxx.setBaqid(yjlb.getBaqid());
			chasYjxx.setBaqmc(yjlb.getBaqmc());
			chasYjxx.setLrsj(new Date());
			chasYjxx.setXgsj(new Date());
			chasYjxx.setJqms(String.format("办案区：[%s]在规定时间[%s-%s]内，值班民警没有打卡",yjlb.getBaqmc(),
					DateTimeUtils.getDateStr(kstime,6),
					DateTimeUtils.getDateStr(jstime,6)));
			chasYjxx.setYjjb(yjlb.getYjjb());
			chasYjxx.setYjlb(SYSCONSTANT.YJLB_XG);
			chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
			chasYjxx.setCfsj(jstime);
			chasYjxx.setCfqyid("b21380e56ca8bdde016ca8c57f2b0009");
			chasYjxx.setCfqymc("看守区");

			jdqService.sendYjxxmsg(chasYjxx,"巡更",
					yjlb.getYjfs(),yjlb.getYjsc());

			chasYjxxService.save(chasYjxx);

		}
	}
	//根据小时分钟毫秒数获取(今天或前一天)时间
	public static Date getxgtime(int sf,int i) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE,i);
		int yyyy = now.get(Calendar.YEAR);
		int mm =now.get(Calendar.MONTH) + 1;
		int dd=now.get(Calendar.DAY_OF_MONTH);
		int xs=60*60*1000;
		int xiaoshi=sf/xs;
		int fenzong=(sf%xs)/60/1000;
		String sj=yyyy+"-"+mm+"-"+dd+" "+(xiaoshi<10?"0"+xiaoshi:xiaoshi)+":"+(fenzong<10?"0"+fenzong:fenzong)+":"+"00";
		return  DateTimeUtils.parseDateTime(sj,"yyyy-MM-dd HH:mm:ss");
	}



}
