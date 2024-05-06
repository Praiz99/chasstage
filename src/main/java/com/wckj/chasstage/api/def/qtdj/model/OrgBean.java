package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;

public class OrgBean {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("单位名称")
    private String name;
    @ApiModelProperty("单位代码")
    private String code;
    @ApiModelProperty("单位简称")
    private String sname;
    @ApiModelProperty("简拼")
    private String spym;
    @ApiModelProperty("全拼")
    private String fpym;
    @ApiModelProperty("单位系统代码")
    private String sysCode;
    @ApiModelProperty("是否办案单位")
    private Integer sfbadw;
    @ApiModelProperty("单位级别")
    private Integer level;
    @ApiModelProperty("单位联系方式")
    private String dwlxfs;
    @ApiModelProperty("负责人姓名")
    private String fzrXm;
    @ApiModelProperty("负责人身份证")
    private String fzrSfz;
    @ApiModelProperty("单位地址")
    private String dwdz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpym() {
        return spym;
    }

    public void setSpym(String spym) {
        this.spym = spym;
    }

    public String getFpym() {
        return fpym;
    }

    public void setFpym(String fpym) {
        this.fpym = fpym;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getSfbadw() {
        return sfbadw;
    }

    public void setSfbadw(Integer sfbadw) {
        this.sfbadw = sfbadw;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDwlxfs() {
        return dwlxfs;
    }

    public void setDwlxfs(String dwlxfs) {
        this.dwlxfs = dwlxfs;
    }

    public String getFzrXm() {
        return fzrXm;
    }

    public void setFzrXm(String fzrXm) {
        this.fzrXm = fzrXm;
    }

    public String getFzrSfz() {
        return fzrSfz;
    }

    public void setFzrSfz(String fzrSfz) {
        this.fzrSfz = fzrSfz;
    }

    public String getDwdz() {
        return dwdz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz;
    }
}
