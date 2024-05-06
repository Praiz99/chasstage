package com.wckj.chasstage.common.util.face.dto;

/**
 * 人证比对结果
 */
public class RzbdResult {
    /**
     * 错误码
     *  0 成功 非0失败
     */
    private Integer code;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 匹配分数
     */
    private Integer score;
    public RzbdResult(Integer code, String message) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
