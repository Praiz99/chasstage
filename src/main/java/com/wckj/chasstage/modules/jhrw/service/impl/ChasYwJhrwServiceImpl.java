package com.wckj.chasstage.modules.jhrw.service.impl;

import com.wckj.chasstage.api.def.jhrw.model.JhrwFpryParam;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.jhrw.dao.ChasYwJhrwMapper;
import com.wckj.chasstage.modules.jhrw.entity.ChasYwJhrw;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.jhrwjl.entity.ChasYwJhrwjl;
import com.wckj.chasstage.modules.jhrwjl.service.ChasYwJhrwjlService;
import com.wckj.chasstage.modules.jhry.entity.ChasYwJhry;
import com.wckj.chasstage.modules.jhry.service.ChasYwJhryService;
import com.wckj.chasstage.modules.jjrw.entity.ChasYwJjrw;
import com.wckj.chasstage.modules.jjrw.service.ChasYwJjrwService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scala.annotation.meta.param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChasYwJhrwServiceImpl extends BaseService<ChasYwJhrwMapper, ChasYwJhrw> implements ChasYwJhrwService {
	protected Logger log = LoggerFactory.getLogger(ChasYwJhrwServiceImpl.class);

	@Lazy
	@Autowired
	private ChasBaqryxxService ryxxService;
	@Autowired
	private ChasYwJjrwService jjrwService;
	@Autowired
	private ChasYwJhrwjlService jhrwjlService;
	@Autowired
	private ChasYwJhryService jhryService;
	@Autowired
	private JdoneSysUserService userService;
	@Lazy
	@Autowired
	@Qualifier("webSocketHandler")
	private SpringWebSocketHandler webSocketHandler;

	@Override
	public List<ChasYwJhrw> getJhrwByRybhAndRwlx(String rybh, String rwlx) {
		return baseDao.getJhrwByRybhAndRwlx(rybh,rwlx);
	}

	@Override
	@Transactional
	public DevResult saveJhrw(String yjr, String orgCode, ChasBaqryxx baqry, String rwlx, String jhrwqd, String jhrwzd) {
		DevResult wr = new DevResult();
		ChasYwJjrw jjrw = new ChasYwJjrw();
		ChasYwJhrw jhrw = new ChasYwJhrw();
		ChasYwJhrwjl jhrwjl = new ChasYwJhrwjl();
		try {
			List<ChasYwJhrw> jhrwList = getJhrwByRybhAndRwlx(baqry.getRybh(), rwlx);
			if(jhrwList.size()>0){
				jhrwList.get(0).setJhrwqd(jhrwqd);
				jhrwList.get(0).setJhrwzd(jhrwzd);
				update(jhrwList.get(0));
			}else{
				jhrw.setId(StringUtils.getGuid32());
				jhrw.setIsdel(0);
				jhrw.setBaqid(baqry.getBaqid());
				jhrw.setBaqmc(baqry.getBaqmc());
				jhrw.setDataFlag("0");
				jhrw.setLrsj(new Date());
				jhrw.setXgsj(new Date());
				jhrw.setLrrSfzh(baqry.getMjSfzh());
				jhrw.setXgrSfzh(baqry.getMjSfzh());
				jhrw.setRyxm(baqry.getRyxm());
				jhrw.setRybh(baqry.getRybh());
				jhrw.setRqyy(baqry.getRyzaymc());
				jhrw.setRqsj(baqry.getRRssj());
				jhrw.setRwlx(rwlx);
				jhrw.setRwzt(SYSCONSTANT.JHRWZT_DFP);
				jhrw.setJhrwqd(jhrwqd);
				jhrw.setJhrwzd(jhrwzd);
				jhrw.setDwxtbh(baqry.getDwxtbh());
				save(jhrw);
				//新增交接任务
				jjrw.setId(jhrw.getId());
				jjrw.setIsdel(0);
				jjrw.setBaqid(jhrw.getBaqid());
				jjrw.setBaqmc(jhrw.getBaqmc());
				jjrw.setDataFlag("0");
				jjrw.setLrsj(new Date());
				jjrw.setXgsj(new Date());
				jjrw.setLrrSfzh(baqry.getMjSfzh());
				jjrw.setXgrSfzh(baqry.getMjSfzh());
				jjrw.getDwdm(baqry.getZbdwBh());
				jjrw.setDwmc(baqry.getZbdwMc());
				jjrw.setXtdwdm(baqry.getDwxtbh());
				jjrw.setRwlx(jhrw.getRwlx());
				jjrw.setRybh(jhrw.getRybh());
				jjrw.setRymc(jhrw.getRyxm());
				jjrw.setYjr(yjr);
				jjrw.setYjsj(new Date());
				jjrwService.save(jjrw);

				//监护任务记录
				jhrwjl.setId(StringUtils.getGuid32());
				jhrwjl.setBaqid(baqry.getBaqid());
				jhrwjl.setBaqmc(baqry.getBaqmc());
				jhrwjl.setDataFlag("0");
				jhrwjl.setLrsj(new Date());
				jhrwjl.setXgsj(new Date());
				jhrwjl.setLrrSfzh(baqry.getMjSfzh());
				jhrwjl.setXgrSfzh(baqry.getMjSfzh());
				jhrwjl.setJhrwbh(jhrw.getId());
				jhrwjl.setRwzt(jhrw.getRwzt());
				jhrwjl.setJlsj(new Date());
				//jhrwjl.setRybh(jhrw.getRybh());
				//jhrwjl.setRyxm(jhrw.getRyxm());
				jhrwjlService.save(jhrwjl);
				webSocketHandler.sendLocaltion(orgCode,"true");
				wr.setCode(0);
				wr.setMessage("监护任务生成成功!");
			}
		} catch (Exception e) {
			log.error("saveJhrw:", e);
			throw new RuntimeException(e);
		}
		return wr;
	}

	@Override
	@Transactional
	public DevResult changeJhrwZt(String jsr,String orgCode,ChasBaqryxx baqry, String rwlx, String rwzt) {
		log.info("开始执行更改戒护任务类型："+rwzt+",任务状态："+rwzt+",人员编号："+baqry.getRybh()+",接收人："+jsr+",单位编号："+orgCode);
		DevResult wr = new DevResult();
		try {
			List<ChasYwJhrw> jhrwList = getJhrwByRybhAndRwlx(baqry.getRybh(),rwlx);
			log.info("获取到的戒护任务数量："+jhrwList.size());
			if(jhrwList.size()>0){
				jhrwList.get(0).setRwzt(rwzt);
				if("04".equals(rwzt)){
					jhrwList.get(0).setRwjssj(new Date());
				}
				update(jhrwList.get(0));

				//更新交接任务
				ChasYwJjrw jjrw = jjrwService.findById(jhrwList.get(0).getId());
				log.info("同步到的交接任务编号："+jjrw.getId());
				if(jjrw!=null){
					jjrw.setYjr(jhrwList.get(0).getJhry());
					jjrw.setYjsj(new Date());
					jjrw.setJsr(jsr);
					jjrw.setJssj(new Date());
					jjrwService.update(jjrw);
				}

				ChasYwJhrwjl jhrwjl = new ChasYwJhrwjl();
				jhrwjl.setId(StringUtils.getGuid32());
				jhrwjl.setDataFlag("0");
				jhrwjl.setLrsj(new Date());
				jhrwjl.setXgsj(new Date());
				jhrwjl.setLrrSfzh(baqry.getMjSfzh());
				jhrwjl.setXgrSfzh(baqry.getMjSfzh());
				jhrwjl.setBaqmc(jhrwList.get(0).getBaqmc());
				jhrwjl.setBaqid(jhrwList.get(0).getBaqid());
				jhrwjl.setRwzt(rwzt);
				jhrwjl.setRyxm(jhrwList.get(0).getJhry());
				jhrwjl.setRybh(jhrwList.get(0).getRybh());
				jhrwjl.setJlsj(new Date());
				jhrwjl.setJhrwbh(jhrwList.get(0).getId());
				jhrwjlService.save(jhrwjl);
				webSocketHandler.sendLocaltion(baqry.getBaqid(),"true");
				log.info("webSocket请求戒护大屏baq："+baqry.getBaqid());
				wr.setCode(0);
				wr.setMessage("监护任务状态变更!");
			}else{
				wr.setCode(0);
				wr.setMessage("监护任务未找到!");
			}
		} catch (Exception e) {
			log.error("changeJhrwZt:", e);
			throw new RuntimeException(e);
		}
		return wr;
	}

	@Override
	public PageDataResultSet<ChasYwJhrw> getDpData(int page, int rows, Map<String, Object> params) {
		return baseDao.getDpData(page,rows,params);
	}

	@Override
	public List<ChasYwJhrw> getJhrwByrwzt(String rwzt) {
		return baseDao.getJhrwByrwzt(rwzt);
	}

	@Override
	@Transactional
	public boolean accpectJhrw(String id){
		ChasYwJhrwjl jhrwjl = new ChasYwJhrwjl();
		try {
			ChasYwJhrw jhrw = findById(id);
			jhrw.setRwzt(SYSCONSTANT.JHRWZT_ZXZ);
			update(jhrw);
			//保存交接任务接收人
			ChasYwJjrw jjrw = jjrwService.findById(jhrw.getId());
			if(jjrw!=null){
				jjrw.setJsr(jhrw.getJhry());
				jjrw.setJssj(new Date());
				jjrwService.update(jjrw);
			}
			jhrwjl.setId(StringUtils.getGuid32());
			jhrwjl.setBaqid(jhrw.getBaqid());
			jhrwjl.setBaqmc(jhrw.getBaqmc());
			jhrwjl.setDataFlag("0");
			jhrwjl.setLrsj(new Date());
			jhrwjl.setXgsj(new Date());
			jhrwjl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
			jhrwjl.setXgrSfzh(WebContext.getSessionUser().getIdCard());
			jhrwjl.setJhrwbh(jhrw.getId());
			jhrwjl.setRwzt(jhrw.getRwzt());
			jhrwjl.setJlsj(new Date());
			jhrwjl.setRyxm(jhrw.getJhry());
			jhrwjlService.save(jhrwjl);
			return true;
		} catch (Exception e) {
			log.error("accpectTask:",e);
			throw new RuntimeException(e);
		}
	}
	@Override
	@Transactional
	public boolean deleteBatch(String ids){
		String baqid = null;
		try {
			String[] idstr = ids.split(",");
			for(String id : idstr){
				ChasYwJhrw jhrw = findById(id);
				baqid = jhrw.getBaqid();
				deleteById(id);
			}
			webSocketHandler.sendLocaltion(baqid,"true");
			return true;
		} catch (Exception e) {
			log.error("deleteJhrw:",e);
			throw new RuntimeException(e);
		}
	}
	@Override
	@Transactional
	public boolean saveJhry(String jhrwId,String rybhStr,String names) {
		JdoneSysUser user;
		//1、保存监护任务监护人员字段信息
		ChasYwJhrw jhrw = findById(jhrwId);
		//2、保存监护人员信息
		//更新监护任务
		try {
			jhrw.setRwzt(SYSCONSTANT.JHRWZT_ZXZ);
			jhrw.setRwkssj(new Date());
			//String jhrwry = Stream.of(names.split(",")).collect(Collectors.joining(","));
			jhrw.setJhry(names);
			update(jhrw);
			Map<String,Object> map = JsonUtil.jsonStringToMap(JsonUtil.getJsonString(jhrw));
			map.put("rwlxName", DicUtil.translate("jhrwlx", (String) map.get("rwlx")));
			webSocketHandler.sendLocaltion(jhrw.getBaqid(),JsonUtil.getJsonString(map));
			log.info("webSocket请求戒护大屏baq：" + jhrw.getBaqid());

			//更新交接任务
			ChasYwJjrw jjrw = jjrwService.findById(jhrw.getId());
			if (jjrw != null) {
				jjrw.setYjr(WebContext.getSessionUser().getName());
				jjrw.setYjsj(new Date());
				jjrwService.update(jjrw);
			}

			List<ChasYwJhrwjl> jlList = jhrwjlService.getJhrwjlByJhrwbhAndRwzt(jhrw.getId(), SYSCONSTANT.JHRWZT_ZXZ);
			if (jlList.size() > 0) {
				jlList.get(0).setRyxm(jhrw.getJhry());
				jlList.get(0).setJlsj(new Date());
				jhrwjlService.update(jlList.get(0));
			} else {
				ChasYwJhrwjl jhrwjl = new ChasYwJhrwjl();
				jhrwjl.setId(StringUtils.getGuid32());
				jhrwjl.setDataFlag("0");
				jhrwjl.setLrsj(new Date());
				jhrwjl.setXgsj(new Date());
				jhrwjl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
				jhrwjl.setXgrSfzh(WebContext.getSessionUser().getIdCard());
				jhrwjl.setBaqmc(jhrw.getBaqmc());
				jhrwjl.setBaqid(jhrw.getBaqid());
				jhrwjl.setRwzt(SYSCONSTANT.JHRWZT_ZXZ);
				jhrwjl.setRyxm(jhrw.getJhry());
				jhrwjl.setRybh(jhrw.getRybh());
				jhrwjl.setJlsj(new Date());
				jhrwjl.setJhrwbh(jhrw.getId());
				jhrwjlService.save(jhrwjl);
			}
			//删除原监护人员信息
			List<ChasYwJhry> jhryList = jhryService.getJhryByJhrwbh(jhrwId);
			for (ChasYwJhry rw: jhryList) {
				rw.setIsdel(1);
				jhryService.update(rw);
			}
			String[] rybhs = rybhStr.split(",");
			for (String rybh:rybhs) {
				user = userService.findSysUserByIdCard(rybh);
				//保存监护人员记录
				ChasYwJhry jhry = new ChasYwJhry();
				jhry.setId(StringUtils.getGuid32());
				jhry.setDataFlag("0");
				jhry.setLrsj(new Date());
				jhry.setXgsj(new Date());
				jhry.setLrrSfzh(WebContext.getSessionUser().getIdCard());
				jhry.setXgrSfzh(WebContext.getSessionUser().getIdCard());
				jhry.setBaqmc(jhrw.getBaqmc());
				jhry.setBaqid(jhrw.getBaqid());
				jhry.setJhrwbh(jhrwId);
				jhry.setDwdm(user.getOrgCode());
				jhry.setDwmc(user.getOrgName());
				jhry.setMjjh(user.getLoginId());
				jhry.setRybh(user.getIdCard());
				jhry.setRyxm(user.getName());
				jhryService.save(jhry);
			}
			return true;
		} catch (Exception e) {
			log.error("saveJhry:", e);
			throw new RuntimeException(e);
		}
	}
}
