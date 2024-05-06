package com.wckj.chasstage.common.task;

import com.alibaba.fastjson.JSONArray;

import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 同案预警监测
 */
@Component("taTask")
public class TaTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(TaTask.class);



	public void taAlarm() {
		log.info("开始执行同案定时任务");
		try {
			List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_TA);
			if (yjlbs==null||yjlbs.isEmpty())
				return;
			Set<String> processed = new HashSet<>();
			yjlbs.stream().forEach(yjlb->{
				String baqid = yjlb.getBaqid();
				log.debug("开始轮询,同案预警" + baqid);
				//if(!processed.contains(yjlb.getBaqid())){
					processBaqTa(yjlb);
					//processed.add(yjlb.getBaqid());
				//}
			});
		} catch (Exception e) {
			log.error("同案预警", e);
			e.printStackTrace();
		}
	}

	//处理办案区同案信息
	private void processBaqTa(ChasYjlb yjlb){
		List<Map<String, Object>> lists = getDhsYjInfos(yjlb.getBaqid());
		if (lists == null || lists.size() <2) {
			log.debug("看守区未关押人员或者只有1人");
			return;
		}
		log.debug("等候室定位人员数：" + lists.size());
		Map<String, List<Map<String, Object>>> map = groupByQyAndYw(lists);
		// 参数时间停留多久算混关
		long tatlsj =getTatlsj();
		//当前时间
		long dqsj = new Date().getTime();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			//log.debug("处理同案"+key);
			List<Map<String, Object>> ryxxlist = map.get(key);
			processQyTaInfo(ryxxlist,dqsj,tatlsj,yjlb);

		}
	}
	//根据区域和业务分类
	private Map<String, List<Map<String, Object>>> groupByQyAndYw(List<Map<String, Object>> lists){
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		Set<String> rybhSet = new HashSet<>();
		for (Map<String, Object> qygjryxx : lists) {
			String rybh = qygjryxx.get("rybh")==null?"":qygjryxx.get("rybh").toString();
			if(StringUtils.isEmpty(rybh)){
				continue;
			}
			if(rybhSet.contains(rybh)){//已经处理了
				continue;
			}
			String qyid = qygjryxx.get("qyid")==null?"":qygjryxx.get("qyid").toString();
			if(StringUtils.isEmpty(qyid)){
				continue;
			}
			String ywbh = qygjryxx.get("ywbh")==null?"":qygjryxx.get("ywbh").toString();
			if (StringUtils.isNotEmpty(ywbh)) {
				String[] ywbhArray = ywbh.split(",");
				if(ywbhArray==null||ywbhArray.length<1){
					continue;
				}
				for(String y:ywbhArray){
					if(StringUtils.isEmpty(y)){
						continue;
					}
					String key = qyid + ","+ y;
					if (!map.containsKey(key)) {
						map.put(key, new ArrayList<>());
					}
					map.get(key).add(qygjryxx);
				}
				rybhSet.add(rybh);
			}
		}
		return map;
	}
	//获取同案停留时间
	private long getTatlsj(){
		long tatlsj =0;
		try {
			String closeTime = SysUtil.getParamValue("tatlsj");
			tatlsj=Long.valueOf(closeTime);
			tatlsj=tatlsj*60*1000;
		} catch (Exception e) {
			tatlsj=5*60*1000;
		}
		return tatlsj;
	}
	//处理区域同案信息
	private void processQyTaInfo(List<Map<String, Object>> ryxxlist,long dqsj,long tatlsj,ChasYjlb yjlb){

		String qyid = "";
		String qymc = "";
		JSONArray jsonhxm = new JSONArray();

		if (ryxxlist==null||ryxxlist.size() < 2) {
			return;
		}
		// 需要根据dhskz表中的分配信息判断是否是强制分配，如果是将不产生预警
		if(isAllZcFp(yjlb.getBaqid(),ryxxlist)){
			// 所有分配都是正常，不存在私自分配情况，不产生预警
			log.info("所有分配都是正常，不存在私自分配情况，不产生预警");
			return;
		}
		for (Map<String, Object> ryxx : ryxxlist) {
			qyid = (String) ryxx.get("qyid");
			qymc = (String) ryxx.get("qymc");
			Date kssj = (Date) ryxx.get("kssj");
			if ((dqsj - kssj.getTime()) >= tatlsj) {
				if(jsonhxm.toJSONString().length()<150){//防止数据长度超出数据库字段长度范围
					jsonhxm.add(ryxx.get("ryxm"));
				}
			}
		}
		log.info("同案处理结果"+jsonhxm.toJSONString());
		if(jsonhxm.size()<2){
			log.info("同案人员接触少于同案停留时间，不触发预警");
			return;
		}
		ChasYjxx chasYjxx = new ChasYjxx();
		chasYjxx.setId(StringUtils.getGuid32());
		chasYjxx.setBaqid(yjlb.getBaqid());
		chasYjxx.setBaqmc(yjlb.getBaqmc());
		chasYjxx.setLrsj(new Date());
		chasYjxx.setXgsj(new Date());
		chasYjxx.setJqms(String.format("%s是同案犯，关在同一房间[%s]",jsonhxm.toJSONString(),qymc));
		chasYjxx.setYjjb(yjlb.getYjjb());
		chasYjxx.setYjlb(SYSCONSTANT.YJLB_TA);
		chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
		chasYjxx.setCfsj(new Date());
		chasYjxx.setCfqyid(qyid);
		chasYjxx.setCfqymc(qymc);
		chasYjxx.setCfrxm(formatTaRyxm(jsonhxm,4));
		if(isNeedSave(chasYjxx)){
			jdqService.sendYjxxmsg(chasYjxx,"人员同案",
					yjlb.getYjfs(),yjlb.getYjsc());
		}
		saveYjxx(chasYjxx);
	}

	private static String formatTaRyxm(JSONArray jsonhxm,int maxRysl){
		if(maxRysl<=0){
			maxRysl=4;
		}
		StringBuilder sb = new StringBuilder();
		if(jsonhxm!=null && jsonhxm.size()>0){
			int min=Integer.min(4, jsonhxm.size());
			for(int i=0;i<min;i++){
				sb.append(jsonhxm.getString(i));
				if(i<min-1){
					sb.append(",");
				}
			}
			if(jsonhxm.size()>=4){
				sb.append(",...");
			}
		}
		return sb.toString();
	}
}
