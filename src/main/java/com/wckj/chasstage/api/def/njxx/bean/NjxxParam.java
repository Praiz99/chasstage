package com.wckj.chasstage.api.def.njxx.bean;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 尿检信息查询参数
 * @Package 尿检信息查询参数
 * @Description: 尿检信息查询参数
 * @date 2020-9-1616:13
 */
public class NjxxParam extends BaseParam {
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "人员姓名")
    private String ryxm;
    @ApiParam(value = "检测开始时间")
    private String jcsjStart;
    @ApiParam(value = "检测结束时间")
    private String jcsjEnd;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getJcsjStart() {
        return jcsjStart;
    }

    public void setJcsjStart(String jcsjStart) {
        this.jcsjStart = jcsjStart;
    }

    public String getJcsjEnd() {
        return jcsjEnd;
    }

    public void setJcsjEnd(String jcsjEnd) {
        this.jcsjEnd = jcsjEnd;
    }
}
