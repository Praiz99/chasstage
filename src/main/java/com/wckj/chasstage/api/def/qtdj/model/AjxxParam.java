package com.wckj.chasstage.api.def.qtdj.model;


import com.wckj.api.def.zfba.model.param.AjxxSelectParam;
import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 案件信息查询参数
 * @Package
 * @Description:
 * @date 2020-9-310:57
 */
public class AjxxParam extends BaseParam {

    @ApiParam("案件名称")
    private String ajmc;
    @ApiParam("案件编号")
    private String ajbh;
    @ApiParam("案件类型")
    private String ajlx;
    @ApiParam("主办单位编号")
    private String zbdwBh;
    @ApiParam("主办民警姓名")
    private String zbmjXm;
    @ApiParam("单位系统编号")
    private String orgSysCode;
    @ApiParam("主协办身份证")
    private String zxbSfzh;
    @ApiParam("slsj开始时间")
    private String slsjStart;
    @ApiParam("slsj结束时间")
    private String slsjEnd;

    public String getSlsjStart() {
        return slsjStart;
    }

    public void setSlsjStart(String slsjStart) {
        this.slsjStart = slsjStart;
    }

    public String getSlsjEnd() {
        return slsjEnd;
    }

    public void setSlsjEnd(String slsjEnd) {
        this.slsjEnd = slsjEnd;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getZbmjXm() {
        return zbmjXm;
    }

    public void setZbmjXm(String zbmjXm) {
        this.zbmjXm = zbmjXm;
    }

    public String getOrgSysCode() {
        return orgSysCode;
    }

    public void setOrgSysCode(String orgSysCode) {
        this.orgSysCode = orgSysCode;
    }

    public String getZxbSfzh() {
        return zxbSfzh;
    }

    public void setZxbSfzh(String zxbSfzh) {
        this.zxbSfzh = zxbSfzh;
    }
}
