package com.wckj.chasstage.api.server.imp.mjzpk;

import com.wckj.chasstage.api.def.face.model.FaceResult;
import com.wckj.chasstage.api.def.face.model.FaceTzlx;
import com.wckj.chasstage.api.def.face.model.RecognitionParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.api.def.mjzpk.model.MjzpkBean;
import com.wckj.chasstage.api.def.mjzpk.model.MjzpkParam;
import com.wckj.chasstage.api.def.mjzpk.service.ApiMjzpkService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.common.util.file.dto.FileParam;
import com.wckj.chasstage.common.util.file.service.BaqFileService;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.frws.file.IStreamFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.spring.event.EventTrigger;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 民警照片
 */
@Service
public class ApiMjzpkServiceImpl implements ApiMjzpkService {
    private static final Logger log = LoggerFactory.getLogger(ApiMjzpkServiceImpl.class);
    @Autowired
    private ChasXtMjzpkService apiService;
    @Autowired
    private BaqFileService fileService;
    @Autowired
    private ChasBaqService baqService;

    @Autowired
    private JdoneSysOrgService orgService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ChasBaqrefService baqrefService;
    @Autowired
    private FaceInvokeService faceInvokeService;

    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private BaqFaceService baqFaceService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasXtMjzpk xgpz = apiService.findById(id);
        if (xgpz != null) {
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到民警照片信息");
    }

