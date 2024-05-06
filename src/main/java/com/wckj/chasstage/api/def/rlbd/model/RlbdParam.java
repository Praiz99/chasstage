package com.wckj.chasstage.api.def.rlbd.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wutl
 * @Title: 人脸比对参数
 * @Package
 * @Description:
 * @date 2020-12-1711:28
 */
public class RlbdParam {

    @ApiModelProperty(value = "人脸平台：zz 众智，hk 海康，依图 yt,jwbd 警务百度")
    private String type;
    @ApiModelProperty(value = "办案区Id(必传)")
    private String baqid;
    @ApiModelProperty(value = "人员姓名")
    private String xm;
    @ApiModelProperty(value = "人员身份证")
    private String sfzh;
    @ApiModelProperty(value = "人员照片base64（和照片id二选一）")
    private String base64;
    @ApiModelProperty(value = "民警身份证")
    private String zjhm;
    @ApiModelProperty(value = "照片id（和人员照片base64二选一）")
    private String bizId;
    @ApiModelProperty(value = "照片类型（bizType，传照片id的时候必传）")
    private String bizType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
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
}
