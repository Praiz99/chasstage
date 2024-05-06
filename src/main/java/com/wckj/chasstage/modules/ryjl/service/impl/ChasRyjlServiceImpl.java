package com.wckj.chasstage.modules.ryjl.service.impl;

import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.ryjl.dao.ChasRyjlMapper;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author {XL-SaiAren}
 * @date 创建时间：2017年11月21日 下午2:04:51
 * @version V2.0.0 类说明
 */
@Service
@Transactional
public class ChasRyjlServiceImpl extends BaseService<ChasRyjlMapper, ChasRyjl> implements ChasRyjlService {

	@Override
	public Map<String,Object> saveOrUp(ChasRyjl chasRyjl) throws Exception {
		Map<String,Object> result = new HashMap<>();
		// 保存人员记录表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("baqid", chasRyjl.getBaqid());
		map.put("rybh", chasRyjl.getRybh());
		ChasRyjl dbchasRyjl = baseDao.findByParams(map);
		if (dbchasRyjl != null) {
			MyBeanUtils.copyBeanNotNull2Bean(chasRyjl, dbchasRyjl);
			update(dbchasRyjl);
			// 更新等候室信息
		} else {
			save(chasRyjl);
		}
		result.put("code","200");
		result.put("message","操作成功!");
		return result;
	}

	@Override
	public ChasRyjl findByParams(Map<String, Object> map) {
		return baseDao.findByParams(map);
	}

	@Override
	public void deleteByPrimaryRbyh(String rybh) {
		baseDao.deleteByPrimaryRbyh(rybh);
	}
	/**
	 * 根据办案区id、人员编号查找人员记录信息
	 * @param baqid 办案区id
	 * @param rybh 人员编号
	 * @return
	 */
	@Override
	public ChasRyjl findRyjlByRybh(String baqid, String rybh) {
		Map<String, Object> map = new HashMap<>();
		map.put("baqid", baqid);
		map.put("rybh", rybh);
		//map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);  依据人员编号查询，理论上讲不可能出现重复数据
		if(StringUtils.isEmpty(rybh)){
			return new ChasRyjl();
		}
		return findByParams(map);
	}

	@Override
	public ChasRyjl findRyjlByRybh(String baqid, String rybh, String isdel) {
		Map<String, Object> map = new HashMap<>();
		map.put("baqid", baqid);
		map.put("rybh", rybh);
		map.put("isdel", isdel);
		//map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);  依据人员编号查询，理论上讲不可能出现重复数据
		if(StringUtils.isEmpty(rybh)){
			return new ChasRyjl();
		}
		return findByParams(map);
	}

	@Override
	public ChasRyjl findUsedLockerById(String wpgId) {
		return baseDao.findUsedLockerById(wpgId);
	}

	@Override
	public int clearByRybh(String rybh) {
		if(StringUtils.isNotEmpty(rybh)){
			baseDao.clearByRybh(rybh);
		}
		return 0;
	}

	@Override
	public ChasRyjl findRyjlByRybhUndel(String baqid, String rybh) {
		return baseDao.findRyjlByRybhUndel(baqid, rybh);
	}

	@Override
	public ChasRyjl findByWdbhLUndel(String wdbhl) {
		ChasRyjl chasRyjl = baseDao.findByWdbhLUndel(wdbhl);
		return chasRyjl;
	}

	@Override
	public ChasRyjl findByWdbhL(String baqid, String wdbhL) {
		if(StringUtils.isNotEmpty(baqid)&&StringUtils.isNotEmpty(wdbhL)){
			return baseDao.findByWdbhL(baqid, wdbhL);
		}
		return null;
	}
}
