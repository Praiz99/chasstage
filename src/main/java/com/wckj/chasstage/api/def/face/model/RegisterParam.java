package com.wckj.chasstage.api.def.face.model;

/**
 * @author wutl
 * @Title: 注册参数
 * @Package
 * @Description:
 * @date 2020-12-211:25
 */
public class RegisterParam {
    private String bizId;
    //注册办案区Id
    private String baqid;
    private String tzlx;
    private String dwxtbh;
    private String tzbh;
    private String base64Str;

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getTzlx() {
        return tzlx;
    }

    public void setTzlx(String tzlx) {
        this.tzlx = tzlx;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getTzbh() {
        return tzbh;
    }

    public void setTzbh(String tzbh) {
        this.tzbh = tzbh;
    }
}
