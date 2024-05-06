package com.wckj.chasstage.modules.sbgl.service.impl;


import com.wckj.chasstage.api.def.sbgl.model.SbglBean;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.common.util.oConvertUtils;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sbgl.dao.ChasSbMapper;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasSbServiceImpl extends BaseService<ChasSbMapper, ChasSb> implements ChasSbService {
    protected Logger log = LoggerFactory.getLogger(ChasSbServiceImpl.class);
    @Autowired
    private IJdqService jdqService;

    @Override
    public List<ChasSb> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public DevResult openOrClose(String id, Integer oc) {
        DevResult w = new DevResult();
        try {
            if (StringUtil.isNotEmpty(id) && oc != null) {
                ChasSb s = findById(id);
                if (s != null) {
                    if (oc == 0) {//关闭状态
                        w = jdqService.openOrClose(s.getBaqid(), SYSCONSTANT.OFF, s.getSbbh());
                        if (w.getCode() == 1) {
                            s.setSbzt(1);
                            update(s);
                            w.setMessage("操作成功!!!");
                        }
                    } else if (oc == 1) {//打开状态
                        w = jdqService.openOrClose(s.getBaqid(), SYSCONSTANT.ON, s.getSbbh());
                        if (w.getCode() == 1) {
                            s.setSbzt(0);
                            update(s);
                            w.setMessage("操作成功!!!");
                        }
                    } else {
                        w = DevResult.error("状态值不匹配!!");
                    }
                } else {
                    w = DevResult.error("数据不存在!!");
                }
            } else {
                w = DevResult.error("id不存在!!或状态值为空");
            }
        } catch (Exception e) {
            w.setCodeMessage(1, "异常！请重试");
            w.add("openOrClose", e);
            log.error("openOrClose:", e);
        }
        return w;
    }

    /**
     * 根据类型和功能查找办案区设备
     *
     * @param baqid
     * @return
     */
    public List<ChasSb> getBaqSbsByLxAndGn(String baqid, String lx, String gn) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("sblx", lx);
        map.put("sbgn", gn);
        return findByParams(map);
    }

    public String queryHWatchNo(String watchNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("wdbhL", watchNo);
        if (StringUtils.isNotEmpty(watchNo)) {
            List<ChasSb> wds = this.findList(params, null);
            if (!wds.isEmpty()) {
                return wds.get(0).getKzcs1();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void clearByBaqAndLx(String baqid, String type) {
        baseDao.clearByBaqAndLx(baqid, type);
    }

    @Override
    public Map getRtspValByQyid(String qyid) {
        return baseDao.getRtspValByQyid(qyid);
    }

    @Override
    public ChasSb getPreviewCamera(String baqid, String gw) {
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("sblx", "2");
        params.put("sbgn", "2");
        SessionUser user = WebContext.getSessionUser();
        String roleCode = user.getRoleCode();
        if (!StringUtils.equals(gw, "false")) {
            params.put("kzcs6", roleCode);//根据相应岗位角色选择相应摄像头
        }

        List<ChasSb> wds = this.findList(params, null);
        if (wds != null && !wds.isEmpty()) {
            return wds.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ChasSb> findZDSB() {
        return baseDao.findZDSB();
    }


    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<String, Object>();
        MybatisPageDataResultSet<ChasSb> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        ChasXtQyService qyService= ServiceContext.getServiceByClass(ChasXtQyService.class);

        for (ChasSb chasSb : list.getData()) {
            if(oConvertUtils.isNotEmpty(chasSb.getQyid())){
                ChasXtQy xtQy=qyService.findByYsid(chasSb.getQyid());
                if(oConvertUtils.isNotEmpty(xtQy)){
                    chasSb.setQymc(String.valueOf(xtQy.getQymc()));

                }

            }
        }
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{
                "SBLX","CHAS_XT_BAQ", "SBGN", "CHASET_ZD_SF", "CHASET_ZD_SF"}, new String[]{"sblx","baqid", "sbgn",
                "sfzx", "sfdb"}));
        return result;
    }

    @Override
    public List<ChasSb> getKxxk(String baqid) {
//        return baseDao.getKxxk(baqid);
        return null;
    }

    @Override
    public int saveSbgl(SbglBean bean) {
        ChasSb chasSb = new ChasSb();
        int cou = 0;
        try {
            MyBeanUtils.copyBeanNotNull2Bean(bean, chasSb);
            chasSb.setId(StringUtils.getGuid32());
            chasSb.setSfzx("1");
            chasSb.setSfdb("0");
            chasSb.setLrsj(new Date());
            chasSb.setXgsj(new Date());
            chasSb.setLrrSfzh(WebContext.getSessionUser().getIdCard());
            cou = baseDao.insert(chasSb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cou;

    }

    @Override
    public int updateSbgl(SbglBean bean) {
        ChasSb chasSb = baseDao.selectByPrimaryKey(bean.getId());
        int cou = 0;
        try {
            if (oConvertUtils.isNotEmpty(chasSb)) {
                MyBeanUtils.copyBeanNotNull2Bean(bean, chasSb);
                chasSb.setXgsj(new Date());
                chasSb.setXgrSfzh(WebContext.getSessionUser().getIdCard());
                cou = baseDao.updateByPrimaryKey(chasSb);
            } else {
                return cou;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cou;

    }

    @Override
    public int deleteSbgl(String id) {
        int num = 0;
        String ids[] = id.split(",");
        for (String s : ids) {
            num = baseDao.deleteByPrimaryKey(s);
        }
        return num;
    }

    @Override
    public ChasSb getSbByLxAndQy(String sblx, String qyid) {
        ChasSb sb = baseDao.getSbByLxAndQy(sblx, qyid);
        return sb;
    }


}
