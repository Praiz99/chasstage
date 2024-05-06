package com.wckj.chasstage.api.def.baq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutl
 * @Title: 办案区功能配置对象
 * @Package 办案区功能配置对象
 * @Description: 办案区功能配置对象
 * @date 2020-10-1113:56
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaqGnpzBean {

    @ApiModelProperty(value = "人员入所")
    private String ryrs;
    @ApiModelProperty(value = "人员入所-前台登记")
    private String ryrs_qtdj;
    @ApiModelProperty(value = "人员入所-后台登记")
    private String ryrs_htdj;
    @ApiModelProperty(value = "人员入所-一体化采集")
    private String ryrs_ythcj;
    @ApiModelProperty(value = "人员入所-入区下发人脸")
    private String ryrs_rqxfrl;
    @ApiModelProperty(value = "人员入所-默认分配储物柜")
    private String ryrs_default_locker;
    @ApiModelProperty(value = "人员入所-随身物品大小柜")
    private String ryrs_dsg;
    @ApiModelProperty(value = "人员入所-精伦读卡器")
    private String ryrs_jl;
    @ApiModelProperty(value = "人员入所-华视读卡器")
    private String ryrs_hs;
    @ApiModelProperty(value = "人员入所-良田读卡器")
    private String ryrs_ltdkq;
    @ApiModelProperty(value = "人员入所-人证比对")
    private String ryrs_face_comparison;
    @ApiModelProperty(value = "定位")
    private String location;
    @ApiModelProperty(value = "定位-轨迹视频")
    private String location_gjsp;
    @ApiModelProperty(value = "定位-轨迹视频_押送轨迹视频")
    private String location_gjsp_ysgjsp;
    @ApiModelProperty(value = "定位-2.5D轨迹地图")
    private String location_gjmap;
    @ApiModelProperty(value = "定位-心率")
    private String location_xl;
    @ApiModelProperty(value = "定位-UWB定位")
    private String location_uwb;
    @ApiModelProperty(value = "定位-rfid定位")
    private String location_rfid;
    @ApiModelProperty(value = "定位-海康人脸")
    private String location_hkrl;
    @ApiModelProperty(value = "随身物品")
    private String article;
    @ApiModelProperty(value = "随身物品-刷卡开柜")
    private String article_skkg;
    @ApiModelProperty(value = "随身物品-直接开柜")
    private String article_zjkg;
    @ApiModelProperty(value = "随身物品-网络摄像头")
    private String article_wlsxt;
    @ApiModelProperty(value = "随身物品-高拍仪")
    private String article_gpy;
    @ApiModelProperty(value = "手机柜")
    private String phonecab;
    @ApiModelProperty(value = "手机柜-二维码")
    private String phonecab_qrcode;
    @ApiModelProperty(value = "安全检查拍照")
    private String ather_security_pz;
    @ApiModelProperty(value = "戒护任务")
    private String guard;
    @ApiModelProperty(value = "戒护任务-入区戒护")
    private String guard_rqjh;
    @ApiModelProperty(value = "戒护任务-审讯戒护")
    private String guard_sxjh;
    @ApiModelProperty(value = "戒护任务-体检戒护")
    private String guard_tjjh;
    @ApiModelProperty(value = "戒护任务-出区戒护")
    private String guard_cqjh;
    @ApiModelProperty(value = "调度任务")
    private String dispatch;
    @ApiModelProperty(value = "调度任务-审讯调度")
    private String dispatch_sxdd;
    @ApiModelProperty(value = "调度任务-适格成年人调度")
    private String dispatch_sgcnr;
    @ApiModelProperty(value = "签字捺印")
    private String sigfin;
    @ApiModelProperty(value = "签字捺印-wocam签字版")
    private String sigfin_wacom;
    @ApiModelProperty(value = "签字捺印-威灿签字版")
    private String sigfin_wc;
    @ApiModelProperty(value = "签字捺印-合盛签字版")
    private String sigfin_hs;
    @ApiModelProperty(value = "签字捺印-汉王签字版")
    private String sigfin_hw;
    @ApiModelProperty(value = "签字捺印-有方大屏")
    private String sigfin_yfdp;
    @ApiModelProperty(value = "签字捺印-中正指纹仪")
    private String sigfin_zz;
    @ApiModelProperty(value = "签字捺印-中控指纹仪")
    private String sigfin_zk;
    @ApiModelProperty(value = "签字捺印-亚略特指纹仪")
    private String sigfin_ylt;
    @ApiModelProperty(value = "岗位一-wocam签字版")
    private String gw_1_sigfin_wacom;
    @ApiModelProperty(value = "岗位一-威灿签字版")
    private String gw_1_sigfin_wc;
    @ApiModelProperty(value = "岗位一-合盛签字版")
    private String gw_1_sigfin_hs;
    @ApiModelProperty(value = "岗位一-汉王签字版")
    private String gw_1_sigfin_hw;
    @ApiModelProperty(value = "岗位一-有方大屏")
    private String gw_1_sigfin_yfdp;
    @ApiModelProperty(value = "岗位一-中正指纹仪")
    private String gw_1_sigfin_zz;
    @ApiModelProperty(value = "岗位一-中控指纹仪")
    private String gw_1_sigfin_zk;
    @ApiModelProperty(value = "岗位一-亚略特指纹仪")
    private String gw_1_sigfin_ylt;
    @ApiModelProperty(value = "岗位二-wocam签字版")
    private String gw_2_sigfin_wacom;
    @ApiModelProperty(value = "岗位二-威灿签字版")
    private String gw_2_sigfin_wc;
    @ApiModelProperty(value = "岗位二-合盛签字版")
    private String gw_2_sigfin_hs;
    @ApiModelProperty(value = "岗位二-汉王签字版")
    private String gw_2_sigfin_hw;
    @ApiModelProperty(value = "岗位二-有方大屏")
    private String gw_2_sigfin_yfdp;
    @ApiModelProperty(value = "岗位二-中正指纹仪")
    private String gw_2_sigfin_zz;
    @ApiModelProperty(value = "岗位二-中控指纹仪")
    private String gw_2_sigfin_zk;
    @ApiModelProperty(value = "岗位二-亚略特指纹仪")
    private String gw_2_sigfin_ylt;
    @ApiModelProperty(value = "人脸大数据比对")
    private String face;
    @ApiModelProperty(value = "人脸大数据比对-福清人像云平台")
    private String face_fq;
    @ApiModelProperty(value = "人脸大数据比对-厦门人像云平台")
    private String face_xm;
    @ApiModelProperty(value = "等候室分配")
    private String dhsfp;
    @ApiModelProperty(value = "等候室分配-交互终端分配")
    private String dhsfp_jhzd;
    @ApiModelProperty(value = "等候室分配-登记页面分配")
    private String dhsfp_djym;
    @ApiModelProperty(value = "审讯室分配")
    private String sxsfp;
    @ApiModelProperty(value = "审讯室分配-刷卡分配")
    private String sxsfp_sk;
    @ApiModelProperty(value = "审讯室分配-页面分配")
    private String sxsfp_ym;
    @ApiModelProperty(value = "审讯室分配-页面刷脸分配")
    private String sxsfp_ymslfp;
    @ApiModelProperty(value = "审讯室分配-交互终端分配")
    private String sxsfp_zd;
    @ApiModelProperty(value = "人员出所")
    private String rycs;
    @ApiModelProperty(value = "人员出所-刷卡分配")
    private String rycs_sk;
    @ApiModelProperty(value = "人员出所-页面分配")
    private String rycs_ym;
    @ApiModelProperty(value = "人员出所-单人出所审批")
    private String rycs_only;
    @ApiModelProperty(value = "人员出所-批量出所审批")
    private String rycs_multiple;

    @ApiModelProperty(value = "办案区人脸识别地址")
    private String baqrlsburl;

    @ApiModelProperty(value = "人脸大数据-众智平台")
    private String rldsj_zzpt;
    @ApiModelProperty(value = "众智比对地址")
    private String zz_rlbd_url;
    @ApiModelProperty(value = "众智ackey")
    private String zz_rlbd_ackey;
    @ApiModelProperty(value = "众智sekey")
    private String zz_rlbd_sekey;
    @ApiModelProperty(value = "警务百度地址")
    private String zfpt_rlsb_url;
    @ApiModelProperty(value = "人脸大数据-海康平台")
    private String rldsj_khpt;
    @ApiModelProperty(value = "办案区VMS地址配置")
    private String baqVmsurl;
    @ApiModelProperty(value = "人脸比对类型（zz中正，hk海康，yt依图")
    private String rlbd_type;
    @ApiModelProperty(value = "人脸请求地址")
    private String rlbd_url;
    @ApiModelProperty(value = "人脸请求参数")
    private String rlbd_param;
    @ApiModelProperty(value = "轨迹过滤")
    private String location_tracks;
    @ApiModelProperty(value = "海康人脸定位请求地址")
    private String hkrldw_url;
    @ApiModelProperty(value = "海康人脸定位请求参数")
    private String hkrldw_param;
    @ApiModelProperty(value = "海康人脸定位场所编码")
    private String hkrldw_place_code;
    @ApiModelProperty(value = "海康安防平台请求地址")
    private String hkafpt_url;
    @ApiModelProperty(value = "海康安防平台请求参数")
    private String hkafpt_param;
    @ApiModelProperty(value = "海康安防平台下发人脸参数")
    private String hkafptxfrl_param;
    @ApiModelProperty(value = "所选流程管控终端")
    private String checkedLcgkzd;

    public String getHkafptxfrl_param() {
        return hkafptxfrl_param;
    }

    public void setHkafptxfrl_param(String hkafptxfrl_param) {
        this.hkafptxfrl_param = hkafptxfrl_param;
    }

    public String getHkafpt_url() {
        return hkafpt_url;
    }

    public void setHkafpt_url(String hkafpt_url) {
        this.hkafpt_url = hkafpt_url;
    }

    public String getHkafpt_param() {
        return hkafpt_param;
    }

    public void setHkafpt_param(String hkafpt_param) {
        this.hkafpt_param = hkafpt_param;
    }

    public String getRyrs_rqxfrl() {
        return ryrs_rqxfrl;
    }

    public void setRyrs_rqxfrl(String ryrs_rqxfrl) {
        this.ryrs_rqxfrl = ryrs_rqxfrl;
    }

    public String getZfpt_rlsb_url() {
        return zfpt_rlsb_url;
    }

    public void setZfpt_rlsb_url(String zfpt_rlsb_url) {
        this.zfpt_rlsb_url = zfpt_rlsb_url;
    }

    public String getSxsfp_ymslfp() {
        return sxsfp_ymslfp;
    }

    public void setSxsfp_ymslfp(String sxsfp_ymslfp) {
        this.sxsfp_ymslfp = sxsfp_ymslfp;
    }

    public String getRyrs_ythcj() {
        return ryrs_ythcj;
    }

    public void setRyrs_ythcj(String ryrs_ythcj) {
        this.ryrs_ythcj = ryrs_ythcj;
    }

    public String getRyrs_jl() {
        return ryrs_jl;
    }

    public void setRyrs_jl(String ryrs_jl) {
        this.ryrs_jl = ryrs_jl;
    }

    public String getRyrs_hs() {
        return ryrs_hs;
    }

    public void setRyrs_hs(String ryrs_hs) {
        this.ryrs_hs = ryrs_hs;
    }

    public String getRyrs_face_comparison() {
        return ryrs_face_comparison;
    }

    public void setRyrs_face_comparison(String ryrs_face_comparison) {
        this.ryrs_face_comparison = ryrs_face_comparison;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_gjsp() {
        return location_gjsp;
    }

    public void setLocation_gjsp(String location_gjsp) {
        this.location_gjsp = location_gjsp;
    }

    public String getLocation_gjmap() {
        return location_gjmap;
    }

    public void setLocation_gjmap(String location_gjmap) {
        this.location_gjmap = location_gjmap;
    }

    public String getLocation_xl() {
        return location_xl;
    }

    public void setLocation_xl(String location_xl) {
        this.location_xl = location_xl;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticle_skkg() {
        return article_skkg;
    }

    public void setArticle_skkg(String article_skkg) {
        this.article_skkg = article_skkg;
    }

    public String getArticle_zjkg() {
        return article_zjkg;
    }

    public void setArticle_zjkg(String article_zjkg) {
        this.article_zjkg = article_zjkg;
    }

    public String getArticle_wlsxt() {
        return article_wlsxt;
    }

    public void setArticle_wlsxt(String article_wlsxt) {
        this.article_wlsxt = article_wlsxt;
    }

    public String getArticle_gpy() {
        return article_gpy;
    }

    public void setArticle_gpy(String article_gpy) {
        this.article_gpy = article_gpy;
    }

    public String getPhonecab() {
        return phonecab;
    }

    public void setPhonecab(String phonecab) {
        this.phonecab = phonecab;
    }

    public String getPhonecab_qrcode() {
        return phonecab_qrcode;
    }

    public void setPhonecab_qrcode(String phonecab_qrcode) {
        this.phonecab_qrcode = phonecab_qrcode;
    }

    public String getAther_security_pz() {
        return ather_security_pz;
    }

    public void setAther_security_pz(String ather_security_pz) {
        this.ather_security_pz = ather_security_pz;
    }

    public String getGuard() {
        return guard;
    }

    public void setGuard(String guard) {
        this.guard = guard;
    }

    public String getGuard_rqjh() {
        return guard_rqjh;
    }

    public void setGuard_rqjh(String guard_rqjh) {
        this.guard_rqjh = guard_rqjh;
    }

    public String getGuard_sxjh() {
        return guard_sxjh;
    }

    public void setGuard_sxjh(String guard_sxjh) {
        this.guard_sxjh = guard_sxjh;
    }

    public String getGuard_tjjh() {
        return guard_tjjh;
    }

    public void setGuard_tjjh(String guard_tjjh) {
        this.guard_tjjh = guard_tjjh;
    }

    public String getGuard_cqjh() {
        return guard_cqjh;
    }

    public void setGuard_cqjh(String guard_cqjh) {
        this.guard_cqjh = guard_cqjh;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getDispatch_sxdd() {
        return dispatch_sxdd;
    }

    public void setDispatch_sxdd(String dispatch_sxdd) {
        this.dispatch_sxdd = dispatch_sxdd;
    }

    public String getDispatch_sgcnr() {
        return dispatch_sgcnr;
    }

    public void setDispatch_sgcnr(String dispatch_sgcnr) {
        this.dispatch_sgcnr = dispatch_sgcnr;
    }

    public String getSigfin() {
        return sigfin;
    }

    public void setSigfin(String sigfin) {
        this.sigfin = sigfin;
    }

    public String getSigfin_wacom() {
        return sigfin_wacom;
    }

    public void setSigfin_wacom(String sigfin_wacom) {
        this.sigfin_wacom = sigfin_wacom;
    }

    public String getSigfin_wc() {
        return sigfin_wc;
    }

    public void setSigfin_wc(String sigfin_wc) {
        this.sigfin_wc = sigfin_wc;
    }

    public String getSigfin_hs() {
        return sigfin_hs;
    }

    public void setSigfin_hs(String sigfin_hs) {
        this.sigfin_hs = sigfin_hs;
    }

    public String getSigfin_hw() {
        return sigfin_hw;
    }

    public void setSigfin_hw(String sigfin_hw) {
        this.sigfin_hw = sigfin_hw;
    }

    public String getSigfin_zz() {
        return sigfin_zz;
    }

    public void setSigfin_zz(String sigfin_zz) {
        this.sigfin_zz = sigfin_zz;
    }

    public String getSigfin_zk() {
        return sigfin_zk;
    }

    public void setSigfin_zk(String sigfin_zk) {
        this.sigfin_zk = sigfin_zk;
    }

    public String getSigfin_ylt() {
        return sigfin_ylt;
    }

    public void setSigfin_ylt(String sigfin_ylt) {
        this.sigfin_ylt = sigfin_ylt;
    }

    public String getGw_1_sigfin_wacom() {
        return gw_1_sigfin_wacom;
    }

    public void setGw_1_sigfin_wacom(String gw_1_sigfin_wacom) {
        this.gw_1_sigfin_wacom = gw_1_sigfin_wacom;
    }

    public String getGw_1_sigfin_wc() {
        return gw_1_sigfin_wc;
    }

    public void setGw_1_sigfin_wc(String gw_1_sigfin_wc) {
        this.gw_1_sigfin_wc = gw_1_sigfin_wc;
    }

    public String getGw_1_sigfin_hs() {
        return gw_1_sigfin_hs;
    }

    public void setGw_1_sigfin_hs(String gw_1_sigfin_hs) {
        this.gw_1_sigfin_hs = gw_1_sigfin_hs;
    }

    public String getGw_1_sigfin_hw() {
        return gw_1_sigfin_hw;
    }

    public void setGw_1_sigfin_hw(String gw_1_sigfin_hw) {
        this.gw_1_sigfin_hw = gw_1_sigfin_hw;
    }

    public String getGw_1_sigfin_zz() {
        return gw_1_sigfin_zz;
    }

    public void setGw_1_sigfin_zz(String gw_1_sigfin_zz) {
        this.gw_1_sigfin_zz = gw_1_sigfin_zz;
    }

    public String getGw_1_sigfin_zk() {
        return gw_1_sigfin_zk;
    }

    public void setGw_1_sigfin_zk(String gw_1_sigfin_zk) {
        this.gw_1_sigfin_zk = gw_1_sigfin_zk;
    }

    public String getGw_1_sigfin_ylt() {
        return gw_1_sigfin_ylt;
    }

    public void setGw_1_sigfin_ylt(String gw_1_sigfin_ylt) {
        this.gw_1_sigfin_ylt = gw_1_sigfin_ylt;
    }

    public String getGw_2_sigfin_wacom() {
        return gw_2_sigfin_wacom;
    }

    public void setGw_2_sigfin_wacom(String gw_2_sigfin_wacom) {
        this.gw_2_sigfin_wacom = gw_2_sigfin_wacom;
    }

    public String getGw_2_sigfin_wc() {
        return gw_2_sigfin_wc;
    }

    public void setGw_2_sigfin_wc(String gw_2_sigfin_wc) {
        this.gw_2_sigfin_wc = gw_2_sigfin_wc;
    }

    public String getGw_2_sigfin_hs() {
        return gw_2_sigfin_hs;
    }

    public void setGw_2_sigfin_hs(String gw_2_sigfin_hs) {
        this.gw_2_sigfin_hs = gw_2_sigfin_hs;
    }

    public String getGw_2_sigfin_hw() {
        return gw_2_sigfin_hw;
    }

    public void setGw_2_sigfin_hw(String gw_2_sigfin_hw) {
        this.gw_2_sigfin_hw = gw_2_sigfin_hw;
    }

    public String getGw_2_sigfin_zz() {
        return gw_2_sigfin_zz;
    }

    public void setGw_2_sigfin_zz(String gw_2_sigfin_zz) {
        this.gw_2_sigfin_zz = gw_2_sigfin_zz;
    }

    public String getGw_2_sigfin_zk() {
        return gw_2_sigfin_zk;
    }

    public void setGw_2_sigfin_zk(String gw_2_sigfin_zk) {
        this.gw_2_sigfin_zk = gw_2_sigfin_zk;
    }

    public String getGw_2_sigfin_ylt() {
        return gw_2_sigfin_ylt;
    }

    public void setGw_2_sigfin_ylt(String gw_2_sigfin_ylt) {
        this.gw_2_sigfin_ylt = gw_2_sigfin_ylt;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace_fq() {
        return face_fq;
    }

    public void setFace_fq(String face_fq) {
        this.face_fq = face_fq;
    }

    public String getFace_xm() {
        return face_xm;
    }

    public void setFace_xm(String face_xm) {
        this.face_xm = face_xm;
    }

    public String getDhsfp() {
        return dhsfp;
    }

    public void setDhsfp(String dhsfp) {
        this.dhsfp = dhsfp;
    }

    public String getDhsfp_jhzd() {
        return dhsfp_jhzd;
    }

    public void setDhsfp_jhzd(String dhsfp_jhzd) {
        this.dhsfp_jhzd = dhsfp_jhzd;
    }

    public String getDhsfp_djym() {
        return dhsfp_djym;
    }

    public void setDhsfp_djym(String dhsfp_djym) {
        this.dhsfp_djym = dhsfp_djym;
    }

    public String getSxsfp() {
        return sxsfp;
    }

    public void setSxsfp(String sxsfp) {
        this.sxsfp = sxsfp;
    }

    public String getSxsfp_sk() {
        return sxsfp_sk;
    }

    public void setSxsfp_sk(String sxsfp_sk) {
        this.sxsfp_sk = sxsfp_sk;
    }

    public String getSxsfp_ym() {
        return sxsfp_ym;
    }

    public void setSxsfp_ym(String sxsfp_ym) {
        this.sxsfp_ym = sxsfp_ym;
    }

    public String getSxsfp_zd() {
        return sxsfp_zd;
    }

    public void setSxsfp_zd(String sxsfp_zd) {
        this.sxsfp_zd = sxsfp_zd;
    }

    public String getRycs() {
        return rycs;
    }

    public void setRycs(String rycs) {
        this.rycs = rycs;
    }

    public String getRycs_sk() {
        return rycs_sk;
    }

    public void setRycs_sk(String rycs_sk) {
        this.rycs_sk = rycs_sk;
    }

    public String getRycs_ym() {
        return rycs_ym;
    }

    public void setRycs_ym(String rycs_ym) {
        this.rycs_ym = rycs_ym;
    }

    public String getRycs_only() {
        return rycs_only;
    }

    public void setRycs_only(String rycs_only) {
        this.rycs_only = rycs_only;
    }

    public String getRycs_multiple() {
        return rycs_multiple;
    }

    public void setRycs_multiple(String rycs_multiple) {
        this.rycs_multiple = rycs_multiple;
    }

    public String getRyrs() {
        return ryrs;
    }

    public void setRyrs(String ryrs) {
        this.ryrs = ryrs;
    }

    public String getRyrs_qtdj() {
        return ryrs_qtdj;
    }

    public void setRyrs_qtdj(String ryrs_qtdj) {
        this.ryrs_qtdj = ryrs_qtdj;
    }

    public String getRyrs_htdj() {
        return ryrs_htdj;
    }

    public void setRyrs_htdj(String ryrs_htdj) {
        this.ryrs_htdj = ryrs_htdj;
    }

    public String getRyrs_default_locker() {
        return ryrs_default_locker;
    }

    public void setRyrs_default_locker(String ryrs_default_locker) {
        this.ryrs_default_locker = ryrs_default_locker;
    }

    public String getRyrs_dsg() {
        return ryrs_dsg;
    }

    public void setRyrs_dsg(String ryrs_dsg) {
        this.ryrs_dsg = ryrs_dsg;
    }

    public String getLocation_gjsp_ysgjsp() {
        return location_gjsp_ysgjsp;
    }

    public void setLocation_gjsp_ysgjsp(String location_gjsp_ysgjsp) {
        this.location_gjsp_ysgjsp = location_gjsp_ysgjsp;
    }

    public String getBaqrlsburl() {
        return baqrlsburl;
    }

    public void setBaqrlsburl(String baqrlsburl) {
        this.baqrlsburl = baqrlsburl;
    }

    public String getRldsj_zzpt() {
        return rldsj_zzpt;
    }

    public void setRldsj_zzpt(String rldsj_zzpt) {
        this.rldsj_zzpt = rldsj_zzpt;
    }

    public String getRldsj_khpt() {
        return rldsj_khpt;
    }

    public void setRldsj_khpt(String rldsj_khpt) {
        this.rldsj_khpt = rldsj_khpt;
    }

    public String getBaqVmsurl() {
        return baqVmsurl;
    }

    public void setBaqVmsurl(String baqVmsurl) {
        this.baqVmsurl = baqVmsurl;
    }

    public String getRyrs_ltdkq() {
        return ryrs_ltdkq;
    }

    public void setRyrs_ltdkq(String ryrs_ltdkq) {
        this.ryrs_ltdkq = ryrs_ltdkq;
    }

    public String getZz_rlbd_url() {
        return zz_rlbd_url;
    }

    public void setZz_rlbd_url(String zz_rlbd_url) {
        this.zz_rlbd_url = zz_rlbd_url;
    }

    public String getZz_rlbd_ackey() {
        return zz_rlbd_ackey;
    }

    public void setZz_rlbd_ackey(String zz_rlbd_ackey) {
        this.zz_rlbd_ackey = zz_rlbd_ackey;
    }

    public String getZz_rlbd_sekey() {
        return zz_rlbd_sekey;
    }

    public void setZz_rlbd_sekey(String zz_rlbd_sekey) {
        this.zz_rlbd_sekey = zz_rlbd_sekey;
    }

    public String getLocation_uwb() {
        return location_uwb;
    }

    public void setLocation_uwb(String location_uwb) {
        this.location_uwb = location_uwb;
    }

    public String getLocation_rfid() {
        return location_rfid;
    }

    public void setLocation_rfid(String location_rfid) {
        this.location_rfid = location_rfid;
    }

    public String getLocation_hkrl() {
        return location_hkrl;
    }

    public void setLocation_hkrl(String location_hkrl) {
        this.location_hkrl = location_hkrl;
    }

    public String getRlbd_type() {
        return rlbd_type;
    }

    public void setRlbd_type(String rlbd_type) {
        this.rlbd_type = rlbd_type;
    }

    public String getRlbd_url() {
        return rlbd_url;
    }

    public void setRlbd_url(String rlbd_url) {
        this.rlbd_url = rlbd_url;
    }

    public String getRlbd_param() {
        return rlbd_param;
    }

    public void setRlbd_param(String rlbd_param) {
        this.rlbd_param = rlbd_param;
    }

    public String getLocation_tracks() {
        return location_tracks;
    }

    public void setLocation_tracks(String location_tracks) {
        this.location_tracks = location_tracks;
    }

    public String getHkrldw_url() {
        return hkrldw_url;
    }

    public void setHkrldw_url(String hkrldw_url) {
        this.hkrldw_url = hkrldw_url;
    }

    public String getHkrldw_param() {
        return hkrldw_param;
    }

    public void setHkrldw_param(String hkrldw_param) {
        this.hkrldw_param = hkrldw_param;
    }

    public String getHkrldw_place_code() {
        return hkrldw_place_code;
    }

    public void setHkrldw_place_code(String hkrldw_place_code) {
        this.hkrldw_place_code = hkrldw_place_code;
    }

    public String getSigfin_yfdp() {
        return sigfin_yfdp;
    }

    public void setSigfin_yfdp(String sigfin_yfdp) {
        this.sigfin_yfdp = sigfin_yfdp;
    }

    public String getGw_1_sigfin_yfdp() {
        return gw_1_sigfin_yfdp;
    }

    public void setGw_1_sigfin_yfdp(String gw_1_sigfin_yfdp) {
        this.gw_1_sigfin_yfdp = gw_1_sigfin_yfdp;
    }

    public String getGw_2_sigfin_yfdp() {
        return gw_2_sigfin_yfdp;
    }

    public void setGw_2_sigfin_yfdp(String gw_2_sigfin_yfdp) {
        this.gw_2_sigfin_yfdp = gw_2_sigfin_yfdp;
    }

    public String getCheckedLcgkzd() {
        return checkedLcgkzd;
    }

    public void setCheckedLcgkzd(String checkedLcgkzd) {
        this.checkedLcgkzd = checkedLcgkzd;
    }
}
