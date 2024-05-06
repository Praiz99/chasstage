package com.wckj.chasstage.common.util.face.dto;

/**
 * 生成人脸特征码结果
 */
public class FeatureResult {
    /**
     * 错误码
     *  0 成功 非0失败
     */
    private Integer code;
    /**
     * 描述信息
     */
    private String message;

    public FeatureResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
