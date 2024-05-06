package com.wckj.chasstage.api.def.njxx.bean;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 尿检信息记录查询参数
 * @Package 尿检信息记录查询参数
 * @Description: 尿检信息记录查询参数
 * @date 2020-9-1616:47
 */
public class NjxxjlParam extends BaseParam {
    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "人员编号")
    private String rybh;
    @ApiParam(value = "被检测人姓名")
    private String bjcrXm;
    @ApiParam(value = "存取开启时间")
    private String cqsj1;
    @ApiParam(value = "存取结束时间")
    private String cqsj2;

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getBjcrXm() {
        return bjcrXm;
    }

    public void setBjcrXm(String bjcrXm) {
        this.bjcrXm = bjcrXm;
    }

    public String getCqsj1() {
        return cqsj1;
    }

    public void setCqsj1(String cqsj1) {
        this.cqsj1 = cqsj1;
    }

    public String getCqsj2() {
        return cqsj2;
    }

    public void setCqsj2(String cqsj2) {
        this.cqsj2 = cqsj2;
    }
}
