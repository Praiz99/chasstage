package com.wckj.chasstage.api.def.qtdj.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;

/**
 * @author wutl
 * @Title: 办案区人员信息
 * @Package 办案区人员信息
 * @Description: 办案区人员信息
 * @date 2020-9-2315:22
 */
public class RyxxBean {
    @ApiModelProperty("主键id")
    @ApiParam(value = "人员id(新增不传，修改带id)")
    private String id;

    @ApiParam(value = "办案区ID")
    @ApiModelProperty("办案区id")
    private String baqid;

    @ApiParam(value = "办案区名称")
    @ApiModelProperty("办案区名称")
    private String baqmc;

    @ApiParam(value = "rybh,如果是盘查人员入所，需要带入人员编号")
    @ApiModelProperty("人员编号")
    private String rybh;

    @ApiParam(value = "姓名")
    @ApiModelProperty("人员姓名")
    private String ryxm;

    @ApiParam(value = "证件类型")
    @ApiModelProperty("证件类型 字典 CHAS_ZD_CASE_ZJLX")
    private String zjlx;

    @ApiModelProperty("证件号码")
    @ApiParam(value = "证件号码")
    private String rySfzh;

    @ApiModelProperty("性别")
    @ApiParam(value = "性别 字典 CHAS_ZD_ZB_XB")
    private String xb;

    @ApiModelProperty("民族")
    @ApiParam(value = "民族")
    private String mz;

    @ApiModelProperty("身份")
    @ApiParam(value = "身份")
    private String sf;

    @ApiModelProperty("出生日期")
    @ApiParam(value = "出生日期")
    private Date csrq;

    @ApiModelProperty("年龄")
    @ApiParam(value = "年龄")
    private Integer nl;

    @ApiModelProperty("现住地址")
    @ApiParam(value = "现住地址")
    private String xzdxz;

    @ApiModelProperty("到案方式")
    @ApiParam(value = "到案方式 字典 DAFS")
    private String dafs;

    @ApiModelProperty("人员来源")
    @ApiParam(value = "人员来源",hidden = true)
    private String ryly;

    @ApiModelProperty("入所原因名称")
    @ApiParam(value = "入所原因名称")
    private String ryZaymc;

    @ApiModelProperty("入所时间")
    @ApiParam(value = "入所时间")
    private Date rRssj;

    @ApiModelProperty("人员类型")
    @ApiParam(value = "人员类型")
    private String rylx;

    @ApiModelProperty("主办民警")
    @ApiParam(value = "主办民警")
    private String mjSfzh;

    @ApiModelProperty("是否严重疾病")
    @ApiParam(value = "是否严重疾病",hidden = true)
    private Integer rSfyzjb;

    @ApiModelProperty("严重疾病")
    @ApiParam(value = "严重疾病",hidden = true)
    private String rYzjb;

    @ApiModelProperty("是否伤势检查")
    @ApiParam(value = "是否伤势检查",hidden = true)
    private Integer rSfssjc;

    @ApiModelProperty("伤势描述")
    @ApiParam(value = "伤势描述",hidden = true)
    private String rSsms;

    @ApiModelProperty("是否特殊体表特征")
    @ApiParam(value = "是否特殊体表特征",hidden = true)
    private Integer rSjtstbtz;

    @ApiModelProperty("有无伤情")
    @ApiParam(value = "有无伤情",hidden = true)
    private Integer rSfzs;

    @ApiModelProperty("伤势成因")
    @ApiParam(value = "伤势成因",hidden = true)
    private String rZsss;

    @ApiModelProperty("人员出所去向")
    @ApiParam(value = "人员出所去向",hidden = true)
    private String cRyqx;

    @ApiModelProperty("出所时间")
    @ApiParam(value = "出所时间",hidden = true)
    private Date cCssj;

    @ApiModelProperty("出所原因")
    @ApiParam(value = "出所原因",hidden = true)
    private String cCsyy;

    @ApiModelProperty("是否有随身物品")
    @ApiParam(value = "是否有随身物品",hidden = true)
    private Integer sfywp;

