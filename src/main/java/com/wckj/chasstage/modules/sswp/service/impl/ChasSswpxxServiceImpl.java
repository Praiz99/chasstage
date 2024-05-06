package com.wckj.chasstage.modules.sswp.service.impl;

import com.wckj.api.def.zfba.model.ApiGgYwbh;
import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.def.wpgl.model.SswpBean;
import com.wckj.chasstage.api.server.device.IWpgService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sswp.dao.ChasSswpxxMapper;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import com.wckj.jdone.modules.sys.util.DicUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.*;

@Service
@Transactional
public class ChasSswpxxServiceImpl extends BaseService<ChasSswpxxMapper, ChasSswpxx> implements ChasSswpxxService {
    private static final Logger log = Logger.getLogger(ChasSswpxxServiceImpl.class);

    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasSswpZpxxService zpxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    //    @Autowired
//    private ApiGgYwbhService jdoneComYwbhService;
    @Autowired
    private IWpgService iWpgService;
    @Autowired
    private ChasSswpxgService sswpxgService;

    @Override
    public Map<String, Object> saveOrupdate(String id, String sswpxgsJson, String delIds, ChasSswpxx sswpxx, String wplb) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        SessionUser user = WebContext.getSessionUser();
        ApiGgYwbhService jdoneComYwbhService = ServiceContext.getServiceByClass(ApiGgYwbhService.class);
        try {
            if (StringUtils.isNotEmpty(delIds)) {
                String[] idstr = delIds.split(",");
                for (String sswpid : idstr) {
                    ChasSswpxx ss = findById(sswpid);
                    if (deleteById(sswpid) == 1) {
                        if (ss != null) {
                            String rybh = ss.getRybh();
                            if (StringUtils.isNotEmpty(rybh)) {
                                ChasBaqryxx ryxxT = null;
                                Map<String, Object> ryParam = new HashMap<String, Object>();
                                ryParam.put("rybh", rybh);
                                List<ChasBaqryxx> ryxxList = baqryxxService.findList(ryParam, "");
                                if (ryxxList.size() > 0) {
                                    ryxxT = ryxxList.get(0);
                                }
                                if (ryxxT != null) {
                                    String baqRysswpzt = getBaqRySswpZtByRybh(rybh);
                                    if (StringUtils.isNotEmpty(baqRysswpzt)) {
                                        ryxxT.setSswpzt(baqRysswpzt);
                                        baqryxxService.update(ryxxT);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (StringUtils.isEmpty(id)) {//新增页面处理
                JSONObject sswpxxs = JSONObject.fromObject(sswpxgsJson);
                if (!sswpxxs.isNullObject()) {
                    JSONObject json = JSONObject.fromObject(sswpxxs);
                    int size = json.size();
                    for (int i = 0; i < size; i++) {
                        ChasBaqryxx ryxxT = null;
                        JSONObject o = (JSONObject) json.get("" + i);
                        ChasSswpxx temp = (ChasSswpxx) JSONObject.toBean(o, ChasSswpxx.class);
                        if (StringUtils.isNotEmpty(temp.getId())) {
                            ChasSswpxx sswpT = findById(temp.getId());
                            if (sswpT != null) {
                                MyBeanUtils.copyBeanNotNull2Bean(temp, sswpT);
                                update(sswpT);
                            }
                        } else {
                            ApiGgYwbh comYwbh = null;
                            ChasRyjl ryjl = ryjlService.findUsedLockerById(temp.getCfwz());
                            if (ryjl != null) {
                                //排除自己
                                boolean tips = false;
                                //物品柜
                                if (StringUtil.equals(wplb, "qt")) {
                                    if (StringUtil.isNotEmpty(ryjl.getCwgId()) && !StringUtil.equals(ryjl.getCwgId(), temp.getCfwz())) {
                                        tips = true;
                                    }
                                }
                                //手机柜
                                if (StringUtil.equals(wplb, "sj")) {
                                    if (StringUtil.isNotEmpty(ryjl.getSjgId()) && !StringUtil.equals(ryjl.getSjgId(), temp.getCfwz())) {
                                        tips = true;
                                    }
                                }
                                if (tips) {
                                    result.put("success", false);
                                    result.put("msg", "所选存放柜已被占用,请重新选择!");
                                    return result;
                                }
                            } else {
                                ryjl = ryjlService.findRyjlByRybh("", temp.getRybh());
                                ChasBaqryxx baqryxx = baqryxxService.findByRybh(temp.getRybh());
                                //初次分配
                                if (StringUtil.equals(wplb, "qt")) {
                                    if (StringUtil.equals(ryjl.getCwgId(), "0") || StringUtil.isEmpty(ryjl.getCwgId())) {
                                        ChasSswpxg sswpxg = sswpxgService.findById(temp.getCfwz());
                                        if (sswpxg != null) {
                                            ryjl.setXgrSfzh(baqryxx.getMjSfzh());
                                            ryjl.setCwgId(sswpxg.getSbId());
                                            ryjl.setCwgBh(sswpxg.getBh());
                                            ryjl.setCwgLx("");
                                            DevResult devResult = iWpgService.cwgBd(ryjl.getBaqid(), ryjl.getRybh(), sswpxg.getBh());
                                            if (devResult.getCode() != 1) {
                                                result.put("success", false);
                                                result.put("msg", "绑定储物柜失败:" + devResult.getMessage());
                                                return result;
                                            }
                                            ryjlService.update(ryjl);
                                        }
                                    }
                                }
                                if (StringUtil.equals(wplb, "sj")) {
                                    if (StringUtil.equals(ryjl.getSjgId(), "0") || StringUtil.isEmpty(ryjl.getSjgId())) {
                                        //初次分配
                                        ChasSswpxg sswpxg = sswpxgService.findById(temp.getCfwz());
                                        if (sswpxg != null) {
                                            ryjl.setXgrSfzh(baqryxx.getMjSfzh());
                                            ryjl.setSjgId(sswpxg.getSbId());
                                            ryjl.setSjgBh(sswpxg.getBh());
                                            DevResult devResult = iWpgService.sjgBd(ryjl.getBaqid(), ryjl.getRybh(), sswpxg.getBh());
                                            if (devResult.getCode() != 1) {
                                                result.put("success", false);
                                                result.put("msg", "绑定手机柜失败:" + devResult.getMessage());
                                                return result;
                                            }
                                            ryjlService.update(ryjl);
                                        }
                                    }
                                }
                            }
                            try {
                                comYwbh = jdoneComYwbhService.getYwbh("W", user.getOrgCode(), null);
                                temp.setId(StringUtils.getGuid32());
                                temp.setLrrSfzh(user.getIdCard());
                                temp.setXgrSfzh(user.getIdCard());
                                temp.setSfznbaq(1);
                                if (StringUtils.isNotEmpty(temp.getRybh())) {
                                    Map<String, Object> ryParam = new HashMap<String, Object>();
                                    ryParam.put("rybh", temp.getRybh());
                                    List<ChasBaqryxx> ryxxList = baqryxxService.findList(ryParam, "");
                                    if (ryxxList.size() > 0) {
                                        ryxxT = ryxxList.get(0);
                                        temp.setBaqid(ryxxT.getBaqid());
                                        temp.setBaqmc(ryxxT.getBaqmc());
                                    }
                                }
                                temp.setWpbh(comYwbh.getYwbh());
                                temp.setZbrSfzh(user.getIdCard());
                                temp.setZbrXm(user.getName());
                                if (StringUtils.isNotEmpty(user.getOrgCode())) {
                                    temp.setZbdwBh(user.getOrgCode());
                                }
                                temp.setLx(SYSCONSTANT.SSWPLX_SSWP);//随身物品
                                temp.setWpzt(SYSCONSTANT.SSWPZT_ZSWP);//在所物品
                                if (StringUtils.isNotEmpty(user.getOrgSysCode())) {
                                    temp.setDwxtbh(user.getOrgSysCode());
                                }
                                if (StringUtils.isEmpty(temp.getDwxtbh())) {
                                    result.put("success", false);
                                    result.put("msg", "保存失败:系统单位编号不存在!");
                                    return result;
                                }
                                String wjid = temp.getWjid();
                                temp.setWjid("");  //统一置空，将所有照片关联信息存到zpxx关联表中
                                temp.setRksj(new Date());
                                save(temp);
                                String useRybh = temp.getRybh();
                                if (StringUtils.isNotEmpty(useRybh)) {
                                    String baqRysswpzt = getBaqRySswpZtByRybh(temp.getRybh());
                                    if (StringUtils.isNotEmpty(baqRysswpzt)) {
                                        ryxxT.setSswpzt(baqRysswpzt);
                                        baqryxxService.update(ryxxT);
                                    }
                                }

                                //保存文件照片信息
                                ChasSswpZpxx zpxx = new ChasSswpZpxx();
                                zpxx.setId(StringUtils.getGuid32());
                                zpxx.setLrrSfzh(user.getIdCard());
                                zpxx.setLrsj(new Date());
                                if (ryxxT != null) {
                                    zpxx.setBaqid(ryxxT.getBaqid());
                                    zpxx.setBaqmc(ryxxT.getBaqmc());
                                    zpxx.setRybh(ryxxT.getRybh());
                                }
                                zpxx.setBizId(wjid);
                                zpxx.setDataFlag("");
                                zpxx.setIsdel(0);
                                zpxx.setWpid(temp.getId());
                                zpxx.setZplx(SYSCONSTANT.SSWP_ZP_MX);  //明细照片
                                zpxx.setXgrSfzh(user.getIdCard());
                                zpxx.setXgsj(new Date());
                                zpxxService.save(zpxx);

                                jdoneComYwbhService.ywbhCommit(comYwbh);
                            } catch (Exception e) {
                                jdoneComYwbhService.ywbhRollBack(comYwbh);
                                throw new Exception(e);
                            }
                        }
                        result.put("success", true);
                        result.put("msg", "保存成功!");
                    }
                }
            } else {//修改页面
                sswpxx.setXgrSfzh(user.getIdCard());
                ChasSswpxx chasSswpxx = findById(id);
                MyBeanUtils.copyBeanNotNull2Bean(sswpxx, chasSswpxx);
                update(chasSswpxx);
                Map<String, Object> params = new HashMap<>();
                params.put("wpid", chasSswpxx.getId());
                List<ChasSswpZpxx> zpxxes = zpxxService.findList(params, null);
                if (zpxxes.isEmpty()) {
                    //保存文件照片信息
                    ChasSswpZpxx zpxx = new ChasSswpZpxx();
                    zpxx.setId(StringUtils.getGuid32());
                    zpxx.setLrrSfzh(user.getIdCard());
                    zpxx.setLrsj(new Date());
                    zpxx.setBaqid(chasSswpxx.getBaqid());
                    zpxx.setBaqmc(chasSswpxx.getBaqmc());
                    zpxx.setRybh(chasSswpxx.getRybh());
                    zpxx.setBizId(sswpxx.getWjid());
                    zpxx.setDataFlag("");
                    zpxx.setIsdel(0);
                    zpxx.setWpid(chasSswpxx.getId());
                    zpxx.setZplx(SYSCONSTANT.SSWP_ZP_MX);  //明细照片
                    zpxx.setXgrSfzh(user.getIdCard());
                    zpxx.setXgsj(new Date());
                    zpxxService.save(zpxx);
                }
                result.put("success", true);
                result.put("msg", "修改成功!");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "系统异常");
            log.error("saveOrupdate:", e);
            throw e;
        }
        return result;
    }

    @Override
    public void SaveWithUpdate_List(String json, String delids) throws Exception {
        SessionUser user = WebContext.getSessionUser();
        ApiGgYwbhService jdoneComYwbhService = ServiceContext.getServiceByClass(ApiGgYwbhService.class);

        //删除信息
        if (StringUtil.isNotEmpty(delids)) {
            deleteSswpxxByIds(delids);
        }

        if (StringUtils.isNotEmpty(json)) {
            //解析JSON数据
            List<Map> listmap = JsonUtil.getListFromJsonString(json, Map.class);
            for (Map<String, Object> map : listmap) {
                String json_ = JsonUtil.getJsonString(map);
                //json转物品对象
                ChasSswpxx temp = JsonUtil.getObjectFromJsonString(json_, ChasSswpxx.class);
                //取关键业务数据
                String ryid = (String) map.get("ryid");
                String type = (String) map.get("type");
                ChasBaqryxx baqryxx = baqryxxService.findById(ryid);

                //判断是否存在数据库
                if (StringUtils.isNotEmpty(temp.getId())) {
                    ChasSswpxx sswp = findById(temp.getId());
                    if (sswp != null) {  //修改
                        MyBeanUtils.copyBeanNotNull2Bean(temp, sswp);
                        update(sswp);
                    }
                } else {  //新增
                    if (baqryxx == null) {
                        continue;
                    }
                    if (StringUtils.equals(type, SYSCONSTANT.SSWP_ZP_MX)) {  //总照片不存为随身物品,只保存照片ID
                        ApiGgYwbh comYwbh = null;
                        try {
                            comYwbh = jdoneComYwbhService.getYwbh("W", user.getOrgCode(), null);
                            temp.setId(StringUtils.getGuid32());
                            temp.setLrrSfzh(user.getIdCard());
                            temp.setXgrSfzh(user.getIdCard());
                            temp.setRybh(baqryxx.getRybh());
                            temp.setSfznbaq(1);
                            temp.setBaqid(baqryxx.getBaqid());
                            temp.setBaqmc(baqryxx.getBaqmc());
                            temp.setWpbh(comYwbh.getYwbh());
                            temp.setZbrSfzh(user.getIdCard());
                            temp.setZbrXm(user.getName());
                            temp.setRksj(new Date());
                            if (StringUtils.isNotEmpty(user.getOrgCode())) {
                                temp.setZbdwBh(user.getOrgCode());
                            }
                            temp.setLx(SYSCONSTANT.SSWPLX_SSWP);//随身物品
                            temp.setWpzt(SYSCONSTANT.SSWPZT_ZSWP);//在所物品
                            if (StringUtils.isNotEmpty(user.getOrgSysCode())) {
                                temp.setDwxtbh(user.getOrgSysCode());
                            }

                            //暂时不区分 物品柜ID 和 手机柜ID ，都用物品柜ID作为该人员的所有物品的存放位置。
                            //在开柜记录（代替取物记录操作）中，暂时该人员的物品柜开柜的记录信息
                            ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(), baqryxx.getRybh());
                            if (ryjl != null) {
                                temp.setCfwz(ryjl.getCwgId());
                            }
                            save(temp);

                            if (StringUtils.isNotEmpty(temp.getRybh())) {
                                String baqRysswpzt = getBaqRySswpZtByRybh(temp.getRybh());
                                if (StringUtils.isNotEmpty(baqRysswpzt)) {
                                    baqryxx.setSswpzt(baqRysswpzt);
                                    baqryxxService.update(baqryxx);
                                }
                            }

                            jdoneComYwbhService.ywbhCommit(comYwbh);
                        } catch (Exception e) {
                            jdoneComYwbhService.ywbhRollBack(comYwbh);
                            throw new Exception(e);
                        }
                    }
                }

                String bizId = (String) map.get("bizId");  //物品照片IDS
                if (StringUtil.isNotEmpty(bizId)) {
                    for (String id : bizId.split(",")) {
                        ChasSswpZpxx zp = zpxxService.findZpxxByBizId(id);
                        if (StringUtil.isEmpty(id) || zp != null) {  //如果存在则不在保存
                            continue;
                        }
                        ChasSswpZpxx zpxx = new ChasSswpZpxx();
                        zpxx.setId(StringUtils.getGuid32());
                        zpxx.setLrrSfzh(user.getIdCard());
                        zpxx.setLrsj(new Date());
                        zpxx.setBaqid(baqryxx.getBaqid());
                        zpxx.setBaqmc(baqryxx.getBaqmc());
                        zpxx.setBizId(id);
                        zpxx.setDataFlag("");
                        zpxx.setIsdel(0);
                        zpxx.setRybh(baqryxx.getRybh());
                        zpxx.setWpid(temp.getId());
                        zpxx.setZplx(type);
                        zpxx.setXgrSfzh(user.getIdCard());
                        zpxx.setXgsj(new Date());
                        zpxxService.save(zpxx);
                    }
                }
            }
        }
    }

    public String getBaqRySswpZtByRybh(String rybh) {
        String sswpzt = "";
        if (StringUtils.isNotEmpty(rybh)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("rybh", rybh);
            List<ChasSswpxx> sswpxxList = findList(param, "");
            if (sswpxxList.size() > 0) {
                //获取在所物品数量
                String[] wpzts = new String[]{SYSCONSTANT.SSWPZT_ZSWP, SYSCONSTANT.SSWPZT_ZBCL};
                param.put("wpzts", wpzts);
                List<ChasSswpxx> sswpxxZsList = findList(param, "");

                if (sswpxxZsList.size() == 0) {
                    sswpzt = SYSCONSTANT.BAQRY_SSWPZT_YCL;
                } else {
                    if (sswpxxList.size() == sswpxxZsList.size()) {
                        //未处理
                        sswpzt = SYSCONSTANT.BAQRY_SSWPZT_WCL;
                    } else {
                        //部分处理
                        sswpzt = SYSCONSTANT.BAQRY_SSWPZT_BFCL;
                    }
                }
            } else {
                sswpzt = SYSCONSTANT.BAQRY_SSWPZT_WU;
            }
        }
        return sswpzt;
    }

    @Override
    public DevResult SaveWithUpdate_Form(SswpBean sswpBean) {
        int jg = 0;
        DevResult dev = new DevResult();
        SessionUser user = WebContext.getSessionUser();
        ApiGgYwbh comYwbh = null;
        ApiGgYwbhService jdoneComYwbhService = ServiceContext.getServiceByClass(ApiGgYwbhService.class);


        if (StringUtil.equals(sswpBean.getDataType(), SYSCONSTANT.SSWP_ZP_RS_ZL)) {

            //合照
            if ("undefined".equals(sswpBean.getWjid())) {
                dev.setCode(0);
                dev.setMessage("文件id错误");
            } else {

                ChasBaqryxx baqryxx = baqryxxService.findByRybh(sswpBean.getRybh());
                ChasSswpZpxx zpxx = zpxxService.findHzpzxxByRybh(sswpBean.getRybh());
                boolean isAdd = false;
                if (zpxx == null) {
                    zpxx = new ChasSswpZpxx();
                    zpxx.setId(StringUtils.getGuid32());
                    isAdd = true;
                }
                zpxx.setLrrSfzh(user.getIdCard());
                zpxx.setLrsj(new Date());
                zpxx.setBaqid(baqryxx.getBaqid());
                zpxx.setBaqmc(baqryxx.getBaqmc());
                zpxx.setBizId(sswpBean.getWjid());
                zpxx.setDataFlag("");
                zpxx.setIsdel(0);
                zpxx.setRybh(baqryxx.getRybh());
                zpxx.setWpid("");
                zpxx.setZplx(SYSCONSTANT.SSWP_ZP_RS_ZL);
                zpxx.setXgrSfzh(user.getIdCard());
                zpxx.setXgsj(new Date());
                if (isAdd) {
                    zpxxService.save(zpxx);
                } else {
                    zpxxService.update(zpxx);
                }
                dev.setCode(1);
                dev.setMessage("保存成功");
            }
        }
        if (StringUtil.equals(sswpBean.getDataType(), SYSCONSTANT.SSWP_ZP_MX)) {  //明细
            try {
                if (StringUtils.isNotEmpty(sswpBean.getId())) {  //修改
                    ChasSswpxx sswpxx1 = findById(sswpBean.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(sswpBean, sswpxx1);
                    jg = update(sswpxx1);
                    dev.setCode(1);
                    dev.setMessage("物品修改成功");
                } else {
                    if (oConvertUtils.isNotEmpty(sswpBean.getLbmc())) {//区别岗位二和物品批量新增不走分配物品柜
                        dev = initWpg(sswpBean);
                    } else {
                        if (StringUtils.isNotEmpty(sswpBean.getRybh())) {
                            ChasRyjl ryjl = ryjlService.findRyjlByRybh("", sswpBean.getRybh());
                            if ("03".equals(sswpBean.getLb())) {
                                sswpBean.setCfwz(String.valueOf(ryjl.getSjgId()));
                            } else {
                                sswpBean.setCfwz(String.valueOf(ryjl.getCwgId()));
                            }
                            dev.setCode(1);
                            sswpBean.setCyr(ryjl.getXm());
                        } else {
                            dev.setCode(0);
                        }

                    }
                    if (dev.getCode() == 1) {
                        comYwbh = jdoneComYwbhService.getYwbh("W", user.getOrgCode(), 11);
                        ChasSswpxx sswpxx = new ChasSswpxx();
                        MyBeanUtils.copyBeanNotNull2Bean(sswpBean, sswpxx);
                        sswpxx.setId(StringUtils.getGuid32());
                        sswpxx.setLrrSfzh(user.getIdCard());
                        if (StringUtil.isNotEmpty(sswpBean.getLrsjk())) {
                            sswpxx.setLrsj(DateTimeUtils.parseDateTime(sswpBean.getLrsjk(), "yyyy-MM-dd HH:mm:ss"));
                        } else {
                            sswpxx.setLrsj(new Date());
                        }
                        sswpxx.setXgrSfzh(user.getIdCard());
                        sswpxx.setXgsj(new Date());
                        /*sswpxx.setDwxtbh(user.getOrgSysCode());
                        sswpxx.setZbdwBh(user.getOrgCode());
                        sswpxx.setZbrSfzh(user.getIdCard());
                        sswpxx.setZbrXm(user.getName());*/
                        sswpxx.setWpbh(comYwbh.getYwbh());
                        sswpxx.setSfznbaq(SYSCONSTANT.N_I);
                        if("null".equals(sswpxx.getCfwz())){
                            sswpxx.setCfwz("");
                        }
                        ChasBaqryxx baqryxx = null;
                        if (StringUtils.isNotEmpty(sswpxx.getRybh())) {
                            Map<String, Object> ryParam = new HashMap<String, Object>();
                            ryParam.put("rybh", sswpxx.getRybh());
                            List<ChasBaqryxx> ryxxList = baqryxxService.findList(ryParam, "");
                            baqryxx = ryxxList.get(0);
                            if (ryxxList.size() > 0) {
                                sswpxx.setBaqid(baqryxx.getBaqid());
                                sswpxx.setBaqmc(baqryxx.getBaqmc());
                                sswpxx.setDwxtbh(baqryxx.getDwxtbh());
                                sswpxx.setZbdwBh(baqryxx.getZbdwBh());
                                sswpxx.setZbrSfzh(baqryxx.getMjSfzh());
                                sswpxx.setZbrXm(baqryxx.getMjXm());
                            }
                        }
                        sswpxx.setLx(SYSCONSTANT.SSWPLX_SSWP);//随身物品
                        sswpxx.setWpzt(SYSCONSTANT.SSWPZT_ZSWP);//在所物品
                        sswpxx.setRksj(new Date());

                        jg = save(sswpxx);
                        if (StringUtil.isNotEmpty(sswpxx.getRybh())) {
                            uprywpzt(sswpxx.getRybh());
                        }


                        //保存文件照片信息
                        ChasSswpZpxx zpxx = new ChasSswpZpxx();
                        zpxx.setId(StringUtils.getGuid32());
                        zpxx.setLrrSfzh(user.getIdCard());
                        zpxx.setLrsj(new Date());
                        zpxx.setBaqid(sswpxx.getBaqid());
                        zpxx.setBaqmc(sswpxx.getBaqmc());
                        zpxx.setRybh(sswpxx.getRybh());
                        zpxx.setBizId(sswpxx.getWjid());
                        zpxx.setDataFlag("");
                        zpxx.setIsdel(0);
                        zpxx.setWpid(sswpxx.getId());
                        zpxx.setZplx(SYSCONSTANT.SSWP_ZP_MX);  //明细照片
                        zpxx.setXgrSfzh(user.getIdCard());
                        zpxx.setXgsj(new Date());
                        zpxxService.save(zpxx);

                        String baqRysswpzt = getBaqRySswpZtByRybh(sswpxx.getRybh());
                        if (StringUtils.isNotEmpty(baqRysswpzt)) {
                            if (baqryxx != null) {
                                baqryxx.setSswpzt(baqRysswpzt);
                                baqryxxService.update(baqryxx);
                            }
                        }
                        jdoneComYwbhService.ywbhCommit(comYwbh);
                    }
                    dev.setCode(1);
                    dev.setMessage("物品保存成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    jdoneComYwbhService.ywbhRollBack(comYwbh);
                    dev.setCode(0);
                    dev.setMessage("物品保存失败");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        return dev;
    }

    private DevResult initWpg(SswpBean sswpBean) {
        DevResult dev = new DevResult();
        dev.setCode(1);
        String wplb = sswpBean.getLbmc();
        ChasRyjl ryjl = ryjlService.findUsedLockerById(sswpBean.getCfwz());
        if (ryjl != null) {
            //排除自己
            boolean tips = false;
            //物品柜
            if (StringUtil.equals(wplb, "qt")) {
                if (StringUtil.isNotEmpty(ryjl.getCwgId()) && !StringUtil.equals(ryjl.getCwgId(), sswpBean.getCfwz())) {
                    tips = true;
                }
            }
            //手机柜
            if (StringUtil.equals(wplb, "sj")) {
                if (StringUtil.isNotEmpty(ryjl.getSjgId()) && !StringUtil.equals(ryjl.getSjgId(), sswpBean.getCfwz())) {
                    tips = true;
                }
            }
            if (tips) {
                dev.setMessage("所选存放柜已被占用,请重新选择!");
                dev.setCode(0);
            }
        } else {
            ryjl = ryjlService.findRyjlByRybh("", sswpBean.getRybh());
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(sswpBean.getRybh());
            //初次分配
            if (StringUtil.equals(wplb, "qt")) {
                if (StringUtil.equals(ryjl.getCwgId(), "0") || StringUtil.isEmpty(ryjl.getCwgId())) {
                    ChasSswpxg sswpxg = sswpxgService.findById(sswpBean.getCfwz());
                    if (sswpxg != null) {
                        ryjl.setXgrSfzh(baqryxx.getMjSfzh());
                        ryjl.setCwgId(sswpxg.getSbId());
                        ryjl.setCwgBh(sswpxg.getBh());
                        ryjl.setCwgLx("");
                        DevResult devResult = iWpgService.cwgBd(ryjl.getBaqid(), ryjl.getRybh(), sswpxg.getBh());
                        if (devResult.getCode() != 1) {
                            dev.setMessage("绑定储物柜失败:" + devResult.getMessage());
                            dev.setCode(0);
                        }
                        ryjlService.update(ryjl);
                    }
                }
            }
            if (StringUtil.equals(wplb, "sj")) {
                if (StringUtil.equals(ryjl.getSjgId(), "0") || StringUtil.isEmpty(ryjl.getSjgId())) {
                    //初次分配
                    ChasSswpxg sswpxg = sswpxgService.findById(sswpBean.getCfwz());
                    if (sswpxg != null) {
                        ryjl.setXgrSfzh(baqryxx.getMjSfzh());
                        ryjl.setSjgId(sswpxg.getSbId());
                        ryjl.setSjgBh(sswpxg.getBh());
                        DevResult devResult = iWpgService.sjgBd(ryjl.getBaqid(), ryjl.getRybh(), sswpxg.getBh());
                        if (devResult.getCode() != 1) {
                            dev.setCode(0);
                            dev.setMessage("绑定手机柜失败:" + devResult.getMessage());
                        }
                        ryjlService.update(ryjl);
                    }
                }
            }
        }

        return dev;
    }

    /**
     * @param ids
     * @param wpclzt
     * @return java.util.Map<java.lang.String, java.lang.Object>  //返回值
     * @throws //异常
     * @description 物品领回//描述
     * @author lcm //作者
     * @date 2020/9/9 15:30 //时间
     */
    @Override
    public Map<String, Object> takeBack(String ids, String wpclzt) {

        Map<String, Object> result = new HashMap<>();
        try {
            String[] idstr = ids.split(",");
            for (String id : idstr) {
                ChasSswpxx sswpxx = findById(id);
                sswpxx.setWpzt(SYSCONSTANT.SSWPZT_CSWP);
                sswpxx.setWpclzt(wpclzt);
                update(sswpxx);
                String useRybh = sswpxx.getRybh();
                if (StringUtils.isNotEmpty(useRybh)) {
                    Map<String, Object> ryParam = new HashMap<String, Object>();
                    ryParam.put("rybh", useRybh);
                    List<ChasBaqryxx> ryxxList = baqryxxService.findList(ryParam, "");
                    if (ryxxList.size() > 0) {
                        ChasBaqryxx ryxxT = ryxxList.get(0);
                        String baqRysswpzt = getBaqRySswpZtByRybh(useRybh);
                        if (StringUtils.isNotEmpty(baqRysswpzt)) {
                            ryxxT.setSswpzt(baqRysswpzt);
                            baqryxxService.update(ryxxT);
                        }
                    }
                }
            }
            result.put("success", true);
            result.put("msg", "领回成功！");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            log.error("jslh:", e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> findWpxxByRybh(String rybh) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rybh", rybh);
        if (StringUtils.isNotEmpty(rybh)) {
            List<ChasSswpxx> sswpxxlist = findList(params, "lrsj asc");
            result.put("data", DicUtil.translate(sswpxxlist, new String[]{"WPZT", "WPLB", "ZD_CFWZ", "WPZT"},
                    new String[]{"wpzt", "lb", "cfwz", "wpclzt"}));
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "人员编号为空！");

        }

        return result;
    }


    @Override
    public void csqwByRybh(String baqid, String rybh) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("baqid", baqid);
        params.put("rybh", rybh);
        //在所物品
        params.put("wpzt", SYSCONSTANT.SSWPZT_ZSWP);
        if (StringUtils.isNotEmpty(rybh)) {
            List<ChasSswpxx> sswpxxlist = findList(params, "lrsj desc");
            //在所物品 部分处理
            params.put("wpzt", SYSCONSTANT.BAQRY_SSWPZT_BFCL);
            sswpxxlist.addAll(findList(params, "lrsj desc"));
            log.info("csqwByRybh--在所物品总数是否为空:" + sswpxxlist.isEmpty());
            for (ChasSswpxx sswp : sswpxxlist) {
                log.info("人员编号:" + sswp.getRybh() + ",物品名称:" + sswp.getMc());
                sswp.setWpzt(SYSCONSTANT.SSWPZT_BRLH);
                update(sswp);
            }
        }
    }


    @Override
    public Map<String, Object> findWpxxById(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(id)) {
            ChasSswpxx sswpxx = findById(id);
            result.put("data", sswpxx);
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "Id为空！");

        }

        return result;
    }

    @Override
    public Map<String, Object> deleteSswpxxByIds(String ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotEmpty(ids)) {
                String[] idstr = ids.split(",");
                for (String sswpid : idstr) {
                    ChasSswpxx ss = findById(sswpid);
                    if (deleteById(sswpid) == 1) {
                        if (ss != null) {
                            String rybh = ss.getRybh();
                            if (StringUtils.isNotEmpty(rybh)) {
                                ChasBaqryxx ryxxT = null;
                                Map<String, Object> ryParam = new HashMap<String, Object>();
                                ryParam.put("rybh", rybh);
                                List<ChasBaqryxx> ryxxList = baqryxxService.findList(ryParam, "");
                                if (ryxxList.size() > 0) {
                                    ryxxT = ryxxList.get(0);
                                }
                                if (ryxxT != null) {
                                    String baqRysswpzt = getBaqRySswpZtByRybh(rybh);
                                    if (StringUtils.isNotEmpty(baqRysswpzt)) {
                                        ryxxT.setSswpzt(baqRysswpzt);
                                        baqryxxService.update(ryxxT);
                                    }
                                }
                            }
                        }

                        //同步删除照片信息
                        zpxxService.deleteByWpid(ss.getId());
                    }
                }
                result.put("success", true);
                result.put("msg", "删除成功！");
            } else {
                result.put("success", false);
                result.put("msg", "ids为空！");
            }
        } catch (Exception e) {
            result.put("success", false);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> linkedPeople(String wpid, String ryid) throws Exception {
        Map<String, Object> result = new HashMap<>();

        ChasSswpxx sswpxx = findById(wpid);
        if (StringUtils.isEmpty(sswpxx.getRybh())) {
            ChasBaqryxx ryxx = baqryxxService.findById(ryid);
            sswpxx.setRybh(ryxx.getRybh());
            sswpxx.setCyr(ryxx.getRyxm());
            sswpxx.setBaqid(ryxx.getBaqid());
            sswpxx.setBaqmc(ryxx.getBaqmc());
            update(sswpxx);
            uprywpzt(ryxx.getRybh());

            //同步照片信息
            List<ChasSswpZpxx> zpxxes = zpxxService.findByWpid(wpid);
            for (ChasSswpZpxx zpxx : zpxxes) {
                zpxx.setRybh(ryxx.getRybh());
                zpxx.setBaqid(ryxx.getBaqid());
                zpxx.setBaqmc(ryxx.getBaqmc());
                zpxxService.update(zpxx);
            }

            result.put("success", true);
            result.put("msg", "关联成功！");
        } else {
            result.put("success", false);
            result.put("msg", "已关联人员！");
        }
        return result;
    }

    @Override
    public Map<String, Object> unlinkedPeople(String wpid) {
        Map<String, Object> result = new HashMap<>();

        if (StringUtils.isNotEmpty(wpid)) {
            ChasSswpxx sswpxx = findById(wpid);
            String rybh = sswpxx.getRybh();
            sswpxx.setRybh(null);
            sswpxx.setCyr(null);
            sswpxx.setBaqid(null);
            sswpxx.setBaqmc(null);
            update(sswpxx);
            if (StringUtils.isNotEmpty(rybh)) {
                uprywpzt(rybh);
                result.put("success", true);
                result.put("msg", "取消关联成功！");
            } else {
                result.put("success", false);
                result.put("msg", "未关联人员！");
            }

            //同步照片信息
            List<ChasSswpZpxx> zpxxes = zpxxService.findByWpid(wpid);
            for (ChasSswpZpxx zpxx : zpxxes) {
                zpxx.setRybh(null);
                zpxx.setBaqid(null);
                zpxx.setBaqmc(null);
                zpxxService.update(zpxx);
            }
        } else {
            result.put("success", false);
            result.put("msg", "物品编号为空！");
        }
        return result;
    }

    @Override
    public Map<String, Object> saveWtyj(ChasSswpxx sswpxx, CommonsMultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            ChasSswpxx sswp = findById(sswpxx.getId());
            sswp.setWpclzt("03");
            sswp.setYjdh(sswpxx.getYjdh());
            sswp.setYjzpid(StringUtils.getGuid32());

            if (file != null) {
                String exten = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                UploadParamObj uploadParam = new UploadParamObj();
                uploadParam.setOrgSysCode(sswp.getZbdwBh());
                uploadParam.setBizId(sswp.getYjzpid());
                uploadParam.setBizType("chaswtyj");
                FileInfoObj o = FrwsApiForThirdPart.uploadByteFile(
                        new ByteFileObj(sswp.getYjzpid()
                                + exten, file.getBytes()), uploadParam);
                if (o == null) {
                    sswp.setYjzpid(null);
                    result.put("success", false);
                    result.put("msg", "保存邮寄照片异常");
                    return result;
                }
            }
            update(sswp);
            result.put("success", true);
            result.put("msg", "保存邮寄照片成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "保存邮寄照片出错");
        }
        return result;
    }

    private void uprywpzt(String rybh) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rybh", rybh);
        map.put("wpztRadio", SYSCONSTANT.SSWPZT_ZSWP);
        List<ChasSswpxx> zswp = findList(map, null);
        map.put("wpztRadio", SYSCONSTANT.SSWPZT_BRLH);
        List<ChasSswpxx> brlh = findList(map, null);
        map.put("wpztRadio", SYSCONSTANT.SSWPZT_JSLH);
        brlh.addAll(findList(map, null));
        List<ChasBaqryxx> ryxxs = baqryxxService.findList(map, null);
        ChasBaqryxx ryxx = null;
        if (!ryxxs.isEmpty()) {
            ryxx = ryxxs.get(0);
        }
        if (ryxx != null) {
            // 部分
            if (zswp.size() > 0 && brlh.size() > 0) {
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_BFCL);
            }
            // 无
            if (zswp.size() == 0 && brlh.size() == 0) {
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WU);
            }
            // 未
            if (zswp.size() > 0 && brlh.size() == 0) {
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_WCL);
            }
            // 已
            if (zswp.size() == 0 && brlh.size() > 0) {
                ryxx.setSswpzt(SYSCONSTANT.BAQRY_SSWPZT_YCL);
            }
            baqryxxService.update(ryxx);
        }
    }

    @Override
    public Map<String, Object> selectCyrAll(int pageNo, int pageSize, Map<String, Object> params, String orderBy) {
        Map<String, Object> result = new HashMap<>();
        List<String> rybhs = new ArrayList<String>();
        params.put("ryztMulti", "01,02,03,05".split(","));
        SessionUser sessionUser = WebContext.getSessionUser();
        if (!sessionUser.getRoleCode().equals("400008") && !sessionUser.getIsSuperAdmin()) {
            params.put("mjSfzh", sessionUser.getIdCard());
        }
        DataQxbsUtil.getSelectAll(baqryxxService, params);
        PageDataResultSet<ChasBaqryxxBq> Baqryxx = baqryxxService.getEntityPageDataBecauseQymc(-1, -1, params, " r_rssj desc");
        // params.put("baqList", baqids);
        if (Baqryxx.getData().size() > 0) {
            params.put("ryzt", "1");
            for (ChasBaqryxxBq chasBaqryxx : Baqryxx.getData()) {
                rybhs.add(chasBaqryxx.getRybh());
            }
            params.put("rybhs", rybhs);
            PageDataResultSet<ChasRyjl> qt = ryjlService.getEntityPageData(pageNo, pageSize, params, " xgsj desc");

            result.put("rows", DicUtil.translate(qt.getData(), new String[]{
                            "ZD_ZB_XB", "zd_case_tsqt", "RSYY", "RYZT",
                            "ZD_SYS_ORG_CODE_SNAME", "CSYY", "BAQRYSSWPZT", "ZFBA_GG_SHZT"},
                    new String[]{"xb", "tsqt", "ryZaybh", "ryzt", "zbdwBh", "cCsyy", "sswpzt", "shzt"}));
            result.put("total", qt.getTotal());
        }
        return result;
    }

    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<String, Object>();
        SessionUser user = WebContext.getSessionUser();

        //单选框操作
        String me = String.valueOf(param.get("me"));
//        String lb = String.valueOf(param.get("lb").toString());
//        params.put("lb", lb);
        if ("2".equals(me)) {
            param.put("zbdwBh", user.getCurrentOrgSysCode());// 本单位的
        } else if ("1".equals(me)) {
            param.put("zbrSfzh", user.getIdCard());// 我处理的
        }

        MybatisPageDataResultSet<ChasSswpxx> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);
        for (ChasSswpxx chasSswpxx : list) {
            if (StringUtil.isEmpty(chasSswpxx.getWpclzt())){
                chasSswpxx.setWpclzt("在区物品");
            }
            if (StringUtil.isEmpty(chasSswpxx.getCfwz())||"null".equals(chasSswpxx.getCfwz())){
                chasSswpxx.setCfwz("");
            }
        }
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{"WPZT", "WPLB", "ZD_SYS_SF", "ZD_JDONE_ORG_SYS_CODE", "ZD_CFWZ", "WPZT"},
                new String[]{"wpzt", "lb", "sfznbaq", "dwxtbh", "cfwz", "wpclzt"}));

        return result;
    }

