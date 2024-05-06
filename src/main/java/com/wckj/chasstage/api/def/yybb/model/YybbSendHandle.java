package com.wckj.chasstage.api.def.yybb.model;

import com.wckj.chasstage.api.server.imp.device.internal.IBbqOper;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 语音播报控制器
 * @Package
 * @Description:
 * @date 2020-12-2410:07
 */
public class YybbSendHandle {
    /**
     * 人员姓名
     */
    private String ryxm = "";
    /**
     * 民警姓名
     */
    private String mjxm = "";
    /**
     * 等候室名称
     */
    private String dhsmc = "";
    /**
     * 审讯室名称
     */
    private String sxsmc = "";
    /**
     * 办案区id
     */
    private String baqid = "";
    /**
     * 区域Id
     */
    private String qyid = "";
    /**
     * 播报内含
     */
    private String bbnr = "";


    public YybbSendHandle(String ryxm, String mjxm, String dhsmc, String sxsmc, String baqid, String qyid, String bbnr) {
        this.ryxm = ryxm;
        this.mjxm = mjxm;
        this.dhsmc = dhsmc;
        this.sxsmc = sxsmc;
        this.baqid = baqid;
        this.qyid = qyid;
        this.bbnr = bbnr;
    }


    public void speak() {
        //调用DC
        IBbqOper iBbqOper = ServiceContext.getServiceByClass(IBbqOper.class);
        iBbqOper.play(this.baqid, this.qyid, this.bbnr, "");
    }

    /**
     * 替换占位符
     */
    public void replaceContent() {
        if (StringUtils.isNotEmpty(this.ryxm)) {
            this.bbnr = this.bbnr.replace("(嫌疑人)", this.ryxm);
        }
        if (StringUtils.isNotEmpty(this.mjxm)) {
            this.bbnr = this.bbnr.replace("(民警)", this.mjxm);
        }
        if (StringUtils.isNotEmpty(this.dhsmc)) {
            this.bbnr = this.bbnr.replace("(等候室)", this.dhsmc);
        }
        if (StringUtils.isNotEmpty(this.sxsmc)) {
            this.bbnr = this.bbnr.replace("(审讯室)", this.sxsmc);
        }
        System.out.println(this.bbnr);
    }


    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getMjxm() {
        return mjxm;
    }

    public void setMjxm(String mjxm) {
        this.mjxm = mjxm;
    }

    public String getDhsmc() {
        return dhsmc;
    }

    public void setDhsmc(String dhsmc) {
        this.dhsmc = dhsmc;
    }

    public String getSxsmc() {
        return sxsmc;
    }

    public void setSxsmc(String sxsmc) {
        this.sxsmc = sxsmc;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getBbnr() {
        return bbnr;
    }

    public void setBbnr(String bbnr) {
        this.bbnr = bbnr;
    }
}
