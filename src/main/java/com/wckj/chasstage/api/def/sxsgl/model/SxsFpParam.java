package com.wckj.chasstage.api.def.sxsgl.model;

import io.swagger.annotations.ApiParam;

public class SxsFpParam {
    @ApiParam(value = "人员ID",required = true)
    private String ryid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "审讯室ID",required = true)
    private String qyid;
    @ApiParam(value = "使用审讯室的民警姓名")
    private String useSxsMjxm;

    public String getUseSxsMjxm() {
        return useSxsMjxm;
    }

    public void setUseSxsMjxm(String useSxsMjxm) {
        this.useSxsMjxm = useSxsMjxm;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }
}
