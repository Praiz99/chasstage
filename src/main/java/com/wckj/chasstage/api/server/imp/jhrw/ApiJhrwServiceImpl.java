package com.wckj.chasstage.api.server.imp.jhrw;


import com.wckj.chasstage.api.def.jhrw.model.*;
import com.wckj.chasstage.api.def.jhrw.service.ApiJhrwService;
import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
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
import com.wckj.framework.core.dic.DicObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.com.dic.core.ComDicManager;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicCode;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicCodeService;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ApiJhrwServiceImpl implements ApiJhrwService {
	protected Logger log = LoggerFactory.getLogger(ApiJhrwServiceImpl.class);

	@Autowired
	private ChasYwJhrwService jhrwService;
	@Autowired
	private ChasBaqService baqService;
	//@Autowired
	//private ChasYwJjrwService jjrwService;

	@Autowired
	private ChasYwJhrwjlService jhrwjlService;

	@Autowired
	private JdoneSysUserService userService;

	@Autowired
	private ChasYwJhryService jhryService;
	@Autowired
	private ComDicManager dicManager;
	@Autowired
	private JdoneComDicCodeService codeService;

	//@Lazy
	//@Autowired
	//@Qualifier("webSocketHandler")
	//private SpringWebSocketHandler webSocketHandler;
	@Override
	public ApiReturnResult<?> get(String id) {
		ChasYwJhrw xgpz = jhrwService.findById(id);
		if(xgpz!=null){
			return ResultUtil.ReturnSuccess(xgpz);
		}
		return ResultUtil.ReturnError("无法根据id找到戒护任务信息");
	}
	@Override
	public ApiReturnResult<?> getPageData(JhrwParam param){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = MyBeanUtils.copyBean2Map(param);
		DataQxbsUtil.getSelectAll(jhrwService, params);
		if(StringUtil.isEmpty(param.getBaqid())){
			//String userRoleId = WebContext.getSessionUser().getRoleCode();
			//if (!"0101".equals(userRoleId)) {
				/*String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
				Map<String, Object> p= new HashMap<>();
				p.put("sydwdm", orgCode);
				List<ChasBaq> baqList = baqService.findListByParams(p);
				ChasBaq byParams = null;
				if (baqList.size() > 0) {
					byParams = baqList.get(0);
					params.put("baqid",byParams.getId());
				}
				if (byParams == null) {
					return ResultUtil.ReturnError("当前登录人单位，未配置办案区！");
				}*/
				String baqid =baqService.getZrBaqid();
				if(StringUtil.isEmpty(baqid)){
					return ResultUtil.ReturnError("当前登录人单位，未配置办案区！");
				}
				params.put("baqid",baqid);
			//}
		}
		DataQxbsUtil.getSelectAll(jhrwService, params);
		PageDataResultSet<ChasYwJhrw> list = jhrwService.getEntityPageData(param.getPage(),param.getRows(),params,"xgsj desc");
		result.put("total", list.getTotal());
		result.put("rows", DicUtil.translate(list.getData(), new String[]{"jhrwlx","jhrwzt"}, new String[]{"rwlx","rwzt"}));
		return ResultUtil.ReturnSuccess(result);
	}

	@Override
	public ApiReturnResult<?> getJhrwCount(JhrwParam param){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = new HashMap<>();
		params.put("rwzts","01,02,03".split(","));
		DataQxbsUtil.getSelectAll(jhrwService, params);
		PageDataResultSet<ChasYwJhrw> list = jhrwService.getEntityPageData(param.getPage(),param.getRows(),params,"xgsj desc");
		List<ChasYwJhrw> data = list.getData();
		result.put("total", list.getTotal());
		result.put("rows", DicUtil.translate(data, new String[]{"jhrwlx","jhrwzt"}, new String[]{"rwlx","rwzt"}));
		return ResultUtil.ReturnSuccess(result);
	}

	/**
	 * 删除戒护任务
	 * @param ids
	 * @return
	 */
	@Override
	public ApiReturnResult<?> deletes(String ids){
		try {
			if(jhrwService.deleteBatch(ids)){
				return ResultUtil.ReturnSuccess("删除成功!");
			}
		} catch (Exception e) {
			log.error("deleteJhrw:",e);
		}
		return ResultUtil.ReturnError("删除失败");
	}
	/**
	 * 戒护任务分配人员
	 * @param param
	 * @return
	 */
	@Override
	public ApiReturnResult<?> saveJhry(JhrwFpryParam param) {
		JdoneSysUser user;
		//1、保存监护任务监护人员字段信息
		ChasYwJhrw jhrw = jhrwService.findById(param.getJhrwId());
		if (jhrw == null) {
			return ResultUtil.ReturnError("未找到戒护任务");
		}

		try {
			if(jhrwService.saveJhry(param.getJhrwId(), param.getRybhs(), param.getNames())){
				return ResultUtil.ReturnSuccess("保存成功");
			}
		} catch (Exception e) {
			log.error("saveJhry:", e);

		}
		return ResultUtil.ReturnError("保存失败");
	}



	/**
	 * 领取任务
	 * @param id
	 * @return
	 */
	@Override
	public ApiReturnResult<?> accpectJhrw(String id){
		try {
			if(jhrwService.accpectJhrw(id)){
				return ResultUtil.ReturnSuccess("领取成功!");
			}
		} catch (Exception e) {
			log.error("accpectTask:",e);
		}
		return ResultUtil.ReturnError("领取失败");
	}


	@Override
	public ApiReturnResult<?> getUserData() {
		Map<String, Object> result = new HashMap<>(16);
		List<String> jhryResultList = new ArrayList<>();
		List<String> resultList = new ArrayList<>();
		SessionUser user = WebContext.getSessionUser();
		String orgCode = user.getOrgCode();
		String sysCode = user.getOrgSysCode();
		List<JdoneSysUser> list = userService.findUsersByOrgCode(orgCode,sysCode);
		List<ChasYwJhrw> jhrwList = jhrwService.getJhrwByrwzt(SYSCONSTANT.JHRWZT_ZXZ);
		for (ChasYwJhrw jhrw :jhrwList) {
			String jhrwId = jhrw.getId();
			List<ChasYwJhry> jhryList = jhryService.getJhryByJhrwbh(jhrwId);
			if(jhryList.size()>1){
				jhryResultList.add(jhryList.get(0).getRybh());
				jhryResultList.add(jhryList.get(1).getRybh());
			}
		}
		list.stream()
				.filter((JdoneSysUser sysUser) ->sysUser.getIdCard()!=null)
				.filter((JdoneSysUser sysUser) -> !jhryResultList.contains(sysUser.getIdCard()))
				.forEach((JdoneSysUser sysUser)->resultList.add(sysUser.getName()+","+sysUser.getIdCard()));
		result.put("resultList",resultList);
		return ResultUtil.ReturnSuccess(result);
	}

	/**
	 * 根据人员编号查询办案区人员列表流转记录
	 * @param rybh 编号
	 * @return
	 */
	@Override
	public ApiReturnResult<?> getJhrwjlByRybh(String rybh){
		Map<String,Object> result = new HashMap<>();
		List<JhrwjlInfoBean> infoList = new ArrayList<>();
		List<ChasYwJhrwjl> list = jhrwjlService.getJhrwjlByRybh(rybh);
		for (ChasYwJhrwjl jl : list) {
			ChasYwJhrw jhrw = jhrwService.findById(jl.getJhrwbh());
			JhrwjlInfoBean jhInfo = new JhrwjlInfoBean();
			jhInfo.setJlmc(jhrw.getJhrwqd()+"--"+jhrw.getJhrwzd());
			jhInfo.setJlsj(jl.getJlsj());
			jhInfo.setJhr(jl.getRyxm());
			//01入区戒护 02出区戒护 03体检戒护 04审讯戒护 05送押戒护 06审讯调度 07适格成年人调度
			jhInfo.setRwlx(DicUtil.translate("jhrwlx",jhrw.getRwlx()));
			//01待分配 02待执行 03执行中 04已执行
			jhInfo.setRwzt(DicUtil.translate("jhrwzt",jhrw.getRwzt()));
			infoList.add(jhInfo);
		}
		//result.put("success",true);
		result.put("data",infoList);
		//result.put("rows",infoList);
		//result.put("total",infoList.size());
		return ResultUtil.ReturnSuccess(result);
	}

	@Override
	public ApiReturnResult<?> getjlData(JhrwjlParam param){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = MyBeanUtils.copyBean2Map(param);
		String userRoleId = WebContext.getSessionUser().getRoleCode();
		if (!"0101".equals(userRoleId)) {
			String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
			params.put("sydwdm", orgCode);
		}
		if(param.getPage()==null||param.getPage()<1){
			param.setPage(1);
		}
		if(param.getRows()==null||param.getRows()<1){
			param.setRows(10);
		}
		DataQxbsUtil.getSelectAll(jhrwjlService, params);
		PageDataResultSet<ChasYwJhrwjl> list = jhrwjlService.getEntityPageData(param.getPage(),param.getRows(),params,"xgsj");
		result.put("total", list.getTotal());
		result.put("rows", DicUtil.translate(list.getData(), new String[]{"jhrwzt"}, new String[]{"rwzt"}));
		return ResultUtil.ReturnSuccess(result);
	}

	@Override
	public ApiReturnResult<?> getJhry(String jhrwId){
		List<ChasYwJhry> jhrylist = jhryService.getJhryByJhrwbh(jhrwId);
		if(jhrylist.size()>1){
			return ResultUtil.ReturnSuccess(jhrylist);
		}else{
			return ResultUtil.ReturnSuccess("暂未分配人员");
		}
	}

	@Override
	public ApiReturnResult<?> getDpData(JhrwParam p){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = MyBeanUtils.copyBean2Map(p);
		params.put("rwzt23", "0203");
		PageDataResultSet<ChasYwJhrw> list = jhrwService.getEntityPageData(p.getPage(),p.getRows(),params,"xgsj");
		result.put("total", list.getTotal());
		result.put("data", DicUtil.translate(list.getData(), new String[]{"jhrwlx","jhrwzt"}, new String[]{"rwlx","rwzt"}));
		return ResultUtil.ReturnSuccess(result);
	}

	@Override
	public ApiReturnResult<?> getJhrwlxDic(DicParam param){
		Map<String,Object> result = new HashMap<>(16);

		DicObj dicObj = dicManager.getDicObj("jhrwlx");
		Map<String,Object> params = new HashMap<>();
		params.put("dicId",dicObj.getId());
		List<JdoneComDicCode> codeObjs = codeService.findList(params,null);
		codeObjs=codeObjs.stream().filter(dic->
				"01".equals(dic.getCode())||
				"02".equals(dic.getCode())||
				"03".equals(dic.getCode())||
				"04".equals(dic.getCode()))
				.collect(Collectors.toList());
		if(StringUtils.isNotEmpty(param.getQueryValue())){
			codeObjs=codeObjs.stream().filter(dic->
					dic.getName().contains(param.getQueryValue()))
					.collect(Collectors.toList());
		}
		result.put("Total", codeObjs.size());
		codeObjs=codeObjs.stream().skip((param.getPage()-1)*param.getPagesize()).limit(param.getPagesize())
				.collect(Collectors.toList());
		result.put("Rows", codeObjs);
		return ResultUtil.ReturnSuccess(result);
	}

	/**
	 * 获取jhrwzt字典数据
	 * @param param
	 * @return
	 */
	@Override
	public ApiReturnResult<?> getJhrwztDic(DicParam param){
		Map<String,Object> result = new HashMap<>();
		DicObj dicObj = dicManager.getDicObj("jhrwzt");
		Map<String,Object> params = new HashMap<>();
		params.put("dicId",dicObj.getId());
		List<JdoneComDicCode> codeObjs = codeService.findList(params,null);
		codeObjs=codeObjs.stream().filter(dic->
				"01".equals(dic.getCode())||
						"03".equals(dic.getCode())||
						"04".equals(dic.getCode()))
				.collect(Collectors.toList());
		if(StringUtils.isNotEmpty(param.getQueryValue())){
			codeObjs=codeObjs.stream().filter(dic->
					dic.getName().contains(param.getQueryValue()))
					.collect(Collectors.toList());
		}
		result.put("Total", codeObjs.size());
		codeObjs=codeObjs.stream().skip((param.getPage()-1)*param.getPagesize()).limit(param.getPagesize())
				.collect(Collectors.toList());
		result.put("Rows", codeObjs);
		return ResultUtil.ReturnSuccess(result);
	}
}
