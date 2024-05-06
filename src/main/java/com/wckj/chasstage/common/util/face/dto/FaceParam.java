package com.wckj.chasstage.common.util.face.dto;

import java.util.List;

/**
 * 人脸(注册、识别)业务相关数据
 */
public class FaceParam {
    public static final String RYLX_XYR="xyr";
    public static final String RYLX_MJ="mj";
    public static final String RYLX_FK="fk";
    /**
     * 特征编号，例如人员编号，民警身份证号
     */
    private String tzbh;
    /**
     * 人员类型 嫌疑人(xyr) 民警(mi) 访客(fk)
     */
    private String rylx;
    /**
     * 单位系统编号
     */
    private String dwxtbh;
    /**
     * 录入时间限制（24h以内）
     */
    private String lrsjxz;
    private List<String> rybhList;
    public FaceParam() {
    }

    public FaceParam(String tzbh, String rylx, String dwxtbh) {
        this.tzbh = tzbh;
        this.rylx = rylx;
        this.dwxtbh = dwxtbh;
    }

    public String getTzbh() {
        return tzbh;
    }

    public void setTzbh(String tzbh) {
        this.tzbh = tzbh;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getLrsjxz() {
        return lrsjxz;
    }

    public void setLrsjxz(String lrsjxz) {
        this.lrsjxz = lrsjxz;
    }

    public List<String> getRybhList() {
        return rybhList;
    }

    public void setRybhList(List<String> rybhList) {
        this.rybhList = rybhList;
    }
}
