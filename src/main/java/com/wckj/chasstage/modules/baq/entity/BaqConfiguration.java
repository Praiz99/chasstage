package com.wckj.chasstage.modules.baq.entity;

import com.wckj.chasstage.common.util.JsonUtil;
import com.wckj.framework.core.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 办案区智能配置类
 */
public class BaqConfiguration {
    private String config;
    private Map<String, String> configMap = new HashMap<>();

    //0 未启用 1 启用
    public BaqConfiguration(String config) {
        this.config = config;
        try {
            if (StringUtils.isNotEmpty(config)) {
                this.configMap = JsonUtil.parse(config, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getIsSecurity(String key) {
        if ("1".equals(configMap.get(key))) {
            return true;
        }
        return false;
    }

    /**
     * 人员入所
     */
    public boolean getRyrs() {
        if (StringUtils.equals(configMap.get("ryrs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsQtdj() {
        if (StringUtils.equals(configMap.get("ryrs_qtdj"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsHtdj() {
        if (StringUtils.equals(configMap.get("ryrs_htdj"), "1")) {
            return true;
        }
        return false;
    }
    public boolean getRyrsRqrlxf() {
        if (StringUtils.equals(configMap.get("ryrs_rqxfrl"), "1")) {
            return true;
        }
        return false;
    }
    public boolean getRyrsDefaultLocker() {
        if (StringUtils.equals(configMap.get("ryrs_default_locker"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsDxg() {
        if (StringUtils.equals(configMap.get("ryrs_dsg"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsJl() {
        if (StringUtils.equals(configMap.get("ryrs_jl"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsHs() {
        if (StringUtils.equals(configMap.get("ryrs_hs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsLtdkq() {
        if (StringUtils.equals(configMap.get("ryrs_ltdkq"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRyrsFaceComparison() {
        if (StringUtils.equals(configMap.get("ryrs_face_comparison"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 定位
     */
    public boolean getDw() {
        if (StringUtils.equals(configMap.get("location"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwGjsp() {
        if (StringUtils.equals(configMap.get("location_gjsp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwGjspYsgjsp() {
        if (StringUtils.equals(configMap.get("location_gjsp_ysgjsp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwGjMap() {
        if (StringUtils.equals(configMap.get("location_gjmap"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwXl() {
        if (StringUtils.equals(configMap.get("location_xl"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwUwb() {
        if (StringUtils.equals(configMap.get("location_uwb"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwRfid() {
        if (StringUtils.equals(configMap.get("location_rfid"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDwHkrl() {
        if (StringUtils.equals(configMap.get("location_hkrl"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 随身物品
     */
    public boolean getSswp() {
        if (StringUtils.equals(configMap.get("article"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSswpSkkg() {
        if (StringUtils.equals(configMap.get("article_skkg"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSswpZjkg() {
        if (StringUtils.equals(configMap.get("article_zjkg"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 直接开柜 或者 刷卡终端开柜都返回 true
     *
     * @return
     */
    public boolean getSswpKg() {
        if (getSswpZjkg() || getSswpSkkg()) {
            return true;
        }
        return false;
    }

    public boolean getSswpWlsxt() {
        if (StringUtils.equals(configMap.get("article_wlsxt"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSswpGpy() {
        if (StringUtils.equals(configMap.get("article_gpy"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 手机柜
     */
    public boolean getPhonecab() {
        if (StringUtils.equals(configMap.get("phonecab"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getPhonecabQrCode() {
        if (StringUtils.equals(configMap.get("phonecab_qrcode"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 安全检查
     */
    public boolean getSecurityPicture() {
        if (StringUtils.equals(configMap.get("ather_security_pz"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 戒护任务
     */
    public boolean getJhrw() {
        if (StringUtils.equals(configMap.get("guard"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getJhrwRqjh() {
        if (StringUtils.equals(configMap.get("guard_rqjh"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getJhrwSxjh() {
        if (StringUtils.equals(configMap.get("guard_sxjh"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getJhrwTjjh() {
        if (StringUtils.equals(configMap.get("guard_tjjh"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getJhrwCqjh() {
        if (StringUtils.equals(configMap.get("guard_cqjh"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 调度任务
     */
    public boolean getDdrw() {
        if (StringUtils.equals(configMap.get("dispatch"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDdrwSxdd() {
        if (StringUtils.equals(configMap.get("dispatch_sxdd"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDdrwSgcnr() {
        if (StringUtils.equals(configMap.get("dispatch_sgcnr"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 签字捺印
     */
    public boolean getQzny() {
        if (StringUtils.equals(configMap.get("sigfin"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWacom() {
        if (StringUtils.equals(configMap.get("sigfin_wacom"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWacomGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_wacom"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWacomGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_wacom"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWc() {
        if (StringUtils.equals(configMap.get("sigfin_wc"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWcGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_wc"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyWcGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_wc"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHs() {
        if (StringUtils.equals(configMap.get("sigfin_hs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHsGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_hs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHsGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_hs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHw() {
        if (StringUtils.equals(configMap.get("sigfin_hw"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHwGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_hw"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyHwGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_hw"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZz() {
        if (StringUtils.equals(configMap.get("sigfin_zz"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZzGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_zz"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZzGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_zz"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZk() {
        if (StringUtils.equals(configMap.get("sigfin_zk"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZkGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_zk"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyZkGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_zk"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyYlt() {
        if (StringUtils.equals(configMap.get("sigfin_ylt"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyYltGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_ylt"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyYltGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_ylt"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSignFinger() {
        if (getQznyWacom() || getQznyWc() || getQznyHs() || getQznyHw()) {
            return true;
        }
        return false;
    }

    /**
     * 人脸比对
     */
    public boolean getRl() {
        if (StringUtils.equals(configMap.get("face"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getFaceFq() {
        if (StringUtils.equals(configMap.get("face_fq"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getFaceXm() {
        if (StringUtils.equals(configMap.get("face_xm"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 等候室分配
     */
    public boolean getDhs() {
        if (StringUtils.equals(configMap.get("dhsfp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDhsJhzd() {
        if (StringUtils.equals(configMap.get("dhsfp_jhzd"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getDhsDjym() {
        if (StringUtils.equals(configMap.get("dhsfp_djym"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 审讯室分配
     */
    public boolean getSxs() {
        if (StringUtils.equals(configMap.get("sxsfp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSxsSk() {
        if (StringUtils.equals(configMap.get("sxsfp_sk"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getSxsYm() {
        if (StringUtils.equals(configMap.get("sxsfp_ym"), "1")) {
            return true;
        }
        return false;
    }
    public boolean getSxsYmSlfp() {
        if (StringUtils.equals(configMap.get("sxsfp_ymslfp"), "1")) {
            return true;
        }
        return false;
    }
    public boolean getSxsZd() {
        if (StringUtils.equals(configMap.get("sxsfp_zd"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getLed() {
        if (getDhs() || getSxs()) {
            return true;
        }
        return false;
    }

    /**
     * 人员出所
     */
    public boolean getRycs() {
        if (StringUtils.equals(configMap.get("rycs"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRycsSk() {
        if (StringUtils.equals(configMap.get("rycs_sk"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getRycsYm() {
        if (StringUtils.equals(configMap.get("rycs_ym"), "1")) {
            return true;
        }
        return false;
    }

    //单人出所是否审批
    public boolean getRycsOnly() {
        if (StringUtils.equals(configMap.get("rycs_only"), "1")) {
            return true;
        }
        return false;
    }

    //多人出所是否审批
    public boolean getRycsMultiple() {
        if (StringUtils.equals(configMap.get("rycs_multiple"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 是否启用捺印功能
     *
     * @return
     */
    public boolean getSfqyny() {
        return getQznyZk() || getQznyZz() || getQznyYlt();
    }

    public boolean getQgc() {
        /*if(StringUtils.equals(configMap.get("qgc"),"1")){
            return true;
        }
        return false;*/
        return false;
    }

    public boolean getSendVms() {
        if (StringUtils.equals(configMap.get("sned_vms"), "1")) {
            return true;
        }
        return true;
    }


    /**
     * 人脸大数据-众智平台
     *
     * @return
     */
    public boolean getRldsjZzpt() {
        if (StringUtils.equals(configMap.get("rldsj_zzpt"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 人脸大数据-海康平台
     *
     * @return
     */
    public boolean getRldsjHkpt() {
        if (StringUtils.equals(configMap.get("rldsj_hkpt"), "1")) {
            return true;
        }
        return false;
    }

    /**
     * 办案区人脸识别地址
     *
     * @return
     */
    public String getBaqrlsburl() {
        return configMap.get("baqrlsburl");
    }

    /**
     * 办案区人脸识别地址
     *
     * @return
     */
    public String getBaqVmsurl() {
        return configMap.get("baqVmsurl");
    }

    /**
     * 众智比对地址
     *
     * @return
     */
    public String getZzRlbdUrl() {
        return configMap.get("zz_rlbd_url");
    }

    /**
     * 百度警务地址
     * @return
     */
    public String getZfptRlsbUrl() {
        return configMap.get("zfpt_rlsb_url");
    }
    /**
     * 众智ackey
     *
     * @return
     */
    public String getZzRlbdAckey() {
        return configMap.get("zz_rlbd_ackey");
    }

    /**
     * 众智sekey
     *
     * @return
     */
    public String getZzRlbdSekey() {
        return configMap.get("zz_rlbd_sekey");
    }

    /**
     * 人脸比对类型
     *
     * @return
     */
    public String getRlbdType() {
        return configMap.get("rlbd_type");
    }

    /**
     * 人脸比对地址
     *
     * @return
     */
    public String getRlbdUrl() {
        return configMap.get("rlbd_url");
    }

    /**
     * 人脸比对参数
     *
     * @return
     */
    public String getRlbdParam() {
        return configMap.get("rlbd_param");
    }

    /**
     * 海康人脸定位地址
     * @return
     */
    public String getHkrldwUrl() {
        return configMap.get("hkrldw_url");
    }

    /**
     * 海康人脸定位参数
     * @return
     */
    public String getHkrldwParam() {
        return configMap.get("hkrldw_param");
    }

    /**
     * 海康人脸定位场所编码
     * @return
     */
    public String getHkrldwPlaceCode() {
        return configMap.get("hkrldw_place_code");
    }

    /**
     * 海康安防平台url
     * @return
     */
    public String getHkafptUrl() {
        return configMap.get("hkafpt_url");
    }

    /**
     * 海康安防平台参数
     * @return
     */
    public String getHkafptParam() {
        return configMap.get("hkafpt_param");
    }

    /**
     * 海康安防平台下发人脸参数
     * @return
     */
    public String getHkafptxfrlParam() {
        return configMap.get("hkafptxfrl_param");
    }
    public boolean getLocationTracks(){
        return StringUtils.equals(configMap.get("location_tracks"), "1");
    }

    public String getSfqyFace(){
        return  configMap.get("face");
    }

    public boolean getQznyYfdpGw1() {
        if (StringUtils.equals(configMap.get("gw_1_sigfin_yfdp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyYfdpGw2() {
        if (StringUtils.equals(configMap.get("gw_2_sigfin_yfdp"), "1")) {
            return true;
        }
        return false;
    }

    public boolean getQznyYfdp() {
        if (StringUtils.equals(configMap.get("sigfin_yfdp"), "1")) {
            return true;
        }
        return false;
    }
}
