package com.wckj.chasstage.api.def.jhrw.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 监护任务记录信息
 */
public class JhrwjlInfoBean{
    @ApiModelProperty("记录名称")
    private String jlmc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("记录时间")
    private Date jlsj;
    @ApiModelProperty("戒护人")
    private String jhr;
    @ApiModelProperty("任务类型")
    private String rwlx;
    @ApiModelProperty("任务状态")
    private String rwzt;

    public String getJlmc() {
        return jlmc;
    }

    public void setJlmc(String jlmc) {
        this.jlmc = jlmc;
    }

    public Date getJlsj() {
        return jlsj;
    }

    public void setJlsj(Date jlsj) {
        this.jlsj = jlsj;
    }

    public String getJhr() {
        return jhr;
    }

    public void setJhr(String jhr) {
        this.jhr = jhr;
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
}