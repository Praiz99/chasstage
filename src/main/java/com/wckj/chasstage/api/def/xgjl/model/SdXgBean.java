package com.wckj.chasstage.api.def.xgjl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SdXgBean {
    @ApiModelProperty("打卡时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dksj;
    @ApiModelProperty("打卡人员")
    private String kh;
    @ApiModelProperty("异常情况")
    private String ycqk;

    public Date getDksj() {
        return dksj;
    }

    public void setDksj(Date dksj) {
        this.dksj = dksj;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getYcqk() {
        return ycqk;
    }

    public void setYcqk(String ycqk) {
        this.ycqk = ycqk;
    }
}
