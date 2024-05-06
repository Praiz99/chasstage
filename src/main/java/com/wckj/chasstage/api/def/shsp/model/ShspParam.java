package com.wckj.chasstage.api.def.shsp.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import com.wckj.jdone.modules.rmd.entity.JdoneRmdMsg;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

public class ShspParam extends BaseParam {

    @ApiModelProperty(value = "创建开始时间 (可以不填)")
    private String createStartTime;

    @ApiModelProperty(value = "创建结束时间 (可以不填)")
    private String createEndTime;
    @ApiModelProperty(value = "消息类型")
    private String msgType;
    @ApiModelProperty(value = "业务类型")
    private String bizType;
    @ApiModelProperty(value = "登录人身份证号")
    private String idCard;
    @ApiModelProperty(value = "审核状态 00 未审批 01已审批")
    private String msgStatus;
    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }
}
