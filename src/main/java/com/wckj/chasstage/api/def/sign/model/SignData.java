package com.wckj.chasstage.api.def.sign.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class SignData {

    @ApiModelProperty("签名布尔数组（签名捺印对应的状态  为 true 就是已签名）")
    private boolean[] qmb;
    @ApiModelProperty("签名提示数组 签名的中文提示")
    private String[] qmts;
    @ApiModelProperty("签名标识数组  每一个签名对应的标识")
    private String[] qmType;
    @ApiModelProperty("签名图片数据数组  每一个签名对应的图片数据")
    private String[] qmData;
    @ApiModelProperty("当前需要签名的数组下标  与签名标识数组对应")
    private Integer[] config_qm;
    @ApiModelProperty("当前需要捺印的数组下标  与签名标识数组对应")
    private Integer[] config_ny;
    @ApiModelProperty("当前需要签名详细信息集合")
    private List<Map<String,Object>> ListSignInfo;
    @ApiModelProperty("当前需要捺印详细信息集合")
    private List<Map<String,Object>> ListFingerInfo;

    public boolean[] getQmb() {
        return qmb;
    }

    public void setQmb(boolean[] qmb) {
        this.qmb = qmb;
    }

    public String[] getQmts() {
        return qmts;
    }

    public void setQmts(String[] qmts) {
        this.qmts = qmts;
    }

    public String[] getQmType() {
        return qmType;
    }

    public void setQmType(String[] qmType) {
        this.qmType = qmType;
    }

    public String[] getQmData() {
        return qmData;
    }

    public void setQmData(String[] qmData) {
        this.qmData = qmData;
    }

    public Integer[] getConfig_qm() {
        return config_qm;
    }

    public void setConfig_qm(Integer[] config_qm) {
        this.config_qm = config_qm;
    }

    public Integer[] getConfig_ny() {
        return config_ny;
    }

    public void setConfig_ny(Integer[] config_ny) {
        this.config_ny = config_ny;
    }

    public List<Map<String, Object>> getListSignInfo() {
        return ListSignInfo;
    }

    public void setListSignInfo(List<Map<String, Object>> listSignInfo) {
        ListSignInfo = listSignInfo;
    }

    public List<Map<String, Object>> getListFingerInfo() {
        return ListFingerInfo;
    }

    public void setListFingerInfo(List<Map<String, Object>> listFingerInfo) {
        ListFingerInfo = listFingerInfo;
    }
}
