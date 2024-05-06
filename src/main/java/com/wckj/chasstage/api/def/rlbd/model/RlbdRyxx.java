package com.wckj.chasstage.api.def.rlbd.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 人脸比对常口库
 * @Package
 * @Description:
 * @date 2020-12-229:50
 */
public class RlbdRyxx {
    @ApiModelProperty(value = "姓名")
    private String xm;
    @ApiModelProperty(value = "身份证")
    private String sfzh;
    @ApiModelProperty(value = "出生日期")
    private String csrq;
    @ApiModelProperty(value = "户籍地详址")
    private String hzdxz;
    @ApiModelProperty(value = "民族")
    private String mz;
    @ApiModelProperty(value = "相似度")
    private String xxd;
    @ApiModelProperty(value = "年龄")
    private int age;
    @ApiModelProperty(value = "性别")
    private int sex;
    @ApiModelProperty(value = "性别名")
    private String sexName;
    @ApiModelProperty(value = "照片")
    private String facePicUrl;
    @ApiModelProperty(value = "照片base64")
    private String facePicData;

    public String getFacePicData() {
        return facePicData;
    }

    public void setFacePicData(String facePicData) {
        this.facePicData = facePicData;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
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

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getHzdxz() {
        return hzdxz;
    }

    public void setHzdxz(String hzdxz) {
        this.hzdxz = hzdxz;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getXxd() {
        return xxd;
    }

    public void setXxd(String xxd) {
        this.xxd = xxd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFacePicUrl() {
        return facePicUrl;
    }

    public void setFacePicUrl(String facePicUrl) {
        this.facePicUrl = facePicUrl;
    }
}
