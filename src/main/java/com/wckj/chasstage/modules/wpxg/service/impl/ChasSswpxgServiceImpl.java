package com.wckj.chasstage.modules.wpxg.service.impl;


import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.wpg.entity.ChasSswpg;
import com.wckj.chasstage.modules.wpg.service.ChasSswpgService;
import com.wckj.chasstage.modules.wpxg.dao.ChasSswpxgMapper;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.chasstage.modules.wpxg.service.UnusedLockers;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasSswpxgServiceImpl extends BaseService<ChasSswpxgMapper, ChasSswpxg> implements ChasSswpxgService {

	@Autowired
	private ChasSswpgService sswpgService;

	@Override
	public Map<String, Object> saveSswpxg(String sswpxgsJson, String delIds, String sswpgId) throws Exception {
		String idCard = WebContext.getSessionUser().getIdCard();
		ChasSswpg sswpgTemp = null;
		Map<String,Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(sswpgId)){
			sswpgTemp = sswpgService.findById(sswpgId);
		}else {
			result.put("success", false);
			result.put("msg", "查询随身物品柜出错!!");
			return result;
		}
		//新增与保存的处理
		JSONObject sswpxgs= JSONObject.fromObject(sswpxgsJson);
		if (sswpxgs!=null) {
            JSONObject json= JSONObject.fromObject(sswpxgs);
            int size = json.size();
            for (int i = 0; i < size; i++) {
                JSONObject o = (JSONObject) json.get(""+i);
                ChasSswpxg temp = (ChasSswpxg) JSONObject.toBean(o,ChasSswpxg.class);
                temp.setXgrSfzh(idCard);
                temp.setBaqid(sswpgTemp.getBaqid());
                temp.setBaqmc(sswpgTemp.getBaqmc());
                temp.setSswpgid(sswpgTemp.getId());
                temp.setIsdel(SYSCONSTANT.N_I);
                temp.setDataflag(SYSCONSTANT.N);
                if(StringUtils.isNotEmpty(temp.getId())){//修改
                	ChasSswpxg chasSswpxg = findById(temp.getId());
    				MyBeanUtils.copyBeanNotNull2Bean(temp, chasSswpxg);
    				update(chasSswpxg);
    				result.put("success", true);
    				result.put("msg", "保存成功!");
                }else{//新增
                	temp.setId(StringUtils.getGuid32());
                	temp.setLrrSfzh(idCard);
                	temp.setXgrSfzh(idCard);
    				save(temp);
    				result.put("success", true);
    				result.put("msg", "保存成功!");
                }
            }
        }
		//删除的数据的处理
		if(StringUtils.isNotEmpty(delIds)){
			String[] idstr = delIds.split(",");
			for(String id : idstr){
				deleteById(id);
			}
			result.put("success", true);
			result.put("msg", "保存成功!");
		}
		return result;
	}

	@Override
	public ChasSswpxg findBySbid(Map<String,Object> map) {
		return baseDao.findBySbid(map);
	}

	@Override
	public void clearByWpg(String wpgid) {
		baseDao.clearByWpg(wpgid);
	}

	@Override
	public UnusedLockers getUnusedLockers(List<String> baqids) {
		List<ChasSswpxg> sswpxgs = baseDao.findUnusedByBaqids(baqids);
		return new UnusedLockers(){
			@Override
			public boolean isHaveUnusedLockerByThing() {
				for (ChasSswpxg sswpxg : sswpxgs) {
					if(StringUtil.equals(sswpxg.getSbgn(),"1")){
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean isHaveUnusedLockerByPhone() {
				for (ChasSswpxg sswpxg : sswpxgs) {
					if(StringUtil.equals(sswpxg.getSbgn(),"40")){
						return true;
					}
				}
				return false;
			}

			@Override
			public List<ChasSswpxg> getUnusedData() {
				return sswpxgs;
			}

			@Override
			public List<ChasSswpxg> getUnusedThingData() {
				List<ChasSswpxg> sswpxgList = new ArrayList<>();
				for (ChasSswpxg sswpxg : sswpxgs) {
					if(StringUtil.equals(sswpxg.getSbgn(),"1")){
						sswpxgList.add(sswpxg);
					}
				}
				return sswpxgList;
			}

			@Override
			public List<ChasSswpxg> getUnusedPhoneData() {
				List<ChasSswpxg> sswpxgList = new ArrayList<>();
				for (ChasSswpxg sswpxg : sswpxgs) {
					if(StringUtil.equals(sswpxg.getSbgn(),"40")){
						sswpxgList.add(sswpxg);
					}
				}
				return sswpxgList;
			}
		};
	}

	@Override
	public PageDataResultSet<ChasSswpxg> getWpgPageData(Integer page, Integer rows, Map<String, Object> params, String orderBy) {
		MybatisPageDataResultSet<ChasSswpxg> mybatisPageData =this.baseDao.getWpgPageData(page, rows, params, orderBy);
		return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
	}


}
