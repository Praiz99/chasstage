package com.wckj.chasstage.common.util.face.service;

/**
 * @author wutl
 * @Title: 人脸服务调用（新）
 * @Package
 * @Description:
 * @date 2020-12-1514:51
 */
public interface FaceInvokeService {

    /**
     *
     * @param bizId 照片bizId
     * @param baqid 办案区id
     * @param dwxtbh 嫌疑人主办单位系统编号
     */
    void saveBaqryFeature(String bizId, String baqid, String dwxtbh,String tzbh);


    /**
     *
     * @param bizId 照片bizId
     * @param dwxtbh 嫌疑人主办单位系统编号
     */
    boolean saveMjFeature(String bizId, String dwxtbh, String tzbh);

    /**
     * 根据办案区id删除特征
     * @param baqid
     * @param tzbh
     * @param tzlx
     */
    void deleteFeatureByTzbh(String baqid,String tzbh,String tzlx);
}
