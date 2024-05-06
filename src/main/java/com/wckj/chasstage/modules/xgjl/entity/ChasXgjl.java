package com.wckj.chasstage.modules.xgjl.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 巡更记录
 */
public class ChasXgjl {
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
	 * 打卡时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date dksj;
	/**
	 * 卡号
	 */
	private String kh;

	/**
	 * 设备编号
	 */
	private String sbbh;
	/**
	 * 区域id
	 */
	private String qyid;

	/**
	 * 区域名称
	 */
	private String qymc;
	/**
	 * 设备名称
	 */
	private String sbmc;
	private String ycqk;
	public ChasXgjl() {
		this.isdel = 0;
		this.dataflag = "0";
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


	public Date getDksj() {
		return dksj;
	}


	public void setDksj(Date dksj) {
		this.dksj = dksj;
	}


	public String getKh() {
		return kh;
	}


	public void setKh(String kh) {
		this.kh = kh == null ? null : kh.trim();
	}


	public String getSbbh() {
		return sbbh;
	}


	public void setSbbh(String sbbh) {
		this.sbbh = sbbh == null ? null : sbbh.trim();
	}


	public String getQyid() {
		return qyid;
	}


	public void setQyid(String qyid) {
		this.qyid = qyid == null ? null : qyid.trim();
	}


	public String getQymc() {
		return qymc;
	}


	public void setQymc(String qymc) {
		this.qymc = qymc == null ? null : qymc.trim();
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc == null ? null : sbmc.trim();
	}

	public String getYcqk() {
		return ycqk;
	}

	public void setYcqk(String ycqk) {
		this.ycqk = ycqk;
	}
}