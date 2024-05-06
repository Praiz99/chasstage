package com.wckj.chasstage.api.def.baq.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 办案区功能管理
 * @Package
 * @Description:
 * @date 2020-10-1113:57
 */
public class BaqGnglBean {

    @ApiModelProperty(value = "配置Id 主键(新增的时候无id，保存完刷新数据会有id)")
    private String id;
    @ApiModelProperty(value = "办案区Id")
    private String baqid;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "排序号")
    private String pxh;
    @ApiModelProperty(value = "是否启用")
    private String sfqy;
    @ApiModelProperty(value = "是否可编辑 1 是  0 否")
    private String sfkbj;
    @ApiModelProperty(value = "功能管理（含接口数据）")
    private BaqGnpzBean gnpzBean;
    @ApiModelProperty(value = "设备配置")
    private BaqSbpzBean sbpzBean;
    @ApiModelProperty(value = "自定义参数配置")
    private String zdyData;

    public String getZdyData() {
        return zdyData;
    }

    public void setZdyData(String zdyData) {
        this.zdyData = zdyData;
    }

    public BaqGnpzBean getGnpzBean() {
        return gnpzBean;
    }

    public void setGnpzBean(BaqGnpzBean gnpzBean) {
        this.gnpzBean = gnpzBean;
    }

    public BaqSbpzBean getSbpzBean() {
        return sbpzBean;
    }

    public void setSbpzBean(BaqSbpzBean sbpzBean) {
        this.sbpzBean = sbpzBean;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPxh() {
        return pxh;
    }

    public void setPxh(String pxh) {
        this.pxh = pxh;
    }

    public String getSfqy() {
        return sfqy;
    }

    public void setSfqy(String sfqy) {
        this.sfqy = sfqy;
    }

    public String getSfkbj() {
        return sfkbj;
    }

    public void setSfkbj(String sfkbj) {
        this.sfkbj = sfkbj;
    }
}
