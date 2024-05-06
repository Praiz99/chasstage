package com.wckj.chasstage.api.server.imp.device.internal.json;

import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

/**
 * @author:zengrk
 */
public class Brake extends BaseDevData {
    private String netPort;
    private String useNet;
    private String netIp;
    private String cslxfs;
    private String ip;
    private Integer port;
    private String bh;
    private String devlx;
    private String ledid;
    private String cardreaderid;
    private String username;
    private String passwd;
    private String rlxflx;
    private String rlyxsx;

    public String getNetPort() {
        return netPort;
    }

    public void setNetPort(String netPort) {
        this.netPort = netPort;
    }

    public String getUseNet() {
        return useNet;
    }

    public void setUseNet(String useNet) {
        this.useNet = useNet;
    }

    public String getNetIp() {
        return netIp;
    }

    public void setNetIp(String netIp) {
        this.netIp = netIp;
    }

    public String getCslxfs() {
        return cslxfs;
    }

    public void setCslxfs(String cslxfs) {
        this.cslxfs = cslxfs;
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

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getDevlx() {
        return devlx;
    }

    public void setDevlx(String devlx) {
        this.devlx = devlx;
    }

    public String getLedid() {
        return ledid;
    }

    public void setLedid(String ledid) {
        this.ledid = ledid;
    }

    public String getCardreaderid() {
        return cardreaderid;
    }

    public void setCardreaderid(String cardreaderid) {
        this.cardreaderid = cardreaderid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRlxflx() {
        return rlxflx;
    }

    public void setRlxflx(String rlxflx) {
        this.rlxflx = rlxflx;
    }

    public String getRlyxsx() {
        return rlyxsx;
    }

    public void setRlyxsx(String rlyxsx) {
        this.rlyxsx = rlyxsx;
    }

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getIp());
        chasSb.setKzcs3(this.getUsername()+":"+this.getPasswd());
        return chasSb;
    }
}
