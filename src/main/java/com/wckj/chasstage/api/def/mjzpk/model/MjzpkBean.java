package com.wckj.chasstage.api.def.mjzpk.model;

import io.swagger.annotations.ApiModelProperty;


public class MjzpkBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("单位名称")
    private String dwmc;
    @ApiModelProperty("单位系统编号")
    private String dwxtbh;
    @ApiModelProperty("民警姓名")
    private String mjxm;
    @ApiModelProperty("民警身份证号")
    private String mjsfzh;
    @ApiModelProperty("民警警号")
    private String mjjh;
    @ApiModelProperty("照片id base64Str 二选一")
    private String zpid;
    @ApiModelProperty("联系人")
    private String lxr;
    @ApiModelProperty("工作单位")
    private String gzdw;
    @ApiModelProperty("备注")
    private String bz;
    @ApiModelProperty("联系电话")
    private String lxdh;
    @ApiModelProperty("民警图片数据base64字符串 zpid二选一")
    private String base64Str;
    @ApiModelProperty("审批状态")
    private String spzt;
    @ApiModelProperty("人员类型")
    private String rylx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getMjsfzh() {
        return mjsfzh;
    }

    public void setMjsfzh(String mjsfzh) {
        this.mjsfzh = mjsfzh;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }
}
