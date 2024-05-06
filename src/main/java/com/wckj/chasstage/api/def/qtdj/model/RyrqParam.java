package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author wutl
 * @Title: 人员入区参数
 * @Package
 * @Description:
 * @date 2020-9-211:36
 */
public class RyrqParam {

    @ApiParam(value = "主键标识")
    private String id;
    @ApiParam(value = "照片Id", required = true)
    private String zpid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "腕带低频编号")
    private String wdbhL;
    @ApiParam(value = "办案区名称", required = true)
    private String baqmc;
    @ApiParam(value = "办案区id", required = true)
    private String baqid;
    @ApiParam(value = "访问")
    private String fwsx;
    @ApiParam(value = "访问人数")
    private String fwrs;
    @ApiParam(value = "访问原因", required = true)
    private String fwyy;
    @ApiParam(value = "证件类型", required = true)
    private String zjlx;
    @ApiParam(value = "民警警号")
    private String mjjh;
    @ApiParam(value = "人员身份证")
    private String rySfzh;
    @ApiParam(value = "人员姓名")
    private String ryxm;
    @ApiParam(value = "出生日期")
    private Date crsq;
    @ApiParam(value = "性别")
    private String xb;
    @ApiParam(value = "联系电话")
    private String lxdh;
    @ApiParam(value = "户籍地址")
    private String hjdz;
    @ApiParam(value = "现居地详址")
    private String xzdxz;
    @ApiParam(value = "入所时间", required = true)
    private Date rssj;
    @ApiParam(value = "主案由名称", required = true)
    private String ryZaybhmc;
    @ApiParam(value = "主案由编号", required = true)
    private String ryZaybh;
    @ApiParam(value = "人员类型", required = true)
    private String rylx;
    @ApiParam(value = "人员类型名称", required = true)
    private String rylxName;
    @ApiParam(value = "到案方式名称", required = true)
    private String dafsName;
    @ApiParam(value = "到案方式", required = true)
    private String dafs;
    @ApiParam(value = "到案方式其他")
    private String dafsText;
    @ApiParam(value = "特殊群体名称", required = true)
    private String tsqtmc;
    @ApiParam(value = "特殊群体", required = true)
    private String tsqt;
    @ApiParam(value = "民警姓名，多个;隔开")
    private String mjXm;
    @ApiParam(value = "民警身份证号，多个;隔开")
    private String mjSfzh;
    @ApiParam(value = "主办单位编号")
    private String zbdwBh;
    @ApiParam(value = "主办单位名称")
    private String zbdwMc;
    @ApiParam(value = "协办民警姓名")
    private String xbMjXm;
    @ApiParam(value = "协办民警身份证")
    private String xbMjSfzh;
    @ApiParam(value = "业务编号字符串")
    private String ywbhStr;
    @ApiParam(value = "业务编号")
    private String ywbh;
    @ApiParam(value = "箱子类型；大中小超大")
    private String boxType;
    @ApiParam(value = "手机柜Id")
    private String sjgId;
    @ApiParam(value = "手机柜编号")
    private String sjgBh;
    @ApiParam(value = "物品柜id")
    private String cwgid;
    @ApiParam(value = "物品柜编号")
    private String cwgbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getFwsx() {
        return fwsx;
    }

    public void setFwsx(String fwsx) {
        this.fwsx = fwsx;
    }

    public String getFwrs() {
        return fwrs;
    }

    public void setFwrs(String fwrs) {
        this.fwrs = fwrs;
    }

    public String getFwyy() {
        return fwyy;
    }

    public void setFwyy(String fwyy) {
        this.fwyy = fwyy;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getMjjh() {
        return mjjh;
    }

    public void setMjjh(String mjjh) {
        this.mjjh = mjjh;
    }

    public String getRySfzh() {
        return rySfzh;
    }

    public void setRySfzh(String rySfzh) {
        this.rySfzh = rySfzh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public Date getCrsq() {
        return crsq;
    }

    public void setCrsq(Date crsq) {
        this.crsq = crsq;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getHjdz() {
        return hjdz;
    }

    public void setHjdz(String hjdz) {
        this.hjdz = hjdz;
    }

    public String getXzdxz() {
        return xzdxz;
    }

    public void setXzdxz(String xzdxz) {
        this.xzdxz = xzdxz;
    }

    public Date getRssj() {
        return rssj;
    }

    public void setRssj(Date rssj) {
        this.rssj = rssj;
    }

    public String getRyZaybhmc() {
        return ryZaybhmc;
    }

    public void setRyZaybhmc(String ryZaybhmc) {
        this.ryZaybhmc = ryZaybhmc;
    }

    public String getRyZaybh() {
        return ryZaybh;
    }

    public void setRyZaybh(String ryZaybh) {
        this.ryZaybh = ryZaybh;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getRylxName() {
        return rylxName;
    }

    public void setRylxName(String rylxName) {
        this.rylxName = rylxName;
    }

    public String getDafsName() {
        return dafsName;
    }

    public void setDafsName(String dafsName) {
        this.dafsName = dafsName;
    }

    public String getDafs() {
        return dafs;
    }

    public void setDafs(String dafs) {
        this.dafs = dafs;
    }

    public String getDafsText() {
        return dafsText;
    }

    public void setDafsText(String dafsText) {
        this.dafsText = dafsText;
    }

    public String getTsqtmc() {
        return tsqtmc;
    }

    public void setTsqtmc(String tsqtmc) {
        this.tsqtmc = tsqtmc;
    }

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }

    public String getMjXm() {
        return mjXm;
    }

    public void setMjXm(String mjXm) {
        this.mjXm = mjXm;
    }

    public String getMjSfzh() {
        return mjSfzh;
    }

    public void setMjSfzh(String mjSfzh) {
        this.mjSfzh = mjSfzh;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getZbdwMc() {
        return zbdwMc;
    }

    public void setZbdwMc(String zbdwMc) {
        this.zbdwMc = zbdwMc;
    }

    public String getXbMjXm() {
        return xbMjXm;
    }

    public void setXbMjXm(String xbMjXm) {
        this.xbMjXm = xbMjXm;
    }

    public String getXbMjSfzh() {
        return xbMjSfzh;
    }

    public void setXbMjSfzh(String xbMjSfzh) {
        this.xbMjSfzh = xbMjSfzh;
    }

    public String getYwbhStr() {
        return ywbhStr;
    }

    public void setYwbhStr(String ywbhStr) {
        this.ywbhStr = ywbhStr;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getSjgId() {
        return sjgId;
    }

    public void setSjgId(String sjgId) {
        this.sjgId = sjgId;
    }

    public String getSjgBh() {
        return sjgBh;
    }

    public void setSjgBh(String sjgBh) {
        this.sjgBh = sjgBh;
    }

    public String getCwgid() {
        return cwgid;
    }

    public void setCwgid(String cwgid) {
        this.cwgid = cwgid;
    }

    public String getCwgbh() {
        return cwgbh;
    }

    public void setCwgbh(String cwgbh) {
        this.cwgbh = cwgbh;
    }
}
