package com.wckj.chasstage.api.def.ythcj.model;

import com.wckj.chasstage.modules.ythcj.entity.ChasYthcj;
import io.swagger.annotations.ApiModelProperty;

public class YthcjParam {

    @ApiModelProperty("网络信息json字符串")
    private String netJson; //网络信息json字符串
    @ApiModelProperty("社会关系json字符串")
    private String societyJson;  //社会关系json字符串
    @ApiModelProperty("网络关系删除ids")
    private String delNetIds;  //网络关系删除ids
    @ApiModelProperty("社会关系删除ids")
    private String delSocietyIds;  //社会关系删除ids
    @ApiModelProperty("写死baqryxx")
    private String fromSign;  //

    public String getNetJson() {
        return netJson;
    }

    public void setNetJson(String netJson) {
        this.netJson = netJson;
    }

    public String getSocietyJson() {
        return societyJson;
    }

    public void setSocietyJson(String societyJson) {
        this.societyJson = societyJson;
    }

    public String getDelNetIds() {
        return delNetIds;
    }

    public void setDelNetIds(String delNetIds) {
        this.delNetIds = delNetIds;
    }

    public String getDelSocietyIds() {
        return delSocietyIds;
    }

    public void setDelSocietyIds(String delSocietyIds) {
        this.delSocietyIds = delSocietyIds;
    }

    public String getFromSign() {
        return fromSign;
    }

    public void setFromSign(String fromSign) {
        this.fromSign = fromSign;
    }

}
