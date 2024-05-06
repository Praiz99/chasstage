package com.wckj.chasstage.api.def.qtdj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 警情信息
 * @Package
 * @Description:
 * @date 2020-9-310:57
 */
public class JqxxBean {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "警情编号")
    private String jqbh;
    @ApiModelProperty(value = "警情名称")
    private String jqmc;
    @ApiModelProperty(value = "警情地点")
    private String jqdd;
    @ApiModelProperty(value = "处警民警姓名")
    private String cjmj;
    @ApiModelProperty(value = "处警民警身份证")
    private String cjmjSfzh;
    @ApiModelProperty(value = "处警单位")
    private String cjdw;
    @ApiModelProperty(value = "处警单位代码")
    private String cjdwDm;
    @ApiModelProperty(value = "处警内容")
    private String cjnr;
    @ApiModelProperty(value = "处警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cjsj;
    @ApiModelProperty(value = "处警来源")
    private String jqly;
    @ApiModelProperty(value = "处警来源描述")
    private String jqlyms;
    @ApiModelProperty(value = "处警类别")
    private String jqlb;
    @ApiModelProperty(value = "处警类别描述")
    private String jqlbms;
    @ApiModelProperty(value = "处警状态")
    private String jqzt;
    @ApiModelProperty(value = "处警状态描述")
    private String jqztms;
    @ApiModelProperty(value = "处警人姓名")
    private String jbrxm;
    @ApiModelProperty(value = "处警人身份证")
    private String jbrSfzh;
    @ApiModelProperty(value = "报警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bjsj;
    @ApiModelProperty(value = "报警电话")
    private String bjdh;
    @ApiModelProperty(value = "派单到达时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pdddsj;
    @ApiModelProperty(value = "派单接收时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pdjssj;
    @ApiModelProperty(value = "到达现场时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ddxcsj;
    @ApiModelProperty(value = "处警措施")
    private String cjcs;
    @ApiModelProperty(value = "处警结果")
    private String cjjg;
    @ApiModelProperty(value = "处警意见")
    private String cjyj;
    @ApiModelProperty(value = "系统单位代码")
    private String orgSysCode;
    @ApiModelProperty("审核状态：01未审核02已审核")
    private String shzt;
    @ApiModelProperty("是否提醒：1是0否")
    private Integer sftx;
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public String getJqmc() {
        return jqmc;
    }

    public void setJqmc(String jqmc) {
        this.jqmc = jqmc;
    }

    public String getJqdd() {
        return jqdd;
    }

    public void setJqdd(String jqdd) {
        this.jqdd = jqdd;
    }

    public String getCjmj() {
        return cjmj;
    }

    public void setCjmj(String cjmj) {
        this.cjmj = cjmj;
    }

    public String getCjmjSfzh() {
        return cjmjSfzh;
    }

    public void setCjmjSfzh(String cjmjSfzh) {
        this.cjmjSfzh = cjmjSfzh;
    }

    public String getCjdw() {
        return cjdw;
    }

    public void setCjdw(String cjdw) {
        this.cjdw = cjdw;
    }

    public String getCjdwDm() {
        return cjdwDm;
    }

    public void setCjdwDm(String cjdwDm) {
        this.cjdwDm = cjdwDm;
    }

    public String getCjnr() {
        return cjnr;
    }

    public void setCjnr(String cjnr) {
        this.cjnr = cjnr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getJqly() {
        return jqly;
    }

    public void setJqly(String jqly) {
        this.jqly = jqly;
    }

    public String getJqlyms() {
        return jqlyms;
    }

    public void setJqlyms(String jqlyms) {
        this.jqlyms = jqlyms;
    }

    public String getJqlb() {
        return jqlb;
    }

    public void setJqlb(String jqlb) {
        this.jqlb = jqlb;
    }

    public String getJqlbms() {
        return jqlbms;
    }

    public void setJqlbms(String jqlbms) {
        this.jqlbms = jqlbms;
    }

    public String getJqzt() {
        return jqzt;
    }

    public void setJqzt(String jqzt) {
        this.jqzt = jqzt;
    }

    public String getJqztms() {
        return jqztms;
    }

    public void setJqztms(String jqztms) {
        this.jqztms = jqztms;
    }

    public String getJbrxm() {
        return jbrxm;
    }

    public void setJbrxm(String jbrxm) {
        this.jbrxm = jbrxm;
    }

    public String getJbrSfzh() {
        return jbrSfzh;
    }

    public void setJbrSfzh(String jbrSfzh) {
        this.jbrSfzh = jbrSfzh;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    public String getBjdh() {
        return bjdh;
    }

    public void setBjdh(String bjdh) {
        this.bjdh = bjdh;
    }

    public Date getPdddsj() {
        return pdddsj;
    }

    public void setPdddsj(Date pdddsj) {
        this.pdddsj = pdddsj;
    }

    public Date getPdjssj() {
        return pdjssj;
    }

    public void setPdjssj(Date pdjssj) {
        this.pdjssj = pdjssj;
    }

    public Date getDdxcsj() {
        return ddxcsj;
    }

    public void setDdxcsj(Date ddxcsj) {
        this.ddxcsj = ddxcsj;
    }

    public String getCjcs() {
        return cjcs;
    }

    public void setCjcs(String cjcs) {
        this.cjcs = cjcs;
    }

    public String getCjjg() {
        return cjjg;
    }

    public void setCjjg(String cjjg) {
        this.cjjg = cjjg;
    }

    public String getCjyj() {
        return cjyj;
    }

    public void setCjyj(String cjyj) {
        this.cjyj = cjyj;
    }

    public String getOrgSysCode() {
        return orgSysCode;
    }

    public void setOrgSysCode(String orgSysCode) {
        this.orgSysCode = orgSysCode;
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

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }
}
