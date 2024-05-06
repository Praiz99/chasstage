package com.wckj.chasstage.api.server.imp.device.internal.json;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.wd.entity.ChasWd;

/**
 * 标签、腕带设备
 */
public class Tag extends BaseDevData{

    private String hightNo;
    private String lowNo;
    private String type;
    public String getHightNo() {
        return hightNo;
    }

    public void setHightNo(String hightNo) {
        this.hightNo = hightNo;
    }

    public String getLowNo() {
        return lowNo;
    }

    public void setLowNo(String lowNo) {
        this.lowNo = lowNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChasSb toSb(){
        ChasSb chasSb = super.toSb();
        chasSb.setKzcs1(this.getHightNo());
        chasSb.setKzcs2(this.getLowNo());
        chasSb.setKzcs3(this.getType());
        return chasSb;
    }
    public static ChasWd towd(ChasSb sb){
        ChasWd wd = new ChasWd();
        wd.setId(sb.getId());
        wd.setDataflag("0");
        wd.setIsdel(0);
        wd.setLrsj(sb.getLrsj());
        wd.setXgsj(sb.getXgsj());
        wd.setBaqid(sb.getBaqid());
        wd.setBaqmc(sb.getBaqmc());
        wd.setWdbhH(sb.getKzcs1());
        wd.setWdbhL(sb.getKzcs2());
        wd.setYsid(sb.getSbbh());
        wd.setLx(sb.getKzcs3());
        return wd;
    }
}
