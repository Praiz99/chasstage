package com.wckj.chasstage.api.def.face.model;


/**
 * 人脸识别结果实体类
 */
public class FaceResult {
    //单位系统编号
    private String dwxtbh;
    //特征类型
    private String tzlx;
    //特征编号
    private String tzbh;
    //分数
    private Integer score;
    //办案区id
    private String baqid;

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getTzlx() {
        return tzlx;
    }

    public void setTzlx(String tzlx) {
        this.tzlx = tzlx;
    }

    public String getTzbh() {
        return tzbh;
    }

    public void setTzbh(String tzbh) {
        this.tzbh = tzbh;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }
}
