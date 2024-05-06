package com.wckj.chasstage.api.def.qtdj.model;


import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 警情信息查询参数
 * @Package
 * @Description:
 * @date 2020-9-310:58
 */
public class JqxxParam extends BaseParam {

    @ApiParam(value = "报警时间")
    private String bjsj;

    @ApiParam(value = "处警人员")
    private String cjry;

    @ApiParam(value = "警情编号")
    private String jqbh;

    @ApiParam(value = "警情名称")
    private String jqmc;

    @ApiParam("单位系统编号")
    private String orgSysCode;

    @ApiParam("单位编号")
    private String orgCode;
    @ApiParam(value = "本人七日处警 01 本单位七日处警 02")
    private String status;

    @ApiParam(value = "cx开始时间")
    private String begin;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @ApiParam(value = "cx结束时间")

    private String end;

    public String getBjsj() {
        return bjsj;
    }

    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }

    public String getCjry() {
        return cjry;
    }

    public void setCjry(String cjry) {
        this.cjry = cjry;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public String getJqmc() {
        return jqmc;
    }

    public void setJqmc(String jqmc) {
        this.jqmc = jqmc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgSysCode() {
        return orgSysCode;
    }

    public void setOrgSysCode(String orgSysCode) {
        this.orgSysCode = orgSysCode;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
