package com.wckj.chasstage.modules.wpg.entity;

import java.util.Date;

/**
 * 随身物品柜组（）
 */
public class ChasSswpg {
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
	 *组名称
	 */
	private String mc;

	/**
	 *是否智能柜
	 */
	private Integer sfzng;

	/**
	 * 单位代码
	 */
	private String dwdm;
	/**
	 * 单位系统代码
	 */
	private String dwxtbh;
	/**
	 * 原始id （dc系统数据库主键）
	 */
	private String ysid;

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

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public Integer getSfzng() {
		return sfzng;
	}

	public void setSfzng(Integer sfzng) {
		this.sfzng = sfzng;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getDwxtbh() {
		return dwxtbh;
	}

	public void setDwxtbh(String dwxtbh) {
		this.dwxtbh = dwxtbh;
	}

	public String getYsid() {
		return ysid;
	}

	public void setYsid(String ysid) {
		this.ysid = ysid;
	}
}