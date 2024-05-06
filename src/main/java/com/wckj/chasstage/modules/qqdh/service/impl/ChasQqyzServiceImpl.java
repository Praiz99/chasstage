package com.wckj.chasstage.modules.qqdh.service.impl;


import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.api.server.device.ILedService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qqdh.dao.ChasQqyzMapper;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;
import com.wckj.jdone.modules.act2.service.ProcessEngine2Service;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChasQqyzServiceImpl extends BaseService<ChasQqyzMapper, ChasQqyz> implements ChasQqyzService {
    protected Logger log = LoggerFactory.getLogger(ChasQqyzServiceImpl.class);
    @Lazy
    @Autowired(required = false)
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasRyjlService chasRyjlService;
    @Autowired
    private ILedService ledService;
    @Autowired
    private IJdqService jdqService;
    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private ChasBaqService service;

    /**
     * @param id
     * @param org
     * @return void  //返回值
     * @throws //异常
     * @description 挂断电话//描述
     * @author lcm //作者
     * @date 2020/9/14 14:00 //时间
     */
    @Override
    public void unusedPhone(String id, String org) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ryid", id);
        params.put("baqid", org);
        params.put("zt", SYSCONSTANT.QQTEL_JS);
        List<ChasQqyz> qqtqe = findbyparams(params);
        if (!qqtqe.isEmpty()) {
            for (ChasQqyz qqyz : qqtqe) {
                qqyz.setZt(SYSCONSTANT.QQTEL_JS);
                update(qqyz);
            }
        }
    }

    public List<ChasQqyz> findbyparams(Map<String, Object> map) {
        return baseDao.findbyparams(map);
    }

    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<>();

        MybatisPageDataResultSet<ChasQqyz> qt = baseDao.selectAll(pageNo, pageSize, param, " lrsj desc");

        result.put("rows", DicUtil.translate(qt.getData(), new String[]{"QQYZ"}, new String[]{"zt"}));
        result.put("total", qt.getTotal());
        return result;
    }

    @Override
    public DevResult saveOrUpdate(ChasQqyz qqyz,boolean add) {
        try {
            SessionUser user = WebContext.getSessionUser();
            if (StringUtils.isEmpty(qqyz.getId())||add) {// 新增
                Map<String, Object> param = new HashMap<String, Object>();
                //Integer[] ztList = {SYSCONSTANT.QQTEL_SPBTG, SYSCONSTANT.QQTEL_JS};
                //param.put("ztList", ztList);
                param.put("rybh", qqyz.getRybh());
                ChasBaqryxx ryxx = chasBaqryxxService.findById(qqyz.getRyid());
                if(StringUtil.isEmpty(qqyz.getId())){
                    qqyz.setId(StringUtils.getGuid32());
                }
                qqyz.setBaqid(ryxx.getBaqid());
                qqyz.setBaqmc(ryxx.getBaqmc());
                qqyz.setLrrSfzh(user.getIdCard());
                if (qqyz.getZt() == null) {
                    qqyz.setZt(SYSCONSTANT.QQTEL_SQ);
                }
                save(qqyz);
                return DevResult.success("保存成功");

            } else {
                qqyz.setXgrSfzh(user.getIdCard());
                ChasQqyz chasQqyz = findById(qqyz.getId());
                MyBeanUtils.copyBeanNotNull2Bean(qqyz, chasQqyz);
                update(chasQqyz);
                return DevResult.success("修改成功");
            }
        } catch (Exception e) {
            DevResult.error(e.getMessage());
            log.error("saveOrUpdate:", e);

        }
        return new DevResult();
    }

    @Override
    public int deleteByPrimaryRbyh(String rybh) {
        return baseDao.deleteByPrimaryRbyh(rybh);

    }

    @Override
    public int deleteByqqid(String ids) {
        String idstr[] = ids.split(",");
        int num = 0;
        for (String id : idstr) {
            num = baseDao.deleteByPrimaryKey(id);
        }
        return num;
    }


    @Override
    public DevResult closeQqdhById(String baqid, String rybh, String id) {
        return dhOnOffById(baqid, rybh, id);
    }

    @Override
    public DevResult openQqdhById(String baqid, String rybh, String id) {
        return dhOnOffById(baqid, rybh, id);
    }

    @Override
    public DevResult qqdhTqly(String id) {
        DevResult result = new DevResult();
        try {
            ChasQqyz qqyz = findById(id);
            if (qqyz == null) {
                result.setCode(0);
                result.setMessage("亲情电话信息不存在");
            } else if (qqyz.getZt() < 5) {
                result.setCode(0);

                result.setMessage("亲情电话还未结束");
            } else if (StringUtil.isNotEmpty(qqyz.getLywj())) {
                result.setCode(0);
                result.setMessage("获取录音文件成功");
                Map<String, Object> data = new HashMap<>();
                data.put("data", qqyz.getLywj());
                result.setData(data);
            } else {
                result = jdqService.getQqdhRec(qqyz);
                if (result.getCode() == 1) {
                    if (result.getData() != null && result.getData().containsKey("ftpUrl")) {
                        String ftpUrl = result.getData().get("ftpUrl").toString();
                        if (StringUtil.isNotEmpty(ftpUrl)) {
                            qqyz.setLywj(ftpUrl);
                            update(qqyz);
                            Map<String, Object> data = new HashMap<>();
                            data.put("data", ftpUrl);
                            result.setCode(0);
                            result.setData(data);
                            result.setMessage("获取录音文件成功");
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.setMessage("获取录音文件失败");
            log.error("getQqyzLywj:", e);
        }
        return result;
    }

    /**
     * @param baqid
     * @param rybh
     * @param watchNo
     * @return com.wckj.chasstage.modules.device.util.DevResult  //返回值
     * @throws //异常
     * @description 关闭亲情电话//描述
     * @author lcm //作者
     * @date 2020/9/14 14:07 //时间
     */
    @Override
    public DevResult closeQqdh(String baqid, String rybh, String watchNo) {

        return dhOnOff(baqid, rybh, watchNo);
    }

    /**
     * 验证是否审批通过
     *
     * @param ryid
     * @return
     */

    public Map<String, Object> valideApprove(String ryid) {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ryid", ryid);
            params.put("zt", SYSCONSTANT.QQTEL_SPTG+"");
            List<ChasQqyz> qqyzs = findList(params, null);

            if (qqyzs.isEmpty()) {  //没有查询到审批通过的数据
                result.put("sptg", false);
            } else {
                result.put("sptg", true);
            }

            params.clear();
            params.put("ryid", ryid);
            params.put("zt", SYSCONSTANT.QQTEL_SQ+"");
            qqyzs = findList(params, null);
            if (qqyzs.isEmpty()) {  //亲情驿站电话是否是否有申请的
                result.put("sq", false);
            } else {
                result.put("sq", true);
            }
            params.clear();
            params.put("ryid", ryid);
            params.put("zt", SYSCONSTANT.QQTEL_SYZ+"");
            qqyzs = findList(params, null);
            if (qqyzs.isEmpty()) {  //亲情驿站电话是否使用中
                result.put("syz", false);
            } else {
                result.put("syz", true);
            }

            params.clear();
            params.put("ryid", ryid);
            qqyzs = findList(params, null);
            if (qqyzs.isEmpty()) {  //该人员是否存在亲情驿站数据
                result.put("count", false);
            } else {
                result.put("count", true);
            }
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);

        }
        return result;
    }

    /**
     * @param baqid
     * @param rybh
     * @param watchNo
     * @return com.wckj.chasstage.modules.device.util.DevResult  //返回值
     * @throws //异常
     * @description 打开亲情电话//描述
     * @author lcm //作者
     * @date 2020/9/14 14:07 //时间
     */
    @Override
    public DevResult openQqdh(String baqid, String rybh, String watchNo) {
        return dhOnOff(baqid, rybh, watchNo);
    }

    private DevResult dhOnOffById(String baqid, String rybh, String id) {
        log.debug(String.format("办案区%s 刷卡打电话id%s 页面打电话人员编号%s", baqid, id, rybh));
        ChasQqyz chasQqyz = findById(id);
        if (chasQqyz != null) {
            Integer status = chasQqyz.getZt();
            log.debug(String.format("当前亲情驿站状态[%d]", status));
            if (SYSCONSTANT.QQTEL_SPTG.equals(status)) {
                return processSPTGQqdh(baqid, chasQqyz.getWdbh(), chasQqyz);
            } else if (SYSCONSTANT.QQTEL_SYZ.equals(status)) {
                return processSYZGQqdh(baqid, chasQqyz);
            } else if (SYSCONSTANT.QQTEL_JS.equals(status)) {
                return DevResult.success("亲情电话使用结束");
            } else {
                return DevResult.success("亲情电话审批未通过");
            }
        } else {
            String msg = String.format("办案区{%s}人员{%s}未申请亲情电话记录,id%s", baqid, rybh, id);
            log.debug(msg);
            return DevResult.success(msg);
        }
    }

    private DevResult dhOnOff(String baqid, String rybh, String watchNo) {
        log.debug(String.format("办案区%s 刷卡打电话手环%s 页面打电话人员编号%s", baqid, watchNo, rybh));
        ChasQqyz chasQqyz = getQqdhByRybh(baqid, rybh, watchNo);
        if (chasQqyz != null) {
            Integer status = chasQqyz.getZt();
            log.debug(String.format("当前亲情驿站状态[%d]", status));
            if (SYSCONSTANT.QQTEL_SPTG.equals(status)) {
                return processSPTGQqdh(baqid, watchNo, chasQqyz);
            } else if (SYSCONSTANT.QQTEL_SYZ.equals(status)) {
                return processSYZGQqdh(baqid, chasQqyz);
            } else if (SYSCONSTANT.QQTEL_JS.equals(status)) {
                return DevResult.success("亲情电话使用结束");
            } else {
                return DevResult.success("亲情电话审批未通过");
            }
        } else {
            String msg = String.format("办案区{%s}人员{%s}未申请亲情电话记录,手环号%s", baqid, rybh, watchNo);
            log.debug(msg);
            return DevResult.success(msg);
        }
    }

    //处理使用中的亲情电话
    private DevResult processSYZGQqdh(String baqid, ChasQqyz chasQqyz) {
        log.debug("关闭亲情驿站电话，更新记录状态为结束");
        // 关闭亲情电话 并刷新LED 小屏
        DevResult result = phoneOpenOrClose(baqid, SYSCONSTANT.OFF, chasQqyz.getThsj());
        if (result.getCode() != 1) {
            return result;
        }
        chasQqyz.setZt(SYSCONSTANT.QQTEL_JS);
        chasQqyz.setThjssj(new Date());
        update(chasQqyz);
        ledService.refreshStation(baqid, "亲情驿站:空闲");
        return DevResult.success("亲情电话关闭成功");
    }

    //处理审批通过状态的亲情电话
    private DevResult processSPTGQqdh(String baqid, String watchNo, ChasQqyz chasQqyz) {
        log.debug("亲情驿站审审批通过，可以使用电话 更新亲情驿站申请表的状态为使用中");

        // 审批通过 打开电话
        DevResult result = phoneOpenOrClose(baqid, SYSCONSTANT.ON, chasQqyz.getThsj() * 60 * 1000);
        if (result.getCode() != 1) {
            return result;
        }
        chasQqyz.setThkssj(new Date());
        chasQqyz.setZt(SYSCONSTANT.QQTEL_SYZ);
        update(chasQqyz);
        ledService.refreshStation(baqid, String.format("亲情驿站:%s 通话中 共%d分钟", chasQqyz.getSqrxm(), chasQqyz.getThsj()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    log.debug("电话开启倒计时 办案区:" + chasQqyz.getBaqid() + "手环:" + watchNo);
                    dhOnOff(chasQqyz.getBaqid(), chasQqyz.getRybh(), watchNo);
                } catch (Exception e) {
                    log.error("电话开启倒计时异常 办案区:" + chasQqyz.getBaqid() + "手环:" + watchNo + " 异常:", e);
                }
            }
        }, chasQqyz.getThsj() * 60000);
        return DevResult.success("亲情电话打开成功");
    }

    //通过人员编号获取最新的一条亲情电话
    private ChasQqyz getQqdhByRybh(String baqid, String rybh, String watchNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        if (StringUtils.isNotEmpty(watchNo)) {
            map.put("wdbh_l", watchNo);
            map.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
            ChasRyjl chasRyjl = chasRyjlService.findByParams(map);
            if (chasRyjl != null) {
                map.put("rybh", chasRyjl.getRybh());
            }
        } else {
            map.put("rybh", rybh);
        }
        //查找不需要审批的申请
        map.put("bsp", "bsp");
        List<ChasQqyz> qqyzlist = findList(map, "lrsj desc");
        if (qqyzlist != null && !qqyzlist.isEmpty()) {
            return qqyzlist.get(0);
        }
        map.put("bsp", "");
        // 申请审批限制只存在一条审批通过未使用的记录
        qqyzlist = findList(map, "spsj desc");
        if (qqyzlist != null && !qqyzlist.isEmpty()) {
            return qqyzlist.get(0);
        }
        return null;
    }

    /**
     * 亲情电话继电器打开或关闭
     *
     * @param baqid 办案区id
     * @param onoff 打开或者关闭
     * @param time  通话时间
     * @return
     */
    private DevResult phoneOpenOrClose(String baqid, Integer onoff, long time) {

        final DevResult wr = new DevResult();
        String msg = "";
        //查找办案区所有亲情电话
        List<ChasSb> chasSblist = chasSbService.getBaqSbsByLxAndGn(baqid, SYSCONSTANT.SBLX_JDQ, SYSCONSTANT.SBGN_DKQQDH);
        if (chasSblist == null || chasSblist.isEmpty()) {

            ChasBaq baq = service.findById(baqid);
            msg = String.format("办案区:%s  未配置亲情电话", baq.getBaqmc());
            return DevResult.error(msg);
        }
        chasSblist.stream().map(sb -> sb.getSbbh()).forEach(sbbh -> {
            if (SYSCONSTANT.ON.equals(onoff)) {
                wr.add(sbbh, jdqService.openRelayBytime(baqid, sbbh, time));
            } else {
                wr.add(sbbh, jdqService.openOrClose(baqid, onoff, sbbh));
            }
        });
        wr.setCode(1);
        return wr;
    }

}
