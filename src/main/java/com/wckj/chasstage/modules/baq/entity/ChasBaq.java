package com.wckj.chasstage.modules.baq.entity;

import scala.Serializable;

import java.util.Date;

/**
 * 办案区信息
 */
public class ChasBaq {
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
	 * 是否默认办案区
	 */
	private Integer isDefault;
	/**
	 * 办案区名称
	 */
	private String baqmc;

	/**
	 * 单位代码
	 */
	private String dwdm;

	/**
	 * 单位名称
	 */
	private String dwmc;

	/**
	 * 备注
	 */
	private String bz;


	/**
	 * DeviceCenter 系统 ip
	 */
	private String ip;

	/**
	 * DeviceCenter 系统 端口
	 */
	private String port;
	/**
	 * DeviceCenter 系统 系统名称
	 */
	private String xtmc;

	/**
	 * 是否定位
	 */
	private Integer sfdw;

	/**
	 * 是否智能办案区
	 */
	private Integer sfznbaq;

	private String sfdws;

	private String sfznbaqs;
	/**
	 * 单位系统编号
	 */
	private String dwxtbh;
	/**
	 * 联系电话
	 */
	private String lxdh;

	private String virtual;

	/**
	 * 是否大中心办案区
	 */
	private Integer sfdzx;

	public ChasBaq() {
		this.isdel = 0;
		this.dataflag = "0";
	}
	
	public String getDwxtbh() {
		return dwxtbh;
	}

	public void setDwxtbh(String dwxtbh) {
		this.dwxtbh = dwxtbh == null ? null : dwxtbh.trim();
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


	public Integer getIsDefault() {
		return isDefault;
	}


	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}


	public Date getXgsj() {
		return xgsj;
	}


	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}


	public String getBaqmc() {
		return baqmc;
	}


	public void setBaqmc(String baqmc) {
		this.baqmc = baqmc == null ? null : baqmc.trim();
	}


	public String getDwdm() {
		return dwdm;
	}


	public void setDwdm(String dwdm) {
		this.dwdm = dwdm == null ? null : dwdm.trim();
	}


	public String getDwmc() {
		return dwmc;
	}


	public void setDwmc(String dwmc) {
		this.dwmc = dwmc == null ? null : dwmc.trim();
	}


	public String getBz() {
		return bz;
	}


	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port == null ? null : port.trim();
	}


	public String getXtmc() {
		return xtmc;
	}


	public void setXtmc(String xtmc) {
		this.xtmc = xtmc == null ? null : xtmc.trim();
	}


	public Integer getSfdw() {
		return sfdw;
	}


	public void setSfdw(Integer sfdw) {
		this.sfdw = sfdw;
	}


	public Integer getSfznbaq() {
		return sfznbaq;
	}


	public void setSfznbaq(Integer sfznbaq) {
		this.sfznbaq = sfznbaq;
	}

	public String getSfdws() {
		return sfdws;
	}

	public void setSfdws(String sfdws) {
		this.sfdws = sfdws;
	}

	public String getSfznbaqs() {
		return sfznbaqs;
	}

	public void setSfznbaqs(String sfznbaqs) {
		this.sfznbaqs = sfznbaqs;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getVirtual() {
		return virtual;
	}

	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

	public Integer getSfdzx() {
		return sfdzx;
	}

	public void setSfdzx(Integer sfdzx) {
		this.sfdzx = sfdzx;
	}
}