    @Override
    public ApiReturnResult<?> save(MjzpkBean bean) {
        try {
            if (StringUtils.isEmpty(bean.getDwxtbh())) {
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if (baq != null) {
                    bean.setDwxtbh(baq.getDwxtbh());
                }
            }
            if (StringUtils.isNotEmpty(bean.getMjsfzh())) {
                ChasXtMjzpk ywFkdj = apiService.findBySfzh(bean.getMjsfzh());
                if (ywFkdj != null) {
                    return ResultUtil.ReturnError("该民警已经登注册，不能重复注册");
                }
            }
            if (StringUtils.isEmpty(bean.getZpid())
                    && StringUtils.isNotEmpty(bean.getBase64Str())) {//上传图片数据
                FileParam p = new FileParam();
                p.setFileName(bean.getMjsfzh() + ".jpg");
                p.setData(Base64.getDecoder().decode(bean.getBase64Str()));
                p.setBizId(StringUtils.getGuid32());
                p.setBizType("mjzpk");
                p.setDwxtbh(bean.getDwxtbh());
                if (!fileService.uploadFile(p)) {
                    return ResultUtil.ReturnError("上传民警照片数据失败");
                } else {
                    bean.setZpid(p.getBizId());
                }
            }
            ChasXtMjzpk yjlb = new ChasXtMjzpk();
            MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
            //生成民警照片库数据
            boolean r = faceInvokeService.saveMjFeature(yjlb.getZpid(), yjlb.getDwxtbh(), yjlb.getMjsfzh());
            if (!r) {
                return ResultUtil.ReturnError("图片不能识别人脸，不能注册");
            }
            Map<String, Object> map = apiService.saveOrUpdate(yjlb);
            if (map.get("success") != null && (boolean) map.get("success")) {
                return ResultUtil.ReturnSuccess("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存民照片息出错", e);
            return ResultUtil.ReturnError("保存失败:"+e.getMessage());
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(MjzpkBean bean) {
        try {
            ChasXtMjzpk yjlb = apiService.findById(bean.getId());
            if (yjlb != null) {
                //删除人脸特征，修改的时候
                String oldmjsfzh = yjlb.getMjsfzh();
                String mjsfzh = bean.getMjsfzh();
                //老身份证不为空，并且身份证号不一致，需要删除之前身份证的特征，重新生成
                if(StringUtils.isNotEmpty(oldmjsfzh) && !oldmjsfzh.equals(mjsfzh)){
                    deleteFaceByUpdate(yjlb);
                }
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
//                boolean r = faceService.downloadFile(yjlb, true);
                boolean r = faceInvokeService.saveMjFeature(yjlb.getZpid(), yjlb.getDwxtbh(), yjlb.getMjsfzh());
                if (!r) {
                    return ResultUtil.ReturnError("图片不能识别人脸，不能注册");
                }
                Map<String, Object> map = apiService.saveOrUpdate(yjlb);
                if (map.get("success") != null && (boolean) map.get("success")) {
                    return ResultUtil.ReturnSuccess("修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改民警照片信息出错:" + e.getMessage(), e);
            return ResultUtil.ReturnError("修改失败:" + e.getMessage());
        }
        return ResultUtil.ReturnError("修改失败");
    }

    private void deleteFaceByUpdate(ChasXtMjzpk yjlb){
        Map<String, Object> param = new HashMap<>();
        String dwxtbh = yjlb.getDwxtbh();
        String tzbh = yjlb.getMjsfzh();
        if(StringUtils.isEmpty(dwxtbh)||StringUtils.isEmpty(tzbh)){
            log.error("删除民警特征异常，单位系统编号【"+dwxtbh+"】或特征编号【"+tzbh+"】为空:");
            return;
        }
        param.put("sydwxtbh", dwxtbh);
        List<ChasBaqref> list = baqrefService.findList(param, null);
        if (list.size() < 1) {
            log.error("民警【" + tzbh + "】不存在关联办案区！");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ChasBaqref chasBaqref = list.get(i);
            String baqid = chasBaqref.getBaqid();
            faceInvokeService.deleteFeatureByTzbh(baqid, tzbh, FaceTzlx.MJ.getCode());
        }
    }


    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            if (StringUtils.isNotEmpty(ids)) {
                apiService.delete(ids.split(","));
                return ResultUtil.ReturnSuccess("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除民警照片信息出错", e);
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(MjzpkParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);

        DataQxbsUtil.getSelectAll(apiService, map);
        map.put("baqid", null);
        PageDataResultSet<ChasXtMjzpk> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "lrsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData()
                , new String[]{"QQYZ"}, new String[]{"spzt"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> genFeature(String id) {
        try {
            boolean flag = true;
            String mjsfzh = null;
            String[] idsArr = id.split(",");
            for (int i = 0; i < idsArr.length; i++) {
                String idtemp = idsArr[i];
                if (StringUtils.isEmpty(idtemp)) {
                    continue;
                }
                ChasXtMjzpk yjlb = apiService.findById(idtemp);
                if (yjlb != null) {
                    boolean r = faceInvokeService.saveMjFeature(yjlb.getZpid(), yjlb.getDwxtbh(), yjlb.getMjsfzh());
                    //民警特征生成识别
                    if (!r) {
                        mjsfzh = yjlb.getMjsfzh();
                        flag = false;
                        break;
                    }
//                boolean r = faceService.downloadFile(yjlb, true);
                }
            }
            if(flag){
                return ResultUtil.ReturnSuccess("人脸特征数据生成成功");
            }else{
                return ResultUtil.ReturnError("人脸【"+mjsfzh+"】特征数据生成失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("人脸特征数据生成出错" + e.getMessage(), e);
            return ResultUtil.ReturnError("人脸特征数据生成失败" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getMjzpkList(MjzpkParam param) {
        String baqid = param.getBaqid();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        //|| StringUtils.isEmpty(startDate)
        if (StringUtils.isEmpty(baqid) &&StringUtils.isEmpty(param.getMjsfzh())) {

            return ResultUtil.ReturnError("参数错误，请设置办案区id或身份证号");
        }
        if (StringUtils.isEmpty(baqid)) {
            endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        try {
            Map<String, Object> param1 = new HashMap<>();
            param1.put("baqid", baqid);
            param1.put("lrsj1", startDate);
            param1.put("lrsj2", endDate);
            param1.put("mjsfzh", param.getMjsfzh());
            List<ChasXtMjzpk> mjzpkList = apiService.findList(param1, "lrsj desc");
            log.info("民警照片数量:" + mjzpkList.size());
            List<Map<String, Object>> mapList = DicUtil.translate(mjzpkList, new String[]{"QQYZ"}, new String[]{"spzt"});
            return ResultUtil.ReturnSuccess(mapList);
        } catch (Exception e) {
            log.error("getMjzpkList:", e);
        }
        return ResultUtil.ReturnError("获取失败");
    }

    /**
     * 批量导入民警照片库
     *
     * @param request
     * @param file
     * @return
     */
    @Override
    public ApiReturnResult<String> plupload(HttpServletRequest request, CommonsMultipartFile file) {
        ApiReturnResult<String> apiReturnResult = new ApiReturnResult<>();
        InputStream inputStream = null;
        try {
            if (file == null) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("未选择上传文件");
                return apiReturnResult;
            }
            List<ExcelInUtil.ExcelColumn> columnList = new ArrayList<>();
            columnList.add(new ExcelInUtil.ExcelColumn("序号", "num"));
            columnList.add(new ExcelInUtil.ExcelColumn("单位", "dwdm"));
            columnList.add(new ExcelInUtil.ExcelColumn("民警姓名", "mjxm"));
            columnList.add(new ExcelInUtil.ExcelColumn("身份证号", "mjsfzh"));
            columnList.add(new ExcelInUtil.ExcelColumn("警号", "mjjh"));
            columnList.add(new ExcelInUtil.ExcelColumn("照片", "zpid"));
            columnList.add(new ExcelInUtil.ExcelColumn("备注", "bz"));
            inputStream = file.getInputStream();
            List<Map<String, Object>> list = ExcelInUtil.readExcel(inputStream, columnList);
            if (list != null && !list.isEmpty()) {
                list.stream().forEach(this::processItem);
                apiReturnResult.setCode("200");
                apiReturnResult.setMessage("导入成功");
                return apiReturnResult;
            } else {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("未解析到民警信息");
                return apiReturnResult;
            }
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("系统异常:" + e.getMessage());
            log.error("importData:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return apiReturnResult;
    }

    /**
     * 根据文件bizId上传民警照片库
     *
     * @param request
     * @param bizId
     * @return
     */
    @Override
    public ApiReturnResult<String> pluploadByBizId(HttpServletRequest request, String bizId) {
        ApiReturnResult<String> apiReturnResult = new ApiReturnResult<>();
        try {
            if (StringUtils.isEmpty(bizId)) {
                apiReturnResult.setCode("500");
                apiReturnResult.setMessage("未选择上传文件");
                return apiReturnResult;
            }
            List<FileInfoObj> fileInfoList = FrwsApiForThirdPart.getFileInfoList(bizId);
            if (fileInfoList != null) {
                log.debug("下载" + bizId + "文件数据大小" + fileInfoList.size());
            } else {
                log.debug("下载" + bizId + "文件数据为null");
            }
            for (int i = 0; i < fileInfoList.size(); i++) {
                FileInfoObj fileInfoObj = fileInfoList.get(i);
                if (fileInfoObj == null) {
                    log.debug("continue：下载文件数据为null");
                    continue;
                }
                String fileId = fileInfoObj.getId();
                pluploadByFileId(fileId);
            }
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("导入成功，稍后请刷新列表！");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("系统异常:" + e.getMessage());
            log.error("importData:", e);
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> mjzpkReg(MjzpkBean bean, String lrrsfzh) {
        SessionUser sessionUser = WebContext.getSessionUser();

        try {
            String sfzh = bean.getMjsfzh();
            String mjxm = bean.getMjxm();
            String mjjh = bean.getMjjh();
            String zpid = bean.getZpid();
            String lxfs = bean.getLxdh();
            String gzdw = bean.getGzdw();
            String baqid = bean.getBaqid();
//            String lrrsfzh = sessionUser.getIdCard();
            if(StringUtil.isEmpty(sfzh)
                    ||StringUtil.isEmpty(zpid)){
                return ResultUtil.ReturnError("参数错误");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("mjsfzh", sfzh);
//            params.put("spzt", "1");
            List<ChasXtMjzpk> mjzpks = mjzpkService.findByParams(params);
            if (mjzpks != null && !mjzpks.isEmpty()) {
//                result.setData(mjzpks.get(0));
                return ResultUtil.ReturnError("人员信息已注册");
            }
            /*params.put("spzt", "0");
            mjzpks = mjzpkService.findByParams(params);
            if (mjzpks != null && !mjzpks.isEmpty()) {
//                apiReturnResult.setData(mjzpks.get(0));
                return ResultUtil.ReturnError("人脸正在申请中");
            }*/

            ChasXtMjzpk reg = new ChasXtMjzpk();
            reg.setId(StringUtils.getGuid32());
            reg.setIsdel((short)0);
            reg.setLrsj(new Date());
            reg.setMjsfzh(sfzh);
            reg.setMjjh(mjjh);
            reg.setMjxm(mjxm);
            reg.setLxdh(lxfs);
            reg.setGzdw(gzdw);
            reg.setLrrSfzh(lrrsfzh);
            JdoneSysUser lrr = userService.findSysUserByIdCard(lrrsfzh);
            if(lrr!=null){
                params.clear();
                params.put("dwdm", lrr.getOrgCode());
                ChasBaq baq = baqService.findByParams(params);
                if(baq!=null){
                    reg.setBaqid(baq.getId());
                    reg.setBaqmc(baq.getBaqmc());
                }
            }
            if(StringUtil.isNotEmpty(baqid)){
                reg.setBaqid(baqid);
                ChasBaq baq = baqService.findById(baqid);
                if(baq!=null){
                    reg.setBaqmc(baq.getBaqmc());
                    reg.setDwdm(baq.getDwdm());
                    reg.setDwmc(baq.getDwmc());
                    reg.setDwxtbh(baq.getDwxtbh());
                }

            }
            boolean r = faceInvokeService.saveMjFeature(reg.getZpid(), reg.getDwxtbh(), reg.getMjsfzh());
            if (!r) {
                return ResultUtil.ReturnError("图片不能识别人脸，不能注册");
            }
            reg.setZpid(zpid);
            reg.setSpzt("0");
            mjzpkService.save(reg);
            return ResultUtil.ReturnSuccess("成功发起申请中",reg);
        } catch (Exception e) {
            log.error("mjzpkReg:", e);
            return ResultUtil.ReturnError( "系统异常:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> getMjxxByImg(String baqid, String base64) {
        if(StringUtils.isEmpty(baqid)|| StringUtils.isEmpty(base64)){
            return ResultUtil.ReturnError("参数为空");
        }
        ChasBaq baq = baqService.findById(baqid);
        if (baq == null) {
            return ResultUtil.ReturnError("获取办案区信息失败!");
        }
        try {
            if (StringUtils.isNotEmpty(baq.getDwxtbh())) {
                RecognitionParam recognitionParam = new RecognitionParam();
                recognitionParam.setDwxtbh(baq.getDwxtbh());
                recognitionParam.setBaqid(baqid);
                recognitionParam.setBase64Str(base64);
                recognitionParam.setTzlx("mj");
                List<FaceResult> faceResults = baqFaceService.recognition(recognitionParam);
                if(ObjectUtils.isEmpty(faceResults) || faceResults.size() == 0){
                    return ResultUtil.ReturnSuccess("未查询到人员信息!");
                }else{
                    FaceResult faceResult = faceResults.get(0);
                    String sfzh = faceResult.getTzbh();
                    ChasXtMjzpk mjzpk = apiService.findBySfzh(sfzh);
                    if(ObjectUtils.isEmpty(mjzpk)){
                        return ResultUtil.ReturnSuccess("未匹配到民警照片信息!");
                    }else{
                        Map<String, Object> map = new HashMap<>();
                        map.put("mjzpk", mjzpk);
                        return ResultUtil.ReturnSuccess("人脸识别成功", map);
                    }
                }
            } else {
                return ResultUtil.ReturnError("办案区单位系统编号不能为空!");
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("checkMjZpByBase64:", e);
            return ResultUtil.ReturnError( "系统异常:" + e.getMessage());
        }
    }

    @Override
    public ApiReturnResult<?> findBySfzh(String sfzh) {
        if (StringUtils.isEmpty(sfzh)) {
            return ResultUtil.ReturnError("参数为空");
        }
        ChasXtMjzpk mjzpk = apiService.findBySfzh(sfzh);
        if (ObjectUtils.isEmpty(mjzpk)) {
            return ResultUtil.ReturnSuccess("未匹配到民警照片信息!");
        }
        return ResultUtil.ReturnSuccess(mjzpk);

    }


    public void pluploadByFileId(String fileId) {
        log.debug("开始处理文件id" + fileId + "批量导入数据。");
        InputStream inputStream = null;
        try {
            if (StringUtils.isEmpty(fileId)) {
                log.error("文件id为空");
                return;
            }
            List<ExcelInUtil.ExcelColumn> columnList = new ArrayList<>();
            columnList.add(new ExcelInUtil.ExcelColumn("序号", "num"));
            columnList.add(new ExcelInUtil.ExcelColumn("单位", "dwdm"));
            columnList.add(new ExcelInUtil.ExcelColumn("民警姓名", "mjxm"));
            columnList.add(new ExcelInUtil.ExcelColumn("身份证号", "mjsfzh"));
            columnList.add(new ExcelInUtil.ExcelColumn("警号", "mjjh"));
            columnList.add(new ExcelInUtil.ExcelColumn("照片", "zpid"));
            columnList.add(new ExcelInUtil.ExcelColumn("备注", "bz"));
            IStreamFileObj iStreamFileObj = FrwsApiForThirdPart.downloadStreamFileByFileId(fileId);
            log.debug("下载批量导入文件" + fileId + "，成功。");
            if (iStreamFileObj == null) {
                log.error("文件【" + fileId + "】为空");
                return;
            }
            inputStream = iStreamFileObj.getInputStream();
            List<Map<String, Object>> list = ExcelInUtil.readExcel(inputStream, columnList);
            log.debug("解析excel" + fileId + "，成功。");
            if (list != null && !list.isEmpty()) {
                log.debug("获取excel,民警照片库数量" + list.size());
                list.stream().forEach(this::processItem);
                log.debug("文件【" + fileId + "】导入成功");
                return;
            } else {
                log.debug("文件【" + fileId + "】未解析到民警信息");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常importData:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void processItem(Map<String, Object> item) {
        //读取到身份证号和照片
        if (item.containsKey("mjsfzh") && item.containsKey("photo")) {
            try {
                String mjsfzh = item.get("mjsfzh").toString();
                Map<String, Object> param = new HashMap<>();
                param.put("mjsfzh", mjsfzh);
                List<ChasXtMjzpk> mjzpks = mjzpkService.findByParams(param);
                ChasXtMjzpk mjzpk;
                if (mjzpks == null || mjzpks.isEmpty()) {//新增
                    mjzpk = new ChasXtMjzpk();
                    mjzpk.setIsdel((short) 0);
                } else {
                    mjzpk = mjzpks.get(0);
                    //删除原图片
                    if (StringUtil.isNotEmpty(mjzpk.getZpid())) {
                        FrwsApiForThirdPart.deleteFileByBizId(mjzpk.getZpid());
                    }
                    //删除民警照片库数据
                    if (StringUtils.isNotEmpty(mjzpk.getDwxtbh())) {
                        Map<String, Object> paramByMj = new HashMap<>();
                        paramByMj.put("sydwxtbh", mjzpk.getDwxtbh());
                        List<ChasBaqref> list = baqrefService.findList(paramByMj, null);
                        for (int i = 0; i < list.size(); i++) {
                            ChasBaqref chasBaqref = list.get(i);
                            faceInvokeService.deleteFeatureByTzbh(chasBaqref.getBaqid(), mjzpk.getMjsfzh(), FaceTzlx.MJ.getCode());
                        }
                    }
                }
                String zpid = StringUtils.getGuid32();
                String dwdm = item.get("dwdm").toString();
                String mjxm = item.get("mjxm").toString();
                String mjjh = item.get("mjjh").toString();
                String exten = item.get("mimeType").toString();
                byte[] photo = (byte[]) item.get("photo");
                UploadParamObj uploadParam = new UploadParamObj();
                uploadParam.setOrgSysCode(dwdm);
                uploadParam.setBizId(zpid);
                uploadParam.setBizType("mjzpk");
                FrwsApiForThirdPart.uploadByteFile(
                        new ByteFileObj(zpid + exten, photo), uploadParam);
                JdoneSysOrg sysOrg = orgService.findByCode(dwdm);
                if (sysOrg != null) {
                    mjzpk.setDwdm(dwdm);
                    mjzpk.setDwmc(sysOrg.getName());
                    mjzpk.setDwxtbh(sysOrg.getSysCode());
                }
                SessionUser sessionUser = WebContext.getSessionUser();
                if (sessionUser != null) {
                    mjzpk.setLrrSfzh(sessionUser.getIdCard());
                }
                mjzpk.setZpid(zpid);
                mjzpk.setMjjh(mjjh);
                mjzpk.setMjsfzh(mjsfzh);
                mjzpk.setMjxm(mjxm);
                mjzpk.setSpzt("1");
                mjzpk.setLrsj(new Date());
                log.error("开始出发民警照片库异步方法");
                EventTrigger.triggerEventAsync(this, "MJZPK_SAVE_ALL", mjzpk);
                log.error("结束出发民警照片库异步方法");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("生成民警照片库失败", e);
            }
        }
    }
}
