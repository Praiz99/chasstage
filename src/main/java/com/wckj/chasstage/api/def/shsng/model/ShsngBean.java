package com.wckj.chasstage.api.def.shsng.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author:zengrk
 */
public class ShsngBean {
    /**
     * 主键
     */
    @ApiModelProperty(value = "id主键")
    private String id;
    @ApiModelProperty(value = "数据是否逻辑删除")
    private Short isdel;
    @ApiModelProperty(value = "数据标识")
    private String dataFlag;
    @ApiModelProperty(value = "录入人身份证号")
    private String lrrSfzh;
    @ApiModelProperty(value = "录入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lrsj;
    @ApiModelProperty(value = "修改人身份证号")
    private String xgrSfzh;
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xgsj;
    @ApiModelProperty(value = "手环编号")
    private String shbh;
    @ApiModelProperty(value = "在柜情况（1、在柜， 2、已取出）")
    private String zgqk;
    @ApiModelProperty(value = "收纳柜Id（备用）")
    private String sngid;
    @ApiModelProperty(value = "办案区代码")
    private String baqid;
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;
    @ApiModelProperty(value = "腕带 2.4g高频卡号腕带上打印的，nvr ip")
    private String kzcs1;
    @ApiModelProperty(value = "腕带 低频13.56卡号读卡器读到，nvr 端口")
    private String kzcs2;
    @ApiModelProperty(value = "设备类型")
    private String sblx;
    @ApiModelProperty(value = "设备名称")
    private String sbmc;
    @ApiModelProperty(value = "发放时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ffsj;
    @ApiModelProperty(value = "归还时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ghsj;
    @ApiModelProperty(value = "时间戳")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sjbq;
    @ApiModelProperty(value = "状态 1、佩戴中，2、已归还，3、手动归还")
    private String zt;
    @ApiModelProperty(value = "nvr用户名   1(手环)  2（胸卡）")
    private String kzcs3;

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

    public String getShbh() {
        return shbh;
    }

    public void setShbh(String shbh) {
        this.shbh = shbh == null ? null : shbh.trim();
    }

    public String getZgqk() {
        return zgqk;
    }

    public void setZgqk(String zgqk) {
        this.zgqk = zgqk == null ? null : zgqk.trim();
    }

    public String getSngid() {
        return sngid;
    }

    public void setSngid(String sngid) {
        this.sngid = sngid == null ? null : sngid.trim();
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

    public String getKzcs1() {
        return kzcs1;
    }

    public void setKzcs1(String kzcs1) {
        this.kzcs1 = kzcs1 == null ? null : kzcs1.trim();
    }

    public String getKzcs2() {
        return kzcs2;
    }

    public void setKzcs2(String kzcs2) {
        this.kzcs2 = kzcs2 == null ? null : kzcs2.trim();
    }

    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx == null ? null : sblx.trim();
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc == null ? null : sbmc.trim();
    }

    public Date getFfsj() {
        return ffsj;
    }

    public void setFfsj(Date ffsj) {
        this.ffsj = ffsj;
    }

    public Date getGhsj() {
        return ghsj;
    }

    public void setGhsj(Date ghsj) {
        this.ghsj = ghsj;
    }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    public String getKzcs3() {
        return kzcs3;
    }

    public void setKzcs3(String kzcs3) {
        this.kzcs3 = kzcs3;
    }
}
