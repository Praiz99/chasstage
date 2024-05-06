package com.wckj.chasstage.api.def.yybb.model;

/**
 * @author wutl
 * @Title: 语音播报发送内容
 * @Package
 * @Description:
 * @date 2020-12-2219:35
 */
public class YybbSendParam {

    /**
     * 人员姓名
     */
    private String ryxm;
    /**
     * 民警姓名
     */
    private String mjxm;
    /**
     * 等候室名称
     */
    private String dhsmc;
    /**
     * 审讯室名称
     */
    private String sxsmc;
    /**
     * 办案区id
     */
    private String baqid;
    /**
     * 播报环节
     */
    private YyhjEnue bbhj;

    public YyhjEnue getBbhj() {
        return bbhj;
    }

    public void setBbhj(YyhjEnue bbhj) {
        this.bbhj = bbhj;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getDhsmc() {
        return dhsmc;
    }

    public void setDhsmc(String dhsmc) {
        this.dhsmc = dhsmc;
    }

    public String getSxsmc() {
        return sxsmc;
    }

    public void setSxsmc(String sxsmc) {
        this.sxsmc = sxsmc;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

}
