package com.wckj.chasstage.api.def.sxsgl.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 审讯室摄像头信息
 */
public class SxsSxtBean {
    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("端口")
    private String port;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
