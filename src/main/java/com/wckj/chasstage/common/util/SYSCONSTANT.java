package com.wckj.chasstage.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author {XL-SaiAren}
 * @date 创建时间：2017年11月14日 下午5:28:58
 * @version V2.0.0 类说明
 */
public interface SYSCONSTANT {
	/**
	 * 通用状态
	 */
	/** 是/开启 1 */
	String Y = "1";
	/** 是 */
	Boolean Y_B = true;
	/** 是/开启 1 */
	Integer Y_I = 1;
	/** 是/关闭 1 */
	Integer OFF = 1;
	/** 否/关闭 0 */
	String N = "0";
	/** 否 */
	Boolean N_B = false;
	/** 否/关闭 0 */
	Integer N_I = 0;
	/** 否/开启 0 */
	Integer ON = 0;
	/**
	 * 是否 是
	 */
	String SF_S = "1";
	/**
	 * 是否 否
	 */
	String SF_F = "0";

	/** ALL_DATA_MARK */
	/** 正常 0 */
	String ALL_DATA_MARK_NORMAL = "0";
	/** 正常 0 */
	Integer ALL_DATA_MARK_NORMAL_I = 0;
	/** 删除 1 */
	String ALL_DATA_MARK_DELETE = "1";
	/** 删除 1 */
	Integer ALL_DATA_MARK_DELETE_I = 1;
	/** 失效 2 */
	String ALL_DATA_MARK_FAILURE = "2";
	/** 失效 2 */
	Integer ALL_DATA_MARK_FAILURE_I = 2;

	/** ALLLOCATION */
	/** 定位 1 */
	String ALL_LOCATION_ODW = "1";
	/** 不定位 0 */
	String ALL_LOCATION_NDW = "0";
	/** 进行中 1 */
	String ALL_LOCATION_JXZ = "1";
	/** 结束 2 */
	String ALL_LOCATION_JS = "2";

	/** 区域/房间类型，1 登记间 */
	String FJLX_DJS = "1";
	/** 区域/房间类型 2 信息采集间 */
	String FJLX_XXCJJ = "2";
	/** 区域/房间类型，4 看守区 等候室 候问室 */
	String FJLX_DHS = "4";
	/** 区域/房间类型，3 审讯室 询问室 */
	String FJLX_SXS = "3";


	/** 房间类型 */
	/** 登记区 1 */
	String ZD_CASE_FJLX_DJQ = "1";
	/** 信息采集室 2 */
	String ZD_CASE_FJLX_XXCJS = "2";
	/** 审讯室 3 */
	String ZD_CASE_FJLX_SXS = "3";
	/** 看守区 4 */
	String ZD_CASE_FJLX_KSQ = "4";
	/** 其他 5 */
	String ZD_CASE_FJLX_QT = "5";
	/** 餐饮 9 */
	String ZD_CASE_FJLX_CY = "9";
	/** 尿检区 11*/
	String ZD_CASE_FJLX_NJQ = "11";


	/** ZDBOXTYPE */
	/** wu 无 0 */
	String BOX_TYPE_W = "0";
	/** xiao 小 3 */
	String BOX_TYPE_X = "3";
	/** zhong 中2 */
	String BOX_TYPE_Z = "2";
	/** da 大 1 */
	String BOX_TYPE_D = "1";
	/** chaoda 超大 4 */
	String BOX_TYPE_CD = "4";

	/** 审讯室状态 */
	/** 审讯室电源状态 */
	/** 审讯室排风扇状态 */
	/** KONGXIAN 空闲1 */
	String SXSZT_KX = "1";
	/** SHIYONG 使用2 */
	String SXSZT_SY = "2";
	/** TINGYONG 停用3 */
	String SXSZT_TY = "3";
	/** 审讯室 继续用电 */
	String SXSZT_JX = "4";
	/** 讯问室 1 */
	String SXSLX_XWS = "1";
	/** 询问室2 */
	String SXSLX_XWS2 = "2";
	/** 特殊审讯室3 */
	String SXSLX_TSXWS = "3";
	/** 未成年询讯问室 4 */
	String SXSLX_WCNXWS = "4";
	/** 讯/询问室 */
	String SXSLX_XXWS = "5";

