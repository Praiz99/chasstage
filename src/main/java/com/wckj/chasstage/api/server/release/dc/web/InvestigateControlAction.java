package com.wckj.chasstage.api.server.release.dc.web;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.delaytask.DelayQueueManager;
import com.wckj.chasstage.common.delaytask.DelayTask;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *笔录结束调用接口
 *
 */
@Controller
@RequestMapping("/device/interface/investigateControlAction")
public class InvestigateControlAction {
	final static Logger log = Logger.getLogger(InvestigateControlAction.class);
	@Autowired
	private ChasRyjlService chasRyjlService;
	@Autowired
	private ChasBaqryxxService chasBaqryxxService;
	@Autowired
	private ChasSxsKzService chasSxsKzService;
	@Autowired
	private ChasSxsglService chasSxsglService;
//	@Autowired
//	private ILedService ledService;
//	@Autowired
//	private ChasDhsKzService chasDhsKzService;
	@Autowired
	private DelayQueueManager delayQueueManager;
	/**
	 * 结束笔录记录，笔录软件接口.核对后触发。
	 * @return
	 */
	@RequestMapping("endNote2")
	@ResponseBody
	public DevResult endNote(String peopleId, String id, String ip, String jxyd, String police) {
		String rybh =peopleId;
		DevResult wr= new DevResult();
		String msg=String.format("调用笔录结束 传入的人员编号是:%s，身份证号：%s，继续用电标志:%s，主办民警姓名：%s,电脑IP：%s", rybh, id, jxyd,police,ip);
		log.debug(msg);
		Map<String, Object> map = new HashMap<>();
		//验证 身份证和人员ID
		if(StringUtils.isEmpty(rybh)&& StringUtils.isEmpty(id)){
			msg=String.format("调用笔录结束 人员编号 身份证不能为空 !主办民警%s电脑IP%s", police,ip);
			wr.setCode(-1);
			wr.setMessage(msg);
			log.error(msg);
			return wr;
		}
		//根据身份证号查ryxx
		if(StringUtils.isNotEmpty(id)){
			map.clear();
			map.put("rysfzh", id);
			//非出所状态
			map.put("ryzts", SYSCONSTANT.BAQRYZT_YCS);
			List<ChasBaqryxx> chasBaqryxx	= chasBaqryxxService.findList(map, null);
			if(!chasBaqryxx.isEmpty()){
				rybh=chasBaqryxx.get(0).getRybh();
			}
		}
		//根据人员编号查询人员记录
		ChasRyjl chasRyjl=null;
		if(StringUtils.isNotEmpty(rybh)){
			map.clear();
			map.put("rybh", rybh);
			map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
			chasRyjl=chasRyjlService.findByParams(map);
		}
		//没有调查记录
		if(chasRyjl==null){
			msg=String.format("调用笔录结束 %s没有调查记录",rybh);
			wr.setCode(-1);
			wr.setMessage(msg);
			log.error(msg);
			return wr;
		}
		if(chasRyjl.getSxsBh()==null){
			msg=String.format("调用笔录结束 %s 未分配审讯室 无法结束笔录",rybh);
			wr.setCode(-1);
			wr.setMessage(msg);
			log.error(msg);
			return wr;
		}
		//查询审讯室
		ChasSxsKz chasSxsKz=null;
		chasSxsKz =chasSxsKzService.findAllById(chasRyjl.getSxsBh());
		if(chasSxsKz==null){
			msg=String.format("调用笔录结束 %s 查不到审讯室分配纪录",rybh);
			wr.setCode(-1);
			wr.setMessage(msg);
			log.error(msg);
			return wr;
		}
		if(chasSxsKz.getHdsj()!=null){
			msg=String.format("%s笔录已经核对结束，不需要重复核对",rybh);
			wr.setCode(1);
			wr.setMessage(msg);
			log.error(msg);
			return wr;
		}
		//结束笔录
		//结束笔录 继续用电
		if (SYSCONSTANT.Y.equals(jxyd)) {
			chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_JX);
			chasSxsKz.setJxyd(jxyd);
		}else{
			chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_TY);//停用，需要延时关闭审讯室电源，已改成调用延时任务
			chasSxsKz.setDyzt(SYSCONSTANT.SXSZT_TY);
		}
		ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
		chasSxsKz.setIsdel(SYSCONSTANT.Y_I);//防止在更新led时，认为人员还在审讯中而显示错误的led信息
		chasSxsKz.setHdsj(new Date());
		chasSxsKz.setXgrSfzh(baqryxx.getMjSfzh());
		chasSxsKzService.update(chasSxsKz);
		if(SYSCONSTANT.Y.equals(jxyd)){//立即更新为民警正在办公
			//更新审讯室led信息
			//新建分配记录,用于后台审讯室列表界面
			ChasSxsKz sxsKz = new ChasSxsKz();
			sxsKz.setId(StringUtils.getGuid32());
			sxsKz.setSymj(DicUtil.translate("JDONE_SYS_USER",chasSxsKz.getLrrSfzh()));
			sxsKz.setFpzt(SYSCONSTANT.SXSZT_SY);
			sxsKz.setDyzt(SYSCONSTANT.SXSZT_SY);
			sxsKz.setBaqid(chasSxsKz.getBaqid());
			sxsKz.setBaqmc(chasSxsKz.getBaqmc());
			sxsKz.setSymj(chasSxsKz.getSymj());
			sxsKz.setQyid(chasSxsKz.getQyid());
			sxsKz.setJxyd(jxyd);
			sxsKz.setFpzt(SYSCONSTANT.SXSZT_JX);
			sxsKz.setKssj(new Date());
			sxsKz.setLrsj(new Date());
			sxsKz.setLrrSfzh(baqryxx.getLrrSfzh());
			sxsKz.setXgrSfzh(baqryxx.getLrrSfzh());
			chasSxsKzService.save(sxsKz);
			chasSxsglService.sxsGx(chasSxsKz.getBaqid(), chasSxsKz.getId());
		}else{//不继续用电
			log.info("调用延时断电+"+chasSxsKz.getId());
			delayQueueManager.put(DelayTask.buildTask(chasSxsKz.getId()));
		}
		wr.setCode(0);
		wr.setMessage("笔录记录完成");
		return wr;
	}
/*
	@RequestMapping("testdd")
	@ResponseBody
	public DevResult testdd() {
		DevResult wr= new DevResult();
		SxsOutTime out=ServiceContext.getServiceByClass(SxsOutTime.class);
		out.outTime();
		wr.setCode(0);
		wr.setMessage("测试审讯室断电完成");
		return wr;
	}
*/
}
