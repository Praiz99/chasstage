package com.wckj.chasstage.api.def.jhrw.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class JhrwFpryParam extends BaseParam {
    @ApiParam("戒护任务id")
    private String jhrwId;
    @ApiParam("监护人员身份证号，以，拼接")
    private String rybhs;
    @ApiParam("监护人员姓名，以，拼接")
    private String names;

    public String getJhrwId() {
        return jhrwId;
    }

    public void setJhrwId(String jhrwId) {
        this.jhrwId = jhrwId;
    }

    public String getRybhs() {
        return rybhs;
    }

    public void setRybhs(String rybhs) {
        this.rybhs = rybhs;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
