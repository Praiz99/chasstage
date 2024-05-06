package com.wckj.chasstage.common.util.file.service;

import com.wckj.chasstage.common.util.file.dto.FileParam;

/**
 * 办案区文件service
 */
public interface BaqFileService {
    /**
     * 上传文件
     * @param param
     * @return
     */
    boolean uploadFile(FileParam param);

    /**
     * 下载文件
     * @param param
     * @return
     */
    String  downloadFile(FileParam param);

    /**
     * 获取文件访问url
     * @param zpid
     * @return
     */
    String getFileUrl(String zpid);
    String  saveFile(String base64Str);
}
