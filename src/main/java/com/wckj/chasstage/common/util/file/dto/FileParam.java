package com.wckj.chasstage.common.util.file.dto;

public class FileParam {
    /**
     * dwxtbh
     */
    private String dwxtbh;
    /**
     * bizId
     */
    private String bizId;
    /**
     * bizType
     */
    private String bizType;
    /**
     * 文件数据
     */
    private byte[] data;
    /**
     * 保存文件路径
     */
    private String filePath;
    /**
     * 保存至文件服务器（本地）文件名
     */
    private String fileName;

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
