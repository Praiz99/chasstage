package com.wckj.chasstage.modules.yy.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 预约信息
 */
public class ChasYwYy {
    /**
     * id
     */
    private String id;
    /**
     * 逻辑删除
     */
    private Short isdel;
    /**
     * 版本
     */
    private String dataFlag;
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
     * 办案区id
     */
    private String baqid;
    /**
     * 办案区名称
     */
    private String baqmc;
    /**
     * 预约类型
     */
    private String yylx;
    /**
     * 预约人警号
     */
    private String yyrjh;

    /**
     *预约人姓名
     */
    private String yyrxm;

    /**
     *预约人身份证号
     */
    private String yyrsfzh;

    /**
     *预约人单位代码
     */
    private String yyrdwdm;

    /**
     *预约人单位名称
     */
    private String yyrdwmc;

    /**
     *开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date kssj;

    /**
     *结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date jssj;

    /**
     *人员数量
     */
    private Short rysl;

    /**
     *是否使用审讯室
     */
    private Short sysxs;

    /**
     *审讯室数量
     */
    private Short sxssl;

    /**
     *预约备注
     */
    private String yybz;

    /**
     *预约状态
     */
    private String yyzt;

    /**
     *审批人警号
     */
    private String sprjh;

    /**
     *审批人姓名
     */
    private String sprxm;

    /**
     *审批人单位代码
     */
    private String sprdwdm;

    /**
     *审批意见
     */
    private String spyj;

    /**
     *出所原因
     */
    private String csyy;

    /**
     *入区时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rqsj;

    /**
     *出区时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cqsj;

    /**
     *联系电话
     */
    private String tel;

    /**
     *手环编号
     */
    private String shbh;

    /**
     *出入区状态
     */
    private String crqzt;

    /**
     *人员编号
     */
    private String rybh;

    /**
     * 是否调度
     */
    private String sfdd;

    /**
     * 联系方式
     */
    private String lxfs;

    private String imgUrl;
    /**
     * 车牌号码
     */
    private String cphm;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag == null ? null : dataFlag.trim();
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

    public String getYylx() {
        return yylx;
    }

    public void setYylx(String yylx) {
        this.yylx = yylx == null ? null : yylx.trim();
    }

    public String getYyrjh() {
        return yyrjh;
    }

    public void setYyrjh(String yyrjh) {
        this.yyrjh = yyrjh == null ? null : yyrjh.trim();
    }

    public String getYyrxm() {
        return yyrxm;
    }

    public void setYyrxm(String yyrxm) {
        this.yyrxm = yyrxm == null ? null : yyrxm.trim();
    }

    public String getYyrsfzh() {
        return yyrsfzh;
    }

    public void setYyrsfzh(String yyrsfzh) {
        this.yyrsfzh = yyrsfzh == null ? null : yyrsfzh.trim();
    }

    public String getYyrdwdm() {
        return yyrdwdm;
    }

    public void setYyrdwdm(String yyrdwdm) {
        this.yyrdwdm = yyrdwdm == null ? null : yyrdwdm.trim();
    }

    public String getYyrdwmc() {
        return yyrdwmc;
    }

    public void setYyrdwmc(String yyrdwmc) {
        this.yyrdwmc = yyrdwmc == null ? null : yyrdwmc.trim();
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public Short getRysl() {
        return rysl;
    }

    public void setRysl(Short rysl) {
        this.rysl = rysl;
    }

    public Short getSysxs() {
        return sysxs;
    }

    public void setSysxs(Short sysxs) {
        this.sysxs = sysxs;
    }

    public Short getSxssl() {
        return sxssl;
    }

    public void setSxssl(Short sxssl) {
        this.sxssl = sxssl;
    }

    public String getYybz() {
        return yybz;
    }

    public void setYybz(String yybz) {
        this.yybz = yybz == null ? null : yybz.trim();
    }

    public String getYyzt() {
        return yyzt;
    }

    public void setYyzt(String yyzt) {
        this.yyzt = yyzt == null ? null : yyzt.trim();
    }

    public String getSprjh() {
        return sprjh;
    }

    public void setSprjh(String sprjh) {
        this.sprjh = sprjh == null ? null : sprjh.trim();
    }

    public String getSprxm() {
        return sprxm;
    }

    public void setSprxm(String sprxm) {
        this.sprxm = sprxm == null ? null : sprxm.trim();
    }

    public String getSprdwdm() {
        return sprdwdm;
    }

    public void setSprdwdm(String sprdwdm) {
        this.sprdwdm = sprdwdm == null ? null : sprdwdm.trim();
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }

    public String getCsyy() {
        return csyy;
    }

    public void setCsyy(String csyy) {
        this.csyy = csyy == null ? null : csyy.trim();
    }

    public Date getRqsj() { return rqsj; }

    public void setRqsj(Date rqsj) { this.rqsj = rqsj; }

    public Date getCqsj() { return cqsj; }

    public void setCqsj(Date cqsj) { this.cqsj = cqsj; }

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getShbh() { return shbh; }

    public void setShbh(String shbh) { this.shbh = shbh; }

    public String getCrqzt() { return crqzt; }

    public void setCrqzt(String crqzt) { this.crqzt = crqzt; }

    public String getRybh() { return rybh; }

    public void setRybh(String rybh) { this.rybh = rybh; }

    public String getSfdd() {
        return sfdd;
    }

    public void setSfdd(String sfdd) {
        this.sfdd = sfdd;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }
}
