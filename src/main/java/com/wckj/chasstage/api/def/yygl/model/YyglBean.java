package com.wckj.chasstage.api.def.yygl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class YyglBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("办案区id")
    private String baqid;
    @ApiModelProperty("办案区名称")
    private String baqmc;
    @ApiModelProperty("预约类型")
    private String yylx;
    @ApiModelProperty("预约人警号")
    private String yyrjh;
    @ApiModelProperty("预约人姓名")
    private String yyrxm;
    @ApiModelProperty("预约人身份证号")
    private String yyrsfzh;
    @ApiModelProperty("预约人单位代码")
    private String yyrdwdm;
    @ApiModelProperty("预约人单位名称")
    private String yyrdwmc;

    private String  kssj;
    private String jssj;
    @ApiModelProperty("人员数量")
    private Short rysl;
    @ApiModelProperty("是否使用审讯室")
    private Short sysxs;
    @ApiModelProperty("审讯室数量")
    private Short sxssl;
    @ApiModelProperty("预约备注")
    private String yybz;
    @ApiModelProperty("预约状态 0待申请 1待审批，2预约成功，3审批未通过，4预约失败,5已失效")
    private String yyzt;
    @ApiModelProperty("出所原因 出所辨认,移送，释放")
    private String csyy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("入区时间")
    private Date rqsj;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("出区时间")
    private Date cqsj;
    @ApiModelProperty("联系电话")
    private String tel;
    @ApiModelProperty("胸卡编号")
    private String shbh;
    @ApiModelProperty("出入区状态 01预约 02入区 03出区")
    private String crqzt;
    @ApiModelProperty("人员编号")
    private String rybh;
    @ApiModelProperty("来源 app App预约 client 客户端预约")
    private String from;
    @ApiModelProperty("预约人头像url,可能为空")
    private String imgUrl;
    @ApiModelProperty("车牌号")
    private String cphm;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getYylx() {
        return yylx;
    }

    public void setYylx(String yylx) {
        this.yylx = yylx;
    }

    public String getYyrjh() {
        return yyrjh;
    }

    public void setYyrjh(String yyrjh) {
        this.yyrjh = yyrjh;
    }

    public String getYyrxm() {
        return yyrxm;
    }

    public void setYyrxm(String yyrxm) {
        this.yyrxm = yyrxm;
    }

    public String getYyrsfzh() {
        return yyrsfzh;
    }

    public void setYyrsfzh(String yyrsfzh) {
        this.yyrsfzh = yyrsfzh;
    }

    public String getYyrdwdm() {
        return yyrdwdm;
    }

    public void setYyrdwdm(String yyrdwdm) {
        this.yyrdwdm = yyrdwdm;
    }

    public String getYyrdwmc() {
        return yyrdwmc;
    }

    public void setYyrdwmc(String yyrdwmc) {
        this.yyrdwmc = yyrdwmc;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public Short getRysl() {
        return rysl;
    }

    public void setRysl(Short rysl) {
        this.rysl = rysl;
    }

    public Short getSysxs() {
        return sysxs;
    }

    public void setSysxs(Short sysxs) {
        this.sysxs = sysxs;
    }

    public Short getSxssl() {
        return sxssl;
    }

    public void setSxssl(Short sxssl) {
        this.sxssl = sxssl;
    }

    public String getYybz() {
        return yybz;
    }

    public void setYybz(String yybz) {
        this.yybz = yybz;
    }

    public String getYyzt() {
        return yyzt;
    }

    public void setYyzt(String yyzt) {
        this.yyzt = yyzt;
    }

    public String getCsyy() {
        return csyy;
    }

    public void setCsyy(String csyy) {
        this.csyy = csyy;
    }

    public Date getRqsj() {
        return rqsj;
    }

    public void setRqsj(Date rqsj) {
        this.rqsj = rqsj;
    }

    public Date getCqsj() {
        return cqsj;
    }

    public void setCqsj(Date cqsj) {
        this.cqsj = cqsj;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShbh() {
        return shbh;
    }

    public void setShbh(String shbh) {
        this.shbh = shbh;
    }

    public String getCrqzt() {
        return crqzt;
    }

    public void setCrqzt(String crqzt) {
        this.crqzt = crqzt;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }
}
