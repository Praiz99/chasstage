package com.wckj.chasstage.api.def.znqt.model;

import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 智能墙体保存参数
 * @Package
 * @Description:
 * @date 2020-10-2013:48
 */
public class ZnqtParam {

    @ApiParam(value = "id主键")
    private String id;
    @ApiParam(value = "办案区id")
    private String baqid;
    @ApiParam(value = "办案区名称")
    private String baqmc;
    @ApiParam(value = "区域id")
    private String wzdm;
    @ApiParam(value = "区域名称")
    private String wzmc;
    @ApiParam(value = "备注")
    private String bz;
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

    public String getWzdm() {
        return wzdm;
    }

    public void setWzdm(String wzdm) {
        this.wzdm = wzdm;
    }

    public String getWzmc() {
        return wzmc;
    }

    public void setWzmc(String wzmc) {
        this.wzmc = wzmc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
