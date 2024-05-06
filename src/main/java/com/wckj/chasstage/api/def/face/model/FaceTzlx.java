package com.wckj.chasstage.api.def.face.model;

/**
 * @author wutl
 * @Title: 特征类型枚举
 * @Package
 * @Description:
 * @date 2020-12-213:48
 */
public enum FaceTzlx {

    MJ("民警","mj"), BAQRY("办案区人员", "baqry"), QT("其他", "qt");

    private String name;
    private String code;
    // 构造方法
    private FaceTzlx(String name, String code) {
        this.name = name;
        this.code = code;
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
}
