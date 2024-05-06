package com.wckj.chasstage.modules.jhrw.entity;

import java.util.Date;

/**
 * 戒护任务
 */
public class ChasYwJhrw {
    /**
     *id
     */
    private String id;

    /**
     *逻辑删除
     */
    private int isdel;

    /**
     *版本
     */
    private String dataFlag;

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
     * 人员姓名
     */
    private String ryxm;

    /**
     * 人员编号
     */
    private String rybh;

    /**
     * 入区原因
     */
    private String rqyy;

    /**
     * 入区时间
     */
    private Date rqsj;

    /**
     * 任务类型 01入区戒护 02出区戒护 03体检戒护 04审讯戒护
     * 05送押戒护 06审讯调度 07适格成年人调度
     */
    private String rwlx;

    /**
     * 任务状态 01待分配 02待执行 03执行中 04已执行
     */
    private String rwzt;

    /**
     * 监护人员
     */
    private String jhry;

    /**
     * 任务开始时间
     */
    private Date rwkssj;

    /**
     * 任务结束时间
     */
    private Date rwjssj;

    private Date sjbq;

    /**
     * 戒护任务起点
     */
    private String jhrwqd;

    /**
     * 戒护任务终点
     */
    private String jhrwzd;
    /**
     * 单位系统编号
     */
    private String dwxtbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getLrrSfzh() {
        return lrrSfzh;
    }

    public void setLrrSfzh(String lrrSfzh) {
        this.lrrSfzh = lrrSfzh;
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
        this.xgrSfzh = xgrSfzh;
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
        this.baqid = baqid;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRqyy() {
        return rqyy;
    }

    public void setRqyy(String rqyy) {
        this.rqyy = rqyy;
    }

    public Date getRqsj() {
        return rqsj;
    }

    public void setRqsj(Date rqsj) {
        this.rqsj = rqsj;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public String getRwzt() {
        return rwzt;
    }

    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }

    public String getJhry() {
        return jhry;
    }

    public void setJhry(String jhry) {
        this.jhry = jhry;
    }

    public Date getRwkssj() {
        return rwkssj;
    }

    public void setRwkssj(Date rwkssj) {
        this.rwkssj = rwkssj;
    }

    public Date getRwjssj() {
        return rwjssj;
    }

    public void setRwjssj(Date rwjssj) {
        this.rwjssj = rwjssj;
    }

    public String getRybh() { return rybh; }

    public void setRybh(String rybh) { this.rybh = rybh; }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }

    public String getJhrwqd() {
        return jhrwqd;
    }

    public void setJhrwqd(String jhrwqd) {
        this.jhrwqd = jhrwqd;
    }

    public String getJhrwzd() {
        return jhrwzd;
    }

    public void setJhrwzd(String jhrwzd) {
        this.jhrwzd = jhrwzd;
    }

    public String getDwxtbh() { return dwxtbh; }

    public void setDwxtbh(String dwxtbh) { this.dwxtbh = dwxtbh; }
}