package com.wckj.chasstage.api.def.face.service;


import com.wckj.chasstage.api.def.face.model.FaceResult;
import com.wckj.chasstage.api.def.face.model.RecognitionParam;
import com.wckj.chasstage.api.def.face.model.RegisterParam;

import java.util.List;

/**
 * @author wutl
 * @Title: 办案区人脸识别接口
 * @Package
 * @Description:
 * @date 2020-12-211:15
 */
public interface BaqFaceService {

    /**
     * 人脸注册
     * @param registerParam
     * @return
     */
    boolean register(RegisterParam registerParam);

    /**
     * 人脸识别
     * @param recognitionParam
     * @return
     */
    List<FaceResult> recognition(RecognitionParam recognitionParam);

    /**
     * 人证比对
     * @param destStr
     * @param origStr
     * @return 返回分数
     */
    Integer rzbd(String baqid,String destStr,String origStr);


    /**
     * 人证比对
     * @param tzbh
     * @param tzlx
     * @return 返回分数
     */
    boolean delete(String baqid,String tzbh,String tzlx);

}
