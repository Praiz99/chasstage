package com.wckj.chasstage.api.def.jhrw.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class JhrwjlParam extends BaseParam {
    @ApiParam("戒护任务id")
    private String jhrwbh;
    @ApiParam("人员编号")
    private String rybh;

    public String getJhrwbh() {
        return jhrwbh;
    }

    public void setJhrwbh(String jhrwbh) {
        this.jhrwbh = jhrwbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
}
