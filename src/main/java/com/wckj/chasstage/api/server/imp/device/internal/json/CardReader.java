package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

/**
 * 读卡器
 */
public class CardReader extends BaseDevData{
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getIp());
        return chasSb;
    }

}
