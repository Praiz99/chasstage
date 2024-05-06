package com.wckj.chasstage.api.def.baq.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 办案区参数
 * @Package 办案区参数
 * @Description: 办案区参数
 * @date 2020-9-516:54
 */
public class BaqParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;

    @ApiParam(value = "单位系统编号")
    private String dwxtbh;

    @ApiParam(value = "设备服务系统IP",hidden = true)
    private String ip;

    @ApiParam(value = "使用单位编号")
    private String sydwdm;

    @ApiParam(value = "办案区名称")
    private String baqmc;

    @ApiParam(value = "登录人身份证号")
    private String userIdCard;

    @ApiParam(value = "是否智能办案区(0否1是)")
    private String sfznbaq;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSydwdm() {
        return sydwdm;
    }

    public void setSydwdm(String sydwdm) {
        this.sydwdm = sydwdm;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getSfznbaq() {
        return sfznbaq;
    }

    public void setSfznbaq(String sfznbaq) {
        this.sfznbaq = sfznbaq;
    }
}
