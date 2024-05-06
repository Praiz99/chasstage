package com.wckj.chasstage.api.server.imp.fkgl;

import com.wckj.chasstage.api.def.face.model.RegisterParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.api.def.fkgl.model.FkglBean;
import com.wckj.chasstage.api.def.fkgl.model.FkglParam;
import com.wckj.chasstage.api.def.fkgl.service.ApiFkglService;
import com.wckj.chasstage.api.def.rygj.service.ApiRygjService;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 访客管理
 */
@Service
public class ApiFkglServiceImpl implements ApiFkglService {
    private static final Logger log= LoggerFactory.getLogger(ApiFkglServiceImpl.class);
    @Autowired
    private ChasYwFkdjService apiService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private BaqFaceService baqFaceService;
    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ApiRygjService apiRygjService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private IHikBrakeService iHikBrakeService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYwFkdj xgpz = apiService.findById(id);
        if(xgpz!=null){
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到访客信息");
    }

    @Override
    public ApiReturnResult<?> save(FkglBean bean) {
        try {
            if(StringUtils.isEmpty(bean.getBaqid())){
                return ResultUtil.ReturnError("民警办案区id不能为空");
            }
            String baqid = bean.getBaqid();
            BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
            if(StringUtils.isEmpty(bean.getBaqmc())){
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if(baq!=null){
                    bean.setBaqmc(baq.getBaqmc());
                }else{
                    return ResultUtil.ReturnError("无民警办案区信息");
                }
            }
            if(StringUtils.isNotEmpty(bean.getFksfzh()) && StringUtils.isEmpty(bean.getId())){
                ChasYwFkdj ywFkdj = apiService.isFkzq(bean.getFksfzh());
                if(ywFkdj!=null){
                    return ResultUtil.ReturnError("该访客已经登记，不能重复登记");
                }
            }
            processScry(bean);
            ChasYwFkdj yjlb = new ChasYwFkdj();
            MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
            yjlb.setJrsj(new Date());
            if(apiService.saveOrUpdate(yjlb)){
                Map<String, Object> data = new HashMap<>();
                data.put("id", yjlb.getId());
                if(configuration.getDwHkrl()){
                    String bizId = yjlb.getZpid();
                    FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId(bizId);
                    if (fileInfoObj == null) {
                        return ResultUtil.ReturnSuccess("未查询到访客人脸照片！", data);
                    }else{
                        Map<String, Object> response = apiService.saveOrUpdateFkRldw(yjlb);
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
            log.error("保存访客信息出错", e);
        }
        return ResultUtil.ReturnError("保存失败");
    }

    /**
     * 处理速裁人员业务
     * @param bean
     */
    private void processScry(FkglBean bean){
        if("07".equals(bean.getFklb())){//速裁人员
            ChasBaq baq = baqService.findById(bean.getBaqid());
            RegisterParam param = new RegisterParam();
            param.setBizId(bean.getZpid());
            param.setBaqid(bean.getBaqid());
            param.setTzlx("qt");
            param.setDwxtbh(baq.getDwxtbh());
            param.setTzbh(bean.getFksfzh());
            boolean register = baqFaceService.register(param);
            if(!register){
                log.error(bean.getFksfzh()+"速裁人脸注册失败");
            }
        }
    }
    @Override
    public ApiReturnResult<?> update(FkglBean bean) {
        try {
            if(StringUtils.isEmpty(bean.getBaqid())){
                return ResultUtil.ReturnError("民警办案区id不能为空");
            }
            if(StringUtils.isEmpty(bean.getBaqmc())){
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if(baq!=null){
                    bean.setBaqmc(baq.getBaqmc());
                }else{
                    return ResultUtil.ReturnError("无民警办案区信息");
                }
            }
           // processScry(bean);
            ChasYwFkdj yjlb = apiService.findById(bean.getId());
            if(yjlb!=null){
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                if(apiService.saveOrUpdate(yjlb)){
                    return ResultUtil.ReturnSuccess("修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改访客信息出错", e);
        }
        return ResultUtil.ReturnError("修改失败");
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id->{
                        ChasYwFkdj fkdj = apiService.findById(id);
                        if(fkdj!=null){
                            if("07".equals(fkdj.getFklb())){
                                baqFaceService.delete(fkdj.getBaqid(), fkdj.getFksfzh(), "qt");
                            }
                            apiService.deleteById(id);
                            //结束人脸定位
                            BaqConfiguration configuration = baqznpzService.findByBaqid(fkdj.getBaqid());
                            if(configuration.getDwHkrl()){
                                baqryxxService.endRldw(fkdj.getBaqid(), fkdj.getRybh(), fkdj.getRegisterCode());
                            }
                            //删除下发的海康人脸门禁
                            if(StringUtils.equals(fkdj.getZt(),"02")){
                                new Thread(() -> {
                                    iHikBrakeService.deleteIssuedToBrakeByFaceAsyn("M",fkdj.getId(),fkdj.getFkxm(), fkdj.getZpid(), fkdj.getBaqid(),new Date());
                                }).start();
                            }
                        }
                    });
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除访客信息出错", e);
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(FkglParam param) {
        if(StringUtils.isEmpty(param.getBaqid())){
            String baqid=baqService.getZrBaqid();
            if(StringUtils.isEmpty(baqid)){
                return ResultUtil.ReturnError("当前用户单位未关联办案区");
            }
            param.setBaqid(baqid);
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
//        DataQxbsUtil.getSelectAll(apiService, map);
//        String userRoleId = WebContext.getSessionUser().getRoleCode();
//        if (!"0101".equals(userRoleId)) {
//            String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
//            map.put("sydwdm", orgCode);
//        }
        PageDataResultSet<ChasYwFkdj> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "lrsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData()
                ,new String[]{"CHAS_ZD_ZB_XB","FKLB","CHAS_ZD_CASE_ZJLX","JS_XKLB"}
                ,new String[]{"xb","fklb","zjlx","xklx"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> leave(String id) {
        try {
            ChasYwFkdj yjlb = apiService.findById(id);
            BaqConfiguration configuration = baqznpzService.findByBaqid(yjlb.getBaqid());
            if(yjlb!=null){
                if(SYSCONSTANT.MJRQ_CQ.equals(yjlb.getZt())){
                    return ResultUtil.ReturnSuccess("该访客已经出区，无需重复出区");
                }
                yjlb.setXgsj(new Date());
                yjlb.setCqsj(new Date());
                yjlb.setZt(SYSCONSTANT.MJRQ_CQ);
                Map<String ,Object> map=new HashMap<>();
                map.put("baqid",yjlb.getBaqid());
                map.put("rybh",yjlb.getRybh());
                ChasRygj rygj = rygjService.findzhlocation(map);
                if(rygj!=null){
                    rygj.setJssj(new Date());
                    rygjService.update(rygj);
                    apiRygjService.sendLastVmsInfo(rygj.getBaqid(),rygj.getRybh(),rygj.getQyid());
                }
                if(apiService.saveOrUpdate(yjlb)){
                    if("07".equals(yjlb.getFklb())){
                        baqFaceService.delete(yjlb.getBaqid(), yjlb.getFksfzh(), "qt");
                    }
                    if(configuration.getDwHkrl()){//结束人脸定位
                        baqryxxService.endRldw(yjlb.getBaqid(), yjlb.getRybh(), yjlb.getRegisterCode());
                    }
                    return ResultUtil.ReturnSuccess("出区成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("访客出区出错", e);
        }
        return ResultUtil.ReturnError("出区失败");
    }

}
