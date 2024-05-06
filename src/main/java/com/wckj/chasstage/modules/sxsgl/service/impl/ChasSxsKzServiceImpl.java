package com.wckj.chasstage.modules.sxsgl.service.impl;


import com.wckj.chasstage.api.def.face.model.FaceTzlx;
import com.wckj.chasstage.api.server.device.IDzspService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.face.service.ChasYwFeatureService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjSnapService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sxsgl.dao.ChasSxsKzMapper;
import com.wckj.chasstage.modules.sxsgl.dto.SxskzUtil;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChasSxsKzServiceImpl extends BaseService<ChasSxsKzMapper, ChasSxsKz> implements ChasSxsKzService {
    protected Logger log = LoggerFactory.getLogger(ChasSxsKzServiceImpl.class);

    //审讯室走廊ID 便于审讯室大屏定位是否进入走廊
    //202312121121385B7501C3A4749D6BE6	进审讯区走道
    //202312121121595356BAA1278FBC3431	审讯区左走道
    //20231212112211F09E842D2A35607D80	审讯区中走道
    //20231212112231D44CA3B648F1C9182F	审讯区右走道
    public static final String SXS_ZL_QY_IDS = "202312121121385B7501C3A4749D6BE6,202312121121595356BAA1278FBC3431,20231212112211F09E842D2A35607D80,20231212112231D44CA3B648F1C9182F";

    @Autowired
    private ChasYwRygjSnapService rygjSnapService;
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasYwFeatureService featureService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private IDzspService dzspService;
    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private FaceInvokeService faceInvokeService;
    @Override
    public List<ChasSxsKz> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public List<ChasSxsKz> getSxsKzByRybh(Map<String, Object> map) {
        return baseDao.getSxsKzByRybh(map);
    }


    @Override
    public List<ChasSxsKz> selectByysdd(Map<String, Object> map) {
        return baseDao.selectByysdd(map);
    }


    @Override
    public ChasSxsKz findAllById(String id) {
        return baseDao.findAllById(id);
    }


    @Override
    public void deleteByPrimaryRbyh(String rybh) {
        baseDao.deleteByPrimaryRbyh(rybh);
    }

    @Override
    public String findcountByBaqid(String baqid) {
        return baseDao.findcountByBaqid(baqid);
    }
    //查询当前审讯室是否正在使用
    @Override
    public int findcountByQyid(String qyid){
        return baseDao.findcountByQyid(qyid);
    }

    @Override
    public void updateRyxmByRybh(String ryxm, String rybh) {
        baseDao.updateRyxmByRybh(ryxm,rybh);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int clearByrybh(String rybh){
        if(StringUtils.isEmpty(rybh)){
            throw new BizDataException("人员编号为null");
        }
        rygjSnapService.deleteByRybh(rybh);
        //人脸特征删除
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        faceInvokeService.deleteFeatureByTzbh(baqryxx.getBaqid(), baqryxx.getRybh(), FaceTzlx.BAQRY.getCode());
//        featureService.deleteById(rybh);
        return baseDao.clearByrybh(rybh);
    }


    @Override
    public ChasSxsKz getJxydSxs(String qyid) {
        return baseDao.getJxydSxs(qyid);
    }



    @Override
    public int getCountByRybh(String baqid, String rybh,String hour) {
        return baseDao.getCountByRybh(baqid, rybh,hour);
    }

    @Override
    public PageDataResultSet<ChasSxsKz> getSxssyjlApiPageData(int pageNo, int pageSize, Object param, String orderBy) {
        MybatisPageDataResultSet<ChasSxsKz> mybatisPageData = this.baseDao.selectSyjl(pageNo, pageSize, param, orderBy);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }

    @Override
    public int selectSyjlSize(Map<String, Object> map) {
        return baseDao.selectSyjlSize(map);
    }

    /**
     * 解除审讯室分配记录 刷新LED(LED在另一端控制)
     * @return
     */
    @Override
    public DevResult sxsJc(String baqid, String rybh, boolean dxled) {
        // 审讯室解除
        log.debug(String.format("办案区：%s 人员编号:%s 审讯室解除  是否刷新大小屏:%s", baqid,
                rybh, dxled));
        DevResult wr = new DevResult();
        String msg;
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        if(chasRyj == null){
            return DevResult.error(rybh+"找不到人员信息");
        }
        String sxsbh = chasRyj.getSxsBh();
        if (StringUtil.isNotEmpty(sxsbh)) {
            // 解除审讯室分配
            ChasSxsKz chasSxsKz = findAllById(sxsbh);
            if (chasSxsKz == null) {
                log.error("根据审讯室编号 未找到分配记录");
                return DevResult.error("根据审讯室编号 未找到分配记录");
            }

            // 笔录结束定时检查和定位触发事件共存
            if (SYSCONSTANT.SXSZT_KX.equals(chasSxsKz.getFpzt()) ) {
                // 已解除不再触发事件
                msg = String.format("办案区:%s 人员:%s  编号:%s 已解除不再触发事件",
                        chasRyj.getBaqmc(), chasRyj.getXm(), chasRyj.getRybh());
                wr.setCode(0);
                wr.setMessage(msg);
                log.debug(msg);
                return wr;
            } else {
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                chasSxsKz.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
                chasSxsKz.setFpzt(SYSCONSTANT.SXSZT_TY);
                chasSxsKz.setDyzt(SYSCONSTANT.SXSZT_TY);
                chasSxsKz.setXgrSfzh(baqryxx.getMjSfzh());
                update(chasSxsKz);
                //解除人员记录表中审讯室id
                chasRyj.setSxsBh(null);
                chasRyj.setXgrSfzh(baqryxx.getMjSfzh());
                chasRyjlService.update(chasRyj);
                // 关闭继电器
                //wr.add("解除审讯室 关闭继电器", jdqService.openOrColseXxs(baqid, chasSxsKz.getQyid(),SYSCONSTANT.OFF));
                // 刷新LED
                wr.add("解除审讯室 刷新审讯屏",ledService.refreshSxsLedBySxskz(baqid, chasSxsKz.getQyid(),chasSxsKz.getId()));
                wr.add("解除审讯室 刷新电子水牌",dzspService.refresh(baqid, chasSxsKz.getQyid(),rybh));
                if (dxled) {
                    //ChasXtQy qy = qyService.findById(chasSxsKz.getQyid());
                    //String content = qy.getQymc()+"空闲";
                    //wr.add("解除审讯室 刷新大小屏", ledService.refreshSxsDxp(baqid,content));
                    SxskzUtil.getInstance().removeSxsFpxx(baqid,chasRyj.getRybh());
                    String content = SxskzUtil.getInstance().getLastSxsFpxx(baqid);
                    log.debug("解除审讯室 刷新大小屏:"+content);
                    wr.add("解除审讯室 刷新大小屏", ledService.refreshSxsDxp(baqid,content));
                }
                wr.add("解除审讯室 刷新休息屏", ledService.refreshMjRoom(baqid));
            }
            msg = "解除分配成功";
            log.debug(String.format("办案区：%s 人员编号:%s 解除分配成功  是否刷新大小屏:%s",
                    baqid, rybh, dxled));
            wr.setCodeMessage(1, msg);
        } else {
            msg = "未分配审讯室 解除分配失败";
            log.debug(String.format("办案区：%s 人员编号:%s 未分配审讯室 解除分配失败 是否刷新大小屏:%s",
                    baqid, rybh, dxled));
            wr.setCodeMessage(0, msg);
        }
        return wr;
    }

    @Override
    public ChasSxsKz findTheLastFpjl(String baqid) {
        return baseDao.findTheLastFpjl(baqid);
    }

    @Override
    public Map<String, Object> findSxsryList(String baqid) {
        Map<String, Object> result = new HashMap<>(16);
        List<Map<String, Object>> sxsryZdList = new ArrayList<>();
        List<Map<String, Object>> sxsryList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>(16);
        try {
            List<Map<String, Object>> dhsDpList = this.baseDao.selectSxsDpData(baqid);
            for (Map<String, Object> m : dhsDpList) {
                if (m != null && m.get("rybh") != null) {
                    params.clear();
                    params.put("baqid", baqid);
                    params.put("rybh", m.get("rybh"));
                    ChasRygj chasRygj = chasYwRygjService.findzhlocation(params);
                    if (chasRygj != null) {
                        String dqqyid = chasRygj.getQyid();
                        m.put("dqqyid", dqqyid);
                        m.put("dqwz", chasRygj.getQymc());
                        if (SXS_ZL_QY_IDS.contains(dqqyid)) {
                            sxsryZdList.add(m);
                        } else {
                            sxsryList.add(m);
                        }
                    } else {
                        sxsryList.add(m);
                    }
                }
            }
            result.put("sxsryList", DicUtil.translate(sxsryList, new String[]{"CHAS_ZD_ZB_XB"}, new String[]{"ryxb"}));
            result.put("sxsryZdList", DicUtil.translate(sxsryZdList, new String[]{"CHAS_ZD_ZB_XB"}, new String[]{"ryxb"}));
            result.put("code", 0);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
