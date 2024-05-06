package com.wckj.chasstage.modules.sxdp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ChasSxdp {
	
	private String id;
	
	private int isdel;
	
	private String dataFlag;
	
	private String lrrSfzh;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lrsj;
	
	private String xgrSfzh;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date xgsj;
	
	private String ddrwid;
	
	private String xyrxm;
	
	private String sxsbh;
	
	private String sxsmc;
	
	private String sxmjbh;
	
	private String sxmjxm;
	
	private String rwzt;
	
	private String xyr;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date zwdgsj;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sxsj;

	public String getId() {
		return id;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public String getLrrSfzh() {
		return lrrSfzh;
	}

	public Date getLrsj() {
		return lrsj;
	}

	public String getXgrSfzh() {
		return xgrSfzh;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public String getDdrwid() {
		return ddrwid;
	}

	public String getXyrxm() {
		return xyrxm;
	}

	public String getSxsbh() {
		return sxsbh;
	}

	public String getSxsmc() {
		return sxsmc;
	}

	public String getSxmjbh() {
		return sxmjbh;
	}

	public String getSxmjxm() {
		return sxmjxm;
	}

	public String getRwzt() {
		return rwzt;
	}

	public Date getZwdgsj() {
		return zwdgsj;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public void setLrrSfzh(String lrrSfzh) {
		this.lrrSfzh = lrrSfzh;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	public void setXgrSfzh(String xgrSfzh) {
		this.xgrSfzh = xgrSfzh;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public void setDdrwid(String ddrwid) {
		this.ddrwid = ddrwid;
	}

	public void setXyrxm(String xyrxm) {
		this.xyrxm = xyrxm;
	}

	public void setSxsbh(String sxsbh) {
		this.sxsbh = sxsbh;
	}

	public void setSxsmc(String sxsmc) {
		this.sxsmc = sxsmc;
	}

	public void setSxmjbh(String sxmjbh) {
		this.sxmjbh = sxmjbh;
	}

	public void setSxmjxm(String sxmjxm) {
		this.sxmjxm = sxmjxm;
	}

	public void setRwzt(String rwzt) {
		this.rwzt = rwzt;
	}

	public void setZwdgsj(Date zwdgsj) {
		this.zwdgsj = zwdgsj;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public String getXyr() {
		return xyr;
	}

	public void setXyr(String xyr) {
		this.xyr = xyr;
	}

	public Date getSxsj() {
		return sxsj;
	}

	public void setSxsj(Date sxsj) {
		this.sxsj = sxsj;
	}
	
	

}