	/** 设备功能 */
	String SBGN_SSWPG = "1";
	String SBGN_RSWPPZ = "2";
	String SBGN_NJPZ = "3";
	String SBGN_CSWPPZ = "4";
	String SBGN_FPSXS = "5";
	String SBGN_DC = "6";
	String SBGN_DHSLED = "7";
	String SBGN_SXSLED = "8";
	String SBGN_QQDH = "9";
	String SBGN_PFS = "10";
	String SBGN_DP = "11";
	String SBGN_TTB = "12";
	String SBGN_DWB = "13";
	String SBGN_DZSZ = "14";
	String SBGN_DWJZ = "15";
	String SBGN_DWBQ = "16";
	String SBGN_TGTC = "17";
	String SBGN_TGSH = "18";
	String SBGN_LKSXS = "19";
	String SBGN_DDDHS = "20";
	String SBGN_YJJB = "21";
	String SBGN_DKQQDH = "22";
	String SBGN_ZNQT = "23";
	String SBGN_XGB = "24";
	String SBGN_XP = "25";
	String SBGN_CKDWB = "26";
	String SBGN_CKBJQ = "27";
	String SBGN_YYBB = "28";
	String SBGN_CJDKQ = "29";
	String SBGN_JZ = "30";
	String SBGN_ZDMK = "31";
	String SBGN_ZDMG = "32";
	String SBGN_ZDMT = "33";
	String SBGN_CPSB = "34";
	String SBGN_ZSD = "35";
	String SBGN_MJXXSLED = "36";
	String SBGN_WZG = "37";
	String SBGN_CKJZLQ = "38";
	//看守区读卡器
	String SBGN_KSQDKQ = "47";
	String SBGN_WCNKSQDKQ = "48";
	//看守区大屏
	String SBGN_KSQDP = "50";
	String SBGN_WCNKSQDP = "51";
	//手环胸卡归还收纳柜
	String SBGN_GHSNG = "94";
	/** 设备类型 */
	/** led 1 */
	String SBLX_LED = "1";
	/** 摄像头 2 */
	String SBLX_SXT = "2";
	/** 继电器3 */
	String SBLX_JDQ = "3";
	/** 智能柜4 */
	String SBLX_ZNG = "4";
	/** 读卡器5  */
	String SBLX_DKQ = "5";
	/** 标签6 */
	String SBLX_BQ = "6";
	/** 天线7 */
	String SBLX_TX = "7";
	/** 基站8 */
	String SBLX_JZ = "8";
	/** 门禁主机 */
	String SBLX_MJZJ = "9";
	String SBLX_NVR = "10";
	/** 电子水牌*/
	String SBLX_DZSP = "11";
	/** 预约类型 */
	/** 入区预约 0 */
	String YYLX_RQYY = "0";
	/** 审讯室预约 1 */
	String YYLX_SXSYY = "1";
	/** 出区预约 2 */
	String YYLX_CQYY = "2";

	/** 预约状态 */
	/** 待申请 0 */
	String YYZT_DSQ = "0";
	/** 待审批 1 */
	String YYZT_DSP = "1";
	/** 预约成功 2 */
	String YYZT_YYCG = "2";
	/** 审批未通过 3 */
	String YYZT_SPWTG = "3";
	/** 预约失效 4 */
	String YYZT_YYSX = "4";
	/** 已失效5 */
	String YYZT_YSX = "5";
	/** 预约出所原因 */
	/** 出所辨认0 */
	String YYCSYY_CSBR = "0";
	/** 押送1 */
	String YYCSYY_YS = "1";
	/** 释放 2 */
	String YYCSYY_SF = "2";

	/** 离开类型 */
	/** 入所 */
	String LKLX_RS = "0";
	/** 临时出所 */
	String LKLX_LSCS = "1";
	/** 出所 */
	String LKLX_CS = "2";
	/** cssp在所 */
	String CSSP_RYZT_ZS = "0";
	/** cssp临时出所 */
	String CSSP_RYZT_LSCS = "1";
	/** cssp回所 */
	String CSSP_RYZT_HS = "2";
	/** CHUSUO 出所 类型 2 */
	String CSSP_SHLX_CS = "2";
	/** LINGSHICHUSHUO 临时出所 类型1 */
	String CSSP_SHLX_LSCS = "1";
	/** ZDCASEBAQRYZT */
	/** RUSUOSHENHE 入所审核 00 */
	String BAQRYZT_RSSH = "00";
	/** ZAISUO 在所 01 */
	String BAQRYZT_ZS = "01";
	/** DAISHENHE 出所审核 02 */
	String BAQRYZT_DSH = "02";
	/** DAICHUSUO 待出所 03 */
	String BAQRYZT_DCS = "03";
	/** YICHUSUO 已出所 04 */
	String BAQRYZT_YCS = "04";
	/** LINSHICHUSUODAISHENHE 临出审核 05 */
	String BAQRYZT_LCSH = "05";
	/** LINSHICHUSUO 临时出所 06 */
	String BAQRYZT_LSCS = "06";
	/** SHANCHUSHENHE 删除审核 07 */
	String BAQRYZT_SCSH = "07";
	/** SHANCHU 删除 08 */
	String BAQRYZT_SC = "08";
	/** zaitao 在逃 09 */
	String BAQRYZT_ZT = "09";

