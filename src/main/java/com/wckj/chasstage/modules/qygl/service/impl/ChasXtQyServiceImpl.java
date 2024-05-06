package com.wckj.chasstage.modules.qygl.service.impl;


import com.wckj.chasstage.api.server.device.DataTB;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.qygl.dao.ChasXtQyMapper;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasXtQyServiceImpl extends BaseService<ChasXtQyMapper, ChasXtQy> implements ChasXtQyService {
    protected Logger log = LoggerFactory.getLogger(ChasXtQyServiceImpl.class);
    @Autowired
    private DataTB dataTB;

    @Override
    @Transactional(readOnly = false)
    public int delete(String[] idstr) {
        int b = 0;
        for (String id : idstr) {
            b = baseDao.deleteByPrimaryKey(id);
        }
        return b;
    }

    @Override
    public List<ChasXtQy> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public List<ChasXtQy> findKfpDhs(Map<String, Object> map) {
        return baseDao.findKfpDhs(map);
    }

    @Override
    public List<ChasXtQy> findbtaAndtxbDhs(Map<String, Object> map) {
        return baseDao.findbtaAndtxbDhs(map);
    }

    @Override
    public List<ChasXtQy> getDhsByRybh(String rybh) {
        return baseDao.getDhsByRybh(rybh);
    }

    @Override
    public ChasXtQy findByYsid(String areaId) {
        ChasXtQy qy = baseDao.findByYsid(areaId);
        if(qy!=null){
            return qy;
        }
        return findById(areaId);
    }

    @Override
    public List<ChasXtQy> findfreeSxs(String baqid) {
        return baseDao.findfreeSxs(baqid);
    }


    @Override
    public void clearAll(String baqid) {
        baseDao.clearByBaq(baqid);
    }

    @Override
    public List<ChasXtQy> getDhsRoomData(Map<String, Object> map) {
        return baseDao.getDhsRoomData(map);
    }

    @Override
    public Map<String, Object> saveOrUpdate(ChasXtQy xtqy, String id) throws Exception {
        String idCard = WebContext.getSessionUser().getIdCard();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(id)) { // 新增
                xtqy.setId(StringUtils.getGuid32());
                xtqy.setLrrSfzh(idCard);
                save(xtqy);
                result.put("success", true);
                result.put("msg", "保存成功!");
            } else {// 修改
                xtqy.setXgrSfzh(idCard);
                ChasXtQy chasXtqy = findById(id);
                MyBeanUtils.copyBeanNotNull2Bean(xtqy, chasXtqy);
                update(chasXtqy);

                result.put("success", true);
                result.put("msg", "修改成功!");
            }
        } catch (Exception e) {
            log.error("saveOrUpdate:", e);
            result.put("success", false);
            result.put("msg", "系统异常:" + e.getMessage());
            throw e;
        }
        return result;
    }

    //获取办案区配置的区域
    @Override
    public DevResult getQyDicByBaq(String baqid,String fjlx, String queryValue, int page, int pagesize) {
        Map<String, Object> result = new HashMap<>(16);
        DevResult w = new DevResult();

        if (StringUtils.isNotEmpty(queryValue)) {
            ChasXtQy xtqy = findById(queryValue);
            if (xtqy != null) {
                result.put("Total", 1);
                result.put("Rows", xtqy);
                w.setData(result);
                w.setCode(1);
            }else {
                w.setCode(0);
                w.setMessage("数据为空");
            }
        } else {

//            if (!StringUtils.isEmpty(baqid)) {
                Map<String, Object> param = new HashMap<>();
                if (!StringUtils.isEmpty(baqid)){
                    param.put("baqid", baqid);
                }
                if (StringUtils.isNotEmpty(fjlx)) {
                    param.put("fjlx", fjlx);
                }
                PageDataResultSet<ChasXtQy> data = getEntityPageData(page, pagesize, param, "");
                if (data.getTotalPage() == data.getCurrentPage()) {
                    if (!"3".equals(fjlx)) {
                        ChasXtQy xgQy = new ChasXtQy();
                        xgQy.setId("b21380e56ca8bdde016ca8c57f2b0009");
                        xgQy.setQymc("看守区");
                        data.getData().add(xgQy);
                    }
                }
                if (!"3".equals(fjlx)) {
                    data.setTotal(data.getTotal() + 1);
                }

                result.put("Total", data.getTotal());
                result.put("Rows", data.getData());
                w.setData(result);
                w.setCode(1);;

//            } else {
//                w.error("参数为空");
//
//            }

        }

        return w;
    }

    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<String, Object>();
        MybatisPageDataResultSet<ChasXtQy> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{
                "XTQY"}, new String[]{"fjlx"}));
        return result;
    }

    @Override
    public Map<String, Object> tbBaqQy(String baqid) {
        DevResult dresult = dataTB.baqQyTb(baqid);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", dresult);
        return result;
    }

}
