package com.wckj.chasstage.api.def.common.model;

import io.swagger.annotations.ApiParam;

/**
 * @author wutl
 * @Title: 基础参数接口
 * @Package
 * @Description:
 * @date 2020-9-816:46
 */
public class BaseParam {
    @ApiParam(value = "当前页")
    private Integer page;
    @ApiParam(value = "每页数")
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
