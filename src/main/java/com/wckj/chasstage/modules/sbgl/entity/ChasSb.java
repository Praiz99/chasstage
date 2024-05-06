package com.wckj.chasstage.modules.sbgl.entity;

import java.util.Date;

/**
 * 设备
 */
public class ChasSb implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 6621082367624208119L;

    /**
     *id
     */
    private String id;

    /**
     *数据是否逻辑删除
     */
    private Integer isdel;

    /**
     *数据标识
     */
    private String dataflag;

    /**
     *录入人身份证号
     */
    private String lrrSfzh;

    /**
     *录入时间
     */
    private Date lrsj;

    /**
     *修改人身份证号
     */
    private String xgrSfzh;

    /**
     *修改时间
     */
    private Date xgsj;

    /**
     *办案区id
     */
    private String baqid;

    /**
     *办案区名称
     */
    private String baqmc;

    /**
     *区域id
     */
    private String qyid;

    /**
     *设备类型
     */
    private String sblx;

    /**
     *设备编号
     */
    private String sbbh;

    private String qymc;


    /**
     *设备名称
     */
    private String sbmc;

    /**
     *设备功能
     */
    private String sbgn;
    /**
     * 设备状态(0,关闭，1，打开)
     */
    private Integer sbzt;

    private String kzcs1;

    private String kzcs2;
    private String kzcs3;
    private String kzcs4;
    private String kzcs5;
    //目前用于随身物品摄像头关联岗位编号
    private String kzcs6;
    private String wlzt;
    private String gzzt;

    //设备故障描述
    private String sbgzms;
    //是否在线
    private String sfzx;
    //是否丢包
    private String sfdb;
    //监控接口
    private String jkjk;
    //厂商电话
    private String csdh;
    public ChasSb(){
        this.isdel=0;
        this.dataflag="0";
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public Integer getIsdel() {
        return isdel;
    }


    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }


    public String getDataflag() {
        return dataflag;
    }


    public void setDataflag(String dataflag) {
        this.dataflag = dataflag == null ? null : dataflag.trim();
    }


    public String getLrrSfzh() {
        return lrrSfzh;
    }


    public void setLrrSfzh(String lrrSfzh) {
        this.lrrSfzh = lrrSfzh == null ? null : lrrSfzh.trim();
    }


    public Date getLrsj() {
        return lrsj;
    }


    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }


    public String getXgrSfzh() {
        return xgrSfzh;
    }


    public void setXgrSfzh(String xgrSfzh) {
        this.xgrSfzh = xgrSfzh == null ? null : xgrSfzh.trim();
    }


    public Date getXgsj() {
        return xgsj;
    }


    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }


    public String getBaqid() {
        return baqid;
    }


    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }


    public String getBaqmc() {
        return baqmc;
    }


    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc == null ? null : baqmc.trim();
    }


    public String getQyid() {
        return qyid;
    }


    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }


    public String getSblx() {
        return sblx;
    }


    public void setSblx(String sblx) {
        this.sblx = sblx == null ? null : sblx.trim();
    }


    public String getSbbh() {
        return sbbh;
    }


    public void setSbbh(String sbbh) {
        this.sbbh = sbbh == null ? null : sbbh.trim();
    }


    public String getSbmc() {
        return sbmc;
    }


    public void setSbmc(String sbmc) {
        this.sbmc = sbmc == null ? null : sbmc.trim();
    }


    public String getSbgn() {
        return sbgn;
    }

    public void setSbgn(String sbgn) {
        this.sbgn = sbgn == null ? null : sbgn.trim();
    }
    public Integer getSbzt() {
        return sbzt;
    }

    public void setSbzt(Integer sbzt) {
        this.sbzt = sbzt;
    }

    public String getKzcs1() {
        return kzcs1;
    }

    public void setKzcs1(String kzcs1) {
        this.kzcs1 = kzcs1;
    }

    public String getKzcs2() {
        return kzcs2;
    }

    public void setKzcs2(String kzcs2) {
        this.kzcs2 = kzcs2;
    }

    public String getKzcs3() {
        return kzcs3;
    }

    public void setKzcs3(String kzcs3) {
        this.kzcs3 = kzcs3;
    }

    public String getKzcs4() {
        return kzcs4;
    }

    public void setKzcs4(String kzcs4) {
        this.kzcs4 = kzcs4;
    }

    public String getKzcs5() {
        return kzcs5;
    }

    public void setKzcs5(String kzcs5) {
        this.kzcs5 = kzcs5;
    }

    public String getKzcs6() {
        return kzcs6;
    }

    public void setKzcs6(String kzcs6) {
        this.kzcs6 = kzcs6;
    }

    public String getWlzt() {
        return wlzt;
    }

    public void setWlzt(String wlzt) {
        this.wlzt = wlzt;
    }

    public String getGzzt() {
        return gzzt;
    }

    public void setGzzt(String gzzt) {
        this.gzzt = gzzt;
    }

    public String getSbgzms() { return sbgzms; }

    public void setSbgzms(String sbgzms) { this.sbgzms = sbgzms; }

    public String getSfzx() { return sfzx; }

    public void setSfzx(String sfzx) { this.sfzx = sfzx; }

    public String getSfdb() { return sfdb; }

    public void setSfdb(String sfdb) { this.sfdb = sfdb; }

    public String getJkjk() { return jkjk; }

    public void setJkjk(String jkjk) { this.jkjk = jkjk; }

    public String getCsdh() {
        return csdh;
    }

    public void setCsdh(String csdh) {
        this.csdh = csdh;
    }
}
