package com.wckj.chasstage.modules.qqdh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 亲情电话
 */
public class ChasQqyz {
    /**
     * 主键
     */
    private String id;
    /**
     * 逻辑删除
     */
    private Integer isdel;
    /**
     * 版本
     */
    private String dataflag;
    /**
     * 录入人身份证号
     */
    private String lrrSfzh;
    /**
     * 录入时间
     */
    private Date lrsj;
    /**
     * 修改人身份证号
     */
    private String xgrSfzh;
    /**
     * 修改时间
     */
    private Date xgsj;
    /**
     * 办案区
     */
    private String baqid;
    /**
     * 办案区名称
     */
    private String baqmc;
    /**
     * 人员ID
     */
    private String ryid;
    /**
     * 腕带编号（未使用）
     */
    private String wdbh;
    /**
     * 状态 默认---0
     */
    private Integer zt;
    /**
     * 联系人姓名
     */
    private String lxr;
    /**
     * 申请通话时长 单位分钟
     */
    private Integer thsj;
    /**
     * 审批人员
     */
    private String spr;
    /**
     * 审批时间
     */
    private Date spsj;
    /**
     * 审批意见
     */
    private String spyj;
    /**
     * 通话开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date thkssj;
    /**
     * 通话结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date thjssj;
    /**
     * 人员编号
     */
    private String rybh;
    /**
     * 联系人电话
     */
    private String lxrdh;
    /**
     * 申请原因
     */
    private String sqyy;
    //
    /**
     * 申请人姓名
     */
    private String sqrxm;
    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sqsj;
    /**
     * 性别
     */
    private String xb;
    /**
     * 单位系统编号
     */
    private String dwxtbh;
    /**
     * 主办单位编号
     */
	private String zbdwbh;
    /**
     * 主办单位名称
     */
    private String zbdwmc;
    /**
     * 录音文件
     */
    private String lywj;
    private Integer[] ztList;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    public String getDwxtbh() {
		return dwxtbh;
	}
	public void setDwxtbh(String dwxtbh) {
		 this.dwxtbh = dwxtbh == null ? null : dwxtbh.trim();
	}
	public String getZbdwbh() {
		return zbdwbh;
	}
	public void setZbdwbh(String zbdwbh) {
		 this.zbdwbh = zbdwbh == null ? null : zbdwbh.trim();
	}
	public String getZbdwmc() {
		return zbdwmc;
	}
	public void setZbdwmc(String zbdwmc) {
		 this.zbdwmc = zbdwmc == null ? null : zbdwmc.trim();
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
    public String getRyid() {
        return ryid;
    }
    public void setRyid(String ryid) {
        this.ryid = ryid == null ? null : ryid.trim();
    }
    public String getWdbh() {
        return wdbh;
    }
    public void setWdbh(String wdbh) {
        this.wdbh = wdbh == null ? null : wdbh.trim();
    }
    public Integer getZt() {
        return zt;
    }
    public void setZt(Integer zt) {
        this.zt = zt;
    }
    public String getLxr() {
        return lxr;
    }
    public void setLxr(String lxr) {
        this.lxr = lxr == null ? null : lxr.trim();
    }
    public Integer getThsj() {
        return thsj;
    }
    public void setThsj(Integer thsj) {
        this.thsj = thsj;
    }
    public String getSpr() {
        return spr;
    }
    public void setSpr(String spr) {
        this.spr = spr == null ? null : spr.trim();
    }
    public Date getSpsj() {
        return spsj;
    }
    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }
    public Date getThkssj() {
        return thkssj;
    }
    public void setThkssj(Date thkssj) {
        this.thkssj = thkssj;
    }
    public Date getThjssj() {
        return thjssj;
    }
    public void setThjssj(Date thjssj) {
        this.thjssj = thjssj;
    }
    public String getRybh() {
        return rybh;
    }
    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }
    public String getLxrdh() {
        return lxrdh;
    }
    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh == null ? null : lxrdh.trim();
    }
    public String getSqyy() {
        return sqyy;
    }
    public void setSqyy(String sqyy) {
        this.sqyy = sqyy == null ? null : sqyy.trim();
    }
    public String getSqrxm() {
        return sqrxm;
    }
    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm == null ? null : sqrxm.trim();
    }
    public Date getSqsj() {
        return sqsj;
    }
    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }
    public String getXb() {
        return xb;
    }
    public void setXb(String xb) {
        this.xb = xb == null ? null : xb.trim();
    }
    public Integer[] getZtList() {
        return ztList;
    }
    public void setZtList(Integer[] ztList) {
        this.ztList = ztList;
    }

    public String getLywj() {
        return lywj;
    }

    public void setLywj(String lywj) {
        this.lywj = lywj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }
}