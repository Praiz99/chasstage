package com.wckj.chasstage.api.def.face.service;

import com.wckj.chasstage.api.def.face.model.RecogParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;

/**
 * 人脸识别服务
 */
public interface ApiFaceService extends IApiBaseService {
    //人脸识别
    ApiReturnResult<?> recognition(RecogParam param);

    /**
     * 嫌疑人的身份核验(防误放)
     * @param baqid 办案区id
     * @param data 人脸base64
     * @return
     */
    ApiReturnResult<?> checkXyrInfo(String baqid, String data);

    /**
     * 嫌疑人识别（不分删除状态）
     * @param baqid
     * @param data
     * @param sblx
     * @return
     */
    ApiReturnResult<?> recognitionAll(String baqid, String data, String sblx);

    /**
     * 人证比对
     * @param faceStr
     * @param zjStr
     * @return
     */
    ApiReturnResult<String> rzbd(String faceStr, String zjStr);

    /**
     * 识别速裁人员
     * @param baqid
     * @param data
     * @param sblx
     * @return
     */
    ApiReturnResult<?> recognitionScry(String baqid, String data, String sblx);
}
