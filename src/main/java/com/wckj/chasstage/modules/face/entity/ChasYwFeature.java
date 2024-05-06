package com.wckj.chasstage.modules.face.entity;

import java.util.Date;

/**
 * 人脸特征
 */
public class ChasYwFeature {
    /**
     * 主键 同 tzbh
     */
    private String id;
    /**
     * 特征编号
     */
    private String tzbh;
    /**
     * 人员类型
     */
    private String rylx;
    /**
     * 人脸数量默认1
     */
    private Integer num;
    /**
     * 特征数据长度
     */
    private Long length;
    /**
     * 特征数据base64字符串
     */
    private String data;
    /**
     * 单位系统编号
     */
    private String dwxtbh;
    /**
     * 录入时间
     */
    private Date lrsj;
    /**
     * 修改时间
     */
    private Date xgsj;
    //临时保存比对分数，不保存到数据中
    private int score=0;

    /**
     * 是否删除
     */
    private Integer isdel;



    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public Date getLrsj() {
        return lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}