    @ApiModelProperty("是否特殊群体")
    @ApiParam(value = "是否特殊群体")
    private String sftsqt;

    @ApiModelProperty("殊特群体")
    @ApiParam(value = "殊特群体(CHAS_ZD_CASE_TSQT)")
    private String tsqt;

    @ApiModelProperty("人员状态")
    @ApiParam(value = "人员状态",hidden = true)
    private String ryzt;

    @ApiModelProperty("是否严重疾病")
    @ApiParam(value = "是否严重疾病",hidden = true)
    private Integer cSfyzjb;

    @ApiModelProperty("严重疾病描述")
    @ApiParam(value = "严重疾病描述",hidden = true)
    private String cYzjb;

    @ApiModelProperty("是否检查伤势")
    @ApiParam(value = "是否检查伤势",hidden = true)
    private Integer cSfssjc;

    @ApiModelProperty("伤势描述")
    @ApiParam(value = "伤势描述",hidden = true)
    private String cSsms;

    @ApiModelProperty("是否特殊体表特征")
    @ApiParam(value = "是否特殊体表特征",hidden = true)
    private Integer cSjtstbtz;

    @ApiModelProperty("否是自述伤势")
    @ApiParam(value = "否是自述伤势",hidden = true)
    private Integer cSfzs;

    @ApiModelProperty("自述伤势")
    @ApiParam(value = "自述伤势",hidden = true)
    private String cZsss;

    @ApiModelProperty("是否自报名")
    @ApiParam(value = "是否自报名")
    private Integer sfzbxm;

    @ApiModelProperty("主办单位编号")
    @ApiParam(value = "主办单位编号",hidden = true)
    private String zbdwBh;

    @ApiModelProperty("联系电话")
    @ApiParam(value = "联系电话")
    private String lxdh;

    @ApiModelProperty("户籍")
    @ApiParam(value = "户籍")
    private String hjdXzqh;

    @ApiModelProperty("户籍地")
    @ApiParam(value = "户籍地")
    private String hjdxz;

    @ApiModelProperty("关押状态（01，在所，02，送押，03，已关押）")
    @ApiParam(value = "关押状态（01，在所，02，送押，03，已关押）",hidden = true)
    private String gyzt;

    @ApiModelProperty("抓获地点")
    @ApiParam(value = "抓获地点）")
    private String zhdd;

    @ApiModelProperty("公章名称（打印公章）")
    @ApiParam(value = "公章名称（打印公章）",hidden = true)
    private String signname;

    @ApiModelProperty("入所原因（案由编号）")
    @ApiParam(value = "入所原因（案由编号 字典 RSYY）")
    private String ryZaybh;

    @ApiModelProperty("家属联系电话")
    @ApiParam(value = "家属联系电话",hidden = true)
    private String jslxdh;

    @ApiModelProperty("家属与嫌疑人关系")
    @ApiParam(value = "家属与嫌疑人关系",hidden = true)
    private String jsgx;

    @ApiModelProperty("国籍")
    @ApiParam(value = "国籍")
    private String gj;

    @ApiModelProperty("同步时间")
    @ApiParam(value = "同步时间",hidden = true)
    private Date tbsj;

    @ApiModelProperty("照片id")
    @ApiParam(value = "照片id")
    private String zpid;

    @ApiModelProperty("址住行政区划")
    @ApiParam(value = "址住行政区划")
    private String xzdXzqh;

    @ApiModelProperty("是否身份不明")
    @ApiParam(value = "是否身份不明")
    private Integer sfsfbm;

    @ApiModelProperty("工作单位")
    @ApiParam(value = "工作单位")
    private String gzdw;

    @ApiModelProperty("别名")
    @ApiParam(value = "别名")
    private String bm;

    @ApiModelProperty("绰号")
    @ApiParam(value = "绰号")
    private String ch;

    @ApiModelProperty("曾用名")
    @ApiParam(value = "曾用名")
    private String cym;

    @ApiModelProperty("文化程度")
    @ApiParam(value = "文化程度")
    private String whcd;

    @ApiModelProperty("政治面貌")
    @ApiParam(value = "政治面貌")
    private String zzmm;