	/** 人员调查记录状态 */
	/** 人员调查记录 进行中 1 */
	String BAQRYDCZT_JXZ = "1";
	/** 人员调查记录 结束 2 */
	String BAQRYDCZT_JS = "2";
	/** 人员调查记录 押送3(1本地押送 2外地押送) */
	String BAQRYDCZT_YS = "3";

	/** ZDCASEDAFS 到案方式 */
	/** XINGSHICHUANHUAN 刑事传唤 01 */
	String DAFS_XSCH = "01";
	/** HANGZHENGCHUANHUAN 行政传唤 02 */
	String DAFS_HZCH = "02";
	/** JIXUPANWEN 继续盘问 03 */
	String DAFS_JXPW = "03";
	/** QUNZHONGNIUSONG 群众扭送 04 */
	String DAFS_QZNS = "04";
	/** TOUANZISHOU 投案自首 05 */
	String DAFS_TAZS = "05";
	/** JUCHUAN 拘传 06 */
	String DAFS_JC = "06";
	/** CHUANXUN 传讯 07 */
	String DAFS_CX = "07";
	/** WAICHANWEIYISONG 外单位移送 08 */
	String DAFS_WCWYS = "08";
	/** QITA 其他 99 */
	String DAFS_QT = "99";

	/** 等候室类型 */
	/** HOUWENSHI 候问室 1 */
	String DHSLX_HWS = "1";
	/** DENGHOUSHI 等候室 2 */
	String DHSLX_DHS = "2";
	/**
	 * 普通等候室
	 */
	String DHSLX_PT = "2";
	/**
	 * 单独看押
	 */
	String DHSLX_PT_DDKY = "92";
	/**
	 * 特殊等候室
	 */
	String DHSLX_TS = "3";
	String DHSLX_TS_DDKY = "93";
	/**
	 * 男等候室
	 */
	String DHSLX_NAN = "5";
	String DHSLX_NAN_DDKY = "95";
	/**
	 * 女等候室
	 */
	String DHSLX_NV = "6";
	String DHSLX_NV_DDKY = "96";
	/**
	 * 未成年人等候室
	 */
	String DHSLX_WCN = "7";
	String DHSLX_WCN_DDKY = "97";
	/**
	 * 临时特殊等候室
	 */
	String DHSLX_LSTS = "4";
	/** 等候室分配状态 */
	/** FENGPEI 分配 1 */
	String DHSTZ_FP = "1";
	/** ZHANLI 暂离 2 */
	String DHSTZ_ZL = "2";

	/** 亲情电话申请状态 */
	/** 亲情电话申请1 */
	Integer QQTEL_SQ = 0;
	/** 亲情电话审批中2 */
	Integer QQTEL_SP = 2;
	/** 亲情电话审批通过3 */
	Integer QQTEL_SPTG = 1;
	/** 亲情电话审批不通过4 */
	Integer QQTEL_SPBTG = 3;
	/** 亲情电话使用中 5 */
	Integer QQTEL_SYZ = 4;
	/** 亲情电话结束 6 */
	Integer QQTEL_JS = 5;

	/** 标签类型 嫌疑人1 */
	String TAGTYPE_XYR = "1";
	/** 标签类型 民警 2 */
	String TAGTYPE_MJ = "2";
	/** 标签类型 警车3 */
	String TAGTYPE_JC = "3";
	/** 标签类型 访客 4 */
	String TAGTYPE_FK = "4";

