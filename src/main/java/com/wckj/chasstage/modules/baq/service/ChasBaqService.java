package com.wckj.chasstage.modules.baq.service;

import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.baq.dao.ChasBaqMapper;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;
public interface ChasBaqService extends IBaseService<ChasBaqMapper, ChasBaq> {

	ChasBaq findByParams(Map<String, Object> map);

	List<ChasBaq> findListByParams(Map<String, Object> map);

	String delete(String[] ids);

	Map<String, Object> saveOrUpdate(ChasBaq baq) throws Exception;

	Map<String, Object> saveOrUpdate(Object baq) throws Exception;

	DevResult getBaqDicByUser(String queryValue, int page, int pagesize);

	Map<?,?> getBaqPageData(int page,int rows,Map<String,Object> params,String order);

	/**
	 *
	 * @return
	 * @param sysCodeLike
	 */
    List<ChasBaq> getBaqDicByLogin(String sysCodeLike);

	/**
	 * 根据单位获取单位关联所有办案区
	 * @param orgCode
	 * @return
	 */
	List<ChasBaq> getBaqByOrgCode(String orgCode);

	/**
	 * 获取当前用户责任办案区id
	 * @return
	 */
	String getZrBaqid();

	ChasBaq getBaqByLoginedUser();

}
