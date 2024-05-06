package com.wckj.chasstage.api.def.shsp.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 审核确定对象
 * @Package
 * @Description:
 * @date 2020-11-1216:07
 */
public class ShcomitParam {
    @ApiModelProperty(value = "身份证号")
    private String sfzh;
    @ApiModelProperty(value = "业务id")
    private String id;
    @ApiModelProperty(value = "审批状态")
    private String spzt;
    @ApiModelProperty(value = "消息id")
    private String msgId;
    @ApiModelProperty(value = "")
    private String instId;
    @ApiModelProperty(value = "任务id")
    private String taskId;
    @ApiModelProperty(value = "审批意见")
    private String proOpinion;
    @ApiModelProperty(value = "审批类型")
    private String proType;
    @ApiModelProperty(value = "节点id")
    private String nodeId;
    @ApiModelProperty(value = "节点标识")
    private String nodeMark;
    @ApiModelProperty(value = "审批返回类型")
    private String proResultType;

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProOpinion() {
        return proOpinion;
    }

    public void setProOpinion(String proOpinion) {
        this.proOpinion = proOpinion;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    public String getProResultType() {
        return proResultType;
    }

    public void setProResultType(String proResultType) {
        this.proResultType = proResultType;
    }
}
