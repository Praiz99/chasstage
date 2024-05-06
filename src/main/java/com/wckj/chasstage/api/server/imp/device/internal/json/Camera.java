package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

/**
 * 摄像头
 */
public class Camera extends BaseDevData{
    private String nvrid;
    private String ip;
    private Integer port;
    private String userName;
    private String password;
    private String chNo;
    public String getNvrid() {
        return nvrid;
    }

    public void setNvrid(String nvrid) {
        this.nvrid = nvrid;
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

    public String getChNo() {
        return chNo;
    }

    public void setChNo(String chNo) {
        this.chNo = chNo;
    }

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getNvrid());
        chasSb.setKzcs2(this.getIp());
        chasSb.setKzcs3(this.getPort()+"");
        chasSb.setKzcs4(this.getUserName()+":"+this.getPassword());
        chasSb.setKzcs5(this.getChNo());
        return chasSb;
    }
}
