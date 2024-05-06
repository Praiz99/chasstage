package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

/**
 * 电子水牌
 */
public class Dzsp extends BaseDevData{
    private String ip;
    private Integer port;
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

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getIp());
        chasSb.setKzcs2(this.getPort()+"");
        return chasSb;
    }

}
