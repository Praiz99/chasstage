package com.wckj.chasstage.api.def.dhsgl.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 等候室预分配结果，是否出现同案、混关等异常分配，用于等候室调整
 */
public class DhsYfpBean {
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "描述信息")
    private String message;
    @ApiModelProperty(value = "分配成功时，附加信息")
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static DhsYfpBean of(Integer code, String message){
        DhsYfpBean bean = new DhsYfpBean();
        bean.setCode(code);
        bean.setMessage(message);
        return bean;
    }
}
