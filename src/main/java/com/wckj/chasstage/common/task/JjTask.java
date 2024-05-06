package com.wckj.chasstage.common.task;

import com.alibaba.fastjson.JSONArray;

import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 人员预警监测
 */
@Component("ryjjTask")
public class JjTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(JjTask.class);

	public void jjAlarm() {
		log.info("开始执行人员聚集定时任务");
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_JJ);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				String baqid = yjlb.getBaqid();
				//log.debug("开始轮询,人员聚集" + baqid);
				//if(!processed.contains(baqid)){
					processBaqJj(yjlb);
					//processed.add(baqid);
				//}
			});
		} catch (Exception e) {
			log.error("人员聚集出错", e);
			e.printStackTrace();
		}
	}

	//处理办案区人员聚集信息
	private void processBaqJj(ChasYjlb yjlb){
		List<Map<String, Object>> lists = getRyjujiYjInfos(yjlb.getBaqid());
		if (lists == null || lists.size() == 0) {
			log.debug("办案区区未关押人员");
			return;
		}
		log.debug("区域定位人员数：" + lists.size());
		Map<String, List<Map<String, Object>>> map = groupByQy(lists);
		// 参数时间停留多久算混关
		long jjtlsj =getJjtlsj();
		//当前时间
		long dqsj = new Date().getTime();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			List<Map<String, Object>> ryxxlist = map.get(key);
			processQyJjInfo(ryxxlist,dqsj,jjtlsj,yjlb);
		}
	}
	//根据区域和业务分类
	private Map<String, List<Map<String, Object>>> groupByQy(List<Map<String, Object>> lists){
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		for (Map<String, Object> qygjryxx : lists) {
			String qyid = qygjryxx.get("id").toString();
			String key = qyid;
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<>());
			}
			map.get(key).add(qygjryxx);
		}
		return map;
	}
	//获取人员聚集停留时间
	private long getJjtlsj(){
		long tatlsj =0;
		try {
			String closeTime = SysUtil.getParamValue("jjtlsj");
			tatlsj=Long.valueOf(closeTime);
			tatlsj=tatlsj*60*1000;
		} catch (NumberFormatException e) {
			tatlsj=5*60*1000;
		}
		return tatlsj;
	}
	//处理区域同案信息
	private void processQyJjInfo(List<Map<String, Object>> ryxxlist,long dqsj,long tatlsj,ChasYjlb yjlb){
		String qyid = "";
		String qymc = "";
		JSONArray jsonRy = new JSONArray();

		if (ryxxlist==null||ryxxlist.size() < 2) {
			return;
		}
		if(isAllZcFp(yjlb.getBaqid(),ryxxlist)){
			// 所有分配都是正常，不存在私自分配情况，不产生预警
			return;
		}
		for (Map<String, Object> ryxx : ryxxlist) {
			qyid = (String) ryxx.get("id");
			qymc = (String) ryxx.get("qymc");
			Date kssj = (Date) ryxx.get("kssj");
			if ((dqsj - kssj.getTime()) >= tatlsj) {
				jsonRy.add(ryxx.get("rybh"));
			}
		}
		Map<String, Object> ryxx = ryxxlist.get(0);
		String id =ryxx.get("id").toString();
		ChasXtQy qy = qyService.findByYsid(id);
		int rysl = qy.getRysl()==null? 0 :qy.getRysl();
		if(rysl > 0&&jsonRy.size()>rysl){
			ChasYjxx chasYjxx = new ChasYjxx();
			chasYjxx.setId(StringUtils.getGuid32());
			chasYjxx.setBaqid(yjlb.getBaqid());
			chasYjxx.setBaqmc(yjlb.getBaqmc());
			chasYjxx.setLrsj(new Date());
			chasYjxx.setXgsj(new Date());
			chasYjxx.setJqms(String.format("区域[%s]目前人数[%d]超出最大人数",qymc,jsonRy.size()));
			chasYjxx.setYjjb(yjlb.getYjjb());
			chasYjxx.setYjlb(SYSCONSTANT.YJLB_JJ);
			chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
			chasYjxx.setCfsj(new Date());
			chasYjxx.setCfqyid(qyid);
			chasYjxx.setCfqymc(qymc);
			if(isNeedSave(chasYjxx)){
				jdqService.sendYjxxmsg(chasYjxx,"人员聚集",
						yjlb.getYjfs(),yjlb.getYjsc());
			}
			saveYjxx(chasYjxx);
		}
	}
}
