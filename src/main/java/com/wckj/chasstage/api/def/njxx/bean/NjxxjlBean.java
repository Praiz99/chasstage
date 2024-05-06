package com.wckj.chasstage.api.def.njxx.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 尿检信息记录
 * @Package 尿检信息记录
 * @Description: 尿检信息记录
 * @date 2020-9-1616:30
 */
public class NjxxjlBean {
    @ApiModelProperty(value = "尿检记录id")
    private String id;
    @ApiModelProperty(value = "办案区id")
    private String baqid;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Integer isdel;
    @ApiModelProperty(value = "数据标识")
    private String dataFlag;
    @ApiModelProperty(value = "录入身份证",hidden = true)
    private String lrrSfzh;
    @ApiModelProperty(value = "录入时间",hidden = true)
    private Date lrsj;
    @ApiModelProperty(value = "修改人身份证",hidden = true)
    private String xgrSfzh;
    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date xgsj;
    @ApiModelProperty(value = "尿检编号")
    private String nybh;
    @ApiModelProperty(value = "被检查人身份证号")
    private String bjcrSfzh;
    @ApiModelProperty(value = "被检查人身份证姓名")
    private String bjcrXm;
    @ApiModelProperty(value = "操作民警身份证")
    private String czmjSfzh;
    @ApiModelProperty(value = "操作民警姓名")
    private String czmjXm;
    @ApiModelProperty(value = "出区时间")
    private Date cqsj;
    @ApiModelProperty(value = "时间标识")
    private Date sjbq;
    @ApiModelProperty(value = "主办单位编号")
    private String zbdwBh;
    @ApiModelProperty(value = "主办单位名称")
    private String zbdwMc;
    @ApiModelProperty(value = "主办单位系统编号")
    private String dwxtbh;
    @ApiModelProperty(value = "操作类型")
    private String czlx;
    @ApiModelProperty(value = "人员编号")
    private String rybh;
    @ApiModelProperty(value = "业务id")
    private String businessId;

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

    public String getNybh() {
        return nybh;
    }

    public void setNybh(String nybh) {
        this.nybh = nybh;
    }

    public String getBjcrSfzh() {
        return bjcrSfzh;
    }

    public void setBjcrSfzh(String bjcrSfzh) {
        this.bjcrSfzh = bjcrSfzh;
    }

    public String getBjcrXm() {
        return bjcrXm;
    }

    public void setBjcrXm(String bjcrXm) {
        this.bjcrXm = bjcrXm;
    }

    public String getCzmjSfzh() {
        return czmjSfzh;
    }

    public void setCzmjSfzh(String czmjSfzh) {
        this.czmjSfzh = czmjSfzh;
    }

    public String getCzmjXm() {
        return czmjXm;
    }

    public void setCzmjXm(String czmjXm) {
        this.czmjXm = czmjXm;
    }

    public Date getCqsj() {
        return cqsj;
    }

    public void setCqsj(Date cqsj) {
        this.cqsj = cqsj;
    }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
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

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }
}
