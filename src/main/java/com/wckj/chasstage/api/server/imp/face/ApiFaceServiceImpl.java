package com.wckj.chasstage.api.server.imp.face;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.face.model.FaceResult;
import com.wckj.chasstage.api.def.face.model.RecogParam;
import com.wckj.chasstage.api.def.face.service.ApiFaceService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.file.service.BaqFileService;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 人脸识别服务
 */
@Service
public class ApiFaceServiceImpl implements ApiFaceService {
    final static Logger log = Logger.getLogger(ApiFaceServiceImpl.class);
    @Autowired
    private BaqFileService fileService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasXtMjzpkService mjzpkService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasYwJhrwService jhrwService;

    @Override
    public ApiReturnResult<?> recognition(RecogParam param) {
        /*if (StringUtils.isNotEmpty(param.getBaqid())) {
            ChasBaq baq = baqService.findById(param.getBaqid());
            if (baq != null) {
                param.setDwxtbh(baq.getDwxtbh());
            }
        }
        String file = fileService.saveFile(param.getBase64Str());
        if (StringUtils.isEmpty(file)) {
            return ResultUtil.ReturnError("保存base64字符串至本地文件失败");
        }
        FaceFeature faceFeature = faceService.genFaceFeature(file);
        if (faceFeature == null) {
            return ResultUtil.ReturnError("生成人员图片特征数据失败");
        }
        FaceParam fp = new FaceParam();
        fp.setDwxtbh(param.getDwxtbh());
        fp.setRylx(param.getRylx());
        String minFaceStr = SysUtil.getParamValue("MIN_POLICE_RECOGNITION");
        String tzbh = faceService.searchBach(faceFeature, minFaceStr, fp);
        log.info("人脸识别结果" + tzbh);
        FileUtil.delete(file);
        if (StringUtils.isEmpty(tzbh)) {
            return ResultUtil.ReturnSuccess("无法找到匹配人员信息");
        }
        if ("mj".equalsIgnoreCase(param.getRylx())) {
            ChasXtMjzpk bySfzh = mjzpkService.findBySfzh(tzbh);
            if (bySfzh != null) {
                return ResultUtil.ReturnSuccess(bySfzh);
            } else {
                return ResultUtil.ReturnSuccess("暂无人员【" + tzbh + "】信息");
            }
        } else if ("xyr".equalsIgnoreCase(param.getRylx())) {
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(tzbh);
            if (baqryxx != null) {
                return ResultUtil.ReturnSuccess(baqryxx);
            } else {
                return ResultUtil.ReturnSuccess("暂无人员【" + tzbh + "】信息");
            }

        }*/
        return ResultUtil.ReturnError("人员类型参数错误");
    }

