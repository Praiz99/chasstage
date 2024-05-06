package com.wckj.chasstage.api.def.wpgl.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

public class SswpBeanView {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "我处理的 本单位的")
    private String me;

    @ApiModelProperty(value = "物品处理状态")
    private String wpclzt;


    @ApiModelProperty(value = "办案区ID")
    private String baqid;

    @ApiModelProperty(value = "办案区名称")
    private String baqmc;


    @ApiModelProperty(value = "物品编号")
    private String wpbh;


    @ApiModelProperty(value = "处理类型")
    private String cllx;

    @ApiModelProperty(value = "物品类型（随身物品、涉案物品")
    private String lx;

    @ApiParam(value = "名称")
    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "物品类别")
    private String lb;
    @ApiModelProperty(value = "物品类别名称")
    private String lbName;


    @ApiModelProperty(value = "持有人")
    private String cyr;


    @ApiModelProperty(value = "物品单位")
    private String dw;

    @ApiModelProperty(value = "物品数量")
    private String sl;

    @ApiModelProperty(value = "物品特征")
    private String tz;


    @ApiModelProperty(value = "价值")
    private String jz;

    @ApiModelProperty(value = "存放位置")
    private String cfwz;

    @ApiModelProperty(value = "条形码")
    private String txm;


    @ApiModelProperty(value = "备注")
    private String bz;


    @ApiModelProperty(value = "物品状态(01-在所物品；02-出所物品)")
    private String wpzt;


    @ApiModelProperty(value = "主办人姓名")
    private String zbrXm;


    @ApiModelProperty(value = "主办人身份证")
    private String zbrSfzh;


    @ApiModelProperty(value = "主办单位")
    private String zbdwBh;


    @ApiModelProperty(value = "同步时间")
    private Date tbsj;


    @ApiModelProperty(value = "入库时间")
    private Date rksj;


    @ApiModelProperty(value = "出库时间")
    private Date cksj;


    @ApiModelProperty(value = "入库民警身份证号")
    private String rkmjSfzh;


    @ApiModelProperty(value = "入库民警")
    private String rkmjXm;


    @ApiModelProperty(value = "出库民警身份证号")
    private String ckmjSfzh;


    @ApiModelProperty(value = "出库民警姓名")
    private String ckmjXm;

    @ApiModelProperty(value = "人员编号")

    private String rybh;
    @ApiModelProperty(value = "物品照片id")

    private String wjid;
    @ApiModelProperty(value = "单位系统编号")
    private String dwxtbh;

    @ApiModelProperty(value = "是否智能办案区(0:非智能,1：智能)")
    private Integer sfznbaq;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getWpclzt() {
        return wpclzt;
    }

    public void setWpclzt(String wpclzt) {
        this.wpclzt = wpclzt;
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

    public String getWpbh() {
        return wpbh;
    }

    public void setWpbh(String wpbh) {
        this.wpbh = wpbh;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }

    public String getCfwz() {
        return cfwz;
    }

    public void setCfwz(String cfwz) {
        this.cfwz = cfwz;
    }

    public String getTxm() {
        return txm;
    }

    public void setTxm(String txm) {
        this.txm = txm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getWpzt() {
        return wpzt;
    }

    public void setWpzt(String wpzt) {
        this.wpzt = wpzt;
    }

    public String getZbrXm() {
        return zbrXm;
    }

    public void setZbrXm(String zbrXm) {
        this.zbrXm = zbrXm;
    }

    public String getZbrSfzh() {
        return zbrSfzh;
    }

    public void setZbrSfzh(String zbrSfzh) {
        this.zbrSfzh = zbrSfzh;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public Date getTbsj() {
        return tbsj;
    }

    public void setTbsj(Date tbsj) {
        this.tbsj = tbsj;
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public Date getCksj() {
        return cksj;
    }

    public void setCksj(Date cksj) {
        this.cksj = cksj;
    }

    public String getRkmjSfzh() {
        return rkmjSfzh;
    }

    public void setRkmjSfzh(String rkmjSfzh) {
        this.rkmjSfzh = rkmjSfzh;
    }

    public String getRkmjXm() {
        return rkmjXm;
    }

    public void setRkmjXm(String rkmjXm) {
        this.rkmjXm = rkmjXm;
    }

    public String getCkmjSfzh() {
        return ckmjSfzh;
    }

    public void setCkmjSfzh(String ckmjSfzh) {
        this.ckmjSfzh = ckmjSfzh;
    }

    public String getCkmjXm() {
        return ckmjXm;
    }

    public void setCkmjXm(String ckmjXm) {
        this.ckmjXm = ckmjXm;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public Integer getSfznbaq() {
        return sfznbaq;
    }

    public void setSfznbaq(Integer sfznbaq) {
        this.sfznbaq = sfznbaq;
    }

    public String getLbName() {
        return lbName;
    }

    public void setLbName(String lbName) {
        this.lbName = lbName;
    }
}
