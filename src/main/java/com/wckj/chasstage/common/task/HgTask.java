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
 * 男女混关预警监测
 */
@Component("hgTask")
public class HgTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(HgTask.class);

	public void hgAlarm() {
		log.info("开始执行混关定时任务");
        try {
            List<ChasYjlb> yjlbs = getYjlbListByLb(SYSCONSTANT.YJLB_HG);
            if (yjlbs == null||yjlbs.isEmpty()){
                return;
            }
            Set<String> processed = new HashSet<>();
            yjlbs.stream().forEach(yjlb->{
                String baqid = yjlb.getBaqid();
                String baqmc =yjlb.getBaqmc();
                log.debug("开始轮询,混关预警" + baqid);
                //if(!processed.contains(baqid)){
                    processBaqHg(yjlb);
                   // processed.add(baqid);
               // }

            });
        } catch (Exception e) {
            log.error("混关定时任务出错", e);
            e.printStackTrace();
        }
    }
	//处理办案区混关预警
	private void processBaqHg(ChasYjlb yjlb){
		List<Map<String, Object>> lists = getDhsYjInfos(yjlb.getBaqid());
		if (lists == null || lists.size() == 0) {
			log.debug("看守区未关押人员");
			return;
		}
		log.debug("等候室定位人员数：" + lists.size());
		Map<String, List<Map<String, Object>>> map = groupByQyAndXb(lists);
		//已报警列表
		Map<String, String> ybj = new HashMap<>();
		//参数时间停留多久算混关
		//String msg = "";
		//msg = JsonUtil.getJsonString(map);
		long hgtlsj = getHgtlsj();
		long dqsj = new Date().getTime();
		//msg += "混关停留时间:"+hgtlsj;
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String ketsplit[] = key.split(",");
			if (ybj.containsKey(ketsplit[0])) {
				continue;
			}
			processQyHgInfo(key,map,dqsj,hgtlsj,yjlb);
			ybj.put(ketsplit[0], "");
		}
	}
	//根据区域和性别进行分类
	private Map<String, List<Map<String, Object>>> groupByQyAndXb(List<Map<String, Object>> lists){
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		Set<String> rybhSet = new HashSet<>();
		for (Map<String, Object> qygjryxx : lists) {
			String qyid = (String) qygjryxx.get("qyid");
			if(StringUtils.isEmpty(qyid)){
				continue;
			}
			String xb = (String) qygjryxx.get("xb");
			String key = qyid + "," + xb;
			String rybh = qygjryxx.get("rybh")==null?"":qygjryxx.get("rybh").toString();
			if(StringUtils.isEmpty(rybh)){
				continue;
			}
			if(rybhSet.contains(rybh)){//已经处理了
				continue;
			}
			//1男2女
			if ("1".equals(xb) || "2".equals(xb)) {
				if (!map.containsKey(key)) {
					map.put(key, new ArrayList<>());
				}
				map.get(key).add(qygjryxx);
				rybhSet.add(rybh);
			}

		}
		return map;
	}
	//获取混关停留时间
	private long getHgtlsj(){
		long hgtlsj = 0;

		try {
			String closeTime = SysUtil.getParamValue("hgtlsj");
			hgtlsj = Long.valueOf(closeTime);
			hgtlsj = hgtlsj * 60 * 1000;

		} catch (Exception e) {
			hgtlsj = 5 * 60 * 1000;
		}
		return hgtlsj;
	}
	//处理区域混关信息
	private void processQyHgInfo(String key,Map<String, List<Map<String, Object>>> map,long dqsj,long hgtlsj,
								 ChasYjlb yjlb) {
		log.info("处理混关"+key);
		String ketsplit[] = key.split(",");
		String hgxb = ketsplit[0] + "," + ("1".equals(ketsplit[1]) ? "2" : "1");
		//如果另一个性别的也存在 则报警
		if (map.containsKey(hgxb)) {
			String qyid = "";
			String qymc = "";
			if(isAllZcFp(yjlb.getBaqid(),map.get(key))&&isAllZcFp(yjlb.getBaqid(),map.get(hgxb))){
				// 所有分配都是正常，不存在私自分配情况，不产生预警
				log.info("所有分配都是正常，不存在私自分配情况，不产生预警");
				return;
			}
			JSONArray jsonhxm = new JSONArray();
			for (Map<String, Object> ryxx : map.get(key)) {
				qyid = (String) ryxx.get("qyid");
				qymc = (String) ryxx.get("qymc");
				Date kssj = (Date) ryxx.get("kssj");
				if ((dqsj - kssj.getTime()) >= hgtlsj) {
					if(jsonhxm.size()<=3){
						jsonhxm.add(ryxx.get("ryxm"));
					}else{
						break;
					}

				}

			}
			JSONArray jsongxm = new JSONArray();
			for (Map<String, Object> ryxx : map.get(hgxb)) {
				Date kssj = (Date) ryxx.get("kssj");
				if ((dqsj - kssj.getTime()) >= hgtlsj) {
					if(jsongxm.size()<=3){//防止数据超出数据库字段长度
						jsongxm.add(ryxx.get("ryxm"));
					}else{
						break;
					}
				}
			}
			if(jsonhxm.size()<1||jsongxm.size()<1){
				log.info("混关人员接触少于混关停留时间，不触发预警");
				return;
			}
			String msg = String.format("%s和%s，异性关在[%s]",jsonhxm.toJSONString(),jsongxm.toJSONString(),qymc);
			log.info("混关结果"+msg);
			ChasYjxx chasYjxx = new ChasYjxx();
			chasYjxx.setId(StringUtils.getGuid32());
			chasYjxx.setBaqid(yjlb.getBaqid());
			chasYjxx.setBaqmc(yjlb.getBaqmc());
			chasYjxx.setLrsj(new Date());
			chasYjxx.setXgsj(new Date());
			chasYjxx.setJqms(msg);
			chasYjxx.setYjjb(yjlb.getYjjb());
			chasYjxx.setYjlb(SYSCONSTANT.YJLB_HG);
			chasYjxx.setYjzt(SYSCONSTANT.YJZT_WCL);
			chasYjxx.setCfsj(new Date());
			chasYjxx.setCfqyid(qyid);
			chasYjxx.setCfqymc(qymc);
			jsonhxm.addAll(jsongxm);
			chasYjxx.setCfrxm(formatHgRyxm(jsonhxm,6));
			if(isNeedSave(chasYjxx)){
				jdqService.sendYjxxmsg(chasYjxx,"人员混关",
						yjlb.getYjfs(),yjlb.getYjsc());
			}
			saveYjxx(chasYjxx);
		}
	}
	private static String formatHgRyxm(JSONArray jsonhxm,int maxRysl){
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
