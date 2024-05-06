package com.wckj.chasstage.api.def.baqry.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 出入情况对象
 * @Package 出入情况对象
 * @Description: 出入情况对象
 * @date 2020-9-2214:30
 */
public class CrqkBean {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "是否删除")
    private String isdel;
    @ApiModelProperty(value = "删除标识")
    private String dataFlag;
    @ApiModelProperty(value = "录入人身份证")
    private String lrrSfzh;
    @ApiModelProperty(value = "录入时间")
    private Date lrsj;
    @ApiModelProperty(value = "修改人身份证")
    private String xgrSfzh;
    @ApiModelProperty(value = "修改时间")
    private Date xgsj;
    @ApiModelProperty(value = "办案区Id")
    private String baqid;
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;
    @ApiModelProperty(value = "业务编号")
    private String ywbh;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "入库原因")
    private String lkyy;
    @ApiModelProperty(value = "入库时间")
    private Date lksj;
    @ApiModelProperty(value = "回来时间")
    private Date hlsj;
    @ApiModelProperty(value = "审批人身份证")
    private String sprSfzh;
    @ApiModelProperty(value = "审批人姓名")
    private String sprXm;
    @ApiModelProperty(value = "主办单位编号")
    private String zbdwBh;
    @ApiModelProperty(value = "主办单位名称")
    private String zbdwMc;
    @ApiModelProperty(value = "主办人身份证")
    private String zbrSfzh;
    @ApiModelProperty(value = "主办人姓名")
    private String zbrXm;
    @ApiModelProperty(value = "审批状态")
    private String spzt;
    @ApiModelProperty(value = "审批意见")
    private String spyj;
    @ApiModelProperty(value = "审批时间")
    private String spsj;
    @ApiModelProperty(value = "人员状态")
    private String ryzt;
    @ApiModelProperty(value = "入库原因代码")
    private String lkyydm;
    @ApiModelProperty(value = "入库类型")
    private String lklx;
    @ApiModelProperty(value = "出所类型")
    private String cslx;
    @ApiModelProperty(value = "单位系统编号")
    private String dwxtbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
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

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getLkyy() {
        return lkyy;
    }

    public void setLkyy(String lkyy) {
        this.lkyy = lkyy;
    }

    public Date getLksj() {
        return lksj;
    }

    public void setLksj(Date lksj) {
        this.lksj = lksj;
    }

    public Date getHlsj() {
        return hlsj;
    }

    public void setHlsj(Date hlsj) {
        this.hlsj = hlsj;
    }

    public String getSprSfzh() {
        return sprSfzh;
    }

    public void setSprSfzh(String sprSfzh) {
        this.sprSfzh = sprSfzh;
    }

    public String getSprXm() {
        return sprXm;
    }

    public void setSprXm(String sprXm) {
        this.sprXm = sprXm;
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

    public String getZbrSfzh() {
        return zbrSfzh;
    }

    public void setZbrSfzh(String zbrSfzh) {
        this.zbrSfzh = zbrSfzh;
    }

    public String getZbrXm() {
        return zbrXm;
    }

    public void setZbrXm(String zbrXm) {
        this.zbrXm = zbrXm;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    public String getSpsj() {
        return spsj;
    }

    public void setSpsj(String spsj) {
        this.spsj = spsj;
    }

    public String getRyzt() {
        return ryzt;
    }

    public void setRyzt(String ryzt) {
        this.ryzt = ryzt;
    }

    public String getLkyydm() {
        return lkyydm;
    }

    public void setLkyydm(String lkyydm) {
        this.lkyydm = lkyydm;
    }

    public String getLklx() {
        return lklx;
    }

    public void setLklx(String lklx) {
        this.lklx = lklx;
    }

    public String getCslx() {
        return cslx;
    }

    public void setCslx(String cslx) {
        this.cslx = cslx;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }
}
