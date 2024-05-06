package com.wckj.chasstage.api.def.shsp.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wutl
 * @Title: 消息返回对象
 * @Package
 * @Description:
 * @date 2020-10-2915:21
 */
public class RmdMsgBean {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "消息类型")
    private String msgType;
    @ApiModelProperty(value = "消息类型名称")
    private String msgTypeName;
    @ApiModelProperty(value = "业务id")
    private String bizId;
    @ApiModelProperty(value = "业务类型")
    private String bizType;
    @ApiModelProperty(value = "数据编号")
    private String sjbh;
    @ApiModelProperty(value = "数据编号")
    private String sjmc;
    @ApiModelProperty(value = "关联业务数据")
    private String glywData;
    @ApiModelProperty(value = "消息标题")
    private String msgTitle;
    @ApiModelProperty(value = "消息正文")
    private String msgDealUrl;
    @ApiModelProperty(value = "消息来源对象名称")
    private String sendObjName;
    @ApiModelProperty(value = "消息来源对象标识")
    private String sendObjMark;
    @ApiModelProperty(value = "消息来源单位名称")
    private String sendOrgName;
    @ApiModelProperty(value = "消息来源单位编号")
    private String sendOrgCode;
    @ApiModelProperty(value = "消息来源单位系统编号")
    private String sendOrgSysCode;
    @ApiModelProperty(value = "消息状态【变更00：未处理01：已处理02：无需处理】")
    private String msgStatus;
    @ApiModelProperty(value = "是否短信提醒")
    private Integer isMobileRmd = 0;
    @ApiModelProperty(value = "是否邮件提醒")
    private Integer isMailRmd = 0;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "接收对象单位名称")
    private String recOrgName;
    @ApiModelProperty(value = "接收对象单位编号")
    private String recOrgCode;
    @ApiModelProperty(value = "接收对象单位系统编号")
    private String recOrgSysCode;
    @ApiModelProperty(value = "接收对象标识")
    private String recObjMark;
    @ApiModelProperty(value = "接收对象名称")
    private String recObjName;
    @ApiModelProperty(value = "是否删除")
    private Integer isdel = 0;
    @ApiModelProperty(value = "数据标识")
    private String dataFlag = "0";
    @ApiModelProperty(value = "消息正文")
    private String msgContent;
    @ApiModelProperty(value = "是否不再提醒")
    private Integer isNotRmd = 0;
    @ApiModelProperty(value = "是否已读")
    private Integer isRead = 0;
    @ApiModelProperty(value = "处理人姓名")
    private String clrXm;
    @ApiModelProperty(value = "处理人身份证号")
    private String clrSfzh;
    @ApiModelProperty(value = "处理人单位名称")
    private String clrDwmc;
    @ApiModelProperty(value = "处理人单位编号")
    private String clrDwbh;
    @ApiModelProperty(value = "处理人单位系统编号")
    private String clrDwxtbh;
    @ApiModelProperty(value = "处理时间")
    private Date clsj;
    @ApiModelProperty(value = "是否已短信提醒")
    private Integer isHaveMobileRmd = 0;
    @ApiModelProperty(value = "是否已邮件提醒")
    private Integer isHaveMailRmd = 0;
    @ApiModelProperty(value = "提醒开始时间")
    private Date rmdStartTime;
    @ApiModelProperty(value = "提醒截止时间")
    private Date rmdEndTime;
    @ApiModelProperty(value = "流程处理ID")
    private String nodeProId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSjbh() {
        return sjbh;
    }

    public void setSjbh(String sjbh) {
        this.sjbh = sjbh;
    }

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    public String getGlywData() {
        return glywData;
    }

    public void setGlywData(String glywData) {
        this.glywData = glywData;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgDealUrl() {
        return msgDealUrl;
    }

    public void setMsgDealUrl(String msgDealUrl) {
        this.msgDealUrl = msgDealUrl;
    }

    public String getSendObjName() {
        return sendObjName;
    }

    public void setSendObjName(String sendObjName) {
        this.sendObjName = sendObjName;
    }

    public String getSendObjMark() {
        return sendObjMark;
    }

    public void setSendObjMark(String sendObjMark) {
        this.sendObjMark = sendObjMark;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public String getSendOrgCode() {
        return sendOrgCode;
    }

    public void setSendOrgCode(String sendOrgCode) {
        this.sendOrgCode = sendOrgCode;
    }

    public String getSendOrgSysCode() {
        return sendOrgSysCode;
    }

    public void setSendOrgSysCode(String sendOrgSysCode) {
        this.sendOrgSysCode = sendOrgSysCode;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Integer getIsMobileRmd() {
        return isMobileRmd;
    }

    public void setIsMobileRmd(Integer isMobileRmd) {
        this.isMobileRmd = isMobileRmd;
    }

    public Integer getIsMailRmd() {
        return isMailRmd;
    }

    public void setIsMailRmd(Integer isMailRmd) {
        this.isMailRmd = isMailRmd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecOrgName() {
        return recOrgName;
    }

    public void setRecOrgName(String recOrgName) {
        this.recOrgName = recOrgName;
    }

    public String getRecOrgCode() {
        return recOrgCode;
    }

    public void setRecOrgCode(String recOrgCode) {
        this.recOrgCode = recOrgCode;
    }

    public String getRecOrgSysCode() {
        return recOrgSysCode;
    }

    public void setRecOrgSysCode(String recOrgSysCode) {
        this.recOrgSysCode = recOrgSysCode;
    }

    public String getRecObjMark() {
        return recObjMark;
    }

    public void setRecObjMark(String recObjMark) {
        this.recObjMark = recObjMark;
    }

    public String getRecObjName() {
        return recObjName;
    }

    public void setRecObjName(String recObjName) {
        this.recObjName = recObjName;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getIsNotRmd() {
        return isNotRmd;
    }

    public void setIsNotRmd(Integer isNotRmd) {
        this.isNotRmd = isNotRmd;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getClrXm() {
        return clrXm;
    }

    public void setClrXm(String clrXm) {
        this.clrXm = clrXm;
    }

    public String getClrSfzh() {
        return clrSfzh;
    }

    public void setClrSfzh(String clrSfzh) {
        this.clrSfzh = clrSfzh;
    }

    public String getClrDwmc() {
        return clrDwmc;
    }

    public void setClrDwmc(String clrDwmc) {
        this.clrDwmc = clrDwmc;
    }

    public String getClrDwbh() {
        return clrDwbh;
    }

    public void setClrDwbh(String clrDwbh) {
        this.clrDwbh = clrDwbh;
    }

    public String getClrDwxtbh() {
        return clrDwxtbh;
    }

    public void setClrDwxtbh(String clrDwxtbh) {
        this.clrDwxtbh = clrDwxtbh;
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    public Integer getIsHaveMobileRmd() {
        return isHaveMobileRmd;
    }

    public void setIsHaveMobileRmd(Integer isHaveMobileRmd) {
        this.isHaveMobileRmd = isHaveMobileRmd;
    }

    public Integer getIsHaveMailRmd() {
        return isHaveMailRmd;
    }

    public void setIsHaveMailRmd(Integer isHaveMailRmd) {
        this.isHaveMailRmd = isHaveMailRmd;
    }

    public Date getRmdStartTime() {
        return rmdStartTime;
    }

    public void setRmdStartTime(Date rmdStartTime) {
        this.rmdStartTime = rmdStartTime;
    }

    public Date getRmdEndTime() {
        return rmdEndTime;
    }

    public void setRmdEndTime(Date rmdEndTime) {
        this.rmdEndTime = rmdEndTime;
    }

    public String getNodeProId() {
        return nodeProId;
    }

    public void setNodeProId(String nodeProId) {
        this.nodeProId = nodeProId;
    }
}
