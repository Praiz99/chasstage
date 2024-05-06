package com.wckj.chasstage.api.def.jhrw.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class JhrwParam extends BaseParam {
    @ApiParam("办案区id")
    private String baqid;
    @ApiParam("人员姓名")
    private String ryxm;
    @ApiParam("戒护人员")
    private String jhry;
    @ApiParam("任务类型")
    private String rwlx;
    @ApiParam("任务状态")
    private String rwzt;
    @ApiParam("起始入区时间")
    private String rqsj1;
    @ApiParam("结束任务开始时间")
    private String rqsj2;
    @ApiParam("起始任务开始时间")
    private String rwkssj1;
    @ApiParam("结束入区时间")
    private String rwkssj2;

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

    public String getJhry() {
        return jhry;
    }

    public void setJhry(String jhry) {
        this.jhry = jhry;
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

    public String getRqsj1() {
        return rqsj1;
    }

    public void setRqsj1(String rqsj1) {
        this.rqsj1 = rqsj1;
    }

    public String getRqsj2() {
        return rqsj2;
    }

    public void setRqsj2(String rqsj2) {
        this.rqsj2 = rqsj2;
    }

    public String getRwkssj1() {
        return rwkssj1;
    }

    public void setRwkssj1(String rwkssj1) {
        this.rwkssj1 = rwkssj1;
    }

    public String getRwkssj2() {
        return rwkssj2;
    }

    public void setRwkssj2(String rwkssj2) {
        this.rwkssj2 = rwkssj2;
    }
}
