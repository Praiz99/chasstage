package com.wckj.chasstage.api.def.wpgl.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

public class SswpParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "持有人")
    private String cyr;
    @ApiParam(value = "物品名称")
    private String mc;
    @ApiParam(value = "物品状态 01在区 02出区 ")
    private String wpzt;
    @ApiParam(value = "邮寄单号")
    private String yjdh;
    @ApiParam(value = "物品类别 物品 qt 手机sj ")
    private String lb;
    @ApiParam(value = "我处理的0 本单位的 1")
    private String me;
    @ApiParam(value = "物品处理状态 本人领回01 家属带领02 委托邮寄03")
    private String wpclzt;

    @ApiParam(value = "单位代码")
    private String dwdm;
    @ApiParam(value = "人员id")
    private String ryid;

    @ApiParam(value = "人员编号")
    private String rybh;

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
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

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getWpzt() {
        return wpzt;
    }

    public void setWpzt(String wpzt) {
        this.wpzt = wpzt;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getWpclzt() {
        return wpclzt;
    }

    public void setWpclzt(String wpclzt) {
        this.wpclzt = wpclzt;
    }
}