	/** 人员类型常量-办案区人员 **/
	String RYLX_BAQRY = "0100";
	/** 人员类型常量-犯罪嫌疑人 字典变更RYLB 0202**/
	String RYLX_FZXYR = "0202";
	/** 人员类型常量-违法行为人 **/
	String RYLX_WFXWR = "0300";
	/** 人员类型常量-另案人员 **/
	String RYLX_LARY = "0400";
	/** 人员类型常量-报案人 **/
	String RYLX_BAR = "0501";
	/** 人员类型常量-受害人 **/
	String RYLX_SHR = "0502";
	/** 人员类型常量-证人 **/
	String RYLX_ZR = "0503";
	/** 人员类型常量-保证人 **/
	String RYLX_BZR = "0504";
	/** 人员类型常量-辩护人 **/
	String RYLX_BHR = "0505";
	/** 人员类型常量-法人 **/
	String RYLX_FR = "0506";
	/** 人员类型常量-负责人 **/
	String RYLX_ZRR = "0507";
	/** 人员类型常量-赔偿请求人 **/
	String RYLX_PCCQR = "0600";

	/** 随身物品类型-随身物品 **/
	String SSWPLX_SSWP = "01";
	/** 随身物品类型-涉案物品 **/
	String SSWPLX_SAWP = "02";
	/** 随身物品类别-普通物品 **/
	String SSWPLB_PTWP = "01";
	/** 随身物品状态-在所物品 **/
	String SSWPZT_ZSWP = "01";
	/** 随身物品类别-出所物品 **/
	String SSWPZT_CSWP = "02";
	/** 随身物品类别-暂不处理 **/
	String SSWPZT_ZBCL = "31";
	/** 随身物品类别-本人领回 **/
	String SSWPZT_BRLH = "21";
	/** 随身物品类别-家属领回 **/
	String SSWPZT_JSLH = "22";
	/** 随身物品类别-临时取物 **/
	String SSWPZT_LSQW = "9999";
	/** 办案区人员随身物品状态 **/
	/** 办案区人员随身物品状态--无 **/
	String BAQRY_SSWPZT_WU = "01";
	/** 办案区人员随身物品状态--未处理 **/
	String BAQRY_SSWPZT_WCL = "02";
	/** 办案区人员随身物品状态--部分处理 **/
	String BAQRY_SSWPZT_BFCL = "03";
	/** 办案区人员随身物品状态--已处理 **/
	String BAQRY_SSWPZT_YCL = "04";
	/** 随身物品照片类别-入所总照片*/
	String SSWP_ZP_RS_ZL = "01";
	/** 随身物品照片类别-明细照片*/
	String SSWP_ZP_MX = "02";
	/** 随身物品照片类别-出所总照片*/
	String SSWP_ZP_CS_ZL = "03";

	/** 审批/审核状态 sp/shzt **/
	/** 审批/审核状态 审批中 0 **/
	String SHZT_SPZ = "0";
	/** 审批/审核状态 审批通过 1 **/
	String SHZT_SPTG = "1";
	/** 审批/审核状态 审批不通过-1 **/
	String SHZT_SPBTG = "-1";

	/** 办案区人员页面跳转 */
	/** 办案区人员 */
	String FROMSIGNBAQRYXX = "baqryxx";
	/** 办案区警情人员列表 */
	String FROMSIGNJQRYXX = "jqryxx";
	/** 盘查人员 */
	String FROMSIGNPCRY = "pcryxx";
	/**执法办案嫌疑人*/
	String FROMSIGNXYR = "zfbaxyr";

	/** 预警类别*/
	/** 男女混关1 */
	String YJLB_HG = "1";
	/** 同案 2*/
	String YJLB_TA = "2";
	/** 巡更3 */
	String YJLB_XG = "3";
	/** 脱岗预警 4*/
	String YJLB_TG = "4";
	/** 人员逃脱5 */
	String YJLB_TT = "5";
	/** 手环没电 */
	String YJLB_MD = "6";
	/** 人员聚集 */
	String YJLB_JJ = "7";
	/** 人员心率异常 */
	String YJLB_XL = "8";
	String YJLB_DRSX = "9";
	String YJLB_MJTG = "10";
	String YJLB_BLWKL = "11";
	String YJLB_RQWSX = "12";
	String YJLB_RQCS = "13";
	String YJLB_YTHWCJ = "14";
	String YJLB_DRSX_XWFX = "15";
	/** 收纳柜存量预警*/
	String YJLB_SNGCLYJ = "211";
	/**肢体冲突**/
	String YJLB_ZTCT="132";
	/**攀高行为**/
	String YJLB_PG="121";

