package com.wckj.chasstage.api.def.rygj.model;

import com.wckj.chasstage.api.def.common.model.BaseParam;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author sfl
 * @Title: 人员轨迹参数
 * @Package 人员轨迹参数
 * @Description: 人员轨迹参数
 * @date 2020-9-11 9:30
 */
public class RygjParam extends BaseParam {

    @ApiParam(value = "办案区ID")
    private String baqid;
    @ApiParam(value = "人员ID")
    private String ryid;
    @ApiParam(value = "轨迹视频")
    private String gjsp;

    @ApiParam(value = "人员编号")
    private String rybh;

    @ApiParam(value = "人员类型 xyr:嫌疑人 mj:民警 fk 访客")
    private String rylx;
    @ApiParam(value = "开始时间")

    private String  lrsjStart;
    @ApiParam(value = "结束时间")
    private String  lrsjEnd;

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getGjsp() {
        return gjsp;
    }

    public void setGjsp(String gjsp) {
        this.gjsp = gjsp;
    }

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

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getLrsjStart() {
        return lrsjStart;
    }

    public void setLrsjStart(String lrsjStart) {
        this.lrsjStart = lrsjStart;
    }

    public String getLrsjEnd() {
        return lrsjEnd;
    }

    public void setLrsjEnd(String lrsjEnd) {
        this.lrsjEnd = lrsjEnd;
    }
}