    /**
     * 嫌疑人的身份核验(防误放)
     *
     * @param baqid 办案区id
     * @param data  人脸base64
     * @return
     */
    @Override
    public ApiReturnResult<?> checkXyrInfo(String baqid, String data) {
        log.debug("办案区【"+baqid+"】开始执行防误放识别");
        List<FaceResult> faceResults = JSONHelper.toList(data, FaceResult.class);
        //识别出来的人员信息
        List<ChasBaqryxx> baqryList = new ArrayList<>();
        for (int i = 0; i < faceResults.size(); i++) {
            FaceResult faceResult = faceResults.get(i);
            String rybh = faceResult.getTzbh();
            if (StringUtils.isEmpty(rybh)) {
                continue;
            }
            String minFaceStr = SysUtil.getParamValue("MIN_POLICE_RECOGNITION");
            int minFaceValue = 80;
            if(StringUtils.isNotEmpty(minFaceStr)){
                try {
                    minFaceValue = Integer.parseInt(minFaceStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("人脸匹配分数不为数字，默认80分");
                }
            }
            Integer score = faceResult.getScore();
            if(score >= minFaceValue){
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                if(baqryxx != null){
                    baqryList.add(baqryxx);
                }
            }
        }
        log.debug("识别到的防误放人员数量为" + baqryList.size());
        Collections.sort(baqryList, (ry1, ry2) -> {
            if (ry1.getLrsj().getTime() > ry2.getLrsj().getTime()) {
                return -1;
            } else if (ry1.getLrsj().getTime() < ry2.getLrsj().getTime()) {
                return 1;
            } else {
                return 0;
            }
        });
        if (baqryList.size() > 0) {
            ChasBaqryxx baqryxx = baqryList.get(0);
            String ryzt = baqryxx.getRyzt();
            if (SYSCONSTANT.BAQRYZT_YCS.equals(ryzt)) {
                log.debug("识别到的防误放人员【"+baqryxx.getRybh()+"】开始结束戒护任务");
                DevResult devResult = jhrwService.changeJhrwZt(baqryxx.getMjXm(), baqryxx.getZbdwBh(), baqryxx, SYSCONSTANT.JHRWLX_CQJH, SYSCONSTANT.JHRWZT_YZX);
                if (devResult.getCode() != 0) {
                    log.info("结束出区戒护任务失败:" + devResult.getMessage());
                }
                return ResultUtil.ReturnSuccess(baqryxx);
            } else {
                return ResultUtil.ReturnError("该人员未出所");
            }
        } else {
            return ResultUtil.ReturnError("暂无人员信息");
        }
    }

    @Override
    public ApiReturnResult<?> recognitionAll(String baqid, String data, String sblx) {
        log.debug("办案区【"+baqid+"】开始执行识别类型【"+sblx+"】的识别");
        List<FaceResult> faceResults = JSONHelper.toList(data, FaceResult.class);
        //识别出来的人员信息
        List<ChasBaqryxx> baqryList = new ArrayList<>();
        log.debug("识别的人脸数据个数【" + faceResults.size() + "】");

        //如果是速裁人员，查询预约的速裁数据
        List<String> scrybhList = new ArrayList<>();
        if(!sblx.equals("2")){
            ApiReturnResult<?> scryResult = getScRyList(baqid);
            //如果速裁查询异常，则返回结果
            if(!scryResult.getIsSuccess()){
                return scryResult;
            }
            scrybhList = (List<String>) scryResult.getData();
        }
        for (int i = 0; i < faceResults.size(); i++) {
            FaceResult faceResult = faceResults.get(i);
            String rybh = faceResult.getTzbh();
            //如果人员编号为null，跳出循环取下1个
            if (StringUtils.isEmpty(rybh)) {
                continue;
            }
            String minFaceStr = SysUtil.getParamValue("MIN_POLICE_RECOGNITION");
            int minFaceValue = 80;
            if(StringUtils.isNotEmpty(minFaceStr)){
                try {
                    minFaceValue = Integer.parseInt(minFaceStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("人脸匹配分数不为数字，默认80分");
                }
            }
            Integer score = faceResult.getScore();
            if(score >= minFaceValue){
                // 2 标识 送押类型，不需要人员编号查询条件，1标识 速裁，目前默认空也是素裁
                if(!sblx.equals("2")){
                    //如果速裁人员包含这个数据，加入集合之中
                    if (scrybhList.contains(rybh)) {
                        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                        if(baqryxx != null){
                            baqryList.add(baqryxx);
                        }

                    }
                }else{
                    //送押人员
                    ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                    if(baqryxx != null){
                        baqryList.add(baqryxx);
                    }
                }
            }
        }
        log.debug("识别到的送押人员数量为" + baqryList.size());
        Collections.sort(baqryList, (ry1, ry2) -> {
            if (ry1.getLrsj().getTime() > ry2.getLrsj().getTime()) {
                return -1;
            } else if (ry1.getLrsj().getTime() < ry2.getLrsj().getTime()) {
                return 1;
            } else {
                return 0;
            }
        });
        if(baqryList.size()>0){
            ChasBaqryxx baqryxx = baqryList.get(0);
            if (baqryxx != null) {
                if ("2".equals(sblx)) {
                    String cRyqx = baqryxx.getCRyqx();
                    if (("01".equals(cRyqx) || "04".equals(cRyqx)) && StringUtil.equals(baqryxx.getRyzt(),SYSCONSTANT.BAQRYZT_YCS)) {
                        return ResultUtil.ReturnSuccess(baqryxx);
                    } else {
                        return ResultUtil.ReturnError("此人员不是送押状态！");
                    }
                }
                return ResultUtil.ReturnSuccess(baqryxx);
            } else {
                return ResultUtil.ReturnError("暂无人员信息");
            }
        }else{
            return ResultUtil.ReturnError("暂无人员信息");
        }
    }

    /**
     * 人证比对
     *
     * @param faceStr
     * @param zjStr
     * @return
     */
    @Override
    public ApiReturnResult<String> rzbd(String faceStr, String zjStr) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
       /* String path = "tmp/face/rzdb/" + StringUtils.getGuid32() + "/";
        String faceFile = FileUtil.byte2File(Base64.getDecoder().decode(faceStr), path, "face.jpg");
        String imageFile = FileUtil.byte2File(Base64.getDecoder().decode(zjStr), path, "image.jpg");
        FaceFeature faceFeature = faceService.genFaceFeature(faceFile);
        FaceFeature zjFeature = faceService.genFaceFeature(imageFile);
        if (faceFeature == null || zjFeature == null) {
            returnResult.setCode("500");
            returnResult.setMessage("文件特征码生成失败，无法比对");
            return returnResult;
        }
        try {
            int code = faceService.checkFace(faceFeature, zjFeature);
            String paramValue = SysUtil.getParamValue("chasstage.rzbd.score");
            Integer score = 80;
            if (StringUtils.isNotEmpty(paramValue)) {
                score = new Integer(paramValue);
            }
            if (code >= score) {
                returnResult.setCode("200");
                returnResult.setMessage("比对成功");
            } else {
                returnResult.setCode("500");
                returnResult.setMessage("比对失败，相似度【" + code + "】");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("无法比对" + e.getMessage());
        }*/
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> recognitionScry(String baqid, String data, String sblx) {
        try {
            log.debug("办案区【"+baqid+"】开始执行识别类型【"+sblx+"】的识别");
            List<FaceResult> faceResults = JSONHelper.toList(data, FaceResult.class);
            log.debug("识别的人脸数据个数【" + faceResults.size() + "】");
            if(faceResults==null||faceResults.isEmpty()
                    ||StringUtil.isEmpty(faceResults.get(0).getTzbh())){
                return ResultUtil.ReturnError("速裁人员身份证号不能为空");
            }
            return getScRyxxBysfzh(faceResults.get(0).getTzbh());
            //return getScRyxxBysfzh(data);
        } catch (Exception e) {
            log.error("处理速裁人员识别结果出错",e );
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("处理速裁人员识别结果出错");
    }

    //获取速裁人员列表
    private ApiReturnResult<List<String>> getScRyList(String baqid) {
        if (StringUtils.isEmpty(baqid)) {
            return ResultUtil.ReturnError("baqid不能为空");
        }
        String url = SysUtil.getParamValue("scryxx_url");
        if (StringUtils.isEmpty(url)) {
            return ResultUtil.ReturnError("未配置速裁平台接口地址");
        }
        try {
            JSONObject get = HttpClientUtils.httpGet(url + "agxt/api/yyxx/getRyxxByBaqid?baqid=" + baqid);
            if (get != null) {
                int code = get.getInteger("code");
                if (code == 0) {
                    List<String> rybhList = new ArrayList<>();
                    JSONArray data = null;
                    try {
                        data = get.getJSONArray("data");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResultUtil.ReturnSuccess(rybhList);
                    }
                    for (int i = 0; i < data.size(); ++i) {
                        rybhList.add(data.getJSONObject(i).getString("rybh"));
                    }
                    return ResultUtil.ReturnSuccess(rybhList);
                } else {
                    return ResultUtil.ReturnError(get.getString("message"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("速裁平台接口获取人员列表出错", e);
        }
        return ResultUtil.ReturnError("速裁平台接口获取人员列表出错");
    }

    private ApiReturnResult<String> getScRyxxBysfzh(String sfzh) {
        if (StringUtils.isEmpty(sfzh)) {
            return ResultUtil.ReturnError("身份证号不能为空");
        }
        String url = SysUtil.getParamValue("scryxx_url");
        if (StringUtils.isEmpty(url)) {
            return ResultUtil.ReturnError("未配置速裁平台接口地址");
        }
        try {
            JSONObject get = HttpClientUtils.httpGet(url + "agxt/api/yyxx/getRyxxByXyrsfz?xyrsfz=" + sfzh);
            if (get != null) {
                int code = get.getInteger("code");
                if (code == 0) {
                    return ResultUtil.ReturnSuccess(get.getString("message"));
                } else {
                    return ResultUtil.ReturnError(get.getString("message"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("速裁平台接口获取人员信息出错", e);
        }
        return ResultUtil.ReturnError("速裁平台接口获取人员信息出错");
    }
}
