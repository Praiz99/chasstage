package com.wckj.chasstage.api.def.jhrw.service;


import com.wckj.chasstage.api.def.jhrw.model.*;
import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.jhrw.entity.ChasYwJhrw;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.jhrwjl.entity.ChasYwJhrwjl;
import com.wckj.chasstage.modules.jhrwjl.service.ChasYwJhrwjlService;
import com.wckj.chasstage.modules.jhry.entity.ChasYwJhry;
import com.wckj.chasstage.modules.jhry.service.ChasYwJhryService;
import com.wckj.chasstage.modules.jjrw.entity.ChasYwJjrw;
import com.wckj.chasstage.modules.jjrw.service.ChasYwJjrwService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface ApiJhrwService extends IApiBaseService {

	ApiReturnResult<?> get(String id);
	ApiReturnResult<?> getPageData(JhrwParam param);
	/**
	 * 删除戒护任务
	 *
	 * @param ids
	 * @return
	 */
	ApiReturnResult<?> deletes(String ids);

	ApiReturnResult<?> getJhrwCount(JhrwParam param);

	/**
	 * 戒护任务分配人员
	 *
	 * @param param
	 * @return
	 */
	ApiReturnResult<?> saveJhry(JhrwFpryParam param);



	/**
	 * 领取任务
	 *
	 * @param id
	 * @return
	 */
	public ApiReturnResult<?> accpectJhrw(String id);

	/**
	 * 获取监护人员列表（分配人员使用）
	 * @return
	 */
	ApiReturnResult<?> getUserData();

	/**
	 * 根据人员编号查询办案区人员列表流转记录
	 *
	 * @param rybh 编号
	 * @return
	 */
	ApiReturnResult<?> getJhrwjlByRybh(String rybh);

	/**
	 * 获取监护任务记录列表
	 * @param param
	 * @return
	 */
	ApiReturnResult<?> getjlData(JhrwjlParam param);

	/**
	 * 获取戒护任务分配的监护人员列表
	 * @param jhrwId
	 * @return
	 */
	ApiReturnResult<?> getJhry(String jhrwId);

	/**
	 * 戒护大屏获取待执行、执行中的戒护任务
	 * @param p
	 * @return
	 */
	ApiReturnResult<?> getDpData(JhrwParam p);

	/**
	 * 获取Jhrwlx字典数据
	 *
	 * @param param
	 * @return
	 */
	public ApiReturnResult<?> getJhrwlxDic(DicParam param);

	/**
	 * 获取jhrwzt字典数据
	 *
	 * @param param
	 * @return
	 */
	ApiReturnResult<?> getJhrwztDic(DicParam param);
}
