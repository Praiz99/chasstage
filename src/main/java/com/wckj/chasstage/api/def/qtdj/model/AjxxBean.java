package com.wckj.chasstage.api.def.qtdj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 案件信息
 * @Package
 * @Description:
 * @date 2020-9-310:57
 */
public class AjxxBean {
    @ApiModelProperty("案件id")
    private String id;
    @ApiModelProperty("案件编号")
    private String ajbh;
    @ApiModelProperty("案件名称")
    private String ajmc;
    @ApiModelProperty("主办人姓名")
    private String zbrXm;
    @ApiModelProperty("主办人身份证号")
    private String zbrSfzh;
    @ApiModelProperty("协办人姓名")
    private String xbrXm;
    @ApiModelProperty("协办人身份证号")
    private String xbrSfzh;
    @ApiModelProperty("主办单位代码")
    private String zbdwDm;
    @ApiModelProperty("主办单位名称")
    private String zbdwMc;
    @ApiModelProperty("系统机构代码")
    private String orgSysCode;
    @ApiModelProperty("案件状态")
    private String ajzt;
    @ApiModelProperty("案件状态描述")
    private String ajztMc;
    @ApiModelProperty("案件类型")
    private String ajlx;
    @ApiModelProperty("案件类型描述")
    private String ajlxMc;
    @ApiModelProperty("案件类别")
    private String ajlb;
    @ApiModelProperty("案件类别描述")
    private String ajlbMc;
    @ApiModelProperty("简要案情")
    private String jyaq;
    @ApiModelProperty("是否在办")
    private String sfzb;
    @ApiModelProperty("案由编号")
    private String ayBh;
    @ApiModelProperty("案由名称")
    private String ayMc;
    @ApiModelProperty("发案地点")
    private String fadd;
    @ApiModelProperty("发案时间上限")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fasjSx;
    @ApiModelProperty("发案时间下限")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fasjXx;
    @ApiModelProperty("报警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bjsj;
    @ApiModelProperty("受立时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date slsj;
    @ApiModelProperty("立案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lasj;
    @ApiModelProperty("破案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pasj;
    @ApiModelProperty("结案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jasj;
    @ApiModelProperty("不予立案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bylasj;
    @ApiModelProperty("撤销案件时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cxajsj;
    @ApiModelProperty("案件来源")
    private String ajly;
    @ApiModelProperty("立案批准时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lapzsj;
    @ApiModelProperty("立案批准人身份证号")
    private String lapzrSfzh;
    @ApiModelProperty("立案批准人姓名")
    private String lapzrXm;
    @ApiModelProperty("审核状态：01未审核02已审核")
    private String shzt;
    @ApiModelProperty("是否提醒：1是0否")
    private Integer sftx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
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

    public String getXbrXm() {
        return xbrXm;
    }

    public void setXbrXm(String xbrXm) {
        this.xbrXm = xbrXm;
    }

    public String getXbrSfzh() {
        return xbrSfzh;
    }

    public void setXbrSfzh(String xbrSfzh) {
        this.xbrSfzh = xbrSfzh;
    }

    public String getZbdwDm() {
        return zbdwDm;
    }

    public void setZbdwDm(String zbdwDm) {
        this.zbdwDm = zbdwDm;
    }

    public String getZbdwMc() {
        return zbdwMc;
    }

    public void setZbdwMc(String zbdwMc) {
        this.zbdwMc = zbdwMc;
    }

    public String getOrgSysCode() {
        return orgSysCode;
    }

    public void setOrgSysCode(String orgSysCode) {
        this.orgSysCode = orgSysCode;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public String getAjztMc() {
        return ajztMc;
    }

    public void setAjztMc(String ajztMc) {
        this.ajztMc = ajztMc;
    }

    public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx;
    }

    public String getAjlxMc() {
        return ajlxMc;
    }

    public void setAjlxMc(String ajlxMc) {
        this.ajlxMc = ajlxMc;
    }

    public String getAjlb() {
        return ajlb;
    }

    public void setAjlb(String ajlb) {
        this.ajlb = ajlb;
    }

    public String getAjlbMc() {
        return ajlbMc;
    }

    public void setAjlbMc(String ajlbMc) {
        this.ajlbMc = ajlbMc;
    }

    public String getJyaq() {
        return jyaq;
    }

    public void setJyaq(String jyaq) {
        this.jyaq = jyaq;
    }

    public String getSfzb() {
        return sfzb;
    }

    public void setSfzb(String sfzb) {
        this.sfzb = sfzb;
    }

    public String getAyBh() {
        return ayBh;
    }

    public void setAyBh(String ayBh) {
        this.ayBh = ayBh;
    }

    public String getAyMc() {
        return ayMc;
    }

    public void setAyMc(String ayMc) {
        this.ayMc = ayMc;
    }

    public String getFadd() {
        return fadd;
    }

    public void setFadd(String fadd) {
        this.fadd = fadd;
    }

    public Date getFasjSx() {
        return fasjSx;
    }

    public void setFasjSx(Date fasjSx) {
        this.fasjSx = fasjSx;
    }

    public Date getFasjXx() {
        return fasjXx;
    }

    public void setFasjXx(Date fasjXx) {
        this.fasjXx = fasjXx;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public Date getLasj() {
        return lasj;
    }

    public void setLasj(Date lasj) {
        this.lasj = lasj;
    }

    public Date getPasj() {
        return pasj;
    }

    public void setPasj(Date pasj) {
        this.pasj = pasj;
    }

    public Date getJasj() {
        return jasj;
    }

    public void setJasj(Date jasj) {
        this.jasj = jasj;
    }

    public Date getBylasj() {
        return bylasj;
    }

    public void setBylasj(Date bylasj) {
        this.bylasj = bylasj;
    }

    public Date getCxajsj() {
        return cxajsj;
    }

    public void setCxajsj(Date cxajsj) {
        this.cxajsj = cxajsj;
    }

    public String getAjly() {
        return ajly;
    }

    public void setAjly(String ajly) {
        this.ajly = ajly;
    }

    public Date getLapzsj() {
        return lapzsj;
    }

    public void setLapzsj(Date lapzsj) {
        this.lapzsj = lapzsj;
    }

    public String getLapzrSfzh() {
        return lapzrSfzh;
    }

    public void setLapzrSfzh(String lapzrSfzh) {
        this.lapzrSfzh = lapzrSfzh;
    }

    public String getLapzrXm() {
        return lapzrXm;
    }

    public void setLapzrXm(String lapzrXm) {
        this.lapzrXm = lapzrXm;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    public Integer getSftx() {
        return sftx;
    }

    public void setSftx(Integer sftx) {
        this.sftx = sftx;
    }
}
