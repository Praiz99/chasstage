package com.wckj.chasstage.modules.ythcj.service.impl;

import com.wckj.api.def.zfba.model.ApiGgRyzjlb;
import com.wckj.api.def.zfba.service.gg.ApiGgRyxxService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.chasstage.modules.pcry.service.ChasYwPcryService;
import com.wckj.chasstage.modules.ythcj.dao.ChasYthcjMapper;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjShgx;
import com.wckj.chasstage.modules.ythcj.entity.ChasYthcjWlxx;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjService;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjShgxService;
import com.wckj.chasstage.modules.ythcj.service.ChasYthcjWlxxService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYthcjServiceImpl extends BaseService<ChasYthcjMapper, ChasYthcj> implements ChasYthcjService {

	private static final Logger log = Logger.getLogger(ChasYthcjServiceImpl.class);
	@Autowired
	private ChasBaqryxxService chasBaqryxxService;
	@Autowired
	private ChasYthcjWlxxService chasYthcjWlxxService;
	@Autowired
	private ChasYthcjShgxService chasYthcjShgxService;
	@Autowired
	private ChasYwPcryService pcryService;

	/**
	 * @param societyJson
	 *            社会关系json字符串
	 * @param delNetIds
	 *            网络关系删除ids
	 * @param delSocietyIds
	 *            社会关系删除ids
	 */
	@Override
	public Map<String, Object> saveYthcjRyxx(ChasYthcj formYthcj,String netJson, String societyJson, String delNetIds,String delSocietyIds, String fromSign) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		SessionUser user = WebContext.getSessionUser();
		Map<String, Object> result = new HashMap<String, Object>();
		String useRybh = null;// 人员编号 用于网络信息和社会关系保存
		String useRyid = null;
		String useXm = null;
		String ryId = formYthcj.getId();// 页面传过来的id
		try {
			if (SYSCONSTANT.FROMSIGNBAQRYXX.equals(fromSign) || SYSCONSTANT.FROMSIGNJQRYXX.equals(fromSign)) {
				// 办案区人员信息采集
				ChasYthcj chasYthcj = findById(ryId);
				if (chasYthcj != null) {
					// 修改
					useRybh = chasYthcj.getRybh();
					useXm = chasYthcj.getRyxm();
					param.put("rybh", formYthcj.getRybh());
					if (StringUtils.isNotEmpty(formYthcj.getRybh())) {
						List<ChasBaqryxx> baqRyxxList = chasBaqryxxService.findList(param, "");
						ChasBaqryxx chasBaqRyxx = null;
						if (baqRyxxList.size() > 0) {
							chasBaqRyxx = baqRyxxList.get(0);
							chasBaqRyxx.setSfythcj(SYSCONSTANT.Y_I);
							chasBaqryxxService.update(chasBaqRyxx);
						}
					}
					MyBeanUtils.copyBeanNotNull2Bean(formYthcj, chasYthcj);
					update(chasYthcj);
					result.put("success", true);
					result.put("msg", "保存成功!");
				} else {
					// 新增
					param.put("rybh", formYthcj.getRybh());
					if (StringUtils.isEmpty(formYthcj.getRybh()))
						return null;
					List<ChasBaqryxx> baqRyxxList = chasBaqryxxService.findList(param, "");
					ChasBaqryxx chasBaqRyxx = null;
					if (baqRyxxList.size() > 0) {
						chasBaqRyxx = baqRyxxList.get(0);
						useRybh = chasBaqRyxx.getRybh();
						useXm = chasBaqRyxx.getRyxm();
						formYthcj.setDataflag("0");
						// formYthcj.setRybh(chasBaqRyxx.getRybh());
						//formYthcj.setId(StringUtils.getGuid32());
						formYthcj.setId(ryId);  //一体化采集刷新新页面后，返回当前ID。
						formYthcj.setLrrSfzh(user == null ? "" : user.getIdCard());
						formYthcj.setXgrSfzh(user == null ? "" : user.getIdCard());
						formYthcj.setZbdwBh(chasBaqRyxx.getZbdwBh());
						formYthcj.setZbdwMc(chasBaqRyxx.getZbdwMc());
						formYthcj.setBaqid(chasBaqRyxx.getBaqid());
						formYthcj.setBaqmc(chasBaqRyxx.getBaqmc());
						// formYthcj.setDwxtbh(DicUtil.translate("JDONE_SYS_SYSCODE",
						// chasBaqRyxx.getZbdwBh()));
						formYthcj.setDwxtbh(chasBaqRyxx.getDwxtbh());
						save(formYthcj);
						chasBaqRyxx.setSfythcj(1);
						chasBaqRyxx.setSftb(1);
						chasBaqRyxx.setTjxgrsfzh(user == null ? "" : user.getIdCard());
						chasBaqryxxService.update(chasBaqRyxx);
						result.put("success", true);
						result.put("msg", "保存成功!");
					}
					//查询是否存在盘查人员，如果有就联动修改盘查人员的采集状态
					List<ChasYwPcry> pcryList = pcryService.findList(param, "");
					if(!pcryList.isEmpty()){
						ChasYwPcry pcry = pcryList.get(0);
						pcry.setYthcjzt(1);
						pcryService.update(pcry);
					}
				}
			} else if (SYSCONSTANT.FROMSIGNPCRY.equals(fromSign)) {
				// 盘查人员信息采集
				ChasYthcj chasYthcj = findById(ryId);
				if (chasYthcj != null) {
					// 修改
					useRybh = chasYthcj.getRybh();
					useXm = chasYthcj.getRyxm();
					param.put("rybh", formYthcj.getRybh());
					if (StringUtils.isNotEmpty(formYthcj.getRybh())) {
						List<ChasBaqryxx> baqRyxxList = chasBaqryxxService.findList(param, "");
						ChasBaqryxx chasBaqRyxx = null;
						if (baqRyxxList.size() > 0) {
							chasBaqRyxx = baqRyxxList.get(0);
							chasBaqRyxx.setSfythcj(SYSCONSTANT.Y_I);
							chasBaqryxxService.update(chasBaqRyxx);
						}
					}
					MyBeanUtils.copyBeanNotNull2Bean(formYthcj, chasYthcj);
					update(chasYthcj);
					result.put("success", true);
					result.put("msg", "保存成功!");
				} else {
					// 新增
					param.put("rybh", formYthcj.getRybh());
					if (StringUtils.isEmpty(formYthcj.getRybh()))
						return null;
					List<ChasYwPcry> pcryList = pcryService.findList(param, "");
					ChasYwPcry chasPcry = null;
					if (pcryList.size() > 0) {
						chasPcry = pcryList.get(0);
						useRybh = chasPcry.getRybh();
						useXm = chasPcry.getRyxm();
						// useRyid = peopleId;
						// formYthcj.setRybh(chasPcry.getRybh());
						//formYthcj.setId(StringUtils.getGuid32());
						formYthcj.setId(ryId);  //一体化采集刷新新页面后，返回当前ID。
						formYthcj.setDataflag("0");
						formYthcj.setLrrSfzh(user == null ? "" : user.getIdCard());
						formYthcj.setXgrSfzh(user == null ? "" : user.getIdCard());
						if(StringUtils.isNotEmpty(user.getOrgCode())){
							formYthcj.setZbdwBh(user.getOrgCode());
							formYthcj.setZbdwMc(user.getOrgName());
						}
						formYthcj.setBaqid(chasPcry.getBaqid());
						formYthcj.setBaqmc(chasPcry.getBaqmc());
						if(StringUtils.isNotEmpty(user.getOrgSysCode())){
							formYthcj.setDwxtbh(user.getOrgSysCode());
						}
						if(StringUtils.isEmpty(formYthcj.getDwxtbh())){
							result.put("success", false);
							result.put("msg", "保存失败:系统单位编号不存在!");
							return result;
						}
						save(formYthcj);
						chasPcry.setYthcjzt(1);
						chasPcry.setXgrSfzh(user == null ? "" : user.getIdCard());
						pcryService.update(chasPcry);
						result.put("success", true);
						result.put("msg", "保存成功!");
					}
					//查询是否有办案区人员，如果有，那么就联动修改办案区人员采集状态
					List<ChasBaqryxx> baqryxxes = chasBaqryxxService.findList(param,null);
					if(!baqryxxes.isEmpty()){
						ChasBaqryxx baqryxx = baqryxxes.get(0);
						baqryxx.setSfythcj(1);
						chasBaqryxxService.update(baqryxx);
					}
				}
			}else if(SYSCONSTANT.FROMSIGNXYR.equals(fromSign)){
				// 办案区人员信息采集
				ChasYthcj chasYthcj = findById(ryId);
				if (chasYthcj != null) {
					// 修改
					useRybh = chasYthcj.getRybh();
					useXm = chasYthcj.getRyxm();
					MyBeanUtils.copyBeanNotNull2Bean(formYthcj, chasYthcj);
					update(chasYthcj);
					result.put("success", true);
					result.put("msg", "保存成功!");
				} else {
					// 新增
					if (StringUtils.isEmpty(formYthcj.getRybh()))
						return null;
					ApiGgRyxxService apiGgRyxxService = ServiceContext.getServiceByClass(ApiGgRyxxService.class);
					ApiGgRyzjlb xyrxx = apiGgRyxxService.getRyxxByRybh(formYthcj.getRybh(), ApiGgRyzjlb.class);
					if(xyrxx != null){
						useRybh = xyrxx.getRybh();
						useXm = xyrxx.getRyXm();
						formYthcj.setDataflag("0");
						formYthcj.setId(ryId);  //一体化采集刷新新页面后，返回当前ID。
						formYthcj.setLrrSfzh(user == null ? "" : user.getIdCard());
						formYthcj.setXgrSfzh(user == null ? "" : user.getIdCard());
						formYthcj.setZbdwBh(user.getCurrentOrgCode());
						formYthcj.setZbdwMc(user.getCurrentOrgName());
						formYthcj.setBaqid("");
						formYthcj.setBaqmc("");
						formYthcj.setDwxtbh(user.getCurrentOrgSysCode());
						save(formYthcj);
						result.put("success", true);
						result.put("msg", "保存成功!");
					}
				}
			}
			if(StringUtil.isNotEmpty(netJson)){
				// 网络信息处理
				JSONObject netJsonObject = JSONObject.fromObject(netJson);
				if (netJsonObject != null && !netJsonObject.isEmpty()) {
					JSONObject chasNetJson = JSONObject.fromObject(netJsonObject);
					int size = chasNetJson.size();
					for (int i = 0; i < size; i++) {
						JSONObject o = (JSONObject) chasNetJson.get("" + i);
						ChasYthcjWlxx temp = (ChasYthcjWlxx) JSONObject.toBean(o,ChasYthcjWlxx.class);
						temp.setXgrSfzh(user == null ? "" : user.getIdCard());
						temp.setRyxxId(ryId);
						if (StringUtils.isNotEmpty(temp.getId())) {// 修改
							ChasYthcjWlxx chasWlxx = chasYthcjWlxxService.findById(temp.getId());
							MyBeanUtils.copyBeanNotNull2Bean(temp, chasWlxx);
							chasWlxx.setXgsj(new Date());
							chasYthcjWlxxService.update(chasWlxx);
							result.put("success", true);
							result.put("msg", "保存成功!");
						} else {// 新增
							temp.setId(StringUtils.getGuid32());
							temp.setLrsj(new Date());
							temp.setLrrSfzh(user == null ? "" : user.getIdCard());
							temp.setXgsj(new Date());
							temp.setXgrSfzh(user == null ? "" : user.getIdCard());
							temp.setRybh(useRybh);
							temp.setRyxm(useXm);
							temp.setRyxxId(useRyid);
							chasYthcjWlxxService.save(temp);
							result.put("success", true);
							result.put("msg", "保存成功!");
						}
					}
				}
			}
			if(StringUtil.isNotEmpty(delNetIds)){
				// 删除的数据的处理
				if (StringUtils.isNotEmpty(delNetIds)) {
					String[] idstr = delNetIds.split(",");
					for (String id : idstr) {
						chasYthcjWlxxService.deleteById(id);
					}
					result.put("success", true);
					result.put("msg", "保存成功!");
				}
			}
			if(StringUtil.isNotEmpty(societyJson)){
				// 社会关系信息处理
				JSONObject societyJsonObject = JSONObject.fromObject(societyJson);
				if (societyJsonObject != null && !societyJsonObject.isEmpty()) {
					JSONObject chasSocietyJson = JSONObject.fromObject(societyJsonObject);
					int size = chasSocietyJson.size();
					for (int i = 0; i < size; i++) {
						JSONObject o = (JSONObject) chasSocietyJson.get("" + i);
						ChasYthcjShgx temp = (ChasYthcjShgx) JSONObject.toBean(o,ChasYthcjShgx.class);
						temp.setXgrSfzh(user == null ? "" : user.getIdCard());
						temp.setRyxxId(ryId);
						if (StringUtils.isNotEmpty(temp.getId())) {// 修改
							ChasYthcjShgx chasShgx = chasYthcjShgxService.findById(temp.getId());
							MyBeanUtils.copyBeanNotNull2Bean(temp, chasShgx);
							chasShgx.setXgsj(new Date());
							chasYthcjShgxService.update(chasShgx);
							result.put("success", true);
							result.put("msg", "保存成功!");
						} else {// 新增
							temp.setId(StringUtils.getGuid32());
							temp.setLrsj(new Date());
							temp.setLrrSfzh(user == null ? "" : user.getIdCard());
							temp.setXgsj(new Date());
							temp.setXgrSfzh(user == null ? "" : user.getIdCard());
							temp.setRybh(useRybh);
							temp.setRyxxId(useRyid);
							chasYthcjShgxService.save(temp);
							result.put("success", true);
							result.put("msg", "保存成功!");
						}
					}
				}
			}
			if(StringUtil.isNotEmpty(delSocietyIds)){
				// 删除的数据的处理
				if (StringUtils.isNotEmpty(delSocietyIds)) {
					String[] idstr = delSocietyIds.split(",");
					for (String id : idstr) {
						chasYthcjShgxService.deleteById(id);
					}
					result.put("success", true);
					result.put("msg", "保存成功!");
				}
			}
		} catch (Exception e) {
			log.error("saveYthcjRyxx:", e);
			result.put("success", false);
			result.put("msg", "系统异常!");
			throw e;
		}
		return result;
	}

	@Override
	public void deleteByPrimaryRbyh(String rybh) {
		baseDao.deleteByPrimaryRbyh(rybh);
	}
}
