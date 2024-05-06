package com.wckj.chasstage.modules.znpz.entity;


import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;

public class ChasXtBaqznpz {
    private String id;

    private String baqid;

    private String configuration;

    private String gnpzid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public BaqConfiguration getBaqConfiguration(){
        return new BaqConfiguration(configuration);
    }

    public String getGnpzid() {
        return gnpzid;
    }

    public void setGnpzid(String gnpzid) {
        this.gnpzid = gnpzid;
    }
}
