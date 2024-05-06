package com.wckj.chasstage.api.def.common.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 网络摄像头信息
 * @Package 网络摄像头信息
 * @Description: 网络摄像头信息
 * @date 2020-9-2720:20
 */
public class WlsxtBean {
    @ApiModelProperty(value = "摄像头编号")
    private String sbbh;
    @ApiModelProperty(value = "摄像头ip")
    private String ip;
    @ApiModelProperty(value = "摄像头端口")
    private String  port;
    @ApiModelProperty(value = "摄像头用户名")
    private String userName;
    @ApiModelProperty(value = "摄像头密码")
    private String password;
    @ApiModelProperty(value = "通道号")
    private String channel;
    @ApiModelProperty(value = "摄像头名称")
    private String sbmc;

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
    }

    public String getSbbh() {
        return sbbh;
    }

    public void setSbbh(String sbbh) {
        this.sbbh = sbbh;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
