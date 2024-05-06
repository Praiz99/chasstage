package com.wckj.chasstage.api.def.jhrw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class JhrwglBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("人员姓名")
    private String ryxm;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("入区原因")
    private String rqyy;
    @ApiModelProperty("入区时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rqsj;
    @ApiModelProperty("任务类型 01入区戒护 02出区戒护 03体检戒护 04审讯戒护 05送押戒护 06审讯调度 07适格成年人调度")
    private String rwlx;
    @ApiModelProperty("任务状态 01待分配 02待执行 03执行中 04已执行")
    private String rwzt;
    @ApiModelProperty("监护人员")
    private String jhry;
    @ApiModelProperty("任务开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwkssj;
    @ApiModelProperty("任务结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwjssj;
    @ApiModelProperty("戒护任务起点")
    private String jhrwqd;
    @ApiModelProperty("戒护任务终点")
    private String jhrwzd;
    @ApiModelProperty("单位系统编号")
    private String dwxtbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRqyy() {
        return rqyy;
    }

    public void setRqyy(String rqyy) {
        this.rqyy = rqyy;
    }

    public Date getRqsj() {
        return rqsj;
    }

    public void setRqsj(Date rqsj) {
        this.rqsj = rqsj;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public String getRwzt() {
        return rwzt;
    }

    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }

    public String getJhry() {
        return jhry;
    }

    public void setJhry(String jhry) {
        this.jhry = jhry;
    }

    public Date getRwkssj() {
        return rwkssj;
    }

    public void setRwkssj(Date rwkssj) {
        this.rwkssj = rwkssj;
    }

    public Date getRwjssj() {
        return rwjssj;
    }

    public void setRwjssj(Date rwjssj) {
        this.rwjssj = rwjssj;
    }

    public String getJhrwqd() {
        return jhrwqd;
    }

    public void setJhrwqd(String jhrwqd) {
        this.jhrwqd = jhrwqd;
    }

    public String getJhrwzd() {
        return jhrwzd;
    }

    public void setJhrwzd(String jhrwzd) {
        this.jhrwzd = jhrwzd;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }
}
