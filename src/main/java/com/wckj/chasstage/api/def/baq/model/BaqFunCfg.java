package com.wckj.chasstage.api.def.baq.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author wutl
 * @Title: 办案区功能配置
 * @Package 办案区功能配置
 * @Description: 办案区功能配置
 * @date 2020-9-299:08
 */
public class BaqFunCfg {
    /**
     * 岗位一签字 (1 合盛 2威灿 3 wacon 4 汉王)
     */
    @ApiModelProperty(value = "岗位一签字 (1 合盛 2威灿 3 wacon 4 汉王)")
    private Integer gw1qz;

    /**
     * 岗位一捺印 (1 中正 2亚略特 3中控)
     */
    @ApiModelProperty(value = "岗位一捺印 (1 中正 2亚略特 3中控)")
    private Integer gw1ny;

    /**
     * 岗位二签字 (1 合盛 2威灿 3 wacon 4 汉王)
     */
    @ApiModelProperty(value = "岗位二签字 (1 合盛 2威灿 3 wacon 4 汉王)")
    private Integer gw2qz;

    /**
     * 岗位二捺印  (1 中正 2亚略特 3中控)
     */
    @ApiModelProperty(value = "岗位二捺印  (1 中正 2亚略特 3中控)")
    private Integer gw2ny;
    /**
     * 后台签字 (1 合盛 2威灿 3 wacon 4 汉王)
     */
    @ApiModelProperty(value = "后台签字 (1 合盛 2威灿 3 wacon 4 汉王)")
    private Integer htqz;

    /**
     * 后台捺印 (1 中正 2亚略特 3中控)
     */
    @ApiModelProperty(value = "后台捺印 (1 中正 2亚略特 3中控)")
    private Integer htny;


    /**
     * 读卡器配置（ 01 精伦读卡器 02 华视读卡器）
     */
    @ApiModelProperty(value = "读卡器配置（ 01 精伦读卡器 02 华视读卡器）")
    private String  dkqpz;
    /**
     * 随身物品大小柜（ 1 分大小柜  0 不分大小柜）
     */
    @ApiModelProperty(value = "随身物品大小柜（ 1 分大小柜  0 不分大小柜）")
    private Integer sswpDxg;



    /**
     * 随身物品网络摄像头（ 1 是有  0 是无）
     */
    @ApiModelProperty(value = "随身物品网络摄像头（ 1 是有  0 是无）")
    private Integer wlsxt;


    /**
     * 随身物品高拍仪拍照（ 1 是有  0 是无）
     */
    @ApiModelProperty(value = " 随身物品高拍仪拍照（ 1 是有  0 是无）")
    private Integer gpypz;

    /**
     * 出所方式（ 1 分大小柜  0 不分大小柜）
     */
    @ApiModelProperty(value = "1页面出所，0 终端出所")
    private Integer csfs;

    /**
     * 批量出所审批（ 1 需要审批  0 无需审批）
     */
    @ApiModelProperty(value = "单人出所审批（ 1 需要审批  0 无需审批）")
    private Integer drcssfsp;

    /**
     * 批量出所审批（ 1 需要审批  0 无需审批）
     */
    @ApiModelProperty(value = "批量出所审批（ 1 需要审批  0 无需审批）")
    private Integer plscsfcp;

    @ApiModelProperty(value = "是否显示手机柜（ 1 是  0 否）")
    private Integer sfsjg;

    @ApiModelProperty(value = "押送轨迹视频（ 1 是  0 否）")
    private Integer ysgjsp;

    @ApiModelProperty(value = "办案区VMS服务地址")
    private String baqVmsurl;

    @ApiModelProperty(value = "办案区人脸服务地址")
    private String faceUrl;

    @ApiModelProperty(value = "人证比对分数")
    private Integer rzbdfs;

    @ApiModelProperty(value = "是否启用心率（1启用，0不启用）")
    private Integer sfqyxl;

