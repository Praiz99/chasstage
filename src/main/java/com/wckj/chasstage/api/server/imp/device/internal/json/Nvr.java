package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

public class Nvr extends BaseDevData{
    private String ip;
    private Integer port;
    private String userName;
    private String password;
    private String chNum;
    private String chStart;

    public String getChNum() {
        return chNum;
    }

    public void setChNum(String chNum) {
        this.chNum = chNum;
    }

    public String getChStart() {
        return chStart;
    }

    public void setChStart(String chStart) {
        this.chStart = chStart;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
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

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getIp());
        chasSb.setKzcs2(this.getPort()+"");
        chasSb.setKzcs3(this.getUserName());
        chasSb.setKzcs4(this.getPassword());
        chasSb.setKzcs5(this.getChStart());
        return chasSb;
    }
}
