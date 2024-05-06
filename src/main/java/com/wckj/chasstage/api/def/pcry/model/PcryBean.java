package com.wckj.chasstage.api.def.pcry.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PcryBean {
    /**
     * 主键
     */
    @ApiModelProperty(value = "id主键")
    private String id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")

    private Integer isdel;

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本")

    private String dataflag;

    /**
     * 录入人身份证号
     */
    @ApiModelProperty(value = "录入人身份证号")

    private String lrrSfzh;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")

    private Date lrsj;

    /**
     * 修改人身份证号
     */
    @ApiModelProperty(value = "修改人身份证号")

    private String xgrSfzh;

    /**
     * 修改时间
     */

    @ApiModelProperty(value = "修改时间")

    private Date xgsj;

    /**
     * 办案区
     */
    @ApiModelProperty(value = "办案区")

    private String baqid;

    /**
     * 办案区名称
     */
    @ApiModelProperty(value = "办案区名称")

    private String baqmc;

    /**
     * 人员编号
     */
    @ApiModelProperty(value = "人员编号")

    private String rybh;

    /**
     * 人员姓名
     */
    @ApiModelProperty(value = "人员姓名")
    @ApiParam(value = "人员姓名", required = true)
    private String ryxm;

    /**
     * 人员身份证号
     */
    @ApiModelProperty(value = "人员身份证号")
    @ApiParam(value = "人员身份证号", required = true)

    private String rysfzh;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")

    private String zjlx;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @ApiParam(value = "性别", required = true)

    private String xb;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名")

    private String bm;

    /**
     * 曾用名
     */
    @ApiModelProperty(value = "曾用名")

    private String cym;

    /**
     * 绰号
     */
    @ApiModelProperty(value = "绰号")

    private String ch;

    /**
     * 国籍
     */
    @ApiModelProperty(value = "国籍")

    private String gj;

    /**
     * 文化程度
     */
    @ApiModelProperty(value = "文化程度")

    private String whcd;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")

    private String mz;

    /**
     * 工作单位
     */
    @ApiModelProperty(value = "工作单位")

    private String gzdw;

    /**
     * 户籍
     */
    @ApiModelProperty(value = "户籍")

    private String hjdxzqh;

    /**
     * 现住地详址
     */
    @ApiModelProperty(value = "现住地详址")

    private String xzdxz;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")

    private String lxdh;

    /**
     * 照片
     */
    @ApiModelProperty(value = "照片")

    private String zpid;

    /**
     * 主办单位名称
     */
    @ApiModelProperty(value = "主办单位名称")

    private String zbdwMc;

    /**
     * 采集时间
     */
    @ApiModelProperty(value = "采集时间")
    @ApiParam(value = "采集时间", required = true)
    private Date cjsj;

    /**
     * 主办单位编号
     */
    @ApiModelProperty(value = "主办单位编号")

    private String zbdwBh;

    /**
     * 采集地点
     */
    @ApiModelProperty(value = "采集地点")

    private String cjdd;

    /**
     * 是否自报名
     */
    @ApiModelProperty(value = "是否自报名")

    private Integer sfzbm;

    /**
     * 是否身份不明
     */
    @ApiModelProperty(value = "是否身份不明")

    private Integer sfsfbm;

    /**
     * 一体化采集状态
     */
    @ApiModelProperty(value = "一体化采集状态")

    private Integer ythcjzt;
    private String sfythcjmc;

    private String zzmm;
    /**
     * 人员类型
     */
    @ApiModelProperty(value = "人员类型")

    private String rylx;
    /**
     * 单位系统编号
     */
    @ApiModelProperty(value = "单位系统编号")

    private String dwxtbh;

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

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRysfzh() {
        return rysfzh;
    }

    public void setRysfzh(String rysfzh) {
        this.rysfzh = rysfzh;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getHjdxzqh() {
        return hjdxzqh;
    }

    public void setHjdxzqh(String hjdxzqh) {
        this.hjdxzqh = hjdxzqh;
    }

    public String getXzdxz() {
        return xzdxz;
    }

    public void setXzdxz(String xzdxz) {
        this.xzdxz = xzdxz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getZbdwMc() {
        return zbdwMc;
    }

    public void setZbdwMc(String zbdwMc) {
        this.zbdwMc = zbdwMc;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getCjdd() {
        return cjdd;
    }

    public void setCjdd(String cjdd) {
        this.cjdd = cjdd;
    }

    public Integer getSfzbm() {
        return sfzbm;
    }

    public void setSfzbm(Integer sfzbm) {
        this.sfzbm = sfzbm;
    }

    public Integer getSfsfbm() {
        return sfsfbm;
    }

    public void setSfsfbm(Integer sfsfbm) {
        this.sfsfbm = sfsfbm;
    }

    public Integer getYthcjzt() {
        return ythcjzt;
    }

    public void setYthcjzt(Integer ythcjzt) {
        this.ythcjzt = ythcjzt;
    }

    public String getSfythcjmc() {
        return sfythcjmc;
    }

    public void setSfythcjmc(String sfythcjmc) {
        this.sfythcjmc = sfythcjmc;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }
}
