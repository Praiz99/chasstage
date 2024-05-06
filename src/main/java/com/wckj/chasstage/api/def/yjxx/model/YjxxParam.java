package com.wckj.chasstage.api.def.yjxx.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class YjxxParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("触发人姓名")
    private String cfrxm;
    @ApiParam("处理人姓名")
    private String clrxm;
    @ApiParam("预警类别")
    private String yjlb;
    @ApiParam("开始预警时间")
    private String startYjsj;
    @ApiParam("结束预警时间")
    private String endYjsj;
    @ApiParam("预警状态")
    private String yjzt;
    @ApiParam("触发区域id")
    private String cfqyid;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getCfrxm() {
        return cfrxm;
    }

    public void setCfrxm(String cfrxm) {
        this.cfrxm = cfrxm;
    }

    public String getClrxm() {
        return clrxm;
    }

    public void setClrxm(String clrxm) {
        this.clrxm = clrxm;
    }

    public String getYjlb() {
        return yjlb;
    }

    public void setYjlb(String yjlb) {
        this.yjlb = yjlb;
    }

    public String getStartYjsj() {
        return startYjsj;
    }

    public void setStartYjsj(String startYjsj) {
        this.startYjsj = startYjsj;
    }

    public String getEndYjsj() {
        return endYjsj;
    }

    public void setEndYjsj(String endYjsj) {
        this.endYjsj = endYjsj;
    }

    public String getYjzt() {
        return yjzt;
    }

    public void setYjzt(String yjzt) {
        this.yjzt = yjzt;
    }

    public String getCfqyid() {
        return cfqyid;
    }

    public void setCfqyid(String cfqyid) {
        this.cfqyid = cfqyid;
    }
}