    @ApiModelProperty("到案方式")
    @ApiParam(value = "到案方式，DAFS 取字典名称")
    private String dafsText;

    @ApiModelProperty("是否同步至涉案人员")
    @ApiParam(value = "是否同步至涉案人员",hidden = true)
    private Integer sftb;

    @ApiModelProperty("办案区使用情况编号")
    @ApiParam(value = "办案区使用情况编号",hidden = true)
    private String baqsyqkbh;

    @ApiModelProperty("一体化采集状态")
    @ApiParam(value = "一体化采集状态",hidden = true)
    private Integer sfythcj;

    @ApiModelProperty("显示颜色")
    @ApiParam(value = "显示颜色",hidden = true)
    private String xsys;

    @ApiModelProperty("安全检查开始时间")
    @ApiParam(value = "安全检查开始时间")
    private Date aqjckssj;

    @ApiModelProperty("安全检查结束时间")
    @ApiParam(value = "安全检查结束时间")
    private Date aqjcjssj;

    @ApiModelProperty("警情编号")
    @ApiParam(value = "警情编号",hidden = true)
    private String jqbh;

    @ApiModelProperty("案件编号")
    @ApiParam(value = "案件编号",hidden = true)
    private String ajbh;

    @ApiModelProperty("主办民警姓名")
    @ApiParam(value = "主办民警姓名")
    private String mjXm;

    @ApiModelProperty("主办单位名称")
    @ApiParam(value = "主办单位名称")
    private String zbdwMc;

    @ApiModelProperty("主办单位系统编号")
    @ApiParam(value = "主办单位系统编号",hidden = true)
    private String dwxtbh;

    @ApiModelProperty("伤势材料ID")
    @ApiParam(value = "伤势材料ID",hidden = true)
    private String ssclid;

    @ApiModelProperty("null")
    @ApiParam(value = "null",hidden = true)
    private Integer sfznbaq;

    @ApiModelProperty("抓获时间")
    @ApiParam(value = "抓获时间")
    private Date zhsj;

    @ApiModelProperty("抓获地行政区划代码")
    @ApiParam(value = "抓获地行政区划代码")
    private String zhdxzqh;

    @ApiModelProperty("抓获地行政区划名称")
    @ApiParam(value = "抓获地行政区划名称")
    private String zhdxzqhmc;

    @ApiModelProperty("随身物品状态（字典:无、未处理、部分处理、已处理）")
    @ApiParam(value = "随身物品状态（字典:无、未处理、部分处理、已处理）",hidden = true)
    private String sswpzt;

    @ApiModelProperty("常口暂口照片ID")
    @ApiParam(value = "常口暂口照片ID",hidden = true)
    private String ckzkzpid;

    @ApiModelProperty("出所后再次修改标示（0未修改，1修改过）")
    @ApiParam(value = "出所后再次修改标示（0未修改，1修改过）",hidden = true)
    private Integer xgbs;

    @ApiModelProperty("审核状态")
    @ApiParam(value = "审核状态",hidden = true)
    private String shzt;

    @ApiModelProperty("时间戳")
    @ApiParam(value = "时间戳",hidden = true)
    private Date sjbq;

    @ApiModelProperty("是否饮酒")
    @ApiParam(value = "是否饮酒",hidden = true)
    private Integer rSfyj;

    @ApiModelProperty("人体标记")
    @ApiParam(value = "人体标记",hidden = true)
    private String rtbj;

    @ApiModelProperty("协办民警身份证号，多个使用逗号隔开")
    @ApiParam(value = "协办民警身份证号，多个使用逗号隔开",hidden = true)
    private String xbMjSfzh;

