package com.wckj.chasstage.api.def.sxsgl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 审讯室列表信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SxsBean {
    /**
     * sxskz id
     */
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("审讯室名称")
    private String qymc;
    @ApiModelProperty("房间类型")
    private String fjlx;
    @ApiModelProperty("扩展类型")
    private String kzlx;
    @ApiModelProperty("区域原始编号")
    private String ysbh;
    @ApiModelProperty("人员数量 灯：（ > 0 亮） ， = 0 暗）， 中间图片状态（= 1 占用 ，= 0空闲）")
    private Integer rysl=0;
    @ApiModelProperty("人员id")
    private String ryid;
    @ApiModelProperty("人员编号 摄像头：（rybh!=null 亮），（rybh==null 暗）" )
    private String rybh;
    @ApiModelProperty("人员姓名")
    private String ryxm;
    @ApiModelProperty("人员性别")
    private String ryxb;
    @ApiModelProperty("入区原因")
    private String rqyy;
    @ApiModelProperty("人员类型")
    private String rylx;
    @ApiModelProperty("特殊群体")
    private String tsqt;
    @ApiModelProperty("审讯开始时间")
    private String kssj;
    @ApiModelProperty("人员照片")
    private String ryzp;
    @ApiModelProperty("开始时间")
    private long startTime;
    @ApiModelProperty("主办民警")
    private String zbmj;
    @ApiModelProperty("入所时间")
    private String rssj;
    private Integer kgzt;

    private Integer sxtzt;
    @ApiModelProperty("排风扇状态 1关 0开")
    private String pfszt;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getKzlx() {
        return kzlx;
    }

    public void setKzlx(String kzlx) {
        this.kzlx = kzlx;
    }

    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }

    public Integer getRysl() {
        return rysl;
    }

    public void setRysl(Integer rysl) {
        this.rysl = rysl;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRyxb() {
        return ryxb;
    }

    public void setRyxb(String ryxb) {
        this.ryxb = ryxb;
    }

    public String getRqyy() {
        return rqyy;
    }

    public void setRqyy(String rqyy) {
        this.rqyy = rqyy;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getRyzp() {
        return ryzp;
    }

    public void setRyzp(String ryzp) {
        this.ryzp = ryzp;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getZbmj() {
        return zbmj;
    }

    public void setZbmj(String zbmj) {
        this.zbmj = zbmj;
    }

    public String getRssj() {
        return rssj;
    }

    public void setRssj(String rssj) {
        this.rssj = rssj;
    }

    public Integer getKgzt() {
        return kgzt;
    }

    public void setKgzt(Integer kgzt) {
        this.kgzt = kgzt;
    }

    public Integer getSxtzt() {
        return sxtzt;
    }

    public void setSxtzt(Integer sxtzt) {
        this.sxtzt = sxtzt;
    }

    public String getPfszt() {
        return pfszt;
    }

    public void setPfszt(String pfszt) {
        this.pfszt = pfszt;
    }
}
