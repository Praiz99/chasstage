package com.wckj.chasstage.modules.pcry.service.impl;


import com.wckj.api.def.zfba.model.ApiGgYwbh;
import com.wckj.api.def.zfba.service.gg.ApiGgYwbhService;
import com.wckj.chasstage.api.def.pcry.model.PcryBean;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.pcry.dao.ChasYwPcryMapper;
import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.chasstage.modules.pcry.service.ChasYwPcryService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ChasYwPcryServiceImpl extends BaseService<ChasYwPcryMapper, ChasYwPcry> implements ChasYwPcryService {
    protected Logger log = LoggerFactory.getLogger(ChasYwPcryServiceImpl.class);
    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;

    @Override
    @Transactional(readOnly = false)
    public int delete(String[] idstr) {
        int num = 0;
        for (String id : idstr) {
            num = baseDao.deleteByPrimaryKey(id);
        }
        return num;
    }

    @Override
    public Map<String, Object> selectAll(Integer pageNo, Integer pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> params = new HashMap<String, Object>();
        //查询时 使用
        String rvalue = String.valueOf(param.get("rvalue"));  //筛选条件
        if (oConvertUtils.isNotEmpty(rvalue)) {
            if (StringUtils.equals(rvalue, "currentMonth")) {
                //默认查询一个月的
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 0);
                c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
                String first = sdf.format(c.getTime()) + " 00:00:00";
                //获取当前月最后一天
                Calendar ca = Calendar.getInstance();
                ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                String last = sdf.format(ca.getTime()) + " 23:59:59";
                param.put("lrsj1", first);
                param.put("lrsj2", last);
            }
            if (StringUtils.equals(rvalue, "all")) {  //查询全部

            }
        } else {
            if (oConvertUtils.isNotEmpty(param.get("kscjsj"))) {
                param.put("cjsj1", param.get("kscjsj"));
            }
            if (oConvertUtils.isNotEmpty(param.get("jscjsj"))) {
                param.put("cjsj2",  param.get("jscjsj"));
            }
            boolean empty = false;
//            for (String key : param.keySet()) {
//                if (param.get(key) != null && StringUtil.isNotEmpty((String) param.get(key))) {
//                    empty = false;
//                }
//            }
            if (empty) {
                //如果没有传任何参数
                //默认查询一个月的
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 0);
                c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
                String first = sdf.format(c.getTime()) + " 00:00:00";
                //获取当前月最后一天
                Calendar ca = Calendar.getInstance();
                ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                String last = sdf.format(ca.getTime()) + " 23:59:59";
                param.put("lrsj1", first);
                param.put("lrsj2", last);
            }
        }
        MybatisPageDataResultSet<ChasYwPcry> list = baseDao.selectAll(pageNo, pageSize, param, "lrsj desc");
        List<ChasYwPcry> data = list.getData();
        for (int i = 0; i < data.size(); i++) {
            ChasYwPcry pcry1 = data.get(i);
            if (pcry1.getYthcjzt() == 0) {
                pcry1.setSfythcjmc("未采集");
            } else {
                pcry1.setSfythcjmc("已采集");
            }
        }
        result.put("total", list.getTotal());
        result.put("rows", DicUtil.translate(list.getData(), new String[]{"CHAS_ZD_ZB_XB"}, new String[]{"xb"}));
        return result;
    }

    @Override
    public ChasYwPcry findByRybh(String rybh) {
        return baseDao.findByRybh(rybh);
    }

    @Override
    public Map<String, Object> getTaryxx(String rybh, String ryxm) {
        Map<String, Object> result = new HashMap<>();
        try {


            Map<String, Object> param = new HashMap<>(16);
            param.put("zbdwBh", WebContext.getSessionUser().getCurrentOrgSysCode());
            param.put("ryzt", SYSCONSTANT.BAQRYZT_ZS);
            param.put("ryxm", ryxm);

            String startTime = "";
            Date date = new Date();
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            startTime = sim.format(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.DAY_OF_YEAR, -6);//减6天
            date = rightNow.getTime();
            param.put("rssj1", sim.format(date) + " 00:00:00");
            param.put("rssj2", startTime + " 23:59:59");
            List<Map<String, Object>> baqryxxes = baqryxxService.getDataByYwbh(param);

            for (Map<String, Object> ryxx : baqryxxes) {
                if (StringUtils.isNotEmpty((String) ryxx.get("zpid"))) {
                    FileInfoObj fileInfoByBizId = FrwsApiForThirdPart.getFileInfoByBizId((String) ryxx.get("zpid"));
                    if (fileInfoByBizId != null) {
                        ryxx.put("zpid", fileInfoByBizId.getDownUrl());
                    }
                }
            }
            //排除自己
            if (StringUtils.isNotEmpty(rybh)) {
                Iterator<Map<String, Object>> iterator = baqryxxes.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> baqry = iterator.next();
                    if (StringUtils.equals(rybh, (String) baqry.get("rybh"))) {
                        iterator.remove();
                    }
                }
            }
            result.put("success", true);
            result.put("data", baqryxxes);
        } catch (Exception e) {
            result.put("success", false);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> ryrsxx(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        int sfznbaq = 99;
        ChasBaqryxxService chasBaqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        SessionUser sessionUser = WebContext.getSessionUser();
        try {

            if (StringUtils.isNotEmpty(id)) {//非空 携带数据库信息
                String zrbaq = "-1";  //责任办案区
                String baqid = "";  //办案区ID
                String ywbhStr = "";  //办案区人员业务编号
                boolean enableRssj = true;
                final long RSSJ_MODIFY_ALLOWED_TIME = 2 * 3600 * 1000;
                ChasBaqryxx baqryxx = new ChasBaqryxx();
                ChasYwPcry chasPcry = findById(id);
                Map<String, Object> param = new HashMap<String, Object>();
                String rybh = chasPcry.getRybh();
                param.put("rybh", rybh);
                List<ChasBaqryxx> ryxxList = chasBaqryxxService.findList(param, "xgsj desc");
                if (ryxxList.size() > 0) {  //修改
                    baqryxx = ryxxList.get(0);
                    JdoneSysUser sysuser = new JdoneSysUser();
                    JdoneSysUserService userService = ServiceContext.getServiceByClass(JdoneSysUserService.class);
                    sysuser.setIdCard(baqryxx.getMjSfzh());
                    List<JdoneSysUser> users = userService.findList(sysuser, null);
                    data.put("mjxm", users.get(0).getName());
                    data.put("zbdw", users.get(0).getOrgCode());
                    // 判断是智能办案区
//                sfznbaq = baqryxx.getSfznbaq();
                    ChasRyjlService ryjlService = ServiceContext.getServiceByClass(ChasRyjlService.class);
                    ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(), baqryxx.getRybh());
                    data.put("chasRyjl", JSONHelper.toJSONString(ryjl));
                    // 判断人员入所是不是超过两个小时
                    Date rssj = baqryxx.getRRssj();
                    if (rssj != null) {
                        if ((System.currentTimeMillis() - rssj.getTime() >= RSSJ_MODIFY_ALLOWED_TIME)) {
                            enableRssj = false;
                        }
                    }
                    ChasXtQyService qyService = ServiceContext.getServiceByClass(ChasXtQyService.class);
                    // 分配等候室
                    List<ChasXtQy> qys = qyService.getDhsByRybh(baqryxx.getRybh());
                    if (qys != null && qys.size() > 0) {
                        data.put("waitingRoomName", qys.get(0).getQymc());
                    }
                    if (StringUtils.isEmpty(baqryxx.getZpid())) {

                        baqryxx.setZpid(StringUtils.getGuid32());
                    }

                    if (StringUtils.isEmpty(baqryxx.getSsclid())) {
                        baqryxx.setSsclid(StringUtils.getGuid32());
                    }
                    if (StringUtils.isEmpty(baqryxx.getCkzkzpid())) {
                        baqryxx.setCkzkzpid(StringUtils.getGuid32());
                    }

                    //获取同案人员信息
                    String taryxm = "";
                    if (StringUtil.isNotEmpty(ryjl.getAjbh())) {
                        List<ChasBaqryxx> baqryxxes = chasBaqryxxService.findTaryByRyjlYwbh(ryjl.getAjbh());
                        for (ChasBaqryxx ryxx : baqryxxes) {
                            if (!StringUtils.equals(ryxx.getRybh(), baqryxx.getRybh())) {
                                taryxm += ryxx.getRyxm() + "、";
                            }
                        }
                        if (StringUtils.isNotEmpty(taryxm)) {
                            taryxm = taryxm.substring(0, taryxm.length() - 1);
                        }
                    }
                    data.put("tary", taryxm);

                    //获取入所物品总照片
                    param.clear();
                    param.put("rybh", baqryxx.getRybh());
                    param.put("zplx", SYSCONSTANT.SSWP_ZP_RS_ZL);
//                List<ChasSswpZpxx> zpxxes = zpxxService.findList(param, null);
                    String sswp_rsId = "";  //如果没有配置物品拍照，则使用控件拍照，将采用此ID。
                    if (1 == 1) {
//                if (!zpxxes.isEmpty()) {
                        //入所物品总照片只有一张
//                    ChasSswpZpxx zpxx = zpxxes.get(0);
//                    if (StringUtil.isNotEmpty(zpxx.getBizId())) {
//                        List<FileInfoObj> fileInfoList = FrwsApiForThirdPart.getFileInfoList(zpxx.getBizId());
//                      data.put("imgs", fileInfoList);
//                    }
//					data.put("sswp_rsId", zpxx.getBizId());
                    }
                } else {  //在盘查人员处点击新增
                    baqryxx.setBaqid(chasPcry.getBaqid());
                    baqryxx.setBaqmc(chasPcry.getBaqmc());
                    baqryxx.setRybh(chasPcry.getRybh());
                    baqryxx.setRyxm(chasPcry.getRyxm());
                    baqryxx.setZjlx(chasPcry.getZjlx());
                    baqryxx.setRysfzh(chasPcry.getRysfzh());
                    baqryxx.setXb(chasPcry.getXb());
                    baqryxx.setCsrq(chasPcry.getCsrq());
                    if (chasPcry.getCsrq() != null) {
                        String csrqStr = DateTimeUtils.getDateStr(chasPcry.getCsrq(), 13);
                        int age = DateTimeUtils.getAge(chasPcry.getCsrq());
                        baqryxx.setNl(age);
                        data.put("csrq", csrqStr);
                    }
                    baqryxx.setBm(chasPcry.getBm());
                    baqryxx.setCym(chasPcry.getCym());
                    baqryxx.setCh(chasPcry.getCh());
                    baqryxx.setGj(chasPcry.getGj());
                    baqryxx.setWhcd(chasPcry.getWhcd());
                    baqryxx.setMz(chasPcry.getMz());
                    baqryxx.setGzdw(chasPcry.getGzdw());
                    baqryxx.setHjdxzqh(chasPcry.getHjdxzqh());
                    baqryxx.setHjdxz(chasPcry.getHjdxzqh());
                    baqryxx.setXzdxz(chasPcry.getXzdxz());
                    baqryxx.setZbdwBh(chasPcry.getZbdwBh());
                    baqryxx.setCCssj(chasPcry.getCjsj());
                    baqryxx.setLxdh(chasPcry.getLxdh());
                    baqryxx.setSfzbxm(chasPcry.getSfzbm());
                    baqryxx.setSfsfbm(chasPcry.getSfsfbm());
                    baqryxx.setSfythcj(chasPcry.getYthcjzt());
                    baqryxx.setZpid(chasPcry.getZpid());
                    baqryxx.setSsclid(StringUtils.getGuid32());
                    data.put("zpid", chasPcry.getZpid());
                    data.put("ssclid", baqryxx.getSsclid());
                    if (StringUtils.isEmpty(chasPcry.getZpid())) {
                        data.put("zpid", StringUtils.getGuid32());
                    }
                    if (StringUtils.isEmpty(baqryxx.getSsclid())) {
                        data.put("ssclid", StringUtils.getGuid32());
                    }
                    data.put("ckzpid", StringUtils.getGuid32());
//				result.put("sessionUser", sessionUser);
                    data.put("fromSign", SYSCONSTANT.FROMSIGNBAQRYXX);
                }

                if (sfznbaq != 99) {
                    data.put("sfznbaq", sfznbaq);
                }
                param.clear();
//            param.put("sydwdm", sessionUser.getOrgCode());
                boolean dw = false, wpg = false, sjg = false;
                List<Map<String, Object>> configurations = new ArrayList<>();
                List<Map<String, Object>> baqDicList = new ArrayList<Map<String, Object>>();
                ChasBaqrefService baqrefService = ServiceContext.getServiceByClass(ChasBaqrefService.class);
                ChasBaqService baqService = ServiceContext.getServiceByClass(ChasBaqService.class);
                ChasXtBaqznpzService baqznpzService = ServiceContext.getServiceByClass(ChasXtBaqznpzService.class);


                List<ChasBaqref> baqList = baqrefService.findList(param, "");
                List<String> baqids = new ArrayList<>();
                for (int i = 0, len = baqList.size(); i < len; i++) {
                    ChasBaqref chasBaqref = baqList.get(i);
                    Map<String, Object> datamap = new HashMap<String, Object>();
                    baqids.add(chasBaqref.getBaqid());
                    ChasBaq baqTemp = baqService.findById(chasBaqref.getBaqid());
                    if (baqTemp != null) {
                        datamap.put("id", baqTemp.getId());
                        datamap.put("baqmc", baqTemp.getBaqmc());
                        datamap.put("sfznbaq", baqTemp.getSfznbaq());
                        if (StringUtils.equals(sessionUser.getOrgCode(), baqTemp.getDwdm())) {
                            zrbaq = baqTemp.getId();
                        }
                        if (i == 0) {
                            if (StringUtils.isEmpty(baqryxx.getId())) {
                                data.put("sfznbaq", baqTemp.getSfznbaq());
                            }
                            data.put("currentBaqid", baqTemp.getId());
                            data.put("currentBaqmc", baqTemp.getBaqmc());
                        }
                    }
                    Map<String, Object> baqConfigMap = new HashMap<>();
                    BaqConfiguration configuration = baqznpzService.findByBaqid(baqTemp.getId());
                    baqConfigMap.put(baqTemp.getId(), configuration);
                    configurations.add(baqConfigMap);
                    baqDicList.add(datamap);

                    if (!dw) {
                        dw = configuration.getDw();
                    }
                    if (!wpg) {
                        wpg = configuration.getSswp();
                    }
                    if (!sjg) {
                        sjg = configuration.getPhonecab();
                    }
                }
                if (StringUtils.isNotEmpty(baqryxx.getId())) {  //如果是修改那么就不干预默认办案区的值。
                    zrbaq = "";
                }
                data.put("chasBaqryxx", JSONHelper.toJSONString(baqryxx));
                data.put("sjg", sjg);
                data.put("dw", dw);
                data.put("wpg", wpg);
                data.put("baqDicList", JSONArray.fromObject(baqDicList));
                data.put("zrbaq", zrbaq);
                data.put("ywbhStr", ywbhStr);
                data.put("chasBaqryxx", baqryxx);
                data.put("flag", "pcry");
                data.put("enableRssj", enableRssj);
                data.put("znpzs", JsonUtil.toJson(configurations));
                result.put("data", data);
                result.put("success", true);
                result.put("msg", "获取数据正常");
            } else {

            }
        } catch (Exception e) {
            result.put("msg", "系统异常," + e.getMessage());
            result.put("success", false);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> saveOrupdate(PcryBean entity) {
        //页面添加字典
        Map<String, Object> result = new HashMap<>();
        SessionUser user = WebContext.getSessionUser();
        ApiGgYwbhService apiYwbhService = ServiceContext.getServiceByClass(ApiGgYwbhService.class);
        ApiGgYwbh ywbhObject = null;
        try {
            if (StringUtils.isEmpty(entity.getId())) { // 新增
                ywbhObject = apiYwbhService.getYwbh("R", user.getOrgCode(), 6);
                entity.setYthcjzt(SYSCONSTANT.N_I);
                entity.setLrrSfzh(user.getIdCard());
                entity.setRybh(ywbhObject.getYwbh());
                entity.setId(StringUtils.getGuid32());
                if (StringUtils.isNotEmpty(user.getOrgCode())) {
                    entity.setZbdwBh(user.getOrgCode());
                    entity.setZbdwMc(user.getOrgName());
                }
                if (StringUtils.isNotEmpty(user.getOrgSysCode())) {
                    entity.setDwxtbh(user.getOrgSysCode());
                }
                if (StringUtils.isEmpty(entity.getDwxtbh())) {
                    result.put("success", false);
                    result.put("msg", "保存失败:单位系统编号不存在!");
//					return WebResult.error("保存失败:单位系统编号不存在!");
                }
                //entity.setDwxtbh(DicUtil.translate("JDONE_SYS_SYSCODE", entity.getZbdwBh()));
                //entity.setZbdwMc((DicUtil.translate("ZD_JDONE_ORG_SYS_CODE", entity.getZbdwBh())));
                ChasYwPcry pcry = new ChasYwPcry();
                MyBeanUtils.copyBeanNotNull2Bean(entity, pcry);
                save(pcry);
                apiYwbhService.ywbhCommit(ywbhObject);
                result.put("success", true);
                result.put("msg", "保存成功!");

            } else {// 修改
                entity.setXgrSfzh(user.getIdCard());
                ChasYwPcry chasPcry = findById(entity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(entity, chasPcry);
                chasPcry.setSfzbm(entity.getSfzbm());
                chasPcry.setSfsfbm(entity.getSfsfbm());
                update(chasPcry);
                result.put("success", true);
                result.put("msg", "修改成功!");
            }
        } catch (Exception e) {
            log.error("saveOrupdate:", e);
            if (ywbhObject != null) {
                try {
                    apiYwbhService.ywbhRollBack(ywbhObject);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            result.put("success", false);
            result.put("msg", "系统异常！" + e.getMessage());
        }
        return result;
    }


}