	/** 预警级别*/
	/** 忽略0*/
	String YJJB_HL = "0";
	/** 一级1 同案 混关 聚集 */
	String YJJB_YJ = "1";
	/** 二级 2*/
	String YJJB_EJ = "2";
	/** 三级 3 心率异常、未巡更打卡 需要触发报警*/
	String YJJB_SJ = "3";
	/** 特级 4 逃脱需要触发报警*/
	String YJJB_TJ = "4";
	/** 预警状态*/
	/**未处理 0*/
	String YJZT_WCL = "0";
	/**已处理 1*/
	String YJZT_YCL = "1";
	/**已忽略 2*/
	String YJZT_YHL = "2";

	/**办案区前台流程节点*/
	String LCJD_XXDJ = "01";  //信息登记
	String LCJD_XXDJ_QZ = "02";
	String LCJD_AQJC = "03";
	String LCJD_SSWPCF = "04";
	String LCJD_AQJC_QZ = "05";
	String LCJD_SSWP_GLYQZ = "06";
	String LCJD_YTHCJ = "07";
	String LCJD_DHSFP = "08";
	String LCJD_XXW = "09";
	String LCJD_LSCS = "10";
	String LCJD_CS =  "11";

	/**damin*/
	String ADMIN = "admin";
	/**省厅级*/
	String PROVINCE = "province";
	/**地市级*/
	String CITY = "city";
	/**区县级*/
	String REG = "reg";
	/**机构级*/
	String ORG = "org";
	/**用户级*/
	String USER = "user";

	/** 特殊群体*/
	/**未成年 07*/
	String TSQT_WCN = "07";
	String TSQT_ZDJH = "14";
	/*--------------------------kafka-----------------------------*/
	String TOPIC_NAME = "znba-baq";
	String MSG_TYPE = "ZFBA_AJXX_MODIFY_ADD";



	/**选择业务编号*/
	String YWBH_A = "01";
	String YWBH_J = "02";
	String YWBH_X = "03";


	/**民警入区状态  预约 在区 出区*/
	String MJRQ_YY = "01";
	String MJRQ_ZQ = "02";
	String MJRQ_CQ = "03";

	/**证件类型*/

	/**身份证号*/
	String ZJLX_SFZH = "111";
	/**民警警号*/
	String ZJLX_MJJH = "409";

	/**任务类型:入区监护*/
	String JHRWLX_RQJH = "01";
	/**任务类型:出区监护*/
	String JHRWLX_CQJH = "02";
	/**任务类型:体检监护*/
	String JHRWLX_TJJH = "03";
	/**任务类型:审讯监护*/
	String JHRWLX_SXJH = "04";
	/**任务类型:送押监护*/
	String JHRWLX_SYJH = "05";
	/**任务类型:审讯调度*/
	String JHRWLX_SXDD = "06";
	/**任务类型:适格成年人调度*/
	String JHRWLX_SGCNRDD = "07";

	/**任务状态：待分配*/
	String JHRWZT_DFP = "01";
	/**任务状态：待执行*/
	String JHRWZT_DZX = "02";
	/**任务状态：执行中*/
	String JHRWZT_ZXZ = "03";
	/**任务状态：已执行*/
	String JHRWZT_YZX = "04";

	/**设备请求对接*/
	/**aes key*/
	String AES_KEY = "chasEt-aes-key";
	/**偏移量*/
	String AES_IV = "chasEt-aes-iv";
	/**请求端口*/
	String HTTP_PORT = "chasEt-http-port";
	/**请求地址*/
	String HTTP_URL = "chasEt-http-url";

	/**光盘-状态*/
	String COMPACT_DISC_KLWC = "01"; //刻录完成
	String COMPACT_DISC_DRK = "02"; //待入库
	String COMPACT_DISC_ZK = "03"; //在库
	String COMPACT_DISC_CK = "04"; //出库

	/**光盘操作记录-操作类型*/
	String COMPACT_DISC_CR = "01"; //存入
	String COMPACT_DISC_QC = "02"; //取出

	/**光盘出库原因*/
	String COMPACT_DISC_JY = "01"; //借用
	String COMPACT_DISC_YJ = "02"; //移交
	String COMPACT_DISC_XH = "03"; //销毁
	//注释
}
