package com.wckj.chasstage.modules.wd.entity;

import java.util.Date;

/**
 * 腕带(标签、手环)
 */
public class ChasWd {
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
	 *修改时间
	 */
	private Date xgsj;

	/**
	 *办案区
	 */
	private String baqid;

	/**
	 *办案区名称
	 */
	private String baqmc;

	/**
	 *高频编号
	 */
	private String wdbhH;

	/**
	 *低频编号
	 */
	private String wdbhL;

	/**
	 * 原始id (dc系统数据库主键)
	 */
	private String ysid;
	/**
	 * 类型 1手环 2胸卡
	 */
	private String lx;
	
	public ChasWd() {
		this.isdel = 0;
		this.dataflag = "0";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		this.dataflag = dataflag;
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

	public String getWdbhH() {
		return wdbhH;
	}

	public void setWdbhH(String wdbhH) {
		this.wdbhH = wdbhH;
	}

	public String getWdbhL() {
		return wdbhL;
	}

	public void setWdbhL(String wdbhL) {
		this.wdbhL = wdbhL;
	}

	public String getYsid() {
		return ysid;
	}

	public void setYsid(String ysid) {
		this.ysid = ysid;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}
}