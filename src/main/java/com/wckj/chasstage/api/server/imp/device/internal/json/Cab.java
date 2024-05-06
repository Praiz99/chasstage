package com.wckj.chasstage.api.server.imp.device.internal.json;



import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;

import java.util.Date;

/**
 * 随身物品小柜
 */
public class Cab extends BaseDevData{
    private String deviceFactory;
    private String proNo;
    private String boxNo;
    private String bpcbNo;
    private String fpcbNo;
    private String fboxNo;
    private String bboxNo;
    private String cabType;
    private String boxType;
    private String useState;

    private String cabGroup;

    private String ip;
    private String bip;
    private String port;

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }


    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }




    public String getBpcbNo() {
        return bpcbNo;
    }

    public void setBpcbNo(String bpcbNo) {
        this.bpcbNo = bpcbNo;
    }

    public String getFpcbNo() {
        return fpcbNo;
    }

    public void setFpcbNo(String fpcbNo) {
        this.fpcbNo = fpcbNo;
    }

    public String getFboxNo() {
        return fboxNo;
    }

    public void setFboxNo(String fboxNo) {
        this.fboxNo = fboxNo;
    }

    public String getBboxNo() {
        return bboxNo;
    }

    public void setBboxNo(String bboxNo) {
        this.bboxNo = bboxNo;
    }

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }



    public String getCabGroup() {
        return cabGroup;
    }

    public void setCabGroup(String cabGroup) {
        this.cabGroup = cabGroup;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBip() {
        return bip;
    }

    public void setBip(String bip) {
        this.bip = bip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public ChasSswpxg toSswpxg(String wpgid){
        ChasSswpxg chasSswpxg = new ChasSswpxg();
        chasSswpxg.setId(this.getId());
        chasSswpxg.setIsdel(0);
        chasSswpxg.setBaqid(this.getOrg());
        chasSswpxg.setBaqmc(this.getOrgName());
        if (this.getCreateDate() != null) {
            chasSswpxg.setLrsj(new Date(this.getCreateDate()));
        }
        if (this.getUpdateDate() != null) {
            chasSswpxg.setXgsj(new Date(this.getUpdateDate()));
        }
        chasSswpxg.setMc(this.getName());
        chasSswpxg.setSswpgid(wpgid);
        chasSswpxg.setBh(this.getBoxNo());
        chasSswpxg.setSbId(this.getId());
        chasSswpxg.setDataflag("0");
        chasSswpxg.setLrrSfzh("admin");
        chasSswpxg.setXgrSfzh("admin");
        chasSswpxg.setSbgn(getDeviceFun());
        chasSswpxg.setBoxlx(getBoxType());
        return chasSswpxg;
    }

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getIp());
        chasSb.setKzcs2(this.getBip());
        chasSb.setKzcs3(this.getPort());
        return chasSb;
    }
}
