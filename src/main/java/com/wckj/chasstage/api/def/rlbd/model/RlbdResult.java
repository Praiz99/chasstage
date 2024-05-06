package com.wckj.chasstage.api.def.rlbd.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wutl
 * @Title: 人脸比对结果
 * @Package
 * @Description:
 * @date 2020-12-229:47
 */
public class RlbdResult {
    @ApiModelProperty(value = "常口库")
    private List<RlbdRyxx> ckList;
    @ApiModelProperty(value = "在逃库")
    private List<RlbdRyxx> ztList;
    @ApiModelProperty(value = "前科库")
    private List<RlbdRyxx> qkList;


    public List<RlbdRyxx> getCkList() {
        return ckList;
    }

    public void setCkList(List<RlbdRyxx> ckList) {
        this.ckList = ckList;
    }

    public List<RlbdRyxx> getZtList() {
        return ztList;
    }

    public void setZtList(List<RlbdRyxx> ztList) {
        this.ztList = ztList;
    }

    public List<RlbdRyxx> getQkList() {
        return qkList;
    }

    public void setQkList(List<RlbdRyxx> qkList) {
        this.qkList = qkList;
    }
}
