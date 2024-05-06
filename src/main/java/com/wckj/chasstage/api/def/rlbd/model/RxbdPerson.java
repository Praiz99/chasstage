package com.wckj.chasstage.api.def.rlbd.model;

/**
 * 海康人像网关管理平台比对人员信息实体类
 *     bzry_SJJZD_SSXQDM比中人员_实际居住地_省市县（区）
 *     bzry_SJJZ_QHNXXDZ字符串类型，比中人员_实际居住地_区划内详址
 *     bzry_HJDZ_SSXQDM字符串类型，比中人员_户籍地址_省市县（区）
 *     bzry_HJDZ_QHNXXDZ字符串类型，比中人员_户籍地址_区划内详址
 *     xtfwjdid字符串类型，协同服务节点ID
 *     txcjzdid字符串类型，图像采集终端ID
 *     bddw_GAJGJGDM字符串类型，比对单位_公安机关机构代码
 *     bddw_GAJGMC字符串类型，比对单位_公安机关名称
 *     bzry_GMSFHM比中人员_公民身份号码
 *     bzry_XM比中人员_姓名
 *     bzry_XBDM比中人员_性别代码，字典详情请看附件
 *     bzry_MZDM比中人员_民族代码，字典详情请看附件
 *     bzry_CSRQ比中人员_出生日期
 *     bzry_XP比中人员_照片（base64）
 *     bzry_TZID比中人员_特征ID
 *     bdsj比对时间,格式：yyyymmddhhmmss
 *     bzry_YJBSDM比中人员_预警标识：1-红色预警，2-橙色预警，3-黄色预警，4-不预警
 *     gzrylxdm字段目前为空
 *     zazdrgllbdm治安重点人管理类别（对应类型查看附件《重点人标签》）
 *     jyczcs建议处置措施
 *     gksy管控事由
 *     gkdw_GAJGJGDM管控单位_公安机关机构代码
 *     gkdw_GAJGMC管控单位_公安机关名称
 *     zrmj_XM责任民警_姓名
 *     zrmj_LXDH责任民警_联系电话
 *     djdw_GAJGJGDM登记单位_公安机关机构代码
 *     djdw_GAJGMC登记单位_公安机关名称
 *     djr_XM登记人_姓名
 *     djsj登记时间，时间格式：yyyymmddhhmmss
 *     earlyWarning预警标识,1-红色预警，2-橙色预警，3-黄色预警，4-不预警
 *     similarity 相似度（min/1000-1）
 */
public class RxbdPerson {
    //bzry_GMSFHM比中人员_公民身份号码
    private String bzry_GMSFHM;

    //bzry_XM比中人员_姓名
    private String bzry_XM;

    //bzry_XBDM比中人员_性别代码，字典详情请看附件
    private String bzry_XBDM;

    //bzry_CSRQ比中人员_出生日期
    private String bzry_CSRQ;

    //字符串类型，比中人员_户籍地址_省市县（区）
    private String bzry_HJDZ_SSXQDM;

    //bzry_HJDZ_QHNXXDZ字符串类型，比中人员_户籍地址_区划内详址
    private String bzry_HJDZ_QHNXXDZ;

    //bzry_MZDM比中人员_民族代码，字典详情请看附件
    private String bzry_MZDM;

    //similarity 相似度（min/1000-1）
    private Double similarity;

    //zazdrgllbdm治安重点人管理类别（对应类型查看附件《重点人标签》）
    private String zazdrgllbdm;

    public String getBzry_GMSFHM() {
        return bzry_GMSFHM;
    }

    public void setBzry_GMSFHM(String bzry_GMSFHM) {
        this.bzry_GMSFHM = bzry_GMSFHM;
    }

    public String getBzry_XM() {
        return bzry_XM;
    }

    public void setBzry_XM(String bzry_XM) {
        this.bzry_XM = bzry_XM;
    }

    public String getBzry_XBDM() {
        return bzry_XBDM;
    }

    public void setBzry_XBDM(String bzry_XBDM) {
        this.bzry_XBDM = bzry_XBDM;
    }

    public String getBzry_CSRQ() {
        return bzry_CSRQ;
    }

    public void setBzry_CSRQ(String bzry_CSRQ) {
        this.bzry_CSRQ = bzry_CSRQ;
    }

    public String getBzry_HJDZ_SSXQDM() {
        return bzry_HJDZ_SSXQDM;
    }

    public void setBzry_HJDZ_SSXQDM(String bzry_HJDZ_SSXQDM) {
        this.bzry_HJDZ_SSXQDM = bzry_HJDZ_SSXQDM;
    }

    public String getBzry_HJDZ_QHNXXDZ() {
        return bzry_HJDZ_QHNXXDZ;
    }

    public void setBzry_HJDZ_QHNXXDZ(String bzry_HJDZ_QHNXXDZ) {
        this.bzry_HJDZ_QHNXXDZ = bzry_HJDZ_QHNXXDZ;
    }

    public String getBzry_MZDM() {
        return bzry_MZDM;
    }

    public void setBzry_MZDM(String bzry_MZDM) {
        this.bzry_MZDM = bzry_MZDM;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public String getZazdrgllbdm() {
        return zazdrgllbdm;
    }

    public void setZazdrgllbdm(String zazdrgllbdm) {
        this.zazdrgllbdm = zazdrgllbdm;
    }

    @Override
    public String toString() {
        return "RxbdPerson{" +
                "bzry_GMSFHM='" + bzry_GMSFHM + '\'' +
                ", bzry_XM='" + bzry_XM + '\'' +
                ", bzry_XBDM='" + bzry_XBDM + '\'' +
                ", bzry_CSRQ='" + bzry_CSRQ + '\'' +
                ", bzry_HJDZ_SSXQDM='" + bzry_HJDZ_SSXQDM + '\'' +
                ", bzry_HJDZ_QHNXXDZ='" + bzry_HJDZ_QHNXXDZ + '\'' +
                ", bzry_MZDM='" + bzry_MZDM + '\'' +
                ", similarity=" + similarity +
                '}';
    }
}