    public Map<String, Object> getImageByWpid(String id) {
        Map<String, Object> result = new HashMap<>();
        //查询该物品信息所有照片信息
        Map<String, Object> params = new HashMap<>();
        params.put("wpid", id);
        List<ChasSswpZpxx> zpxxes = zpxxService.findList(params, null);

        List<Map<String, Object>> listmap = new ArrayList<>();
        for (ChasSswpZpxx zpxx : zpxxes) {
            FileInfoObj infoObj = FrwsApiForThirdPart.getFileInfoByBizId(zpxx.getBizId());
            Map<String, Object> map = new HashMap<>();
            if (infoObj != null) {
                map.put("zpxxId", zpxx.getId());
                map.put("id", infoObj.getId());
                map.put("downUrl", infoObj.getDownUrl());
                listmap.add(map);
            }
        }
        result.put("imgs", listmap);  //物品照片集合
        result.put("imgsize", listmap.size());

        return result;
    }

    @Override
    public Map<String, Object> findZpxxByRybh(String rybh, String zplx) {
        Map<String, Object> result = new HashMap<>();
        //查询该物品信息所有照片信息
        Map<String, Object> params = new HashMap<>();
        params.put("rybh", rybh);
        params.put("zplx", zplx);

        List<ChasSswpZpxx> zpxxes = zpxxService.findList(params, null);
        int imgsize = 0;
        if (zpxxes.size() >= 1) {
            List<Map<String, Object>> listmap = new ArrayList<>();
            for (int i = 0; i < zpxxes.size(); i++) {
                ChasSswpZpxx zpxx = zpxxes.get(i);
                if (StringUtils.isNotEmpty(zpxx.getBizId())) {
                    List<FileInfoObj> fileInfoObjs = FrwsApiForThirdPart.getFileInfoList(zpxx.getBizId());
                    for (FileInfoObj fileInfoObj : fileInfoObjs) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("wjid", zpxx.getBizId());
                        map.put("zpxxId", zpxx.getId());
                        map.put("id", fileInfoObj.getId());
                        map.put("bizType", fileInfoObj.getBizType());
                        map.put("downUrl", fileInfoObj.getDownUrl());
                        imgsize++;
                        listmap.add(map);
                    }
                }
            }
            result.put("imgs", listmap);  //物品照片集合
            result.put("imgsize", listmap.size());
        } else {
            result.put("imgs", new ArrayList<>());  //物品照片集合
            result.put("imgsize", imgsize);
        }

        return result;
    }
}
