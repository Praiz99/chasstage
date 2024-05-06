package com.wckj.chasstage.api.def.jhrw.model;

import io.swagger.annotations.ApiParam;

public class DicParam {
    @ApiParam("字典值查询关键字")
    private String queryValue;
    @ApiParam("页码")
    private int page;
    @ApiParam("每页数量")
    private int pagesize;

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
