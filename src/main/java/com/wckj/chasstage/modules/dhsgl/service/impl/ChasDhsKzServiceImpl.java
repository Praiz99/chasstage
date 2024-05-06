package com.wckj.chasstage.modules.dhsgl.service.impl;

import com.wckj.chasstage.api.def.dhsgl.model.DhsParam;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.dhsgl.dao.ChasDhsKzMapper;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ChasDhsKzServiceImpl extends BaseService<ChasDhsKzMapper, ChasDhsKz> implements ChasDhsKzService {
    protected Logger log = LoggerFactory.getLogger(ChasDhsKzServiceImpl.class);

    //等候室走廊ID 便于等候室大屏定位是否进入走廊
    //202312080633587BF4523857FF3B4F54          看守大厅
    //2023121211285498A12EE121FF9B8204         进等候区走道
    //202312130138554E8AF6E7A52446445B        采集区直通审讯区走道
    public static final String DHS_ZL_QY_IDS = "202312080633587BF4523857FF3B4F54,2023121211285498A12EE121FF9B8204,202312130138554E8AF6E7A52446445B";

    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ChasYwRygjService chasYwRygjService;
    @Autowired
    private ChasXtQyService chasQyService;
    @Autowired
    private ILedService ledService;
    @Autowired
    @Lazy
    private ChasBaqryxxService chasBaqryxxService;
    //根据定位轨迹信息查询是否存在同案、混关等情况
    public List<Map<String,Object>> selectDhsForyj(Map<String,Object> map){
        return baseDao.selectDhsForyj(map);
    }
    //根据定位轨迹信息查询是否存在人员聚集情况
    public List<Map<String,Object>> selectForryjj(Map<String,Object> map){
        return baseDao.selectForryjj(map);
    }
    @Override
    public List<ChasDhsKz> findByParams(Map<String, Object> map) {
        return baseDao.findByParams(map);
    }

    @Override
    public int findcountByBaqidAndQyid(String baqid,String qyid){
        return baseDao.findcountByBaqidAndQyid(baqid, qyid);
    }

    @Override
    public String findcountByBaqid(String baqid) {
        return baseDao.findcountByBaqid(baqid);
    }

    @Override
    public void updateXmXbByRybh(String ryxm, String ryxb, String rybh) {
        baseDao.updateXmXbByRybh(ryxm, ryxb, rybh);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int clearByrybh(String rybh){
        return baseDao.clearByrybh(rybh);
    }

    @Override
    public int getTaRs(String qyid, String ywbh, String baqid) {
        return baseDao.getTaRs(qyid, ywbh, baqid);
    }

    @Override
    public int getHgRs(String qyid, String xb,String baqid) {
        return baseDao.getHgRs(qyid, xb,baqid);
    }
    @Override
    public List<ChasBaqryxx> selectRyxxBydhs(DhsParam param) {
        Map<String,Object> map = new HashMap<>();
        map.put("dhsId", param.getDhsId());
        map.put("ryxm", param.getRyxm());
        map.put("xb", param.getRyxb());
        map.put("zjlx", param.getZjlx());
        map.put("zjhm", param.getZjhm());
        map.put("csrq", param.getCsrq());
        map.put("hjszd", param.getHjszd());
        map.put("rqyy", param.getRqyy());
        map.put("rqsj1", param.getRqsj());
        map.put("dafs", param.getDafs());
        map.put("rylx", param.getRylx());
        map.put("zbmj", param.getZbmj());
        map.put("tsqt", param.getTsqt());
        map.put("baqid",param.getBaqid());
        map.put("csrq1", param.getCsrq1());
        map.put("csrq2", param.getCsrq2());
        map.put("rqsj1", param.getRqsj1());
        map.put("rqsj2", param.getRqsj2());

        return baseDao.selectRyxxBydhsId(map);
    }

    /**
     * 等候室解除
     * @param baqid
     * @param rybh
     * @param dxled
     * @return
     */
    @Override
    public DevResult dhsJc(String baqid, String rybh, boolean dxled) {
        log.debug(String.format("解除 办案区{%s}人员{%s}等候室分配关系",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        ChasDhsKz chasDhs = findById(chasRyj.getDhsBh());
        if (chasDhs == null) {
            return DevResult.success("解除等候室-已出所");
        }
        //将临时特殊等候室恢复成普通等候室
        ChasXtQy xtQy = chasQyService.findByYsid(chasDhs.getQyid());
        if(xtQy != null&& SYSCONSTANT.DHSLX_LSTS.equals(xtQy.getKzlx())){
            xtQy.setKzlx(SYSCONSTANT.DHSLX_PT);
            chasQyService.update(xtQy);
        }
        resetQykzlx(xtQy);//恢复单独看押的状态
        chasDhs.setIsdel(SYSCONSTANT.ALL_DATA_MARK_DELETE_I);
        update(chasDhs);

        //删除人员记录表中人员分配的等候室信息
        ChasBaqryxx baqryxx = chasBaqryxxService.findByRybh(rybh);
        chasRyj.setDhsBh(null);
        chasRyj.setXgrSfzh(baqryxx.getMjSfzh());
        chasRyjlService.update(chasRyj);
        DevResult wr = ledService.refreshDhsLed(baqid,chasDhs.getQyid());
        if (dxled) {
            wr.add("dhsJc dhsDXLed", ledService.refreshDhsDp(baqid));
        }
        String msg = String.format("办案区：%s 人员:%s解除等候室:%s 是否刷新大小屏:%s", baqid,
                chasRyj.getXm(), chasDhs.getQyid(), dxled);
        log.debug(msg);
        wr.setCodeMessage(1, msg);
        return wr;
    }

    @Override
    public Map<String, Object> findDhsryList(String baqid) {
        Map<String, Object> result = new HashMap<>(16);
        List<Map<String, Object>> dhsryZdList = new ArrayList<>();
        List<Map<String, Object>> dhsryList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>(16);
        try {
            List<Map<String, Object>> dhsDpList = this.baseDao.selectDhsDpData(baqid);
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
                        if (DHS_ZL_QY_IDS.contains(dqqyid)) {
                            dhsryZdList.add(m);
                        } else {
                            dhsryList.add(m);
                        }
                    } else {
                        dhsryList.add(m);
                    }
                }
            }
            result.put("dhsryList", DicUtil.translate(dhsryList, new String[]{"CHAS_ZD_ZB_XB"}, new String[]{"ryxb"}));
            result.put("dhsryZdList", DicUtil.translate(dhsryZdList, new String[]{"CHAS_ZD_ZB_XB"}, new String[]{"ryxb"}));
            result.put("code", 0);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    private void resetQykzlx(ChasXtQy qy){
        if(qy!=null&& StringUtil.isNotEmpty(qy.getId())){
            if(SYSCONSTANT.DHSLX_PT_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_PT);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_TS_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_TS);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NAN_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NAN);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_NV_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_NV);
                chasQyService.update(qy);
            }else if(SYSCONSTANT.DHSLX_WCN_DDKY.equals(qy.getKzlx())){
                qy.setKzlx(SYSCONSTANT.DHSLX_WCN);
                chasQyService.update(qy);
            }

        }
    }
}
