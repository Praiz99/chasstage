package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.framework.core.utils.StringUtils;

import java.util.Date;

public class BaseDevData {
    private String id;
    private String org;
    private String orgName;
    private String name;
    private String deviceType;
    private String deviceFun;
    private String deviceLocation;
    private String proNo;
    private Long updateDate;
    private Long createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceFun() {
        return deviceFun;
    }

    public void setDeviceFun(String deviceFun) {
        this.deviceFun = deviceFun;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public ChasSb toSb(){
        ChasSb chasSb = new ChasSb();
        chasSb.setId(StringUtils.getGuid32());
        chasSb.setIsdel(0);
        chasSb.setDataflag("0");
        chasSb.setBaqid(this.getOrg());
        chasSb.setBaqmc(this.getOrgName());
        if(StringUtils.isNotEmpty(this.getDeviceLocation())){
            chasSb.setQyid(this.getDeviceLocation());
        }
        chasSb.setSblx(this.getDeviceType());
        chasSb.setSbbh(this.getId());
        chasSb.setSbmc(this.getName());
        chasSb.setSbgn(this.getDeviceFun());
        chasSb.setSbzt(0);
        if (this.getCreateDate() != null) {
            chasSb.setLrsj(new Date(this.getCreateDate()));
        }
        if (this.getUpdateDate() != null) {
            chasSb.setXgsj(new Date(this.getUpdateDate()));
        }
        return chasSb;
    }
}
