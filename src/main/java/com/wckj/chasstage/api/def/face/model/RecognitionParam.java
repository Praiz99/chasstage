package com.wckj.chasstage.api.def.face.model;

/**
 * @author wutl
 * @Title: 人脸识别参数
 * @Package
 * @Description:
 * @date 2020-12-211:25
 */
public class RecognitionParam {

    private String dwxtbh;
    private String qxch = "0";
    private String tzlx;
    //识别办案区Id
    private String baqid;
    private String cxkssj;
    private String cxjssj;
    private String sfcxls;
    private String base64Str;
    private String bizId;

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getQxch() {
        return qxch;
    }

    public void setQxch(String qxch) {
        this.qxch = qxch;
    }

    public String getTzlx() {
        return tzlx;
    }

    public void setTzlx(String tzlx) {
        this.tzlx = tzlx;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getCxkssj() {
        return cxkssj;
    }

    public void setCxkssj(String cxkssj) {
        this.cxkssj = cxkssj;
    }

    public String getCxjssj() {
        return cxjssj;
    }

    public void setCxjssj(String cxjssj) {
        this.cxjssj = cxjssj;
    }

    public String getSfcxls() {
        return sfcxls;
    }

    public void setSfcxls(String sfcxls) {
        this.sfcxls = sfcxls;
    }

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
}
