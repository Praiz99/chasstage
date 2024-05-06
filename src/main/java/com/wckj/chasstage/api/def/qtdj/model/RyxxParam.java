package com.wckj.chasstage.api.def.qtdj.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class RyxxParam extends BaseParam {
    @ApiParam("入所原因（案由编号）字典 RSYY")
    private String ryZaybh;
    @ApiParam("案件编号")
    private String ajbh;
    @ApiParam("案件名称")
    private String ajmc;
    @ApiParam(value = "人员状态 (01在区 06临时出所 04已出所)")
    private String ryzt;
    @ApiParam(value = "出所原因")
    private String cCsyy;
    @ApiParam(value = "处所原因代码")
    private String cRyqx;
    @ApiParam(value = "人员姓名")
    private String ryxm;
    @ApiParam(value = "人员证件号码")
    private String ryzjhm;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "办案区id")
    private String baqid;
    @ApiParam(value = "人员状态(多个用)")
    private String ryztMulti;
    @ApiParam(value = "单位系统编号")
    private String zbdwBh;
    @ApiParam(value = "开始入所时间")
    private String rssj1;
    @ApiParam(value = "结束入所时间")
    private String rssj2;
    @ApiParam(value = "结束出所时间")
    private String cssj1;
    @ApiParam(value = "结束出所时间")
    private String cssj2;
    @ApiParam(value = "系统单位代码")
    private String sysCode;
    @ApiParam(value = "查询业务类型(非必填，体检预约 01)")
    private String bizType;
    @ApiParam(value = "体检预约")
    private String yytj;
    @ApiParam(value = "模糊查询")
    private String likequery;
    @ApiParam(value = "查询来源（app查询传 app即可）")
    private String from;
    @ApiParam(value = "app登录身份证号")
    private String appSfzh;
    @ApiParam(value = "用户角色代码")
    private String roleCode;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAppSfzh() {
        return appSfzh;
    }

    public void setAppSfzh(String appSfzh) {
        this.appSfzh = appSfzh;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyZaybh() {
        return ryZaybh;
    }

    public void setRyZaybh(String ryZaybh) {
        this.ryZaybh = ryZaybh;
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

    public String getRyzt() {
        return ryzt;
    }

    public void setRyzt(String ryzt) {
        this.ryzt = ryzt;
    }

    public String getcCsyy() {
        return cCsyy;
    }

    public void setcCsyy(String cCsyy) {
        this.cCsyy = cCsyy;
    }

    public String getRyztMulti() {
        return ryztMulti;
    }

    public void setRyztMulti(String ryztMulti) {
        this.ryztMulti = ryztMulti;
    }

    public String getcRyqx() {
        return cRyqx;
    }

    public void setcRyqx(String cRyqx) {
        this.cRyqx = cRyqx;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getRssj1() {
        return rssj1;
    }

    public void setRssj1(String rssj1) {
        this.rssj1 = rssj1;
    }

    public String getRssj2() {
        return rssj2;
    }

    public void setRssj2(String rssj2) {
        this.rssj2 = rssj2;
    }

    public String getCssj1() {
        return cssj1;
    }

    public void setCssj1(String cssj1) {
        this.cssj1 = cssj1;
    }

    public String getCssj2() {
        return cssj2;
    }

    public void setCssj2(String cssj2) {
        this.cssj2 = cssj2;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getYytj() {
        return yytj;
    }

    public void setYytj(String yytj) {
        this.yytj = yytj;
    }

    public String getLikequery() {
        return likequery;
    }

    public void setLikequery(String likequery) {
        this.likequery = likequery;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRyzjhm() {
        return ryzjhm;
    }

    public void setRyzjhm(String ryzjhm) {
        this.ryzjhm = ryzjhm;
    }
}