    @ApiModelProperty("协办民警姓名,多个时使用逗号隔开")
    @ApiParam(value = "协办民警姓名,多个时使用逗号隔开",hidden = true)
    private String xbMjXm;
    @ApiParam(hidden = true)
    private String ryztMulti;
    @ApiModelProperty("入所开始时间")
    private String rssj1;
    @ApiModelProperty("入所结束时间")
    private String rssj2;
    @ApiParam(hidden = true)
    private String cssj1;
    @ApiParam(hidden = true)
    private String cssj2;
    @ApiParam(hidden = true)
    private String csrq1;
    @ApiParam(hidden = true)
    private String csrq2;
    @ApiParam(hidden = true)
    private List<String> rybhList;
    @ApiParam(hidden = true)
    private List<String> ryztList;
    @ApiParam(hidden = true)
    private String lrsjStart;
    @ApiParam(hidden = true)
    private String lrsjEnd;

    @ApiModelProperty("储物柜id")
    @ApiParam(value = "储物柜id")
    private String cwgid;
    @ApiModelProperty("储物柜类型")
    @ApiParam(value = "储物柜类型")
    private String cwglx;
    @ApiModelProperty("储物柜编号")
    @ApiParam(value = "储物柜编号")
    private String cwgbh;
    @ApiModelProperty("手机柜编号")
    @ApiParam(value = "手机柜编号")
    private String sjgBh;
    @ApiModelProperty("手机柜编号")
    @ApiParam(value = "手机柜id")
    private String sjgId;
    @ApiModelProperty("低频腕带编号")
    @ApiParam(value = "低频腕带编号")
    private String wdbhL;
    @ApiModelProperty("高频腕带编号")
    @ApiParam(value = "高频腕带编号",hidden = true)
    private String wdbhH;
    @ApiModelProperty("是否重点看管")
    @ApiParam(value = "是否重点看管",hidden = true)
    private String ddkg;
    @ApiModelProperty("关联警情")
    @ApiParam(value = "关联警情")
    private String ywbhStr;
    @ApiModelProperty("同案人员编号（多个逗号可开）")
    @ApiParam(value = "同案人员编号（多个逗号可开）")
    private String tarybh;
    @ApiParam(hidden = true)
    private String rsWpAllBizId;
    @ApiParam(hidden = true)
    private String isUpdateywbh;

    private String zjlxName;

    private String xbName;

    private String tsqtName;

    private String dafsName;

    private String rylxName;
    @ApiModelProperty("预约体检")
    @ApiParam(value = "预约体检")
    private String yytj;


    @ApiModelProperty("送押车牌号码")
    private String sycphm;
    @ApiModelProperty("心率状态（0无心率数据，1低心率，2正常心率，3高心率）")
    private String xlzt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaqid() {
        return baqid;
    }

    public void setBaqid(String baqid) {
        this.baqid = baqid;
    }

    public String getBaqmc() {
        return baqmc;
    }

