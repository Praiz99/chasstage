package com.wckj.chasstage.modules.syrw.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ChasSyrw {
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 办案区id
	 */
	private String baq;
	/**
	 * 办案区名称
	 */
	private String baqmc;
	/**
	 * 嫌疑人编号
	 */
	private String xyrbh;
	/**
	 * 嫌疑人姓名
	 */
	private String xyrxm;
	/**
	 * 性别
	 */
	private String xb;
	/**
	 * 入区原因
	 */
	private String rqyy;
	/**
	 * 特殊群体
	 */
	private String tsqt;
	/**
	 * 案件类型
	 */
	private String ajlx;
	/**
	 * 主办民警
	 */
	private String zbmj;
	/**
	 * 出区原因
	 */
	private String cqyy;
	/**
	 * 送押状态
	 */
	private String syzt;
	/**
	 * 送押时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sysj;
	/**
	 * 送押人员编号(逗号隔开)
	 */
	private String syrybh;
	/**
	 * 送押人员姓名(逗号隔开)
	 */
	private String syry;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 申请人身份证号
	 */
	private String sqr;
	/**
	 * 申请人姓名
	 */
	private String sqrxm;
	/**
	 * 联系人
	 */
	private String lxr;
	/**
	 * 联系电话
	 */
	private String lxdh;
	
	public String getId() {
		return id;
	}

	public String getBaq() {
		return baq;
	}

	public String getXyrbh() {
		return xyrbh;
	}

	public String getXyrxm() {
		return xyrxm;
	}

	public String getXb() {
		return xb;
	}

	public String getRqyy() {
		return rqyy;
	}

	public String getTsqt() {
		return tsqt;
	}

	public String getAjlx() {
		return ajlx;
	}

	public String getZbmj() {
		return zbmj;
	}

	public String getCqyy() {
		return cqyy;
	}

	public String getSyzt() {
		return syzt;
	}

	public Date getSysj() {
		return sysj;
	}

	public String getSyrybh() {
		return syrybh;
	}

	public String getSyry() {
		return syry;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getSqr() {
		return sqr;
	}

	public String getSqrxm() {
		return sqrxm;
	}

	public String getLxr() {
		return lxr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBaq(String baq) {
		this.baq = baq;
	}

	public void setXyrbh(String xyrbh) {
		this.xyrbh = xyrbh;
	}

	public void setXyrxm(String xyrxm) {
		this.xyrxm = xyrxm;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public void setRqyy(String rqyy) {
		this.rqyy = rqyy;
	}

	public void setTsqt(String tsqt) {
		this.tsqt = tsqt;
	}

	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}

	public void setZbmj(String zbmj) {
		this.zbmj = zbmj;
	}

	public void setCqyy(String cqyy) {
		this.cqyy = cqyy;
	}

	public void setSyzt(String syzt) {
		this.syzt = syzt;
	}

	public void setSysj(Date sysj) {
		this.sysj = sysj;
	}

	public void setSyrybh(String syrybh) {
		this.syrybh = syrybh;
	}

	public void setSyry(String syry) {
		this.syry = syry;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public void setSqrxm(String sqrxm) {
		this.sqrxm = sqrxm;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getBaqmc() {
		return baqmc;
	}

	public void setBaqmc(String baqmc) {
		this.baqmc = baqmc;
	}
	
}
