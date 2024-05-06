package com.wckj.chasstage.api.server.imp.mjgl;

import com.alibaba.fastjson.JSON;
import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.fkgl.service.ApiFkglService;
import com.wckj.chasstage.api.def.mjgl.model.MjglBean;
import com.wckj.chasstage.api.def.mjgl.model.MjglParam;
import com.wckj.chasstage.api.def.mjgl.service.ApiMjglService;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.pojo.HikFaceLocationRyxx;
import com.wckj.chasstage.modules.httpApi.client.hkrldw.service.HikFaceLocationService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 民警入区管理
 */
@Service
public class ApiMjglServiceImpl implements ApiMjglService {
    private static final Logger log = LoggerFactory.getLogger(ApiMjglServiceImpl.class);
    @Autowired
    private ChasYwMjrqService apiService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ChasYwYyService yyService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYwMjrq xgpz = apiService.findById(id);
        if (xgpz != null) {
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到民警入区信息");
    }

    @Override
    public ApiReturnResult<?> save(MjglBean bean) {
        try {
            if (StringUtils.isEmpty(bean.getBaqid())) {
                return ResultUtil.ReturnError("民警办案区id不能为空");
            }
            String baqid = bean.getBaqid();
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
            if (StringUtils.isEmpty(bean.getBaqmc())) {
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if (baq != null) {
                    bean.setBaqmc(baq.getBaqmc());
                } else {
                    return ResultUtil.ReturnError("无民警办案区信息");
                }
            }
            if (StringUtils.isNotEmpty(bean.getMjsfzh()) && StringUtils.isEmpty(bean.getId())) {
                ChasYwMjrq ywFkdj = apiService.isMjzq(bean.getMjsfzh());
                if (ywFkdj != null) {
                    return ResultUtil.ReturnError("该民警已经登记，不能重复登记");
                }
            }
            ChasXtMjzpk mjzpk = mjzpkService.findBySfzh(bean.getMjsfzh());
            if (mjzpk == null) {
                return ResultUtil.ReturnError("民警照片信息未注册");
            }
            Boolean status = null;
            ChasYwMjrq yjlb = new ChasYwMjrq();
            if(StringUtils.isNotEmpty(bean.getId())){
                yjlb = apiService.findById(bean.getId());
                if (yjlb != null) {
                    yjlb.setXgsj(new Date());
                    MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                    status = apiService.edit(yjlb);
                }
            }else{
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                yjlb.setJrsj(new Date());
                status = apiService.insert(yjlb);
            }
            if (status) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", yjlb.getId());
                if(configuration.getDwHkrl()){
                    String bizId = yjlb.getZpid();
                    FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
                    if (fileInfoObj == null) {
                        return ResultUtil.ReturnSuccess("未查询到民警人脸照片！", data);
                    }else{
                        Map<String, Object> response = apiService.saveOrUpdateMjRldw(yjlb);
                        if((Boolean) response.get("status")){
                            return ResultUtil.ReturnSuccess("保存成功", data);
                        }else{
                            return ResultUtil.ReturnWarning("人脸信息上传失败", data);
                        }
                    }
                }else{
                    return ResultUtil.ReturnSuccess("保存成功", data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存民警信息出错", e);
        }
        return ResultUtil.ReturnError("保存失败");
    }

    private boolean addYyxx(ChasYwMjrq mjrq, ChasXtMjzpk mjzpk) {
        try {
            ChasYwYy yy = null;
            Map<String, Object> param2 = new HashMap<>();
            param2.put("yyrsfzh", mjrq.getMjsfzh());
            param2.put("nowTime", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            param2.put("notcrqzt", "03");
            //param2.put("yyzt","2");
            List<ChasYwYy> list = yyService.findList(param2, "kssj desc");
            if (list.size() > 0) {
                yy = list.get(0);
                yy.setRqsj(new Date());
                yy.setRybh(mjrq.getRybh());
                yy.setShbh(mjrq.getWdbhL());
                yy.setCrqzt("02");
                yyService.update(yy);
                return true;
            } else {
                yy = new ChasYwYy();
                yy.setBaqid(mjrq.getBaqid());
                yy.setBaqmc(mjrq.getBaqmc());
                yy.setYylx(mjrq.getFwyy());
                yy.setYyrjh(mjrq.getMjjh());
                yy.setYyrxm(mjrq.getMjxm());
                yy.setYyrsfzh(mjrq.getMjsfzh());
                yy.setYyrdwdm(mjzpk.getDwdm());
                yy.setYyrdwmc(mjzpk.getDwmc());
                yy.setKssj(new Date());
                int fwsx = mjrq.getFwsx() == null ? 24 : mjrq.getFwsx();
                yy.setJssj(new Date(yy.getKssj().getTime() + 1000 * 3600 * fwsx));
                yy.setRysl((short) 0);
                yy.setSysxs((short) 0);
                yy.setSxssl((short) 0);
                yy.setYyzt("2");
                yy.setRqsj(new Date());
                yy.setTel(mjrq.getLxdh());
                yy.setShbh(mjrq.getWdbhL());
                yy.setCrqzt("02");
                yy.setRybh(mjrq.getRybh());
                yyService.saveOrUpdate(yy);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存预约信息出错", e);
        }
        return false;
    }

    @Override
    public ApiReturnResult<?> update(MjglBean bean) {
        try {
            if (StringUtils.isEmpty(bean.getBaqid())) {
                return ResultUtil.ReturnError("民警办案区id不能为空");
            }
            if (StringUtils.isEmpty(bean.getBaqmc())) {
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if (baq != null) {
                    bean.setBaqmc(baq.getBaqmc());
                }
            }
            ChasYwMjrq yjlb = apiService.findById(bean.getId());
            if (yjlb != null) {
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                if (apiService.edit(yjlb)) {
                    return ResultUtil.ReturnSuccess("修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改民警信息出错", e);
        }
        return ResultUtil.ReturnError("修改失败");
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id -> {
                        apiService.deleteById(id);
                        //结束人脸定位
                        ChasYwMjrq mjrq = apiService.findById(id);
                        BaqConfiguration configuration = baqznpzService.findByBaqid(mjrq.getBaqid());
                        if(configuration.getDwHkrl()){
                            baqryxxService.endRldw(mjrq.getBaqid(), mjrq.getRybh(), mjrq.getRegisterCode());
                        }
                        //删除下发的海康人脸门禁(在区的民警)
                        if(!StringUtils.equals(mjrq.getZt(),"02")){
                            new Thread(() -> {
                                iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("M",mjrq.getId(),mjrq.getMjxm(),mjrq.getZpid(),mjrq.getBaqid(),new Date());
                            }).start();
                        }
                    });
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除民警入区信息出错", e);
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(MjglParam param) {

        if (StringUtils.isEmpty(param.getBaqid())) {
            String baqid = baqService.getZrBaqid();
            if (StringUtils.isEmpty(baqid)) {
                return ResultUtil.ReturnError("当前用户单位未关联办案区");
            }
            param.setBaqid(baqid);
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);

//        DataQxbsUtil.getSelectAll(apiService, map);
//        if(WebContext.getSessionUser()!=null){
//            String userRoleId = WebContext.getSessionUser().getRoleCode();
//            if (!"0101".equals(userRoleId)) {
//                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
//                map.put("sydwdm", orgCode);
//            }
//        }
        PageDataResultSet<ChasYwMjrq> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "lrsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData()
                , new String[]{"FSARRQZT", "ZD_CASE_ZJLX", "CHAS_ZD_ZB_XB", "CHAS_XKLB"}
                , new String[]{"zt", "zjlx", "xb", "xklx"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> leave(String id) {
        try {
            ChasYwMjrq yjlb = apiService.findById(id);
            if (yjlb != null) {
                if (SYSCONSTANT.MJRQ_CQ.equals(yjlb.getZt())) {
                    return ResultUtil.ReturnSuccess("该民警已经出区，无需重复出区");
                }
                Map<String, Object> map = apiService.mjChuqu(id);
                if ((Boolean) map.get("success")) {
                    String baqid = yjlb.getBaqid();
                    BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
                    if(configuration.getDwHkrl()){//结束人脸定位
                        baqryxxService.endRldw(yjlb.getBaqid(), yjlb.getRybh(), yjlb.getRegisterCode());
                    }
                    return ResultUtil.ReturnSuccess("出区成功");
                } else {
                    return ResultUtil.ReturnError(map.get("message").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("访客出区出错", e);
        }
        return ResultUtil.ReturnError("出区失败");
    }

    /**
     * 根据腕带编号获取民警信息
     * @param wdbhl
     * @return
     */
    @Override
    public ApiReturnResult<?> getMjxxByWdbhl(String wdbhl) {
        if (StringUtils.isEmpty(wdbhl)) {
            return ResultUtil.ReturnError("腕带编号不能为空");
        }
        Map<String, String> param = new HashMap<>();
        param.put("wdbhL", wdbhl);
        // 01 预约 02 在区 03离区
        param.put("zt", "02");
        PageDataResultSet<ChasYwMjrq> pageData = apiService.getEntityPageData(1,
                10, param, "lrsj desc");
        List<Map<String, Object>> dataList = DicUtil.translate(pageData.getData()
                , new String[]{"FSARRQZT", "ZD_CASE_ZJLX", "CHAS_ZD_ZB_XB", "CHAS_XKLB"}
                , new String[]{"zt", "zjlx", "xb", "xklx"});
        if (dataList.size() > 0) {
            Map<String, Object> objectMap = dataList.get(0);
            Map<String, Object> mjxxData = new HashMap<>();
            mjxxData.put("dwmc", objectMap.get(""));
            mjxxData.put("dwxtbh", objectMap.get(""));
            mjxxData.put("mjxm", objectMap.get("mjxm"));
            mjxxData.put("mjsfzh", objectMap.get("mjsfzh"));
            mjxxData.put("mjjh", objectMap.get("mjjh"));
            ApiReturnResult<Object> returnResult = ResultUtil.ReturnSuccess("获取成功");
            returnResult.setData(mjxxData);
            return returnResult;
        } else {
            return ResultUtil.ReturnError("没有该胸卡绑定的民警信息");
        }
    }
}