    public void setBaqmc(String baqmc) {
        this.baqmc = baqmc;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getRySfzh() {
        return rySfzh;
    }

    public void setRySfzh(String rySfzh) {
        this.rySfzh = rySfzh;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    public String getXzdxz() {
        return xzdxz;
    }

    public void setXzdxz(String xzdxz) {
        this.xzdxz = xzdxz;
    }

    public String getDafs() {
        return dafs;
    }

    public void setDafs(String dafs) {
        this.dafs = dafs;
    }

    public String getRyly() {
        return ryly;
    }

    public void setRyly(String ryly) {
        this.ryly = ryly;
    }

    public String getRyZaymc() {
        return ryZaymc;
    }

    public void setRyZaymc(String ryZaymc) {
        this.ryZaymc = ryZaymc;
    }

    public Date getrRssj() {
        return rRssj;
    }

    public void setrRssj(Date rRssj) {
        this.rRssj = rRssj;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getMjSfzh() {
        return mjSfzh;
    }

    public void setMjSfzh(String mjSfzh) {
        this.mjSfzh = mjSfzh;
    }

    public Integer getrSfyzjb() {
        return rSfyzjb;
    }

    public void setrSfyzjb(Integer rSfyzjb) {
        this.rSfyzjb = rSfyzjb;
    }

    public String getrYzjb() {
        return rYzjb;
    }

    public void setrYzjb(String rYzjb) {
        this.rYzjb = rYzjb;
    }

    public Integer getrSfssjc() {
        return rSfssjc;
    }

    public void setrSfssjc(Integer rSfssjc) {
        this.rSfssjc = rSfssjc;
    }

    public String getrSsms() {
        return rSsms;
    }

    public void setrSsms(String rSsms) {
        this.rSsms = rSsms;
    }

    public Integer getrSjtstbtz() {
        return rSjtstbtz;
    }

    public void setrSjtstbtz(Integer rSjtstbtz) {
        this.rSjtstbtz = rSjtstbtz;
    }

    public Integer getrSfzs() {
        return rSfzs;
    }

    public void setrSfzs(Integer rSfzs) {
        this.rSfzs = rSfzs;
    }

    public String getrZsss() {
        return rZsss;
    }

    public void setrZsss(String rZsss) {
        this.rZsss = rZsss;
    }

    public String getcRyqx() {
        return cRyqx;
    }

    public void setcRyqx(String cRyqx) {
        this.cRyqx = cRyqx;
    }

    public Date getcCssj() {
        return cCssj;
    }

    public void setcCssj(Date cCssj) {
        this.cCssj = cCssj;
    }

    public String getcCsyy() {
        return cCsyy;
    }

    public void setcCsyy(String cCsyy) {
        this.cCsyy = cCsyy;
    }

    public Integer getSfywp() {
        return sfywp;
    }

    public void setSfywp(Integer sfywp) {
        this.sfywp = sfywp;
    }

    public String getSftsqt() {
        return sftsqt;
    }

    public void setSftsqt(String sftsqt) {
        this.sftsqt = sftsqt;
    }

    public String getTsqt() {
        return tsqt;
    }

    public void setTsqt(String tsqt) {
        this.tsqt = tsqt;
    }

    public String getRyzt() {
        return ryzt;
    }

    public void setRyzt(String ryzt) {
        this.ryzt = ryzt;
    }

    public Integer getcSfyzjb() {
        return cSfyzjb;
    }

    public void setcSfyzjb(Integer cSfyzjb) {
        this.cSfyzjb = cSfyzjb;
    }

    public String getcYzjb() {
        return cYzjb;
    }

    public void setcYzjb(String cYzjb) {
        this.cYzjb = cYzjb;
    }

    public Integer getcSfssjc() {
        return cSfssjc;
    }

    public void setcSfssjc(Integer cSfssjc) {
        this.cSfssjc = cSfssjc;
    }

    public String getcSsms() {
        return cSsms;
    }

    public void setcSsms(String cSsms) {
        this.cSsms = cSsms;
    }

    public Integer getcSjtstbtz() {
        return cSjtstbtz;
    }

    public void setcSjtstbtz(Integer cSjtstbtz) {
        this.cSjtstbtz = cSjtstbtz;
    }

    public Integer getcSfzs() {
        return cSfzs;
    }

    public void setcSfzs(Integer cSfzs) {
        this.cSfzs = cSfzs;
    }

    public String getcZsss() {
        return cZsss;
    }

    public void setcZsss(String cZsss) {
        this.cZsss = cZsss;
    }

    public Integer getSfzbxm() {
        return sfzbxm;
    }

    public void setSfzbxm(Integer sfzbxm) {
        this.sfzbxm = sfzbxm;
    }

    public String getZbdwBh() {
        return zbdwBh;
    }

    public void setZbdwBh(String zbdwBh) {
        this.zbdwBh = zbdwBh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getHjdXzqh() {
        return hjdXzqh;
    }

    public void setHjdXzqh(String hjdXzqh) {
        this.hjdXzqh = hjdXzqh;
    }

    public String getHjdxz() {
        return hjdxz;
    }

    public void setHjdxz(String hjdxz) {
        this.hjdxz = hjdxz;
    }

    public String getGyzt() {
        return gyzt;
    }

    public void setGyzt(String gyzt) {
        this.gyzt = gyzt;
    }

    public String getZhdd() {
        return zhdd;
    }

    public void setZhdd(String zhdd) {
        this.zhdd = zhdd;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getRyZaybh() {
        return ryZaybh;
    }

    public void setRyZaybh(String ryZaybh) {
        this.ryZaybh = ryZaybh;
    }

    public String getJslxdh() {
        return jslxdh;
    }

    public void setJslxdh(String jslxdh) {
        this.jslxdh = jslxdh;
    }

    public String getJsgx() {
        return jsgx;
    }

    public void setJsgx(String jsgx) {
        this.jsgx = jsgx;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public Date getTbsj() {
        return tbsj;
    }

    public void setTbsj(Date tbsj) {
        this.tbsj = tbsj;
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getXzdXzqh() {
        return xzdXzqh;
    }

    public void setXzdXzqh(String xzdXzqh) {
        this.xzdXzqh = xzdXzqh;
    }

    public Integer getSfsfbm() {
        return sfsfbm;
    }

    public void setSfsfbm(Integer sfsfbm) {
        this.sfsfbm = sfsfbm;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getDafsText() {
        return dafsText;
    }

    public void setDafsText(String dafsText) {
        this.dafsText = dafsText;
    }

    public Integer getSftb() {
        return sftb;
    }

    public void setSftb(Integer sftb) {
        this.sftb = sftb;
    }

    public String getBaqsyqkbh() {
        return baqsyqkbh;
    }

    public void setBaqsyqkbh(String baqsyqkbh) {
        this.baqsyqkbh = baqsyqkbh;
    }

    public Integer getSfythcj() {
        return sfythcj;
    }

    public void setSfythcj(Integer sfythcj) {
        this.sfythcj = sfythcj;
    }

    public String getXsys() {
        return xsys;
    }

    public void setXsys(String xsys) {
        this.xsys = xsys;
    }

    public Date getAqjckssj() {
        return aqjckssj;
    }

    public void setAqjckssj(Date aqjckssj) {
        this.aqjckssj = aqjckssj;
    }

    public Date getAqjcjssj() {
        return aqjcjssj;
    }

    public void setAqjcjssj(Date aqjcjssj) {
        this.aqjcjssj = aqjcjssj;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getMjXm() {
        return mjXm;
    }

    public void setMjXm(String mjXm) {
        this.mjXm = mjXm;
    }

    public String getZbdwMc() {
        return zbdwMc;
    }

    public void setZbdwMc(String zbdwMc) {
        this.zbdwMc = zbdwMc;
    }

    public String getDwxtbh() {
        return dwxtbh;
    }

    public void setDwxtbh(String dwxtbh) {
        this.dwxtbh = dwxtbh;
    }

    public String getSsclid() {
        return ssclid;
    }

    public void setSsclid(String ssclid) {
        this.ssclid = ssclid;
    }

    public Integer getSfznbaq() {
        return sfznbaq;
    }

    public void setSfznbaq(Integer sfznbaq) {
        this.sfznbaq = sfznbaq;
    }

    public Date getZhsj() {
        return zhsj;
    }

    public void setZhsj(Date zhsj) {
        this.zhsj = zhsj;
    }

    public String getZhdxzqh() {
        return zhdxzqh;
    }

    public void setZhdxzqh(String zhdxzqh) {
        this.zhdxzqh = zhdxzqh;
    }

    public String getZhdxzqhmc() {
        return zhdxzqhmc;
    }

    public void setZhdxzqhmc(String zhdxzqhmc) {
        this.zhdxzqhmc = zhdxzqhmc;
    }

    public String getSswpzt() {
        return sswpzt;
    }

    public void setSswpzt(String sswpzt) {
        this.sswpzt = sswpzt;
    }

    public String getCkzkzpid() {
        return ckzkzpid;
    }

    public void setCkzkzpid(String ckzkzpid) {
        this.ckzkzpid = ckzkzpid;
    }

    public Integer getXgbs() {
        return xgbs;
    }

    public void setXgbs(Integer xgbs) {
        this.xgbs = xgbs;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    public Date getSjbq() {
        return sjbq;
    }

    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }

    public Integer getrSfyj() {
        return rSfyj;
    }

    public void setrSfyj(Integer rSfyj) {
        this.rSfyj = rSfyj;
    }

    public String getRtbj() {
        return rtbj;
    }

    public void setRtbj(String rtbj) {
        this.rtbj = rtbj;
    }

    public String getXbMjSfzh() {
        return xbMjSfzh;
    }

    public void setXbMjSfzh(String xbMjSfzh) {
        this.xbMjSfzh = xbMjSfzh;
    }

    public String getXbMjXm() {
        return xbMjXm;
    }

    public void setXbMjXm(String xbMjXm) {
        this.xbMjXm = xbMjXm;
    }

    public String getRyztMulti() {
        return ryztMulti;
    }

    public void setRyztMulti(String ryztMulti) {
        this.ryztMulti = ryztMulti;
    }

    public String getRssj1() {
        return rssj1;
    }

    public void setRssj1(String rssj1) {
        this.rssj1 = rssj1;
    }

    public String getRssj2() {
        return rssj2;
    }

    public void setRssj2(String rssj2) {
        this.rssj2 = rssj2;
    }

    public String getCssj1() {
        return cssj1;
    }

    public void setCssj1(String cssj1) {
        this.cssj1 = cssj1;
    }

    public String getCssj2() {
        return cssj2;
    }

    public void setCssj2(String cssj2) {
        this.cssj2 = cssj2;
    }

    public String getCsrq1() {
        return csrq1;
    }

    public void setCsrq1(String csrq1) {
        this.csrq1 = csrq1;
    }

    public String getCsrq2() {
        return csrq2;
    }

    public void setCsrq2(String csrq2) {
        this.csrq2 = csrq2;
    }

    public List<String> getRybhList() {
        return rybhList;
    }

    public void setRybhList(List<String> rybhList) {
        this.rybhList = rybhList;
    }

    public List<String> getRyztList() {
        return ryztList;
    }

    public void setRyztList(List<String> ryztList) {
        this.ryztList = ryztList;
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

    public String getCwgid() {
        return cwgid;
    }

    public void setCwgid(String cwgid) {
        this.cwgid = cwgid;
    }

    public String getCwglx() {
        return cwglx;
    }

    public void setCwglx(String cwglx) {
        this.cwglx = cwglx;
    }

    public String getCwgbh() {
        return cwgbh;
    }

    public void setCwgbh(String cwgbh) {
        this.cwgbh = cwgbh;
    }

    public String getSjgBh() {
        return sjgBh;
    }

    public void setSjgBh(String sjgBh) {
        this.sjgBh = sjgBh;
    }

    public String getSjgId() {
        return sjgId;
    }

    public void setSjgId(String sjgId) {
        this.sjgId = sjgId;
    }

    public String getWdbhL() {
        return wdbhL;
    }

    public void setWdbhL(String wdbhL) {
        this.wdbhL = wdbhL;
    }

    public String getWdbhH() {
        return wdbhH;
    }

    public void setWdbhH(String wdbhH) {
        this.wdbhH = wdbhH;
    }

    public String getDdkg() {
        return ddkg;
    }

    public void setDdkg(String ddkg) {
        this.ddkg = ddkg;
    }

    public String getYwbhStr() {
        return ywbhStr;
    }

    public void setYwbhStr(String ywbhStr) {
        this.ywbhStr = ywbhStr;
    }

    public String getTarybh() {
        return tarybh;
    }

    public void setTarybh(String tarybh) {
        this.tarybh = tarybh;
    }

    public String getRsWpAllBizId() {
        return rsWpAllBizId;
    }

    public void setRsWpAllBizId(String rsWpAllBizId) {
        this.rsWpAllBizId = rsWpAllBizId;
    }

    public String getIsUpdateywbh() {
        return isUpdateywbh;
    }

    public void setIsUpdateywbh(String isUpdateywbh) {
        this.isUpdateywbh = isUpdateywbh;
    }

    public String getYytj() {
        return yytj;
    }

    public void setYytj(String yytj) {
        this.yytj = yytj;
    }

    public String getSycphm() {
        return sycphm;
    }

    public void setSycphm(String sycphm) {
        this.sycphm = sycphm;
    }

    public String getXlzt() {
        return xlzt;
    }

    public void setXlzt(String xlzt) {
        this.xlzt = xlzt;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }
}