    @ApiModelProperty(value = "是否启用人脸定位（1启用，0不启用）")
    private Integer sfqyrldw;
    @ApiModelProperty(value = "审讯室页面刷脸分配（1启用，0不启用）")
    private Integer sxsymslfp;
    @ApiModelProperty(value = "人脸比对")
    private String rlbdType;
    @ApiModelProperty(value = "是否启用人脸比对")
    private String sfqyface;
    @ApiModelProperty(value = "自定义配置")
    private Map zdypz;

    public Map getZdypz() {
        return zdypz;
    }

    public void setZdypz(Map zdypz) {
        this.zdypz = zdypz;
    }

    public String getRlbdType() {
        return rlbdType;
    }

    public void setRlbdType(String rlbdType) {
        this.rlbdType = rlbdType;
    }

    public Integer getSxsymslfp() {
        return sxsymslfp;
    }

    public void setSxsymslfp(Integer sxsymslfp) {
        this.sxsymslfp = sxsymslfp;
    }

    public Integer getGw1qz() {
        return gw1qz;
    }

    public void setGw1qz(Integer gw1qz) {
        this.gw1qz = gw1qz;
    }

    public Integer getGw1ny() {
        return gw1ny;
    }

    public void setGw1ny(Integer gw1ny) {
        this.gw1ny = gw1ny;
    }

    public Integer getGw2qz() {
        return gw2qz;
    }

    public void setGw2qz(Integer gw2qz) {
        this.gw2qz = gw2qz;
    }

    public Integer getGw2ny() {
        return gw2ny;
    }

    public void setGw2ny(Integer gw2ny) {
        this.gw2ny = gw2ny;
    }

    public Integer getHtqz() {
        return htqz;
    }

    public void setHtqz(Integer htqz) {
        this.htqz = htqz;
    }

    public Integer getHtny() {
        return htny;
    }

    public void setHtny(Integer htny) {
        this.htny = htny;
    }

    public String getDkqpz() {
        return dkqpz;
    }

    public void setDkqpz(String dkqpz) {
        this.dkqpz = dkqpz;
    }

    public Integer getSswpDxg() {
        return sswpDxg;
    }

    public void setSswpDxg(Integer sswpDxg) {
        this.sswpDxg = sswpDxg;
    }

    public Integer getCsfs() {
        return csfs;
    }

    public void setCsfs(Integer csfs) {
        this.csfs = csfs;
    }

    public Integer getDrcssfsp() {
        return drcssfsp;
    }

    public void setDrcssfsp(Integer drcssfsp) {
        this.drcssfsp = drcssfsp;
    }

    public Integer getPlscsfcp() {
        return plscsfcp;
    }

    public void setPlscsfcp(Integer plscsfcp) {
        this.plscsfcp = plscsfcp;
    }

    public Integer getSfsjg() {
        return sfsjg;
    }

    public void setSfsjg(Integer sfsjg) {
        this.sfsjg = sfsjg;
    }

    public Integer getYsgjsp() {
        return ysgjsp;
    }

    public void setYsgjsp(Integer ysgjsp) {
        this.ysgjsp = ysgjsp;
    }

    public String getBaqVmsurl() {
        return baqVmsurl;
    }

    public void setBaqVmsurl(String baqVmsurl) {
        this.baqVmsurl = baqVmsurl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public Integer getRzbdfs() {
        return rzbdfs;
    }

    public void setRzbdfs(Integer rzbdfs) {
        this.rzbdfs = rzbdfs;
    }

    public Integer getWlsxt() {
        return wlsxt;
    }

    public void setWlsxt(Integer wlsxt) {
        this.wlsxt = wlsxt;
    }

    public Integer getGpypz() {
        return gpypz;
    }

    public void setGpypz(Integer gpypz) {
        this.gpypz = gpypz;
    }

    public Integer getSfqyxl() {
        return sfqyxl;
    }

    public void setSfqyxl(Integer sfqyxl) {
        this.sfqyxl = sfqyxl;
    }

    public Integer getSfqyrldw() {
        return sfqyrldw;
    }

    public void setSfqyrldw(Integer sfqyrldw) {
        this.sfqyrldw = sfqyrldw;
    }

    public String getSfqyface() {
        return sfqyface;
    }

    public void setSfqyface(String sfqyface) {
        this.sfqyface = sfqyface;
    }
}
