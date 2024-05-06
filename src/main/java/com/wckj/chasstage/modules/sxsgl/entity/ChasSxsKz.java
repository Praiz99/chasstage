package com.wckj.chasstage.modules.sxsgl.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 审讯室使用情况
 */
public class ChasSxsKz {
    /**
     *主键
     */
    private String id;

    /**
     *逻辑删除
     */
    private Integer isdel;

    /**
     *版本
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
     * 修改时间
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
     *房间状态
     */
    private String fpzt;

    /**
     *电源状态
     */
    private String dyzt;

    /**
     *人员记录id
     */
    private String ryid;

    /**
     *开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date kssj;

    /**
     *使用民警
     */
    private String symj;

    /**
     *人员编号
     */
    private String rybh;
    /**
     * 人员姓名
     */
    private String ryxm;
    /**
     *区域id
     */
    private String qyid;
    /**
     * 继续用电
     */
    private String  jxyd;
    /**
     * 笔录核对时间
     */
    private Date hdsj;
    /**
     * 区域名称
     */
    private String qymc;


    private Date gbsj;

    private String rylx;

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public Date getGbsj() {
        return gbsj;
    }

    public void setGbsj(Date gbsj) {
        this.gbsj = gbsj;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm == null ? null : ryxm.trim();
    }

    public String getJxyd() {
        return jxyd;
    }

    public void setJxyd(String jxyd) {
        this.jxyd = jxyd == null ? null : jxyd.trim();
    }

    public Date getHdsj() {
        return hdsj;
    }

    public void setHdsj(Date hdsj) {
        this.hdsj = hdsj;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
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


    public String getFpzt() {
        return fpzt;
    }


    public void setFpzt(String fpzt) {
        this.fpzt = fpzt == null ? null : fpzt.trim();
    }


    public String getDyzt() {
        return dyzt;
    }


    public void setDyzt(String dyzt) {
        this.dyzt = dyzt == null ? null : dyzt.trim();
    }


    public String getRyid() {
        return ryid;
    }


    public void setRyid(String ryid) {
        this.ryid = ryid == null ? null : ryid.trim();
    }


    public Date getKssj() {
        return kssj;
    }


    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }


    public String getSymj() {
        return symj;
    }


    public void setSymj(String symj) {
        this.symj = symj == null ? null : symj.trim();
    }


    public String getRybh() {
        return rybh;
    }


    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }
}