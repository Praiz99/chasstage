package com.wckj.chasstage.api.def.shsng.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author:zengrk
 */
public class ShsngParam extends BaseParam {
    @ApiParam(value = "序号")
    private String kzcs2;

    @ApiParam(value = "状态")
    private String zt;

    @ApiParam(value = "开始发放时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ksffsj;

    @ApiParam(value = "结束发放时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jsffsj;

    @ApiParam(value = "开始归还时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String ksghsj;

    @ApiParam(value = "结束归还时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String jsghsj;
    @ApiParam(value = "nvr用户名   1(手环)  2（胸卡）")
    private String kzcs3;

    public String getKzcs3() {
        return kzcs3;
    }

    public void setKzcs3(String kzcs3) {
        this.kzcs3 = kzcs3;
    }


    public String getKzcs2() {
        return kzcs2;
    }

    public void setKzcs2(String kzcs2) {
        this.kzcs2 = kzcs2;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Date getKsffsj() {
        return ksffsj;
    }

    public void setKsffsj(Date ksffsj) {
        this.ksffsj = ksffsj;
    }

    public Date getJsffsj() {
        return jsffsj;
    }

    public void setJsffsj(Date jsffsj) {
        this.jsffsj = jsffsj;
    }

    public String getKsghsj() {
        return ksghsj;
    }

    public void setKsghsj(String ksghsj) {
        this.ksghsj = ksghsj;
    }

    public String getJsghsj() {
        return jsghsj;
    }

    public void setJsghsj(String jsghsj) {
        this.jsghsj = jsghsj;
    }
}
