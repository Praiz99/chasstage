package com.wckj.chasstage.api.def.baq.model;

import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import io.swagger.annotations.ApiParam;

public class BaqznpzBean {
    private String id;

    private String baqid;

    @ApiParam(value = "办案区配置json内容:{\"ryrs\":\"1\",\"ryrs_qtdj\":\"0\",\"ryrs_htdj\":\"1\",\"location\":\"0\",\"location_gjsp\":\"0\",\"location_gjmap\":\"0\",\"location_xl\":\"0\",\"article\":\"1\",\"article_skkg\":\"0\",\"article_zjkg\":\"0\",\"article_wlsxt\":\"0\",\"article_gpy\":\"0\",\"guard\":\"0\",\"guard_rqjh\":\"0\",\"guard_sxjh\":\"0\",\"guard_tjjh\":\"0\",\"guard_cqjh\":\"0\",\"dispatch\":\"0\",\"dispatch_sxdd\":\"0\",\"dispatch_sgcnr\":\"0\",\"sigfin\":\"0\",\"sigfin_wacom\":\"0\",\"sigfin_wc\":\"0\",\"sigfin_hs\":\"0\",\"sigfin_zz\":\"0\",\"sigfin_zk\":\"0\",\"sigfin_ylt\":\"0\",\"face\":\"0\",\"phonecab\":\"1\",\"phonecab_qrcode\":\"0\",\"dhsfp\":\"0\",\"dhsfp_jhzd\":\"0\",\"dhsfp_djym\":\"0\",\"sxsfp\":\"0\",\"sxsfp_sk\":\"0\",\"sxsfp_ym\":\"0\",\"sxsfp_zd\":\"0\",\"rycs\":\"0\",\"rycs_sk\":\"0\",\"rycs_ym\":\"0\",\"ather\":\"0\",\"ather_security_pz\":\"0\"}", required = true)
    private String configuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public BaqConfiguration getBaqConfiguration() {
        return new BaqConfiguration(configuration);
    }
}
