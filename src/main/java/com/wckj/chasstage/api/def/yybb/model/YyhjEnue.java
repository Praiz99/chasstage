package com.wckj.chasstage.api.def.yybb.model;

/**
 * @author wutl
 * @Title: 语音播报环节
 * @Package
 * @Description:
 * @date 2020-12-2410:09
 */
public enum YyhjEnue {

    XXDJDT("信息登记大厅","01"), RSAQJCWC("人身安全检查完成", "02"), SSWPDJWC("随身物品登记完成", "03"),FPSXSH("分配审讯室后", "04");

    private String name;
    private String code;
    // 构造方法
    YyhjEnue(String name, String code) {
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
