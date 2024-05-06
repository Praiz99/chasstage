package com.wckj.chasstage.modules.shsng.service.impl;

import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.shsng.dao.ChasSngMapper;
import com.wckj.chasstage.modules.shsng.entity.ChasSng;
import com.wckj.chasstage.modules.shsng.service.ChasShsngService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zengrk
 */
@Service
@Transactional
public class ChasShsngServiceImpl extends BaseService<ChasSngMapper, ChasSng> implements ChasShsngService {
    private static final Logger log = Logger.getLogger(ChasShsngServiceImpl.class);

    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasYwFkdjService fkdjService;
    @Autowired
    private IWdService wdService;
    @Override
    public void getShxkDatas(String cbaqid) throws Exception{
        Map<String, Object> params = new HashMap<>();

        params.put("sblx", "6");
        params.put("baqid", cbaqid);
        List<ChasSb> chasSbs = chasSbService.findByParams(params);
        for (ChasSb sb: chasSbs){
            ChasSng sng = new ChasSng();
            sng.setShbh(sb.getSbbh());
            sng.setBaqid(sb.getBaqid());
            sng.setBaqmc(sb.getBaqmc());
            sng.setKzcs1(sb.getKzcs1());
            sng.setKzcs2(sb.getKzcs2());
            sng.setSblx(sb.getSblx());
            sng.setSbmc(sb.getSbmc());
            sng.setKzcs3(sb.getKzcs3());
            String wdbhl = sb.getKzcs2();
            if("1".equals(sb.getKzcs3())) {
                ChasBaqryxx ryxx = chasBaqryxxService.findRyxxBywdbhL(wdbhl);
                if (null != ryxx) {
                    if (StringUtils.equals(ryxx.getRyzt(), "00") || StringUtils.equals(ryxx.getRyzt(), "04")
                            || StringUtils.equals(ryxx.getRyzt(), "08") || StringUtils.equals(ryxx.getRyzt(), "09")) {
                        //人员不在区
                        sng.setZt("2");
                        sng.setFfsj(ryxx.getRRssj());
                        sng.setGhsj(ryxx.getCCssj());
                    } else {
                        sng.setZt("1");
                        sng.setFfsj(ryxx.getRRssj());
                    }
                } else {
                    sng.setZt("2");
                }
            }else {
                String baqid = sb.getBaqid();
                params.clear();
                params.put("baqid", baqid);
                params.put("wdbhL", wdbhl);
                params.put("zt", SYSCONSTANT.MJRQ_ZQ);
                params.put("isdel", SYSCONSTANT.N_I);
                List<ChasYwFkdj> ryjls = fkdjService.findList(params, " xgsj desc");
                if(ryjls.size() > 0) {
                    int dl = validateWdDl(baqid, sb);
                    if (dl == 2) {
                        log.info("该胸卡已被占用!");
                        sng.setZt("1");
                        sng.setFfsj(ryjls.get(0).getJrsj());
                    } else {
                        sng.setZt("2");
                        sng.setFfsj(ryjls.get(0).getJrsj());
                        sng.setGhsj(ryjls.get(0).getCqsj());
                    }
                }else {
                    sng.setZt("2");
                }
            }
            saveOrupdate(sng);
        }
    }

    public void saveOrupdate(ChasSng sng) throws Exception{
        ChasSng chasSng = baseDao.findDataBySbbh(sng.getShbh());
        SessionUser user = WebContext.getSessionUser();
        if(null == chasSng){
            sng.setId(StringUtils.getGuid32());
            sng.setXgsj(new Date());
            sng.setXgrSfzh(user.getIdCard());
            sng.setLrsj(new Date());
            sng.setLrrSfzh(user.getIdCard());
            sng.setIsdel((short) 0);
            sng.setDataFlag("0");
            sng.setZgqk("2");
            baseDao.insert(sng);
        }else {
            sng.setId(chasSng.getId());
            sng.setFfsj(null);
            sng.setGhsj(null);
            sng.setZt(null);
            sng.setZgqk(null);
            baseDao.updateByPrimaryKey(sng);
        }
    }

    //验证手环是否低电量
    private int validateWdDl(String baqid,ChasSb wd){
        DevResult dev=wdService.wdDdl(baqid, wd);
        if(dev != null){
            int code = dev.getCode();
            if(code == 1){
                HashMap device = (HashMap)dev.get("device");
                if(device != null){
                    String status = device.get("status")==null?"":device.get("status").toString();
                    String userId = device.get("userId")==null?"":device.get("userId").toString();
                    String userName = device.get("userId")==null?"":device.get("userName").toString();
                    if(StringUtil.isNotEmpty(userId)||StringUtil.isNotEmpty(userName)){//已经绑人了
                        return 2;
                    }
                    String isQuantity = device.get("isQuantity")==null?"":device.get("isQuantity").toString();
                    if("Y".equalsIgnoreCase(isQuantity)){
                        return 1;
                    }
                    return 0;
                }
            }
        }
        return -1;
    }
    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<String, Object>();
        MybatisPageDataResultSet<ChasSng> chasSngs = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        result.put("rows", chasSngs.getData());
        result.put("total",chasSngs.getTotal());
        return result;
    }

    @Override
    public Integer selectZg() {
        return baseDao.selectZg();
    }

    @Override
    public void yjcl() {
        baseDao.yjcl();
    }

    @Override
    public Map<String,Object> sdgh(String id) {
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        SessionUser user = WebContext.getSessionUser();
        ChasSng chasSng = baseDao.selectByPrimaryKey(id);
        if(StringUtil.isEmpty(id)){
            result.put("success",false);
            result.put("message","id为空");
            return result;
        }
        if(chasSng==null){
            result.put("success",false);
            result.put("message","没有该id的手环");
            return result;
        }
        chasSng.setXgsj(new Date());
        chasSng.setXgrSfzh(user.getIdCard());
        chasSng.setZt("3");
        chasSng.setGhsj(new Date());
        baseDao.updateByPrimaryKey(chasSng);
        return result;
    }
    @Override
    public ChasSng findeByWdbhL(String kzcs2) {
        return baseDao.findeByWdbhL(kzcs2);
    }

    @Override
    public void sngUpdate(ChasSng sng) {
        baseDao.sngUpdate(sng);
    }
}
