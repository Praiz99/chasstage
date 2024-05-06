package com.wckj.chasstage.api.server.imp.baq;

import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.baq.model.*;
import com.wckj.chasstage.api.def.baq.service.ApiBaqService;
import com.wckj.chasstage.api.server.device.DataTB;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.signconfig.service.ChasXtSignConfigService;
import com.wckj.chasstage.modules.wszh.entity.ChasWszh;
import com.wckj.chasstage.modules.wszh.service.ChasWszhService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;

import java.util.*;

/**
 * @author wutl
 * @Title: 办案区服务
 * @Package 办案区服务
 * @Description: 办案区服务
 * @date 2020-9-815:15
 */
@Service
public class ApiBaqServiceImp implements ApiBaqService {
    private static final Logger log = LoggerFactory.getLogger(ApiBaqServiceImp.class);

    @Autowired
    private ChasBaqService baqService;

    @Autowired
    private ChasXtSignConfigService signConfigService;

    @Autowired
    private ChasWszhService chasWszhService;

    @Autowired
    private ChasXtBaqznpzService baqznpzService;

    @Autowired
    private DataTB dataTB;

    @Autowired
    private ChasXtGnpzService gnpzService;

    @Autowired
    private JdoneSysUserService jdoneSysUserService;

    /**
     * 保存 和 修改办案区
     *
     * @param baq
     * @return
     */
    @Override
    public ApiReturnResult<?> SaveWithUpdate(BaqBean baq) {
        try {
            if ((boolean) baqService.saveOrUpdate(baq).get("success")) {
                return ResultUtil.ReturnSuccess("操作成功!");
            } else {
                return ResultUtil.ReturnSuccess("操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ApiBaqServiceImp.save:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
    }

    /**
     * 删除办案区信息
     *
     * @param ids
     * @return
     */
    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            if (!"0".equals(baqService.delete(ids.split(",")))) {
                return ResultUtil.ReturnError("操作失败!");
            }
        } catch (Exception e) {
            log.error("ApiBaqServiceImp.delete:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 获取办案区分页数据
     *
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getBaqPageData(BaqParam param) {
        Map<String, Object> params = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(baqService, params);
        SessionUser sessionUser = WebContext.getSessionUser();
        if (sessionUser != null) {
            String loginId = sessionUser.getLoginId();
            // 只有admin查询所有办案区
            if ("admin".equals(loginId)&&StringUtils.isEmpty(param.getBaqid())) {
                params.put("baqid", null);
            }
        }
        if(StringUtils.isNotEmpty(String.valueOf(params.get("userIdCard")))){//预约权限控制。 根据用户所在分局，仅可以预约本分局所属的大中心或下属办案区
            JdoneSysUser user = jdoneSysUserService.findSysUserByIdCard(String.valueOf(params.get("userIdCard")));
            if(user != null){
                params.put("qxbs", "reg");
                params.put("orgSysCode", user.getOrgSysCode());
            }
        }
        return ResultUtil.ReturnSuccess(baqService.getBaqPageData(param.getPage(), param.getRows(), params, "xgsj desc"));
    }


    /**
     * 保存签字捺印配置
     *
     * @param config
     * @return
     */
    @Override
    public ApiReturnResult<?> saveSignConfig(SignConfigBean config) {
        try {
            signConfigService.saveSignConfig(config);
        } catch (Exception e) {
            log.error("saveSignConfig:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 获取签字捺印配置
     *
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getSignConfigPageData(SignConfigParam param) {
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", param.getBaqid());
        DataQxbsUtil.getSelectAll(signConfigService, params);
        return ResultUtil.ReturnSuccess(signConfigService.getSignConfigPageData(param.getPage(), param.getRows(), params, null));
    }

    /**
     * 获取文书字号分页数据
     *
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getWszhPageData(WszhParam param) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", param.getBaqid());
        params.put("zht", param.getZht());
        params.put("zh", param.getZh());
        DataQxbsUtil.getSelectAll(chasWszhService, params);
        return ResultUtil.ReturnSuccess(chasWszhService.getWszhPageData(param.getPage(), param.getRows(), params, null));
    }

    @Override
    public ApiReturnResult<?> getBaqDicByUser(String queryValue, int page, int pagesize) {
        DevResult result = baqService.getBaqDicByUser(queryValue, page, pagesize);
        if (result.getCode() == 1) {
            return ResultUtil.ReturnSuccess("获取数据成功", result.getData());
        } else {
            return ResultUtil.ReturnError(result.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getWszhPData(String baqid) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isEmpty(baqid)) {
            return ResultUtil.ReturnSuccess(new WszhBean());
        }
        params.put("baqid", baqid);
        try {
            List<ChasWszh> wszh1 = chasWszhService.findList(params, "");
            if (wszh1.size() > 0) {
                WszhBean bean = new WszhBean();
                MyBeanUtils.copyBeanNotNull2Bean(wszh1.get(0), bean);

                return ResultUtil.ReturnSuccess("获取数据成功", bean);
            } else {
                WszhBean wszhBean = new WszhBean();
                wszhBean.setId("");
                return ResultUtil.ReturnSuccess(wszhBean);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }

    /**
     * 保存修改文书字号信息
     *
     * @param wszh
     * @return
     */
    @Override
    public ApiReturnResult<?> saveWithUpdateInstrument(WszhBean wszh) {
        try {
            chasWszhService.SaveWithUpdate(wszh);
        } catch (Exception e) {
            log.error("SaveWithUpdateInstrument:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 删除文书字号信息
     *
     * @param ids
     * @return
     */
    @Override
    public ApiReturnResult<?> deleteWszh(String ids) {
        try {
            chasWszhService.deleteByids(ids);
        } catch (Exception e) {
            log.error("deleteWszh:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }


    /**
     * 获取办案配置分配数据
     *
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getBaqznpzPageData(BaqznpzParam param) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", param.getBaqid());
        DataQxbsUtil.getSelectAll(baqznpzService, params);
        return ResultUtil.ReturnSuccess(baqznpzService.getBaqznpzPageData(param.getPage(), param.getRows(), params, null));
    }

    @Override
    public ApiReturnResult<?> getBaqPzData(String baqid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", baqid);
        return ResultUtil.ReturnSuccess(baqznpzService.getBaqznpzPageData(-1, -1, params, null));

    }

    @Override
    public ApiReturnResult<?> getBaqSingnData(String baqid) {
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        return ResultUtil.ReturnSuccess(signConfigService.findByBaqid(baqid));
    }

    /**
     * 保存办案区信息
     *
     * @param baqznpz
     * @return
     */
    @Override
    public ApiReturnResult<?> saveBaqznpz(BaqznpzBean baqznpz) {
        try {
            baqznpzService.saveBaqznpz(baqznpz);
            return ResultUtil.ReturnSuccess("操作成功!");
        } catch (Exception e) {
            log.error("saveBaqznpz:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
    }


    @Override
    public ApiReturnResult<?> tbBaqqy(String baqId) {
        try {
            DevResult result = dataTB.baqQyTb(baqId);
            if (result.getCode() == 0) {
                return ResultUtil.ReturnSuccess(result.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("同步区域失败", e);
        }
        return ResultUtil.ReturnError("同步区域失败");
    }

    @Override
    public ApiReturnResult<?> tbSb(String baqId) {
        try {
            DevResult result = dataTB.getDeviceByOrg(baqId, "1");
            if (result.getCode() == 1) {
                return ResultUtil.ReturnSuccess(result.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("同步设备失败", e);
        }
        return ResultUtil.ReturnError("同步设备失败");
    }

    @Override
    public ApiReturnResult<?> baqtb(String baqid) {
        ChasBaq baq = baqService.findById(baqid);
        try {
            DevResult result = dataTB.baqTb(baqid, baq.getBaqmc());
            if (result.getCode() == 1) {
                return ResultUtil.ReturnSuccess(result.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("同步办案区失败", e);
        }
        return ResultUtil.ReturnError("同步办案区失败");
    }

    /**
     * 根据当前登录人获取办案区信息
     *
     * @return
     */
    @Override
    public ApiReturnResult<BaqBean> getBaqByLogin() {
        ApiReturnResult<BaqBean> returnResult = new ApiReturnResult<>();
        SessionUser sessionUser = WebContext.getSessionUser();
        try {
            ChasBaq byParams = null;
            //优先查询当前登录人的责任单位办案区
            //首先根据区县查询，如果没有查询法制大队的责任，如果没有返回空
            //优先取配置了大中心标记的区县级别办案区座位登录人办案区
            Map<String, Object> paramzr = new HashMap<>();
            String qxdm = sessionUser.getCurrentOrgSysCode().substring(0, 6);
            paramzr.put("sysCode", qxdm);
            paramzr.put("sfdzx", 1);
            List<ChasBaq> dzxbaqList = baqService.findListByParams(paramzr);
            if (dzxbaqList.size() > 0) {
                byParams = dzxbaqList.get(0);
            }else{
                paramzr.clear();
                //paramzr.put("dwdm", sessionUser.getCurrentOrgCode().substring(0, 6) + "000000");
                paramzr.put("dwdm", sessionUser.getCurrentOrgCode());
                List<ChasBaq> zrbaqList = baqService.findListByParams(paramzr);
                if (zrbaqList.size() > 0) {
                    byParams = zrbaqList.get(0);
                } else {
                    //paramzr.put("dwdm", sessionUser.getCurrentOrgCode().substring(0, 6) + "180000");
                    paramzr.put("dwdm", sessionUser.getCurrentOrgCode());
                    zrbaqList = baqService.findListByParams(paramzr);
                    if (zrbaqList.isEmpty()) {
                        BaqBean baqBean = new BaqBean();
                        baqBean.setId("");
                        return ResultUtil.ReturnSuccess(baqBean);
                    } else {
                        byParams = zrbaqList.get(0);
                    }
                    //查不到责任办案区，取第一个办案区
                /*Map<String, Object> param = new HashMap<>();
                param.put("sydwdm", sessionUser.getCurrentOrgCode());
                byParams = null;
                List<ChasBaq> baqList = baqService.findListByParams(param);
                if (baqList.size() > 0) {
                    byParams = baqList.get(0);
                }*/
                }
            }
            if (byParams == null) {
                returnResult.setCode("500");
                returnResult.setMessage("当前登录人单位，未配置办案区！");
                return returnResult;
            }
            BaqBean baqBean = new BaqBean();
            MyBeanUtils.copyBeanNotNull2Bean(byParams, baqBean);
            returnResult.setCode("200");
            returnResult.setData(baqBean);
            returnResult.setMessage("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("获取异常：" + e.getMessage());
        }
        return returnResult;
    }

    /**
     * 根据办案区id获取办案区配置
     *
     * @param baqid
     * @return
     */
    @Override
    public ApiReturnResult<BaqFunCfg> getBaqcfgByBaqid(String baqid) {
        ApiReturnResult<BaqFunCfg> baqCfgResult = new ApiReturnResult<>();
        BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
        String baqVmsurl = configuration.getBaqVmsurl();
        String rlbdType = configuration.getRlbdType();
        String sfqyface = configuration.getSfqyFace();
        BaqFunCfg funCfg = new BaqFunCfg();
        int gw1qz = 1;
        int gw1ny = 1;
        int gw2qz = 1;
        int gw2ny = 1;
        int htqz = 1;
        int htny = 1;
        int sswpDxg = 0;
        String dkqpz = "01";
        int csfs = 1;
        int sfsjg = 0;
        int wlsxt = 0;
        int gpypz = 0;
        int drcssfsp = 1;
        int plscsfcp = 1;
        int ysgjsp = 0;
        int rzbdfs = 60;
        int sfqyxl = 1;
        int sfqyrldw = 1;
        int sxsymslfp = 0;
        if (configuration.getQznyZzGw1()) {
            gw1ny = 1;
        }
        if (configuration.getQznyYltGw1()) {
            gw1ny = 2;
        }
        if (configuration.getQznyZkGw1()) {
            gw1ny = 3;
        }
        if (configuration.getQznyHsGw1()) {
            gw1qz = 1;
        }
        if (configuration.getQznyWcGw1()) {
            gw1qz = 2;
        }
        if (configuration.getQznyWacomGw1()) {
            gw1qz = 3;
        }
        if (configuration.getQznyHwGw1()) {
            gw1qz = 4;
        }
        if(configuration.getQznyYfdpGw1()) {
            gw1qz = 7;
        }

        if (configuration.getQznyZzGw2()) {
            gw2ny = 1;
        }
        if (configuration.getQznyYltGw2()) {
            gw2ny = 2;
        }
        if (configuration.getQznyZkGw2()) {
            gw2ny = 3;
        }
        if (configuration.getQznyHsGw2()) {
            gw2qz = 1;
        }
        if (configuration.getQznyWcGw2()) {
            gw2qz = 2;
        }
        if (configuration.getQznyWacomGw2()) {
            gw2qz = 3;
        }
        if (configuration.getQznyHwGw2()) {
            gw2qz = 4;
        }
        if(configuration.getQznyYfdpGw2()) {
            gw2qz = 7;
        }

        if (configuration.getQznyZz()) {
            htny = 1;
        }
        if (configuration.getQznyYlt()) {
            htny = 2;
        }
        if (configuration.getQznyZk()) {
            htny = 3;
        }
        if (configuration.getQznyHs()) {
            htqz = 1;
        }
        if (configuration.getQznyWc()) {
            htqz = 2;
        }
        if (configuration.getQznyWacom()) {
            htqz = 3;
        }
        if (configuration.getQznyHw()) {
            htqz = 4;
        }
        if(configuration.getQznyYfdp()) {
            htqz = 7;
        }

        if (configuration.getRyrsJl()) {
            dkqpz = "01";
        }
        if (configuration.getRyrsHs()) {
            dkqpz = "02";
        }
        if (configuration.getRyrsLtdkq()) {
            dkqpz = "03";
        }
        if (configuration.getRyrsDxg()) {
            sswpDxg = 1;
        } else {
            sswpDxg = 0;
        }
        if (configuration.getRycsYm()) {
            csfs = 1;
        }
        if(configuration.getSxsYmSlfp()){
            sxsymslfp=1;
        }
        //刷卡终端出所
        if (configuration.getRycsSk()) {
            csfs = 0;
        }
        //单人出所审批
        if (configuration.getRycsOnly()) {
            drcssfsp = 1;
        } else {
            drcssfsp = 0;
        }
        //批量出所审批
        if (configuration.getRycsMultiple()) {
            plscsfcp = 1;
        } else {
            plscsfcp = 0;
        }
        //是否显示手机柜
        if (configuration.getPhonecab()) {
            sfsjg = 1;
        } else {
            sfsjg = 0;
        }
        if (configuration.getDwGjspYsgjsp()) {
            ysgjsp = 1;
        } else {
            ysgjsp = 0;
        }
        String baqrlsburl = configuration.getBaqrlsburl();
        if (StringUtils.isNotEmpty(baqrlsburl)) {
            funCfg.setFaceUrl(baqrlsburl);
        }
        String rzbdscore = SysUtil.getParamValue("chasstage.rzbd.score");
        if (StringUtils.isNotEmpty(rzbdscore)) {
            try {
                rzbdfs = Integer.parseInt(rzbdscore);
            } catch (Exception e) {
            }
        }
        //随身物品网络摄像头
        boolean sswpWlsxt = configuration.getSswpWlsxt();
        if(sswpWlsxt){
            wlsxt = 1;
        }else{
            wlsxt = 0;
        }
        //随身物品高拍仪
        boolean sswpGpy = configuration.getSswpGpy();
        if(sswpGpy){
            gpypz = 1;
        }else{
            gpypz = 0;
        }

        //是否启用心率
        if (configuration.getDwXl()) {
            sfqyxl = 1;
        } else {
            sfqyxl = 0;
        }
        //是否启用人脸定位
        if(configuration.getDwHkrl()){
            sfqyrldw = 1;
        }else{
            sfqyrldw = 0;
        }
        funCfg.setSfqyxl(sfqyxl);
        funCfg.setWlsxt(wlsxt);
        funCfg.setGpypz(gpypz);
        funCfg.setRzbdfs(rzbdfs);
        funCfg.setDkqpz(dkqpz);
        funCfg.setGw1ny(gw1ny);
        funCfg.setCsfs(csfs);
        funCfg.setGw1qz(gw1qz);
        funCfg.setGw2ny(gw2ny);
        funCfg.setGw2qz(gw2qz);
        funCfg.setHtny(htny);
        funCfg.setHtqz(htqz);
        funCfg.setSswpDxg(sswpDxg);
        funCfg.setDrcssfsp(drcssfsp);
        funCfg.setPlscsfcp(plscsfcp);
        funCfg.setSfsjg(sfsjg);
        funCfg.setYsgjsp(ysgjsp);
        funCfg.setBaqVmsurl(baqVmsurl);
        funCfg.setSfqyrldw(sfqyrldw);
        funCfg.setSxsymslfp(sxsymslfp);
        funCfg.setRlbdType(rlbdType);
        funCfg.setSfqyface(StringUtils.isNotEmpty(sfqyface)? sfqyface: "0");
        funCfg.setZdypz(JSONObject.parseObject(gnpz.getZdypz(),Map.class));
        baqCfgResult.setMessage("获取成功");
        baqCfgResult.setCode("200");
        baqCfgResult.setData(funCfg);
        return baqCfgResult;
    }

    @Override
    public ApiReturnResult<List<BaqGnglBean>> getBaqgnglByBaqid(String baqid) {
        List<ChasXtGnpz> gnpzs = gnpzService.findList(null, "lrsj asc");
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        List<BaqGnglBean> baqGnpzBeans = new ArrayList<>();
        if (baqznpz == null) {
            baqznpz = new ChasXtBaqznpz();
        }
        try {
            for (ChasXtGnpz gnpz : gnpzs) {
                BaqGnglBean gnglBean = new BaqGnglBean();
                gnglBean.setId(gnpz.getId());
                gnglBean.setBaqid(baqid);
                //设置办案区的接口配置，接口配置属于办案区
                gnglBean.setGnpzBean(JsonUtil.parse(gnpz.getGnpz(), BaqGnpzBean.class));
                BaqGnpzBean gnpzBean = gnglBean.getGnpzBean();
                setJkpzValue(baqznpz, gnpzBean);
                gnglBean.setName(gnpz.getName());
                gnglBean.setPxh(gnpz.getPxh() + "");
                gnglBean.setSfkbj(gnpz.getSfkbj());
                if (StringUtil.equals(baqznpz.getGnpzid(), gnpz.getId())) {
                    gnglBean.setSfqy("1");
                } else {
                    gnglBean.setSfqy("0");
                }
                gnglBean.setSbpzBean(JsonUtil.parse(gnpz.getSbpz(), BaqSbpzBean.class));
                String s = fZdypz(gnpz.getZdypz());
                gnglBean.setZdyData(s);
                baqGnpzBeans.add(gnglBean);
            }
        } catch (Exception e) {
            log.error("getBaqgnglByBaqid:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!", baqGnpzBeans);
    }

    @Override
    public ApiReturnResult<?> saveBaqgnglByBaqid(BaqGnglBean baqGnglBean) {
        ChasXtGnpz gnpz = new ChasXtGnpz();
        try {
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqGnglBean.getBaqid());
            String zdyS = cZdypz(baqGnglBean.getZdyData());
            if (StringUtil.isNotEmpty(baqGnglBean.getId())) {
                gnpz = gnpzService.findById(baqGnglBean.getId());
                gnpz.setName(baqGnglBean.getName());
                if (StringUtil.isNotEmpty(baqGnglBean.getPxh())) {
                    gnpz.setPxh(Integer.parseInt(baqGnglBean.getPxh()));
                }
                gnpz.setGnpz(JsonUtil.toJson(baqGnglBean.getGnpzBean()));
                gnpz.setSbpz(JsonUtil.toJson(baqGnglBean.getSbpzBean()));
                gnpz.setSfkbj(SYSCONSTANT.SF_S);
                gnpz.setZdypz(zdyS);
                gnpzService.update(gnpz);
            } else {
                SessionUser user = WebContext.getSessionUser();
                gnpz.setId(StringUtils.getGuid32());
                gnpz.setName(baqGnglBean.getName());
                if (StringUtil.isNotEmpty(baqGnglBean.getPxh())) {
                    gnpz.setPxh(Integer.parseInt(baqGnglBean.getPxh()));
                }
                gnpz.setGnpz(JsonUtil.toJson(baqGnglBean.getGnpzBean()));
                gnpz.setSbpz(JsonUtil.toJson(baqGnglBean.getSbpzBean()));
                gnpz.setIsdel(0);
                gnpz.setSfkbj(SYSCONSTANT.SF_S);
                if(user != null){
                    gnpz.setLrrSfzh(user.getIdCard());
                }
                gnpz.setLrsj(new Date());
                gnpz.setZdypz(zdyS);
                gnpzService.save(gnpz);
            }
            if (StringUtil.equals(baqGnglBean.getSfqy(), "1")) {
                if (baqznpz == null) {
                    baqznpz.setId(StringUtils.getGuid32());
                    baqznpz.setBaqid(baqGnglBean.getBaqid());
                    baqznpz.setConfiguration(JsonUtil.toJson(baqGnglBean.getGnpzBean()));
                    baqznpz.setGnpzid(gnpz.getId());
                    baqznpzService.save(baqznpz);
                } else {
                    baqznpz.setConfiguration(JsonUtil.toJson(baqGnglBean.getGnpzBean()));
                    baqznpz.setGnpzid(gnpz.getId());
                    baqznpzService.update(baqznpz);
                }
            } else {
                //不是选中的，但是如果存在配置，则修改配置中的接口配置
                if (baqznpz != null) {
                    String configuration = baqznpz.getConfiguration();
                    BaqGnpzBean baqpz = JsonUtil.parse(configuration, BaqGnpzBean.class);
                    BaqGnpzBean gnpzBean = baqGnglBean.getGnpzBean();
                    baqpz.setRldsj_zzpt(gnpzBean.getRldsj_zzpt());
                    baqpz.setRldsj_khpt(gnpzBean.getRldsj_khpt());
                    baqpz.setZz_rlbd_url(gnpzBean.getZz_rlbd_url());
                    baqpz.setZz_rlbd_ackey(gnpzBean.getZz_rlbd_ackey());
                    baqpz.setZz_rlbd_sekey(gnpzBean.getZz_rlbd_sekey());
                    baqpz.setBaqVmsurl(gnpzBean.getBaqVmsurl());
                    baqpz.setBaqrlsburl(gnpzBean.getBaqrlsburl());
                    baqpz.setRlbd_type(gnpzBean.getRlbd_type());
                    //依图人脸配置
                    baqpz.setRlbd_url(gnpzBean.getRlbd_url());
                    baqpz.setRlbd_param(gnpzBean.getRlbd_param());
                    //海康人脸定位配置
                    baqpz.setHkrldw_url(gnpzBean.getHkrldw_url());
                    baqpz.setHkrldw_param(gnpzBean.getHkrldw_param());
                    baqpz.setHkrldw_place_code(gnpzBean.getHkrldw_place_code());
                    baqpz.setHkafpt_url(gnpzBean.getHkafpt_url());
                    baqpz.setHkafpt_param(gnpzBean.getHkafpt_param());
                    baqpz.setHkafptxfrl_param(gnpzBean.getHkafptxfrl_param());
                    baqznpz.setConfiguration(JsonUtil.toJson(baqpz));
                    baqznpzService.update(baqznpz);
                }
            }
        } catch (Exception e) {
            log.error("saveBaqgnglByBaqid:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    private String fZdypz(String zdyData){
        List<Map<String,Object>> list = new ArrayList<>();
        String zdyS = "";
        Map<String, Object> map = JSONObject.parseObject(zdyData, Map.class);
        if(map != null){
            Set<String> strings = map.keySet();
            for (String key : strings) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("csmc",key);
                map1.put("csz",map.get(key));
                list.add(map1);
            }
            zdyS = JSONObject.toJSONString(list);
        }
        return zdyS;
    }

    private String cZdypz(String zdyData){
        Map<String, Object> zdyMap = new HashMap<>();
        List<Map<String,Object>> map = JSONObject.parseObject(zdyData, List.class);
        if(map != null) {
            for (Map<String, Object> objectMap : map) {
                String csmc = (String) objectMap.get("csmc");
                if(StringUtils.isNotEmpty(csmc)){
                    String csz = (String) objectMap.get("csz");
                    zdyMap.put(csmc,csz);
                }
            }
        }
        String zdyS = JSONObject.toJSONString(zdyMap);
        return zdyS;
    }

    @Override
    public ApiReturnResult<?> deleteBaqgnglByBaqid(String id) {
        try {
            gnpzService.deleteById(id);
        } catch (Exception e) {
            log.error("deleteBaqgnglByBaqid:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    @Override
    public ApiReturnResult<?> checkInBaqpz(String baqid, String gnpzId) {
        try {
            ChasXtGnpz gnpz = gnpzService.findById(gnpzId);
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
            if (baqznpz == null) {
                baqznpz = new ChasXtBaqznpz();
                baqznpz.setId(StringUtils.getGuid32());
                baqznpz.setGnpzid(gnpzId);
                //选中的时候接口配置不更改。
                String gnpzJson = gnpz.getGnpz();
                BaqGnpzBean baqpz = JsonUtil.parse(gnpzJson, BaqGnpzBean.class);
                setJkpzValue(baqznpz, baqpz);
                baqznpz.setConfiguration(JsonUtil.toJson(baqpz));
                baqznpz.setBaqid(baqid);
                baqznpzService.save(baqznpz);
            } else {
                baqznpz.setGnpzid(gnpzId);
                //选中的时候接口配置不更改。
                String gnpzJson = gnpz.getGnpz();
                BaqGnpzBean baqpz = JsonUtil.parse(gnpzJson, BaqGnpzBean.class);
                setJkpzValue(baqznpz, baqpz);
                baqznpz.setConfiguration(JsonUtil.toJson(baqpz));
                baqznpzService.update(baqznpz);
            }
        } catch (Exception e) {
            log.error("checkInBaqpz:", e);
            return ResultUtil.ReturnError("操作失败:" + e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 设置接口配置的值
     *
     * @param baqznpz
     * @param baqpz
     */
    private void setJkpzValue(ChasXtBaqznpz baqznpz, BaqGnpzBean baqpz) {
        BaqConfiguration baqConfiguration = baqznpz.getBaqConfiguration();
        baqpz.setRldsj_zzpt(baqConfiguration.getRldsjZzpt() ? "1" : "0");
        baqpz.setRldsj_khpt(baqConfiguration.getRldsjHkpt() ? "1" : "0");
        baqpz.setZz_rlbd_url(baqConfiguration.getZzRlbdUrl());
        baqpz.setZz_rlbd_ackey(baqConfiguration.getZzRlbdAckey());
        baqpz.setZz_rlbd_sekey(baqConfiguration.getZzRlbdSekey());
        baqpz.setBaqVmsurl(baqConfiguration.getBaqVmsurl());
        baqpz.setBaqrlsburl(baqConfiguration.getBaqrlsburl());
        baqpz.setRlbd_url(baqConfiguration.getRlbdUrl());
        baqpz.setRlbd_type(baqConfiguration.getRlbdType());
        baqpz.setRlbd_param(baqConfiguration.getRlbdParam());
        baqpz.setHkrldw_url(baqConfiguration.getHkrldwUrl());
        baqpz.setHkrldw_param(baqConfiguration.getHkrldwParam());
        baqpz.setHkrldw_place_code(baqConfiguration.getHkrldwPlaceCode());
        baqpz.setHkafpt_url(baqConfiguration.getHkafptUrl());
        baqpz.setHkafpt_param(baqConfiguration.getHkafptParam());
        baqpz.setHkafptxfrl_param(baqConfiguration.getHkafptxfrlParam());
    }

    /**
     * 根据当前登录人获取下面办案区（市级20 区县30 基层40）
     *
     * @return
     */
    @Override
    public ApiReturnResult<?> getBaqDicByLogin() {
        ApiReturnResult<Map<String, Object>> returnResult = new ApiReturnResult<>();
        try {
            SessionUser sessionUser = WebContext.getSessionUser();
            Integer roleLevel = sessionUser.getRoleLevel();
            String sysCode = sessionUser.getCurrentOrgSysCode();
            String sysCodeLike = sysCode;
            if (20 == roleLevel) {
                sysCodeLike = sysCode.substring(0, 4);
            } else if (30 == roleLevel) {
                sysCodeLike = sysCode.substring(0, 6);
            } else if (10 == roleLevel) {
                sysCodeLike = sysCode.substring(0, 2);
            } else {
                sysCodeLike = sysCode.substring(0, 6);
            }
            List<ChasBaq> baqList = baqService.getBaqDicByLogin(sysCodeLike);
            List<Map<String, Object>> baqDicMapList = new ArrayList<>();
            for (int i = 0; i < baqList.size(); i++) {
                ChasBaq chasBaq = baqList.get(i);
                Map<String, Object> dicMap = new HashMap<>();
                dicMap.put("code", chasBaq.getId());
                dicMap.put("name", chasBaq.getBaqmc());
                baqDicMapList.add(dicMap);
            }
            returnResult.setCode("200");
            returnResult.setMessage("获取成功");
            Map<String, Object> data = new HashMap<>();
            data.put("Rows", baqDicMapList);
            data.put("Total", baqDicMapList.size());
            returnResult.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("获取失败" + e.getMessage());
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> getStartBhByLogin() {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        try {
            SessionUser sessionUser = WebContext.getSessionUser();
            Integer roleLevel = sessionUser.getRoleLevel();
            String sysCode = sessionUser.getCurrentOrgSysCode();
            String startWith = sysCode;
            if (20 == roleLevel) {
                startWith = sysCode.substring(0, 4);
            } else if (30 == roleLevel) {
                startWith = sysCode.substring(0, 6);
            } else if (10 == roleLevel) {
                startWith = sysCode.substring(0, 2);
            } else {
                startWith = sysCode.substring(0, 6);
            }
            returnResult.setCode("200");
            returnResult.setMessage("获取成功");
            returnResult.setData(startWith);
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("获取失败" + e.getMessage());
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> xgqzlx(String id, String[] checkedCities) {
        try {
            signConfigService.xgqzlx(id,checkedCities);
        } catch (Exception e) {
            log.error("saveSignConfig:", e);
            return ResultUtil.ReturnError("操作失败!");
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }
}
