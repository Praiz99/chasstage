package com.wckj.chasstage.api.server.imp.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.face.model.FaceResult;
import com.wckj.chasstage.api.def.face.model.RecognitionParam;
import com.wckj.chasstage.api.def.face.model.RegisterParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.common.util.HttpClientUtils;
import com.wckj.chasstage.common.util.JSONHelper;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.taskClient.util.HttpClientUtil;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author wutl
 * @Title: 办案区人脸服务接口
 * @Package
 * @Description:
 * @date 2020-12-211:16
 */
@Service
public class BaqFaceServiceImp implements BaqFaceService {

    private static Logger logger = LoggerFactory.getLogger(BaqFaceServiceImp.class);
    @Autowired
    private ChasXtBaqznpzService chasXtBaqznpzService;

    /**
     * 人脸注册
     * @param registerParam
     * @return
     */
    @Override
    public boolean register(RegisterParam registerParam) {
        String baqrlsburl = getFaceUrlByBaqid(registerParam.getBaqid());
        if (StringUtils.isEmpty(baqrlsburl)) {
            //没有配置URL 默认注册成功。其实不生成数据
            logger.debug("当前办案区【"+registerParam.getBaqid()+"】没有配置人脸服务，不进行注册。");
            return false;
        }
        String base64Str = null;
        if (StringUtils.isEmpty(registerParam.getBase64Str())) {
            String bizId = registerParam.getBizId();
            IByteFileObj iByteFileObj = FrwsApiForThirdPart.downloadByteFileByBizId(bizId);
            if (iByteFileObj == null) {
                logger.error("人脸注册，获取bizId[" + bizId + "]文件不存在");
                return false;
            }
            byte[] bytes = iByteFileObj.getBytes();
            base64Str = Base64.getEncoder().encodeToString(bytes);
        } else {
            base64Str = registerParam.getBase64Str();
        }
        if (StringUtils.isEmpty(baqrlsburl)) {
            logger.error("办案区未配置人脸识别服务地址");
            return false;
        }

//        Part[] parts = new Part[5];
//        parts[0] = new StringPart("baqid", registerParam.getBaqid());
//        parts[1] = new StringPart("tzlx", registerParam.getTzlx());
//        parts[2] = new StringPart("dwxtbh", registerParam.getDwxtbh());
//        parts[3] = new StringPart("tzbh", registerParam.getTzbh());
//        parts[4] = new StringPart("base64Str", base64Str);
        Map<String, String> params = new HashMap<>();
        params.put("baqid", registerParam.getBaqid());
        params.put("tzlx", registerParam.getTzlx());
        params.put("dwxtbh", registerParam.getDwxtbh());
        params.put("tzbh", registerParam.getTzbh());
        params.put("base64Str", base64Str);
        params.put("appid", "chasstage");
        try {
//            String body = HttpClientUtil.postMethod(baqrlsburl+"/face/face/register", parts);
//            HashMap hashMap = JSONHelper.toHashMap(body);
//            Object code = hashMap.get("code");
//            if (code != null && "0".equals(code.toString())) {
//                return true;
//            } else {
//                Object message = hashMap.get("message");
//                if (message != null) {
//                    logger.error("人脸识别结果，" + message);
//                }
//                return false;
//            }
            JSONObject jsonObject = HttpClientUtils.httpPostForm(baqrlsburl + "/face/register", params);
            logger.info("人脸注册接口返回内容：" + jsonObject);
            if(!ObjectUtils.isEmpty(jsonObject)){
                if(0 == jsonObject.getInteger("code")){
                    return true;
                }else{
                    logger.error("人脸识别结果，" + jsonObject.getString("message"));
                    return false;
                }
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("办案区未配置人脸识别服务请求异常", e);
            return false;
        }
    }

    /**
     * 人脸识别
     * @param recognitionParam
     * @return
     */
    @Override
    public List<FaceResult> recognition(RecognitionParam recognitionParam) {
        String baqrlsburl = getFaceUrlByBaqid(recognitionParam.getBaqid());
        if (StringUtils.isEmpty(baqrlsburl)) {
            throw new BizDataException("该办案区【"+recognitionParam.getBaqid()+"】没有配置人脸服务！");
        }
        String base64Str = null;
        if (StringUtils.isEmpty(recognitionParam.getBase64Str())) {
            String bizId = recognitionParam.getBizId();
            IByteFileObj iByteFileObj = FrwsApiForThirdPart.downloadByteFileByBizId(bizId);
            if (iByteFileObj == null) {
                logger.error("人脸注册，获取bizId[" + bizId + "]文件不存在");
                return null;
            }
            byte[] bytes = iByteFileObj.getBytes();
            base64Str = Base64.getEncoder().encodeToString(bytes);
        } else {
            base64Str = recognitionParam.getBase64Str();
        }
//        Part[] parts = new Part[8];
//        parts[0] = new StringPart("dwxtbh", recognitionParam.getDwxtbh());
//        parts[1] = new StringPart("qxch", "");
//        parts[2] = new StringPart("tzlx", recognitionParam.getTzlx());
//        parts[3] = new StringPart("baqid", recognitionParam.getBaqid());
//        parts[4] = new StringPart("cxkssj", "");
//        parts[5] = new StringPart("cxjssj", "");
//        parts[6] = new StringPart("sfcxls", "");
//        parts[7] = new StringPart("base64Str", base64Str);
        Map<String, String> params = new HashMap<>();
        params.put("dwxtbh", recognitionParam.getDwxtbh());
        params.put("tzlx", recognitionParam.getTzlx());
        params.put("baqid", recognitionParam.getBaqid());
        params.put("base64Str", base64Str);
        params.put("sfcxls", recognitionParam.getSfcxls());
        params.put("appid", "chasstage");
        try {
            //String body = HttpClientUtil.postMethod(baqrlsburl + "/face/face/recognition", parts);
            JSONObject jsonObject = HttpClientUtils.httpPostForm(baqrlsburl + "/face/recognition", params);
            logger.info("人脸识别接口返回内容：" + jsonObject);
//            if(StringUtils.isNotEmpty(body)){
//                JSONObject response = JSON.parseObject(body);
//                if(0 == response.getInteger("code")){
//                    List<FaceResult> faceResultList = JSON.parseArray(response.getString("data"), FaceResult.class);
//                    return faceResultList;
//                }else{
//                    return null;
//                }
//            }else{
//                return null;
//            }
            if(!ObjectUtils.isEmpty(jsonObject)){
                if(0 == jsonObject.getInteger("code")){
                    List<FaceResult> faceResultList = JSON.parseArray(jsonObject.getString("data"), FaceResult.class);
                    return faceResultList;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("办案区未配置人脸识别服务请求异常", e);
            return null;
        }
    }

    /**
     * 人证比对
     * @param baqid
     * @param destStr
     * @param origStr
     * @return
     */
    @Override
    public Integer rzbd(String baqid,String destStr, String origStr) {
        String baqrlsburl = getFaceUrlByBaqid(baqid);
        if (StringUtils.isEmpty(baqrlsburl)) {
            throw new BizDataException("该办案区【"+baqid+"】没有配置人脸服务！");
        }
        try {
//            Part[] parts = new Part[2];
//            parts[0] = new StringPart("dataOrig",origStr);
//            parts[1] = new StringPart("dataDest", destStr);
            Map<String, String> params = new HashMap<>();
            params.put("dataOrig", origStr);
            params.put("dataDest", destStr);
//            String body = HttpClientUtil.postMethod(baqrlsburl+"/rzdb/file", parts);
//            HashMap hashMap = JSONHelper.toHashMap(body);
//            Object code = hashMap.get("code");
//            if (code != null && "0".equals(code.toString())) {
//                Integer data = (Integer) hashMap.get("data");
//                return data;
//            } else {
//                Object message = hashMap.get("message");
//                if (message != null) {
//                    logger.error("人脸识别结果，" + message);
//                }
//                return -1;
//            }
            JSONObject jsonObject = HttpClientUtils.httpPostForm(baqrlsburl + "/rzdb/file", params);
            logger.info("人证比对接口返回内容：" + jsonObject);
            if(!ObjectUtils.isEmpty(jsonObject)){
                if(0 == jsonObject.getInteger("code")){
                    Integer data = jsonObject.getInteger("data");
                    return data;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("办案区未配置人脸识别服务请求异常", e);
            return -1;
        }
    }

    /**
     * 删除人脸特征
     * @param baqid
     * @param tzbh
     * @param tzlx
     * @return
     */
    @Override
    public boolean delete(String baqid,String tzbh, String tzlx) {
        String baqrlsburl = getFaceUrlByBaqid(baqid);
        if (StringUtils.isEmpty(baqrlsburl)) {
            logger.error("当前办案区【"+baqid+"】没有配置人脸服务");
            return false;
        }
        try {
//            Part[] parts = new Part[2];
//            parts[0] = new StringPart("tzlx",tzlx);
//            parts[1] = new StringPart("tzbh", tzbh);
            Map<String, String> params = new HashMap<>();
            params.put("tzlx", tzlx);
            params.put("tzbh", tzbh);
//            String body = HttpClientUtil.postMethod(baqrlsburl+"/face/delete", parts);
//            logger.debug("人脸删除接口返回内容：" + body);
//            HashMap hashMap = JSONHelper.toHashMap(body);
//            Object code = hashMap.get("code");
//            if (code != null && "0".equals(code.toString())) {
//                return true;
//            } else {
//                Object message = hashMap.get("message");
//                if (message != null) {
//                    logger.error("人脸识别结果，" + message);
//                }
//                return false;
//            }
            JSONObject jsonObject = HttpClientUtils.httpPostForm(baqrlsburl + "/face/delete", params);
            logger.info("人脸特征删除接口返回内容：" + jsonObject);
            if(!ObjectUtils.isEmpty(jsonObject)){
                if(0 == jsonObject.getInteger("code")){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("办案区未配置人脸识别服务请求异常", e);
            return false;
        }
    }

    /**
     * 根据办案区获取办案区人脸服务地址
     * @param baqid
     * @return
     */
    public String getFaceUrlByBaqid(String baqid){
        ChasXtBaqznpz baqznpz = chasXtBaqznpzService.findByBaqid2(baqid);
        if (baqznpz == null) {
            logger.error("人脸识别地址查询,办案区id为空");
            return null;
        }
        String baqrlsburl = baqznpz.getBaqConfiguration().getBaqrlsburl();
        if(StringUtils.isEmpty(baqrlsburl) || "null".equals(baqrlsburl)){
            return null;
        }
        return baqrlsburl;
    }
}
