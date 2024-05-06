package com.wckj.chasstage.api.def.gwzz.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class GwzzBean {
    /**
     * <pre>
     * 主键id
     * 表字段 : chas_xt_gwlc.id
     * </pre>
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * <pre>
     * 数据是否逻辑删除
     * 表字段 : chas_xt_gwlc.isdel
     * </pre>
     */
    @ApiModelProperty(value = "数据是否逻辑删除",hidden = true)
    private Short isdel;

    /**
     * <pre>
     * 数据标示
     * 表字段 : chas_xt_gwlc.data_flag
     * </pre>
     */
    @ApiModelProperty(value = "数据标示",hidden = true)
    private String dataFlag;

    /**
     * <pre>
     * 录入人身份证号
     * 表字段 : chas_xt_gwlc.lrr_sfzh
     * </pre>
     */
    @ApiModelProperty(value = "录入人身份证号",hidden = true)
    private String lrrSfzh;

    /**
     * <pre>
     * 录入时间
     * 表字段 : chas_xt_gwlc.lrsj
     * </pre>
     */
    @ApiModelProperty(value = "录入时间",hidden = true)
    private Date lrsj;

    /**
     * <pre>
     * 修改人身份证号
     * 表字段 : chas_xt_gwlc.xgr_sfzh
     * </pre>
     */
    @ApiModelProperty(value = "修改人身份证号",hidden = true)
    private String xgrSfzh;

    /**
     * <pre>
     * 修改时间
     * 表字段 : chas_xt_gwlc.xgsj
     * </pre>
     */
    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date xgsj;

    /**
     * <pre>
     * 办案区id
     * 表字段 : chas_xt_gwlc.baqid
     * </pre>
     */
    @ApiModelProperty(value = "办案区id",hidden = true)
    private String baqid;

    /**
     * <pre>
     * 办案区名称
     * 表字段 : chas_xt_gwlc.baqmc
     * </pre>
     */
    @ApiModelProperty(value = "办案区名称")
    private String baqmc;

    /**
     * <pre>
     * 角色名称
     * 表字段 : chas_xt_gwlc.jsmc
     * </pre>
     */
    @ApiModelProperty(value = "角色名称")
    private String jsmc;

    /**
     * <pre>
     * 角色代码
     * 表字段 : chas_xt_gwlc.jsdm
     * </pre>
     */
    @ApiModelProperty(value = "角色代码")
    private String jsdm;

    /**
     * <pre>
     * 流程职责
     * 表字段 : chas_xt_gwlc.lczz
     * </pre>
     */
    @ApiModelProperty(value = "流程职责")
    private String lczz;

    /**
     * <pre>
     * 流程职责代码
     * 表字段 : chas_xt_gwlc.lczzdm
     * </pre>
     */
    @ApiModelProperty(value = "流程职责代码")
    private String lczzdm;

    /**
     * <pre>
     * 获取：主键id
     * 表字段：chas_xt_gwlc.id
     * </pre>
     *
     * @return chas_xt_gwlc.id：主键id
     */

    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键id
     * 表字段：chas_xt_gwlc.id
     * </pre>
     *
     * @param id
     *            chas_xt_gwlc.id：主键id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：数据是否逻辑删除
     * 表字段：chas_xt_gwlc.isdel
     * </pre>
     *
     * @return chas_xt_gwlc.isdel：数据是否逻辑删除
     */
    public Short getIsdel() {
        return isdel;
    }

    /**
     * <pre>
     * 设置：数据是否逻辑删除
     * 表字段：chas_xt_gwlc.isdel
     * </pre>
     *
     * @param isdel
     *            chas_xt_gwlc.isdel：数据是否逻辑删除
     */
    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    /**
     * <pre>
     * 获取：数据标示
     * 表字段：chas_xt_gwlc.data_flag
     * </pre>
     *
     * @return chas_xt_gwlc.data_flag：数据标示
     */
    public String getDataFlag() {
        return dataFlag;
    }

    /**
     * <pre>
     * 设置：数据标示
     * 表字段：chas_xt_gwlc.data_flag
     * </pre>
     *
     * @param dataFlag
     *            chas_xt_gwlc.data_flag：数据标示
     */
    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag == null ? null : dataFlag.trim();
    }

    /**
     * <pre>
     * 获取：录入人身份证号
     * 表字段：chas_xt_gwlc.lrr_sfzh
     * </pre>
     *
     * @return chas_xt_gwlc.lrr_sfzh：录入人身份证号
     */
    public String getLrrSfzh() {
        return lrrSfzh;
    }

    /**
     * <pre>
     * 设置：录入人身份证号
     * 表字段：chas_xt_gwlc.lrr_sfzh
     * </pre>
     *
     * @param lrrSfzh
     *            chas_xt_gwlc.lrr_sfzh：录入人身份证号
     */
    public void setLrrSfzh(String lrrSfzh) {
        this.lrrSfzh = lrrSfzh == null ? null : lrrSfzh.trim();
    }

    /**
     * <pre>
     * 获取：录入时间
     * 表字段：chas_xt_gwlc.lrsj
     * </pre>
     *
     * @return chas_xt_gwlc.lrsj：录入时间
     */
    public Date getLrsj() {
        return lrsj;
    }

    /**
     * <pre>
     * 设置：录入时间
     * 表字段：chas_xt_gwlc.lrsj
     * </pre>
     *
     * @param lrsj
     *            chas_xt_gwlc.lrsj：录入时间
     */
    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    /**
     * <pre>
     * 获取：修改人身份证号
     * 表字段：chas_xt_gwlc.xgr_sfzh
     * </pre>
     *
     * @return chas_xt_gwlc.xgr_sfzh：修改人身份证号
     */
    public String getXgrSfzh() {
        return xgrSfzh;
    }

    /**
     * <pre>
     * 设置：修改人身份证号
     * 表字段：chas_xt_gwlc.xgr_sfzh
     * </pre>
     *
     * @param xgrSfzh
     *            chas_xt_gwlc.xgr_sfzh：修改人身份证号
     */
    public void setXgrSfzh(String xgrSfzh) {
        this.xgrSfzh = xgrSfzh == null ? null : xgrSfzh.trim();
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：chas_xt_gwlc.xgsj
     * </pre>
     *
     * @return chas_xt_gwlc.xgsj：修改时间
     */
    public Date getXgsj() {
        return xgsj;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：chas_xt_gwlc.xgsj
     * </pre>
     *
     * @param xgsj
     *            chas_xt_gwlc.xgsj：修改时间
     */
    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * <pre>
     * 获取：办案区id
     * 表字段：chas_xt_gwlc.baqid
     * </pre>
     *
     * @return chas_xt_gwlc.baqid：办案区id
     */
    public String getBaqid() {
        return baqid;
    }

    /**
     * <pre>
     * 设置：办案区id
     * 表字段：chas_xt_gwlc.baqid
     * </pre>
     *
     * @param baqid
     *            chas_xt_gwlc.baqid：办案区id
     */
    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }

    /**
     * <pre>
     * 获取：办案区名称
     * 表字段：chas_xt_gwlc.baqmc
     * </pre>
     *
     * @return chas_xt_gwlc.baqmc：办案区名称
     */
    public String getBaqmc() {
        return baqmc;
    }

    /**
     * <pre>
     * 设置：办案区名称
     * 表字段：chas_xt_gwlc.baqmc
     * </pre>
     *
     * @param baqmc
     *            chas_xt_gwlc.baqmc：办案区名称
     */
    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc == null ? null : baqmc.trim();
    }

    /**
     * <pre>
     * 获取：角色名称
     * 表字段：chas_xt_gwlc.jsmc
     * </pre>
     *
     * @return chas_xt_gwlc.jsmc：角色名称
     */
    public String getJsmc() {
        return jsmc;
    }

    /**
     * <pre>
     * 设置：角色名称
     * 表字段：chas_xt_gwlc.jsmc
     * </pre>
     *
     * @param jsmc
     *            chas_xt_gwlc.jsmc：角色名称
     */
    public void setJsmc(String jsmc) {
        this.jsmc = jsmc == null ? null : jsmc.trim();
    }

    /**
     * <pre>
     * 获取：角色代码
     * 表字段：chas_xt_gwlc.jsdm
     * </pre>
     *
     * @return chas_xt_gwlc.jsdm：角色代码
     */
    public String getJsdm() {
        return jsdm;
    }

    /**
     * <pre>
     * 设置：角色代码
     * 表字段：chas_xt_gwlc.jsdm
     * </pre>
     *
     * @param jsdm
     *            chas_xt_gwlc.jsdm：角色代码
     */
    public void setJsdm(String jsdm) {
        this.jsdm = jsdm == null ? null : jsdm.trim();
    }

    /**
     * <pre>
     * 获取：流程职责
     * 表字段：chas_xt_gwlc.lczz
     * </pre>
     *
     * @return chas_xt_gwlc.lczz：流程职责
     */
    public String getLczz() {
        return lczz;
    }

    /**
     * <pre>
     * 设置：流程职责
     * 表字段：chas_xt_gwlc.lczz
     * </pre>
     *
     * @param lczz
     *            chas_xt_gwlc.lczz：流程职责
     */
    public void setLczz(String lczz) {
        this.lczz = lczz == null ? null : lczz.trim();
    }

    /**
     * <pre>
     * 获取：流程职责代码
     * 表字段：chas_xt_gwlc.lczzdm
     * </pre>
     *
     * @return chas_xt_gwlc.lczzdm：流程职责代码
     */
    public String getLczzdm() {
        return lczzdm;
    }

    /**
     * <pre>
     * 设置：流程职责代码
     * 表字段：chas_xt_gwlc.lczzdm
     * </pre>
     *
     * @param lczzdm
     *            chas_xt_gwlc.lczzdm：流程职责代码
     */
    public void setLczzdm(String lczzdm) {
        this.lczzdm = lczzdm == null ? null : lczzdm.trim();
    }
}
